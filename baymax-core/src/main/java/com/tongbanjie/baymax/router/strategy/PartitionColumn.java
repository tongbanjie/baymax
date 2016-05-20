package com.tongbanjie.baymax.router.strategy;

import com.tongbanjie.baymax.router.ColumnProcess;

/**
 * Created by sidawei on 16/4/2.
 */
public class PartitionColumn {

    private String name;

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
