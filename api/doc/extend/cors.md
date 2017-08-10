# 路由 CORS 控制

## 需求

我们希望在路由接口上可以有一个字段用于标识此接口是否支持 CORS，即跨域资源共享。

## 定义

我们在 Swagger 的 `path` 对象上新增了 `x-cors` 属性。

默认 API 接口的 `x-cors` 属性都为 `true`。

一般 API 接口都需要支持 CORS，除非有极特殊情况下需要禁用某个接口的 CORS 支持，可以在接口方法上增加以下注解：

`@Cors(false)`

## 示例

以下是两个接口的 swagger 文档示例，一个支持 cors ，一个不支持。

```json
{
  "/user": {
    post: {
      x-cors: true,
      tags: [
        "User"
      ],
      description: "",
      operationId: "createUser",
      parameters: [],
      responses: {
        ...
      }
    },
    get: {
      x-cors: false,
      tags: [
        "User"
      ],
      description: "",
      operationId: "all",
      responses: {
        ...
      }
    }
  }
}
```