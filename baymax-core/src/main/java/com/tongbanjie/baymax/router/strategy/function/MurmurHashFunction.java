package com.tongbanjie.baymax.router.strategy.function;

import com.tongbanjie.baymax.exception.BayMaxException;
import com.tongbanjie.baymax.router.strategy.PartitionFunction;
import com.tongbanjie.baymax.utils.Hash;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by sidawei on 16/4/2.
 *
 * 一致性Hash的分区函数实现
 */
public class MurmurHashFunction implements PartitionFunction{

    private static final int DEFAULT_VIRTUAL_BUCKET_TIMES = 160;

    /**
     * 真实节点的数量
     */
    private int count;

    /**
     * 每个真实节点创建多少个虚拟节点
     */
    private int virtualBucketTimes = DEFAULT_VIRTUAL_BUCKET_TIMES;

    private SortedMap<Integer,Integer> bucketMap;

    public void init()  {
        try{
            bucketMap = new TreeMap<Integer,Integer>();
            generateBucketMap();
        }catch(Exception e){
            throw new BayMaxException("一致性hash路由创建失败");
        }
    }

    private void generateBucketMap(){
        for(int i = 0; i < count; i++){
            StringBuilder hashName = new StringBuilder("SHARD-").append(i);
            for(int n = 0,shard = virtualBucketTimes; n < shard; n++){
                bucketMap.put(Hash.hashUnencodedChars(hashName.append("-NODE-").append(n)),i);
            }
        }
    }

    public MurmurHashFunction(int count, int virtualBucketTimes){
        this.count = count;
        this.virtualBucketTimes = virtualBucketTimes;
        this.init();
    }

    @Override
    public Integer execute(String columnValue, Map<String, Object> extension) {
        SortedMap<Integer, Integer> tail = bucketMap.tailMap(Hash.hashUnencodedChars(columnValue));
        if (tail.isEmpty()) {
            return bucketMap.get(bucketMap.firstKey());
        }
        return tail.get(tail.firstKey());
    }

}
