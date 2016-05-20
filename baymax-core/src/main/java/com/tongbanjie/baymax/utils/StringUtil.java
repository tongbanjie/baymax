package com.tongbanjie.baymax.utils;

/**
 * Created by sidawei on 16/1/26.
 */
public class StringUtil {

    public static String removeBackquote(String str){
        //删除名字中的`tablename`和'value'
        if (str != null && str.length() > 0) {
            StringBuilder sb = new StringBuilder(str);
            if (sb.charAt(0) == '`'||sb.charAt(0) == '\'') {
                sb.deleteCharAt(0);
            }
            if (sb.charAt(sb.length() - 1) == '`'||sb.charAt(sb.length() - 1) == '\'') {
                sb.deleteCharAt(sb.length() - 1);
            }
            return sb.toString();
        }
        return "";
    }

    public static String removeDot(String str){
        if (str != null && str.indexOf(".") > 0){
            return removeBackquote(str.substring(str.lastIndexOf(".") + 1));
        }else {
            return removeBackquote(str);
        }
    }

    public static boolean contains(String[] array, String s){
        for (String temp : array){
            if (temp.equalsIgnoreCase(s)){
                return true;
            }
        }
        return false;
    }

}
