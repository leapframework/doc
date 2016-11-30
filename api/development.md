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

> **注意：**所有的API控制器都必须继承或间接继承`ApiController`

这里的`UserModel`类声明如下：

```java
public class UserModel extends Model {
    private String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
```

现在我们已经有一个接口了，重新访问文档生成的接口`/demo-api/swagger.json`，可以得到如下json：

```javascript
{
    swagger: "2.0",
    info: {
        title: "demo-api",
        description: "",
        version: "1.0"
    },
    host: "localhost:8080",
    basePath: "/demo/demo-api",
    consumes: [
        "application/json"
    ],
    produces: [
        "application/json"
    ],

    paths: {
        /user: {
            get: {
                description: "",
                operationId: "getAllUser",
                responses: {
                    200: {
                        summary: "Success",
                        description: "",
                        schema: {
                            type: "array",
                            items: {
                                $ref: "#/definitions/UserModel"
                            }
                        }
                    }
                }
            }
        }
    },
    definitions: {
        UserModel: {
            type: "object",
            title: "UserModel",
            properties: {
                id: {
                    type: "string",
                    x-creatable: false,
                    x-updatable: false,
                    x-sortable: false,
                    x-filterable: false
                }
            }
        }
    }
}
```

这个时候我们把生成swagger的接口地址传给swagger ui的[示例工程](http://petstore.swagger.io/)，可以看到如下文档：

![接口文档](/assets/swagger.json.user.png)

这个时候可以看到，接口uri，返回值都已经可以看到了。我们可以点击`Try it out`按钮，直接测试接口就可以看到接口调用和返回的数据了:

![测试接口](/assets/swagger.json.user.try.png)
