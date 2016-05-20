package mybatis;

import com.tbjfund.framework.tpa.TpaSupportDao;
import mybatis.vo.TradeOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 用来测试BayMax对MyBatis的兼容性
 * @author dawei
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/spring-context.xml")
public class MybatisTest {

	@Autowired
	private TpaSupportDao dao;
	
	/**
	 * 测试根据ID查询单条
	 */
	@Test
	public void testInsert(){
		System.out.println("--test start--");
        TradeOrder example = new TradeOrder();
        example.setOrderId(111111);
        example.setStatus(1);
        example.setUserId(1);
        example.setProductId(1);
        example.setProductName("1");
        dao.insert(example);
	}

}
