# API路由规则

API的路由规则和leap mvc的路由规则不同，API的路由规则按照[`JAX-RS`标准](https://jax-rs-spec.java.net/)定义。

如：

```java
@Path("/api1")
public class Api1Controller extends ApiController {
    @GET
    public ApiResponse path1(){
        return ApiResponse.ACCEPTED;
    }
    @POST
    public ApiResponse path2(){
        return ApiResponse.ACCEPTED;
    }
    @DELETE
    public ApiResponse path3(){
        return ApiResponse.ACCEPTED;
    }
    @PATCH
    public ApiResponse path4(){
        return ApiResponse.ACCEPTED;
    }
    @GET("/path5")
    public ApiResponse path5(){
        return ApiResponse.ACCEPTED;
    }
}
```

生成的路由表如下：

```
METHOD  PATH                     ACTION                                         DEFAULT VIEW
------  ----------------------   --------------------------------------------   ------------------------------
POST    /demo-api/api1           Api1Controller.path2                           /demo-api/api1
PATCH   /demo-api/api1           Api1Controller.path4                           /demo-api/api1
GET     /demo-api/api1           Api1Controller.path1                           /demo-api/api1
DELETE  /demo-api/api1           Api1Controller.path3                           /demo-api/api1
GET     /demo-api/api1/path5     Api1Controller.path5                           /demo-api/api1/path5
```

