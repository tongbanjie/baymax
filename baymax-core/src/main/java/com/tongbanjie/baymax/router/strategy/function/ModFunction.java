package com.tongbanjie.baymax.router.strategy.function;

import com.tongbanjie.baymax.router.strategy.PartitionFunction;

import java.util.Map;

/**
 * Created by sidawei on 16/4/2.
 *
 * Mod的分区函数实现
 */
public class ModFunction implements PartitionFunction {

    private int mod;

    private int add = 0;

    @Override
    public Integer execute(String columnValue, Map<String, Object> extension) {
        return ((Long)(Long.valueOf(columnValue) % mod + add)).intValue();
    }

    public int getMod() {
        return mod;
    }

    public void setMod(int mod) {
        this.mod = mod;
    }

    public int getAdd() {
        return add;
    }

    public void setAdd(int add) {
        this.add = add;
    }
}
