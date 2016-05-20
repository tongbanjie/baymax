package com.tongbanjie.baymax.jdbc;

import com.tongbanjie.baymax.jdbc.adapter.UnsupportedResultSetAdapter;
import com.tongbanjie.baymax.router.model.ExecutePlan;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TResultSet extends UnsupportedResultSetAdapter {

	private List<ResultSet>     resultSet;
    protected ResultSet         currentResultSet;
    private ResultSet           metaDataResultSet;
    private ResultSetMetaData   metaData;

    //protected boolean         isFirstSet; // 当前访问的结果是否为第一个结果集

	private TStatement          statement;
	private boolean             isClosed;

    private ExecutePlan         plan;

	public TResultSet(List<ResultSet> listResultSet, TStatement statement, ExecutePlan plan) {
        this.plan = plan;
		this.statement = statement;
		this.resultSet = listResultSet;
		if (listResultSet != null && listResultSet.size() > 0) {
			this.metaDataResultSet = listResultSet.get(0);
			this.currentResultSet = listResultSet.get(0);
			try {
				this.metaData = this.metaDataResultSet.getMetaData();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
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

	@Override
	public abstract boolean next() throws SQLException;

	@Override
	public void close() throws SQLException {
		if (isClosed) {
			return;
		}
		try {
			this.metaData = null;
			this.metaDataResultSet = null;
			SQLException exception = null; 
			if(resultSet != null){
				for(ResultSet set : resultSet){
					try{
						set.close();
					}catch(SQLException e){
						if(exception == null){
							exception = e;
						}else{
							exception.setNextException(e);
						}
					}
				}
			}
			if (exception != null) {
				throw exception;
			}
		} catch (Exception e) {
			throw new SQLException(e);

		} finally{
			isClosed = true;
		}
	}

	@Override
	public boolean wasNull() throws SQLException {
		return this.currentResultSet.wasNull();
	}

	/**
	 * TODO 暂时以第一个结果集的列为准
	 * 
	 * @return
	 */
	public abstract boolean needEscape();

	/**
	 * 根据MetaData把columnIndex转义为columnLabel
	 * 
	 * @param columnIndex
	 * @return
	 * @throws SQLException
	 */
	public String escapeIndexColumn(int columnIndex) throws SQLException {
		return metaData.getColumnLabel(columnIndex);
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		return this.currentResultSet.getWarnings();
	}

	@Override
	public void clearWarnings() throws SQLException {
		this.currentResultSet.clearWarnings();
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		return this.metaData;
	}

	@Override
	public int findColumn(String columnLabel) throws SQLException {
		// TODO logger
		return currentResultSet.findColumn(columnLabel);
	}

	@Override
	public void setFetchDirection(int direction) throws SQLException {
		if (direction != ResultSet.FETCH_FORWARD) {
			throw new UnsupportedOperationException(); // 如果不是默认方向 抛异常
		}
	}

	/**
	 * 获取此 ResultSet 对象的获取方向。
	 */
	@Override
	public int getFetchDirection() throws SQLException {
		return ResultSet.FETCH_FORWARD; // 默认方向
	}

	@Override
	public void setFetchSize(int rows) throws SQLException {
		return;
	}

	/**
	 * 获取此 ResultSet 对象的获取大小
	 */
	@Override
	public int getFetchSize() throws SQLException {
		return 0; // 0 表示不做限制
	}

	/**
	 * 获取此 ResultSet 对象的类型。类型由创建结果集的 Statement 对象确定。
	 * ResultSet.TYPE_FORWARD_ONLY、 ResultSet.TYPE_SCROLL_INSENSITIVE 或
	 * ResultSet.TYPE_SCROLL_SENSITIVE
	 */
	@Override
	public int getType() throws SQLException {
		return currentResultSet.getType();
	}

	/**
	 * 获取此 ResultSet 对象的并发模式。使用的并发由创建结果集的 Statement 对象确定。 并发类型，
	 * ResultSet.CONCUR_READ_ONLY 或 ResultSet.CONCUR_UPDATABLE
	 */
	@Override
	public int getConcurrency() throws SQLException {
		return currentResultSet.getConcurrency();
	}

	/**
	 * 获取此 ResultSet 对象的可保存性 ResultSet.HOLD_CURSORS_OVER_COMMIT 或
	 * ResultSet.CLOSE_CURSORS_AT_COMMIT
	 */
	@Override
	public int getHoldability() throws SQLException {
		return currentResultSet.getHoldability();
	}

	@Override
	public boolean isClosed() throws SQLException {
		return this.isClosed;
	}

	@Override
	public Statement getStatement() throws SQLException {
		return this.statement;
	}

    public List<ResultSet> getResultSet() {
        return resultSet;
    }
}
