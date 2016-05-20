package com.tongbanjie.baymax.parser.mysql;

import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.tongbanjie.baymax.parser.SqlParserFactory;
import com.tongbanjie.baymax.parser.model.ParseResult;
import com.tongbanjie.baymax.parser.model.SqlType;
import com.tongbanjie.baymax.router.model.ExecutePlan;
import com.tongbanjie.baymax.router.model.ExecuteType;
import com.tongbanjie.baymax.test.PrintUtil;
import org.junit.Test;

/**
 * Created by sidawei on 16/3/18.
 *
 * 聚合函数解析测试
 */
public class MySqlSelectAggParserTest {

    public static String s1 = "select count(a),sum(a),avg(a),max(a),min(a) from table1 where a = 1";
    public static String s2 = "select count(a) as c1,sum(a) as c2,avg(a) as c3,max(a) as c4,min(a) as c5 from table1 where a = 1";
    public static String s3 = "select count(a) as c1,avg(a) as c2 from table1 where a = 1";

    public static String s4 = "select *, a, count(a) from table1 where a = 1";

    MySqlSelectParser parser = (MySqlSelectParser) SqlParserFactory.getParser(SqlType.SELECT);

    /*--------------------------------------------------single------------------------------------------------------*/

    /**
     * 聚合函数测试
     */
    @Test
    public void test_0(){
        //无别名测试
        test(s1);
        //有别名测试
        test(s2);
        //select字段不包含
        test(s3);

        test(s4);
    }

    /**
     * group by测试
     */
    @Test
    public void test_1(){
        // group by 在 select list 中有
        test("select count(a),a from table1 where a = 1 group by a");
        // group by 在 select list 中有 但是有别名
        test("select count(a),a as a1 from table1 where a = 1 group by a");
        // group by 在 select list 没有
        test("select count(a) from table1 where a = 1 group by a");
        // group by 在 select list 没有, 但是有*
        test("select count(a),* from table1 where a = 1 group by a");
    }

    /**
     * order by测试
     */
    @Test
    public void test_2(){
        // group by 在 select list 中有
        test("select count(a),a from table1 where a = 1 order by a");
        // group by 在 select list 中有 但是有别名
        test("select count(a),a as a1 from table1 where a = 1 order by a");
        // group by 在 select list 没有
        test("select count(a) from table1 where a = 1 order by a");
        // group by 在 select list 没有, 但是有*
        test("select count(a),* from table1 where a = 1 order by a");
    }

    /**
     * //agg
     * getMergeColumns
     * getAliaColumns
     *
     * //group by
     * setGroupbyColumns
     *
     * @param sql
     */
    public void test(String sql){
        ParseResult result = new ParseResult();
        ExecutePlan plan = new ExecutePlan();

        parser.init(sql, null);
        parser.parse(result);

        plan.setExecuteType(ExecuteType.PARTITION);

        SQLSelectStatement stmt = (SQLSelectStatement) parser.statement;

        parser.parseMysqlQueary(result, plan, (MySqlSelectQueryBlock) stmt.getSelect().getQuery());

        StringBuilder out = new StringBuilder();
        MySqlOutputVisitor outPutVisitor = new MySqlOutputVisitor(out);
        stmt.accept(outPutVisitor);

        System.out.println();
        System.out.println("/***********************agg*************************/");
        System.out.println(plan.getMergeColumns());
        System.out.println("setHasAllColumnExpr:" + result.isHasAllColumnExpr());
        System.out.println(out.toString());
        PrintUtil.printFildAlisMap(result.getAliaColumns());
        System.out.println("/*********************group by**********************/");
        System.out.println(plan.getGroupbyColumns());
        System.out.println("/*********************order by**********************/");
        System.out.println(plan.getOrderbyColumns());

    }

}
