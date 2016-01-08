# 查询方法

## <a id="single_query"></a>单行查询

<!--sec data-title="find(Class &lt; T &gt; entityClass,Object id)" data-id="find1" data-show=true ces-->

```java
<T> T find(Class<T> entityClass,Object id)
```

------

根据id从实体entityClass类对应的数据库表中查询记录

**参数:**

* entityClass : 实体对应类
* id : 查询的id

**结果:**

* 返回对应的实体类对象实例

**异常:**

* RecordNotFoundException : 查找不到指定的id时抛出

<!--endsec-->

<!--sec data-title="find(String entityName,Object id)" data-id="find2" data-show=true ces-->

```java
Entity find(String entityName,Object id)
```

------

根据指定的id从entityName对应的数据库表中查找记录

**参数:**

* entityName:对应数据库表的实体类名,必须是简单类名
* id : 需要查找的记录id

**结果:**

* 返回以Entity类包装的数据库记录,Entity的用法和Map类似,可以通过get方法获取指定的属性值

**异常:**

* RecordNotFoundException : 查找不到指定的id时抛出

<!--endsec-->

<!--sec data-title="find(String entityName,Class &lt; T &gt; resultClass,Object id)" data-id="find3" data-show=true ces-->

```java
<T> T find(String entityName,Class<T> resultClass,Object id)
```

------

据指定的id从entityName对应的数据库表中查找记录,并包装成指定的结果类型

**参数:**

* entityName:对应数据库表的实体类名,必须是简单类名
* resultClass:指定返回结果类型
* id : 需要查找的记录id

**结果:**

* 返回以resultClass包装的对象实例,根据列名和resultClass的属性名一一对应

**异常:**

* RecordNotFoundException : 查找不到指定的id时抛出

<!--endsec-->

<!--sec data-title="find(EntityMapping em,Class &lt; T &gt; resultClass,Object id)" data-id="find4" data-show=true ces-->

```java
<T> T find(EntityMapping em,Class<T> resultClass,Object id)
```

------

效果同`<T> T find(String entityName,Class<T> resultClass,Object id)`,但是以实体映射对象指定数据库表

<!--endsec-->

<!--sec data-title="findOrNull(Class &lt; T &gt; entityClass,Object id)" data-id="findOrNull1" data-show=true ces-->

```java
<T> T findOrNull(Class<T> entityClass,Object id)
```

------

根据id从实体entityClass类对应的数据库表中查询记录

**参数:**

* entityClass : 实体对应类
* id : 查询的id

**结果:**

* 返回对应的实体类对象实例,如果查找不到指定id则返回null

<!--endsec-->

<!--sec data-title="findOrNull(String entityName,Object id)" data-id="findOrNull2" data-show=true ces-->

```java
Entity findOrNull(String entityName,Object id)
```

------

效果同`<T> T findOrNull(Class<T> entityClass,Object id)`,但是以简单类名的方式指定数据库表

<!--endsec-->

<!--sec data-title="findOrNull(String entityName,Class &lt; T &gt; resultClass,Object id)" data-id="findOrNull3" data-show=true ces-->

```java
<T> T findOrNull(String entityName,Class<T> resultClass,Object id)
```

------

据指定的id从entityName对应的数据库表中查找记录,并包装成指定的结果类型

**参数:**

* entityName:对应数据库表的实体类名,必须是简单类名
* resultClass:指定返回结果类型
* id : 需要查找的记录id

**结果:**

* 返回以resultClass包装的对象实例,根据列名和resultClass的属性名一一对应,查找不到则返回null

<!--endsec-->

<!--sec data-title="findOrNull(EntityMapping em,Class &lt; T &gt; resultClass,Object id)" data-id="findOrNull4" data-show=true ces-->

```java
<T> T findOrNull(EntityMapping em,Class<T> resultClass,Object id)
```

------

效果同`<T> T findOrNull(String entityName,Class<T> resultClass,Object id)`,但是以实体映射对象指定数据库表

<!--endsec-->

