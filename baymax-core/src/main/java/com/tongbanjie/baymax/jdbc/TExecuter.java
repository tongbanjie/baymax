package com.tongbanjie.baymax.jdbc;

import com.tongbanjie.baymax.datasource.BaymaxDataSource;
import com.tongbanjie.baymax.exception.BayMaxException;
import com.tongbanjie.baymax.exception.TraceContext;
import com.tongbanjie.baymax.jdbc.explain.ExplainResultSet;
import com.tongbanjie.baymax.jdbc.merge.iterator.IteratorResutSet;
import com.tongbanjie.baymax.jdbc.model.*;
import com.tongbanjie.baymax.parser.model.SqlType;
import com.tongbanjie.baymax.parser.utils.SqlTypeUtil;
import com.tongbanjie.baymax.router.IRouteService;
import com.tongbanjie.baymax.router.model.ExecutePlan;
import com.tongbanjie.baymax.router.model.TrargetSqlEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sidawei on 16/1/29.
 */
public class TExecuter {

    private final static Logger logger = LoggerFactory.getLogger(TExecuter.class);

    private IRouteService               routeService;
    private BaymaxDataSource            dataSource;
    private Map<DataSource, Connection> openedConnection;
    private TConnection                 currentConnection;

    public TExecuter(IRouteService routeService, BaymaxDataSource dataSource, TConnection currentConnection, Map<DataSource, Connection> openedConnection){
        this.routeService       = routeService;
        this.dataSource         = dataSource;
        this.openedConnection   = openedConnection;
        this.currentConnection  = currentConnection;
    }

    public ResultSetHandler execute(StatementCreateCommand createCommand, ExecuteCommand executeCommand, Map<Integer, ParameterCommand> parameterCommand, TStatement outStmt) throws SQLException{
        TraceContext trace = new TraceContext();
        try {
            return execute(createCommand, executeCommand, parameterCommand, outStmt, trace);
        }catch (BayMaxException e){
            logger.error("BayMax Execute SQL Error : trace{"+trace.toString()+"}" ,e);
            throw new SQLException(e);
        }catch(SQLException e){
            logger.error("BayMax Execute SQL Error : trace{"+trace.toString()+"}" ,e);
            throw e;
        }
    }

    /**
     * 执行一个sql
     * @param createCommand     创建Statement的命令
     * @param executeCommand    执行Sql的命令
     * @param parameterCommand  sql的参数
     * @param outStmt           应用层获取的Statement
     * @param trace             跟踪
     * @return
     * @throws SQLException
     */
    public ResultSetHandler execute(StatementCreateCommand createCommand, ExecuteCommand executeCommand, Map<Integer, ParameterCommand> parameterCommand, TStatement outStmt, TraceContext trace) throws SQLException {

        // 参数
        boolean isPreparedStatement   = createCommand.getMethod() != StatementCreateMethod.createStatement ? true : false;
        String  sql                   = getSql(isPreparedStatement, outStmt, executeCommand);

        trace.setSql(sql);
        trace.setCreateCommand(createCommand);
        trace.setExecuteCommand(executeCommand);
        trace.setParameterCommand(parameterCommand);

        // 路由
        ExecutePlan plan = routeService.doRoute(sql, parameterCommand);

        if(logger.isDebugEnabled()){
            logger.debug("BayMax execute SQL:" + plan.toString());
        }

        if (plan.getExecuteType() == null) {
            throw new SQLException("执行计划不正确" + plan.toString());	// 检查执行计划
        }

        ResultSetHandler    resultSetHandler = new ResultSetHandler();
        List<TrargetSqlEntity>     sqlList = plan.getSqlList();					// 所有要执行的SQL
        List<ResultSet>     resultSet = new ArrayList<ResultSet>(sqlList.size());

        // 清理
        outStmt.closeOpenedStatement();									// 关闭上一个SQL打开的Statement,一个JDBC规范的Statement只能保持最近一个开发的ResultSet,所以如果用户在同一个Statement上执行SQL,意味着前面的ResultSet可以被关闭了。
        boolean resultType = false;
        
        //处理explain
        SqlType sqlType = SqlTypeUtil.getSqlType(sql);
        if(SqlType.EXPLAIN == sqlType){
        	 resultSet.add(new ExplainResultSet(plan));
        	 resultSetHandler.setResultSet(new IteratorResutSet(resultSet, outStmt, plan));
        	 setResultToStatement(true, outStmt, resultSetHandler);
        	 return resultSetHandler;
        }
        

        // 执行
        for (TrargetSqlEntity target : sqlList) {
            Connection      conn = getConnection(target);
            Statement       targetStatement = getStatement(isPreparedStatement, createCommand, parameterCommand, conn, target);
            ExecuteMethod   method          = executeCommand.getMethod();
            Object[]        args            = executeCommand.getArgs();

            // 保存Statement
            outStmt.addOpenedStatement(targetStatement);
            if(!isPreparedStatement){
                // 替换为目标SQL
                args[0] = target.getTargetSql();
            }
            // 执行SQL
            Object          executeResult   = method.executeMethod(targetStatement, executeCommand.getArgs());
            // 确定方法的返回类型
            resultType = getResultType(executeResult, method);

            if (resultType) {
                // 保存结果集
                resultSet.add(targetStatement.getResultSet());
            }else{
                // 保存影响的行数
                resultSetHandler.addUpdateCount(targetStatement.getUpdateCount());
                // 需要返回自增键
                if(createCommand.getMethod().autoGeneratedKeys() || executeCommand.getMethod().autoGeneratedKeys()){
                    outStmt.setGeneratedKeysResultSet(targetStatement.getGeneratedKeys());
                }
            }
            // 保存返回类型
            resultSetHandler.setResultType(resultType);
        }
        // 合并结果集
        resultSetHandler.setResultSet(TMerger.mearge(plan, resultSet, outStmt));
        // 把resultSetHandler的值直接保存到Statement,减少Statement对他的依赖
        setResultToStatement(resultType, outStmt, resultSetHandler);
        return resultSetHandler;
    }

    /**
     * 获取原始sql
     * @param userPreparedStatement
     * @param stmt
     * @param executeCommand
     * @return
     */
    private String getSql(boolean userPreparedStatement, Statement stmt, ExecuteCommand executeCommand){
        if(userPreparedStatement){
            return ((TPreparedStatement)stmt).getSql();
        }else{
            return (String) executeCommand.getArgs()[0];
        }
    }

    /**
     * 获取一个真正用于执行本次sql的connection
     * @param target
     * @return
     * @throws SQLException
     */
    private Connection getConnection(TrargetSqlEntity target) throws SQLException {
        String      targetPartition = target.getPartition();
        DataSource  targetDataSource = targetPartition == null ? dataSource.getDefaultDataSource() : dataSource.getDataSourceByName(targetPartition);
        Connection  conn = openedConnection.get(targetDataSource);	// 尝试获取一个已经打开的Connection
        if (conn == null) {
            conn = targetDataSource.getConnection();				// 打开一个Connection
            conn.setAutoCommit(currentConnection.getAutoCommit());
            conn.setTransactionIsolation(currentConnection.getTransactionIsolation());
            openedConnection.put(targetDataSource, conn);			// 保存Connection
        }
        return conn;
    }

    /**
     * 获取一个用于执行sql的Statement
     * @param isPreparedStatement
     * @param createCommand
     * @param parameterCommand
     * @param conn
     * @param target
     * @return
     * @throws SQLException
     */
    private Statement getStatement(boolean isPreparedStatement, StatementCreateCommand createCommand, Map<Integer, ParameterCommand> parameterCommand, Connection conn, TrargetSqlEntity target) throws SQLException {
        Statement targetStatement;
        if(isPreparedStatement){
            Object[] args = createCommand.getArgs();
            args[0] = target.getTargetSql();
            targetStatement = createCommand.getMethod().prepareStatement(conn, args); 	//打开PrepareadStatement
            for(ParameterCommand command : parameterCommand.values()){					// 设置SQL参数
                command.getParameterMethod().setParameter((PreparedStatement)targetStatement, command.getArgs());
            }
        }else{
            targetStatement = conn.createStatement();				// 打开普通Statement
        }
        return targetStatement;
    }

    /**
     * 获取sql执行后的返回类型
     * 因为原始的jdbc返回的Object类型,这里需要判断出这个Object是int还是boolean还是ResultSet,这样就需要通过前面收集的command来判断
     * @param executeResult
     * @param method
     * @return
     */
    private boolean getResultType(Object executeResult, ExecuteMethod method){
        switch (method.getReturnType()){
            case int_type:
                // executeUpdate
                return false;
            case result_set_type:
                // executeQueary
                return true;
            case boolean_type:
                //
                return (Boolean) executeResult;
            default:
                throw new BayMaxException("不支持的返回类型:" + method.getReturnType());
        }
    }

    /**
     * 把resultSetHandler的值直接保存到Statement,减少Statement对他的依赖
     * @param resultType
     * @param outStmt
     * @param resultSetHandler
     */
    private void setResultToStatement(boolean resultType, TStatement outStmt, ResultSetHandler resultSetHandler){
        if(resultType){
            outStmt.setCurrentResultSet(resultSetHandler.getResultSet());
        }else{
            outStmt.setCurrentUpdateCount(resultSetHandler.getUpdateCount());
        }
    }
}
