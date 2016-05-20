package com.tongbanjie.baymax.router.strategy.function;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sidawei on 16/4/2.
 */
public class VirtualModFunctionTest {

    @Test
    public void testExecute_4() throws Exception {
        VirtualModFunction function = new VirtualModFunction(4, 16);
        System.out.println(function);
    }

    @Test
    public void testExecute_8() throws Exception {
        VirtualModFunction function = new VirtualModFunction(8, 16);
        System.out.println(function);
    }

    @Test
    public void testExecute_16() throws Exception {
        VirtualModFunction function = new VirtualModFunction128_32();
        System.out.println(function);
    }

    @Test
    public void VirtualModFunction64_8() throws Exception {
        VirtualModFunction function = new VirtualModFunction64_8();
        System.out.println(function);
    }

    @Test
    public void VirtualModFunction128_16() throws Exception {
        VirtualModFunction function = new VirtualModFunction128_16();
        System.out.println(function);
    }

    @Test
    public void VirtualModFunction1024_16() throws Exception {
        VirtualModFunction function = new VirtualModFunction1024_16();
        System.out.println(function);
    }


}