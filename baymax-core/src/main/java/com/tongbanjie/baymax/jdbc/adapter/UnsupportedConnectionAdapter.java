package com.tongbanjie.baymax.jdbc.adapter;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * Created by sidawei on 16/1/26.
 */
public abstract class UnsupportedConnectionAdapter implements Connection{

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        throw new RuntimeException("not support exception");
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        throw new RuntimeException("not support exception");
    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        throw new SQLException("not support exception");
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        throw new SQLException("not support exception");
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        throw new SQLException("not support exception");
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        throw new SQLException("not support exception");
    }


    @Override
    public Clob createClob() throws SQLException {
        throw new SQLException("not support exception");
    }

    @Override
    public Blob createBlob() throws SQLException {
        throw new SQLException("not support exception");
    }

    @Override
    public NClob createNClob() throws SQLException {
        throw new SQLException("not support exception");
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        throw new SQLException("not support exception");
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        throw new UnsupportedOperationException("getTypeMap");
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        throw new UnsupportedOperationException("setTypeMap");
    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
        throw new UnsupportedOperationException("setHoldability");
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        throw new UnsupportedOperationException("setSavepoint");
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        throw new UnsupportedOperationException("setSavepoint");
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        throw new UnsupportedOperationException("rollback savepoint");
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        throw new UnsupportedOperationException("releaseSavepoint");
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        throw new UnsupportedOperationException("nativeSQL");
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
        throw new UnsupportedOperationException("setCatalog");
    }

    @Override
    public String getCatalog() throws SQLException {
        throw new UnsupportedOperationException("getCatalog");
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new UnsupportedOperationException();
    }


    /**
     * Sets the given schema name to access.
     * <p/>
     * If the driver does not support schemas, it will
     * silently ignore this request.
     * <p/>
     * Calling {@code setSchema} has no effect on previously created or prepared
     * {@code Statement} objects. It is implementation defined whether a DBMS
     * prepare operation takes place immediately when the {@code Connection}
     * method {@code prepareStatement} or {@code prepareCall} is invoked.
     * For maximum portability, {@code setSchema} should be called before a
     * {@code Statement} is created or prepared.
     *
     * @param schema the name of a schema  in which to work
     * @throws SQLException if a database access error occurs
     *                      or this method is called on a closed connection
     * @see #getSchema
     * @since 1.7
     */
    public void setSchema(String schema) throws SQLException {
        throw new UnsupportedOperationException();
    }

    /**
     * Retrieves this <code>Connection</code> object's current schema name.
     *
     * @return the current schema name or <code>null</code> if there is none
     * @throws SQLException if a database access error occurs
     *                      or this method is called on a closed connection
     * @see #setSchema
     * @since 1.7
     */
    public String getSchema() throws SQLException {
        throw new UnsupportedOperationException();
    }

    /**
     * Terminates an open connection.  Calling <code>abort</code> results in:
     * <ul>
     * <li>The connection marked as closed
     * <li>Closes any physical connection to the database
     * <li>Releases resources used by the connection
     * <li>Insures that any thread that is currently accessing the connection
     * will either progress to completion or throw an <code>SQLException</code>.
     * </ul>
     * <p/>
     * Calling <code>abort</code> marks the connection closed and releases any
     * resources. Calling <code>abort</code> on a closed connection is a
     * no-op.
     * <p/>
     * It is possible that the aborting and releasing of the resources that are
     * held by the connection can take an extended period of time.  When the
     * <code>abort</code> method returns, the connection will have been marked as
     * closed and the <code>Executor</code> that was passed as a parameter to abort
     * may still be executing tasks to release resources.
     * <p/>
     * This method checks to see that there is an <code>SQLPermission</code>
     * object before allowing the method to proceed.  If a
     * <code>SecurityManager</code> exists and its
     * <code>checkPermission</code> method denies calling <code>abort</code>,
     * this method throws a
     * <code>java.lang.SecurityException</code>.
     *
     * @param executor The <code>Executor</code>  implementation which will
     *                 be used by <code>abort</code>.
     * @throws SQLException      if a database access error occurs or
     *                           the {@code executor} is {@code null},
     * @throws SecurityException if a security manager exists and its
     *                           <code>checkPermission</code> method denies calling <code>abort</code>
     * @see SecurityManager#checkPermission
     * @see Executor
     * @since 1.7
     */
    public void abort(Executor executor) throws SQLException {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the maximum period a <code>Connection</code> or
     * objects created from the <code>Connection</code>
     * will wait for the database to reply to any one request. If any
     * request remains unanswered, the waiting method will
     * return with a <code>SQLException</code>, and the <code>Connection</code>
     * or objects created from the <code>Connection</code>  will be marked as
     * closed. Any subsequent use of
     * the objects, with the exception of the <code>close</code>,
     * <code>isClosed</code> or <code>Connection.isValid</code>
     * methods, will result in  a <code>SQLException</code>.
     * <p/>
     * <b>Note</b>: This method is intended to address a rare but serious
     * condition where network partitions can cause threads issuing JDBC calls
     * to hang uninterruptedly in socket reads, until the OS TCP-TIMEOUT
     * (typically 10 minutes). This method is related to the
     * {@link #abort abort() } method which provides an administrator
     * thread a means to free any such threads in cases where the
     * JDBC connection is accessible to the administrator thread.
     * The <code>setNetworkTimeout</code> method will cover cases where
     * there is no administrator thread, or it has no access to the
     * connection. This method is severe in it's effects, and should be
     * given a high enough value so it is never triggered before any more
     * normal timeouts, such as transaction timeouts.
     * <p/>
     * JDBC driver implementations  may also choose to support the
     * {@code setNetworkTimeout} method to impose a limit on database
     * response time, in environments where no network is present.
     * <p/>
     * Drivers may internally implement some or all of their API calls with
     * multiple internal driver-database transmissions, and it is left to the
     * driver implementation to determine whether the limit will be
     * applied always to the response to the API call, or to any
     * single  request made during the API call.
     * <p/>
     * <p/>
     * This method can be invoked more than once, such as to set a limit for an
     * area of JDBC code, and to reset to the default on exit from this area.
     * Invocation of this method has no impact on already outstanding
     * requests.
     * <p/>
     * The {@code Statement.setQueryTimeout()} timeout value is independent of the
     * timeout value specified in {@code setNetworkTimeout}. If the query timeout
     * expires  before the network timeout then the
     * statement execution will be canceled. If the network is still
     * active the result will be that both the statement and connection
     * are still usable. However if the network timeout expires before
     * the query timeout or if the statement timeout fails due to network
     * problems, the connection will be marked as closed, any resources held by
     * the connection will be released and both the connection and
     * statement will be unusable.
     * <p/>
     * When the driver determines that the {@code setNetworkTimeout} timeout
     * value has expired, the JDBC driver marks the connection
     * closed and releases any resources held by the connection.
     * <p/>
     * <p/>
     * This method checks to see that there is an <code>SQLPermission</code>
     * object before allowing the method to proceed.  If a
     * <code>SecurityManager</code> exists and its
     * <code>checkPermission</code> method denies calling
     * <code>setNetworkTimeout</code>, this method throws a
     * <code>java.lang.SecurityException</code>.
     *
     * @param executor     The <code>Executor</code>  implementation which will
     *                     be used by <code>setNetworkTimeout</code>.
     * @param milliseconds The time in milliseconds to wait for the database
     *                     operation
     *                     to complete.  If the JDBC driver does not support milliseconds, the
     *                     JDBC driver will round the value up to the nearest second.  If the
     *                     timeout period expires before the operation
     *                     completes, a SQLException will be thrown.
     *                     A value of 0 indicates that there is not timeout for database operations.
     * @throws SQLException                    if a database access error occurs, this
     *                                         method is called on a closed connection,
     *                                         the {@code executor} is {@code null},
     *                                         or the value specified for <code>seconds</code> is less than 0.
     * @throws SecurityException               if a security manager exists and its
     *                                         <code>checkPermission</code> method denies calling
     *                                         <code>setNetworkTimeout</code>.
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see SecurityManager#checkPermission
     * @see Statement#setQueryTimeout
     * @see #getNetworkTimeout
     * @see #abort
     * @see Executor
     * @since 1.7
     */
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        throw new UnsupportedOperationException();
    }

    /**
     * Retrieves the number of milliseconds the driver will
     * wait for a database request to complete.
     * If the limit is exceeded, a
     * <code>SQLException</code> is thrown.
     *
     * @return the current timeout limit in milliseconds; zero means there is
     * no limit
     * @throws SQLException                    if a database access error occurs or
     *                                         this method is called on a closed <code>Connection</code>
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @see #setNetworkTimeout
     * @since 1.7
     */
    public int getNetworkTimeout() throws SQLException {
        throw new UnsupportedOperationException();
    }
}
