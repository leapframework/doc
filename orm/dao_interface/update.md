# 更新方法

## <a id="single_update"></a>单行更新

<!--sec data-title="update(Object entity)" data-id="update1" data-show=true ces-->

```java
int update(Object entity)
```

------

更新单个实体,实体的类型必须与数据库表存在映射关系的类型,并且id不为null

**参数:**

* entity:需要更新的记录对应的实体,类型必须与数据库表存在映射关系,并且id不为null

**结果:**

* 返回记录更新后受影响的行数

<!--endsec-->

## <a id="multi_update"></a>多行更新

<!--sec data-title="batchUpdate(List &lt; ? &gt; entities)" data-id="batchUpdate1" data-show=true ces-->

```java
int[] batchUpdate(List<?> entities)
```

------

批量更新数据库记录

**参数:**

* entities : 需要更新的数据库记录列表,每一个的类型都必须与数据库表存在映射关系

**结果:**

* 返回每一行记录更新后受影响的行数的数组

<!--endsec-->

<!--sec data-title="batchUpdate(Object[] entities)" data-id="batchUpdate2" data-show=true ces-->

```java
int[] batchUpdate(Object[] entities)
```

------

效果与`int[] batchUpdate(List<?> entities)`相同,仅参数传递的类型不同.

<!--endsec-->

<!--sec data-title="batchUpdate(String entityName, List &lt; ? &gt; records)" data-id="batchUpdate3" data-show=true ces-->

```java
int[] batchUpdate(String entityName, List<?> records)
```

------

将对象列表的数据更新到指定的数据库表中.

**参数:**

* entityName : 数据库表的映射实体类名,必须为简单类名
* records : 需要更新的记录,每一个的id都不能为null

**结果:**

* 返回每一行记录更新后受影响的行数的数组

<!--endsec-->

<!--sec data-title="batchUpdate(String entityName, Object[] records)" data-id="batchUpdate4" data-show=true ces-->

```java
int[] batchUpdate(String entityName, Object[] records)
```

------

效果与`int[] batchUpdate(String entityName, List<?> records)`相同,仅参数传递的类型不同

<!--endsec-->

<!--sec data-title="batchUpdate(Class &lt; ? &gt; entityClass, List &lt; ? &gt; records)" data-id="batchUpdate5" data-show=true ces-->

```java
int[] batchUpdate(Class<?> entityClass, List<?> records)
```

------

效果与`int[] batchUpdate(String entityName, List<?> records)`相同,但是数据库表的映射实体类通过类实例传递

<!--endsec-->

<!--sec data-title="batchUpdate(Class &lt; ? &gt; entityClass, Object[] records)" data-id="batchUpdate6" data-show=true ces-->

```java
int[] batchUpdate(Class<?> entityClass, Object[] records)
```

------

效果与`int[] batchUpdate(String entityName, Object[] records)`相同,但是数据库表的映射实体类通过类实例传递

<!--endsec-->

<!--sec data-title="batchUpdate(EntityMapping em, List &lt; ? &gt; records)" data-id="batchUpdate7" data-show=true ces-->

```java
int[] batchUpdate(EntityMapping em, List<?> records)
```

------

效果与`int[] batchUpdate(String entityName, List<?> records)`相同,但是数据库表以实体映射对象指定,EntityMapping对象可以用过`Dao.getOrmContext().getMetadata().getEntityMapping(entity.class)`获取

<!--endsec-->

<!--sec data-title="batchUpdate(EntityMapping em, Object[] records)" data-id="batchUpdate8" data-show=true ces-->

```java
int[] batchUpdate(EntityMapping em, Object[] records)
```

------

效果与`int[] batchUpdate(String entityName, Object[] records)`相同,但是数据库表以实体映射对象指定.

<!--endsec-->
