package com.tongbanjie.baymax.spring;

import com.sun.org.apache.xerces.internal.dom.DeferredElementNSImpl;
import com.tongbanjie.baymax.router.strategy.SimpleTableNodeMapping;
import com.tongbanjie.baymax.spring.bean.ColumnConfig;
import com.tongbanjie.baymax.spring.bean.TableConfig;
import com.tongbanjie.baymax.utils.Pair;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于Spring XML自定义标签拓展
 * @author si.dawei
 *
 */
public class BaymaxBeanDefinitionParser implements org.springframework.beans.factory.xml.BeanDefinitionParser {

	private final Class<?> beanClass;

    private final boolean required;

    private int counter = 2;

	public BaymaxBeanDefinitionParser(Class<?> beanClass, boolean required) {
		this.beanClass = beanClass;
        this.required = required;
	}

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        try {
            String clazzStr = element.getAttribute("class");
            Class clazz = clazzStr == null || clazzStr.length() == 0 ? beanClass : Class.forName(clazzStr);
            return parse(element, parserContext, clazz, required).getObject1();
        } catch (ClassNotFoundException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    public Pair<RootBeanDefinition, String/*name*/> parse(Element element, ParserContext parserContext, Class beanClass, boolean required) throws ClassNotFoundException {

        Pair<RootBeanDefinition, String/*name*/> pair = fixName(element, parserContext, beanClass);

        RootBeanDefinition beanDefinition = pair.getObject1();

        /**
         * Table
         */
        if (beanClass == TableConfig.class){
            beanDefinition.getPropertyValues().addPropertyValue("tableName", element.getAttribute("tableName"));
            beanDefinition.getPropertyValues().addPropertyValue("namePatten", element.getAttribute("namePatten"));

            for (int i = 0; i < element.getChildNodes().getLength(); i++) {
                Node node = element.getChildNodes().item(i);

                /**
                 * Function
                 */
                if ("function".equals(node.getLocalName())){
                    Class clazz = Class.forName(((Element)node).getAttribute("class"));
                    Pair<RootBeanDefinition, String/*name*/> functionDef = parse((Element) node, parserContext, clazz, false);
                    beanDefinition.getPropertyValues().addPropertyValue("function", new BeanDefinitionHolder(functionDef.getObject1(), functionDef.getObject2()));
                    //beanDefinition.getPropertyValues().addPropertyValue("function", new RuntimeBeanReference(id + "Function"));
                }

                /**
                 * NodeMapping
                 */
                if ("nodeMapping".equals(node.getLocalName())){
                    Class clazz = Class.forName(((Element) node).getAttribute("class"));
                    Pair<RootBeanDefinition, String/*name*/> def = parse((Element) node, parserContext, clazz, false);
                    if (clazz == SimpleTableNodeMapping.class){
                        NodeList maplist = node.getChildNodes();
                        List<String> config = null;
                        for (int mapindex = 0; mapindex < maplist.getLength(); mapindex++) {
                            Node nodeElement = maplist.item(mapindex);
                            if (nodeElement instanceof DeferredElementNSImpl){
                                if (config == null){
                                    config = new ArrayList<String>();
                                }
                                String value = nodeElement.getTextContent();
                                if (value.indexOf(":") != -1){
                                    config.add(value);
                                }
                            }
                        }
                        if (config != null){
                            def.getObject1().getPropertyValues().addPropertyValue("config", config);
                        }
                    }
                    beanDefinition.getPropertyValues().addPropertyValue("nodeMapping", new BeanDefinitionHolder(def.getObject1(), def.getObject2()));
                }

                /**
                 * Columns
                 */
                if ("columns".equals(node.getLocalName())){
                    NodeList columns = node.getChildNodes();
                    ManagedList columnList = null;
                    for (int ci = 0; ci < columns.getLength(); ci++) {
                        Node column = columns.item(ci);
                        if (column instanceof  Element){
                            if (columnList == null){
                                columnList = new ManagedList();
                            }
                            ColumnConfig c = new ColumnConfig();
                            c.setName(((Element) column).getAttribute("name"));
                            c.setProcess(((Element) column).getAttribute("process-ref"));
                            columnList.add(c);
                        }
                    }
                    if (columnList != null){
                        beanDefinition.getPropertyValues().addPropertyValue("columns", columnList);
                    }
                }
            }
        }

		// 自定义参数的处理

		/**
		 * 解析标签内部的<property>节点
		 */
		parseProperties(element.getChildNodes(), beanDefinition, parserContext);

        return pair;
	}

    /**
     * ID处理
     * 如果标签上没有定义name 则自动生成name
     * @param element
     * @param parserContext
     */
    private Pair<RootBeanDefinition, String/*name*/> fixName(Element element, ParserContext parserContext, Class beanClass){
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);

        String name = element.getAttribute("name");

        synchronized (BaymaxBeanDefinitionParser.class){
            if (name == null || name.length() == 0) {
                name = "Baymax-" + beanClass.getSimpleName();
                while (parserContext.getRegistry().containsBeanDefinition(name)) {
                    name += (counter++);
                }
            }
            if (name != null && name.length() > 0) {
                if (parserContext.getRegistry().containsBeanDefinition(name)) {
                    throw new IllegalStateException("Duplicate spring bean id " + name);
                }
                parserContext.getRegistry().registerBeanDefinition(name, beanDefinition);
            }
        }
        return new Pair<RootBeanDefinition, String>(beanDefinition, name);
    }

	/**
	 * 解析 property </p>
     * @param nodeList
     * @param beanDefinition
     * @param parserContext
     */
	private static void parseProperties(NodeList nodeList, RootBeanDefinition beanDefinition, ParserContext parserContext) {
		if (nodeList == null || nodeList.getLength() == 0) {
            return;
        }
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                if ("property".equals(node.getNodeName()) || "property".equals(node.getLocalName())) {
                    String name = ((Element) node).getAttribute("name");
                    if (name != null && name.length() > 0) {
                        parserContext.getDelegate().parsePropertyElement((Element) node, beanDefinition);
                    }
                }
            }
        }
	}

}
