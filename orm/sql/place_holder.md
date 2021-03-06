# SQL 占位符

在上节 SQL 配置的例子中，执行的查询 SQL 是不需要参数的。

但实际开发中，更常见的情况是 SQL 查询一般都需要传入参数，用于条件查询等。这个时候我们就需要在 SQL 中配置好参数占位符。

> 代码中如何调用这里配置的 SQL 以及如何传入对应参数将在后面介绍。

## 匿名参数占位符

使用问号 `?` 表示，这是最基础最传统的占位符。
 
SQL 示例如下：

```sql
select * from User where name = ?
```

如果一句 SQL 中有多个匿名参数占位符，则在传入参数列表的时候按照前后顺序对应传入。

## 命名参数占位符

匿名参数占位符的不足在于当有多个匿名参数占位符时，参数之间的对应关系难以维护。

因此推荐在项目实际开发中尽量使用命名参数占位符。

有两种形式可以表示命名参数占位符：

- 以冒号 `:` 为前缀，后接参数名，如 `:name`。
- 使用井号 `#` 包裹住参数名，如 `#name#`。

使用示例如下：

```sql
select * from User where name = :name and age = :age
```

等价于：

```sql
select * from User where name = #name# and age = #age#
```

> 命名参数占位符在执行之前 Leap 会转换为相应的匿名参数占位符形式发送到数据库，因此不必担心 SQL 注入的问题。