package com.tongbanjie.baymax.router.strategy;

import java.util.List;

/**
 * Created by sidawei on 16/4/2.
 *
 * 一个表的分区规则 包含：
 * 分区列,分区函数
 */
public class PartitionTableRule {

    private List<PartitionColumn> columns;    // 分区键

    private PartitionFunction function;       // 分区函数

    public PartitionTableRule() {
    }

    public PartitionTableRule(List<PartitionColumn> columns, PartitionFunction function) {
        this.columns = columns;
        this.function = function;
    }

    public List<PartitionColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<PartitionColumn> columns) {
        this.columns = columns;
    }

    public PartitionFunction getFunction() {
        return function;
    }

    public void setFunction(PartitionFunction function) {
        this.function = function;
    }

    @Override
    public String toString() {
        return String.format("[function:%s] [columns:%s]", function, columns);
    }
}
