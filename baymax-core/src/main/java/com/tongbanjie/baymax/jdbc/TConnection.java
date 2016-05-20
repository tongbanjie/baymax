package com.tongbanjie.baymax.jdbc;

import com.tongbanjie.baymax.datasource.BaymaxDataSource;
import com.tongbanjie.baymax.jdbc.adapter.UnsupportedConnectionAdapter;
import com.tongbanjie.baymax.jdbc.model.*;
import com.tongbanjie.baymax.router.IRouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

public class TConnection extends UnsupportedConnectionAdapter {
	
	private final static Logger logger = LoggerFactory.getLogger(TConnection.class);

	private BaymaxDataSource            baymaxDataSource;
	private Map<DataSource, Connection> openedConnection        = new ConcurrentHashMap<DataSource, Connection>(2);
	private Connection                  connectionForMetaData;
	private Set<TStatement>             openedStatements        = new HashSet<TStatement>(2);
	private boolean                     isAutoCommit            = true; // jdbc规范，新连接为true
	private boolean                     closed;
	private int                         transactionIsolation    = TRANSACTION_READ_COMMITTED;

    private TExecuter                   executer;

	public TConnection(IRouteService routeService, BaymaxDataSource baymaxDataSource) {
        this.baymaxDataSource = baymaxDataSource;
        executer = new TExecuter(routeService, baymaxDataSource, this, openedConnection);
    }

    /**
     * 执行sql语句
     * @param createCommand
     * @param executeCommand
     * @param parameterCommand
     * @param stmt
     * @return
     * @throws SQLException
     */
	public ResultSetHandler executeSql(StatementCreateCommand createCommand, ExecuteCommand executeCommand, Map<Integer, ParameterCommand> parameterCommand, TStatement stmt) throws SQLException {
        checkClosed();
        return executer.execute(createCommand, executeCommand, parameterCommand, stmt);
	}

	@Override
	public Statement createStatement() throws SQLException {
		checkClosed();
		StatementCreateCommand command = new StatementCreateCommand(StatementCreateMethod.createStatement, null);
		TStatement stmt = new TStatement(this, command);
		openedStatements.add(stmt);
		return stmt;
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		// TODO 暂不不支持设置statement的可保存性,并发性等
		throw new UnsupportedOperationException("暂不不支持设置statement的可保存性,并发性等");
		//return createStatement();
	}

	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		checkClosed();
		StatementCreateCommand command = new StatementCreateCommand(StatementCreateMethod.prepareStatement_sql, new Object[]{sql});
		PreparedStatement stmt = new TPreparedStatement(this, command, sql);
		openedStatements.add((TStatement) stmt);
		return stmt;
	}

	// JDBC事务相关的autoCommit设置、commit/rollback、TransactionIsolation等
	@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		checkClosed();
		if (isAutoCommit() == autoCommit) {
			return;
		}
		this.isAutoCommit = autoCommit;
		if(openedConnection.size() != 0){
			Iterator<Entry<DataSource, Connection>>  ite = openedConnection.entrySet().iterator();
			while(ite.hasNext()){
				ite.next().getValue().setAutoCommit(autoCommit);
			}
		}
	}

	@Override
	public boolean getAutoCommit() throws SQLException {
		checkClosed();
		return this.isAutoCommit;
	}

	@Override
	public void commit() throws SQLException {
		checkClosed();
		if(isAutoCommit){
			return;
		}
		SQLException sqlException = null;
		boolean first = true;
		for(Connection conn : openedConnection.values()){
			try{
				conn.commit();
			}catch(SQLException e){
				if(sqlException == null){
					sqlException = e;
				}
				if(first){
					// 第一次Commit就抛异常了,直接break
					break;
				}
			}
			first = false;
		}
//		if(connectionForMetaData != null){
//			try{
//				connectionForMetaData.commit();
//			}catch(SQLException e){
//				sqlException = e;
//			}
//		}
		if(sqlException != null){
			throw sqlException;
		}
	}

	@Override
	public void rollback() throws SQLException {
		checkClosed();
		if(isAutoCommit){
			return;
		}
		SQLException sqlException = null;
		for(Connection conn : openedConnection.values()){
			try{
				conn.rollback();
			}catch(SQLException e){
				if(sqlException == null){
					sqlException = e;
				}
			}
		}
		// 3.metaData
//        if(connectionForMetaData != null){
//			try{
//				connectionForMetaData.rollback();
//			}catch(SQLException e){
//				sqlException = e;
//			}
//		}
		if(sqlException != null){
			throw sqlException;
		}
	}

	@Override
	public void close() throws SQLException {
		if(closed){
			return;
		}
		List<SQLException> exceptions = new LinkedList<SQLException>();
		// 1.statements
        try {
            // 关闭statement
        	Iterator<TStatement> stmtIte = openedStatements.iterator();
        	while(stmtIte.hasNext()){
        		try {
        			stmtIte.next().close();
                } catch (SQLException e) {
                    exceptions.add(e);
                }
        	}
        } catch(Exception e){
        	logger.error("关闭Tconnection 关闭TStatement异常", e);
        }finally {
            openedStatements.clear();
        }
        // 2.connections
        Iterator<Connection> connIte = openedConnection.values().iterator();
        while(connIte.hasNext()){
        	try{
        		connIte.next().close();
        	}catch(SQLException e){
        		exceptions.add(e);
        	}
        }
        // 3.metaData
        if(connectionForMetaData != null && !connectionForMetaData.isClosed()){
			try{
				connectionForMetaData.close();
				connectionForMetaData = null;
			}catch(SQLException e){
				exceptions.add(e);
			}
		}
        closed = true;
        openedConnection.clear();
        if(exceptions.size() > 0){
        	SQLException exception = exceptions.get(0);
        	if(exceptions.size() > 1){
        		for(int i = 1; i<exceptions.size(); i++){
        			exception.setNextException(exceptions.get(i));
        		}
        	}
        	throw exception;
        }
	}

	@Override
	public boolean isClosed() throws SQLException {
		return closed;
	}

	/**
	 * 如果已经有有效的Connection则拿一个,如果没有则在默认的DataSource上创建一个.
	 */
	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		checkClosed();
		if(connectionForMetaData == null){
			if(openedConnection.size() > 0){
				connectionForMetaData = openedConnection.entrySet().iterator().next().getValue();
			}else{
				connectionForMetaData = baymaxDataSource.getDefaultConnection();
			}
		}
		if(connectionForMetaData.isClosed()){
			throw new SQLException("No operations allowed after connection closed.");
		}
		return connectionForMetaData.getMetaData();
	}

	@Override
	public void setTransactionIsolation(int level) throws SQLException {
		this.transactionIsolation = level;
		if(openedConnection.size() != 0){
			Iterator<Entry<DataSource, Connection>>  ite = openedConnection.entrySet().iterator();
			while(ite.hasNext()){
				ite.next().getValue().setTransactionIsolation(level);
			}
		}
	}

	@Override
	public int getTransactionIsolation() throws SQLException {
		checkClosed();
		return transactionIsolation;
	}

	/**
	 * 自增
	 */
	@Override
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		checkClosed();
		StatementCreateCommand command = new StatementCreateCommand(StatementCreateMethod.prepareStatement_sql_autoGeneratedKeys, new Object[]{sql, autoGeneratedKeys});
		PreparedStatement stmt = new TPreparedStatement(this, command, sql);
		openedStatements.add((TStatement) stmt);
		return stmt;
	}

	/**
	 * 结果集的保持时间
	 */
	@Override
	public int getHoldability() throws SQLException {
		return ResultSet.CLOSE_CURSORS_AT_COMMIT;
	}

	@Override
	public void setReadOnly(boolean readOnly) throws SQLException {
		return;
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		return false; // 始终可读写
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		try {
			return (T) this;
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return this.getClass().isAssignableFrom(iface);
	}

	private void checkClosed() throws SQLException {
		if (isClosed()) {
			throw new SQLException("No operations allowed after connection closed.");
		}
	}

	public boolean isAutoCommit() {
		return isAutoCommit;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	/************************************************/

	@Override
	public SQLWarning getWarnings() throws SQLException {
		return null;
	}

	@Override
	public void clearWarnings() throws SQLException {

	}

	@Override
	public boolean isValid(int timeout) throws SQLException {
		return isClosed();
	}

}
