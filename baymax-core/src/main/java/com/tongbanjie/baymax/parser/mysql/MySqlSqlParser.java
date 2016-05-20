package com.tongbanjie.baymax.parser.mysql;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.stat.TableStat.Condition;
import com.tongbanjie.baymax.parser.SqlParser;
import com.tongbanjie.baymax.parser.utils.CalculateUnitUtil;
import com.tongbanjie.baymax.parser.model.ParseResult;
import com.tongbanjie.baymax.parser.mysql.visitor.OrVisitor;
import com.tongbanjie.baymax.parser.mysql.visitor.SqlVisitor;
import com.tongbanjie.baymax.parser.mysql.visitor.ReplaceTableNameVisitor;
import com.tongbanjie.baymax.router.model.ExecutePlan;
import com.tongbanjie.baymax.router.model.ExecuteType;
import com.tongbanjie.baymax.router.model.TrargetSqlEntity;
import com.tongbanjie.baymax.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MySqlSqlParser implements SqlParser {

    protected SQLStatementParser        parser;
    protected SqlVisitor visitor;
    protected SQLStatement              statement;
    protected String                    sql;
    protected List<Object>              parameters;

    @Override
    public void init(String sql, List<Object> parameters) {
        this.parser		    = new MySqlStatementParser(sql);
        this.visitor 		= new SqlVisitor(parameters);
        this.parameters     = parameters;
        this.sql            = sql;
    }

    /**
     * 默认通过visitor解析 子类可以覆盖
     *
     * 限制:分表的where中,分表key只能出现一次且必须是 a=1 或者 a in ()的类型
     * @param result
     */
    @Override
    public void parse(ParseResult result) {
        // 解析sql
        statement = parser.parseStatement();

        // 用visiter遍历sql
        statement.accept(visitor);

        // 表名格式化
        alisMapFix(result);

        // 原始sql
        result.setSql(sql);

        // conditions
        //if (CalculateUnitUtil.hasPartitionTable(result.getTables())){
            List<List<Condition>> mergedConditionList = new ArrayList<List<Condition>>();
            if (visitor.hasOrCondition()){
                OrVisitor orVisitor = new OrVisitor();
                mergedConditionList = new OrVisitor.OrEntity(orVisitor, statement).getOrConditions();
            }else {
                if (visitor.getConditions() != null && visitor.getConditions().size() != 0){
                    mergedConditionList.add(visitor.getConditions());
                }
            }
            // 有分区表 计算路由单元
            result.setCalculateUnits(CalculateUnitUtil.buildCalculateUnits(result.getTableAliasMap(), mergedConditionList));
        //}
    }

    /**
     * 表名统一格式
     * database.`table`  database.'table' ==> table
     * @param result
     */
    private void alisMapFix(ParseResult result){
        Map<String,String> tableAliasMap = new HashMap<String,String>();
        if (visitor.getAliasMap() != null) {
            for (Map.Entry<String, String> entry : visitor.getAliasMap().entrySet()) {

                String key      = entry.getKey();
                String value    = entry.getValue();

                key             = StringUtil.removeDot(key);
                value           = StringUtil.removeBackquote(value);

                if (key.equals(value)) {
                    // sql中所有的表名
                    result.addTable(key);
                }
                tableAliasMap.put(key, value);
            }
            visitor.getAliasMap().putAll(tableAliasMap);
            result.setTableAliasMap(tableAliasMap);
        }
    }

    @Override
    public void changeSql(ParseResult result, ExecutePlan plan) {
        if (ExecuteType.NO == plan.getExecuteType()){
        }else {
            for (TrargetSqlEntity sql : plan.getSqlList()){
                ReplaceTableNameVisitor replaceVisitor = new ReplaceTableNameVisitor(sql.getLogicTableName(), sql.getTargetTableName());
                StringBuilder out = new StringBuilder();
                MySqlOutputVisitor outPutVisitor = new MySqlOutputVisitor(out);
                // 替换表名
                statement.accept(replaceVisitor);
                // 输出sql
                statement.accept(outPutVisitor);
                sql.setOriginalSql(result.getSql());
                sql.setTargetSql(out.toString());
                // 输出sql后要还原statement以便下次替换表名
                replaceVisitor.reset();
            }
        }
    }
}
