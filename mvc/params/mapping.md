# 映射规则

## 默认规则

从前面的例子中我们可以看到，**接口参数的名称默认是与路由方法参数的名称是一致的**。

## 自定义映射

使用 `@RequestParam` 注解可以自定义参数的映射名称。

示例如下：

```java
public class UserController{
    public void find(@RequestParam("user_id") Integer id){
        // do something
    }
}
```

此时客户端传递的 `user_id` 参数将会被映射给路由方法参数 `id` 。

## 路径参数

路由参数映射也是平常开发项目中比较常用到的，需要配个控制器类或路由方法上的自定义路径映射来使用。

在自定义路径 Path 中我们使用 `{}` 来包裹路径参数，并在路由方法中使用 `@PathParam` 来指定引用路径参数。

举个例子：

```java
@Path("/class/{param1}")
public class PathParamController {

    @GET("method/{param2}")
    public String path(@PathParam("param1") String p1, @PathParam("param2") String p2) {
        return p1 + p2;
    }
}
```

当我们访问 `/class/myclass/method/mymethod` 的时候，服务端将返回 `myclassmymethod` 。

## 其他映射

除了这些，还有以下参数映射可以使用。

- `@HeaderParam` ：指定映射 Header 头信息中的字段；

- `@CookieParam` ：指定映射浏览器 cookie 中的字段。

> 以上提到的所有注解都在包 `leap.web.annotation` 下。