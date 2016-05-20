package com.tongbanjie.baymax.parser.mysql.visitor;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.tongbanjie.baymax.sql.DeleteTestSql;
import com.tongbanjie.baymax.sql.InsertTestSql;
import com.tongbanjie.baymax.sql.SelectTestSql;
import com.tongbanjie.baymax.sql.UpdateTestSql;
import org.junit.Test;

/**
 * Created by sidawei on 16/4/9.
 */
public class ReplaceTableNameVisitorTest implements SelectTestSql, DeleteTestSql, UpdateTestSql, InsertTestSql {

    @Test
    public void test_0(){
        test(i1, "table1", "tb");
        test(d1, "table1", "tb");
        test(u1, "table1", "tb");
        test(single1, "table1", "tb");
        test(join1, "table1", "tb");
        //test(join11, "table1", "tb");
    }

    public void test(String sql, String logicName, String targetName){

        MySqlStatementParser parser = new MySqlStatementParser(sql);
        SQLStatement statement = parser.parseStatement();

        ReplaceTableNameVisitor replaceVisitor = new ReplaceTableNameVisitor(logicName, targetName);
        StringBuilder out = new StringBuilder();
        MySqlOutputVisitor outPutVisitor = new MySqlOutputVisitor(out);
        // 替换表名
        statement.accept(replaceVisitor);
        // 输出sql
        statement.accept(outPutVisitor);

        System.out.println();
        System.out.println(sql);
        System.out.println(out.toString());
        // 输出sql后要还原statement以便下次替换表名
        replaceVisitor.reset();
    }

}