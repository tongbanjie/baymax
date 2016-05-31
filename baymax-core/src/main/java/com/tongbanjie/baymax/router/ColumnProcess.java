package com.tongbanjie.baymax.router;

/**
 * Created by sidawei on 16/3/20.
 *
 * 列值的转换,如表oder配置了两个分区列,order_id,user_id, order_id中冗余了user_id的后4位.
 * 所以可以通过这个转换器,取出order_id或user_id中的user_id后4位,用于路由计算.
 *
 */
public interface ColumnProcess {

    /**
     * 列值的处理
     * @param columnValue sql中提取到的 原始的列值
     * @return 处理后的列值
     */
    Object apply(Object columnValue);

}
