# SQL 执行

前文介绍了配置 SQL，接下来将介绍如何执行 SQL。

## 执行非查询 SQL

非查询 SQL 指的是除了 select 之外的其他 SQL，如 update, delete 等。

假设 User 实体是继承于 Model 的，那我们可以这样获取 Query 对象：

```java
// 假设这个 sqlKey 指向的是一个 update 语句
String sqlKey = "user.update";

// 使用 Map 对象传递参数
Map<String, Object> params = new HashMap<>();
int re = User.dao().executeNamedUpdate(sqlKey, params);
```

返回值是数据库受影响的行数。

## 执行查询 SQL

还是如上例获取 Dao 对象并执行：

```java
// 假设这个 sqlKey 指向的是一个查询语句
String sqlKey = "user.all";

// 获取到一个 Query 对象，这个时候还没有执行查询
Query<User> query = User.dao().createNamedQuery(sqlKey, User.class);

// 简单地获取一个结果列表
List<User> users = query.list();

// 或者简单地获取单条记录
User user = query.single();
```

获取到 Query 对象之后，其实我们就可以做很多事情，因为 Leap 内置了很多数据操作的接口，比如排序、分页等等。

上面的例子由于篇幅原因这里只是介绍了最简单的执行，剩下 Query 对象相关的用法我们在下一节讨论。