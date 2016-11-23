# sql配置

## sql配置的目录结构

sql配置的入口是`sqls.xml`文件，leap默认会扫描`conf/sqls.xml`文件和`conf/sqls`目录作为sql的配置。

## sql配置文件

一个空的sql配置文件如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<sqls xmlns="http://www.leapframework.org/schema/sqls">

</sqls>
```

## sql配置语句

leap的orm框架可以支持完全对象化的数据库操作，不需要写sql语句，但是某些情况下，我们要执行的sql不是固定的，或者一些比较复杂的sql不方便使用java代码实现，还有一些动态sql，因此leap也支持使用sql配置，一个简单的sql配置示例如下：

```xml
<command key="testOrderByQuery.testSimpleOrderByWithParam">
    select * from User WHERE name=:name</command>
```

这里的sql可以是标准的sql语法，也可以是leapQL的语法。比如上述例子，实际上是查询User这个对象的属性，如果User在leap的orm中能找到映射的Model类，则会自动转换为标准的sql:

```
select * from user_table WHERE name=?
args:['name']
```

并执行。