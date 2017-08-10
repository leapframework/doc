# 生成操作日志

有些项目需要记录用户的操作日志用于安全审计或行为分析等，这个功能在 Leap 中没有内置，但 Leap 提供了一种简单的方式来自行实现。

下面通过示例代码演示如何把操作日志保存到数据库中。

## 创建实体

创建一个 `OperationLog` 实体用于保存操作日志：

[import](codes/OperationLog.java)

## 实现拦截器

实现路由拦截器 `OperationLogInterceptor` ：

[import](codes/OperationLogInterceptor.java)

## 配置拦截器

修改 `src/main/resources/conf/beans.xml` ：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.leapframework.org/schema/beans">
    ...
    <bean type="leap.web.action.ActionInterceptor" class="hello.interceptors.OperationLogInterceptor"/>
</beans>
```

## 验证测试

为了更访问的测试，我们实现两个 API 接口（一个成功，一个失败）：

在 `src/main/java/hello/controllers/GreetingController.java` 中创建两个方法：

```java
public class GreetingController extends ApiController {
    ...

    @POST("/test_log_ok")
    public ApiResponse testLogOk() {
        return ApiResponse.OK;
    }

    @POST("/test_log_err")
    public ApiResponse testLogErr() {
        return ApiResponse.err(HTTP.Status.BAD_REQUEST, "err");
    }
}
```


启动工程（需要接入 `debug-auth-server` ）：

获取访问令牌：

```
**[terminal]
curl -X POST http://localhost:8088/oauth2/token\?grant_type\=password\&username\=user1\&password\=pass1\&client_id\=client1\&client_secret\=secret1
```

分别访问刚才创建的两个API操作：

```
**[terminal]
curl -X POST http://localhost:8080/greeting/test_log_ok\?access_token\=24579fa7-c5ef-411e-9b04-5cd62ee72bd8

curl -X POST http://localhost:8080/greeting/test_log_err\?access_token\=24579fa7-c5ef-411e-9b04-5cd62ee72bd8
```

打开浏览器访问 `http://localhost:8080/operation_log` 。

如果看到以下的内容，说明日志保存成功。

```json
[
	{
   		id: 1,
   		verb: "POST",
   		path: "/greeting/test_log_ok",
   		name: "testLogOk",
   		title: "测试成功操作",
   		userId: "1d@3KIkWi",
   		loginName: "user1",
   		success: true,
   		status: 200,
   		timestamp: "2017-06-14T15:40:14.807+0800"
	},		
	{
   		id: 2,
   		verb: "POST",
   		path: "/greeting/test_log_err",
   		name: "testLogErr",
   		title: "测试失败操作",
   		userId: "1d@3KIkWi",
   		loginName: "user1",
   		success: false,
   		status: 400,
   		timestamp: "2017-06-14T15:40:32.311+0800"
	}
]
```

> 需要开启 Restd 自动生成 `operation_log` 的访问接口。