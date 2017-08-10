# 数据源配置

在使用 Leap 提供的数据访问功能之前，我们需要配置数据源。

## 配置参数

这里我们假设配上本地环境的 MYSQL 数据库参数：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<config xmlns="http://www.leapframework.org/schema/config">
	<base-package>hello</base-package>

	<properties>
		<property name="db.driverClassName">com.mysql.jdbc.Driver</property>
		<property name="db.url">jdbc:mysql://localhost:3306/demo?useUnicode=true</property>
		<property name="db.username">root</property>
		<property name="db.password">123456</property>
	</properties>
</config>
```

## 配置数据源

配置好了参数，接下来可以直接在数据源配置中使用它们。

我们在 `src/main/resource/conf/beans` 中新建一个 `datasource.xml` 专门配置数据：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	xmlns="http://www.leapframework.org/schema/beans"
	xsi:schemaLocation="http://www.leapframework.org/schema/beans http://www.leapframework.org/schema/beans">
    <bean name="default" type="javax.sql.DataSource" class="leap.db.cp.PooledDataSource" primary="true" destroy-method="close">
        <property name="driverClassName" value="${db.driverClassName}" />
        <property name="jdbcUrl"         value="${db.url}" />
        <property name="username"        value="${db.username}" />
        <property name="password"        value="${db.password}" />
        <property name="maxActive" 		 value="10"/> 
        <property name="maxWait" 		 value="10000"/>
    </bean>
</beans>
```

这里也可以使用其他数据源。

这里我们使用了 Leap 内部提供的一个可以用于实际生产系统的数据源 `leap.db.cp.PooledDataSource` 。