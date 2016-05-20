package com.tongbanjie.baymax.router.strategy;

import java.util.Map;

/**
 * Created by sidawei on 16/3/20.
 */
public interface PartitionTableNodeMapping {

    Map<String/*suffix*/, String/*partition*/> getMapping();

}
