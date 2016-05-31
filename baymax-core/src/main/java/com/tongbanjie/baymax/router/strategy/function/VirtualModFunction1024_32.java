package com.tongbanjie.baymax.router.strategy.function;

/**
 * Created by sidawei on 16/4/2.
 *
 * 以后最大能拓展到1024个表,每个表所在的DB可以再NodeMapping中配置,目前只用其中的32个表,
 * 以下的为这32个表的后缀,请执行填充为3位数,如032
 *
 * suffix: [0, 32, 64, 96, 128, 160, 192, 224, 256, 288, 320, 352, 384, 416, 448, 480, 512, 544, 576, 608, 640, 672, 704, 736, 768, 800, 832, 864, 896, 928, 960, 992]
 *
 * @See com.tongbanjie.baymax.router.strategy.function.VirtualModFunction
 */
public class VirtualModFunction1024_32 extends VirtualModFunction{

    public VirtualModFunction1024_32(){
        super(1024, 32);
    }
}
