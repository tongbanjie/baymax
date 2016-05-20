package com.tongbanjie.baymax.jdbc.merge.orderby;

import com.tongbanjie.baymax.jdbc.TStatement;
import com.tongbanjie.baymax.jdbc.merge.OrderbyColumn;
import com.tongbanjie.baymax.jdbc.merge.iterator.IteratorResultSetGetterAdapter;
import com.tongbanjie.baymax.router.model.ExecutePlan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by sidawei on 16/1/31.
 */
public class OrderByResultSet extends IteratorResultSetGetterAdapter {

    List<OrderbyColumn> orderbyColumns;

    /**
     * 他代表了List<ResultSet>中 每一个下标对应的ResultSet,在上一轮循环中是否被取值;
     * false：被取值,下一轮循环需要调用next();
     * true:未被取值,下一轮循环无需调用next();
     */
    private boolean[] noNext;

    /**
     * 对应下标的ResultSet是否没有下一行了;
     */
    private boolean[] isends;

    private int lastIndex = 0;

    public OrderByResultSet(List<ResultSet> listResultSet, TStatement statement, ExecutePlan plan) {
        super(listResultSet, statement, plan);
        orderbyColumns = plan.getOrderbyColumns();
        noNext = new boolean[listResultSet.size()];
        isends = new boolean[listResultSet.size()];
    }

    @Override
    public boolean nextRow() throws SQLException {
        List<ResultSet> sets = getResultSet();
        if (sets == null) {
            return false;
        }

        // 冒泡;根据ASC,DESC找出最大或者最小的那个ResultSet
        OrderByCompareUnit compareUnit = null;
        for (int i = 0; i< noNext.length; i++){
            if (isends[i]){
                continue;
            }
            // 从上一个循环是否已经把值取走 判断这次循环是否要调用next()
            ResultSet set = getResultSet().get(i);
            if (!noNext[i]){
                if (!set.next()){
                    isends[i] = true;
                    continue;
                }
            }
            // 从ResultSet中取值比较
            if (compareUnit == null){
                compareUnit = new OrderByCompareUnit(set, i, orderbyColumns);
            }else {
                OrderByCompareUnit temp = new OrderByCompareUnit(set, i, orderbyColumns);
                if (temp.compareTo(compareUnit) < 0){
                    compareUnit = temp;
                }
            }
        }

        // 没有可比较的行了
        if (compareUnit == null){
            return false;
        }

        // 设置对应的ResultSet在下轮循环中无需next();
        for (int i = 0; i < noNext.length; i++){
            if (i == compareUnit.getIndex()){
                noNext[i] = false;
                super.currentResultSet = getResultSet().get(i);
                this.lastIndex = i;
            }else {
                noNext[i] = true;
            }
        }
        return true;
    }

    @Override
    public boolean needEscape() {
        if (this.lastIndex == 0){
            return false;
        }
        return true;
    }
}
