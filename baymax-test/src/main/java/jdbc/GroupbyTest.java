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
public class GroupbyTest {
	
	@Autowired
	private BaymaxDataSource dataSource;

    @Test
	public void test() throws SQLException, InterruptedException{

       test("select user_id  from t_order t group by user_id");
       test("select user_id  from t_order t group by user_id order by user_id desc");
       test("select user_id  from t_order t where order_id = 1 group by user_id order by user_id desc");
	}

    public void test(String sql) throws SQLException {
        new Jdbc(dataSource).executeSelect(sql).printSet().close();
    }

}
