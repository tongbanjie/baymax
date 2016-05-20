package com.tongbanjie.baymax.router.strategy.function;

import com.tongbanjie.baymax.router.strategy.PartitionFunction;
import org.mvel2.MVEL;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.integration.impl.MapVariableResolverFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sidawei on 16/3/20.
 */
public class ELFunction implements PartitionFunction {

    private String expression;

    public Integer execute(String columnValue, Map<String, Object> extention) {
        Map<String, Object> vrs = new HashMap<String, Object>();
        //, Map<String, ElFunction<?,?>> functionMap
        //vrs.putAll(functionMap);// 拓展函数
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("value", columnValue);
        vrs.put("$ROOT", params);
        VariableResolverFactory vrfactory = new MapVariableResolverFactory(vrs);
        return MVEL.eval(expression, params, vrfactory, Integer.class);
    }

    /*--------------------------------get set-------------------------------*/

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "ELFunction:" + expression;
    }
}
