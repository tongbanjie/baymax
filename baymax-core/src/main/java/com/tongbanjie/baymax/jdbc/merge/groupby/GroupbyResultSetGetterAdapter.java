package com.tongbanjie.baymax.jdbc.merge.groupby;

import com.tongbanjie.baymax.jdbc.TResultSetLimit;
import com.tongbanjie.baymax.jdbc.TStatement;
import com.tongbanjie.baymax.router.model.ExecutePlan;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public abstract class GroupbyResultSetGetterAdapter extends TResultSetLimit {

    protected GroupbyValue currentValue;

    public GroupbyResultSetGetterAdapter(List<ResultSet> listResultSet, TStatement statement, ExecutePlan plan) {
        super(listResultSet, statement, plan);
    }


    /*------------------------------------------------------------------------*/

	@Override
	public String getString(int columnIndex) throws SQLException {
		return currentValue.getValue(columnIndex, String.class);
	}

	@Override
	public boolean getBoolean(int columnIndex) throws SQLException {
        return currentValue.getValue(columnIndex, boolean.class);
	}

	@Override
	public byte getByte(int columnIndex) throws SQLException {
        return currentValue.getValue(columnIndex, byte.class);
	}

	@Override
	public short getShort(int columnIndex) throws SQLException {
        return currentValue.getValue(columnIndex, short.class);
	}

	@Override
	public int getInt(int columnIndex) throws SQLException {
        return currentValue.getValue(columnIndex, int.class);
	}

	@Override
	public long getLong(int columnIndex) throws SQLException {
        return currentValue.getValue(columnIndex, long.class);
	}

	@Override
	public float getFloat(int columnIndex) throws SQLException {
        return currentValue.getValue(columnIndex, float.class);
	}

	@Override
	public double getDouble(int columnIndex) throws SQLException {
        return currentValue.getValue(columnIndex, double.class);
	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
        return currentValue.getValue(columnIndex, BigDecimal.class);
	}

	@Override
	public byte[] getBytes(int columnIndex) throws SQLException {
        return currentValue.getValue(columnIndex, byte[].class);
	}

	@Override
	public Date getDate(int columnIndex) throws SQLException {
        return currentValue.getValue(columnIndex, Date.class);
	}

	@Override
	public Time getTime(int columnIndex) throws SQLException {
        return currentValue.getValue(columnIndex, Time.class);
	}

	@Override
	public Timestamp getTimestamp(int columnIndex) throws SQLException {
        return currentValue.getValue(columnIndex, Timestamp.class);
	}

	@Override
	public InputStream getAsciiStream(int columnIndex) throws SQLException {
        return currentValue.getValue(columnIndex, InputStream.class);
	}

	@SuppressWarnings("deprecation")
	@Override
	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
        return currentValue.getValue(columnIndex, InputStream.class);
	}

	@Override
	public InputStream getBinaryStream(int columnIndex) throws SQLException {
        return currentValue.getValue(columnIndex, InputStream.class);
	}

	@Override
	public String getString(String columnLabel) throws SQLException {
        return currentValue.getValue(columnLabel, String.class);
	}

	@Override
	public boolean getBoolean(String columnLabel) throws SQLException {
        return currentValue.getValue(columnLabel, boolean.class);
	}

	@Override
	public byte getByte(String columnLabel) throws SQLException {
        return currentValue.getValue(columnLabel, byte.class);
	}

	@Override
	public short getShort(String columnLabel) throws SQLException {
        return currentValue.getValue(columnLabel, short.class);
	}

	@Override
	public int getInt(String columnLabel) throws SQLException {
        return currentValue.getValue(columnLabel, int.class);
	}

	@Override
	public long getLong(String columnLabel) throws SQLException {
        return currentValue.getValue(columnLabel, long.class);
	}

	@Override
	public float getFloat(String columnLabel) throws SQLException {
        return currentValue.getValue(columnLabel, float.class);
	}

	@Override
	public double getDouble(String columnLabel) throws SQLException {
        return currentValue.getValue(columnLabel, double.class);
	}

	@SuppressWarnings("deprecation")
	@Override
	public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
        return currentValue.getValue(columnLabel, BigDecimal.class);
	}

	@Override
	public byte[] getBytes(String columnLabel) throws SQLException {
        return currentValue.getValue(columnLabel, byte[].class);
	}

	@Override
	public Date getDate(String columnLabel) throws SQLException {
        return currentValue.getValue(columnLabel, Date.class);
	}

	@Override
	public Time getTime(String columnLabel) throws SQLException {
        return currentValue.getValue(columnLabel, Time.class);
	}

	@Override
	public Timestamp getTimestamp(String columnLabel) throws SQLException {
        return currentValue.getValue(columnLabel, Timestamp.class);
	}

	@Override
	public InputStream getAsciiStream(String columnLabel) throws SQLException {
        return currentValue.getValue(columnLabel, InputStream.class);
	}

	@SuppressWarnings("deprecation")
	@Override
	public InputStream getUnicodeStream(String columnLabel) throws SQLException {
        return currentValue.getValue(columnLabel, InputStream.class);
	}

	@Override
	public InputStream getBinaryStream(String columnLabel) throws SQLException {
        return currentValue.getValue(columnLabel, InputStream.class);
	}

	@Override
	public Object getObject(int columnIndex) throws SQLException {
        return currentValue.getValue(columnIndex, Object.class);
	}

	@Override
	public Object getObject(String columnLabel) throws SQLException {
        return currentValue.getValue(columnLabel, Object.class);
	}

	@Override
	public Reader getCharacterStream(int columnIndex) throws SQLException {
        return currentValue.getValue(columnIndex, Reader.class);
	}

	@Override
	public Reader getCharacterStream(String columnLabel) throws SQLException {
        return currentValue.getValue(columnLabel, Reader.class);
	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        return currentValue.getValue(columnIndex, BigDecimal.class);
	}

	@Override
	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
        return currentValue.getValue(columnLabel, BigDecimal.class);
	}

	@Override
	public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
        return currentValue.getValue(columnIndex, Object.class);
	}

	@Override
	public Ref getRef(int columnIndex) throws SQLException {
        return currentValue.getValue(columnIndex, Ref.class);
	}

	@Override
	public Blob getBlob(int columnIndex) throws SQLException {
        return currentValue.getValue(columnIndex, Blob.class);
	}

	@Override
	public Clob getClob(int columnIndex) throws SQLException {
        return currentValue.getValue(columnIndex, Clob.class);
	}

	@Override
	public Array getArray(int columnIndex) throws SQLException {
        return currentValue.getValue(columnIndex, Array.class);
	}

	@Override
	public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
        //return currentValue.getValue(columnLabel, InputStream.class);
        return null;
        // TODO
	}

	@Override
	public Ref getRef(String columnLabel) throws SQLException {
        return currentValue.getValue(columnLabel, Ref.class);
	}

	@Override
	public Blob getBlob(String columnLabel) throws SQLException {
        return currentValue.getValue(columnLabel, Blob.class);
	}

	@Override
	public Clob getClob(String columnLabel) throws SQLException {
        return currentValue.getValue(columnLabel, Clob.class);
	}

	@Override
	public Array getArray(String columnLabel) throws SQLException {
        return currentValue.getValue(columnLabel, Array.class);
	}

	@Override
	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
        //return currentValue.getValue(columnLabel, InputStream.class);
        return null;
        // TODO
	}

	@Override
	public Date getDate(String columnLabel, Calendar cal) throws SQLException {
		//return currentResultSet.getDate(columnLabel, cal);
        return null;
        // TODO
	}

	@Override
	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
        //return currentValue.getValue(columnLabel, InputStream.class);
        return null;
        // TODO
	}

	@Override
	public Time getTime(String columnLabel, Calendar cal) throws SQLException {
		//return currentResultSet.getTime(columnLabel, cal);
        return null;
        // TODO
	}

	@Override
	public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
        //return currentValue.getValue(columnIndex, InputStream.class);
        return null;
        // TODO
	}

	@Override
	public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
		//return currentResultSet.getTimestamp(columnLabel, cal);
        return null;
        // TODO
	}

	@Override
	public URL getURL(int columnIndex) throws SQLException {
        return currentValue.getValue(columnIndex, URL.class);
	}

	@Override
	public URL getURL(String columnLabel) throws SQLException {
        return currentValue.getValue(columnLabel, URL.class);
	}

	@Override
	public NClob getNClob(int columnIndex) throws SQLException {
        return currentValue.getValue(columnIndex, NClob.class);
	}

	@Override
	public NClob getNClob(String columnLabel) throws SQLException {
		return currentResultSet.getNClob(columnLabel);
	}

	@Override
	public SQLXML getSQLXML(int columnIndex) throws SQLException {
        return currentValue.getValue(columnIndex, SQLXML.class);
	}

	@Override
	public SQLXML getSQLXML(String columnLabel) throws SQLException {
        return currentValue.getValue(columnLabel, SQLXML.class);
	}

	@Override
	public String getNString(int columnIndex) throws SQLException {
        return currentValue.getValue(columnIndex, String.class);
	}

	@Override
	public String getNString(String columnLabel) throws SQLException {
        return currentValue.getValue(columnLabel, String.class);
	}

	@Override
	public Reader getNCharacterStream(int columnIndex) throws SQLException {
        return currentValue.getValue(columnIndex, Reader.class);
	}

	@Override
	public Reader getNCharacterStream(String columnLabel) throws SQLException {
        return currentValue.getValue(columnLabel, Reader.class);
	}



    @Override
    public boolean needEscape() {
        return false;
    }

    /*------------------------------------以下为1.7方法-----------------------------------*/

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
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.7
     */
    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        return currentValue.getValue(columnIndex, type);
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
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not support
     *                                         this method
     * @since 1.7
     */
    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        return currentValue.getValue(columnLabel, type);
    }

}
