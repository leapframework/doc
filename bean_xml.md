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

当然，如果bean对象的构造方法是包含参数的，也可以用这个配置：

```xml
<bean type="demo.bean.BeanService" class="demo.bean.BeanService" lazy-init="false"
    <constructor-arg name="service">
        <bean class="demo.bean.BeanService"/>
    </constructor-arg>
    <constructor-arg name="name" value="beanname"/></bean>
```

这里的`lazy-init=false`表示这个bean不是懒加载的，leap中默认所有的bean都是懒加载的，只要到需要的时候才初始化，可以通过这个配置在启动后立即初始化。

这里`BeanService`类的定义如下：

```java
public class BeanService {
    private BeanService service;
    private String name;
    public BeanService() {
    }
    public BeanService(BeanService service,String name) {
        this.service = service;
        this.name = name;
    }
    // 省略getter和setter
}
```

在bean的配置中，有两个重要的属性：

* type
* class

type表示bean的类型，一般是一个接口，class表示的是这个bean的具体实现类，一般情况下，我们配置type为接口，class为接口的实现类，如果没有配置type的话，会自动将type设置为和class一样的类型。

