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

## Action

Action是方法，也是真正处理请求的单位，每一个Action都会对应一个请求路径。默认情况下，Controller下的所有public方法都会被当成Action。

如：

```java
public class HomeController{
    public void index(){

    }

    private void user(){

    }
}
```

这里`index`方法是一个Action，而`user`方法则不是。

## 路由表

leap启动完成之后，一般会打印如下路由表(如果没有打印，请检查日志级别或者是否有配置slf4j):

```
METHOD  PATH                     ACTION                 DEFAULT VIEW
------  ----------------------   --------------------   ------------------------------
*       /user_controller         UserController.index   (none)
*       /user_controller/list    UserController.list    (none)
*       /user_controller/index   UserController.index   (none)
```