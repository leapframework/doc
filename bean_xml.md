# bean配置

## bean配置的目录结构

bean配置的入口是`beans.xml`，leap默认会扫描`conf/beans.xml`文件和`conf/beans`目录作为bean的配置。

比如：

```
conf
 - beans
    - bean1.xml
    - bean2.xml
 - beans.xml
 - bean3.xml
```

这里`bean1.xml`,`bean2.xml`,`beans.xml`会被扫描并读取bean配置，`bean3.xml`不会被扫描。

## bean配置文件

一个空的bean配置文件如下:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.leapframework.org/schema/beans">

</beans>
```

## bean对象配置

bean对象的示例如下：

```xml
<bean type="javax.sql.DataSource" class="leap.db.cp.PooledDataSource">
    <property name="driverClassName" value="com.mysql.Driver" />
    <property name="jdbcUrl" value="${jdbc.url}" />
    <property name="username" value="${jdbc.username}" />
    <property name="password" value="${jdbc.password}" />
</bean>
```

这里可以使用占位符的方式配置bean的属性，占位符的名称就是property的key。