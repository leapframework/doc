# 实体字段的操作控制

## 需求

在 Swagger 的规范中，对于定义对象的字段只有类型相关的属性。比如以下 User 对象的两个字段：

```json
{
  "User": {
    "type": "object",
    "properties": {
      "id": {
        "type": "integer",
        "format": "int64"
      },
      "name": {
        "type": "string"
      }
    }
  }
}
```

当我们这个对象作为请求参数的类型时，对于调用端来说，他无法通过文档知道这个对象的哪些字段是可以在查询接口中用来过滤或者排序的，对于服务端来说，我们也无法通过现有的文档规范告知调用端这个信息。

因此针对这个需求，我们**对 Swagger 定义对象的属性进行了扩展**，用于对定义对象属性进行操作控制。

## 定义

我们在 Swagger 定义对象的属性上扩展了如下属性：

- `x-creatable`: 标识这个字段是否可由客户端指定值创建。对于普通字段来说，默认值是 `true`；对于 id 字段来说，默认值是 `false`，即 id 默认由服务端生成。

- `x-updatable`: 标识这个字段是否可由客户端指定值更新。对于普通字段来说，默认值是 `true`；对于 id 字段来说，默认值是 `false`。

- `x-sortable`: 标识这个字段是否可在查询接口中作为排序字段使用。对于所有字段来说，默认值都是 `false`。

- `x-filterable`: 标识这个字段是否可在查询接口中作为过滤条件字段使用。对于所有字段来说，默认值都是 `false`。

了解了扩展属性以及默认值，假如我们需要在服务端自己定义某个字段上的扩展属性的开启状态，我们可以在实体字段上使用注解：

- @Creatable

- @Updatable

- @Sortable

- @Filterable

可以看到注解名称与扩展属性名称都是一一对应的，如果是要对某个字段的操作控制进行关闭，则在注解参数中指定 `false`，例如 `@Updatable(false)` 。默认不加注解参数则注解参数默认值为 `true` 。

注意以上定义和默认值并不仅仅是用于显示，在调用 ModelController 相关接口处理时也会做操作控制校验。

## 示例

假设我们在代码里对 User 实体进行了以下注解：

```java
@Table("user")
public class User extends Model {
    @Id
    @Creatable
    private String id;

    @Column
    @Filterable
    @Sortable
    private String name;

    // 省略 getter 和 setter
}
```

我们将原来不可由前端指定值的 id 字段改为可指定值的，并且将 name 字段改为可排序和可用于过滤条件。

假设我们在某个 API 接口使用了这个实体，那么在启动后的 `/swagger.json` 接口里面我们将可以看到关于 User 对象的定义如下：

```json
User: {
    type: "object",
    title: "User",
    required: [
        "id"
    ],
    properties: {
        id: {
            type: "string",
            x-creatable: true,
            x-updatable: false,
            x-sortable: false,
            x-filterable: true
        },
        name: {
            type: "string",
            x-creatable: true,
            x-updatable: true,
            x-sortable: true,
            x-filterable: true
        }
    }
}
```