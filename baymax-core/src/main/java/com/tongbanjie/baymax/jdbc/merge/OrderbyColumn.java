package com.tongbanjie.baymax.jdbc.merge;

import com.alibaba.druid.sql.ast.SQLOrderingSpecification;

/**
 * 排序字段
 */
public class OrderbyColumn {
    /**
     * 字段名称
     */
    private String          columnName;

    /**
     * 排序类型
     */
	private OrderbyType     orderbyType;

	public OrderbyColumn(String columnName, OrderbyType orderbyType) {
        this.columnName     =   columnName;
        this.orderbyType    =   orderbyType;
    }

    public static OrderbyType buildOrderbyType(SQLOrderingSpecification type){
        if (SQLOrderingSpecification.ASC == type){
            return OrderbyType.ASC;
        }
        if (SQLOrderingSpecification.DESC == type){
            return OrderbyType.DESC;
        }
        return OrderbyType.ASC;
    }

    public String getColumnName() {
        return columnName;
    }

    public OrderbyType getOrderbyType() {
        return orderbyType;
    }

    /**
     * 聚合函数合并类型
     */
    public enum OrderbyType{
            ASC,DESC;
    }

    @Override
    public String toString() {
        return columnName + "-" + orderbyType;
    }
}