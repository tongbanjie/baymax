package com.tongbanjie.baymax.spring;

import com.tongbanjie.baymax.router.ColumnProcess;
import com.tongbanjie.baymax.spring.bean.TableConfig;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * 用于Spring XML自定义标签拓展
 * @author dawei
 *
 */
public class BaymaxNamespaceHandler extends NamespaceHandlerSupport {

	//com.alibaba.dubbo.config.spring.schema.DubboNamespaceHandler
	@Override
	public void init() {
		registerBeanDefinitionParser("table", new BaymaxBeanDefinitionParser(TableConfig.class, false));
		registerBeanDefinitionParser("context", new BaymaxBeanDefinitionParser(BaymaxSpringContext.class, false));
		registerBeanDefinitionParser("process", new BaymaxBeanDefinitionParser(ColumnProcess.class, false));
	}

}
