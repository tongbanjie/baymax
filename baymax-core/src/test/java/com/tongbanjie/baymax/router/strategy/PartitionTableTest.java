package com.tongbanjie.baymax.router.strategy;

import com.tongbanjie.baymax.exception.BayMaxException;
import com.tongbanjie.baymax.parser.model.CalculateUnit;
import com.tongbanjie.baymax.parser.model.ConditionUnit;
import com.tongbanjie.baymax.parser.model.ConditionUnitOperator;
import com.tongbanjie.baymax.router.model.TargetTableEntity;
import com.tongbanjie.baymax.test.TableBuilder;
import com.tongbanjie.baymax.utils.Pair;
import junit.framework.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by sidawei on 16/3/21.
 */
public class PartitionTableTest {

    /**
     * Equals 测试
     * @throws Exception
     */
    @Test
     public void testExecute() throws Exception {
        PartitionTable table = new TableBuilder().appenTable("trade_order", "trade_order_{0}", "value % 4")
                                                 .appendNodeMapping("p0:0,1,2,3")
                                                 .appendColumn("user_id", null).toTable();

        CalculateUnit unit = new CalculateUnit();
        unit.addCondition(ConditionUnit.buildConditionUnit("trade_order", "user_id", new String[]{"4"}, ConditionUnitOperator.EQUAL));

        List<TargetTableEntity> target = table.execute(unit);
        System.out.println(target);
        Assert.assertEquals("p0", target.get(0).getTargetDB());
        Assert.assertEquals("trade_order_0", target.get(0).getTargetTable());
    }

    /**
     * IN 测试
     * @throws Exception
     */
    @Test
    public void testExecute_0() throws Exception {
        PartitionTable table = new TableBuilder()
                .appenTable("trade_order", "trade_order_{0}", "value % 4")
                .appendNodeMapping("p0:0,1")
                .appendNodeMapping("p1:2,3")
                .appendColumn("user_id", null)
                .toTable();

        CalculateUnit unit = new CalculateUnit();
        unit.addCondition(ConditionUnit.buildConditionUnit("trade_order", "user_id", new String[]{"1","2"}, ConditionUnitOperator.IN));

        List<TargetTableEntity> target = table.execute(unit);
        System.out.println(target);
        Assert.assertEquals("p0", target.get(0).getTargetDB());
        Assert.assertEquals("trade_order_1", target.get(0).getTargetTable());

        Assert.assertEquals("p1", target.get(1).getTargetDB());
        Assert.assertEquals("trade_order_2", target.get(1).getTargetTable());
    }

    /**
     * And 有同一个字段
     * @throws Exception
     */
    @Test
    public void testExecute_1() throws Exception {
        PartitionTable table = new TableBuilder()
                .appenTable("trade_order", "trade_order_{0}", "user_id % 4")
                .appendNodeMapping("p0:0,1,2,3")
                .appendColumn("user_id", null)
                .toTable();

        CalculateUnit unit = new CalculateUnit();
        unit.addCondition(ConditionUnit.buildConditionUnit("trade_order", "user_id", new String[]{"1"}, ConditionUnitOperator.EQUAL));
        unit.addCondition(ConditionUnit.buildConditionUnit("trade_order", "user_id", new String[]{"2"}, ConditionUnitOperator.EQUAL));

        try{
            List<TargetTableEntity> target = table.execute(unit);
        }catch (BayMaxException e){
            System.out.print(e);
            Assert.assertEquals("有多个相同列的Condition : user_id:[2]", e.getMessage());
        }
    }

    /**
     * And 有两个不用的分区列
     * @throws Exception
     */
    @Test
    public void testExecute_2() throws Exception {
        PartitionTable table = new TableBuilder()
                .appenTable("trade_order", "trade_order_{0}", "value % 4")
                .appendNodeMapping("p0:0,1,2,3")
                .appendColumn("id", null)
                .appendColumn("user_id", null)
                .toTable();

        CalculateUnit unit = new CalculateUnit();
        unit.addCondition(ConditionUnit.buildConditionUnit("trade_order", "id", new String[]{"2"}, ConditionUnitOperator.EQUAL));
        unit.addCondition(ConditionUnit.buildConditionUnit("trade_order", "user_id", new String[]{"1"}, ConditionUnitOperator.EQUAL));

        try{
            List<TargetTableEntity> target = table.execute(unit);
            System.out.println(target);
        }catch (BayMaxException e){
            Assert.assertEquals("有多个相同列的Condition : user_id:[2]", e.getMessage());
        }
    }

}