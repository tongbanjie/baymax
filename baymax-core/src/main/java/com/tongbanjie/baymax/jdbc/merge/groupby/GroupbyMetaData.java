package com.tongbanjie.baymax.jdbc.merge.groupby;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sidawei on 16/2/3.
 */
public class GroupbyMetaData {

    private int columnCount = 0;

    private Map<String/*columnLabel*/, Integer/*columnIndex*/> labelIndexMap;
    private String/*columnLabel*/[/*columnIndex*/] indexLabelMap;

    public GroupbyMetaData(ResultSetMetaData metaData) throws SQLException {
        columnCount = metaData.getColumnCount();
        labelIndexMap = new HashMap<String, Integer>(columnCount);
        indexLabelMap = new String[columnCount + 1];
        String label;
        for (int i = 1; i <= columnCount; i++){
            label = metaData.getColumnLabel(i);
            labelIndexMap.put(label, i);
            indexLabelMap[i] = label;
            label = null;
        }
    }

    public int getColumnCount() {
        return columnCount;
    }

    public String getColumnLabel(int i) {
        return indexLabelMap[i];
    }

    public int getColumnIndex(String columnLabel){
        return labelIndexMap.get(columnLabel);
    }
}
