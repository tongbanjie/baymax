package com.tongbanjie.baymax.router.strategy.function;

import com.tongbanjie.baymax.router.strategy.PartitionFunction;

import java.util.Map;

/**
 * Created by sidawei on 16/4/2.
 */
public class ModFunction implements PartitionFunction {

    private int mode;

    private int add = 0;

    @Override
    public Integer execute(String columnValue, Map<String, Object> extention) {
        return ((Long)(Long.valueOf(columnValue) % mode + add)).intValue();
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getAdd() {
        return add;
    }

    public void setAdd(int add) {
        this.add = add;
    }
}
