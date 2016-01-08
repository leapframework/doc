# 新增方法

## <a id="single_create"></a>单行新增

<!--sec data-title="insert(Object entity)" data-id="insert1" data-show=true ces-->

```java
int insert(Object entity)
```

------

插入单行记录,这里的参数是一个entity的实例,参数的类型必须是与数据库表有映射的类型.

**参数:**

* entity: 插入数据库的记录,类型必须与数据库表有映射

**结果:**

* 影响的数据库行数

<!--endsec-->

## <a id="multi_create"></a>多行新增

<!--sec data-title="batchInsert(List &lt; ? &gt; entities)" data-id="batchInsert1" data-show=true ces-->

```java
int[] batchInsert(List<?> entities)
```

------

批量插入数据库.

**参数:**

* entities : 需要插入数据库的记录列表,类型必须是与数据库表存在映射关系的类型

**结果:**

* 每个对象插入数据库之后受影响的行数的数组

<!--endsec-->

<!--sec data-title="batchInsert(Object[] entities)" data-id="batchInsert2" data-show=true ces-->

```java
int[] batchInsert(Object[] entities)
```

------

与`int[] batchInsert(List<?> entities)`等效,只是参数类型不同.

<!--endsec-->

<!--sec data-title="batchInsert(String entityName,List &lt; ? &gt; records)" data-id="batchInsert3" data-show=true ces-->

```java
int[] batchInsert(String entityName,List<?> records)
```

------

将对象列表插入到指定的对象映射的数据库表中.

**参数:**

* entityName : 类名,必须是与数据库表映射的类,必须是简单类名
* records : 需要插入数据库的记录列表

**结果:**

* 每行记录插入数据库后受影响的行数的数组

<!--endsec-->

<!--sec data-title="batchInsert(String entityName,Object[] records)" data-id="batchInsert4" data-show=true ces-->

```java
int[] batchInsert(String entityName,Object[] records)
```

------

效果与`int[] batchInsert(String entityName,List<?> records)`相同,仅插入的记录以不同的方式传递而已.

<!--endsec-->

<!--sec data-title="batchInsert(Class &lt; ? &gt; entityClass,List &lt; ? &gt; records)" data-id="batchInsert5" data-show=true ces-->

```java
int[] batchInsert(Class<?> entityClass,List<?> records)
```

------

效果与效果与`int[] batchInsert(String entityName,List<?> records)`相同,但是指定插入数据库的映射类以类对象的方式传递.

<!--endsec-->

<!--sec data-title="batchInsert(Class &lt; ? &gt; entityClass,Object[] records)" data-id="batchInsert6" data-show=true ces-->

```java
int[] batchInsert(Class<?> entityClass,Object[] records)
```

------

效果与`int[] batchInsert(String entityName,Object[] records)`相同,但是指定插入数据库的映射类以类对象的方式传递.

<!--endsec-->

<!--sec data-title="batchInsert(EntityMapping em,List &lt; ? &t; records)" data-id="batchInsert7" data-show=true ces-->

```java
int[] batchInsert(EntityMapping em,List<?> records)
```

------

效果与`int[] batchInsert(String entityName,Object[] records)`相同,但是通过直接指定数据库映射对象的方式传递,EntityMapping对象可以用过`Dao.getOrmContext().getMetadata().getEntityMapping(entity.class)`获取

<!--endsec-->

<!--sec data-title="batchInsert(EntityMapping em,Object[] records)" data-id="batchInsert8" data-show=true ces-->

```java
int[] batchInsert(EntityMapping em,Object[] records)
```

------

效果与`int[] batchInsert(String entityName,Object[] records)`相同,但是通过直接指定数据库映射对象的方式传递.

<!--endsec-->