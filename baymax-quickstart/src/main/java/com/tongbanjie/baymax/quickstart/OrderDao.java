package com.tongbanjie.baymax.quickstart;

public interface OrderDao {


    /**
    * 主键查询
    * @param id
    * @return
    */
    Order findByPrimaryKey(Integer id);


    /**
    * 条件查询－数量
    * @param example
    * @return
    */
    Integer findCountByExample(Order example);

    /**
    * 插入
    * @param example
    * @return
    */
    Integer insert(Order example);

    /**
    * 主键删除
    * @param key
    * @return
    */
    Integer deleteByPrimaryKey(Integer key);

    /**
    * 主键更新
    * @param example
    * @return
    */
    Integer updateByPrimaryKey(Order example);

}