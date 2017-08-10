# 实体查询

## 使用内置方法

对于一个实体，常见的查询接口有两种，一种是单个查询，一种是批量查询。

`ModelController` 内置的两个对应的方法如下：

```java
public class UserController extends ModelController<User> {

    @GET
    public ApiResponse<List<User>> query(QueryOptions options) {
        return queryList(options);
    }

    @GET("/{id}")
    public ApiResponse<User> find(@PathParam Object id, QueryOptionsBase options) {
        return get(id, options);
    }
}
```

当我们定义完这样两个 API 路由接口之后，具体查询操作都调用父类 ModelController 的内置方法去实现，并且没有其他业务逻辑的时候，其实这个接口已经可以实际使用了。

只不过我们仔细看接口方法的参数，使用了两个我们还不认识的对象 QueryOptions 和 QueryOptionsBase 去接收参数。那么这两个对象会为我们接收什么参数呢？

## 使用内置查询参数

在实际的开发过程中一般查询接口涉及到的许多查询参数如 `page`、`page_size`、`limit`、`offset`、`orderby`等都是很常见而且都是可以通用的。

因此 Leap 提供的 QueryOptions 就基本包含了所有查询中需要用到的参数，我们在实际开发过程中可以将路由方法的参数定义为 QueryOptions 类型来接收相关查询参数。QueryOptions 类型中的查询参数属性有以下这些：

| 参数 | 类型 | 作用 |
| :--- | :--- | :--- |
| page\_size | integer | 指定查询页大小 |
| page | integer | 指定查询页码，从1开始。 |
| limit | integer | 指定查询行，类似mysql中的limit |
| offset | integer | 查询偏移量，表示偏移几行开始返回，可以与limit配合使用实现分页。从0开始。 |
| total | boolean | 是否返回查询总数 |
| orderby | string | 排序表达式，如：orderby=name desc或orderby=name asc |
| filters | string | 查询表达式，通过特定的语法实现查询过滤 |
| select | string | 返回字段，可以指定返回的字段，如select=name,id |
| expand | string | 展开表达式，可以指定有关联的对象展开查询 |
| joins | string | 指定连接查询的关系名和别名，在查询时便可使用关系实体的字段 |

QueryOptionsBase 是 QueryOptions 的父类，只包含了 select 和 expand 字段，主要用于单个查询时指定要返回实体的字段和展开对象。

当我们使用 QueryOptions 接收到接口参数时，我们只要把这个参数传给 ModelController 对应方法，它就会按照上面的规则帮我们处理数据。当然，这个需要我们前端传递过来的参数格式符合 Leap 的要求。

下面就是以上一些参数格式的要求。