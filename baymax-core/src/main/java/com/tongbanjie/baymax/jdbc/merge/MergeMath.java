package com.tongbanjie.baymax.jdbc.merge;

import java.math.BigDecimal;

/**
 * Created by sidawei on 16/2/3.
 */
public class MergeMath {

    public static BigDecimal sum(BigDecimal o1, BigDecimal o2){
        if (o1 == null){
            o1 = new BigDecimal(0);
        }
        if (o2 != null){
            return o1.add(o2);
        }else {
            return o1;
        }
    }

    public static BigDecimal min(BigDecimal o1, BigDecimal o2) {
        if (o1 == null){
            return o1;
        }
        if (o2 == null){
            return o2;
        }
        return o1.compareTo(o2) < 0 ? o1 : o2;
    }

    public static BigDecimal max(BigDecimal o1, BigDecimal o2) {
        if (o1 == null){
            return o2;
        }
        if (o2 == null){
            return o1;
        }
        return o1.compareTo(o2) > 0 ? o1 : o2;
    }
}
