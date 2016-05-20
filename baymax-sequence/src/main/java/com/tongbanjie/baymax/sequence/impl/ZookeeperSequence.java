package com.tongbanjie.baymax.sequence.impl;

import com.tongbanjie.baymax.sequence.Sequence;
import com.tongbanjie.baymax.sequence.SequenceDao;
import com.tongbanjie.baymax.sequence.SequenceRange;
import com.tongbanjie.baymax.sequence.exception.SequenceException;
import com.tongbanjie.baymax.sequence.impl.dao.DefaultSequenceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO
 */
public class ZookeeperSequence implements Sequence {
	
	private final static Logger logger = LoggerFactory.getLogger(ZookeeperSequence.class);

	private String name;

	private final Lock lock = new ReentrantLock();

    private String Id;

    private AtomicInteger range = new AtomicInteger();

	@Override
	public Long next() throws SequenceException {
        return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
