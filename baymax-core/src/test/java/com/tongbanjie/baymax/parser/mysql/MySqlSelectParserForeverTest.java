package com.tongbanjie.baymax.parser.mysql;

import com.tongbanjie.baymax.parser.SqlParser;
import com.tongbanjie.baymax.parser.SqlParserFactory;
import com.tongbanjie.baymax.parser.model.ConditionUnit;
import com.tongbanjie.baymax.parser.model.ConditionUnitOperator;
import com.tongbanjie.baymax.parser.model.ParseResult;
import com.tongbanjie.baymax.parser.model.SqlType;
import com.tongbanjie.baymax.test.PrintUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

/**
 * Created by sidawei on 16/3/18.
 */
public class MySqlSelectParserForeverTest {

    // 永真
    public String sql9 = "select * from table1 where (a = 1 or true)";
    public String sql10 = "select * from table1 where (a = 1 or true) or c = 1";
    public String sql11 = "select * from table1 where (a = 1 or true) and c = 1";
    public String sql12 = "select * from table1 where (a = 1 or true) or c = 1";

    // 永假
    public String sql20 = "select * from table1 where (a = 1 and b = 1 and false) and c = 1";
    public String sql21 = "select * from table1 where (a = 1 and false) or c = 1";
    public String sql22 = "select * from table1 where (a = 1 and false) and c = 1";


    SqlParser parser = SqlParserFactory.getParser(SqlType.SELECT);

    /*--------------------------------------------------永远真------------------------------------------------------*/


    @Test
    public void testParse_single1() throws Exception {
        ParseResult result = new ParseResult();
        parser.init(sql9, null);
        parser.parse(result);

        PrintUtil.printCalculates(result.getCalculateUnits());

        Assert.assertEquals(0, result.getCalculateUnits().size());
    }

    @Test
    public void testParse_sql10() throws Exception {
        ParseResult result = new ParseResult();
        parser.init(sql10, null);
        parser.parse(result);

        PrintUtil.printCalculates(result.getCalculateUnits());

        Assert.assertEquals(0, result.getCalculateUnits().size());
    }

    @Test
    public void testParse_sql11() throws Exception {
        ParseResult result = new ParseResult();
        parser.init(sql11, null);
        parser.parse(result);

        PrintUtil.printCalculates(result.getCalculateUnits());

        Iterator<ConditionUnit> ite1 = result.getCalculateUnits().get(0).getTablesAndConditions().get("table1").iterator();
        ConditionUnit unit1_1 = ite1.next();

        Assert.assertEquals(ConditionUnit.buildConditionUnit("table1", "c", new String[]{"1"}, ConditionUnitOperator.EQUAL), unit1_1);

    }

    @Test
    public void testParse_sql12() throws Exception {
        ParseResult result = new ParseResult();
        parser.init(sql12, null);
        parser.parse(result);

        PrintUtil.printCalculates(result.getCalculateUnits());

        Assert.assertEquals(0, result.getCalculateUnits().size());
    }

    /*--------------------------------------------------永假------------------------------------------------------*/
    @Test
    public void testParse_sql20() throws Exception {
        ParseResult result = new ParseResult();
        parser.init(sql20, null);
        parser.parse(result);

        PrintUtil.printCalculates(result.getCalculateUnits());

    }

    @Test
    public void testParse_sql21() throws Exception {
        ParseResult result = new ParseResult();
        parser.init(sql21, null);
        parser.parse(result);

        PrintUtil.printCalculates(result.getCalculateUnits());

    }

    @Test
    public void testParse_sql22() throws Exception {
        ParseResult result = new ParseResult();
        parser.init(sql22, null);
        parser.parse(result);

        PrintUtil.printCalculates(result.getCalculateUnits());

    }
}
