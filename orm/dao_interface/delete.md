# 删除方法

## <a id="single_delete"></a>单行删除

<!--sec data-title="delete(Class &lt; ? &gt; entityClass,Object id)" data-id="delete1" data-show=true ces-->

```java
int delete(Class<?> entityClass,Object id)
```

------

根据id删除entityClass对应的数据库表记录.

**参数:**

* entityClass: 数据库表的映射实体类
* id : 需要删除的记录id

**结果:**

* 返回删除记录后数据库受影响的行数

<!--endsec-->

<!--sec data-title="delete(String entityName,Object id)" data-id="delete2" data-show=true ces-->

```java
int delete(String entityName,Object id)
```

------

效果与`int delete(Class<?> entityClass,Object id)`相同,仅数据库对应的实体表以简单类名的方式传递

<!--endsec-->

<!--sec data-title="delete(EntityMapping em,Object id)" data-id="delete3" data-show=true ces-->

```java
int delete(EntityMapping em,Object id)
```

------

效果与`int delete(String entityName,Object id)`相同,仅数据库对应的实体表以实体映射对象传递

<!--endsec-->

## <a id="multi_delete"></a>多行删除

<!--sec data-title="deleteAll(Class &lt; ? &gt; entityClass)" data-id="deleteAll1" data-show=true ces-->

```java
int deleteAll(Class<?> entityClass)
```

------

删除entityClass类对应的实体表的所有记录

**参数:**

* entityClass : 数据库表对应的类实例

**结果:**

* 返回受影响的数据库行数

<!--endsec-->

<!--sec data-title="deleteAll(String entityName)" data-id="deleteAll2" data-show=true ces-->

```java
int deleteAll(String entityName)
```

------

效果与`int deleteAll(Class<?> entityClass)`相同,数据库表的映射类以简单类名的方式传递

<!--endsec-->

<!--sec data-title="deleteAll(EntityMapping em)" data-id="deleteAll3" data-show=true ces-->

```java
int deleteAll(EntityMapping em)
```

------

效果与`int deleteAll(Class<?> entityClass)`相同,数据库表的映射类以实体映射对象传递

<!--endsec-->

<!--sec data-title="batchDelete(String entityName,List &lt; ? &gt; ids)" data-id="batchDelete1" data-show=true ces-->

```java
int[] batchDelete(String entityName,List<?> ids)
```

------

删除指定实体类映射数据库表中id为ids列表中的记录

**参数:**

* entityName : 实体类的类名,必须是简单类名
* ids:需要删除的记录id列表

**结果:**

* 返回每一个记录删除后受影响的数据库行数

<!--endsec-->

<!--sec data-title="batchDelete(String entityName,Object[] ids)" data-id="batchDelete2" data-show=true ces-->

```java
int[] batchDelete(String entityName,Object[] ids)
```

------

效果与`int[] batchDelete(String entityName,List<?> ids)`相同,仅ids传递的参数类型不同.

<!--endsec-->

<!--sec data-title="batchDelete(Class &lt; ? &gt; entityClass,List &lt; ? &gt; ids)" data-id="batchDelete3" data-show=true ces-->

```java
int[] batchDelete(Class<?> entityClass,List<?> ids)
```

效果与`int[] batchDelete(String entityName,List<?> ids)`相同,仅数据库表的映射类以类实例的方式指定.

<!--endsec-->

<!--sec data-title="batchDelete(Class &lt; ? &gt; entityClass,Object[] ids)" data-id="batchDelete4" data-show=true ces-->

```java
int[] batchDelete(Class<?> entityClass,Object[] ids)
```

------

效果与`int[] batchDelete(String entityName,Object[] ids)`相同,仅数据库表的映射类以类实例的方式指定.

<!--endsec-->

<!--sec data-title="batchDelete(EntityMapping em,List &lt; ? &gt; ids)" data-id="batchDelete5" data-show=true ces-->

```java
int[] batchDelete(EntityMapping em,List<?> ids)
```

------

效果与`int[] batchDelete(String entityName,List<?> ids)`相同,仅数据库表的映射类以实体映射对象指定.

<!--endsec-->

<!--sec data-title="batchDelete(EntityMapping em,Object[] ids)" data-id="batchDelete6" data-show=true ces-->

```java
int[] batchDelete(EntityMapping em,Object[] ids)
```

------

效果与`int[] batchDelete(String entityName,Object[] ids)`相同,仅数据库表的映射类以实体映射对象指定.

<!--endsec-->
