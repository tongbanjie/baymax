package com.tongbanjie.baymax.jdbc.merge.orderby;

import com.tongbanjie.baymax.jdbc.merge.OrderbyColumn;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by sidawei on 16/2/3.
 */
public class OrderByComparetor{

    private List<OrderbyColumn> orderbyColumns;

    public OrderByComparetor(List<OrderbyColumn> orderbyColumns){
        this.orderbyColumns = orderbyColumns;
    }

    /**
     * ASC:
     * e1 > e2 ==> 1
     * e1 < e2 ==> -1
     * e1 = e2 ==> 0
     *
     * DESC:
     * asc * -1
     * @param e1
     * @param e2
     * @return
     * @throws SQLException
     */
    public int compare(CompareEntity e1, CompareEntity e2) throws SQLException {
        for (OrderbyColumn c : orderbyColumns){
            Object c1 = e1.getValue(c.getColumnName());
            Object c2 = e2.getValue(c.getColumnName());

            int ret = 0;

            if (c1 == null && c2 == null){
                ret = 0;
            }else if (c1 == null){
                ret = -1;
            }else if (c2 == null){
                ret = 1;
            }else {
                if (c1 instanceof Number){
                    double d1 = ((Number) c1).doubleValue();
                    double d2 = ((Number) c2).doubleValue();

                    if (d1 > d2){
                        ret = 1;
                    }else if (d1 < d2){
                        ret = -1;
                    }
                } else if (c1 instanceof Date){
                    // java.sql.Date;Time;Timestamp 都是java.util.Date的之类
                    ret = ((Date) c1).compareTo((Date)c2);
                }else if (c1 instanceof String){
                    ret = ((String)c1).compareTo((String)c2);
                }
            }

            if (ret != 0){
                if (c.getOrderbyType() == OrderbyColumn.OrderbyType.DESC){
                    return ret * -1;
                }else {
                    return ret;
                }
            }
        }
        return 0;
    }

    public interface CompareEntity{
        Object getValue(String columnName) throws SQLException;
    }
}
