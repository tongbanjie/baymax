package com.tongbanjie.baymax.jdbc;

import com.tongbanjie.baymax.parser.model.Limit;
import com.tongbanjie.baymax.router.model.ExecutePlan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by sidawei on 16/4/11.
 */
public abstract class TResultSetLimit extends TResultSet {


    private Limit limit;

    private boolean skiped;

    private int read;

    public TResultSetLimit(List<ResultSet> listResultSet, TStatement statement, ExecutePlan plan) {
        super(listResultSet, statement, plan);
        this.limit = plan.getLimit();
    }

    @Override
    public boolean next() throws SQLException {
        if (limit == null ){
            return nextRow();
        }
        if (!skiped){
            skip();
        }
        if (++read <= limit.getRows()){
            if (nextRow()){
                return true;
            }
        }
        return false;
    }

    private void skip(){
        for (int i = 0; i < limit.getOffset(); i++) {
            try {
                if (!nextRow()){
                    break;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        skiped = true;
    }

    public abstract boolean nextRow() throws SQLException;

}
