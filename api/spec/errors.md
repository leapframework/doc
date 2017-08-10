# 错误处理

API 错误分为两种类型：
* 客户端错误（ Client Errors )
* 服务端错误（ Server Errors ）

当 API 请求处理发生错误时，应向调用端返回错误信息。

错误信息包含两部分：

* HTTP 错误状态码：

状态码 | 名称                | 描述
----- | -------------| -----
4xx     | Client Errors      | 400~4xx，表示请求方原因产生的错误
- 400  | Bad Request      | 请求方输入的参数或者格式不正确
- 401  | Unauthorized      | 请求方未经过身份认证
- 403 | Forbidden           | 请求方没有权限访问
- 404 | Not Found          | 访问的接口或数据不存在
5xx     | Server Errors     | 500~5xx，表示服务端原因产生的错误
- 500  | Internal Server Error | 服务端产生非预期的错误，具体需要看错误代码和描述
- 501  | Not Implemented      | 请求的接口未实现
- 503 | Service Unavailable   | 服务端无法处理请求

* JSON 格式的错误对象：

```json
{
    "code": "BAD_REQUEST",
    "message": "请输入'name'参数"
}
```

