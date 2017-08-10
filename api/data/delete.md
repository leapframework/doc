# 实体删除

## 使用内置删除参数和方法

对于删除接口的支持，Leap 提供了 DeleteOptions 参数，目前只有一个属性：cascade\_delete 。

主要用于指定删除的时候是否需要级联删除，注意这里的级联删除指的不是数据库的级联删除，而是基于 Leap 配置的 ORM 实体上的级联删除。同样这个参数也是可以传递给 ModelController 的内置方法去实现，代码示例如下：

```java
@Path("/user")
public class UserController extends ModelController<User> {
    @DELETE("/{id}")
    public ApiResponse deleteUser(String id, DeleteOptions options){
        return delete(id, options);
    }     
}
```