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
public class OrderbyTest {
	
	@Autowired
	private BaymaxDataSource dataSource;

    @Test
	public void test() throws SQLException, InterruptedException{
        // asc desc
        test("select order_id,user_id from t_order t order by user_id asc, t.order_id asc");

        // where
        test("select order_id,user_id from t_order t  where user_id = 2 order by user_id asc");

	}

    public void test(String sql) throws SQLException {
        new Jdbc(dataSource).executeSelect(sql).printSet().close();
    }

}
