package com.tongbanjie.baymax.router.strategy;

import com.tongbanjie.baymax.router.ColumnProcess;

/**
 * Created by sidawei on 16/4/2.
 *
 * 一个分区列的表述
 */
public class PartitionColumn {

    /**
     * 分区的字段名
     */
    private String name;

    /**
     * 当前字段的分区函数
     */
    private ColumnProcess process;

    public PartitionColumn(){

    }

    public PartitionColumn(String name, ColumnProcess process){
        this.name = name;
        this.process = process;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ColumnProcess getProcess() {
        return process;
    }

    public void setProcess(ColumnProcess process) {
        this.process = process;
    }

    @Override
    public String toString() {
        return String.format("[column:%s] [process:%s]", name, process);
    }
}
