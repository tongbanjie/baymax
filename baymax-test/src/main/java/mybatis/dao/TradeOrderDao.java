package mybatis.dao;

import mybatis.vo.TradeOrder;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class TradeOrderDao extends SqlSessionDaoSupport{

    public Integer insert(TradeOrder order){
        return getSqlSession().insert("TradeOrder.insert", order);
    }

}
