package com.tongbanjie.baymax.parser.utils;

import com.tongbanjie.baymax.parser.model.SqlType;

/**
 * Created by sidawei on 16/1/27.
 */
public class SqlTypeUtil {

    public static SqlType getSqlType(String sql) {
        if(sql == null || sql.length() == 0) {
            return null; // null表示无需解析
        }
        while(true){
            if(sql.startsWith("(")){
                sql = sql.substring(1).trim();
            }else{
                break;
            }
        }
        sql = sql.trim();
        sql = sql.toLowerCase();
        SqlType sqlType = SqlType.OTHER;
        if(sql.startsWith("select")){
            sqlType = SqlType.SELECT;
        }else if(sql.startsWith("update")){
            sqlType = SqlType.UPDATE;
        }else if(sql.startsWith("insert")){
            sqlType = SqlType.INSERT;
        }else if(sql.startsWith("delete")){
            sqlType = SqlType.DELETE;
        }else if(sql.startsWith("explain")){
            sqlType = SqlType.EXPLAIN;
        }
        return sqlType;
    }
}
