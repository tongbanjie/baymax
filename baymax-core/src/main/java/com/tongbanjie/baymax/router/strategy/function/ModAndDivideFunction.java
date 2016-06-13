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
	
	 private int mod;

	 private int divide = 1;
	 
	@Override
	public Integer execute(String columnValue, Map<String, Object> extension) {
		 return ((Long)(Long.valueOf(columnValue) % mod / divide)).intValue();
	}

	public int getMod() {
		return mod;
	}

	public void setMod(int mod) {
		this.mod = mod;
	}

	public int getDivide() {
		return divide;
	}

	public void setDivide(int divide) {
		this.divide = divide;
	}
	
	

}
