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
<bean type="demo.bean.BeanService" class="demo.bean.BeanService" lazy-init="false" primary="true">
</bean>
```

在`conf/beans.xml`中有如下配置：

```xml
<bean type="demo.bean.BeanService" class="demo.bean.BeanService" lazy-init="false" primary="true" override=true>
    <property name="service" ref-id="beanservice"></property>
</bean>
```

最终生成的bean是包含`service`属性的bean，如果没有使用`override="true"`这里会抛出重复的bean的异常。

> **注意：**这里的`primary="true"`表示的是主要的bean，同一个type可以有多个bean，但是只能有一个主要的bean，在依赖注入的时候会优先选择主要的bean，如果需要覆盖主要的bean则需要使用`override`。

## sql配置覆盖

同样的，下面是一个覆盖leap内部sql的sql配置例子：

```xml
<command key="security.findUserDetailsById" override="true">
    select * from User where id = :userId
</command>
```

leap中默认使用了一个sql配置，key是`security.findUserDetailsById`，我们依然可以通过`override`的方式覆盖默认的配置。

## 默认配置覆盖规则

在leap中，几乎所有的默认配置都是可以由我们覆盖并重新配置的，但是我们需要先了解leap的覆盖规则：

* 配置了`override=true`之后，leap会根据配置读取的先后顺序，使用后读到配置覆盖先读到的配置。

注意这个规则，如果反过来的话，leap是无法覆盖配置的，也就是说，如果在先读到的配置中写了`override=true`，后读到的配置没有写，那么leap依然会抛出重复配置的异常。

所以我们还需要了解leap对配置的读取顺序：

* leap会先读取目录深度最深的配置
* 同一目录深度下，leap根据配置文件名排序并读取

按照这个顺序，一般来说，jar包中打包的配置是最早读取的，其次就是conf中目录最深的配置，然后依次到最外层配置。