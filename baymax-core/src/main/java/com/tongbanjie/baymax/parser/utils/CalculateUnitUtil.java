package com.tongbanjie.baymax.parser.utils;

import com.alibaba.druid.stat.TableStat;
import com.tongbanjie.baymax.parser.model.CalculateUnit;
import com.tongbanjie.baymax.parser.model.ConditionUnit;
import com.tongbanjie.baymax.parser.model.ConditionUnitOperator;
import com.tongbanjie.baymax.support.BaymaxContext;
import com.tongbanjie.baymax.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sidawei on 16/1/25.
 */
public class CalculateUnitUtil {

    /**
     * 前题：
     * 1. 只有一个表的Contidion
     * 2. Condition已经分割好
     * @param tableAliasMap
     * @param conditionList 这里的conditionList应该是已经用or切分好的计算单元
     * @return
     */
    public static List<CalculateUnit> buildCalculateUnits(Map<String, String> tableAliasMap, List<List<TableStat.Condition>> conditionList) {
        List<CalculateUnit> retList = new ArrayList<CalculateUnit>();
        //遍历or
        for(List<TableStat.Condition> conditions : conditionList) {
            if (conditions == null){
                continue;
            }
            // 构建一个or
            CalculateUnit calculateUnit = new CalculateUnit();
            for(TableStat.Condition condition : conditions) {
                //遍历and
                List<Object> values = condition.getValues();
                if(values.size() == 0) {
                    break;
                }
                if(checkConditionValues(values)) {
                    String columnName = StringUtil.removeBackquote(condition.getColumn().getName());
                    String tableName = StringUtil.removeBackquote(condition.getColumn().getTable());

                // 获取真实表名
                tableName = getRealTableName(tableAliasMap, tableName);

                if(tableAliasMap != null && tableAliasMap.get(tableName) == null) {
                    // 子查询的别名条件忽略掉,不参数路由计算，否则后面找不到表
                    // 虚拟表直接忽略 select x from (select xxxx) u;
                    // 忽略u
                    continue;
                }

                String operator = condition.getOperator();

                //只处理between ,in和=3中操作符
//                    if(operator.equals("between")) {
//                        RangeValue rv = new RangeValue(values.get(0), values.get(1), RangeValue.EE);
//                        CalculateUnit.addShardingExpr(tableName.toUpperCase(), columnName, rv);
//                    } else
                //|| operator.toLowerCase().equals("in")

                    // between暂时不支持 需要枚举出between之间的值
                    // in暂时不支持 a in (1,2,3)要转化为a=1 or a=2 or a=3会导致计算单元的增加
                    if(operator.equals("=")){
                        //只处理=号和in操作符,其他忽略
                        calculateUnit.addCondition(ConditionUnit.buildConditionUnit(tableName, columnName, values.toArray(), ConditionUnitOperator.EQUAL));
                    }
                }
            }
            retList.add(calculateUnit);
        }
        return retList;
    }

    private static boolean checkConditionValues(List<Object> values) {
        for(Object value : values) {
            if(value != null && !value.toString().equals("")) {
                return true;
            }
        }
        return false;
    }

    private static String getRealTableName(Map<String, String> tableAliasMap, String alias){
        if (tableAliasMap != null){
            String realName = tableAliasMap.get(alias);
            return realName != null ? realName : alias;
        }
        return alias;
    }

    public static boolean hasPartitionTable(List<String> tables){
        for (String table : tables){
            if (BaymaxContext.isPartitionTable(table)){
                return true;
            }
        }
        return false;
    }

}
