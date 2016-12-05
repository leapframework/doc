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

这里我们可以看到，在生成的swagger文档中添加了多个通用查询参数，如：`page_size`表示分页查询的每页大小，`total`表示是否返回记录总数等，这里说明一下各个参数的含义：

|参数|类型|作用|
|---|---|---|
|page_size|integer|指定查询页大小|
|page|integer|指定查询页码|
|limit|integer|指定查询行，类似mysql中的limit|
|offset|integer|查询偏移量，表示偏移几行开始返回，可以了limit配合使用实现分页|
|total|boolean|是否返回查询总数|
|orderby|string|排序表达式，如：orderby=name desc或orderby=name asc|
|filters|string|查询表达式，通过特定的语法实现查询过滤|
|select|string|查询字段，可以指定查询的字段，如select=name,id|
|expand|string|展开表达式，可以指定有关联的对象展开查询|

关于查询参数的使用，我们将在后续的章节详细介绍。

## DeleteOptions

**DeleteOptions**参数对象，用于删除对象时使用，如：

```java
@DELETE("/{id}")
public ApiResponse deleteUser(String id, DeleteOptions options){
    return ApiResponse.ACCEPTED;
}
```

```javascript
/user/{id}: {
  delete: {
    description: "",
    operationId: "deleteUser",
    parameters: [
      {
        name: "id",
        description: "",
        in: "path",
        required: true,
        type: "string"
      },
      {
        name: "cascade_delete",
        description: "",
        in: "query",
        type: "boolean"
      }
    ],
    responses: {
      200: {
        summary: "Success",
        description: ""
      }
    }
  }
}
```

这里会自动添加一个`cascade_delete`参数，这个参数主要是指定是否级联删除。

## Partial&lt;T&gt;

**Partial&lt;T&gt;**是一个特定的请求体接收类，可以自动把json格式的请求体转换为这个对象并以类似map的方式使用，声明泛型T之后，还可以使用`T getObject()`方法取到转换后的对象。

```java
@PATCH("/{id}")
public ApiResponse updateUser(String id, Partial<UserModel> partial){
    return ApiResponse.ACCEPTED;
}
```

```javascript
patch: {
  description: "",
  operationId: "updateUser",
  parameters: [
    {
      name: "id",
      description: "",
      in: "path",
      required: true,
      type: "string"
    },
    {
      name: "partial",
      description: "",
      in: "body",
      required: true,
      schema: {
        $ref: "#/definitions/UserModel"
      }
    }
  ],
  responses: {
    200: {
      summary: "Success",
      description: ""
    }
  }
}
```

这里我们看到swagger中多了一个参数`partial`，这个参数需要使用json在请求体中传递。

以上是leap目前内置的比较常用的参数对象，在后续的章节中，我们会详细介绍这几个参数在leap开发API中的使用。