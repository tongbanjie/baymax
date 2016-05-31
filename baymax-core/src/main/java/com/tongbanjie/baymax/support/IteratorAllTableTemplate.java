package com.tongbanjie.baymax.support;

import com.tongbanjie.baymax.router.model.TargetTableEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sidawei on 16/4/5.
 *
 * 全表数据遍历模版类
 * TODO 未完成
 */
public class IteratorAllTableTemplate {

    private static ThreadLocal<TargetTableEntity> local = new ThreadLocal<TargetTableEntity>();

    public static void iterator(String tableName, Callbal call){

        List<TargetTableEntity> tables = ManualRoute.getAllTables(tableName);

        if (tables == null || tables.size() == 0){
            return;
        }

        // 获取所有表名
        for (int i = 0; i < tables.size(); i++) {

            TargetTableEntity table = tables.get(i);

            /**
             * 设定之后Sql的路由结果
             */
            local.set(table);
            try{
                call.call(i, tables.size(), table.getTargetDB(), table.getTargetTable());
            }finally {
                /**
                 * 清除之前的设定
                 */
                local.remove();
            }
        }
    }

    interface Callbal{
        /**
         * 循环所有的表，每个表调用一次，并且在ThreadLocal中放值路由结果
         * @param index     当前循环到第几个表了
         * @param size      一共有几个表
         * @param dbName    当前表所在的数据库DataSource标示
         * @param tableName 当前表的物理表名
         */
        void call(int index, int size, String dbName, String tableName);
    }

}




