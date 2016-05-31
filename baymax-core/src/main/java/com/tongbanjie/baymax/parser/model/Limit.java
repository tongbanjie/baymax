package com.tongbanjie.baymax.parser.model;

/**
 * Created by sidawei on 16/4/11.
 *
 * limit
 */
public class Limit {

    private int offset;

    private int rows;

    public Limit(int offset, int rows) {
        this.offset = offset;
        this.rows = rows;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
