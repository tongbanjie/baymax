package jdbc;

import jdbc.frame.Jdbc;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by sidawei on 18/4/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/spring-context.xml")
public class OriginalTest {

    @Autowired
    @Qualifier("dataSource_p1")
    private DataSource dataSource;

    @Test
    public void test() throws SQLException {
        new Jdbc(dataSource).executeSelect("select * from t_order_0").printSet().close();
    }

}
