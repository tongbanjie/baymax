package com.tongbanjie.baymax.support.column;

import com.tongbanjie.baymax.router.ColumnProcess;

/**
 * Created by sidawei on 16/3/20.
 */
public class SubLeftColumnProcess implements ColumnProcess {

    private int length;

    @Override
    public Object apply(Object columnValue) {
        String s = String.valueOf(columnValue);
        if(s.length() > length){
            s = s.substring(0, length);
        }
        return s;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
