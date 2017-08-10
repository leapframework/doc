# 使用 joins 参数

## 需求

从前面的介绍中可以看到查询基本都是基于**单个实体**的。

而在实际项目中更常见的情况是**一个实体会关联其他关系实体**做查询。

所以这里的 joins 就是用于在查询中关联其他实体。

> 具体实现就是根据这个参数在数据库查询 SQL 中添加 `join` 语句。

## 配置

这里的配置要求很简单，就是要求当前的主实体上配置有与要关联实体之间的关系。

目前可以支持多对一和多对多关系。

具体关系配置方法可以参考[实体关系](/orm/entity/relation/README.md)里的配置说明。

另外还有一个需要注意的事情是，使用 joins 参数默认在 SQL 中都是使用 `inner join`。

如果希望使用的是 `left join` 的话，需要在配置实体关系时，在 `@ManyToOne` 注解上指定 `optional` 参数为 `Bool.TRUE` 。

比如这样：

```java
    @ManyToOne(target = Teacher.class, optional = Bool.TRUE)
    private String teacherId;
```

## 用法

由于我们在关系上的配置已经决定了数据库 SQL 中究竟是使用 `inner join` 还是 `left join`，所以前端使用这个参数就不需要考虑这些了。

只需要提供**要关联的关系名和别名**即可。

这里我们取多对一关系文档中的例子，在宠物 Pet 实体上建立对主人 Owner 的多对一关系，这个时候在宠物实体上的配置如下：

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

工程启动后我们对应用发起如下请求：

```
GET http://localhost:8080/pet?joins=owner o&filters=o.name eq zhangsan
```

这个时候将返回主人名称为 zhangsan 所养的所有宠物信息。看请求参数，我们首先 joins 了关系 owner 并且指定了别名 o，然后在查询参数 filters 中使用了关系字段 o.name 做条件过滤。

在具体用法中有以下几点需要注意：

- joins 参数指定的是关系名而不是实体名，关系名默认是 `@Relational` 注解所标注的字段名，也可以在 `@Relational` 注解中指定值；
- 别名是必须的，并且不能为 `t` ，因为 `t` 是保留别名，有其他用途。