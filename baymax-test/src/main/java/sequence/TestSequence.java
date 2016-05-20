package sequence;

import com.tongbanjie.baymax.datasource.BaymaxDataSource;
import com.tongbanjie.baymax.sequence.exception.SequenceException;
import com.tongbanjie.baymax.sequence.impl.DefaultSequence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/spring-context.xml")
public class TestSequence {
	
	@Autowired
	private BaymaxDataSource dataSource;
	
	@Test
	public void testGetById() throws SQLException, InterruptedException{
		DefaultSequence sequence = new DefaultSequence();
		sequence.setName("user_message");
		sequence.setDataSource(dataSource.getDefaultDataSource());
		while(true){
			long value;
			try {
				value = sequence.next();
				System.out.println(value);
				if(value > 10){
					break;
				}
			} catch (SequenceException e) {
				e.printStackTrace();
			}
		}
	}
}
