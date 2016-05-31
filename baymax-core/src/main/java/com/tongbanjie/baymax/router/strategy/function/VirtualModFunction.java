package com.tongbanjie.baymax.router.strategy.function;

import com.tongbanjie.baymax.router.strategy.PartitionFunction;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by sidawei on 16/4/2.
 *
 * 这是一个虚拟表的分区算法,接收两个参数
 * @property max 以后可拓容的最大表数量
 * @property current 当前使用的表数量
 *
 * 例子：最大16个表,目前使用其中4个表
 * current = 4; max = 16;
 * 最大表数量        {00} {01} {02} {03} {04} {05} {06} {07} {08} {09} {10} {11} {12} {13} {14} {15}
 * 当前使用4个表     {       00        } {       04        } {       08        } {      12         }
 * 表00到表03的数据放在表00中,
 * 表04到表07的数据放在表04中,
 * 表08到表11的数据放在表08中,
 * 表12到表15的数据放在表12中,
 *
 * 拓容：把表00拆分为两个表,表04拆分为两个表,以此类推.
 *
 * 
 */
public class VirtualModFunction implements PartitionFunction{

    /**
     * 这个add比较奇特, 由于历史原因, 有些表的后缀是1开始的,baymax3.0默认后缀为0开始,可以设置add=1兼容老的表规则.
     */
	private int add = 0;

    /**
     * 最大真实节点数
     */
    private int max;

    /**
     * 真实节点的数量
     */
    private int current;

    private int[] bucket;

    private Set suffixSet;

    public void init()  {
        bucket = new int[max];
        suffixSet = new TreeSet();

        int length = max / current;
        int lengthIndex = 0;

        int suffix = 0;

        for (int i = 0; i < max; i++) {
            bucket[i] = suffix;
            lengthIndex ++;
            suffixSet.add(suffix);
            if (lengthIndex == length){
                lengthIndex = 0;
                suffix = i + 1;
            }
        }
    }

    public VirtualModFunction(int max, int current){
        this.current = current;
        this.max = max;
        this.init();
    }

    public int getAdd() {
		return add;
	}

	public void setAdd(int add) {
		this.add = add;
	}
	
	@Override
    public Integer execute(String columnValue, Map<String, Object> extension) {
        return bucket[((Long) (Long.valueOf(columnValue) % max)).intValue()] + add;
    }

    @Override
    public String toString() {
        return "suffix: " + suffixSet.toString() + "   bucket: " + Arrays.toString(bucket);
    }
}
