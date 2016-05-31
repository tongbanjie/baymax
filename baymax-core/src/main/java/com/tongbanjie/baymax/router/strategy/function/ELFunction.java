package com.tongbanjie.baymax.router.strategy.function;

import com.tongbanjie.baymax.router.strategy.PartitionFunction;
import org.mvel2.MVEL;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.integration.impl.MapVariableResolverFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sidawei on 16/3/20.
 *
 * el表达式的分区函数的实现 接收参数
 *
 * @proterty expression 如 value % 4 , 上层会把sql中提取到的参数统一转换位value,不管分区列配置的是user_id还是order_id, 在el表达式中统一使用value作为变量名.
 */
public class ELFunction implements PartitionFunction {

    private String expression;

    public Integer execute(String columnValue, Map<String, Object> extension) {
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
