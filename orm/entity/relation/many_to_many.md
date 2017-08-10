# 多对多关系

## 场景

为了演示如何在实体上配置多对多关系，我们举一个例子。

一个学生，要上很多个不同的老师的课；而老师每次上课也要教很多个学生。

## 配置

那么这里学生 `Student` 与老师 `Teacher` 之间的关系明显就是多对多关系，我们根据这个关系建立各自的实体对象。

我们先从简单的开始，假设这个关系是单向的，是从学生到老师之间的多对多，学生是主实体。那么建立在学生 `Student` 实体上的配置就是：

```java
@Table
@ManyToMany(target = Teacher.class, joinEntityType = StudentTeacher.class)
public class Student extends Model {
    @Id
    private String id;

    @Column
    private String name;

    @Relational
    private List<Teacher> teachers;

    // 省略 getter 和 setter...
}
```

既然是单向的，那么在老师实体上是不需要任何关系配置的：

```java
@Table
public class Teacher extends Model {
    @Id
    private String id;

    @Column
    private String name;

    // 省略 getter 和 setter...
}
```

因为是多对多关系，所以还需要一个中间实体来映射这种关系：

```java
@Table
public class StudentTeacher extends Model {

    @Id
    @ManyToOne(Student.class)
    private String studentId;

    @Id
    @ManyToOne(Teacher.class)
    private String teacherId;

    // 省略 getter 和 setter...
}
```

## 说明

从上面的配置例子中可以看出，要建立多对多关系主要有以下几点配置。

1. 在要建立多对多关系实体上的配置：
    - 在类上标注 `@ManyToMany` 注解。这个注解有以下几个属性：
        - `target` ：指定目标实体类；
        - `joinEntityType` ：指定中间实体类。
    - 新增一个关系字段，类型为目标实体**列表**，并且标注上 `@Relational` 的注解。

2. 在中间实体上的配置：
    - 在两个外键字段上标注 `@ManyToOne` 注解，注解参数分别指向两端的实体。

到这里单向的多对多关系已经建立。

如果要建立双向的呢？很简单，在两个多对多实体上都应用上面主实体的配置即可。

## 多个多对多关系

假设现在一个实体上存在不只一个多对多关系，那么在类上标注一个 `@ManyToMany` 明显是不够的。

可以按照下面这样利用 `@ManyToManys` 注解配置多个多对多关系：

```java
@Table
@ManyToManys({
    @ManyToMany(target = Teacher.class, joinEntityType = StudentTeacher.class),
    @ManyToMany(target = Exam.class, joinEntityType = StudentExam.class)
})
public class Student extends Model {
    ...
}
```