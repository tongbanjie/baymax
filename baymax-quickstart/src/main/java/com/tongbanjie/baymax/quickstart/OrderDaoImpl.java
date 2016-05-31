package com.tongbanjie.baymax.quickstart;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class OrderDaoImpl extends SqlSessionDaoSupport implements OrderDao {

	private final String namaspace = "com.tongbanjie.baymax.quickstart.Order";


	/**
	 * 主键查询
	 *
	 * @param id
	 * @return
	 */
	public Order findByPrimaryKey(Integer id) {
		return (Order) getSqlSession().selectOne(this.namaspace + ".findByPrimaryKey", id);
	}

	/**
	 * 条件查询－数量
	 *
	 * @param example
	 * @return
	 */
	public Integer findCountByExample(Order example) {
		Map map = new HashMap();
		map.put("example", example);
		return (Integer) getSqlSession().selectOne(this.namaspace + ".findCountByExample", map);
	}

	/**
	 * 插入
	 *
	 * @param example
	 * @return
	 */
	public Integer insert(Order example) {
		return getSqlSession().insert(this.namaspace + ".insert", example);
	}

	/**
	 * 主键删除
	 *
	 * @param key
	 * @return
	 */
	public Integer deleteByPrimaryKey(Integer key) {
		return getSqlSession().delete(this.namaspace + ".deleteByPrimaryKey", key);
	}

	/**
	 * 主键更新
	 *
	 * @param example
	 * @return
	 */
	public Integer updateByPrimaryKey(Order example) {
		return getSqlSession().update(this.namaspace + ".updateByPrimaryKey", example);
	}

}