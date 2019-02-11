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
public class DeleteTest {
	
	@Autowired
	private BaymaxDataSource dataSource;

    @Test
	public void test() throws SQLException, InterruptedException{

        int effctCount = new Jdbc(dataSource).executeUpdate("DELETE FROM t_order where order_id = 3002", new Jdbc.PrepareSetting() {
            @Override
            public void set(PreparedStatement statement) throws SQLException {
            }
        }).close().getEffectCount();


        System.out.println(effctCount);
	}

    @Test
    public void test1() throws SQLException, InterruptedException{

        int effctCount = new Jdbc(dataSource).executeUpdate("DELETE FROM t_order where user_id = 1", new Jdbc.PrepareSetting() {
            @Override
            public void set(PreparedStatement statement) throws SQLException {
            }
        }).close().getEffectCount();


        System.out.println(effctCount);
    }

    @Test
    public void test_all() throws SQLException, InterruptedException{

        int effctCount = new Jdbc(dataSource).executeUpdate("DELETE FROM t_order", new Jdbc.PrepareSetting() {
            @Override
            public void set(PreparedStatement statement) throws SQLException {
            }
        }).close().getEffectCount();


        System.out.println(effctCount);
    }
	
}
