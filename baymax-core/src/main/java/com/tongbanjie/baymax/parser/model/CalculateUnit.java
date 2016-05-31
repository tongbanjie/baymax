package com.tongbanjie.baymax.parser.model;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by sidawei on 16/1/26.
 *
 * 一个CalculateUnit表示的是一个计算单元
 * 一个计算单元表示的是经过等式转化后的and相连的一组条件
 * 多个计算单元(多组条件)之间用or相连
 * (A and B)or(C and D)
 */
public class CalculateUnit {

    /**
     * set中不会有相同的column 否则会报错
     * 即一个计算单元内的条件都是用and连接的,所以不可能有 a=1 and a=2
     */
    private Map<String/*table*/, Set<ConditionUnit/*column value*/>> tablesAndConditions = new LinkedHashMap<String, Set<ConditionUnit>>();

    /**
     * get
     * @return
     */
    public Map<String, Set<ConditionUnit>> getTablesAndConditions() {
        return tablesAndConditions;
    }

    /**
     * 表名，列名 相同的要合并
     */
    public void addCondition(ConditionUnit conditionUnit) {
        //String tableName, String columnName, Object[] values
        if (conditionUnit == null){
            return;
        }

        // 同一个计算单元的所有条件
        Set<ConditionUnit> conditionUnits = tablesAndConditions.get(conditionUnit.getTable());

        if (conditionUnits == null) {
            conditionUnits = new LinkedHashSet<ConditionUnit>();
            tablesAndConditions.put(conditionUnit.getTable(), conditionUnits);
        }

        conditionUnits.add(conditionUnit);
    }

}
