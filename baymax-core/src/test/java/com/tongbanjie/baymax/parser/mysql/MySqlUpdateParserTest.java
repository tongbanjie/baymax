package com.tongbanjie.baymax.parser.mysql;

import com.tongbanjie.baymax.parser.SqlParserFactory;
import com.tongbanjie.baymax.parser.model.ParseResult;
import com.tongbanjie.baymax.parser.model.SqlType;
import com.tongbanjie.baymax.router.RouteService;
import com.tongbanjie.baymax.router.strategy.PartitionTable;
import com.tongbanjie.baymax.sql.UpdateTestSql;
import com.tongbanjie.baymax.support.BaymaxContext;
import com.tongbanjie.baymax.test.TableBuilder;
import com.tongbanjie.baymax.utils.NewArrayList;
import org.junit.Test;

/**
 * Created by sidawei on 16/4/10.
 */
public class MySqlUpdateParserTest {

    private RouteService routeService = new RouteService();
    MySqlUpdateParser parser = (MySqlUpdateParser) SqlParserFactory.getParser(SqlType.UPDATE);

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
    public void testCheckUpdateColumn() throws Exception {
        testCheckUpdateColumn(UpdateTestSql.u1);
        testCheckUpdateColumn("update table1 set a='1' where (a = 1 or b = 2) and c = 3");
    }

    public void testCheckUpdateColumn(String sql) throws Exception {
        ParseResult result = new ParseResult();

        parser.init(sql, null);

        parser.parse(result);

        parser.checkUpdateColumn();

    }
}