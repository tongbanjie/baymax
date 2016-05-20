package com.tongbanjie.baymax.parser.mysql.visitor;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.stat.TableStat;
import com.tongbanjie.baymax.sql.DeleteTestSql;
import com.tongbanjie.baymax.sql.SelectTestSql;
import com.tongbanjie.baymax.sql.UpdateTestSql;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sidawei on 16/3/16.
 *
 * or解析器
 */
public class OrVisitorTest implements SelectTestSql, DeleteTestSql, UpdateTestSql {

    /**
     * 单表
     * @throws Exception
     */
    @Test
    public void testGetOrConditions() throws Exception {

        test(single1);
        test(single2);
        test(single3);
        test(single4);
        test(single5);
        test(single6);
        test(single7);
        test(single8);
        test(single9);
        test(single10);
        test(single11);
        test(single12);
        test(single13);
        test(single14);

        test(sb1);
        test(sb2);

        test(join1);
        //[[table1.id = (null, 100), table2.id = null]]

        test(join10);
        //[[table1.id = null, table2.id = (null, 100)]]

        test(union1);
        //[[table1.a = 1, table2.b = 2]]

        test(union2);
        //[[table2.b = 2, table1.a = 1], [table2.c = 3, table1.a = 1]]

        test(d1);
        //[[table1.a = 1, table1.c = 3], [table1.b = 2, table1.c = 3]]

        test(u1);
        //[[table1.a = 1, table1.c = 3], [table1.b = 2, table1.c = 3]]
    }

    private List<List<TableStat.Condition>> test(String sql){
        MySqlStatementParser parser = new MySqlStatementParser(sql);

        SQLStatement statemen = parser.parseStatement();

        List<Object> parameters = new ArrayList<Object>();

        parameters.add(10);

        OrVisitor visitor = new OrVisitor();
        OrVisitor.OrEntity orEntity = new OrVisitor.OrEntity(visitor, statemen);
        List<List<TableStat.Condition>> conditions = orEntity.getOrConditions();
        System.out.println();
        System.out.println(sql);
        System.out.println(conditions);
        System.out.println();
        return conditions;
    }
}