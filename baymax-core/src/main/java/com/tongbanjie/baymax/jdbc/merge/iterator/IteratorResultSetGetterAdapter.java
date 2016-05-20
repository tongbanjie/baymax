package com.tongbanjie.baymax.jdbc.merge.iterator;

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

public abstract class IteratorResultSetGetterAdapter extends TResultSetLimit {

    public IteratorResultSetGetterAdapter(List<ResultSet> listResultSet, TStatement statement, ExecutePlan plan) {
        super(listResultSet, statement, plan);
    }

    /*------------------------------------------------------------------------*/

	@Override
	public String getString(int columnIndex) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getString(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getString(columnIndex);
		}
	}

	@Override
	public boolean getBoolean(int columnIndex) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getBoolean(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getBoolean(columnIndex);
		}
	}

	@Override
	public byte getByte(int columnIndex) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getByte(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getByte(columnIndex);
		}
	}

	@Override
	public short getShort(int columnIndex) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getShort(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getShort(columnIndex);
		}
	}

	@Override
	public int getInt(int columnIndex) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getInt(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getInt(columnIndex);
		}
	}

	@Override
	public long getLong(int columnIndex) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getLong(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getLong(columnIndex);
		}
	}

	@Override
	public float getFloat(int columnIndex) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getFloat(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getFloat(columnIndex);
		}
	}

	@Override
	public double getDouble(int columnIndex) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getDouble(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getDouble(columnIndex);
		}
	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getBigDecimal(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getBigDecimal(columnIndex);
		}
	}

	@Override
	public byte[] getBytes(int columnIndex) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getBytes(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getBytes(columnIndex);
		}
	}

	@Override
	public Date getDate(int columnIndex) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getDate(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getDate(columnIndex);
		}
	}

	@Override
	public Time getTime(int columnIndex) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getTime(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getTime(columnIndex);
		}
	}

	@Override
	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getTimestamp(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getTimestamp(columnIndex);
		}
	}

	@Override
	public InputStream getAsciiStream(int columnIndex) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getAsciiStream(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getAsciiStream(columnIndex);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getUnicodeStream(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getUnicodeStream(columnIndex);
		}
	}

	@Override
	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getBinaryStream(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getBinaryStream(columnIndex);
		}
	}

	@Override
	public String getString(String columnLabel) throws SQLException {
		return currentResultSet.getString(columnLabel);
	}

	@Override
	public boolean getBoolean(String columnLabel) throws SQLException {
		return currentResultSet.getBoolean(columnLabel);
	}

	@Override
	public byte getByte(String columnLabel) throws SQLException {
		return currentResultSet.getByte(columnLabel);
	}

	@Override
	public short getShort(String columnLabel) throws SQLException {
		return currentResultSet.getShort(columnLabel);
	}

	@Override
	public int getInt(String columnLabel) throws SQLException {
		return currentResultSet.getInt(columnLabel);
	}

	@Override
	public long getLong(String columnLabel) throws SQLException {
		return currentResultSet.getLong(columnLabel);
	}

	@Override
	public float getFloat(String columnLabel) throws SQLException {
		return currentResultSet.getFloat(columnLabel);
	}

	@Override
	public double getDouble(String columnLabel) throws SQLException {
		return currentResultSet.getDouble(columnLabel);
	}

	@SuppressWarnings("deprecation")
	@Override
	public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
		return currentResultSet.getBigDecimal(columnLabel, scale);
	}

	@Override
	public byte[] getBytes(String columnLabel) throws SQLException {
		return currentResultSet.getBytes(columnLabel);
	}

	@Override
	public Date getDate(String columnLabel) throws SQLException {
		return currentResultSet.getDate(columnLabel);
	}

	@Override
	public Time getTime(String columnLabel) throws SQLException {
		return currentResultSet.getTime(columnLabel);
	}

	@Override
	public Timestamp getTimestamp(String columnLabel) throws SQLException {
		return currentResultSet.getTimestamp(columnLabel);
	}

	@Override
	public InputStream getAsciiStream(String columnLabel) throws SQLException {
		return currentResultSet.getAsciiStream(columnLabel);
	}

	@SuppressWarnings("deprecation")
	@Override
	public InputStream getUnicodeStream(String columnLabel) throws SQLException {
		return currentResultSet.getUnicodeStream(columnLabel);
	}

	@Override
	public InputStream getBinaryStream(String columnLabel) throws SQLException {
		return currentResultSet.getBinaryStream(columnLabel);
	}

	@Override
	public Object getObject(int columnIndex) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getObject(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getObject(columnIndex);
		}
	}

	@Override
	public Object getObject(String columnLabel) throws SQLException {
		return currentResultSet.getObject(columnLabel);
	}

	@Override
	public Reader getCharacterStream(int columnIndex) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getCharacterStream(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getCharacterStream(columnIndex);
		}
	}

	@Override
	public Reader getCharacterStream(String columnLabel) throws SQLException {
		return currentResultSet.getCharacterStream(columnLabel);
	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getBigDecimal(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getBigDecimal(columnIndex);
		}
	}

	@Override
	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
		return currentResultSet.getBigDecimal(columnLabel);
	}

	@Override
	public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getBigDecimal(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getBigDecimal(columnIndex);
		}
	}

	@Override
	public Ref getRef(int columnIndex) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getRef(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getRef(columnIndex);
		}
	}

	@Override
	public Blob getBlob(int columnIndex) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getBlob(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getBlob(columnIndex);
		}
	}

	@Override
	public Clob getClob(int columnIndex) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getClob(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getClob(columnIndex);
		}
	}

	@Override
	public Array getArray(int columnIndex) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getArray(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getArray(columnIndex);
		}
	}

	@Override
	public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
		return currentResultSet.getObject(columnLabel, map);
	}

	@Override
	public Ref getRef(String columnLabel) throws SQLException {
		return currentResultSet.getRef(columnLabel);
	}

	@Override
	public Blob getBlob(String columnLabel) throws SQLException {
		return currentResultSet.getBlob(columnLabel);
	}

	@Override
	public Clob getClob(String columnLabel) throws SQLException {
		return currentResultSet.getClob(columnLabel);
	}

	@Override
	public Array getArray(String columnLabel) throws SQLException {
		return currentResultSet.getArray(columnLabel);
	}

	@Override
	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getDate(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getDate(columnIndex);
		}
	}

	@Override
	public Date getDate(String columnLabel, Calendar cal) throws SQLException {
		return currentResultSet.getDate(columnLabel, cal);
	}

	@Override
	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getTime(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getTime(columnIndex);
		}
	}

	@Override
	public Time getTime(String columnLabel, Calendar cal) throws SQLException {
		return currentResultSet.getTime(columnLabel, cal);
	}

	@Override
	public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getTimestamp(escapeIndexColumn(columnIndex), cal);
		} else {
			return currentResultSet.getTimestamp(columnIndex, cal);
		}
	}

	@Override
	public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
		return currentResultSet.getTimestamp(columnLabel, cal);
	}

	@Override
	public URL getURL(int columnIndex) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getURL(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getURL(columnIndex);
		}
	}

	@Override
	public URL getURL(String columnLabel) throws SQLException {
		return currentResultSet.getURL(columnLabel);
	}

	@Override
	public NClob getNClob(int columnIndex) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getNClob(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getNClob(columnIndex);
		}
	}

	@Override
	public NClob getNClob(String columnLabel) throws SQLException {
		return currentResultSet.getNClob(columnLabel);
	}

	@Override
	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getSQLXML(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getSQLXML(columnIndex);
		}
	}

	@Override
	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		return currentResultSet.getSQLXML(columnLabel);
	}

	@Override
	public String getNString(int columnIndex) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getNString(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getNString(columnIndex);
		}
	}

	@Override
	public String getNString(String columnLabel) throws SQLException {
		return currentResultSet.getNString(columnLabel);
	}

	@Override
	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		if (needEscape()) {
			return currentResultSet.getNCharacterStream(escapeIndexColumn(columnIndex));
		} else {
			return currentResultSet.getNCharacterStream(columnIndex);
		}
	}

	@Override
	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		return currentResultSet.getNCharacterStream(columnLabel);
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
        if (needEscape()) {
            return currentResultSet.getObject(escapeIndexColumn(columnIndex), type);
        } else {
            return currentResultSet.getObject(columnIndex, type);
        }
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
        return currentResultSet.getObject(columnLabel, type);
    }

}
