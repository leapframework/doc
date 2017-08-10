# 新增数据

## create

新增数据可以调用实体对象的 create 方法，即可将当前对象保存到数据库中。

代码示例如下：

```java
User user = new User();
user.setId("id1");
user.setName("zhangsan");
user.create();
```

create 方法将实体对象插入数据库，如果没有id则根据默认规则自动生成id。

## save

还可以使用 save 方法，它会根据当前实体对象的 id 判断，如果id存在，则执行update，如果id不存在，则执行insert。

示例如下：

```java
User user = new User();
user.setId(null);
user.setName("zhangsan");
user.save();  // 这里由于 id 是 null ，所以执行的是 insert 数据操作

user.setName("lisi");
user.save(); // 这里由于结果 insert 操作 id 已经有值了，所以再执行 save 将会执行 update 数据操作
```