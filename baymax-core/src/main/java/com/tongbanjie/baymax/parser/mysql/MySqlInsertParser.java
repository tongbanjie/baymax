package com.tongbanjie.baymax.parser.mysql;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlInsertStatement;
import com.alibaba.druid.sql.visitor.SQLEvalVisitor;
import com.alibaba.druid.sql.visitor.SQLEvalVisitorUtils;
import com.alibaba.druid.util.JdbcUtils;
import com.tongbanjie.baymax.exception.BayMaxException;
import com.tongbanjie.baymax.parser.model.CalculateUnit;
import com.tongbanjie.baymax.parser.model.ConditionUnit;
import com.tongbanjie.baymax.parser.model.ConditionUnitOperator;
import com.tongbanjie.baymax.parser.model.ParseResult;
import com.tongbanjie.baymax.router.model.ExecutePlan;
import com.tongbanjie.baymax.router.model.ExecuteType;
import com.tongbanjie.baymax.support.BaymaxContext;
import com.tongbanjie.baymax.utils.StringUtil;

import java.sql.SQLNonTransientException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sidawei on 16/1/15.
 */
public class MySqlInsertParser extends MySqlSqlParser {

    /*-----------------------------------------------解析---------------------------------------------------*/

    /**
     * 注意：子查询,批量,分片
     * @param result
     */
    @Override
    public void parse(ParseResult result) {

        // 解析sql
        statement = parser.parseStatement();

        MySqlInsertStatement insert = (MySqlInsertStatement)statement;

        String tableName = StringUtil.removeBackquote(insert.getTableName().getSimpleName());

        result.addTable(tableName);

        // 不是分区表
        if (!BaymaxContext.isPartitionTable(tableName)){
            return;
        }

        if (isMultiInsert(insert)){
            // 批量插入
            throw new UnsupportedOperationException("分区表不支持insert into ...values (),()...或 insert into ...select.....形式的批量插入");
        }else {
            // 单条插入
            result.setCalculateUnits(parserSingleInsert(tableName, insert));
        }
    }

    /**
     * 是否为批量插入：insert into ...values (),()...或 insert into ...select.....
     * @param insertStmt
     * @return
     */
    private boolean isMultiInsert(MySqlInsertStatement insertStmt) {
        return (insertStmt.getValuesList() != null && insertStmt.getValuesList().size() > 1) || insertStmt.getQuery() != null;
    }

    /**
     * 单条insert（非批量）
     * @param tableName
     * @param insertStmt
     * @throws SQLNonTransientException
     */
    private List<CalculateUnit> parserSingleInsert(String tableName, MySqlInsertStatement insertStmt) {
        String[] partitionColumn = BaymaxContext.getPartitionColumns(tableName);
        List<CalculateUnit> units = new ArrayList<CalculateUnit>(1);
        CalculateUnit unit = new CalculateUnit();
        boolean isFind = false;
        for(int i = 0; i < insertStmt.getColumns().size(); i++) {
            String column = StringUtil.removeBackquote(insertStmt.getColumns().get(i).toString());
            if (StringUtil.contains(partitionColumn, column)){
                SQLExpr valueExpr = insertStmt.getValues().getValues().get(i);
                Object value = SQLEvalVisitorUtils.eval(JdbcUtils.MYSQL, valueExpr, parameters, false);
                if (!SQLEvalVisitor.EVAL_VALUE_NULL.equals(value)){
                    unit.addCondition(ConditionUnit.buildConditionUnit(tableName, column, new Object[]{value}, ConditionUnitOperator.EQUAL));
                    isFind = true;
                }
            }
        }
        if(isFind) {
            units.add(unit);
        }else {
            throw new BayMaxException("分片的insert没有包含分区key");
        }
        // insert into .... on duplicateKey
        // such as :INSERT INTO TABLEName (a,b,c) VALUES (1,2,3) ON DUPLICATE KEY UPDATE b=VALUES(b);
        // INSERT INTO TABLEName (a,b,c) VALUES (1,2,3) ON DUPLICATE KEY UPDATE c=c+1;
        if(insertStmt.getDuplicateKeyUpdate() != null) {
            List<SQLExpr> updateList = insertStmt.getDuplicateKeyUpdate();
            for(SQLExpr expr : updateList) {
                SQLBinaryOpExpr opExpr = (SQLBinaryOpExpr)expr;
                String column = StringUtil.removeBackquote(opExpr.getLeft().toString().toUpperCase());
                if(StringUtil.contains(partitionColumn, column)) {
                    throw new BayMaxException("ON DUPLICATE KEY UPDATE b=VALUES(b)中,不能UPDATE分区key:" + column);
                }
            }
        }
        return units;
    }

    /*-----------------------------------------------重写---------------------------------------------------*/

    @Override
    public void changeSql(ParseResult result, ExecutePlan plan) {
        if (plan.getExecuteType() != ExecuteType.NO){
            if (plan.getSqlList().size() != 1){
                throw new BayMaxException("分区表的Insert路由结果必须是单库单表");
            }
        }
        super.changeSql(result, plan);
    }
}
