package com.tongbanjie.baymax.jdbc.merge;

import com.tongbanjie.baymax.exception.BayMaxException;

import java.math.BigDecimal;

/**
 * Created by sidawei on 16/1/31.
 */
public class DataConvert {

    public static Object convertValue(final Object value, final Class<?> convertType) {
        if (value instanceof Number) {
            return convertNumberValue(value, convertType);
        } else {
            if (String.class.equals(convertType)) {
                return value.toString();
            } else {
                return value;
            }
        }
    }

    private static Object convertNumberValue(final Object value, final Class<?> convertType) {
        Number number = (Number) value;
        String name = convertType.getName();
        if ("int".equals(name)){
            return number.intValue();
        }else if ("long".equals(name)){
            return number.longValue();
        }else if ("double".equals(name)){
            return number.doubleValue();
        }else if ("float".equals(name)){
            return number.floatValue();
        }else if ("java.math.BigDecimal".equals(name)){
            if (number instanceof BigDecimal) {
                return number;
            } else {
                return new BigDecimal(number.toString());
            }
        }else if (String.class.getName().equals(name)){
            return number.toString();
        }else if (Object.class.getName().equals(name)){
            return number;
        }
        throw new BayMaxException("Unsupported data type:" + convertType);
    }
}
