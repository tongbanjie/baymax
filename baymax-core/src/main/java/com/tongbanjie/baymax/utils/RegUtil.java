package com.tongbanjie.baymax.utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegUtil {
	
	public static String findFirst(String source, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(source);
        ArrayList<String> strs = new ArrayList<String>();
        while (m.find()) {
            strs.add(m.group(1));            
        } 
        for (String s : strs){
            System.out.println(s);
        }      
        return strs.get(0);
	}

}
