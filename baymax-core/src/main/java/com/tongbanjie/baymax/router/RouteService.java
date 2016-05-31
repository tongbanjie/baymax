package com.tongbanjie.baymax.router;

import com.tongbanjie.baymax.exception.BayMaxException;
import com.tongbanjie.baymax.jdbc.model.ParameterCommand;
import com.tongbanjie.baymax.parser.SqlParserFactory;
import com.tongbanjie.baymax.parser.SqlParser;
import com.tongbanjie.baymax.parser.model.CalculateUnit;
import com.tongbanjie.baymax.parser.model.ParseResult;
import com.tongbanjie.baymax.parser.model.SqlType;
import com.tongbanjie.baymax.parser.utils.SqlTypeUtil;
import com.tongbanjie.baymax.router.model.ExecutePlan;
import com.tongbanjie.baymax.router.model.ExecuteType;
import com.tongbanjie.baymax.router.model.TargetTableEntity;
import com.tongbanjie.baymax.router.model.TrargetSqlEntity;
import com.tongbanjie.baymax.router.strategy.PartitionTable;
import com.tongbanjie.baymax.support.BaymaxContext;
import com.tongbanjie.baymax.utils.Pair;
import com.tongbanjie.baymax.utils.PrintUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 路由服务的流程
 */
public class RouteService implements IRouteService {

    private final Logger logger = LoggerFactory.getLogger(RouteService.class);

    /**
     * 路由入口
     * @param sql   原始的sql语句
     * @param parameterCommand 业务层通过setString(1, "")等设置的参数
     * @return
     */
    public ExecutePlan doRoute(String sql, Map<Integer, ParameterCommand> parameterCommand) {

        SqlType sqlType = SqlTypeUtil.getSqlType(sql);

        SqlParser parser = SqlParserFactory.getParser(sqlType);

        if (parser == null){
            return buildExecutePlanTypeNo(sql, null, sqlType);
        }

        // 解析结果
        ParseResult result = new ParseResult();

        // 初始化解析器
        parser.init(sql, buildParameters(parameterCommand));

        // 解析
        parser.parse(result);

        // 路由
        ExecutePlan plan = route(result.getTables(), result.getCalculateUnits(), sql, sqlType);

        // 改写plan中sql
        parser.changeSql(result, plan);

        return plan;
    }

    /**
     * 根据CalculateUnit进行路由计算
     * @param tables sql中提取到的所有表名
     * @param units sql中提取到的计算单元
     * @param sql 原始sql
     * @param sqlType sql类型
     * @return
     */
    public ExecutePlan route(List<String> tables, List<CalculateUnit> units, String sql, SqlType sqlType){
        if (logger.isDebugEnabled()){
            logger.debug("计算单元:" + PrintUtil.printCalculates(units));
        }

        // 判断是否解析到表名
        if (tables == null || tables.size() == 0){
            return buildExecutePlanTypeNo(sql, null, sqlType);
        }
        // 查找逻辑表对应的分区规则
        PartitionTable partitionTable = null;
        for (String tableName : tables){
            if (BaymaxContext.isPartitionTable(tableName)){
                if (partitionTable != null){
                    throw new BayMaxException("sql中包含了两个分区表");
                }else {
                    partitionTable = BaymaxContext.getPartitionTable(tableName);
                }
            }
        }
        // 没有规则 无需路由
        if (partitionTable == null){
            return buildExecutePlanTypeNo(sql, null, sqlType);
        }

        // 没有计算单元 全表扫描
        if (units == null || units.size() == 0){
            return buildExecutePlanTypeAll(partitionTable, sqlType);
        }

        // 路由单元计算-合并
        Set<TargetTableEntity> nodeSet = new LinkedHashSet<TargetTableEntity>();
        for (CalculateUnit unit : units) {
            List<TargetTableEntity> temp = partitionTable.execute(unit);
            if (temp == null || temp.size() == 0){
                // 这个单元没有路由结果 需要全表扫描
                return buildExecutePlanTypeAll(partitionTable, sqlType);
            }else {
                nodeSet.addAll(temp);
            }
        }

        if (nodeSet.size() == 0){
            return buildExecutePlanTypeNo(sql, partitionTable.getLogicTableName(), sqlType);
        }

        return buildExecutePlanTypePartition(partitionTable, sqlType, nodeSet);
    }

    /**
     * 如果一个表不是分区表,着创建无路由执行计划
     * @param sql
     * @param tableName
     * @param sqlType
     * @return
     */
    private ExecutePlan buildExecutePlanTypeNo(String sql, String tableName, SqlType sqlType){
        // 不需要路由
        ExecutePlan plan = new ExecutePlan();
        plan.setExecuteType(ExecuteType.NO);
        TrargetSqlEntity actionSql = new TrargetSqlEntity();
        actionSql.setSqlType(sqlType);
        actionSql.setPartition(null);
        actionSql.setLogicTableName(tableName);
        actionSql.setOriginalSql(sql);
        actionSql.setTargetSql(sql);
        actionSql.setTargetTableName(tableName);
        plan.addSql(actionSql);
        return plan;
    }

    /**
     * 如果一个表是分区表,但是当前sql不满足某些条件,创建全表扫描执行计划
     * TODO 考虑聚合函数
     * @param partitionTable
     * @param sqlType
     * @return
     */
    private ExecutePlan buildExecutePlanTypeAll(PartitionTable partitionTable, SqlType sqlType){
        // 没有命中的shardingKey,则全表扫描
        ExecutePlan plan = new ExecutePlan();
        List<TargetTableEntity> mappings = partitionTable.getAllTableNames();
        if(mappings != null && mappings.size() > 0){
            for(TargetTableEntity pt : mappings){
                /**
                 * 全表扫描：SQL对象只存储targetDB,targetTableName;执行时不改SQL,不改参数.
                 */
                TrargetSqlEntity actionSql = new TrargetSqlEntity();
                actionSql.setSqlType(sqlType);
                actionSql.setPartition(pt.getTargetDB());
                actionSql.setLogicTableName(partitionTable.getLogicTableName());
                actionSql.setTargetTableName(pt.getTargetTable());
                plan.addSql(actionSql);
            }
            plan.setExecuteType(ExecuteType.ALL);
        }else{
            plan.setExecuteType(ExecuteType.NO);
        }
        return plan;
    }

    /**
     * 是分区表,切sql满足分区执行条件,创建分区执行计划
     * @param partitionTable
     * @param sqlType
     * @param nodeSet
     * @return
     */
    private ExecutePlan buildExecutePlanTypePartition(PartitionTable partitionTable, SqlType sqlType, Set<TargetTableEntity> nodeSet) {
        ExecutePlan routeResult = new ExecutePlan();
        routeResult.setExecuteType(ExecuteType.PARTITION);
        for (TargetTableEntity node : nodeSet){
            TrargetSqlEntity actionSql = new TrargetSqlEntity();
            actionSql.setSqlType(sqlType);
            actionSql.setPartition(node.getTargetDB());
            actionSql.setLogicTableName(partitionTable.getLogicTableName());
            actionSql.setTargetTableName(node.getTargetTable());
            routeResult.addSql(actionSql);
        }
        return routeResult;
    }

    /**
     * 参数排序
     * 业务层通过setString(1, "")等设置的参数, 这里按照1这个位置的数排序,以便在后面sql解析时把sql中的?替换为具体的值.
     * @param commonds
     * @return
     */
    public List<Object> buildParameters(Map<Integer, ParameterCommand> commonds){

        if (commonds == null || commonds.size() == 0){
            return null;
        }

        // 排序
        SortedSet<Map.Entry<Integer, ParameterCommand>> set = new TreeSet<Map.Entry<Integer, ParameterCommand>>(new Comparator<Map.Entry<Integer, ParameterCommand>>() {
            @Override
            public int compare(Map.Entry<Integer, ParameterCommand> e1, Map.Entry<Integer, ParameterCommand> e2) {
                return e1.getKey().compareTo(e2.getKey());
            }
        });

        for (Map.Entry entry : commonds.entrySet()){
            set.add(entry);
        }

        List<Object> result = new ArrayList<Object>(commonds.size());
        for (Map.Entry<Integer, ParameterCommand> entry : set){
            result.add(entry.getValue().getParttionArg());
        }

        return result;
    }
}