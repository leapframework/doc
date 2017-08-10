# 启用 Restd

## 配置

在我们 API 模块的配置文件中，我们可以这样配置：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<apis xmlns="http://www.leapframework.org/schema/webapi">
    <api name="restd" base-path="/" restd-enabled="true">
    </api>
</apis>
```

**启用 Restd 只需要将 API 配置中 api 元素的 restd-enabled 置为 true 即可**。

## 效果

启用后，相当于我们为所有实体各自创建了这样一个 API Controller （这里以 User 实体为例）：

```java
@Path("/user")
public class UserController extends ModelController<User> {

    @GET
    public ApiResponse<List<User>> query(QueryOptions options) {
        return queryList(options);
    }

    @GET("/{id}")
    public ApiResponse<User> find(@PathParam Object id, QueryOptionsBase options) {
        return get(id, options);
    }

    @POST
    public ApiResponse create(Partial<User> partial) {
        return createAndReturn(partial);
    }

    @DELETE("/{id}")
    public ApiResponse delete(@PathParam Object id, DeleteOptions options) {
        return delete(id, options);
    }

    @PATCH("/{id}")
    public ApiResponse update(@PathParam Object id, Partial<User> partial) {
        return updatePartial(id, partial);
    }
}
```