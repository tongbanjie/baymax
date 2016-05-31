package com.tongbanjie.baymax.quickstart.c1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author sunyi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/c1/spring-context.xml")
public class QuickStartC1 {

	// DDL 语句在 ddl.sql 里，有 4 个表
	//
	// order_00, order_02 在 数据源 ds_0 中
	// order_01, order_03 在 数据源 ds_1 中
	//
	// 举例根据 ID 取余来分表
	// order_{m} , m = id % 4


	@Resource(name = "multipleDataSource")
	private DataSource multipleDataSource;

	@Test
	public void insert() {

		try {

			Connection connection = multipleDataSource.getConnection();
			Statement statement = connection.createStatement();


			// 先清理数据
			String delSql = "delete from `order` where id in (10000,10001,10002,10003)";
			int del = statement.executeUpdate(delSql);
			System.out.println(del);

			String commonSql = "insert into `order` (id, product_id, product_name, user_id, amount, create_time , modify_time) values ";

			String sql0 = commonSql + " (10000,'a1','商品A1', 0 , 1000,now(),now())";
			int i0 = statement.executeUpdate(sql0);
			System.out.println(i0);


			String sql1 = commonSql + " (10001,'a1','商品A1', 1 , 1000,now(),now())";
			int i1 = statement.executeUpdate(sql1);
			System.out.println(i1);


			String sql2 = commonSql + " (10002,'a1','商品A1', 2 , 1000,now(),now())";
			int i2 = statement.executeUpdate(sql2);
			System.out.println(i2);


			String sql3 = commonSql + " (10003,'a1','商品A1', 3 , 1000,now(),now())";
			int i3 = statement.executeUpdate(sql3);
			System.out.println(i3);

			// 现在 4 个表里， 每个表都有一条记录

			// 这个例子需主要看下  resource/spring/c1/spring-baymax.xml 配置

			// 为了防止数据对后续测试的影响， 可以将数据删除

			// delete from ds_0.order_00;
			// delete from ds_1.order_01;
			// delete from ds_0.order_02;
			// delete from ds_1.order_03;

		} catch (SQLException e) {
			e.printStackTrace();
		}


	}


}
