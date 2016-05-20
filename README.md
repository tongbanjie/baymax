简介:

baymax是铜板街开发的轻量级分库分表框架，支持多库多表。

使用方法：

<dependency>
    <groupId>com.tongbanjie.baymax</groupId>
    <artifactId>baymax-core</artifactId>
    <version>3.0.0</version>
</dependency>
    <dependency>
    <groupId>com.tongbanjie.baymax</groupId>
    <artifactId>baymax-sequence</artifactId>
    <version>3.0.0</version>
</dependency>
    <dependency>
    <groupId>com.tongbanjie.baymax</groupId>
    <artifactId>baymax-spring</artifactId>
    <version>3.0.0</version>
</dependency>

<!-- 最原始的数据源 -->
<bean id="dataSource_p1" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
    <property name="url">
        <value>
            ${jdbc.druid.url}
        </value>
    </property>
    <property name="username">
        <value>${jdbc.druid.user}</value>
    </property>
    <property name="password">
        <value>${jdbc.druid.password}</value>
    </property>
    <property name="filters" value="stat,wall" />
</bean>
<!-- Baymax数据源 -->
<bean id="multipleDataSource" class="com.tongbanjie.baymax.datasource.BaymaxDataSource" init-method="init">
    <property name="dataSourceGroupSet">
        <set>
            <bean class="com.tongbanjie.baymax.datasource.DataSourceGroup">
                <property name="identity" value="p1"/>
                <property name="targetDataSource" ref="dataSource_p1"/>
            </bean>
        </set>
    </property>
</bean>
<!-- Mybatis -->
<bean id="sqlSessionFactory" class="mybatis.JpaSqlSessionFactoryBean">
    <property name="configLocation" value="classpath:META-INF/mybatis/mybatis-configuration.xml" />
    <property name="mapperLocations" value="classpath*:META-INF/mybatis/tables/*.xml" />
    <property name="dataSource" ref="multipleDataSource" />
</bean>
<!-- 路由 -->
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:baymax="http://baymax.tongbanjie.com/schema/baymax-3.0"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans-3.2.xsd
                           http://baymax.tongbanjie.com/schema/baymax-3.0
                           http://baymax.tongbanjie.com/schema/baymax-3.0.xsd">
    <baymax:context />
    <!-- 一个表 -->
    <baymax:table tableName="sign_user_record" namePatten="sign_user_record_{00}">
        <baymax:columns>
            <baymax:column name="user_id"/>
        </baymax:columns>
        <baymax:function class="com.tongbanjie.baymax.router.strategy.function.VirtualModFunction64_8"/>
        <baymax:nodeMapping class="com.tongbanjie.baymax.router.strategy.SimpleTableNodeMapping">
            <baymax:node>p1:0,8,16,24,32,40,48,56</baymax:node>
        </baymax:nodeMapping>
    </baymax:table>
    <!-- 一个表end -->
</beans>
