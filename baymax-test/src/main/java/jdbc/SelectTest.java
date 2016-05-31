package jdbc;

import com.tongbanjie.baymax.datasource.BaymaxDataSource;
import jdbc.frame.Jdbc;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;

/**
 * Created by sidawei on 16/4/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/spring-context.xml")
public class SelectTest {

    @Autowired
    private BaymaxDataSource dataSource;

    @Test
    public void test() throws SQLException, InterruptedException{
        // or
        test("select order_id, user_id from t_order where user_id = 1 or user_id = 2");

        // (or)and
        //test("select order_id, user_id from t_order where (user_id = 1 or user_id = 2) and product_name='prodtct1' ");

        // union的限制 (分表)union(单表)  且分表的路由结果是单库单表，且和另外一个被union的表在同一个库上
        //test("(select order_id, user_id from t_order where user_id = 1) union (select order_id, user_id from t_order_0)");

        // or 全表扫描
        //test("select order_id, user_id, status from t_order where user_id = 1 or status = 1 order by user_id");

        // limit
        //test("select order_id, user_id from t_order");
        //test("select order_id, user_id from t_order limit 3, 1");

        // limit order by
        //test("select order_id, user_id from t_order order by order_id");
        //test("select order_id, user_id from t_order order by order_id limit 3,1");

        // limit group by
        //test("select user_id from t_order group by user_id order by user_id");
        //test("select user_id from t_order group by user_id order by user_id limit 3,1");

        // limit agg
        //test("select sum(user_id) from t_order");
        //test("select sum(user_id) from t_order");

        // limit agg group by order by
        //test("select user_id,sum(user_id) from t_order group by user_id order by user_id");
        //test("select user_id,sum(user_id) from t_order group by user_id order by user_id limit 3,2");

        // in
        test("select * from t_order where user_id in (2, 3)");


    }

    public void test(String sql) throws SQLException {
        System.out.println("开始");
        long start = System.currentTimeMillis();
        new Jdbc(dataSource).executeSelect(sql).printSet().close();
        System.out.println("耗时:" + (System.currentTimeMillis() - start));
    }

}
