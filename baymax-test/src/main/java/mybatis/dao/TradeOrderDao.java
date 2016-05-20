package mybatis.dao;

import com.tbjfund.framework.tpa.TpaSupportDao;
import mybatis.vo.TradeOrder;
import org.springframework.stereotype.Repository;

@Repository
public class TradeOrderDao extends TpaSupportDao<TradeOrder, Integer> implements ITradeOrderDao {

}
