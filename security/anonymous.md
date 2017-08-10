# 设置匿名访问

项目在接入授权服务器后，默认情况下所有的请求都需要经过身份认证；但有些情况下，我们需要允许部分接口操作是可以匿名访问的。

目前匿名访问通过注解 `leap.core.security.annotation.AllowAnonymous` 来实现，有两种设置方式：

## 在控制器类上注解

```java
import leap.core.security.annotation.AllowAnonymous;

@AllowAnonymous
public class UserController ... {
    ...
}
```

这样 `UserController` 定义的所有路由方法都默认允许匿名访问。

## 在路由方法上注解

```java
@GET
@AllowAnonymous
public void queryCategories(...) {
    ... 
}
```

如果控制器类上默认开启了匿名访问，但是某个方法不允许匿名访问，也可以这么设置：

```java
@GET
@AllowAnonymous(false)
public void queryCategories(...) {
    ... 
}
```

