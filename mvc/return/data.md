# 返回数据

## 返回内容

定义返回数据的接口在我们之前的例子中已经演示过了，Leap **使用路由方法的返回作为接口的返回内容，并且自动进行 JSON 序列化**。

简单示例如下：

```java
public class UserController {

    public User get(String id) {
        return User.findOrNull(id);
    }
}
```

以上示例接口根据传递参数 `id` 查询 `User` 表记录，有则返回 `User` 对象的 JSON 序列化结果，无则返回 `null` 。

## 返回内容与状态码

除了这种方式，还有另外一种方式，就是使用 `leap.web.ResponseEntity` ：

- 可以直接返回 `ResponseEntity` ，主要可以设置返回状态码 `status` 和返回内容 `entity`；

- 或者工程自定义一个继承于 `ResponseEntity` 的类作为统一返回类型。

无论是以上哪种方式，Leap 最终都是调用 `ResponseEntity` 的接口方法获取**返回状态码和返回内容**返回给前端。

下面是是一个简单示例：

```java
public class UserController {

    public ResponseEntity find(String id) {
        User user = User.findOrNull(id);
        return ResponseEntity.of(HTTP.Status.OK, user);
    }
}
```