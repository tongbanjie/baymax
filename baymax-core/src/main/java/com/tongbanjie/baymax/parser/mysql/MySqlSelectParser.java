package com.tongbanjie.baymax.parser.mysql;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLName;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectOrderByItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.expr.MySqlSelectGroupByExpr;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlUnionQuery;
import com.tongbanjie.baymax.exception.BayMaxException;
import com.tongbanjie.baymax.jdbc.merge.MergeColumn;
import com.tongbanjie.baymax.jdbc.merge.OrderbyColumn;
import com.tongbanjie.baymax.parser.model.Limit;
import com.tongbanjie.baymax.parser.model.ParseResult;
import com.tongbanjie.baymax.router.model.ExecutePlan;
import com.tongbanjie.baymax.utils.ReflectionUtils;
import com.tongbanjie.baymax.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sidawei on 16/1/15.
 *
 * select语句解析器
 */
public class MySqlSelectParser extends MySqlSqlParser {

    @Override
    public void changeSql(ParseResult result, ExecutePlan plan) {
        // 解析聚合函数的
        parseStatement(result, plan, (SQLSelectStatement)statement);
        // TODO 设置读写分离
        super.changeSql(result, plan);
    }

    protected void parseStatement(ParseResult result, ExecutePlan plan, SQLSelectStatement statement){
        // 单库单表
        if (plan.getSqlList().size() <= 1){
            return;
        }

        SQLSelectQuery sqlSelectQuery = statement.getSelect().getQuery();
        if(sqlSelectQuery instanceof MySqlSelectQueryBlock) {
            // mysql查询
            parseMysqlQueary(result, plan, (MySqlSelectQueryBlock) sqlSelectQuery);
        } else if (sqlSelectQuery instanceof MySqlUnionQuery) {
            throw new BayMaxException("Union暂不支持发送到多库多表上执行，只能在单库单表执行！");
            // TODO 测试
			/*
			MySqlUnionQuery unionQuery = (MySqlUnionQuery)sqlSelectQuery;
            SQLSelectQuery left = unionQuery.getLeft();
            SQLSelectQuery right = unionQuery.getLeft();
            if (left instanceof MySqlSelectQueryBlock){
                parseMysqlQueary(result, plan, (MySqlSelectQueryBlock) left);
            }
            if (right instanceof MySqlSelectQueryBlock){
                parseMysqlQueary(result, plan, (MySqlSelectQueryBlock) right);
            }
            */
            //if (left.getFrom().getAlias().equalsIgnoreCase(plan.getSqlList().get(0).getLogicTableName())){

            //}
        }
    }

    protected void parseMysqlQueary(ParseResult result, ExecutePlan plan, MySqlSelectQueryBlock mysqlSelectQuery){
        // 解析聚合函数
        parseAggregate(result, plan, mysqlSelectQuery);
        // 解析groupby
        parseGroupBy(result, plan, mysqlSelectQuery);
        // 解析orderby
        parseOrderby(result, plan, mysqlSelectQuery);
        // 解析limit
        parseLimit(result, plan, mysqlSelectQuery);

    }

    /**
     * 解析聚合函数
     * @param result
     * @param plan
     * @param mysqlSelectQuery
     */
    protected void parseAggregate(ParseResult result, ExecutePlan plan, MySqlSelectQueryBlock mysqlSelectQuery){
        // 要合并的列
        Map<String, MergeColumn.MergeType>      aggrColumns         = new HashMap<String, MergeColumn.MergeType>();
        Map<String/*field*/, String/*alias*/>   aliaColumns         = new HashMap<String, String>();
        // 查询的列
        List<SQLSelectItem>                     selectList          = mysqlSelectQuery.getSelectList();
        int                                     size                = selectList.size();
        //boolean                               isDistinct          = mysqlSelectQuery.getDistionOption() == 2;
        List<String>                            avgColumns          = null;

        for (int i = 0; i < size; i++){
            SQLSelectItem item = selectList.get(i);
            if (item.getExpr() instanceof SQLAggregateExpr) {
                SQLAggregateExpr expr = (SQLAggregateExpr) item.getExpr();
                String method = expr.getMethodName();
                // 只处理有别名的情况，无别名添加别名，否则某些数据库会得不到正确结果处理
                MergeColumn.MergeType mergeType = MergeColumn.buildMergeType(method);

                if (MergeColumn.MergeType.MERGE_UNSUPPORT == mergeType) {
                    continue;
                }

                // 没有别名的 增加别名
                if (item.getAlias() == null || item.getAlias().length() <= 0) {
                    item.setAlias(method + i);
                }

                // 保存合并列
                aggrColumns.put(item.getAlias(), mergeType);

                if (MergeColumn.MergeType.MERGE_AVG == mergeType) {
                    if (avgColumns == null) {
                        avgColumns = new ArrayList<String>();
                    }
                    avgColumns.add(item.getAlias());

                    //sum
                    // 表达式
                    SQLAggregateExpr sumExp = new SQLAggregateExpr("SUM");
                    ReflectionUtils.copyProperties(expr, sumExp);
                    sumExp.getArguments().addAll(expr.getArguments());
                    sumExp.setMethodName("SUM");

                    // item
                    SQLSelectItem sum = new SQLSelectItem();
                    String sumColName = item.getAlias() + "SUM";
                    sum.setAlias(sumColName);
                    sum.setExpr(sumExp);

                    // 替换
                    selectList.add(sum);
                    aggrColumns.put(sumColName, MergeColumn.MergeType.MERGE_SUM);

                    // count
                    // 表达式
                    SQLAggregateExpr countExp = new SQLAggregateExpr("COUNT");
                    ReflectionUtils.copyProperties(expr, countExp);
                    countExp.getArguments().addAll(expr.getArguments());
                    countExp.setMethodName("COUNT");

                    // item
                    SQLSelectItem count = new SQLSelectItem();
                    String countColName = item.getAlias() + "COUNT";
                    count.setAlias(countColName);
                    count.setExpr(countExp);

                    // 替换
                    selectList.add(count);
                    aggrColumns.put(countColName, MergeColumn.MergeType.MERGE_COUNT);

                    // 原始avg
                    aggrColumns.put(item.getAlias(), MergeColumn.MergeType.MERGE_AVG);
                }
            }else
            // 所有select字段的别名
            if (item.getExpr() instanceof SQLAllColumnExpr){
                // select *
                result.setHasAllColumnExpr(true);
            }else if(item.getExpr() instanceof SQLIdentifierExpr){
                // 只有普通select list的字段才放到别名列表中，用于orderby groupby的取值比较
                String alia = item.getAlias();
                //String field = getFieldName(item);
                String field = item.getExpr().toString();
                alia = alia == null ? field : alia;
                aliaColumns.put(field, alia);
            }
        }
        plan.setMergeColumns(aggrColumns);
        result.setAliaColumns(aliaColumns);
    }

    protected String getFieldName(SQLSelectItem item){
        if ((item.getExpr() instanceof SQLPropertyExpr)||(item.getExpr() instanceof SQLMethodInvokeExpr)
                || (item.getExpr() instanceof SQLIdentifierExpr) || item.getExpr() instanceof SQLBinaryOpExpr) {
            return item.getExpr().toString();//字段别名
        }else {
            return item.toString();
        }
    }

    /**
     * 解析groupby
     * @param result
     * @param plan
     * @param mysqlSelectQuery
     */
    protected void parseGroupBy(ParseResult result, ExecutePlan plan, MySqlSelectQueryBlock mysqlSelectQuery){
        if(mysqlSelectQuery.getGroupBy() == null) {
            return;
        }
        List<SQLExpr> groupByItems = mysqlSelectQuery.getGroupBy().getItems();
        if (groupByItems == null || groupByItems.size() == 0){
            return;
        }
        List<SQLSelectItem> selectList      = mysqlSelectQuery.getSelectList();
        List<String> groupbyColumns  = new ArrayList<String>(groupByItems.size());
        for(SQLExpr item : groupByItems){
            String name = null;
            if (item instanceof MySqlSelectGroupByExpr){
                name = StringUtil.removeDot(((MySqlSelectGroupByExpr) item).getExpr().toString());
            }else if (item instanceof SQLIdentifierExpr){
                name = item.toString();
            }else if (item instanceof SQLName){
                name = ((SQLName) item).getSimpleName();
            }else {
                throw new BayMaxException("group by 不支持的表达式:" + item.toString());
            }
            if (result.getAliaColumns() != null){
                // 有别名,说明在select list中使用了别名
                String alias = result.getAliaColumns().get(name);
                if (alias != null){
                    // select user_id as uid ....order by user_id
                    // 要把oderby的user_id转换为uid,以便结果集合并,这个替换是等价的
                    // 因为合并的时候需要根据orderby的字段,取值,比较
                    groupbyColumns.add(alias);
                    continue;
                }
            }
            if (!result.isHasAllColumnExpr()){
                // select列表中没有orderby的字段 添加，用于后面做合并
                SQLIdentifierExpr exp = new SQLIdentifierExpr(name);
                // item
                SQLSelectItem addItem = new SQLSelectItem();
                addItem.setExpr(exp);
                exp.setParent(item);
                selectList.add(addItem);
            }
            groupbyColumns.add(name);
        }
        plan.setGroupbyColumns(groupbyColumns);
    }

    /**
     * 解析Orderby
     * @param result
     * @param plan
     * @param mysqlSelectQuery
     */
    protected void parseOrderby(ParseResult result, ExecutePlan plan, MySqlSelectQueryBlock mysqlSelectQuery){
        if (mysqlSelectQuery.getOrderBy() == null){
            return;
        }
        List<SQLSelectOrderByItem> orderByItems = mysqlSelectQuery.getOrderBy().getItems();
        if (orderByItems == null || orderByItems.size() == 0){
            return;
        }
        List<SQLSelectItem> selectList      = mysqlSelectQuery.getSelectList();
        List<OrderbyColumn> orderbyColumns  = new ArrayList<OrderbyColumn>(orderByItems.size());
        for(SQLSelectOrderByItem item : orderByItems){
            String name = StringUtil.removeDot(item.getExpr().toString());
            if (result.getAliaColumns() != null){
                String alias = result.getAliaColumns().get(name);
                if (alias != null){
                    // select user_id as uid ....order by user_id
                    // 要把oderby的user_id转换为uid,以便结果集合并
                    orderbyColumns.add(new OrderbyColumn(alias, OrderbyColumn.buildOrderbyType(item.getType())));
                    continue;
                }
            }
            if (!result.isHasAllColumnExpr()){
                // select列表中没有orderby的字段 添加，用于后面做合并
                SQLIdentifierExpr exp = new SQLIdentifierExpr(name);

                // item
                SQLSelectItem addItem = new SQLSelectItem();
                addItem.setExpr(exp);
                exp.setParent(item);
                selectList.add(addItem);
            }
            orderbyColumns.add(new OrderbyColumn(name, OrderbyColumn.buildOrderbyType(item.getType())));
        }
        plan.setOrderbyColumns(orderbyColumns);
    }

    protected void parseLimit(ParseResult result, ExecutePlan plan, MySqlSelectQueryBlock mysqlSelectQuery){
        MySqlSelectQueryBlock.Limit x = mysqlSelectQuery.getLimit();
        if (x == null){
            return;
        }
        Map<Integer, Object> overrideParameters = new HashMap<Integer, Object>(2);
        int offset = 0;
        if (null != x.getOffset()) {
            if (x.getOffset() instanceof SQLNumericLiteralExpr) {
                offset = ((SQLNumericLiteralExpr) x.getOffset()).getNumber().intValue();
                SQLNumberExpr offsetExpr = new SQLNumberExpr();
                offsetExpr.setNumber(0);
                x.setOffset(offsetExpr);
            } else {
                offset = ((Number) parameters.get(((SQLVariantRefExpr) x.getOffset()).getIndex())).intValue();
                overrideParameters.put(((SQLVariantRefExpr) x.getOffset()).getIndex() + 1, 0);
            }
        }
        int rowCount;
        if (x.getRowCount() instanceof SQLNumericLiteralExpr) {
            rowCount = ((SQLNumericLiteralExpr) x.getRowCount()).getNumber().intValue();
            SQLNumberExpr rowsExpr = new SQLNumberExpr();
            rowsExpr.setNumber(rowCount + offset);
            x.setRowCount(rowsExpr);
        } else {
            rowCount = ((Number) parameters.get(((SQLVariantRefExpr) x.getRowCount()).getIndex())).intValue();
            overrideParameters.put(((SQLVariantRefExpr) x.getRowCount()).getIndex() + 1, rowCount + offset);
        }
        plan.setLimit(new Limit(offset, rowCount));
        plan.setOverrideParameters(overrideParameters);
    }

}
