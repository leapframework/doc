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