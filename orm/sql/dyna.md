# SQL 动态语句

## 问题

在实际的数据库查询案例中，很少有像上一节中查询条件固定且每个查询条件必定会传的例子。

更常见的情况是，一个用户查询功能，可能有的用户单独通过用户名查询，有的用户单独通过年龄查询，用的两者都有。这个时候如果按照这些使用场景一一对应地写查询 SQL 明显是不明智的，因为随着将来查询条件的增多，SQL 配置文件会变得越来越大且越来越多冗余信息，维护将是一场噩梦。

SQL 动态语句就是用来解决这个问题的。

## 基础语法

SQL 动态语句的语法为：`{?and field like #field#}`，其中**大括号包裹住的语句中 Leap 要求一定要有且仅有一个命名参数占位符**。

根据这个命名参数占位符是否有对应的参数值传过来，Leap 将决定这整个语句是否加入最后执行的 SQL 中。

例如我们将上一节 SQL 使用 SQL 动态语句重写：

```sql
select * 
from User 
where 1 = 1
{? and name = #name# } 
{? and age = #age# }
```

这样重写之后，传进来的参数如果不为 null 则在最后执行前会加入参数所在的语句块，为 null 则不加入。

例如我们现在传入参数 `name` 为 null，`age` 为 18，则最后执行的 SQL 为：

```sql
select *
from User
where 1 = 1
and age = 18
```

> 这里的 `1 = 1` 不是必须的，在有固定查询条件在前面的情况下就不需要了。

## nullable 语法

看来 SQL 动态语句已经能很好解决我们一开始提到的问题，但是还有一种情况我们需要考虑，就是如果确实是需要查询字段值为 null 的情况呢？

我们可以在 SQL 动态语句的大括号内后面加上 `;nullable:true`。

例如上例我们改为 name 字段可以查询为 null 值的情况：

```sql
select *
from User
where 1 = 1
{? and name = #name# ;nullable:true}
{? and age = #age# }
```

假如我们现在还是像上例传入参数 `name` 为 null，`age` 为 18，则最后执行的 SQL 为：

```sql
select *
from User
where 1 = 1
and name = null
and age = 18
```

这个时候只有我们单单传 `age` 参数和值而不传 `name` 参数和值时，最后执行 SQL 才为：

```sql
select *
from User
where 1 = 1
and age = 18
```

> 代码中如何传入对应参数将在后面章节介绍。