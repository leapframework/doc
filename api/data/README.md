# 数据 API 开发

以上介绍的是基础 API 开发，但是在实际开发中，基于实体的数据 API 开发更为常见。

因此**在实际开发使用中，建议所有跟具体实体相关的数据 API 路由类都继承于 `leap.web.api.mvc.ModelController`** 而不是 `ApiController` 。

`ModelController` 其实也是继承于 `ApiController`，它提供了更多内置的方法供子类调用。创建 `ModelController` 的示例代码如下：

```java
public class UserController extends ModelController<User> {
    ...
}
```

可以看到在类的声明上 `ModelController` 有一个泛型参数，这个是指定这个 Controller 所对应的实体。在这个 Controller 下的所有接口都应该是跟这个实体相关的。

下面将从增删改查这个角度来展示 `ModelController` 对数据 API 开发的支持。