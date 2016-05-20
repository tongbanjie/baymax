package com.tongbanjie.baymax.jdbc.model;

import com.tongbanjie.baymax.jdbc.TResultSet;

public class ResultSetHandler {
	
	private TResultSet resultSet;
	
	private boolean resultType;	//true表示返回的是ResultSet, false表示返回的是影响的行数;JDBC标准.
	
	private int updateCount = -1;
	
	public ResultSetHandler(){
		
	}
	
	public ResultSetHandler(TResultSet resultSet, boolean resultType, int updateCount){
		this.resultSet = resultSet;
		this.resultType = resultType;
		this.updateCount = updateCount;
	}
	
	public TResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(TResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public boolean isResultType() {
		return resultType;
	}

	public void setResultType(boolean resultType) {
		this.resultType = resultType;
	}

	public int getUpdateCount() {
		return updateCount;
	}

	public void addUpdateCount(int updateCount) {
		if(this.updateCount == -1){
			this.updateCount = updateCount;
		}else{
			this.updateCount += updateCount;
		}
	}
}
