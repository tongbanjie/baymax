package com.tongbanjie.baymax.test;

import com.tongbanjie.baymax.router.ColumnProcess;
import com.tongbanjie.baymax.router.strategy.*;
import com.tongbanjie.baymax.router.strategy.function.ELFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sidawei on 16/3/21.
 */
public class TableBuilder {
    List<PartitionTable>    tables          = new ArrayList<PartitionTable>();
    PartitionTable          concurrentTable = null;
    PartitionTableRule      rule            = null;
    List<PartitionColumn>   columns         = new ArrayList<PartitionColumn>();


    public TableBuilder appenTable(String tableName, String tableNamePatten, String function){
        // table
        concurrentTable = new PartitionTable();
        concurrentTable.setLogicTableName(tableName);
        concurrentTable.setNamePatten(tableNamePatten);
        //tables
        tables.add(concurrentTable);



        ELFunction func = new ELFunction();
        func.setExpression(function);

        rule = new PartitionTableRule();
        rule.setFunction(func);

        return this;
    }

    List<String> nodeMapping = new ArrayList<String>();
    public TableBuilder appendNodeMapping(String mapping){
        nodeMapping.add(mapping);
        concurrentTable.setNodeMapping(new SimpleTableNodeMapping(nodeMapping));
        return this;
    }

    public TableBuilder appendColumn(String column, ColumnProcess process){
        columns.add(new PartitionColumn(column, process));
        rule.setColumns(columns);
        concurrentTable.setRule(rule);
        return this;
    }

    public PartitionTable toTable(){
        return this.tables.get(0);
    }

    public List<PartitionTable> toTables(){
        return this.tables;
    }

}
