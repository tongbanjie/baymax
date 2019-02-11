package jdbc;

import com.tongbanjie.baymax.datasource.BaymaxDataSource;
import jdbc.frame.Jdbc;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/spring-context.xml")
public class InsertTest {
	
	@Autowired
	private BaymaxDataSource dataSource;

    Jdbc jdbc;

    /**
     * 初始化数据＋事务
     * @throws SQLException
     * @throws InterruptedException
     */
    @Test
    public void test0() throws SQLException, InterruptedException {
        jdbc = new Jdbc(dataSource);
        jdbc.doInTransaction(new Jdbc.DoInTransaction() {
            @Override
            public void call() throws SQLException, InterruptedException {
                // order_id,user_id,product_id,product_name,status
                test(1000, 0, 1, "prodtct1", 1);
                test(1001,1,1,"prodtct1",1);
                test(1002,2,1,"prodtct1",1);
                test(1003,3,1,"prodtct1",1);

                test(2000,0,1,"prodtct1",1);
                test(2001,1,1,"prodtct1",1);
                test(2002,2,1,"prodtct1",1);
                test(2003,3,1,"prodtct1",1);

                test(3000,0,1,"prodtct1",1);
                test(3001,1,1,"prodtct1",1);
                test(3002,2,1,"prodtct1",1);
                test(3003, 3, 1, "prodtct1", 1);
            }
        });
        jdbc.close();
    }

    public void test(final int orderId, final int userId, final int productId, final String productName, final int status) throws SQLException, InterruptedException{
        int effctCount = jdbc.executeUpdate("INSERT INTO `t_order`(order_id, user_id, product_id, product_name, status) VALUES (?, ?, ?, ?, ?);", new Jdbc.PrepareSetting() {
            @Override
            public void set(PreparedStatement statement) throws SQLException {
                statement.setInt(1, orderId);
                statement.setInt(2, userId);
                statement.setInt(3, productId);
                statement.setString(4, productName);
                statement.setInt(5, status);
            }
        }).getEffectCount();
        System.out.println(effctCount);
    }

    /**
     * Insert参数不包含分区健测试
     * @throws SQLException
     * @throws InterruptedException
     */
    @Test
    public void test1() throws SQLException, InterruptedException {
        jdbc = new Jdbc(dataSource);
        int effctCount = jdbc.executeUpdate("INSERT INTO `t_order`(status) VALUES (1);").getEffectCount();
    }

    @Test
    public void update1() throws SQLException, InterruptedException {
        jdbc = new Jdbc(dataSource);
        int effctCount = jdbc.executeUpdate("update t_order set product_name = '001' where user_id=2").getEffectCount();
    }

    @Test
    public void updateAll() throws SQLException, InterruptedException {
        jdbc = new Jdbc(dataSource);
        int effctCount = jdbc.executeUpdate("update t_order set product_name = '002' ").getEffectCount();
    }
}
