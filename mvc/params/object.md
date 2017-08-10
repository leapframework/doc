# 对象参数类型

## 服务端

使用对象参数类型会使一些接口的参数更加容易理解、组织与维护。

例如我们有一个创建用户 User 的接口，用户 User 有两个字段 id 和 name 。

按照之前我们可能会通过简单类型来定义这个创建接口，例如：

```java
public class UserController{
    public void create(Integer id, String name){
        // do create
    }
}
```

但是如果改成直接使用对象参数，比如这样：

```java
public class UserController{
    public void create(User user){
        // do create
    }
}
```

两者是完全等价的，客户端调用格式也不需要更改。使用 `/user/create?id=123&name=zhangsan` 都可以调用以上两个接口。

## 客户端

当路由方法参数中只定义一个复杂类型的参数时，客户端调用时使用的参数名可以是该复杂类型中的字段，Leap 会自动进行映射。

除了以上这种参数格式，Leap 还支持另外两种参数传递形式：

- **JSON 格式**：这种格式也是只支持一个复杂类型参数的情况；

    > 针对上面的接口，JSON 格式的请求如下：

    ```
    POST /user/create HTTP/1.1
    Content-Type: application/json
    Host: localhost:8080

    {id:123, name:"zhangsan"}
    ```

- **全路径的属性参数**：因为参数是全路径的，因此可以支持一个路由方法中定义多个复杂类型参数的情况。

    > 针对上面的接口，这种方式的请求如下：

    ```
    POST /user/create HTTP/1.1
    Content-Type: application/x-www-form-urlencoded
    Host: localhost:8080

    user.id=123&user.name=zhangsan
    ```

    > 假如现在在这个接口上加多一个公司 Company 类型的参数 `company` ，字段也是有 `id` 和 `name` ，那么只需要将请求体改成如下即可传递公司相关信息：

    > `user.id=123&user.name=zhangsan&company.id=999&company.name=mycompany`