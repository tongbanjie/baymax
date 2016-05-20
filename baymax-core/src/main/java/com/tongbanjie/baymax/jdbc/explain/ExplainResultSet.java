package com.tongbanjie.baymax.jdbc.explain;

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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import com.tongbanjie.baymax.jdbc.adapter.UnsupportedResultSetAdapter;
import com.tongbanjie.baymax.router.model.ExecutePlan;

public class ExplainResultSet extends UnsupportedResultSetAdapter {
	
	private ExecutePlan plan;
	
	private ExplainResultSetMetaDate explainResultSetMetaDate;
	
	private int offset = -1;
	
	
	public ExplainResultSet(ExecutePlan plan){
		explainResultSetMetaDate = new ExplainResultSetMetaDate();
		this.plan = plan;
	}

	@Override
	public boolean next() throws SQLException {
		offset ++;
		return plan.getSqlList().size() > offset;
	}

	@Override
	public void close() throws SQLException {
	}

	@Override
	public boolean wasNull() throws SQLException {
		return false;
	}

	@Override
	public String getString(int columnIndex) throws SQLException {
		String targetSql = plan.getSqlList().get(offset).getTargetSql();
		return targetSql.replaceAll("\n", " ");
	}

	@Override
	public boolean getBoolean(int columnIndex) throws SQLException {
		return false;
	}

	@Override
	public byte getByte(int columnIndex) throws SQLException {
		return 0;
	}

	@Override
	public short getShort(int columnIndex) throws SQLException {
		return 0;
	}

	@Override
	public int getInt(int columnIndex) throws SQLException {
		return 0;
	}

	@Override
	public long getLong(int columnIndex) throws SQLException {
		return 0;
	}

	@Override
	public float getFloat(int columnIndex) throws SQLException {
		return 0;
	}

	@Override
	public double getDouble(int columnIndex) throws SQLException {
		return 0;
	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex, int scale)
			throws SQLException {
		return null;
	}

	@Override
	public byte[] getBytes(int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public Date getDate(int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public Time getTime(int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public InputStream getAsciiStream(int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public String getString(String columnLabel) throws SQLException {
		String targetSql = plan.getSqlList().get(offset).getTargetSql();
		return targetSql.replaceAll("\n", " ");
	}

	@Override
	public boolean getBoolean(String columnLabel) throws SQLException {
		return false;
	}

	@Override
	public byte getByte(String columnLabel) throws SQLException {
		return 0;
	}

	@Override
	public short getShort(String columnLabel) throws SQLException {
		return 0;
	}

	@Override
	public int getInt(String columnLabel) throws SQLException {
		return 0;
	}

	@Override
	public long getLong(String columnLabel) throws SQLException {
		return 0;
	}

	@Override
	public float getFloat(String columnLabel) throws SQLException {
		return 0;
	}

	@Override
	public double getDouble(String columnLabel) throws SQLException {
		return 0;
	}

	@Override
	public BigDecimal getBigDecimal(String columnLabel, int scale)
			throws SQLException {
		return null;
	}

	@Override
	public byte[] getBytes(String columnLabel) throws SQLException {
		return null;
	}

	@Override
	public Date getDate(String columnLabel) throws SQLException {
		return null;
	}

	@Override
	public Time getTime(String columnLabel) throws SQLException {
		return null;
	}

	@Override
	public Timestamp getTimestamp(String columnLabel) throws SQLException {
		return null;
	}

	@Override
	public InputStream getAsciiStream(String columnLabel) throws SQLException {
		return null;
	}

	@Override
	public InputStream getUnicodeStream(String columnLabel) throws SQLException {
		return null;
	}

	@Override
	public InputStream getBinaryStream(String columnLabel) throws SQLException {
		return null;
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		return null;
	}

	@Override
	public void clearWarnings() throws SQLException {

	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		return explainResultSetMetaDate ;
	}

	@Override
	public Object getObject(int columnIndex) throws SQLException {
		String targetSql = plan.getSqlList().get(offset).getTargetSql();
		return targetSql.replaceAll("\n", " ");
	}

	@Override
	public Object getObject(String columnLabel) throws SQLException {
		String targetSql = plan.getSqlList().get(offset).getTargetSql();
		return targetSql.replaceAll("\n", " ");
	}

	@Override
	public int findColumn(String columnLabel) throws SQLException {
		return 0;
	}

	@Override
	public Reader getCharacterStream(int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public Reader getCharacterStream(String columnLabel) throws SQLException {
		return null;
	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
		return null;
	}

	@Override
	public void setFetchDirection(int direction) throws SQLException {

	}

	@Override
	public int getFetchDirection() throws SQLException {
		return 0;
	}

	@Override
	public void setFetchSize(int rows) throws SQLException {

	}

	@Override
	public int getFetchSize() throws SQLException {
		return 0;
	}

	@Override
	public int getType() throws SQLException {
		return 0;
	}

	@Override
	public int getConcurrency() throws SQLException {
		return 0;
	}

	@Override
	public Statement getStatement() throws SQLException {
		return null;
	}

	@Override
	public Object getObject(int columnIndex, Map<String, Class<?>> map)
			throws SQLException {
		return null;
	}

	@Override
	public Ref getRef(int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public Blob getBlob(int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public Clob getClob(int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public Array getArray(int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public Object getObject(String columnLabel, Map<String, Class<?>> map)
			throws SQLException {
		String targetSql = plan.getSqlList().get(offset).getTargetSql();
		return targetSql.replaceAll("\n", " ");
	}

	@Override
	public Ref getRef(String columnLabel) throws SQLException {
		return null;
	}

	@Override
	public Blob getBlob(String columnLabel) throws SQLException {
		return null;
	}

	@Override
	public Clob getClob(String columnLabel) throws SQLException {
		return null;
	}

	@Override
	public Array getArray(String columnLabel) throws SQLException {
		return null;
	}

	@Override
	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		return null;
	}

	@Override
	public Date getDate(String columnLabel, Calendar cal) throws SQLException {
		return null;
	}

	@Override
	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		return null;
	}

	@Override
	public Time getTime(String columnLabel, Calendar cal) throws SQLException {
		return null;
	}

	@Override
	public Timestamp getTimestamp(int columnIndex, Calendar cal)
			throws SQLException {
		return null;
	}

	@Override
	public Timestamp getTimestamp(String columnLabel, Calendar cal)
			throws SQLException {
		return null;
	}

	@Override
	public URL getURL(int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public URL getURL(String columnLabel) throws SQLException {
		return null;
	}

	@Override
	public int getHoldability() throws SQLException {
		return 0;
	}

	@Override
	public boolean isClosed() throws SQLException {
		return false;
	}

	@Override
	public NClob getNClob(int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public NClob getNClob(String columnLabel) throws SQLException {
		return null;
	}

	@Override
	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		return null;
	}

	@Override
	public String getNString(int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public String getNString(String columnLabel) throws SQLException {
		return null;
	}

	@Override
	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		return null;
	}

    /**
     * <p>Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object and will convert from the
     * SQL type of the column to the requested Java data type, if the
     * conversion is supported. If the conversion is not
     * supported  or null is specified for the type, a
     * <code>SQLException</code> is thrown.
     * <p/>
     * At a minimum, an implementation must support the conversions defined in
     * Appendix B, Table B-3 and conversion of appropriate user defined SQL
     * types to a Java type which implements {@code SQLData}, or {@code Struct}.
     * Additional conversions may be supported and are vendor defined.
     *
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param type        Class representing the Java data type to convert the designated
     *                    column to.
     * @return an instance of {@code type} holding the column value
     * @throws SQLException                    if conversion is not supported, type is null or
     *                                         another error occurs. The getCause() method of the
     *                                         exception may provide a more detailed exception, for example, if
     *                                         a conversion error occurs
     * @throws  if the JDBC driver does not support
     *                                         this method
     * @since 1.7
     */
    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        return null;
    }

    /**
     * <p>Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object and will convert from the
     * SQL type of the column to the requested Java data type, if the
     * conversion is supported. If the conversion is not
     * supported  or null is specified for the type, a
     * <code>SQLException</code> is thrown.
     * <p/>
     * At a minimum, an implementation must support the conversions defined in
     * Appendix B, Table B-3 and conversion of appropriate user defined SQL
     * types to a Java type which implements {@code SQLData}, or {@code Struct}.
     * Additional conversions may be supported and are vendor defined.
     *
     * @param columnLabel the label for the column specified with the SQL AS clause.
     *                    If the SQL AS clause was not specified, then the label is the name
     *                    of the column
     * @param type        Class representing the Java data type to convert the designated
     *                    column to.
     * @return an instance of {@code type} holding the column value
     * @throws SQLException                    if conversion is not supported, type is null or
     *                                         another error occurs. The getCause() method of the
     *                                         exception may provide a more detailed exception, for example, if
     *                                         a conversion error occurs
     * @throws  if the JDBC driver does not support
     *                                         this method
     * @since 1.7
     */
    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        return null;
    }

    @Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

}
