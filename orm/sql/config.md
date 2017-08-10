# SQL 配置

## 配置

SQL 配置的入口是 `sqls.xml` 文件，Leap 默认会扫描 `conf/sqls.xml` 文件和 `conf/sqls` 目录下的 xml 文件作为 SQL 的配置。

具体的 SQL 需要写在 `<sqls>` 下的 `<command>` 元素中，并且需要指定这个 SQL 的 sqlKey 以便代码中调用。

一个简单的 SQL 配置示例如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<sqls xmlns="http://www.leapframework.org/schema/sqls">
    <command key="user.all">
        select * from User
    </command>
</sqls>
```

上例 SQL 的 sqlKey 为 `user.all`，sqlKey 名称 Leap 不做任何限制，建议以模块分层的方式命名，防止名称重复。

## 语法

这里的 SQL 可以是标准的 SQL 语法，也可以是 **LeapQL** 的语法。

即**可以使用 Leap 配置的实体名和属性名代替数据库中对应实体的表名和字段名**，在执行查询前 Leap 会解析 SQL ，并自动根据实体映射做转换。

比如上述例子，实际上是查询 `User` 这个对象的属性，如果 `User` 在 Leap 配置实体中能找到映射的 Model 类，则会在执行前自动转换为标准的 SQL :

```
select * from user_table
args:['name']
```

> 注意：在 xml 文件中 `<` 字符和 `&` 字符是禁止使用的，当在 SQL 中会出现这些字符时需要使用转义字符代替。
> 或者更方便的方式，在 `<command>` 元素中使用 CDATA 部件以 `<![CDATA[` 标记开始，以 `]]>` 标记结束。
