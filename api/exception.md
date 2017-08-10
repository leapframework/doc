# 错误处理

当业务系统发生预期或者非预期错误时，Leap 推荐可以通过以下两种方式返回错误响应。

错误响应规范可以参见[错误处理](/api/spec/errors.md)规范，通过以下方式处理的错误响应都是符合此规范的。

## Controller 层面调用 ApiResponse.err

当错误发生在 Controller 层面，例如传进来的参数不符合业务规范等，这个时候可以直接调用前面章节提到的 `ApiResponse` 类的静态方法 `err`。

例如：

```java
public class UserController extends ModelController<User> {

    @GET
    public ApiResponse doBusiness(Integer num) {
        if(num < 100) {
            // do business
            return ApiResponse.OK;
        } else {
            return ApiResponse.err(HTTP.Status.BAD_REQUEST, "num must less than 100!");
        }
    }
    ...
}
```

以上代码表示此路由要求参数 num 必须小于100，否则报错。

`err` 方法有两个重载形式：

```java
public static ApiResponse err(HTTP.Status status, String message);

public static ApiResponse err(HTTP.Status status,String errorCode, String message);
```

status 参数用于指定响应的 http 状态码，其他两个参数分别对应规范中的 code 和 message 。

## 根据异常抛出相应 Exception 对象

这种处理方式适用于代码中任何地方。Leap 已经为常见异常提供了内置 Exception 类，具体 Exception 类的名称为[错误处理](/api/spec/errors.md)规范列表中 name 列后面接 Exception。例如：

- Bad Request 对应的有 BadRequestException 类；
- Unauthorized 对应的有 UnauthorizedException类；
- ...

这样以上代码也可以改成这样：

```java
public class UserController extends ModelController<User> {

    @GET
    public ApiResponse query(Integer num) {
        if(num < 100) {
            // do business
            return ApiResponse.OK;
        } else {
            throw new BadRequestException("num must less than 100!");
        }
    }
    ...
}
```

对于 API 请求处理时抛出的错误，Leap 都会拦截捕获并按照规范返回错误响应。

由于规范中只是对于一般常见异常做了规范，其他在业务系统中可能产生的异常需要项目根据自身情况创建异常类。

这一类异常推荐统一继承于 `InternalServerErrorException` 类或使用 `InternalServerErrorException` 类本身。