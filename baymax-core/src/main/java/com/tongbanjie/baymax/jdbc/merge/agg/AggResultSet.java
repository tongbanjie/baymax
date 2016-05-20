package com.tongbanjie.baymax.jdbc.merge.agg;

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
        return AggMerger.merge(getResultSet(), mergeColumns, columnLabel, type);
    }

    @Override
    public <T> T merge(int columnIndex, Class<T> type) throws SQLException {
        return merge(mergeColumnsIndex.get(columnIndex), type);
    }

}
