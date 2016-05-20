package jdbc;

import java.sql.SQLException;

import jdbc.frame.Jdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tongbanjie.baymax.datasource.BaymaxDataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/spring-context.xml")
public class ExplainTest {
	@Autowired
	private BaymaxDataSource dataSource;

	@Test
	public void test() throws SQLException, InterruptedException {
		test("explain select order_id, user_id from t_order where user_id = 1 or user_id = 2");

	}

	public void test(String sql) throws SQLException {
		new Jdbc(dataSource).executeSelect(sql).printSet().close();
	}

}
