package com.tongbanjie.baymax.spring.bean;

import com.tongbanjie.baymax.router.strategy.PartitionFunction;
import com.tongbanjie.baymax.router.strategy.PartitionTableNodeMapping;

import java.util.List;

/**
 * Created by sidawei on 16/4/10.
 */
public class TableConfig {

    private String tableName;

    private String namePatten;

    private List<ColumnConfig> columns;

    private PartitionFunction function;

    private PartitionTableNodeMapping nodeMapping;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getNamePatten() {
        return namePatten;
    }

    public void setNamePatten(String namePatten) {
        this.namePatten = namePatten;
    }

    public PartitionFunction getFunction() {
        return function;
    }

    public void setFunction(PartitionFunction function) {
        this.function = function;
    }

    public PartitionTableNodeMapping getNodeMapping() {
        return nodeMapping;
    }

    public void setNodeMapping(PartitionTableNodeMapping nodeMapping) {
        this.nodeMapping = nodeMapping;
    }

    public List<ColumnConfig> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnConfig> columns) {
        this.columns = columns;
    }
}
