package com.tongbanjie.baymax.utils;

import com.tongbanjie.baymax.parser.model.CalculateUnit;
import com.tongbanjie.baymax.parser.model.ConditionUnit;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by sidawei on 16/3/21.
 */
public class PrintUtil {

    public static String printCalculates(List<CalculateUnit> calculateUnits){
        if (calculateUnits == null || calculateUnits.size() == 0){
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (CalculateUnit unit : calculateUnits){
            sb.append("{");
            sb.append(printCalculate(unit));
            sb.append("}");
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    public static String printCalculate(CalculateUnit unit){
        StringBuffer sb = new StringBuffer();
        Map<String/*table*/, Set<ConditionUnit/*column value*/>> tablesAndConditions = unit.getTablesAndConditions();
        if (tablesAndConditions == null){
            return "";
        }
        for (Iterator ite = tablesAndConditions.entrySet().iterator(); ite.hasNext();){
            Map.Entry<String/*table*/, Set<ConditionUnit/*column value*/>> entry = (Map.Entry<String, Set<ConditionUnit>>) ite.next();
            sb.append(entry.getKey()).append(":");
            Set<ConditionUnit> conditionUnits = entry.getValue();
            if (conditionUnits != null){
                for (ConditionUnit conditionUnit : conditionUnits){
                    sb.append("{");
                    sb.append(conditionUnit.getColumn()).append("=");
                    sb.append(conditionUnit.getValues()).append("}");
                }
            }
        }
        return sb.toString();
    }

    public static void printFildAlisMap(Map<String/*field*/, String/*alias*/> aliaColumns){
        if (aliaColumns == null || aliaColumns.isEmpty()){
            return;
        }
        for (Iterator<Map.Entry<String/*field*/, String/*alias*/>> ite = aliaColumns.entrySet().iterator(); ite.hasNext(); ){
            Map.Entry<String/*field*/, String/*alias*/> entry = ite.next();
            System.out.print("column:{"+entry.getKey()+"}");
            System.out.print("alias:{"+entry.getValue()+"}");
            System.out.println();
        }
    }

}
