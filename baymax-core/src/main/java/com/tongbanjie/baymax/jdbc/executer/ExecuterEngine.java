package com.tongbanjie.baymax.jdbc.executer;

/**
 * Created by sidawei on 16/2/7.
 *
 * 多线程执行框架
 * TODO
 */
public class ExecuterEngine {



    public static void execute(final Map map, Reduce reduce){

        new Runnable() {
            @Override
            public void run() {
                map.execute();
            }
        };

    }


    /**
     * 单线程计算
     * @param <Object>
     */
    public interface Map<Object>{

        Object execute();

        Object getResult();

    }

    /**
     * 计算结果合并
     * @param <Object>
     */
    public interface Reduce<Object>{

    }

}
