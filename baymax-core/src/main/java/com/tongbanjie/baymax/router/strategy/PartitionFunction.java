package com.tongbanjie.baymax.router.strategy;

import java.util.Map;

/**
 * Created by sidawei on 16/3/20.
 *
 * 路由函数,实现可以是一致性hash,Mode,el表达式等
 */
public interface PartitionFunction {

    /**
     * 路由计算入口
     * @param columnValue sql中提取到的值,并且已经经过了ColumnProcess的处理
     * @param extension     暂时未使用,给后面预留,如读写分离等
     * @return
     */
    Integer execute(String columnValue, Map<String, Object> extension);

}
