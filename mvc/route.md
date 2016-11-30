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

leap启动完成之后，一般会打印类似如下路由表(如果没有打印，请检查日志级别或者是否有配置slf4j):

```
METHOD  PATH                     ACTION                     DEFAULT VIEW
------  --------------------- ----------------------------- ------------------------------
*       /user_model           UserModelController.index     (none)
*       /user_model/list_user UserModelController.listUser  (none)
*       /user_model/index     UserModelController.index     (none)
```

第一个`METHOD`表示的是请求方法如`POST`，`GET`等，`*`表示接受所有方法，第二个`PATH`表示的是请求的URI，`ACTION`表示请求处理的Action，最后一个`DEFAULT VIEW`表示默认的返回视图页面。

这里我们主要看一下`PATH`到`ACTION`的映射规则：

* 把类名中的Controller的后缀去掉，然后将驼峰式命名转为下划线命名：`UserModelController → user_model`
* 把方法的名字从驼峰式转成下划线命名风格：`listUser → list_user`
* Controller的路径加action的路径即是action的唯一访问uri:`UserModelController.listUser → /user_model/list_user`

这个就是leap MVC的路由规则。