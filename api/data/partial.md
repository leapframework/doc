# 实体操作

## 使用内置实体参数和方法

当我们在做实体新增或修改的时候，需要传递一个或多个对象给服务端进行相应操作。

通常情况下我们只需要在路由方法参数上声明相应实体类即可接收，但在一些情况下会有问题，例如请求参数命名与实体属性命名不一一对应，或者在提交修改的场景下，如果用完整实体类接收无法确定那些 null 值属性是客户端真的想置为 null， 还是根本就没传参数。

这种情况下除了针对每个接口定义对应 POJO 类的解决方法之外，还可以使用 `Partial<T>` 解决。

Partial&lt;T&gt; 相当于一个 Map，partial 的英文含义是“部分的”，意思是可以帮我们接收我们需要的一部分参数。我们将这个类型作为接口的方法参数后，当它为我们接收到参数时，我们可以使用这个对象的以下方法：

* 通过 getProperties 方法可以获取具体传递参数的 Map 对象。
* 通过 getObject 方法可以将 Map 转化为对应实体对象。不过如果只是这样用的话我们建议你还不如直接在接口方法上定义这个对象类型的参数。

当然最便捷的方法还是将它传递给 ModelController 内置方法去处理相应的新增或修改，如下图：

```java
@Path("/user")
public class UserController extends ModelController<User> {
    @POST
    public ApiResponse create(Partial<User> partial) {
        return createAndReturn(partial);
    }

    @PATCH
    public ApiResponse update(String id, Partial<T> partial){
        return updatePartial(id, partial);
    }     
}
```

> 关于 ModelController 更多内置方法的使用，可以直接在 IDE 中查看源码或 ModelController 类的方法列表，根据方法名和参数以及返回类型一般就可以了解这个方法是做什么的了。