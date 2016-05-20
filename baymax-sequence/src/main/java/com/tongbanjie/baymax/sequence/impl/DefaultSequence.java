package com.tongbanjie.baymax.sequence.impl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;

import com.tongbanjie.baymax.sequence.impl.dao.DefaultSequenceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tongbanjie.baymax.sequence.Sequence;
import com.tongbanjie.baymax.sequence.SequenceDao;
import com.tongbanjie.baymax.sequence.SequenceRange;
import com.tongbanjie.baymax.sequence.exception.SequenceException;

public class DefaultSequence implements Sequence {
	
	private final static Logger logger = LoggerFactory.getLogger(DefaultSequence.class);

	private volatile SequenceRange range;
	private String name;
	private String tableName;
	private Integer rangeSize;
	private SequenceDao sequenceDao;
	private DataSource dataSource;

	private final Lock lock = new ReentrantLock();

	@Override
	public Long next() throws SequenceException {
		if (range == null) {
			lock.lock();
			try {
				if (range == null) {
					range = sequenceDao.nextRange(name);
				}
			} finally {
				lock.unlock();
			}
		}

		long value = range.getAndIncrement();
		if (value == -1) {
			lock.lock();
			try {
				for (;;) {
					if (range.isClosed()) {
						range = sequenceDao.nextRange(name);
					}

					value = range.getAndIncrement();
					if (value == -1) {
						logger.info("Sequence continue!!! " + value);
						continue;
					}

					break;
				}
			} finally {
				lock.unlock();
			}
		}

		if (value < 0) {
			throw new SequenceException("Sequence 溢出, value = " + value);
		}
		return value;
	}

	public SequenceRange getRange() {
		return range;
	}

	public SequenceDao getSequenceDao() {
		return sequenceDao;
	}

	public void setSequenceDao(SequenceDao sequenceDao) {
		this.sequenceDao = sequenceDao;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Integer getRangeSize() {
		return rangeSize;
	}

	public void setRangeSize(Integer rangeSize) {
		this.rangeSize = rangeSize;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		sequenceDao = new DefaultSequenceDao(dataSource);
	}

}
