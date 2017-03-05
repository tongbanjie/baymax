package support;

import com.tongbanjie.baymax.parser.model.ConditionUnit;
import com.tongbanjie.baymax.router.model.TargetTableEntity;
import com.tongbanjie.baymax.router.strategy.PartitionColumn;
import com.tongbanjie.baymax.router.strategy.PartitionFunction;
import com.tongbanjie.baymax.router.strategy.PartitionTable;
import com.tongbanjie.baymax.router.strategy.PartitionTableRule;
import com.tongbanjie.baymax.support.column.SubRightColumnProcess;
import com.tongbanjie.baymax.utils.NewArrayList;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by sidawei on 17/3/5.
 *
 * 阳春三月无限好 码农周末码代码
 */
@Component
public class SpecialPartitionTable extends PartitionTable {

    @PostConstruct
    public void init(){
        // 设置分表的逻辑表名
        super.setLogicTableName("t_order");
        // 设置分表的表名生成规则
        super.setNamePatten("t_order_{0}");
        // 设置用来分表的字段名, 分库的字段不要写进来
        super.setColumns(
                NewArrayList.newIt()
                        .add(new PartitionColumn("order_id", new SubRightColumnProcess(1)))
                        .add(new PartitionColumn("user_id",  new SubRightColumnProcess(1)))
                        .toArrayList()
        );
        // 设置一个空的路由函数 实际不会走到这里 在上层就重写了路由算法了
        super.setRule(new PartitionTableRule(super.getColumns(), null));
    }

    @Override
    public List<TargetTableEntity> getAllTableNames() {

        List<TargetTableEntity> tables = new ArrayList<TargetTableEntity>();

        tables.add(new TargetTableEntity("p1", "t_order_0"));
        tables.add(new TargetTableEntity("p1", "t_order_1"));

        tables.add(new TargetTableEntity("p2", "t_order_2"));
        tables.add(new TargetTableEntity("p2", "t_order_3"));

        return tables;
    }

    @Override
    protected TargetTableEntity executeRule(PartitionFunction function, PartitionColumn column, Object value, Set<ConditionUnit> conditionUnits) {

        String dbName = null;

        String tableName = null;

        int suffix = (Integer.valueOf(String.valueOf(value)) % 4);

        tableName = super.format(super.getSuffix(suffix));

        if (suffix <= 1){
            dbName = "p1";
        }else {
            dbName = "p2";
        }

        System.out.println("--------" + super.findColumnFromConditions(conditionUnits, "user_id"));

        return new TargetTableEntity(dbName, tableName);
    }
}
