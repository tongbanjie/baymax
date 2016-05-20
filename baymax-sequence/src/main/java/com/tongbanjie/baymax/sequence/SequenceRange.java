package com.tongbanjie.baymax.sequence;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 表示一个内存中的Sequence区间
 * @author dawei
 *
 */
public class SequenceRange {
	
	private final long min;
	private final long max;
	
	private AtomicLong value;
	
	private volatile boolean closed;
	
	/**
	 * 创建一个内存中的Sequence区间,范围[min, max]
	 * @param min
	 * @param max
	 */
	public SequenceRange(long min, long max){
		this.min = min;
		this.max = max;
		this.value = new AtomicLong(min);
	}
	
	public long getAndIncrement(){
		long currentValue = value.getAndIncrement();
		if(currentValue > max){
			closed = true;
			return -1;
		}
		return currentValue;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public long getMin() {
		return min;
	}

	public long getMax() {
		return max;
	}
	

}
