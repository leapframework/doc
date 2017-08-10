# 在 SQL 中使用环境变量

在前面章节中我们介绍了如何配置和执行 SQL ，并且也可以通过占位符的形式传入参数。

但是在一些业务场景中，其实有一些参数是在很多 SQL 中都要传递的，比如当前用户信息、用户权限以及一些全局属性等等。

如果每次调用都需要在调用代码中显式传进来的话就太麻烦了，定义环境变量可以解决这个问题。

## 配置环境变量

首先我们新建一个我们需要的环境变量类：

```java
package hello.var;

import leap.core.variable.Variable;

@Bean(name = "userId")
public class UserId implements Variable {
    @Override
    public Object getValue() {
        return "userId1"; // 这里做演示只是简单返回一个 demo 字符串，实际使用中这里可以动态获取数据
    }
}

```

我们将这个类注解为 Bean 并且命名为 `userId`。

## 使用

然后我们就可以在 SQL 中使用了：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<sqls xmlns="http://www.leapframework.org/schema/sqls">
    <command key="user.all">
        select * from User
        where id = #{env.userId}
    </command>
</sqls>
```

当我们执行以上的 SQL 时，Leap 将会解析环境变量的值，再执行 SQL。

环境变量表达式有两种形式：(假设环境变量名称为 xxx)

- `#{env.xxx}` ：这种形式的表达式在解析的时候将会在原 SQL 位置上插入一个 `?` 匿名参数占位符，将环境变量的值以 SQL 参数的形式提交给数据库驱动。

- `${env.xxx}` ：这种形式的表达式在解析的时候会将解析出来的环境变量的值直接拼接入 SQL 中，使用的时候需要注意 SQL 注入的问题。

注意 `env.` 开头的前缀是固定的。