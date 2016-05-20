package com.tongbanjie.baymax.parser.model;

/**
 * 目前只支持SELECT,INSERT,UPDATE,DELETE,SELECT_FOR_UPDATE
 * @author dawei
 *
 */
public enum SqlType {
	 /** */
    SELECT(0),
    /** */
    INSERT(1),
    /** */
    UPDATE(2),
    /** */
    DELETE(3),
    /** */
    SELECT_FOR_UPDATE(4),
    /** */
    REPLACE(5),

    EXPLAIN(6),
    /** */
    OTHER(-1);

    private int i;

    private SqlType(int i){
        this.i = i;
    }

    public int value() {
        return this.i;
    }

    public static SqlType valueOf(int i) {
        for (SqlType t : values()) {
            if (t.value() == i) {
                return t;
            }
        }
        throw new IllegalArgumentException("Invalid SqlType:" + i);
    }
}
