package com.tongbanjie.baymax.parser.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 计算条件 代表 column = value
 */
public class ConditionUnit {
    // 表名
    private String table;
    // 列名
    private String column;
    // 字段的值 有可能是in等 所以会有多个
    private List<String> values;
    // 条件操作符
    private ConditionUnitOperator operator;

    public ConditionUnit(){

    }

    /**
     * 构建一个条件
     * @param table0
     * @param column0
     * @param values0
     * @param operator0
     * @return
     */
    public static ConditionUnit buildConditionUnit(String table0, String column0, Object[] values0, ConditionUnitOperator operator0){
        if (values0 == null || values0.length == 0){
            return null;
        }
        ConditionUnit unit = new ConditionUnit();
        unit.table = table0;
        unit.column = column0;
        unit.operator = operator0;
        unit.values = new ArrayList(values0.length);
        for (Object value : values0){
            if (value != null){
                // TODO 是否考虑类型转换
                unit.values.add(value.toString());
            }
        }
        if (values0.length == 0){
            return null;
        }
        return unit;
    }

    @Override
    public int hashCode() {
        return column.hashCode() + values.hashCode() + operator.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        ConditionUnit o = (ConditionUnit)object;
        return  table.equals(o.getTable())
                && column.equals(o.getColumn())
                && values.equals(o.getValues())
                && operator == o.operator;
    }

    @Override
    public String toString() {
        return column + ":" + values;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public ConditionUnitOperator getOperator() {
        return operator;
    }

    public void setOperator(ConditionUnitOperator operator) {
        this.operator = operator;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
