# 多对一关系

## 场景

为了演示如何在实体上配置多对一关系，我们举一个例子。

一只宠物，一般情况下只有一个主人；但是对于人来说，却可能养多只宠物。

## 配置

那么这里宠物 `Pet` 与主人 `Owner` 之间的关系明显就是多对一关系，我们根据这个关系建立各自的实体对象。

首先是主人 `Owner` ：

```java
@Table
public class Owner extends Model {
    @Id
    private String id;

    @Column
    private String name;

    // 省略 getter 和 setter...
}
```

然后是宠物 `Pet`：

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

## 说明

从上面的例子可以看到，配置多对一关系的时候，"一"端的实体不需要做任何改动，而"多"端的实体在需要做以下一些配置：

- 在相应的外键字段上标注 `@ManyToOne` 注解，并且注解参数指定了"一"端的实体类。

- 新增一个实体字段，类型为"一"端实体的类型，并且标注上 `@Relational` 的注解。

到这里多对一关系已经配置完毕。

> 注意： `@ManyToOne` 注解是标注在**外键字段**上，这和一些框架标注在关系字段上的做法稍有不同，需要注意一下。