package com.tongbanjie.baymax.router;

import com.tongbanjie.baymax.parser.model.*;
import com.tongbanjie.baymax.router.strategy.*;
import com.tongbanjie.baymax.sql.SelectTestSql;
import com.tongbanjie.baymax.jdbc.model.ParameterCommand;
import com.tongbanjie.baymax.jdbc.model.ParameterMethod;
import com.tongbanjie.baymax.router.model.ExecutePlan;
import com.tongbanjie.baymax.router.model.ExecuteType;
import com.tongbanjie.baymax.router.model.TrargetSqlEntity;
import com.tongbanjie.baymax.support.BaymaxContext;
import com.tongbanjie.baymax.test.TableBuilder;
import com.tongbanjie.baymax.utils.NewArrayList;
import junit.framework.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by sidawei on 16/3/21.
 */
public class RouteServiceTest implements SelectTestSql{

    private RouteService routeService = new RouteService();

    {
        ininContext();
    }

    /*--------------------------------------------------single------------------------------------------------------*/

    private void ininContext(){
        PartitionTable table = new TableBuilder()
                .appenTable("table1", "table1_{0}", "value % 4")
                .appendNodeMapping("p1:0,1,2,3")
                .appendColumn("a", null)
                .toTable();

        BaymaxContext.setTables(NewArrayList.newIt().add(table).toArrayList());

        BaymaxContext.init();
    }

    /*--------------------------------------------------single------------------------------------------------------*/


    @Test
    public void testDoRoute() throws Exception {
        ExecutePlan plan = routeService.doRoute(single14, null);
        System.out.println(plan);
        Assert.assertEquals(ExecuteType.ALL, plan.getExecuteType());
        TrargetSqlEntity sql = plan.getSqlList().get(0);
        Assert.assertEquals("p1", sql.getPartition());
        Assert.assertEquals("table1", sql.getLogicTableName());
        Assert.assertEquals("table1_1", sql.getTargetTableName());
        Assert.assertEquals(4, plan.getSqlList().size());
    }

    /*--------------------------------------------------single------------------------------------------------------*/

    /**
     * 普通路由
     * @throws Exception
     */
    @Test
    public void testRoute() throws Exception {
        List<String> tables = new ArrayList<String>();
        List<CalculateUnit > units = new ArrayList<CalculateUnit>();

        tables.add("table1");

        CalculateUnit unit1 = new CalculateUnit();
        unit1.addCondition(ConditionUnit.buildConditionUnit("table1", "a", new String[]{"2"}, ConditionUnitOperator.EQUAL));

        units.add(unit1);

        ExecutePlan plan =routeService.route(tables, units, "select", SqlType.SELECT);

        System.out.println(plan);
    }

    /**
     * And
     * @throws Exception
     */
    @Test
    public void testRoute_1() throws Exception {
        List<String> tables = new ArrayList<String>();
        List<CalculateUnit > units = new ArrayList<CalculateUnit>();

        tables.add("table1");

        CalculateUnit unit1 = new CalculateUnit();
        unit1.addCondition(ConditionUnit.buildConditionUnit("table1", "a", new String[]{"2"}, ConditionUnitOperator.EQUAL));
        unit1.addCondition(ConditionUnit.buildConditionUnit("table1", "b", new String[]{"1"}, ConditionUnitOperator.EQUAL));

        units.add(unit1);

        ExecutePlan plan =routeService.route(tables, units, "select", SqlType.SELECT);

        System.out.println(plan);
    }

    /**
     * Or 有相同结果
     * @throws Exception
     */
    @Test
    public void testRoute_2() throws Exception {
        List<String> tables = new ArrayList<String>();
        List<CalculateUnit > units = new ArrayList<CalculateUnit>();

        tables.add("table1");

        CalculateUnit unit1 = new CalculateUnit();
        unit1.addCondition(ConditionUnit.buildConditionUnit("table1", "a", new String[]{"2"}, ConditionUnitOperator.EQUAL));

        CalculateUnit unit2 = new CalculateUnit();
        //unit2.addCondition(ConditionUnit.buildConditionUnit("table1", "a", new String[]{"3"}, ConditionUnitOperator.EQUAL));

        units.add(unit1);
        units.add(unit2);

        ExecutePlan plan =routeService.route(tables, units, "select", SqlType.SELECT);

        System.out.println(plan);
    }

    /*--------------------------------------------------single------------------------------------------------------*/

    @Test
    public void testBuildParameters() throws Exception {
        Map<Integer, ParameterCommand> commonds = new HashMap<Integer, ParameterCommand>();
        commonds.put(3, new ParameterCommand(ParameterMethod.setInt, new Object[]{3}, 3));
        commonds.put(1, new ParameterCommand(ParameterMethod.setInt, new Object[]{1}, 1));
        commonds.put(2, new ParameterCommand(ParameterMethod.setInt, new Object[]{2}, 2));
        List<Object> result = routeService.buildParameters(commonds);
        System.out.println(result);
        Assert.assertEquals(3, result.size());

        Assert.assertEquals(1, result.get(0));
        Assert.assertEquals(2, result.get(1));
        Assert.assertEquals(3, result.get(2));
    }
}