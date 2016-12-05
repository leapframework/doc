# API返回值

leap的API开发使用特定的返回值对象，`ApiResponse<T>`，这是一个泛型对象，真正返回的对象实际上是`T`的json结果，如：

```java
@GET
public ApiResponse<List<UserModel>> getAllUser(QueryOptions options){
    return ApiResponse.of(UserModel.all());
}
```

生成的swagger中返回值的描述如下：

```javascript
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

definitions: {
  UserModel: {
    type: "object",
    title: "UserModel",
    properties: {
      id: {
        type: "string",
        "x-creatable": false,
        "x-updatable": false,
        "x-sortable": false,
        "x-filterable": false
      }
    }
  }
}
```

这里我们就可以看到返回值完整的描述了`List<UserModel>`这个对象，现在我们在swagger ui上看一下完整的swagger生成的文档：

![swagger ui文档](/assets/full_swagger.png)