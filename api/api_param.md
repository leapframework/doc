# API参数

leap的参数绑定本身非常智能，api开发模块还另外提供了一些内置的参数对象。

## QueryOptions

**QueryOptions**参数对象，是用于做通用查询参数的对象，在Action方法中声明这个参数，leap就会自动为文档生成这些特定的查询参数，如：

```java
@GET
public ApiResponse<List<UserModel>> getAllUser(QueryOptions options){
    return ApiResponse.of(UserModel.all());
}
```

```javascript
/user: {
get: {
description: "",
operationId: "getAllUser",
parameters: [
{
name: "page_size",
description: "",
in: "query",
type: "integer",
format: "int32"
},
{
name: "page",
description: "",
in: "query",
type: "integer",
format: "int32"
},
{
name: "limit",
description: "",
in: "query",
type: "integer",
format: "int32"
},
{
name: "offset",
description: "",
in: "query",
type: "integer",
format: "int32"
},
{
name: "total",
description: "",
in: "query",
type: "boolean"
},
{
name: "orderby",
description: "",
in: "query",
type: "string"
},
{
name: "filters",
description: "",
in: "query",
type: "string"
},
{
name: "select",
description: "",
in: "query",
type: "string"
},
{
name: "expand",
description: "",
in: "query",
type: "string"
}
],
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
```