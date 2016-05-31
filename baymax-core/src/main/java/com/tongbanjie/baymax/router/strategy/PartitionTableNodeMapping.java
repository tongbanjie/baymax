package com.tongbanjie.baymax.router.strategy;

import java.util.Map;

/**
 * Created by sidawei on 16/3/20.
 *
 * 当分区函数计算出表的后缀后，通过这个mapping返回这个后缀的表所在的真实Datasource的名称。
 */
public interface PartitionTableNodeMapping {

    Map<String/*suffix*/, String/*partition*/> getMapping();

}
