# API注解

leap的API开发是按照`JAX-RS`标准实现的，注解的使用方法基本按照标准规定的方式使用。

## 路径注解

路径注解决定了一个Action需要使用怎么样的uri访问，一般来说，Action的uri组成如下：

```
/{base-path}/{controller_path}/{action_path}
```

这里`base-path`我们在api配置了，`controller_path`是在controller上通过`@Path`注解指定的，如果没有指定，默认是`@Path("/")`这样的注解，最后的`action_path`有两种方式指定，一种是通过`@Path`和请求方法注解（`@GET`、`@POST`等）联合指定的，另一种是直接在请求方法注解中指定path。

如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<apis xmlns="http://www.leapframework.org/schema/web/apis">
    <api name="demo-api" base-path="/demo-api" base-package="demo.api"></api>
</apis>
```

```java
@Path("/user")public class UserController extends ApiController {
    // 使用@GET和@Path指定action的访问uri
    @GET
    @Path("/all")
    public ApiResponse<List<UserModel>> getAllUser(){
        return ApiResponse.of(UserModel.all());
    }
    // 推荐：使用@GET("/all")的方式指定action的访问uri
    @GET("/all")
    public ApiResponse<List<UserModel>> getAllUsers(){
        return ApiResponse.of(UserModel.all());
    }
}
```

上面的配置和代码，最终生成的路由表如下：

```
METHOD PATH                   ACTION                                      DEFAULT VIEW
------ ---------------------- ------------------------------------------- ------------------------------
GET    /demo-api/user/all     UserController.getAllUser                   /demo-api/user/all
GET    /demo-api/user/all     UserController.getAllUsers                  /demo-api/user/all
```

上面的例子使用的是`@GET`指定http请求方法，实际上对于其他的请求方法注解（`@POST`，`@DELETE`，`@PATCH`等）用法也是一样的。

> **注意：**按照`JAX-RS`的规定，Action的http请求方法是必须指定的，因此每一个Action都必须有至少一个请求方法注解，没有请求方法注解的函数是不会被解析为Action的。

## 路径表达式

在`JAX-RS`的规范中，定义了一些特殊的路径表达式，可以作为模板匹配URL。

如：

```java
@GET("/user/{id}")
public ApiResponse<List<UserModel>> getAllUsers(){
    return ApiResponse.of(UserModel.all());
}
```

表示匹配所有`/user/*`的uri，并且将`*`作为路径参数，如：

```
GET /user/abc
GET /user/def
```

上面两个路径都会被这个Action处理，并且得到的路径参数id分别为`abc`和`def`。

