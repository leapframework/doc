# 查询对象方法

## <a id="entity_query"></a>`EntityQuery`查询

<!--sec data-title="query(String sqlOrKey)" data-id="query1" data-show=true ces-->

```java
<T extends Model> EntityQuery<T> query(String sqlOrKey)
```

------

创建当前Model类的EntityQuery对象并返回,这里可以是一个配置在xml的sql,也可以是一句简单的sql查询,如:

假设xml中配置了如下sql:

```xml
<command key="sqlkey">
	SELECT * FROM model
</command>
```

那么以下两句代码实际上是等效的:

```java
Model.query("sqlkey");
Model.query("SELECT * FROM model");
```

这里Model会自动判断传入的是sqlkey还是sql查询.


**参数:**

* sqlOrKey:可以是配置在sqls.xml中的sqlkey,也可以是直接写一个数据库的sql查询语句.

**结果:**

* 返回创建的EntityQuery对象

<!--endsec-->

<!--sec data-title="EntityQuery查询对象" data-id="EntityQuery" data-show=true ces-->

```
EntityQuery查询对象
```

------

`EntityQuery`对象是一个实体查询对象接口,这个接口对象允许我们在真正查询前做一系列的sql配置,包括:设置参数值,排序,等等操作.

对象的常用方法及简单说明如下:

**参数设置:**

设置命名参数值的方法:

* `Query<T> param(String name,Object value)`
* `Query<T> params(@Nullable Map<String, Object> map)`
* `Query<T> params(@Nullable DynaBean bean)`

以上方法最终都会转化为key/value形式的参数传递给查询sql,并对应设置到命名参数中去.

设置参数占位符的参数值:

* `Query<T> param(Object arg)`
* `Query<T> params(Object[] args)`

以上方法相当于对命名参数占位符为`$0`的参数设置值,比如

```sql
SELECT * FROM model WHERE name = :$0
```

**查询行数:**

* `Query<T> limit(Integer rows)`
* `Query<T> limit(int startRows,int endRows)`
* `Query<T> limit(Limit limit)`
* `QueryResult<T> result(Limit limit)`

以上方法用于在查询前设置查询行数,类似于mysql数据库的`limit`关键字,需要注意的是`result(Limit limit)`方法是立即执行sql,并返回查询结果的,其他的方法都是设置参数而暂时不执行sql.

**分页查询:**

* `PageResult<T> pageResult(Page page)`
* `PageResult<T> pageResult(int index,int size)`

以上两个方法会设置分页查询

**排序:**

* `Query<T> orderBy(String expression)`

这个方法可以设置查询的排序参数.参数值示例:`Model.query(sqlkey).orderBy("id ASC")`

**执行查询:**

* `long count()`
* `T first()`
* `T firstOrNull()`

以上接口可以参考Model对应的接口说明.

* `T single()`

这个方法是指定查询结果只有一行记录,如果查不到记录或者查到两条以上的记录,都会抛出异常

* `T singleOrNull()`

这个方法是指定查询结果只有一行记录,或者没有记录,如果查到两条以上的记录,则会抛出异常

* `List<T> list()`

执行查询并将查询结果包装成列表返回.

**Scalar和Scalars:**

* `Scalar scalar()`
* `Scalar scalarOrNull()`
* `Scalars scalars()`

以上三个方法都是返回一个Scalar对象,有些时候我们可能并不需要查询数据库表的所有属性,只需要其中某个属性(如:id),这个时候如果直接返回Model对象列表,我们再转换一次是非常麻烦的,这种情况下就可以使用Scalar对象了.

比如:

```java
String id = Model.query("SELECT id FROM model").scalar().get(String.class);
```

需要注意的是,`scalar()`相当于`EntityQuery.single()`,当没有查找到记录或者查找到的记录多于一行都会抛出异常,`scalarOrNull()`类似`EntityQuery.singleOrNull()`.  
在我们不确定行数的情况下,可以使用`scalars()`,Scalars的用法和`Scalar`类似,只不过是针对多行记录的而已.

**使用示例:**

* 命名参数、查询行数、排序、列表结果使用示例

```java
Map map = new Map();
map.put("age",3);

List<User> list = User.<User>query("SELECT * FROM user WHERE name=:name AND age > :age").param("name","jim").param(map)
.limit(2).orderBy("id DESC").list();
```

最终执行的sql如下:

```sql
SELECT * FROM model WHERE name='jim' AND age > 3 ORDER BY id DESC limit 0,2 
```

> query(sqlOrKey)默认返回的是EntityQuery<Model>,多数情况下我们需要返回我们指定的泛型类型,所以使用<User>query(sqlOrKey)指定泛型方法的泛型类型.

<!--endsec-->

## <a id="criteria_query"></a>`CriteriaQuery`查询

<!--sec data-title="query()" data-id="query2" data-show=true ces-->

```java
<T extends Model> CriteriaQuery<T> query()
```

------

创建一个无查询条件的CriteriaQuery查询对象

<!--endsec-->

<!--sec data-title="where()" data-id="where1" data-show=true ces-->

```java
<T extends Model> CriteriaWhere<T> where()
```

------

创建一个无查询条件的CriteriaQuery查询对象,和`<T extends Model> CriteriaQuery<T> query()`效果相同

<!--endsec-->

<!--sec data-title="where(Condition &lt; T &gt; condition)" data-id="where2" data-show=true ces-->

```java
<T extends Model> CriteriaQuery<T> where(Condition<T> condition)
```

------

// 预留接口,暂不可用

<!--endsec-->

<!--sec data-title="where(String expression)" data-id="where3" data-show=true ces-->

```java
<T extends Model> CriteriaQuery<T> where(String expression)
```

------

创建一个CriteriaQuery查询对象并添加参查询条件expression

<!--endsec-->

<!--sec data-title="where(String expression,Object... args)" data-id="where4" data-show=true ces-->

```java
<T extends Model> CriteriaQuery<T> where(String expression,Object... args)
```

------

创建一个CriteriaQuery查询对象并添加包含参数占位符的查询条件,args表示参数值

<!--endsec-->

<!--sec data-title="CriteriaQuery查询对象" data-id="CriteriaQuery" data-show=true ces-->

```
CriteriaQuery查询对象
```

------

与`EntityQuery`查询对象的用法类似,只是多了where的多个重载方法提供设置where条件,举例如下:

```
User.where("name=? AND age = :age","jim",3).list();
```

最终执行的sql如下:


```sql
SELECT * FROM user WHERE name='jim' AND age = 3
```

这里要注意,使用参数占位符的方式创建CriteriaQuery查询对象,参数数量(不管是占位符还是命名占位符)必须与可变传参的参数数量一致,否则会导致sql解析异常.
如果使用命名参数占位符的方式创建查询对象,则可以通过`param()`方法给命名参数占位符设置值

<!--endsec-->