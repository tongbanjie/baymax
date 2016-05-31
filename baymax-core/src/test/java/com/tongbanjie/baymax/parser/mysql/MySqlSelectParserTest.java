package com.tongbanjie.baymax.parser.mysql;

import com.tongbanjie.baymax.parser.SqlParser;
import com.tongbanjie.baymax.parser.SqlParserFactory;
import com.tongbanjie.baymax.test.PrintUtil;
import com.tongbanjie.baymax.sql.SelectTestSql;
import com.tongbanjie.baymax.parser.model.ConditionUnit;
import com.tongbanjie.baymax.parser.model.ConditionUnitOperator;
import com.tongbanjie.baymax.parser.model.ParseResult;
import com.tongbanjie.baymax.parser.model.SqlType;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

/**
 * Created by sidawei on 16/3/18.
 *
 * Test: alisMap 路由单元
 */
public class MySqlSelectParserTest implements SelectTestSql {

    SqlParser parser = SqlParserFactory.getParser(SqlType.SELECT);

    /*--------------------------------------------------single------------------------------------------------------*/

    @Test
    public void test_0() throws Exception {
//        test(single1, "{table1:{a=[1]}{b=[2]}}");
//        test(single2, "{table1:{a=[1]}}{table1:{a=[2]}}");
//        test(single13, "{table1:{a=[0]}{c=[0]}}{table1:{a=[0]}{d=[0]}}{table1:{b=[1]}{c=[0]}}{table1:{b=[1]}{d=[0]}}");
//        test(single14, "{table1:{a=[0]}}{table1:{b=[1]}}{table1:{c=[0]}}{table1:{d=[0]}}");
//        test(sb1, "{table2:{b=[0]}}");
//        test(sb2, "{table2:{b=[2]}}");
//        test(join1, "{table1:{id=[100]}}");
//        test(join10, "{table2:{id=[100]}}");
//        test(join11, "{table1:{id=[100]}}");
//        test(union1, "{table1:{a=[1]}table2:{b=[2]}}");
//        test(union2, "{table2:{b=[2]}table1:{a=[1]}}{table2:{c=[3]}table1:{a=[1]}}");
        test(in1, "{table1:{a=[1, 2, 3]}{c=[1]}}{table1:{b=[1]}{c=[1]}}");
    }

    public void test(String sql, String unitStr) throws Exception {
        ParseResult result = new ParseResult();
        parser.init(sql, null);

        parser.parse(result);

        System.out.println();
        System.out.println(sql);
        System.out.println(result.getTableAliasMap());
        String unitStr2 = PrintUtil.printCalculates(result.getCalculateUnits());

        // 计算单元
        Assert.assertEquals(unitStr, unitStr2);

    }
}
