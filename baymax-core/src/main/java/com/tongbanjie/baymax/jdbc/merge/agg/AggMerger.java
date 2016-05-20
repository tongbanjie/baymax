package com.tongbanjie.baymax.jdbc.merge.agg;

import com.tongbanjie.baymax.jdbc.merge.DataConvert;
import com.tongbanjie.baymax.jdbc.merge.MergeColumn;
import com.tongbanjie.baymax.jdbc.merge.MergeMath;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by sidawei on 16/2/3.
 */
public class AggMerger extends MergeMath{

    /**
     * @param columnLabel
     * @param type
     * @param <T>
     * @return
     * @throws SQLException
     */
    public static  <T> T merge(List<ResultSet> sets, Map<String/*columnName*/, MergeColumn.MergeType> mergeColumns, String columnLabel, Class<T> type) throws SQLException {
        Object value = null;
        MergeColumn.MergeType mergeType = mergeColumns.get(columnLabel);
        switch (mergeType) {
            case MERGE_COUNT:
            case MERGE_SUM:
                value = AggMerger.mergeSum(sets, columnLabel, type);
                break;
            case MERGE_MIN:
                value = AggMerger.mergeMin(sets, columnLabel);
                break;
            case MERGE_MAX:
                value = AggMerger.mergeMax(sets, columnLabel);
                break;
            case MERGE_AVG:
                value = AggMerger.mergeAvg(sets, columnLabel + "SUM", columnLabel + "COUNT", type);
                break;
        }
        if (value == null){
            return null;
        }
        return (T) DataConvert.convertValue(value, type);
    }


    /**
     * 合并Count,Sum
     * @param sets
     * @param alias
     * @return
     * @throws SQLException
     */
    public static Object mergeSum(List<ResultSet> sets, String alias, Class<?> type) throws SQLException {
        BigDecimal number = null;
        for (int i = 0; i < sets.size(); i++){
            BigDecimal value = sets.get(i).getBigDecimal(alias);
            number = sum(number, value);
        }
        return DataConvert.convertValue(number, type);
    }

    /**
     * 合并Min
     * @param sets
     * @param alias
     * @return
     * @throws SQLException
     */
    public static Object mergeMin(List<ResultSet> sets, String alias) throws SQLException {
        BigDecimal number = null;
        for (int i = 0; i < sets.size(); i++){
            BigDecimal value = sets.get(i).getBigDecimal(alias);
            number = min(number, value);
        }
        return number;
    }

    /**
     * 合并Max
     * @param sets
     * @param alias
     * @return
     * @throws SQLException
     */
    public static Object mergeMax(List<ResultSet> sets, String alias) throws SQLException {
        BigDecimal number = null;
        for (int i = 0; i < sets.size(); i++){
            BigDecimal value = sets.get(i).getBigDecimal(alias);
            number = max(number, value);
        }
        return number;
    }

    /**
     * 合并Avg
     * @param sets
     * @param aliasSum
     * @param aliasCount
     * @return
     * @throws SQLException
     */
    public static Object mergeAvg(List<ResultSet> sets, String aliasSum, String aliasCount, Class<?> type) throws SQLException {

        BigDecimal sum = (BigDecimal) mergeSum(sets, aliasCount, type);
        BigDecimal count = (BigDecimal) mergeSum(sets, aliasSum, type);

        if (sum != null && count != null){
            BigDecimal number = sum.divide(count, 20, BigDecimal.ROUND_HALF_UP);
            return DataConvert.convertValue(number, type);
        }
        return null;
    }
}
