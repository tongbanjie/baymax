package com.tongbanjie.baymax.parser.mysql.visitor;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.stat.TableStat;
import com.tongbanjie.baymax.sql.DeleteTestSql;
import com.tongbanjie.baymax.sql.InsertTestSql;
import com.tongbanjie.baymax.sql.SelectTestSql;
import com.tongbanjie.baymax.sql.UpdateTestSql;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sidawei on 16/3/15.
 *
 * 主要提取以下三个内容
 * visitor.hasOrCondition()
 * visitor.getConditions()
 * visitor.getAliasMap()
 */
public class SqlVisitorTest implements SelectTestSql, DeleteTestSql, UpdateTestSql, InsertTestSql {

    @Test
    public void test_0() throws Exception {
        test(i1);
        test(join1);
        test(join10);
        test(u1);
        test(d1);


        test(sb1);
        test(sb2);

        test(join1);

        test(join10);

        test(union1);
        //(select a from table1 where a = 1) union (select a from table2 where b = 2)
        //conditions : [table1.a = 1, table2.b = 2]

        test(union2);
        //(select a from table1 where a = 1) union (select a from table2 where b = 2 or c = 3)
        //conditions : [table1.a = 1, table2.b = 2, table2.c = 3]

        test(d1);
        //conditions : [table1.a = 1, table1.b = 2, table1.c = 3]

        test(u1);
        //update table1 set name='iname' where (a = 1 or b = 2) and c = 3
        //conditions : [table1.a = 1, table1.b = 2, table1.c = 3]
    }

    public void test(String sql) throws Exception {

        MySqlStatementParser parser = new MySqlStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();

        SqlVisitor visitor = new SqlVisitor(null);
        statementList.get(0).accept(visitor);

        System.out.println();
        System.out.println(sql);
        System.out.println("Tables : " + visitor.getTables());
        System.out.println("fields : " + visitor.getColumns());
        System.out.println("alias : " + visitor.getAliasMap());
        System.out.println("conditions : " + visitor.getConditions());
        System.out.println("columns : " + visitor.getColumns());


    }

}