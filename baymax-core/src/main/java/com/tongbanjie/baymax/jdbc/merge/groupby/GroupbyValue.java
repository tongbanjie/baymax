package com.tongbanjie.baymax.jdbc.merge.groupby;

import com.tongbanjie.baymax.jdbc.merge.DataConvert;
import com.tongbanjie.baymax.jdbc.merge.MergeColumn;
import com.tongbanjie.baymax.jdbc.merge.orderby.OrderByComparetor;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by sidawei on 16/2/3.
 *
 * 代表一行数据的值, 对于聚合函数,有合并功能
 */
public class GroupbyValue implements OrderByComparetor.CompareEntity{
    /**
     * 一行中 每一列的值
     */
    private Object[] valus;

    private GroupbyMetaData metaData;

    /**
     * 上次get的列
     */
    private String lastColumn;

    private boolean wasNull;

    public GroupbyValue(ResultSet set, GroupbyMetaData metaData, Map<String, MergeColumn.MergeType> aggColumns) throws SQLException {
        this(set, metaData, aggColumns, null);
    }

    /**
     *
     * @param set       当前这行数据的来源
     * @param other     other!=null则合并
     * @throws SQLException
     */
    public GroupbyValue(ResultSet set, GroupbyMetaData metaData, Map<String, MergeColumn.MergeType> aggColumns, GroupbyValue other) throws SQLException {
        this.metaData = metaData;
        int size = metaData.getColumnCount();
        valus = new Object[size + 1];
        for (int i = 1; i< size + 1; i++){
            String columnLabel = metaData.getColumnLabel(i);
            if (aggColumns.containsKey(columnLabel)){
                // 聚合列
                // 注意：不管合不合并都放入,只要是聚合列都放入Bigdecimal,在get时再转换
                valus[i] = set.getBigDecimal(columnLabel);
                if(other != null && other.getValus() != null && other.getValus().length > 0){
                    // 合并
                    MergeColumn.MergeType mergeType = aggColumns.get(columnLabel);
                    if (mergeType == MergeColumn.MergeType.MERGE_AVG){
                        int sumlabel = metaData.getColumnIndex(columnLabel+"SUM");
                        int countlabel = metaData.getColumnIndex(columnLabel + "COUNT");

                        valus[i] = GroupbyAggMerger.mergeAvg(
                                (BigDecimal)valus[sumlabel],            (BigDecimal)valus[countlabel],
                                (BigDecimal)other.getValus()[sumlabel], (BigDecimal)other.getValus()[countlabel],
                                mergeType);
                    }else {
                        valus[i] = GroupbyAggMerger.merge((BigDecimal)valus[i], (BigDecimal)other.getValus()[i], mergeType);
                    }
                }
            }else {
                // 非聚合列
                valus[i] = set.getObject(columnLabel);
            }
        }
    }

    public Object[] getValus() {
        return valus;
    }

    public <T> T getValue(int index, Class<T> type){
        this.lastColumn = metaData.getColumnLabel(index);
        T result = (T)DataConvert.convertValue(valus[index], type);
        this.wasNull = result == null;
        return result;
    }

    public <T> T getValue(String columnLabel, Class<T> type){
        this.lastColumn = columnLabel;
        T result = (T)DataConvert.convertValue(valus[metaData.getColumnIndex(columnLabel)], type);
        this.wasNull = result == null;
        return result;
    }

    /**
     * for compare
     * @param columnName
     * @return
     * @throws SQLException
     */
    @Override
    public Object getValue(String columnName) throws SQLException {
        return this.getValue(columnName, Object.class);
    }

    public String getLastColumn() {
        return lastColumn;
    }

    public boolean wasNull() {
        return wasNull;
    }
}
