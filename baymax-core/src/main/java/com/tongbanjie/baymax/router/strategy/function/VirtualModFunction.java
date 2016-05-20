package com.tongbanjie.baymax.router.strategy.function;

import com.tongbanjie.baymax.router.strategy.PartitionFunction;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by sidawei on 16/4/2.
 *
 * count = 16; max = 16;
 * [1][2][3][4]  [5][6][7][8]  [9][10][11][12] [13][14][15][16]
 *
 * count = 4; max = 16;
 * [    1     ]  [    5     ]  [    9        ] [    13        ]
 * [1][1][1][1]  [5][5][5][5]  [9][9 ][9 ][9 ] [13][13][13][13]
 *
 * count = 8; max = 16;
 * [ 1  ] [  3 ]  [ 5 ] [ 7  ]  [ 9  ]  [ 11  ]  [ 13  ] [  15  ]
 * [1][1] [3][3] [5][5] [7][7]  [9][9] [11][11] [13][13] [15][15]
 *
 * 
 */
public class VirtualModFunction implements PartitionFunction{
	
	private int add = 0;

    /**
     * 最大真实节点数
     */
    private int max;

    /**
     * 真实节点的数量
     */
    private int count;

    private int[] bucket;

    private Set suffixSet;

    public void init()  {
        bucket = new int[max];
        suffixSet = new TreeSet();

        int length = max / count;
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

    public VirtualModFunction(int max, int count){
        this.count = count;
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
    public Integer execute(String columnValue, Map<String, Object> extention) {
        return bucket[((Long) (Long.valueOf(columnValue) % max)).intValue()] + add;
    }

    @Override
    public String toString() {
        return "suffix: " + suffixSet.toString() + "   bucket: " + Arrays.toString(bucket);
    }
}
