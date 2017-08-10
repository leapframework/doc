# 使用 expand 参数

## 需求

当我们查询某个实体的接口时，返回的一般都是那个实体的数据。

如果这个实体有其他关系实体的数据也需要一并获取，我们一般都是再发请求到其他实体接口上获取。

这样做逻辑很清晰，但是其实也很麻烦，要发多次请求。

既然已经有了实体关系，那么能不能在查询某个实体的时候，顺便获取关系实体的数据呢？

可以使用 expand 参数。

## 配置

使用这个参数，首先关系的配置是必须的。

目前 expand 参数可以支持多对一和多对多关系。

具体关系配置方法可以参考[实体关系](/orm/entity/relation/README.md)里的配置说明。

## 用法

这里我们借用多对一关系文档中宠物 Pet 和主人 Owner 之间多对一的关系作为例子。多对多关系也是同理。

假设我们已经配好宠物 Pet 实体上指向主人 Owner 实体的多对一关系。配置如下：

```java
@Table
public class Pet extends Model {
    @Id
    private String id;

    @Column
    private String name;

    @ManyToOne(Owner.class)
    private String ownerId;

    @Relational
    private Owner owner;

    // 省略 getter 和 setter...
}
```

假设查询接口已经按照前面章节创建好。启动应用，当我们请求 `GET /pet` 的时候，假设当前数据库只有一条宠物记录，将返回：

```json
[{
    id: "petId1",
    name: "coco",
    ownerId: "ownerId1",
    owner: null
}]
```

我们可以看到，owner 属性值是 null 。

现在重新发起请求 `GET /pet?expand=owner(name)` ，代表我们想获取主人的扩展信息，此时将返回：

```json
[{
    id:"petId1",
    name:"coco",
    ownerId: "ownerId1",
    owner: {
        name: "zhangsan"
    }
}]
```

通过以上结果我们可以知道 expand 的参数格式为：

`关系名( 展开属性 )`

- **关系名**就是指当前查询实体想要扩展查询的关系的名称，关系名默认是 `@Relational` 注解所标注的字段名，也可以在 `@Relational` 注解中指定值。

- **展开属性**是指对关联实体的属性进行筛选，可以忽略不写，不写则返回关联实体所有属性。如上例中假如我们要求返回的 owner 对象只包含 name 属性，如果不写展开属性将返回 owner 对象所有属性。多个关系属性名或者多个展开属性都使用英文逗号分隔。
