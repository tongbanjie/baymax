package com.tongbanjie.baymax.exception;

public class BayMaxException extends RuntimeException {

	private static final long serialVersionUID = -378533410763590157L;

	private TraceContext trace;
	
	public BayMaxException(String message) {
		super(message);
	}

	public BayMaxException(String message, Exception e) {
		super(message, e);
	}
	
	public BayMaxException(String message, Exception e, String sql){
		this(message, e);
		trace = new TraceContext(sql);
	}
	
	public BayMaxException(String message, Exception e, TraceContext trace){
		this(message, e);
		this.trace = trace;
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() + (trace !=null? trace.toString():"");
	}

}
