# 自定义路由映射

从前面我们可以看到最终映射路由是由控制器 Path 和路由方法 Path 各自解析之后拼接得到的。

所以自定义路由其实也是从这两部分入手。

## 自定义控制器类 Path

使用 `@Path` 注解可以给控制器类定义 Path。

例如：

```java
@Path("/myHome")
public class HomeController{

}
```

> `@Path` 注解的完整路径名称是 `leap.web.annotation.Path`。

这样 `HomeController` 中所有的路由方法的路由前缀都将是 `/myHome` 。

需要注意的是，注解 `@Path` 的值前缀带斜杠 `/` 和不带斜杠，在下面的情况会导致不同的路由映射。

当控制器类是放在配置的** `base-package` 直接子包 `controller` 或 `controllers` 下的子孙包**中时：

- 当 `@Path` 的值**带斜杠前缀**时，按照上面介绍的规则映射，没有影响；

- 当 `@Path` 的值**不带斜杠前缀**时，会以 `base-package` 直接子包 `controller` 或 `controllers` 为根，映射路由将带上**根下的包路径**。

    > 例如：Leap 配置 `base-package` 为 `com.demo` ，在控制器类 `com.demo.controller.system.UserController` 上有 `@Path("user")`。

    > 则这个控制器类的 Path 将是 `/system/user` 。

这个功能主要是方便为不同模块的路由控制器添加统一的模块前缀。

## 自定义路由方法 Path 与 HTTP 请求

### HTTP Method 注解

最常用的自定义路由方法是使用以下对应的 HTTP Method 注解，并在注解的值中指定 Path。目前有以下可用注解：

- `@GET` ：`leap.web.annotation.http.GET`
- `@POST` ：`leap.web.annotation.http.POST`
- `@PUT` ：`leap.web.annotation.http.PUT`
- `@PATCH` ：`leap.web.annotation.http.PATCH`
- `@DELETE` ：`leap.web.annotation.http.DELETE`
- `@OPTIONS` ：`leap.web.annotation.http.OPTIONS`
- `@HEAD` ：`leap.web.annotation.http.HEAD`
- `@ANY` ：`leap.web.annotation.http.ANY`，即接受任意 HTTP 请求方法。

使用示例如下：

```java
public class UserController{

    @GET("queryOne")
    public void find(){
        // do something
    }
}
```

以上路由接口最终的映射路由将是 `/user/queryOne`，只接受 `GET` 请求。

> 目前不支持一个路由方法上使用以上多个注解，多个注解的情况下将只取第一个。

### @RequestMapping 注解

除了以上注解，还有另外一种类似 Spring MVC 的注解也可以做以上自定义，就是使用 `@RequestMapping` 注解。

`@RequestMapping` 注解有 `path` 属性和 `method` 属性分别指定 path 和 HTTP Method。

示例如下：

```java
public class UserController{

    @RequestMapping(method = HTTP.Method.GET, path = "queryOne")
    public void find(){
        // do something
    }
}
```

这个示例最终映射的路径与上面的示例完全一样。

> `@RequestMapping` 注解的完整路径名称是 `leap.web.annotation.RequestMapping`。

> `@RequestMapping` 支持让一个路由方法映射多个路径和 HTTP Method，只需要使用`@RequestMappings`。

### @Path 注解

路由方法也支持使用 `@Path` 自定义 Path，只不过这种方式无法定义支持的 HTTP Method，使用起来还不如上面两种方式，因此不推荐。

### Path 值

路由方法自定义的 Path 值，带斜杠 `/` 前缀和不带斜杠前缀在映射路径时会有不同的处理：

- 当 Path 前缀**不带斜杠前缀**时，路由方法路由映射按照前面介绍的规则映射，即控制器 Path 拼接路由方法 Path；

- 当 Path 前缀**带斜杠前缀**时，路由方法路由映射直接为路由方法 Path ，不带控制器类 Path 前缀。

第二种情况的意思就是当路由方法指定了带斜杠 `/` 前缀的路径，则这个路由接口的路径就直接以此为准，不管这个路由方法所在的控制器类是否自定义过 Path 前缀。

还是上面的例子，假如我们改成这样：

```java
public class UserController{

    @GET("/queryOne")
    public void find(){
        // do something
    }
}
```

对比上面的代码只是加多了一个斜杠，但是映射到的最终路由将是 `/queryOne` 。