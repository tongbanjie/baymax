package com.tongbanjie.baymax.support;

import com.tongbanjie.baymax.parser.model.CalculateUnit;
import com.tongbanjie.baymax.parser.model.ConditionUnit;
import com.tongbanjie.baymax.parser.model.ConditionUnitOperator;
import com.tongbanjie.baymax.router.model.TargetTableEntity;
import com.tongbanjie.baymax.router.strategy.PartitionTable;

import java.util.List;
import java.util.Map;

/**
 * Created by sidawei on 16/4/2.
 *
 * 这个类用来实现Dao层的半自动路由
 *
 */
public class ManualRoute {

    /**
     * 返回路由结果
     * @param tableName
     * @param parameters
     * @return
     */
    public static List<TargetTableEntity> route(String tableName, Map<String, Object> parameters){
        PartitionTable table = BaymaxContext.getPartitionTable(tableName);
        if (table == null){
            return null;
        }
        CalculateUnit unit = new CalculateUnit();
        if (parameters != null && !parameters.isEmpty()){
            for (Map.Entry entry : parameters.entrySet()){
                ConditionUnit conditionUnit = ConditionUnit.buildConditionUnit(tableName, entry.getKey().toString(), new Object[]{entry.getValue()}, ConditionUnitOperator.EQUAL);
                unit.addCondition(conditionUnit);
            }
        }
        return table.execute(unit);
    }

    /**
     *
     * 返回所有的表
     * @param tableName
     * @return
     */
    public static List<TargetTableEntity> getAllTables(String tableName){
        return BaymaxContext.getPartitionTable(tableName).getAllTableNames();
    }

}
