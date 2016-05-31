package com.tongbanjie.baymax.router.model;

/**
 * 执行计划的类型
 */
public enum ExecuteType {
	NO(0, "无需路由"), PARTITION(1, "分区执行"), ALL(2, "全表扫描");
	
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
