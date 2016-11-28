# 路由映射

路由是leap MVC中的重要概念，leap启动完成之后会生成一个路由表并打印在控制台，表示的是对应每个路径的处理方法，路由表的生成依赖Controller和Action。

## Controller

Controller是控制器，leap有一下两种方式指定控制器：

* `base-package`下的所有以Controller结尾的类都会被当成控制器
* `base-package`下所有加了`@Controller`注解的类都会被当成控制器

如：

```java
public class HomeController{

}

@Controller
public class Home{

}
```

以上两个类都是leap的控制器。

