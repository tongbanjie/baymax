package com.tongbanjie.baymax.router.strategy.function;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sidawei on 16/3/21.
 */
public class ELFunctionTest {

    @Test
    public void testExecute() throws Exception {
        ELFunction rule = new ELFunction();
        rule.setExpression("user_id % 4");

        Object result = rule.execute("1", null);

        System.out.println(result);
    }

    @Test
    public void testExecute_0() throws Exception {
        ELFunction rule = new ELFunction();
        rule.setExpression("user_id % 4");

        Object result = rule.execute("1", null);

        System.out.println(result);
    }
}