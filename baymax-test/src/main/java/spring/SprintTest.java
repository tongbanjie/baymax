package spring;

import com.alibaba.fastjson.JSON;
import com.tongbanjie.baymax.spring.bean.TableConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/partition/baymax-spring.xml")
public class SprintTest {

    @Autowired
    List<TableConfig> tableConfig;

    @Test
	public void test() throws SQLException, InterruptedException{

        System.out.println(JSON.toJSONString(tableConfig, true));

	}

}
