package com.tongbanjie.baymax.router.strategy;

import com.tongbanjie.baymax.exception.BayMaxException;
import com.tongbanjie.baymax.router.strategy.PartitionTableNodeMapping;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sidawei on 16/4/2.
 *
 * @See com.tongbanjie.baymax.router.strategy.PartitionTableNodeMapping
 */
public class SimpleTableNodeMapping implements PartitionTableNodeMapping{

    /**
     * 注入配置 格式 分区名:表后缀1,表后缀2
     * 例子 partition1:00,01,02
     */
    private List<String> config;

    /*---------------------------------------------------------------*/

    private Map<String/*suffix*/, String/*partition*/> tableMapping = new ConcurrentHashMap<String, String>(); 	// 所有表到分区的映射

    /*---------------------------------------------------------------*/

    public void init() {
        if (config == null || config.size() == 0){
            throw new BayMaxException("config 不能为空");
        }
        for (String entity : config) {
            String[] str = entity.trim().split(":");
            String node = str[0];
            String suffixStr = str[1];
            if (node == null || suffixStr == null){
                continue;
            }
            node = node.trim();
            suffixStr = suffixStr.trim();
            if (node.length() == 0 || suffixStr.length() == 0){
                continue;
            }
            if (suffixStr.indexOf(",") != -1){
                String[] suffixs = suffixStr.split(",");
                if (suffixs == null){
                    continue;
                }
                for (String suffix : suffixs){
                    if (suffix != null && suffix.trim().length() != 0){
                        tableMapping.put(suffix, node);
                    }
                }
            }
        }
    }

    @Override
    public Map<String, String> getMapping() {
        return tableMapping;
    }

    public void setConfig(List<String> config) {
        this.config = config;
        this.init();
    }

    public SimpleTableNodeMapping(){
    }

    public SimpleTableNodeMapping(List<String> config){
        setConfig(config);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("(");
        for (int i = 0; i < config.size(); i++) {
            sb.append(config.get(i));
            if (i + 1 < config.size()){
                sb.append(" | ");
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
