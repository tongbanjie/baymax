package com.tongbanjie.baymax.parser.mysql;

import com.tongbanjie.baymax.parser.SqlParser;
import com.tongbanjie.baymax.parser.SqlParserFactory;
import com.tongbanjie.baymax.parser.model.ParseResult;
import com.tongbanjie.baymax.parser.model.SqlType;
import com.tongbanjie.baymax.router.RouteService;
import com.tongbanjie.baymax.router.strategy.PartitionTable;
import com.tongbanjie.baymax.sql.InsertTestSql;
import com.tongbanjie.baymax.support.BaymaxContext;
import com.tongbanjie.baymax.test.PrintUtil;
import com.tongbanjie.baymax.test.TableBuilder;
import com.tongbanjie.baymax.utils.NewArrayList;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sidawei on 16/4/9.
 */
public class MySqlInsertParserTest {
    private RouteService routeService = new RouteService();
    SqlParser parser = SqlParserFactory.getParser(SqlType.INSERT);

    {
        ininContext();
    }

    /*--------------------------------------------------single------------------------------------------------------*/

    private void ininContext(){
        PartitionTable table = new TableBuilder()
                .appenTable("table1", "table1_{0}", "value % 4")
                .appendNodeMapping("p1:0,1,2,3")
                .appendColumn("a", null)
                .appendColumn("b", null)
                .toTable();

        BaymaxContext.setTables(NewArrayList.newIt().add(table).toArrayList());

        BaymaxContext.init();
    }

    /*--------------------------------------------------single------------------------------------------------------*/

    @Test
    public void testParse() throws Exception {
        test(InsertTestSql.i1);

    }

    public void test(String sql) throws Exception {
        ParseResult result = new ParseResult();

        parser.init(sql, null);

        parser.parse(result);

        System.out.println();
        System.out.println(sql);
        System.out.println(result.getTableAliasMap());
        String unitStr2 = PrintUtil.printCalculates(result.getCalculateUnits());
    }
}