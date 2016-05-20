package com.tongbanjie.baymax.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.tongbanjie.baymax.jdbc.adapter.UnsupportedPreparedStatementAdapter;
import com.tongbanjie.baymax.jdbc.model.ExecuteCommand;
import com.tongbanjie.baymax.jdbc.model.ExecuteMethod;
import com.tongbanjie.baymax.jdbc.model.ParameterCommand;
import com.tongbanjie.baymax.jdbc.model.ParameterMethod;
import com.tongbanjie.baymax.jdbc.model.ResultSetHandler;
import com.tongbanjie.baymax.jdbc.model.StatementCreateCommand;

public class TPreparedStatement extends UnsupportedPreparedStatementAdapter {

	protected Map<Integer/*第几个?占位符*/, ParameterCommand/*占位符的值*/> parameterSettings = new HashMap<Integer, ParameterCommand>();

    protected String sql;

	public TPreparedStatement(TConnection connection, StatementCreateCommand statementCreateCommand, String sql) {
		super(connection, statementCreateCommand);
		this.sql = sql;
	}

	@Override
	public ResultSet executeQuery() throws SQLException {
		execute();
		return super.currentResultSet;
	}

	@Override
	public int executeUpdate() throws SQLException {
		execute();
		return super.currentUpdateCount;
	}

	// jdbc规范: 返回true表示executeQuery，false表示executeUpdate
	@Override
	public boolean execute() throws SQLException {
		checkClosed();
		clearLastResultSet();
		ExecuteCommand command = new ExecuteCommand(ExecuteMethod.execute, null, false);
		ResultSetHandler handler = connection.executeSql(statementCreateCommand, command, parameterSettings, this);
		return handler.isResultType();
	}

	@Override
	public void setShort(int parameterIndex, short x) throws SQLException {
		parameterSettings.put(parameterIndex, new ParameterCommand(ParameterMethod.setShort, new Object[]{parameterIndex, x}));

	}

	// 数据库从0开始，jdbc规范从1开始，jdbc到数据库要减一
	@Override
	public void setInt(int parameterIndex, int x) throws SQLException {
		// 可分区
		parameterSettings.put(parameterIndex, new ParameterCommand(ParameterMethod.setInt, new Object[] { parameterIndex, x}, x));
	}

	@Override
	public void setLong(int parameterIndex, long x) throws SQLException {
		// 可分区
		parameterSettings.put(parameterIndex, new ParameterCommand(ParameterMethod.setLong, new Object[] { parameterIndex, x}, x));

	}

	@Override
	public void setBoolean(int parameterIndex, boolean x) throws SQLException {
		parameterSettings.put(parameterIndex, new ParameterCommand(ParameterMethod.setBoolean, new Object[] { parameterIndex, x }));
	}

	@Override
	public void setString(int parameterIndex, String x) throws SQLException {
		// 可分区
		parameterSettings.put(parameterIndex, new ParameterCommand(ParameterMethod.setString, new Object[] { parameterIndex, x}, x));
	}

	@Override
	public void setFloat(int parameterIndex, float x) throws SQLException {
		parameterSettings.put(parameterIndex, new ParameterCommand(ParameterMethod.setFloat, new Object[] { parameterIndex, x }));

	}

	@Override
	public void setDouble(int parameterIndex, double x) throws SQLException {
		parameterSettings.put(parameterIndex, new ParameterCommand(ParameterMethod.setDouble, new Object[] { parameterIndex, x }));

	}

	@Override
	public void setBytes(int parameterIndex, byte[] x) throws SQLException {
		parameterSettings.put(parameterIndex, new ParameterCommand(ParameterMethod.setBytes, new Object[] { parameterIndex, x }));

	}

	// 这里ustore底层将date按long存储
	@Override
	public void setDate(int parameterIndex, Date x) throws SQLException {
		// 可分区
		parameterSettings.put(parameterIndex, new ParameterCommand(ParameterMethod.setDate1, new Object[] { parameterIndex, x}, x));
	}

	@Override
	public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
		parameterSettings.put(parameterIndex , new ParameterCommand(ParameterMethod.setDate2, new Object[] { parameterIndex, x, cal }));
	}

	@Override
	public void clearParameters() throws SQLException {
		this.parameterSettings.clear();
	}

	@Override
	public void setNull(int parameterIndex, int sqlType) throws SQLException {
		parameterSettings.put(parameterIndex, new ParameterCommand(ParameterMethod.setNull1, new Object[]{parameterIndex, sqlType}));
	}

	@Override
	public void setByte(int parameterIndex, byte x) throws SQLException {
		parameterSettings.put(parameterIndex, new ParameterCommand(ParameterMethod.setByte, new Object[] { parameterIndex, x }));
	}

	@Override
	public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
		parameterSettings.put(parameterIndex, new ParameterCommand(ParameterMethod.setBigDecimal, new Object[] { parameterIndex, x }));
	}

	@Override
	public void setTime(int parameterIndex, Time x) throws SQLException {
		parameterSettings.put(parameterIndex, new ParameterCommand(ParameterMethod.setTime1, new Object[] { parameterIndex, x }));
	}

	@Override
	public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
		parameterSettings.put(parameterIndex, new ParameterCommand(ParameterMethod.setTimestamp1, new Object[] { parameterIndex, x }));
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
		parameterSettings.put(parameterIndex , new ParameterCommand(ParameterMethod.setAsciiStream, new Object[] { parameterIndex, x, length }));
	}

	@Override
	public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
		parameterSettings.put(parameterIndex , new ParameterCommand(ParameterMethod.setUnicodeStream, new Object[] { parameterIndex, x, length }));
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
		parameterSettings.put(parameterIndex , new ParameterCommand(ParameterMethod.setBinaryStream, new Object[] { parameterIndex, x, length }));
	}

	@Override
	public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
		parameterSettings.put(parameterIndex, new ParameterCommand(ParameterMethod.setObject3, new Object[] { parameterIndex, x, targetSqlType }));
	}

	@Override
	public void setObject(int parameterIndex, Object x) throws SQLException {
		parameterSettings.put(parameterIndex, new ParameterCommand(ParameterMethod.setObject1, new Object[] { parameterIndex, x }));
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
		parameterSettings.put(parameterIndex , new ParameterCommand(ParameterMethod.setCharacterStream, new Object[] { parameterIndex, reader, length }));
	}

	@Override
	public void setRef(int parameterIndex, Ref x) throws SQLException {
		parameterSettings.put(parameterIndex , new ParameterCommand(ParameterMethod.setRef, new Object[] { parameterIndex, x }));
	}

	@Override
	public void setBlob(int parameterIndex, Blob x) throws SQLException {
		parameterSettings.put(parameterIndex, new ParameterCommand(ParameterMethod.setBlob, new Object[] { parameterIndex, x }));
	}

	@Override
	public void setClob(int parameterIndex, Clob x) throws SQLException {
		parameterSettings.put(parameterIndex, new ParameterCommand(ParameterMethod.setClob, new Object[] { parameterIndex, x }));
	}

	@Override
	public void setArray(int parameterIndex, Array x) throws SQLException {
		parameterSettings.put(parameterIndex , new ParameterCommand(ParameterMethod.setArray, new Object[] { parameterIndex, x }));
	}

	@Override
	public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
		if (null == cal) {
			parameterSettings.put(parameterIndex, new ParameterCommand(ParameterMethod.setTime1, new Object[] { parameterIndex, x }));
		} else {
			parameterSettings.put(parameterIndex, new ParameterCommand(ParameterMethod.setTime2, new Object[] { parameterIndex, x }));
		}
	}

	@Override
	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
		parameterSettings.put(parameterIndex, new ParameterCommand(ParameterMethod.setTimestamp2, new Object[] { parameterIndex, x }));

	}

	@Override
	public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
		parameterSettings.put(parameterIndex, new ParameterCommand(ParameterMethod.setNull2, new Object[] { parameterIndex, sqlType, typeName }));

	}

	@Override
	public void setURL(int parameterIndex, URL x) throws SQLException {
		parameterSettings.put(parameterIndex , new ParameterCommand(ParameterMethod.setURL, new Object[] { parameterIndex, x }));
	}

	@Override
	public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
		parameterSettings.put(parameterIndex , new ParameterCommand(ParameterMethod.setObject3, new Object[] { parameterIndex, x, targetSqlType, scaleOrLength }));
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return this.getClass().isAssignableFrom(iface);
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
    public void addBatch() throws SQLException {
        throw new UnsupportedOperationException();
    }

    public String getSql() {
        return sql;
    }

}
