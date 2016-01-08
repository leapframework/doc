# 执行sql

在介绍sql执行的方法之前,我们需要先了解SqlCommand这个参数如何构造

## <a id="sql_command"></a>`SqlCommand`对象

<!--sec data-title="SqlCommand对象" data-id="SqlCommand1" data-show=true ces-->

SqlCommand对象是sql的指定对象,一般通过SqlFactory的对象创建.

SqlFactory对象可以通过leap的IOC或者bean容器获取,有如下几个接口:

普通SqlCommand对象创建接口

```java
SqlCommand createSqlCommand(MetadataContext context, String sql)
SqlCommand createSqlCommand(MetadataContext context,String source,String sql)
```

新增SqlCommand对象创建接口

```java
SqlCommand createInsertCommand(MetadataContext context,EntityMapping em)
SqlCommand createInsertCommand(MetadataContext context,EntityMapping em,String[] fields)
```

更新SqlCommand对象创建接口

```java
SqlCommand createUpdateCommand(MetadataContext context,EntityMapping em)
SqlCommand createUpdateCommand(MetadataContext context,EntityMapping em,String[] fields)
```

删除SqlCommand对象创建接口

```java
SqlCommand createDeleteCommand(MetadataContext context,EntityMapping em)
SqlCommand createDeleteAllCommand(MetadataContext context,EntityMapping em)
```

查询SqlCommand对象创建接口

```java
SqlCommand createFindCommand(MetadataContext context,EntityMapping em)
SqlCommand createFindListCommand(MetadataContext context,EntityMapping em)
SqlCommand createFindAllCommand(MetadataContext context,EntityMapping em)
```

探测SqlCommand对象创建接口

```java
SqlCommand createExistsCommand(MetadataContext context,EntityMapping em)
SqlCommand createCountCommand(MetadataContext context,EntityMapping em)
```

上述所有方法都包含一个参数`MetadataContext`,这个参数是ORM元数据对象,OrmContext实际上就是MetadataContext的子类,一般通过`dao.getOrmContext()`获取这个对象

各个接口中的`EntityMapping`参数我们已经在其他地方用的非常多了,也不需要多做解释.

普通SqlCommand对象创建接口中的两个方法都包含了sql参数,这个参数就是普通的leapQL的查询语句

新增SqlCommand对象创建接口和更新SqlCommand对象创建接口中包含的fields参数是属性名的数组,表示需要插入或者更新的属性名

现在我们已经大致了解SqlCommand如何创建了,现在让我们开始看看sql执行的接口吧.

<!--endsec-->

## <a id="sql_execute"></a>sql执行方法

<!--sec data-title="SqlCommand执行" data-id="SqlCommand2" data-show=true ces-->

```java
executeUpdate(SqlCommand command, Object[] args)
executeUpdate(SqlCommand command, Object bean)
executeUpdate(SqlCommand command, Map params)
```

------

这三个方法的使用方法完全相同,区别只在于传递参数的方式不同而已,SqlCommand解析的是标准的leapQL,因此参数的设置和其他的方式完全相同.

并且三个方法的返回值都是受影响的数据库行数

<!--endsec-->

<!--sec data-title="sql执行" data-id="Sql1" data-show=true ces-->

```java
executeUpdate(String sql, Object bean)
executeUpdate(String sql, Map params)
```

这两个方法的执行结果是完全相同的,只是传递的参数不同而已,参数sql是leapQL或者标准的sql都可以,bean和params都是sql参数

返回值是数据库受影响的行数

------

```java
executeNamedUpdate(String sqlKey, Object[] args)
executeNamedUpdate(String sqlKey, Object bean)
executeNamedUpdate(String sqlKey, Map<String, Object> params)
```

这三个方法都是通过指定sqlKey的方式执行sql,这些sql都是配置在xml中的sql,三个方法的执行结果也是相同的,不同的只是参数的传递方式而已.

返回值是数据库受影响的行数

<!--endsec-->
