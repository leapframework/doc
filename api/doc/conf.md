# 基于配置生成文档

在默认情况下，只要工程配置了 API 模块，Leap 就自动为我们生成了 API 文档，并且自动创建了 web 访问接口。

假设我们配置了以下的 API 模块：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<apis xmlns="http://www.leapframework.org/schema/webapi">
    <api name="hello" base-path="/">
    </api>    
</apis>
```

并且我们工程在本地部署时应用的上下文路径是 `hello` ，那么自动生成的文档访问接口则为：

`http://localhost:8080/hello/swagger.json`

即**在对应 API 模块的 base-path 后加上 /swagger.json 便是文档地址。**

当我们访问这个网址的时候，Leap 会根据当前 API 模块的路由遍历生成 Swagger 文档，无需再做额外配置。

以下是接口返回的例子：

```json
{
  "swagger": "2.0",
  "info": {
    "title": "demo-api",
    "description": "",
    "version": "1.0"
  },
  "host": "localhost:8080",
  "basePath": "/hello",
  "consumes": [
    "application/json"
  ],
  "produces": [
    "application/json"
  ],
  "paths": {
    "/user": {
      "get": {
        "description": "",
        "operationId": "all",
        "responses": {
          "200": {
            "summary": "Success",
            "description": "Success",
            "schema": {
              "type": "array",
              "items": {
                "$ref": "#/definitions/User"
              }
            }
          }
        }
      }
    }
  },
  "definitions": {
    "User": {
      "type": "object",
      "title": "User",
      "required": [
        "name"
      ],
      "properties": {
        "id": {
          "type": "string"
        },
        "name": {
          "type": "string"
        }
      }
    }
  }
}
```

Swagger 文档在一般使用的情况下，我们只需要关注返回内容中 JSON 对象的几个属性：

* info ：通常是作为模块文档整体的标题和介绍，比如介绍一些业务概念、业务规则等；
* paths：这就是我们文档的重点，路由接口列表的文档说明，其中包括了接口路径、方法、请求参数和响应内容等。
