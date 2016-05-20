package com.tongbanjie.baymax.spring;

import com.tongbanjie.baymax.router.ColumnProcess;
import com.tongbanjie.baymax.router.strategy.*;
import com.tongbanjie.baymax.spring.bean.ColumnConfig;
import com.tongbanjie.baymax.spring.bean.TableConfig;
import com.tongbanjie.baymax.support.BaymaxContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by sidawei on 16/4/10.
 */
public class BaymaxSpringContext implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {

    private final static Logger logger = LoggerFactory.getLogger(BaymaxSpringContext.class);

    private AtomicBoolean lock = new AtomicBoolean(false);

    private ApplicationContext applicationContext;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent ev) {
        //防止重复执行。
        if(ev.getApplicationContext().getParent() == null){
            if (lock.compareAndSet(false, true)){
                logger.info("Baymax Spring Context start...");
                init();
            }
        }
    }

    private void init() {
        String[] beans = applicationContext.getBeanNamesForType(TableConfig.class);
        if (beans != null){
            List<PartitionTable> tables = new ArrayList<PartitionTable>();
            for (String beanName : beans){
                TableConfig tableConfig = applicationContext.getBean(beanName, TableConfig.class);
                tables.add(init(tableConfig));
            }
            BaymaxContext.setTables(tables);
            BaymaxContext.init();
        }
    }

    private PartitionTable init(TableConfig tableConfig){

        PartitionTable              partitionTable = new PartitionTable();
        PartitionTableNodeMapping   nodeMapping = tableConfig.getNodeMapping();

        PartitionTableRule          rule = new PartitionTableRule();
        PartitionFunction           function = tableConfig.getFunction();
        List<PartitionColumn>       columnList = new ArrayList<PartitionColumn>();

        for (ColumnConfig cf : tableConfig.getColumns()){
            PartitionColumn column = new PartitionColumn();
            column.setName(cf.getName());
            String columnProcessBeanName = cf.getProcess();
            if(columnProcessBeanName != null && columnProcessBeanName.length() > 0)
                column.setProcess((ColumnProcess) applicationContext.getBean(cf.getProcess()));
            columnList.add(column);
        }
        rule.setColumns(columnList);
        rule.setFunction(function);

        partitionTable.setLogicTableName(tableConfig.getTableName());
        partitionTable.setNamePatten(tableConfig.getNamePatten());
        partitionTable.setRule(rule);
        partitionTable.setNodeMapping(nodeMapping);

        return partitionTable;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
