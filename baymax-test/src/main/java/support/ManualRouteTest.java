package support;

import com.tongbanjie.baymax.datasource.BaymaxDataSource;
import com.tongbanjie.baymax.router.model.TargetTableEntity;
import com.tongbanjie.baymax.support.ManualRoute;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sidawei on 16/5/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/spring-context.xml")
public class ManualRouteTest {
    @Autowired
    private BaymaxDataSource dataSource;

    @Test
    public void testAllTable(){
        List<TargetTableEntity> tables =  ManualRoute.getAllTables("t_order");
        System.out.println(tables);
    }

    @Test
    public void testManualRoute(){
        Map map = new HashMap<String ,String>();
        map.put("user_id", 2);
        System.out.println(ManualRoute.route("t_order", map));
    }

}
