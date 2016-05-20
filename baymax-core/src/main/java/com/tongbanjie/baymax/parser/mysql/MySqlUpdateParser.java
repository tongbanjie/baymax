package com.tongbanjie.baymax.parser.mysql;

import com.alibaba.druid.sql.ast.statement.SQLUpdateSetItem;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlUpdateStatement;
import com.tongbanjie.baymax.exception.BayMaxException;
import com.tongbanjie.baymax.parser.model.ParseResult;
import com.tongbanjie.baymax.router.model.ExecutePlan;
import com.tongbanjie.baymax.router.model.ExecuteType;
import com.tongbanjie.baymax.support.BaymaxContext;
import com.tongbanjie.baymax.utils.StringUtil;

import java.util.List;

/**
 * Created by sidawei on 16/1/15.
 */
public class MySqlUpdateParser extends MySqlSqlParser {

    @Override
    public void changeSql(ParseResult result, ExecutePlan plan) {
        if (plan.getExecuteType() != ExecuteType.NO){
            checkUpdateColumn();
        }
        super.changeSql(result, plan);
    }

    protected void checkUpdateColumn(){
        MySqlUpdateStatement update = (MySqlUpdateStatement)statement;
        String tableName = StringUtil.removeBackquote(update.getTableName().getSimpleName());
        List<SQLUpdateSetItem> updateSetItem = update.getItems();
        String[] partitionColumns = BaymaxContext.getPartitionColumns(tableName);
        if(partitionColumns != null && partitionColumns.length > 0 && updateSetItem != null && updateSetItem.size() > 0) {
            for(SQLUpdateSetItem item : updateSetItem) {
                String column = StringUtil.removeBackquote(item.getColumn().toString());
                if (StringUtil.contains(partitionColumns, column)){
                    throw new BayMaxException("分区表的分区键不能被更新:" + tableName + "." + column);
                }
            }
        }
    }
}
