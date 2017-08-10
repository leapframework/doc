# 简单参数类型

## 服务端

最常用最简单的接口参数类型就是简单参数类型。

定义简单参数类型建议**使用简单类型的包装类型**，例如不使用 `int` 类型而使用 `Integer` 类型。

这是由于简单类型在客户端未传值的情况下不会为 `null` ，而是会有默认值存在，这有可能影响接口方法的逻辑判断。

代码示例如下：

```java
public class UserController{
    public void find(Integer id, String name){
        // do something
    }
}
```

以上示例创建了一个路径为 `/user/find` 的接口，它接受所有 HTTP 请求方法，并且可接收两个参数，名称分别是 `id` 和 `name`。

## 客户端

对于客户端来说，有以下几种方式传递接口参数：

- GET 请求方式时，参数通过 URL 传递。

    > 如上例，调用 URL 则为 `/user/find?id=123&name=zhangsan`。

- 其他请求方式如 POST 时，参数可通过 URL 传递或请求体传递。通过请求体传递时，请求内容格式必须为 `x-www-form-urlencoded` 。

    > 如上例，调用请求内容则为：

    ```
    POST /user/find HTTP/1.1
    Content-Type: application/x-www-form-urlencoded
    Host: localhost:8080
    content-length: 20

    id=123&name=zhangsan
    ```