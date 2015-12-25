# dao接口

前面我们已经清楚了Model接口的强大功能,也能满足大多数的数据库操作了,但是Model对象的功能实在太强大了,有些时候我们不希望暴漏这么强大的数据操作功能给业务层,而希望业务层只能使用我们提供的数据操作结果.

这个时候如果将Model对象返回给业务层那就无疑给了业务层全部的数据操作功能了,这和我们的需求不符.因此leap也提供了传统的dao接口来满足这类需求,通过dao接口,我们可以将没有继承Model类的类也和数据库表映射起来,并且通过dao对该类的实例对象进行操作.

> **注意:**  
> 要使用dao接口将类和数据库表映射,需要在类上注解`@leap.orm.annotation.Entity`

## 导航

* [dao对象获取](#getdao)
* [新增方法](#create)
	- [单行新增](#single_create)
	- [多行新增](#multi_create)
* [更新方法](#update)
	- [单行更新](#single_update)
	- [多行更新](#multi_update)
* [删除方法](#delete)
	- [单行删除](#single_delete)
	- [多行删除](#multi_delete)
* [查询方法](#query)
	- [单行查询](#single_query)
	- [多行查询](#multi_query)
* [Query查询](#query_object)
	- [Query对象和EntityQuery对象的创建](#entity_query)
	- [CriteriaQuery对象创建](#criteria_query)
* [指令对象](#cmd)
* [事务控制](#transaction)
* [校验方法](#validate)
* [执行sql](#execute)
	- [SqlCommand对象](#sql_command)
	- [sql执行方法](#sql_execute)
* [探测方法](#test)
* [其他方法](#other)

### <a id="getdao"></a>dao对象获取

```java
get()
get(String name)
```

##### `Dao get()`

获取默认的Dao的实例对象相当于`Dao.get("default")`

##### `Dao get(String name)`

获取指定名称的Dao实例对象,这里主要是用于多数据源的情况下.

--------

### <a id="create"></a>新增方法

#### <a id="single_create"></a>单行新增

```java
insert(Object entity)
```

##### `int insert(Object entity)`

插入单行记录,这里的参数是一个entity的实例,参数的类型必须是与数据库表有映射的类型.

**参数:**

* entity: 插入数据库的记录,类型必须与数据库表有映射

**结果:**

* 影响的数据库行数

-------

#### <a id="multi_create"></a>多行新增

```java
batchInsert(List<?> entities)
batchInsert(Object[] entities)
batchInsert(String entityName,List<?> records)
batchInsert(String entityName,Object[] records)
batchInsert(Class<?> entityClass,List<?> records)
batchInsert(Class<?> entityClass,Object[] records)
batchInsert(EntityMapping em,List<?> records)
batchInsert(EntityMapping em,Object[] records)
```

##### `int[] batchInsert(List<?> entities)`

批量插入数据库.

**参数:**

* entities : 需要插入数据库的记录列表,类型必须是与数据库表存在映射关系的类型

**结果:**

* 每个对象插入数据库之后受影响的行数的数组

#### `batchInsert(Object[] entities)`

与`int[] batchInsert(List<?> entities)`等效,只是参数类型不同.

#### `int[] batchInsert(String entityName,List<?> records)`

将对象列表插入到指定的对象映射的数据库表中.

**参数:**

* entityName : 类名,必须是与数据库表映射的类,必须是简单类名
* records : 需要插入数据库的记录列表

**结果:**

* 每行记录插入数据库后受影响的行数的数组

##### `int[] batchInsert(String entityName,Object[] records)`

效果与`int[] batchInsert(String entityName,List<?> records)`相同,仅插入的记录以不同的方式传递而已.

##### `int[] batchInsert(Class<?> entityClass,List<?> records)`

效果与效果与`int[] batchInsert(String entityName,List<?> records)`相同,但是指定插入数据库的映射类以类对象的方式传递.

##### `int[] batchInsert(Class<?> entityClass,Object[] records)`

效果与`int[] batchInsert(String entityName,Object[] records)`相同,但是指定插入数据库的映射类以类对象的方式传递.

##### `int[] batchInsert(EntityMapping em,List<?> records)`

效果与`int[] batchInsert(String entityName,Object[] records)`相同,但是通过直接指定数据库映射对象的方式传递,EntityMapping对象可以用过`Dao.getOrmContext().getMetadata().getEntityMapping(entity.class)`获取

##### `int[] batchInsert(EntityMapping em,Object[] records)`

效果与`int[] batchInsert(String entityName,Object[] records)`相同,但是通过直接指定数据库映射对象的方式传递.

--------

### <a id="update"></a>更新方法

#### <a id="single_update"></a>单行更新

```java
update(Object entity)
```

##### `int update(Object entity)`

更新单个实体,实体的类型必须与数据库表存在映射关系的类型,并且id不为null

**参数:**

* entity:需要更新的记录对应的实体,类型必须与数据库表存在映射关系,并且id不为null

**结果:**

* 返回记录更新后受影响的行数

-------

#### <a id="multi_update"></a>多行更新

```java
batchUpdate(List<?> entities)
batchUpdate(Object[] entities)
batchUpdate(String entityName, List<?> records)
batchUpdate(String entityName, Object[] records)
batchUpdate(Class<?> entityClass, List<?> records)
batchUpdate(Class<?> entityClass, Object[] records)
batchUpdate(EntityMapping em, List<?> records)
batchUpdate(EntityMapping em, Object[] records)
```

##### `int[] batchUpdate(List<?> entities)`

批量更新数据库记录

**参数:**

* entities : 需要更新的数据库记录列表,每一个的类型都必须与数据库表存在映射关系

**结果:**

* 返回每一行记录更新后受影响的行数的数组

##### `int[] batchUpdate(Object[] entities)`

效果与`int[] batchUpdate(List<?> entities)`相同,仅参数传递的类型不同.

##### `int[] batchUpdate(String entityName, List<?> records)`

将对象列表的数据更新到指定的数据库表中.

**参数:**

* entityName : 数据库表的映射实体类名,必须为简单类名
* records : 需要更新的记录,每一个的id都不能为null

**结果:**

* 返回每一行记录更新后受影响的行数的数组

##### `int[] batchUpdate(String entityName, Object[] records)`

效果与`int[] batchUpdate(String entityName, List<?> records)`相同,仅参数传递的类型不同

##### `int[] batchUpdate(Class<?> entityClass, List<?> records)`

效果与`int[] batchUpdate(String entityName, List<?> records)`相同,但是数据库表的映射实体类通过类实例传递

##### `int[] batchUpdate(Class<?> entityClass, Object[] records)`

效果与`int[] batchUpdate(String entityName, Object[] records)`相同,但是数据库表的映射实体类通过类实例传递

##### `int[] batchUpdate(EntityMapping em, List<?> records)`

效果与`int[] batchUpdate(String entityName, List<?> records)`相同,但是数据库表以实体映射对象指定,EntityMapping对象可以用过`Dao.getOrmContext().getMetadata().getEntityMapping(entity.class)`获取

##### `batchUpdate(EntityMapping em, Object[] records)`

效果与`int[] batchUpdate(String entityName, Object[] records)`相同,但是数据库表以实体映射对象指定.

-------

### <a id="delete"></a>删除方法

#### <a id="single_delete"></a>单行删除

```java
delete(Class<?> entityClass,Object id)
delete(String entityName,Object id)
delete(EntityMapping em,Object id)
```

##### `int delete(Class<?> entityClass,Object id)`

根据id删除entityClass对应的数据库表记录.

**参数:**

* entityClass: 数据库表的映射实体类
* id : 需要删除的记录id

**结果:**

* 返回删除记录后数据库受影响的行数

##### `int delete(String entityName,Object id)`

效果与`int delete(Class<?> entityClass,Object id)`相同,仅数据库对应的实体表以简单类名的方式传递

##### `int delete(EntityMapping em,Object id)`

效果与`int delete(String entityName,Object id)`相同,仅数据库对应的实体表以实体映射对象传递

-----

#### <a id="multi_delete"></a>多行删除

```java
deleteAll(Class<?> entityClass)
deleteAll(String entityName)
deleteAll(EntityMapping em)
batchDelete(String entityName,List<?> ids)
batchDelete(String entityName,Object[] ids)
batchDelete(Class<?> entityClass,List<?> ids)
batchDelete(Class<?> entityClass,Object[] ids)
batchDelete(EntityMapping em,List<?> ids)
batchDelete(EntityMapping em,Object[] ids)
```

##### `int deleteAll(Class<?> entityClass)`

删除entityClass类对应的实体表的所有记录

**参数:**

* entityClass : 数据库表对应的类实例

**结果:**

* 返回受影响的数据库行数

##### `int deleteAll(String entityName)`

效果与`int deleteAll(Class<?> entityClass)`相同,数据库表的映射类以简单类名的方式传递

##### `int deleteAll(EntityMapping em)`

效果与`int deleteAll(Class<?> entityClass)`相同,数据库表的映射类以实体映射对象传递

##### `int[] batchDelete(String entityName,List<?> ids)`

删除指定实体类映射数据库表中id为ids列表中的记录

**参数:**

* entityName : 实体类的类名,必须是简单类名
* ids:需要删除的记录id列表

**结果:**

* 返回每一个记录删除后受影响的数据库行数

##### `int[] batchDelete(String entityName,Object[] ids)`

效果与`int[] batchDelete(String entityName,List<?> ids)`相同,仅ids传递的参数类型不同.

##### `int[] batchDelete(Class<?> entityClass,List<?> ids)`

效果与`int[] batchDelete(String entityName,List<?> ids)`相同,仅数据库表的映射类以类实例的方式指定.

##### `int[] batchDelete(Class<?> entityClass,Object[] ids)`

效果与`int[] batchDelete(String entityName,Object[] ids)`相同,仅数据库表的映射类以类实例的方式指定.

##### `int[] batchDelete(EntityMapping em,List<?> ids)`

效果与`int[] batchDelete(String entityName,List<?> ids)`相同,仅数据库表的映射类以实体映射对象指定.

##### `int[] batchDelete(EntityMapping em,Object[] ids)`

效果与`int[] batchDelete(String entityName,Object[] ids)`相同,仅数据库表的映射类以实体映射对象指定.

-------

### <a id="query"></a>查询方法

#### <a id="single_query"></a>单行查询

```java
find(Class<T> entityClass,Object id)
find(String entityName,Object id)
find(String entityName,Class<T> resultClass,Object id)
find(EntityMapping em,Class<T> resultClass,Object id)
findOrNull(Class<T> entityClass,Object id)
findOrNull(String entityName,Object id)
findOrNull(String entityName,Class<T> resultClass,Object id)
findOrNull(EntityMapping em,Class<T> resultClass,Object id)
```

##### `<T> T find(Class<T> entityClass,Object id)`

根据id从实体entityClass类对应的数据库表中查询记录

**参数:**

* entityClass : 实体对应类
* id : 查询的id

**结果:**

* 返回对应的实体类对象实例

**异常:**

* RecordNotFoundException : 查找不到指定的id时抛出

##### `Entity find(String entityName,Object id)`

根据指定的id从entityName对应的数据库表中查找记录

**参数:**

* entityName:对应数据库表的实体类名,必须是简单类名
* id : 需要查找的记录id

**结果:**

* 返回以Entity类包装的数据库记录,Entity的用法和Map类似,可以通过get方法获取指定的属性值

**异常:**

* RecordNotFoundException : 查找不到指定的id时抛出

##### `<T> T find(String entityName,Class<T> resultClass,Object id)`

据指定的id从entityName对应的数据库表中查找记录,并包装成指定的结果类型

**参数:**

* entityName:对应数据库表的实体类名,必须是简单类名
* resultClass:指定返回结果类型
* id : 需要查找的记录id

**结果:**

* 返回以resultClass包装的对象实例,根据列名和resultClass的属性名一一对应

**异常:**

* RecordNotFoundException : 查找不到指定的id时抛出

##### `<T> T find(EntityMapping em,Class<T> resultClass,Object id)`

效果同`<T> T find(String entityName,Class<T> resultClass,Object id)`,但是以实体映射对象指定数据库表

##### `<T> T findOrNull(Class<T> entityClass,Object id)`

根据id从实体entityClass类对应的数据库表中查询记录

**参数:**

* entityClass : 实体对应类
* id : 查询的id

**结果:**

* 返回对应的实体类对象实例,如果查找不到指定id则返回null

##### `Entity findOrNull(String entityName,Object id)`

效果同`<T> T findOrNull(Class<T> entityClass,Object id)`,但是以简单类名的方式指定数据库表

##### `<T> T findOrNull(String entityName,Class<T> resultClass,Object id)`

据指定的id从entityName对应的数据库表中查找记录,并包装成指定的结果类型

**参数:**

* entityName:对应数据库表的实体类名,必须是简单类名
* resultClass:指定返回结果类型
* id : 需要查找的记录id

**结果:**

* 返回以resultClass包装的对象实例,根据列名和resultClass的属性名一一对应,查找不到则返回null

##### `<T> T findOrNull(EntityMapping em,Class<T> resultClass,Object id)`

效果同`<T> T findOrNull(String entityName,Class<T> resultClass,Object id)`,但是以实体映射对象指定数据库表

-------

#### <a id="multi_query"></a>多行查询

```java
findList(Class<T> entityClass,Object[] ids)
findList(String entityName,Object[] ids)
findList(String entityName, Class<T> resultClass,Object[] ids)
findList(EntityMapping em,Class<T> resultClass,Object[] ids)
findListIfExists(Class<T> entityClass,Object[] ids)
findListIfExists(String entityName,Object[] ids)
findListIfExists(String entityName, Class<T> resultClass,Object[] ids)
findListIfExists(EntityMapping em,Class<T> resultClass,Object[] ids)
findAll(Class<T> entityClass)
findAll(String entityName,Class<T> resultClass)
```

##### `<T> List<T> findList(Class<T> entityClass,Object[] ids)`

根据id数组查询指定类实例对应的数据库表的记录.

**参数:**

* entityClass : 需要查询的数据库表对应的类实例
* ids : 需要查询的id数组

**结果:**

* 返回查询结果的id列表,如果存在查询不到的结果则抛出异常

**异常:**

* RecordNotFoundException : 当查询结果的大小和id数组的大小不对应时抛出

##### `List<Entity> findList(String entityName,Object[] ids)`

效果同`<T> List<T> findList(Class<T> entityClass,Object[] ids)`,但是以简单类名的方式指定映射实体类,并且返回值是Entity列表

##### `<T> List<T> findList(String entityName, Class<T> resultClass,Object[] ids)`

根据id数组查询指定类实例对应的数据库表的记录,并将返回值解析为以resultClass类的列表

**参数:**

* entityClass : 需要查询的数据库表对应的类实例
* resultClass : 返回列表的类型
* ids : 需要查询的id数组

**结果:**

* 返回查询结果的列表,如果存在查询不到的结果则抛出异常

**异常:**

* RecordNotFoundException : 当查询结果的大小和id数组的大小不对应时抛出

##### `<T> List<T> findList(EntityMapping em,Class<T> resultClass,Object[] ids)`

效果同`<T> List<T> findList(String entityName, Class<T> resultClass,Object[] ids)`,但以实体映射对象的方式指定数据库表

##### `<T> List<T> findListIfExists(Class<T> entityClass,Object[] ids)`

根据id数组查询指定类实例对应的数据库表的记录.

**参数:**

* entityClass : 需要查询的数据库表对应的类实例
* ids : 需要查询的id数组

**结果:**

* 返回查询结果的列表,查找不到的则忽略

##### `List<Entity> findListIfExists(String entityName,Object[] ids)`

效果同`<T> List<T> findListIfExists(Class<T> entityClass,Object[] ids)`,但以简单类名的方式传递实体映射数据库表,并且返回记录以Entity类包装成列表

##### `<T> List<T> findListIfExists(String entityName, Class<T> resultClass,Object[] ids)`

根据id数组查询指定类实例对应的数据库表的记录,并将返回值解析为以resultClass类的列表

**参数:**

* entityClass : 需要查询的数据库表对应的类实例
* resultClass : 返回列表的类型
* ids : 需要查询的id数组

**结果:**

* 返回查询结果的列表,如果存在查询不到的结果则忽略

##### `<T> List<T> findListIfExists(EntityMapping em,Class<T> resultClass,Object[] ids)`

效果同`<T> List<T> findListIfExists(String entityName, Class<T> resultClass,Object[] ids)`,但是以实体映射对象的方式指定数据库表

##### `<T> List<T> findAll(Class<T> entityClass)`

查询指定类实例对应的数据库表的所有记录

**参数:**

* entityClass : 需要查询的数据库表对应的类实例

**结果:**

* 返回查询结果列表

##### `<T> List<T> findAll(String entityName,Class<T> resultClass)`

以entityName指定数据库并查询所有记录,然后将记录包装成resultClass类的列表返回

**参数:**

* entityName : 需要查询的数据库表对应的类名称,必须是简单类名
* resultClass : 返回结果的列表类型

**结果:**

* 返回查询结果列表

-------

### <a id="query_object"></a>Query查询

Query查询是通过创建一个Query对象的方式查询的,Query对象的使用和Model接口中的EntityQuery对象使用完全一直,可以参考Model接口中的EntityQuery查询方法,同时这里也可以创建CriteriaQuery查询

#### <a id="entity_query"></a>Query对象和EntityQuery对象的创建

```java
createSqlQuery(String sql)
createSqlQuery(Class<T> resultClass, String sql)
createSqlQuery(EntityMapping em,String sql)
createSqlQuery(EntityMapping em, Class<T> resultClass, String sql)
createNamedQuery(String queryName)
createNamedQuery(String queryName,Class<T> resultClass)
createNamedQuery(Class<T> entityClass,String queryName)
createNamedQuery(String entityName,String queryName)
createNamedQuery(String entityName,Class<T> resultClass, String queryName)
createNamedQuery(EntityMapping em, Class<T> resultClass, String queryName)
createQuery(Class<T> resultClass, SqlCommand command)
```

##### `Query<Record> createSqlQuery(String sql)`

以leapQL的参数创建一个Query对象

**参数:**

* sql : 需要解析的leapQL

**结果:**

* 返回Query对象

##### `<T> Query<T> createSqlQuery(Class<T> resultClass, String sql)`

以leapQL的参数创建一个Query对象,并指定查询结果的返回类型

**参数:**

* resultClass: 查询结果的返回类型
* sql : 需要解析的leapQL

**结果:**

* 返回Query对象

##### `EntityQuery<EntityBase> createSqlQuery(EntityMapping em,String sql)`

以leapQL和实体映射对象创建EntityQuery对象

**参数:**

* em : 实体映射对象
* sql : 需要解析执行的leapQL

**结果:**

* 返回EntityBase类型的EntityQuery对象

##### `<T> EntityQuery<T> createSqlQuery(EntityMapping em, Class<T> resultClass, String sql)`

以leapQL和实体映射对象创建EntityQuery对象,并指定返回的类型

**参数:**

* em : 实体映射对象
* resultClass : 需要返回的类型的类实例
* sql : 需要解析执行的leapQL

**结果:**

* 返回resultClass类型的EntityQuery对象

##### `Query<Record> createNamedQuery(String queryName)`

通过指定sqlKey的方式创建Query对象.

**参数:**

* queryName : xml中配置的sql的key属性

**结果:**

* 返回Record类型的Query对象,Record是Map的子类,用法和Map一致.

##### `<T> Query<T> createNamedQuery(String queryName,Class<T> resultClass)`

通过指定sqlKey的方式创建Query对象,并指定Query的泛型类型为resultClass

**参数:**

* queryName : xml中配置的sql的key属性
* resultClass : Query的泛型类型

**结果:**

* 返回resultClass类型的Query对象.

##### `<T> EntityQuery<T> createNamedQuery(Class<T> entityClass,String queryName)`

指定sqlKey和数据库映射类,并返回该映射类的EntityQuery对象

**参数:**

* entityClass : 实体类实例
* queryName : xml中配置的sql的key属性

**结果:**

* 返回resultClass类型的EntityQuery对象.

##### `EntityQuery<EntityBase> createNamedQuery(String entityName,String queryName)`

指定sqlKey,并返回EntityBase类型的EntityQuery对象

**参数:**

* entityName : 实体类的简单类名
* queryName : xml中配置的sql的key属性

**结果:**

* 返回EntityBase类型的EntityQuery对象.

##### `<T> EntityQuery<T> createNamedQuery(String entityName,Class<T> resultClass, String queryName)`

指定sqlKey和数据库映射类名,并指定返回EntityQuery的泛型类型为resultClass

**参数:**

* entityName : 实体类的简单类名
* resultClass : 指定EntityQuery的泛型类型
* queryName : xml中配置的sql的key属性

**结果:**

* 返回resultClass类型的EntityQuery对象.

##### `<T> EntityQuery<T> createNamedQuery(EntityMapping em, Class<T> resultClass, String queryName)`

效果同`<T> EntityQuery<T> createNamedQuery(String entityName,Class<T> resultClass, String queryName)`,但是通过直接指定数据库实体映射对象指定数据库表

##### `<T> Query<T> createQuery(Class<T> resultClass, SqlCommand command)`

以SqlCommand的方式创建Query对象,并指定返回的Query的泛型类型

SqlCommand对象在`sql执行的小节详细分析`

-------

#### <a id="criteria_query"></a>CriteriaQuery对象创建

```java
createCriteriaQuery(Class<T> entityClass)
createCriteriaQuery(EntityMapping em, Class<T> resultClass)
```

##### `<T> CriteriaQuery<T> createCriteriaQuery(Class<T> entityClass)`

指定实体映射类生成CriteriaQuery对象

**参数:**

* entityClass : 实体映射类

**结果:**

* 返回生成的CriteriaQuery对象

##### `<T> CriteriaQuery<T> createCriteriaQuery(EntityMapping em, Class<T> resultClass)`

指定实体映射对象生成CriteriaQuery,并指定CriteriaQuery的泛型类型为resultClass

**参数:**

* em : 实体映射对象
* resultClass : 泛型的类型

**结果:**

* 返回resultClass类型的CriteriaQuery

------

### <a id="cmd"></a>指令对象

```java
cmdInsert(Class<?> entityClass)
cmdInsert(String entityName)
cmdInsert(EntityMapping em)
cmdUpdate(Class<?> entityClass)
cmdUpdate(String entityName)
cmdUpdate(EntityMapping em)
```

指令对象与Model接口中的指令对象使用方法完全相同,可以参考Model接口对指令对象的使用说明.

##### `InsertCommand cmdInsert(Class<?> entityClass)`

通过指定数据库映射类生成插入指令

**参数:**

* entityClass : 数据库映射类

**结果:**

* 返回指定类型的插入指令对象

**异常:**

* MappingNotFoundException:如果没有找到实体映射对象则抛出

##### `InsertCommand cmdInsert(String entityName)`

效果同`InsertCommand cmdInsert(Class<?> entityClass)`,仅指定数据库映射类使用简单类名指定.

##### `InsertCommand cmdInsert(EntityMapping em)`

效果同`InsertCommand cmdInsert(Class<?> entityClass)`,仅指定数据库映射类使用实体映射对象

##### `UpdateCommand cmdUpdate(Class<?> entityClass)`

通过指定数据库映射类生成更新指令

**参数:**

* entityClass : 数据库映射类

**结果:**

* 返回指定类型的更新指令对象

**异常:**

* MappingNotFoundException:如果没有找到实体映射对象则抛出

##### `UpdateCommand cmdUpdate(String entityName)`

效果同`UpdateCommand cmdUpdate(Class<?> entityClass)`,仅指定数据库映射类使用简单类名指定.

##### `UpdateCommand cmdUpdate(EntityMapping em)`

效果同`UpdateCommand cmdUpdate(Class<?> entityClass)`,仅指定数据库映射类使用实体映射对象

------

### <a id="transaction"></a>事务控制

```java
doTransaction(TransactionCallback callback)
doTransaction(TransactionCallbackWithResult<T> callback)
doTransaction(TransactionCallback callback, boolean requiresNew)
doTransaction(TransactionCallbackWithResult<T> callback, boolean requiresNew)
```

使用控制的方法使用和Model接口完全一致,请参考Model接口的说明文档

### <a id="validate"></a>校验方法

```java
validate(Object entity)
validate(Object entity, int maxErrors)
validate(EntityMapping em,Object entity)
validate(EntityMapping em,Object entity, int maxErrors)
```

##### `Errors validate(Object entity)`

校验映射实体类实例并返回校验结果对象

**参数:**

* entity:需要校验的实体类对象实例

**结果:**

* 返回校验结果的Error对象

##### `Errors validate(Object entity, int maxErrors)`

校验映射实体类实例并返回校验结果对象,同时指定在校验的最大错误数,当校验错误数达到最大错误数时停止校验并返回校验结果,0表示校验全部错误

**参数:**

* entity:需要校验的实体类对象实例
* maxErrors: 最大错误数

**结果:**

* 返回校验结果的Error对象

##### `Errors validate(EntityMapping em,Object entity)`

以指定实体映射对象的配置校验映射类实例

**参数:**

* em:实体映射对象
* entity: 需要校验的对象

**结果:**

* 返回校验结果的Error对象

##### `Errors validate(EntityMapping em,Object entity, int maxErrors)`

以指定实体映射对象的配置校验映射类实例,并指定最大校验失败数量,当校验错误数达到最大错误数时停止校验并返回校验结果,0表示校验全部错误

**参数:**

* em:实体映射对象
* entity: 需要校验的对象
* maxErrors : 最大错误数

**结果:**

* 返回校验结果的Error对象

### <a id="execute"></a>sql执行

```java
executeUpdate(SqlCommand command, Object[] args)
executeUpdate(SqlCommand command, Object bean)
executeUpdate(SqlCommand command, Map params)
executeUpdate(String sql, Object bean)
executeUpdate(String sql, Map params)
executeNamedUpdate(String sqlKey, Object[] args)
executeNamedUpdate(String sqlKey, Object bean)
executeNamedUpdate(String sqlKey, Map<String, Object> params)
```

在介绍sql执行的方法之前,我们需要先了解SqlCommand这个参数如何构造

#### <a id="sql_command"></a>`SqlCommand`对象

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

#### <a id="sql_execute"></a>sql执行方法

##### `SqlCommand`执行

```java
executeUpdate(SqlCommand command, Object[] args)
executeUpdate(SqlCommand command, Object bean)
executeUpdate(SqlCommand command, Map params)
```

这三个方法的使用方法完全相同,区别只在于传递参数的方式不同而已,SqlCommand解析的是标准的leapQL,因此参数的设置和其他的方式完全相同.

并且三个方法的返回值都是受影响的数据库行数

#### sql执行

```java
executeUpdate(String sql, Object bean)
executeUpdate(String sql, Map params)
```

这两个方法的执行结果是完全相同的,只是传递的参数不同而已,参数sql是leapQL或者标准的sql都可以,bean和params都是sql参数

返回值是数据库受影响的行数

```java
executeNamedUpdate(String sqlKey, Object[] args)
executeNamedUpdate(String sqlKey, Object bean)
executeNamedUpdate(String sqlKey, Map<String, Object> params)
```

这三个方法都是通过指定sqlKey的方式执行sql,这些sql都是配置在xml中的sql,三个方法的执行结果也是相同的,不同的只是参数的传递方式而已.

返回值是数据库受影响的行数


-------

### <a id="test"></a>探测方法

```java
exists(Class<?> entityClass,Object id)
count(Class<?> entityClass)
```

##### `boolean exists(Class<?> entityClass,Object id)`

探测实体映射类实例是否存在指定的id的记录

**参数:**

* entityClass : 实体映射类
* id : 需要探测的id

**结果:**

* 存在则返回true,不存在则返回false

**异常:**

* MappingNotFoundException:找不到实体映射类的实体映射对象则抛出

##### `long count(Class<?> entityClass)`

探测实体映射类的数据库记录行数

**参数:**

* entityClass : 实体映射类

**结果:**

* 返回该实体映射类对应数据库表的行数

### <a id="other"></a>其他方法

```java
getOrmContext()
getJdbcExecutor()
```

##### `OrmContext getOrmContext()` 

获取当前ORM映射的上下文

##### `JdbcExecutor getJdbcExecutor()`

获取JDBC执行器, JDBC执行器可以用来写原始的jdbc代码,和其他不经过leapQL翻译的sql代码.