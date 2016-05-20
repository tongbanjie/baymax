package com.tongbanjie.baymax.router.strategy.function;

import java.util.Map;

import com.tongbanjie.baymax.router.strategy.PartitionFunction;

/**
 * 
 * @author dongpeng
 * 取一个整数的中间一段
 * 例如 20160314  得到 1603 = 20160314 %10000000/100  
 */
public class ModAndDivideFunction implements PartitionFunction {
	
	 private int mode;

	 private int divide = 1;
	 
	@Override
	public Integer execute(String columnValue, Map<String, Object> extention) {
		 return ((Long)(Long.valueOf(columnValue) % mode / divide)).intValue();
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getDivide() {
		return divide;
	}

	public void setDivide(int divide) {
		this.divide = divide;
	}
	
	

}
