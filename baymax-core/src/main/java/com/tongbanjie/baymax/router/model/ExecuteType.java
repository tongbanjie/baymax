package com.tongbanjie.baymax.router.model;

public enum ExecuteType {
	NO(0, "not need rote"), PARTITION(1, "execute in some partitions"), ALL(2, "execute whith all partitions");
	
	private int key;
	
	private String value;
	
	ExecuteType(int key, String value){
		this.key = key;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "key:" + this.key + " value:" + this.value;
	}

	public int getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

}
