package com.tongbanjie.baymax.jdbc.merge.groupby;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 代表一行数据的key,可以是gorupby的值,相同key的要在以后合并
 */
public class GroupbyKey{

        List<Object> keys;

        public GroupbyKey(ResultSet set,List<String> groupbyColumns) throws SQLException {
            keys = new ArrayList<Object>(groupbyColumns.size());
            for (String column : groupbyColumns){
                keys.add(set.getObject(column));
            }
        }

        /**
         * 用于查找同一个组
         * @param o
         * @return
         */
        @Override
        public boolean equals(Object o) {
            if (o == null || !(o instanceof GroupbyKey)){
                return false;
            }
            List<Object> outKey = ((GroupbyKey)o).getKeys();
            for (Object item : keys){
                for (Object outItem : outKey){
                    if (!item.equals(outItem)){
                        return false;
                    }
                }
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hashcode = 0;
            for (Object item : keys){
                hashcode += item.hashCode();
            }
            return hashcode;
        }

        public List<Object> getKeys() {
            return keys;
        }
    }