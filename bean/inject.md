# 获取 Bean

声明好 Bean，一般我们会有以下几种场景需要获取 Bean 对象。

## 在成员变量上注入

在一个 Bean 中最方便以及最常见的使用其他 Bean 的方式，就是将其他 Bean 注入为成员变量。

我们使用 `@inject` 注解完成这项工作，代码如下：

```java
package hello;

import leap.core.annotation.Bean;
import leap.core.annotation.Inject;

@Bean
public class PetService {

    @Inject
    private UserService userService;

    ...
}
```

配置完成后，leap 会在运行时解析依赖并注入相应对象。

> 注意使用 `@inject` 的类也需要是一个 Bean ，否则普通类并不会被 Leap 扫描和处理依赖注入。

> Controller 类不需要 `@Bean` 注解，但是 Leap 还是会扫描 Controller 类中标识 @Inject 的成员变量并注入。

## 在成员变量上强制注入

当我们使用 `@Inject` 注解声明注入时，这个声明默认是注入对象可为空的，即声明的 Bean 类型如果是接口，允许没有对应的实现类。

这个时候，由于没有实现类，因此成员变量将会保留原值 `null`，并且程序启动不会报错，只有在运行时实际使用到才会抛出空指针异常。

显然这在面向接口编程的项目中是很容易出错的，这个时候我们可以配合 `@Inject` 使用 `@M` 注解。

> M 是 mandatory 的意思，即强制的，不允许为空。

它表示，这个注入的依赖必须要找到对应的实现类，否则将在工程启动的时候报错。代码如下：

```java
package hello;

import leap.core.annotation.Bean;
import leap.core.annotation.Inject;
import leap.core.annotation.M;

@Bean
public class PetService {

    @Inject @M
    private UserService userService;

    ...
}
```

如果项目没有面向接口编程，或者有些依赖确实允许为空，又或者开发人员确定依赖有对应的实现，则可以不使用这个注解。

## 代码式获取

以上方式已经可以满足大部分情况下获取 Bean 对象的需求。

但是在一些情况下，比如当前类无法声明为 Bean 等，那么我们只能通过 Leap 的接口从 BeanFactory 中获取 Bean 对象了。

获取方法如下：

```java
UserService userService = AppContext.current().getBeanFactory().getBean(UserService.class);
...
```