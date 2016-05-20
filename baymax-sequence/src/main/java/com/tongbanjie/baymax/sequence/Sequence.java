package com.tongbanjie.baymax.sequence;

import com.tongbanjie.baymax.sequence.exception.SequenceException;

public interface Sequence {
	
	Long next() throws SequenceException;

}
