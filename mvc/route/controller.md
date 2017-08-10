# 控制器类

控制器类，就是我们常说的 Controller 。

一个控制器里面可以包含多个路由方法，一般做法都是把同模块或者同实体的路由方法都放在同一个控制器中。

## 位置

Leap 对控制器类存放的位置没有太多要求，唯一**要求控制器类的包是在配置文件中的 `base-package` 包路径下**。

至于是把所有控制器类都放在同一个包里，还是按照模块存放控制器都可以，取决于具体项目的选择。

不过 Leap 推荐的做法是放在同一个包里，而且对放在 `base-package` 直接子包 `controller` 或 `controllers` 下的控制器类，会在路由映射时做包路径映射处理。

具体处理可以看下一篇的路由映射规则。

## 定义

工程启动的时候，Leap 会在 `base-package` 包路径下遍历扫描类，将符合以下规则的类作为控制器类。

- 不能是抽象类；

- 必须隐式或显式地拥有无参构造方法；

- 类上标注有 `@Controller` 注解或类名称以 `Controller` 结尾。

如果有符合以上规则的类但其实并不是作为控制器类，可以使用 `@NonController` 标注，Leap 就会忽略它。

> `@Controller` 注解的完整路径名称是 `leap.web.annotation.Controller`。

> `@NonController` 注解的完整路径名称是 `leap.web.annotation.NonController`。

## 示例

以上规则看起来好像比较复杂，但是创建一个控制器类还是很简单的。

常用的方式就是直接创建一个以 `Controller` 作为名称结尾的类。例如：

```java
public class HomeController{

}
```

不需要做额外配置，也无需注解。

当然你也可以使用注解，类似这样：

```java
@Controller
public class Home{

}
```

## 增强

在定义控制器类的时候还有一个可选操作，就是让控制器类继承 `leap.web.action.ControllerBase` 。

继承 `ControllerBase` 之后就可以在路由方法中获取当前请求上下文的相关信息，方便接口开发。