package com.tongbanjie.baymax.sequence.impl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tongbanjie.baymax.sequence.SequenceDao;
import com.tongbanjie.baymax.sequence.SequenceRange;
import com.tongbanjie.baymax.sequence.exception.SequenceException;

/**
 * 线程不安全
 * 
 * @author dawei
 *
 */
public class DefaultSequenceDao implements SequenceDao {
	private final static Logger logger = LoggerFactory.getLogger(DefaultSequenceDao.class);

	public final static String DEFAULT_TABLE_NAME = "tbj_sequence"; 	// 表名
	public final static int DEFAULT_RANGE_SIZE = 500; 					// 每次取多少个Sequence
	public final static int DEFAULT_RANGE_SIZE_MIN = 1; 				// 最少取多少个
	public final static int DEFAULT_RANGE_SIZE_MAN = 10000; 			// 最大取多少个
	public static final int DEFAULT_RETRY_TIMES = 150; 					// 最大重试次数

	public final static String nameColumn = "name";
	public final static String valueColumn = "value";

	private int retryTimes = DEFAULT_RETRY_TIMES;
	private DataSource dataSource;
	
	public DefaultSequenceDao(DataSource dataSource){
		this.dataSource = dataSource;
	}

	@Override
	public SequenceRange nextRange(String name) throws SequenceException {
		return nextRange(name, DEFAULT_TABLE_NAME, DEFAULT_RANGE_SIZE);
	}

	@Override
	public SequenceRange nextRange(String name, String tableName, int rangeSize) throws SequenceException {
		if (name == null) {
			throw new IllegalArgumentException("序列名称不能为空");
		}
		checkRangeSize(rangeSize);
		long oldValue;
		long newValue;

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		for (int i = 0; i < retryTimes + 1; ++i) {
			try {
				conn = dataSource.getConnection();
				stmt = conn.prepareStatement("select value from " + tableName + " where name = ? ");
				stmt.setString(1, name);
				rs = stmt.executeQuery();
				rs.next();
				oldValue = rs.getLong(1);

				if (oldValue < 0) {
					StringBuilder message = new StringBuilder();
					message.append("Sequence的值不能小于0, value = ").append(oldValue);
					message.append(", 表名: ").append(tableName);

					throw new SequenceException(message.toString());
				}

				if (oldValue > Long.MAX_VALUE - 100000000L) {
					StringBuilder message = new StringBuilder();
					message.append("Sequence 溢出, value = ").append(oldValue);
					message.append(", 表名: ").append(tableName);

					throw new SequenceException(message.toString());
				}
				newValue = oldValue + rangeSize;
			} catch (SQLException e) {
				throw new SequenceException(e);
			} finally {
				closeResultSet(rs);
				rs = null;
				closeStatement(stmt);
				stmt = null;
				closeConnection(conn);
				conn = null;
			}

			try {
				conn = dataSource.getConnection();
				stmt = conn.prepareStatement("update " + tableName + " set value = ?, update_time = ? where name = ? and value = ?");
				stmt.setLong(1, newValue);
				stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
				stmt.setString(3, name);
				stmt.setLong(4, oldValue);
				int affectedRows = stmt.executeUpdate();
				if (affectedRows == 0) {
					// retry
					continue;
				}
				retryTimes = DEFAULT_RETRY_TIMES; // 重置重试计数器
				return new SequenceRange(oldValue + 1, newValue);
			} catch (SQLException e) {
				throw new SequenceException(e);
			} finally {
				closeStatement(stmt);
				stmt = null;
				closeConnection(conn);
				conn = null;
			}
		}
		throw new SequenceException("重试次数过多, retryTimes = " + retryTimes);
	}
	
    public void checkRangeSize(int size) {
        if (size < DEFAULT_RANGE_SIZE_MIN || size > DEFAULT_RANGE_SIZE_MAN) {
            StringBuilder message = new StringBuilder();
            message.append("Sequence溢出 [").append(size);
            message.append(",").append(DEFAULT_RANGE_SIZE_MAN).append("], step = ").append(size);

            throw new IllegalArgumentException(message.toString());
        }
    }

	private static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.debug("JDBC ResultSet 关闭异常", e);
			} catch (Throwable e) {
				logger.debug("JDBC ResultSet 关闭异常", e);
			}
		}
	}

	private static void closeStatement(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.debug("JDBC Statement 关闭异常", e);
			} catch (Throwable e) {
				logger.debug("JDBC Statement 关闭异常", e);
			}
		}
	}

	private static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.debug("JDBC Connection 关闭异常", e);
			} catch (Throwable e) {
				logger.debug("JDBC Connection 关闭异常", e);
			}
		}
	}
}
