# 声明 Bean

为了分离依赖，实现代码的松耦合，我们首先需要向 Leap 声明哪些类是需要注册成为 Bean 的。

## 注解方式声明

在一些开发框架中，比如说 Spring ，基于语义的需要，为开发者提供了不同的注解用于标识不同层次的 Bean 对象，比如 `@Repository`、`@Service` 和 `@Controller` 等。

但在 Leap 这边声明 Bean 对象不需要这么复杂，无论哪个层次的 Bean，都使用 `@Bean` 标注即可。代码示例如下：

```java
package hello;

import leap.core.annotation.Bean;

@Bean
public class UserService {

    ...
}
```

以上是最简单最常用的用法，一般情况下只需要这样声明就可以在其他地方使用这个 Bean 了。

当然 Bean 注解还有以下这些常用参数可以配置：

- id ：指定 Bean 的 id ，一般用于在代码中通过 id 从 BeanFactory 获取这个 Bean 。

- name ：指定 Bean 的 name ，作为该 Bean 的另外一个标识。

- type ：指定 Bean 所实现的类型，一般是一个接口类。

## XML 方式声明

Leap 推荐使用的声明 Bean 的方式是简单清晰的注解式声明，当然也提供 XML 方式供需要统一配置 Bean 的项目使用。

在资源配置目录下的 `conf/beans.xml` 文件或在 `conf/beans` 目录下的任意 XML 文件中放置我们的 Bean 配置，代码示例如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.leapframework.org/schema/beans">

    <bean type="javax.sql.DataSource" class="leap.db.cp.PooledDataSource"></bean>

</beans>
```

XML 配置中的属性与注解大同小异，只是在 XML 配置中需要指定 class 属性，即指定这个 Bean 对象对应的具体实现类。