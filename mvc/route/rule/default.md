# 默认规则

## 默认映射

在我们定义好控制器类和路由方法，在**没有使用注解自定义路由映射**之前，默认的映射规则如下：

- **控制器类 Path** ：取类名，去除 `Controller` 后缀，将命名风格从驼峰式转为小写下划线形式。

    > 如名称为 `UserInfoController` 的控制器类，映射出的 Path 将是 `/user_info` 。

- **路由方法 Path** ：取方法名，将命名风格从驼峰式转为小写下划线形式。

    > 如名称为 `queryFirstUser` 的路由方法，映射到的 Path 将是 `/query_first_user` 。

- **最终映射路由**：将控制器 Path 和路由方法 Path 拼接起来，就是具体路由方法的最终映射路由。

    > 即 `UserInfoController` 中的 `queryFirstUser` 方法最终映射为 `/user_info/query_first_user` 。

- **映射 HTTP Method** ：默认所有路由接口支持任意 HTTP Method 请求。

Leap 推荐的 Url 路由命名规范是使用小写下划线命名风格。

如果觉得这样的默认映射规则不适用，我们还可以自定义路由映射。

## 约定映射

### 类名约定

Leap 默认约定名为 `HomeController` 控制器类**在没有使用注解自定义路由映射的情况下**，映射到的控制器类 Path 是根路径 `/` 。

例如下面的代码：

```java
public class HomeController{
    public void user(){
        // do something
    }
}
```

路由方法 `user()` 最终映射路由将是 `/user` 而不是 `/home/user` 。

### 方法名约定

Leap 默认约定名为 `index` 的路由方法**在没有使用注解自定义路由映射的情况下**，映射到路由方法 Path 是空或 `/` 。

例如下面的代码：

```java
public class UserController{
    public void index(){
        // do something
    }
}
```

路由方法 `index()` 最终映射路由将是 `/user` 或 `/user/` ，而不是 `/user/index` 。

> 参照上面两个约定，那么如果需要定义路由处理应用上下文根路径上的请求，只需要在 `HomeController` 中定义一个 `index` 方法即可。