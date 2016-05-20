package com.tongbanjie.baymax.sequence;

import com.tongbanjie.baymax.sequence.exception.SequenceException;

public interface SequenceDao {
	
	SequenceRange nextRange(String name) throws SequenceException;

	SequenceRange nextRange(String name, String tableName, int rangeSize) throws SequenceException;

}
