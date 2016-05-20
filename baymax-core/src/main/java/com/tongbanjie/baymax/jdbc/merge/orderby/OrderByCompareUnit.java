package com.tongbanjie.baymax.jdbc.merge.orderby;

import com.tongbanjie.baymax.exception.BayMaxException;
import com.tongbanjie.baymax.jdbc.merge.OrderbyColumn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by sidawei on 16/2/2.
 *
 * TODO 测试 null,所有字段都相等,Date
 */
public class OrderByCompareUnit implements Comparable{

    private List<OrderbyColumn> orderbyColumns;

    private ResultSet set;

    /**
     * 当前ResultSet的下标
     */
    private int index;

    public OrderByCompareUnit(ResultSet set, int index, List<OrderbyColumn> orderbyColumns){
        this.set = set;
        this.index = index;
        this.orderbyColumns = orderbyColumns;
    }

    @Override
    public int compareTo(final Object o) {
        if (o == null){
            return 1;
        }
        OrderByComparetor comparetor = new OrderByComparetor(orderbyColumns);
        try {
            return comparetor.compare(new OrderByComparetor.CompareEntity() {
                @Override
                public Object getValue(String columnName) throws SQLException {
                    return set.getObject(columnName);
                }
            }, new OrderByComparetor.CompareEntity() {
                @Override
                public Object getValue(String columnName) throws SQLException {
                    return ((OrderByCompareUnit)o).set.getObject(columnName);
                }
            });
        } catch (SQLException e) {
            throw new BayMaxException("Order by 排序失败, 不能识别的字段类型:" + o.getClass() + ";只能接收Number,Date和String类型.", e);
        }
    }

    public ResultSet getSet() {
        return set;
    }

    public int getIndex() {
        return index;
    }
}
