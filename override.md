# 覆盖配置

leap提供了非常强大的覆盖配置功能，所谓覆盖配置，就是我们通过配置覆盖系统或者框架做好的默认配置，这使得leap可以做一个开箱即用的产品，也可以开箱定制配置。

在leap的配置中，很多地方可以使用`override=true`来指定覆盖默认配置。

## property配置覆盖

假设我们在配置文件`conf/config/property.xml`中有如下配置：

```xml
<property name="property1">property1</property>
```

另外在`conf/config.xml`中有如下配置：

```xml
<property name="property1" override="true">override-property1</property>
```

那么最终启动完成后，读取的配置是：

```
property1=override-property1
```

如果我们把`override="true"`去掉，则会启动报错，提示有重复的配置。

## bean配置覆盖

假设我们在配置文件`conf/beans/bean.xml`中有如下配置：

```xml
<bean type="demo.bean.BeanService" class="demo.bean.BeanService" lazy-init="false">
    <property name="service" ref-id="beanservice"></property>
</bean>
```