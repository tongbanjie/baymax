package jdbc;

import com.tongbanjie.baymax.datasource.BaymaxDataSource;
import jdbc.frame.Jdbc;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/spring-context.xml")
public class AggTest {
	
	@Autowired
	private BaymaxDataSource dataSource;

    @Test
	public void test_00() throws SQLException, InterruptedException{
       new Jdbc(dataSource)
        .executeSelect("select sum(user_id) from t_order")
        .printSet().close();
	}

    @Test
    public void test_0() throws SQLException, InterruptedException{
        new Jdbc(dataSource)
                .executeSelect("select count(order_id),sum(order_id),min(order_id),max(order_id),avg(order_id) from t_order")
                .printSet().close();
    }

    @Test
    public void test() throws SQLException, InterruptedException{
        new Jdbc(dataSource)
                .executeSelect("select count(order_id),sum(order_id),min(order_id),max(order_id),avg(order_id),user_id from t_order group by user_id order by user_id asc")
                .printSet().close();
    }

}
