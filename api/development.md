#API开发

使用leap开发api是一个非常简单的过程。

假设我们已经在`conf/apis.xml`中配置了如下api：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<apis xmlns="http://www.leapframework.org/schema/web/apis">
    <api name="demo-api" base-path="/demo-api" base-package="demo.api"></api>
</apis>
```

现在我们开始为这个api开发服务接口。

首先需要在`demo.api`包（或子包）下创建一个控制器：

```java
@Path("/user")
public class UserController extends ApiController {
    @GET
    public ApiResponse<List<UserModel>> getAllUser(){
        return ApiResponse.of(UserModel.all());
    }
}
```