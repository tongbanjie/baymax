package com.tongbanjie.baymax.router.strategy;

import com.tongbanjie.baymax.router.ColumnProcess;

import java.util.Map;

/**
 * Created by sidawei on 16/3/20.
 */
public interface PartitionFunction {

    Integer execute(String columnValue, Map<String, Object> extention);

}
