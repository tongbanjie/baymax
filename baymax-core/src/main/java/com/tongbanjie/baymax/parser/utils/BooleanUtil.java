package com.tongbanjie.baymax.parser.utils;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.wall.spi.WallVisitorUtils;

/**
 * Created by sidawei on 16/3/17.
 *
 * 永真 永假条件判断
 */
public class BooleanUtil {
    /**
     * 判断条件是否永假的
     * @param expr
     * @return
     */
    public static boolean isConditionAlwaysFalse(SQLExpr expr) {
        Object o = WallVisitorUtils.getValue(expr);
        if(Boolean.FALSE.equals(o)) {
            return true;
        }
        return false;
    }

    public static boolean isConditionAlwaysTrue(SQLExpr expr) {
        Object o = WallVisitorUtils.getValue(expr);
        if(Boolean.TRUE.equals(o)) {
            return true;
        }
        return false;
    }
}
