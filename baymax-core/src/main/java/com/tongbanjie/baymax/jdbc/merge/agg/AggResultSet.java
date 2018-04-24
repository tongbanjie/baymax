package com.tongbanjie.baymax.jdbc.merge.agg;

import com.tongbanjie.baymax.exception.BayMaxException;
import com.tongbanjie.baymax.jdbc.TStatement;
import com.tongbanjie.baymax.jdbc.merge.MergeColumn;
import com.tongbanjie.baymax.router.model.ExecutePlan;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by sidawei on 16/1/31.
 */
public class AggResultSet extends AggResultSetGetterAdapter {

    Map<String/*columnName*/, MergeColumn.MergeType> mergeColumns;

    Map<Integer/*columnIndex*/, String/*columnName*/> mergeColumnsIndex;

    Map<String/*columnName*/, Boolean/*wasNull*/> wasNull;

    public AggResultSet(List<ResultSet> listResultSet, TStatement statement, ExecutePlan plan) throws SQLException {
        super(listResultSet, statement, plan);
        mergeColumns = plan.getMergeColumns();
        initMergeColumnIndex(currentResultSet.getMetaData());
        // TODO index转化为alias
    }

    private void initMergeColumnIndex(ResultSetMetaData metaData) throws SQLException {
        mergeColumnsIndex = new HashMap<Integer, String>();
        int size = metaData.getColumnCount();
        for (int i = 1; i <= size; i++){
            String name = metaData.getColumnLabel(i);
            if (mergeColumns.containsKey(name)){
                mergeColumnsIndex.put(i, name);
            }
        }
        this.wasNull = new HashMap<String, Boolean>();
    }

    /**
     * 先考虑没有groupby 只有一行的时候
     * @return
     * @throws SQLException
     */
    @Override
    public boolean nextRow() throws SQLException {
        List<ResultSet> sets = getResultSet();
        if (sets == null) {
            return false;
        }
        boolean hasNext = false;
        Iterator<ResultSet> ite = sets.iterator();
        while (ite.hasNext()){
            ResultSet set = ite.next();
            if (!set.next()){
                ite.remove();
            }else {
                hasNext = true;
            }
        }
        return hasNext;
    }

    @Override
    public boolean needEscape() {
        return false;
    }

    /*------------------*/

    @Override
    public boolean isAggColumn(String name){
        return mergeColumns.containsKey(name);
    }

    @Override
    public boolean isAggColumn(int index){
        return mergeColumnsIndex.containsKey(index);
    }

    @Override
    public <T> T merge(String columnLabel, Class<T> type) throws SQLException {
        T result = AggMerger.merge(getResultSet(), mergeColumns, columnLabel, type);
        wasNull.put(columnLabel, result == null);
        return result;

    }

    @Override
    public <T> T merge(int columnIndex, Class<T> type) throws SQLException {
        T result = AggMerger.merge(getResultSet(), mergeColumns, mergeColumnsIndex.get(columnIndex), type);
        wasNull.put(mergeColumnsIndex.get(columnIndex), result == null);
        return result;
    }

    @Override
    public boolean wasNull() throws SQLException {
        if(lastColumn.getObject1() != null && mergeColumns.containsKey(lastColumn.getObject1())){
            return wasNull(lastColumn.getObject1());
        }
        if (lastColumn.getObject2() != null && mergeColumnsIndex.containsKey(lastColumn.getObject2())){
            return wasNull(mergeColumnsIndex.get(lastColumn.getObject2()));
        }
        return super.wasNull();
    }

    private boolean wasNull(String columnName){
        Boolean n = wasNull.get(columnName);
        if (n == null){
            throw new BayMaxException("调用 wasNull 前，必须先get!");
        }
        return n;
    }
}
