# Model接口

我们已经知道,Model是ActiveRecord模式的重要对象,作为它作为桥梁连接了java对象和数据库记录,在leap的Model类中包含了许多常用并且功能强大的方法,本节我们将对Model类提供的常用方法做详细的说明.

## 导航

* [查询方法](#query)
	- [单行查询](#single_query)
	- [多行查询](#multi_query)
* [新增方法](#create)
	- [单行新增](#single_create)
	- [多行新增](#multi_create)
* [更新方法](#update)
	- [单行更新](#single_update)
	- [多行更新](#multi_update)
* [删除方法](#delete)
	- [单行删除](#single_delete)
	- [多行删除](#multi_delete)
* [查询对象方法](#object_query)
	- [EntityQuery查询](#entity_query)
	- [CriteriaQuery查询](#criteria_query)
* [事务控制方法](#transaction)
* [指令方法](#cmd)
* [其他方法](#other)
	- [实例创建方法](#newinstance)
	- [数据库相关方法](#database)
	- [普通操作相关方法](#operate)

## <a id="query"></a>查询方法

### <a id="single_query"></a>单行查询

单行查询的方法如下:

```java
find(Object id);
findOrNull(Object id);
first();
firstOrNull();
last();
lastOrNull();
findBy(String field,Object value);
```

###### `<T extends Model> T find(Object id)`

这个方法是传入一个id查找记录并返回查询的结果对象.

**参数:**

* id : 查找记录的id

**结果:**

* 查询的结果对象,不会为null

**异常:**

* `IllegalArgumentException`:id为null时抛出  
* `RecordNotFoundException`:查询结果为空时抛出  
* `IllegalStateException`:查询结果包含多条记录时抛出

###### `<T extends Model> T findOrNull(Object id)`

这个方法是传入一个id查找记录并返回查询的结果对象.

**参数:**

* id : 查找记录的id

**结果:**

* 查询的结果对象,查找不到记录时返回null

**异常:**  

* `IllegalArgumentException`:id为null时抛出  
* `IllegalStateException`:查询结果包含多条记录时抛出

##### `<T extends Model> T first()`

这个方法会根据id排序之后查询第一条记录.

**结果:**

* 查询的结果对象,查询结果不会为null

**异常:**

* `EmptyRecordsException`:查询结果为空时抛出

##### `<T extends Model> T firstOrNull()`

这个方法会根据id排序之后查询第一条记录.

**结果:**

* 查询的结果对象,查询结果为空时返回null

##### `<T extends Model> T last()`

这个方法会根据id倒序排序并去第一条记录

**结果:**

* 查询的结果对象,查询结果不会为null

**异常:**

* `EmptyRecordsException`:查询结果为空时抛出

##### `<T extends Model> T lastOrNull()`

这个方法会根据id倒序排序之后查询第一条记录.

**结果:**

* 查询的结果对象,查询结果为空时返回null

##### `<T extends Model> T findBy(String field,Object value)`

这个方法会根据传入的属性值创建查询条件并查询结果,比如传入("name","tomcat"),相当于创建查询条件:WHERE name='tomcat'.

**参数:**

* field : 要查找的属性名
* value : 要查找的属性值

**结果:**

* 查询的结果对象,有可能为null.

**异常:**  

* `TooManyRecordsException`:查询结果到两条以上的结果时会抛出

--------

### <a id="multi_query"></a>多行查询

多行查询的方法如下:

```java
findList(Object[] ids);
findListIfExists(Object[] ids);
findAll()/all();
first(int num);
last(int num);
lastCreated(int num);
lastUpdated(int num);
```

##### `<T extends Model> List<T> findList(Object[] ids)`

这个方法通过传入id数组查询多行记录并返回列表对象.

**参数:**

* ids : 查找记录的id数组

**结果:**

* 查询的结果对象列表,如果查询结果的列表的长度不等于ids数组的长度,则抛出异常

**异常:**  

* `IllegalArgumentException`:ids为null时抛出  
* `TooManyRecordsException`:查询结果长度大于ids数组的长度时抛出  
* `RecordNotFoundException`:查询结果长度小于ids数组的长度时抛

##### `<T extends Model> List<T> findListIfExists(Object[] ids)`

这个方法通过传入id数组查询多行记录并返回列表对象.

**参数:**

* ids : 查找记录的id数组

**结果:**

* 查询的结果对象列表,在ids数组中,能查到几条记录就返回几条记录

**异常:**  

* `IllegalArgumentException`:ids为null时抛出    
* `TooManyRecordsException`:查询结果长度大于ids数组的长度时抛出

##### `<T extends Model> List<T> findAll()和<T extends Model> List<T> all()`

这两个方法的作用是完全相同的,会直接查询整个表的全部数据并返回对象列表

**结果:**

* 查询数据库表的结果对象列表

##### `<T extends Model> List<T> first(int num)`

这个方法会按照id排序之后查找前num行数据

**参数:**

* num : 要查找的行数

**结果:**

* 查询的结果对象列表,查询结果小于num也不会抛出异常

**异常:**

* `IllegalArgumentException`:当传入的num值不大于0时抛出

##### `<T extends Model> List<T> last(int num)`

这个方法会按照id倒序排序之后查找前num行数据

**参数:**

* num : 要查找的行数

**结果:**

* 查询的结果对象列表,查询结果小于num也不会抛出异常

**异常:**

* `IllegalArgumentException`:当传入的num值不大于0时抛出

##### `<T extends Model> List<T> lastCreated(int num)`

这个方法会查询最后创建的几行记录并返回,实际上就是根据`createdAt`字段倒序排序查询的.

**参数:**

* num : 要查找的行数

**结果:**

* 查询的结果对象列表,查询结果小于num也不会抛出异常

**异常:**

* `IllegalArgumentException`:当传入的num值不大于0时抛出
* `ObjectNotFoundExceptiony`:找不到`createdAt`属性时会抛出

##### `<T extends Model> List<T> lastUpdated(int num)`

这个方法会查询最后修改的几行记录并返回,实际上就是根据`updatedAt`字段倒序排序查询的.

**参数:**

* num : 要查找的行数

**结果:**

* 查询的结果对象列表,查询结果小于num也不会抛出异常

**异常:**

* `IllegalArgumentException`:当传入的num值不大于0时抛出
* `ObjectNotFoundExceptiony`:找不到`updatedAt`属性时抛出

--------

## <a id="create"></a>新增方法

### <a id="single_create"></a>单行新增

```java
create(Map<String, Object> fields);
save();
trySave();
create()/insert();
create(CreateCallback<T> callback);
create(PostCreateCallback<T> callback);
tryCreate();
tryCreate(CreateCallback<T> callback);
```

##### `<T extends Model> T create(Map<String, Object> fields)`

根据传入的map属性创建一个Model对象并插入到数据库中,fields的key/value分别对应model的属性和值,相当于

```java
T t = new T();
t.setKey(fields.get("key"));
t.create();
```

**参数:**

* fields : Model对应的属性和值.

**结果:**

* 返回创建并插入数据库后的对象.

##### `<T extends Model> T save()`

保存Model对象,这里的保存有可能是insert也有可能是update,根据model的id判断,如果id存在,则执行update,如果id不存在,则执行insert.

**结果:**

* 返回保存后的对象

**异常:**

* `RecordNotSavedException`:保存记录失败时抛出

##### `boolean trySave()`

保存Model对象,这里的保存有可能是insert也有可能是update,根据model的id判断,如果id存在,则执行update,如果id不存在,则执行insert.

**结果:**

* 保存成功则返回true,保存失败则返回false

##### `<T extends Model> T create()`和`<T extends Model> T insert()`

将Model对象插入数据库,如果没有id则根据默认规则自动生成id.

**结果:**

* 返回插入数据库后的对象

**异常:**

* `RecordNotSavedException`:插入记录失败时抛出

##### `<T extends Model> void create(CreateCallback<T> callback)`

将Model对象插入数据库,在插入前进行回调操作.

**参数:**

* callback:CreateCallback的实例,在Model对象保存之前会调用`callback.preCreate()`,保存之后会调用`callback.postCreate()`,这个过程受事务控制,可以通过抛出异常回滚保存.

**异常:**

* `RecordNotSavedException`:插入记录失败时抛出

##### `<T extends Model> void create(PostCreateCallback<T> callback)`

将Model对象插入数据库,在插入前进行回调操作.

**参数:**

* callback:PostCreateCallback的实例,PostCreateCallback是CreateCallback的子接口,提供了`preCreate(T record, TransactionStatus s)`的默认实现,因此只需要实现`postCreate(T record, TransactionStatus s)`即可

**异常:**

* `RecordNotSavedException`:插入记录失败时抛出

##### `boolean tryCreate()`

将Model对象插入数据库,如果没有id则根据默认规则自动生成id.

**结果:**

* 插入成功则返回true,失败则返回false

##### `<T extends Model> boolean tryCreate(CreateCallback<T> callback)`

将Model对象插入数据库,在插入前进行回调操作.

**参数:**

* callback:CreateCallback的实例,在Model对象保存之前会调用`callback.preCreate()`,保存之后会调用`callback.postCreate()`,这个过程受事务控制,可以通过抛出异常回滚保存.

**结果:**

* 插入成功则返回true,失败则返回false

------

### <a id="multi_create"></a>多行新增

```java
createAll(Object[] records);
```

##### `int[] createAll(Object[] records)`

批量插入数据库记录.

**参数:**

* records:需要插入数据库的Model对象数组

**结果:**

* 插入成功则返回每一行执行结果的影响行数(数据库的返回结果),失败则抛出异常.

**异常:**

* `IllegalArgumentException`:插入records为null时抛出

## <a id="update"></a>更新方法

### <a id="single_update"></a>单行更新

```java
update(Object id,Map<String, Object> fields);
update(Object id,String field,Object value);
update();
update(UpdateCallback<T> callback);
update(PostUpdateCallback<T> callback);
tryUpdate();
tryUpdate(UpdateCallback<T> callback);
```

##### `boolean update(Object id,Map<String, Object> fields)`

更新指定id的数据库记录,fields表示的是需要更新的字段,Key/Value表示field/value,比如:

```java
Map kv = new HashMap();
kv.put("name","jim");
kv.put("age","3");
Model.update("a",kv);
```

那么最终执行的sql如下:

```sql
UPDATE model SET name='jim', age=3 WHERE id = 'a'
```

**参数:**

* id : 需要更新的记录id
* fields : 需要更新的记录对应的属性和值

**结果:**

* 有记录被更新则返回true,没有记录被更新则返回false

##### `boolean update(Object id,String field,Object value)`

更新指定id的数据库记录,field表示的是需要更新的字段,value表示目标值,这个方法相当于`boolean update(Object id,Map<String, Object> fields)`仅设置一组key/value时的效果一致.

**参数:**

* id : 需要更新的记录id
* field : 需要更新的记录对应的属性
* value : 需要将属性修改的目标值

**结果:**

* 有记录被更新则返回true,没有记录被更新则返回false

##### `<T extends Model> T update()`

根据id更新当前Model实例对象,并且包含所有为null的属性,比如:

```java
Model model = new Model();
model.setId("id");
model.update();
```

上面的代码最终会执行如下sql:

```sql
UPDATE model SET field1 = null, field2 = null ,... WHERE id='id'
```

**结果:**

* 更新成功则返回被更新的对象,更新失败则抛出异常

**异常:**

* `IllegalArgumentException`:当实例对象的id为null时抛出
* `RecordNotSavedException`:更新记录失败时抛出

##### `<T extends Model> T update(UpdateCallback<T> callback)`

更新Model对象实例,在更新前进行回调操作.

**参数:**

* callback:UpdateCallback的实例,在Model对象保存之前会调用`callback.preCreate()`,保存之后会调用`callback.postCreate()`,这个过程受事务控制,可以通过抛出异常回滚保存.

**结果:**

* 更新成功则返回被更新的对象,失败则抛出异常

**异常:**

* `IllegalStateException`:当被更新的实例对象id为null时抛出
* `RecordNotSavedException`:更新记录失败时抛出

##### `<T extends Model> T update(PostUpdateCallback<T> callback)`

更新Model对象实例,在更新前进行回调操作.

**参数:**

* callback:PostUpdateCallback的实例,在Model对象保存之前会调用`callback.preCreate()`,保存之后会调用`callback.postCreate()`,这个过程受事务控制,可以通过抛出异常回滚保存.  
`callback.preCreate()`已经提供了默认实现,因此只需要实现`callback.postCreate()`即可.

**结果:**

* 更新成功则返回被更新的对象,失败则抛出异常

**异常:**

* `IllegalStateException`:当被更新的实例对象id为null时抛出
* `RecordNotSavedException`:更新记录失败时抛出

##### `boolean tryUpdate()`

更新Model对象实例.

**结果:**

* 有记录被更新则返回true,没有记录被更新则返回false

**异常:**

* `IllegalStateException`:当被更新的实例对象id为null时抛出

##### `<T extends Model> boolean tryUpdate(UpdateCallback<T> callback)`

更新Model对象实例,在更新前进行回调操作.

**参数:**

* callback:UpdateCallback的实例,在Model对象保存之前会调用`callback.preCreate()`,保存之后会调用`callback.postCreate()`,这个过程受事务控制,可以通过抛出异常回滚保存.  

**结果:**

* 有记录被更新则返回true,没有记录被更新则返回false

**异常:**

* `IllegalStateException`:当被更新的实例对象id为null时抛出

------

### <a id="multi_update"></a>多行更新

```java
updateAll(Object[] records);
updateAll(Condition<T> condition);
updateAll(String whereExpression);
updateAll(String whereExpression,Object... args);
updateAll(String whereExpression,Map<String, Object> params);
updateAll(Map<String, Object> fields, String whereExpression, Object... args);
```

##### `int[] updateAll(Object[] records)`

同时更新多个Model实例对象.

**参数:**

* records:需要更新的实例对象数组

**结果:**

* 每个对象更新后影响的行数的数组`

**异常:**

* `IllegalStateException`:当被更新的实例对象中包含

##### `<T extends Model> int updateAll(Condition<T> condition)`

// 预留接口,暂不可用

##### `int updateAll(String whereExpression)`

根据条件表达式更新记录,更新的属性是当前实例对象的属性,但是id(主键属性)不会被更新,比如:

```java
Model model = new Model();
model.setField1("field1");
model.setField2("field2");
model.updateAll("name='modelname'");
```

那么最终执行的sql如下:

```sql
UPDATE model SET field1 = 'field1', field2='field2' WHERE name='modelname'
```

**参数:**

* whereExpression:条件表达式,可以包含WHERE也可以不包含WHERE,但是需要按照sql的语法.

**结果:**

* 返回受影响的行数

##### `int updateAll(String whereExpression,Object... args)`

根据条件表达式更新记录,更新的属性是当前实例对象的属性,但是id(主键属性)不会被更新,这里的条件表达式允许带参数占位符,比如:

```java
Model model = new Model();
model.setField1("field1");
model.setField2("field2");
model.updateAll("name=?","modelname");
```

那么最终执行的sql如下:

```sql
UPDATE model SET field1 = 'field1', field2='field2' WHERE name='modelname'
```

这里有一点需要特别注意的:  

我们已经知道leapQL对IN关键字的特殊处理,如果这里我们使用如下代码:

```java
String names = new String[]{"name1","name2","name3"};
model.updateAll("name IN ?",names);
```

执行时会发现sql解析失败了,并且抛出的异常是参数占位符的数量和参数值不对应,这是因为java本身对可变传参的处理导致的问题.

java中,可变传参是已数组的方式接收的,这样会导致我们的数组传进去后,每一个元素都被当成可变传参的单独参数了,因此会导致参数数量和占位符数量不同的异常,解决的方法是创建一个Object[]数组,并将我们的数组对象作为Object[]数组的元素即可,如下:


```java
String names = new String[]{"name1","name2","name3"};
model.updateAll("name IN ?",new Object[]{names});
```

最终执行的sql如下:

```sql
UPDATE model SET name=xxx WHERE name IN ("name1","name2","name3")
```

**参数:**

* whereExpression:条件表达式,可以包含WHERE也可以不包含WHERE,但是需要按照sql的语法.
* args:参数占位符对应的参数值

**结果:**

* 返回受影响的行数

##### `int updateAll(String whereExpression,Map<String, Object> params)`

根据条件表达式更新记录,更新的属性是当前实例对象的属性,但是id(主键属性)不会被更新,这里的条件表达式允许带命名参数占位符,比如:

```java
Model model = new Model();
model.setField1("field1");
model.setField2("field2");
Map map = new HashMap();
map.put("name", "modelname");
model.updateAll("name=:name",map);
```

那么最终执行的sql如下:

```sql
UPDATE model SET field1 = 'field1', field2='field2' WHERE name='modelname'
```

**参数:**

* whereExpression:条件表达式,可以包含WHERE也可以不包含WHERE,但是需要按照sql的语法.
* params:命名参数的参数名和值

**结果:**

* 返回受影响的行数

##### `int updateAll(Map<String, Object> fields, String whereExpression, Object... args)`

将符合where条件的所有数据库记录的属性值更新成fields中的value,这里的条件表达式允许带参数占位符,比如:

```java
Map kv = new HashMap();
kv.put("name","jim");
kv.put("age","3");
Model.updateAll(kv,"name=?","lucy");
```

那么最终执行的sql如下:

```sql
UPDATE model SET name='jim', age=3 WHERE name = 'lucy'
```

这里的条件表达式包含IN关键字的参数时,可以参考`int updateAll(String whereExpression,Object... args)`的说明处理.

**参数:**

* fields : 需要更新的记录对应的属性和值
* whereExpression : sql的where表达式,可以包含where也可以不写where,通过占位符`?`表示参数
* args : where表达式的参数列表,按照顺序设入where表达式的占位符

**结果:**

* 返回结果为int,表示受影响的行数

-------

## <a id="delete"></a>删除方法

### <a id="single_delete"></a>单行删除

```java
delete(Object id);
tryDelete(Object id);
delete();
tryDelete();
```

##### `void delete(Object id)`

根据id删除指定记录

**参数:**

* id:需要删除的记录id

**异常:**

* `IllegalStateException`:当id为null时抛出异常
* `RecordNotDeletedException`:当删除失败(包括没有找到指定id的记录)时抛出异常

##### `boolean tryDelete(Object id)`

根据id删除指定记录

**参数:**

* id:需要删除的记录id

**结果:**

* 删除成功返回true,没有删除记录或者删除记录失败返回false

**异常:**

* `IllegalStateException`:当id为null时抛出异常
* `RecordNotDeletedException`:当删除失败过程出现错误时抛出

##### `void delete()`

根据model实例对象id删除数据库中对应当前model对象的记录.

**异常:**

* `IllegalStateException`:当实例对象的id为null时抛出
* `RecordNotDeletedException`:当删除失败或数据库中没有对应model实例的记录是抛出

##### `boolean tryDelete()`

根据model实例对象id删除数据库中对应当前model对象的记录.

**结果:**

* 成功删除则返回true,没有找到对应的数据库记录则返回false

**异常:**

* `IllegalStateException`:当实例对象的id为null时抛出

------

### <a id="multi_delete"></a>多行删除

```java
deleteAll();
deleteAll(Object[] ids);
deleteAll(Condition<T> condition);
deleteAll(String whereExpression);
deleteAll(String whereExpression,Object... args);
deleteAll(String whereExpression,Map<String, Object> params);
deleteBy(String field,Object value);
```

##### `int deleteAll()`

删除数据库表的全部记录

**结果:**

* 返回受影响的数据库行数

##### `int[] deleteAll(Object[] ids)`

根据id数组数据库记录

**参数:**

* ids : 需要删除的记录的id数组

**结果:**

* 返回受影响的数据库行数

##### `<T extends Model> int deleteAll(Condition<T> condition)`

// 预留接口,暂不可用

##### `int deleteAll(String whereExpression)`

根据条件表达式删除数据库记录,比如:

```java
Model.deleteAll("name='a'");
```

最终执行的sql如下:

```sql
DELETE FROM model WHERE name='a'
```

**参数:**

* whereExpression : 条件表达式

**结果:**

* 返回受影响的数据库行数

##### `int deleteAll(String whereExpression,Object... args)`

根据条件表达式(带参数占位符)和参数删除数据库记录,比如:

```java
Model.deleteAll("name=?","a");
```

最终执行的sql如下:

```sql
DELETE FROM model WHERE name='a'
```

**参数:**

* whereExpression : 条件表达式
* args : 参数占位符的值

**结果:**

* 返回受影响的数据库行数

##### `int deleteAll(String whereExpression,Map<String, Object> params)`

根据条件表达式和命名参数删除数据库记录,比如:

```java
Map map = new HashMap();
map.put("name","a");
Model.deleteAll("name=:name",map);
```

最终执行的sql如下:

```sql
DELETE FROM model WHERE name='a'
```

**参数:**

* whereExpression : 条件表达式
* params : 命名参数占位符的key和value

**结果:**

* 返回受影响的数据库行数

##### `int deleteBy(String field,Object value)`

根据属性的值删除数据库记录,比如:

```java
Model.deleteBy("name","a");
```

最终执行的sql如下:

```sql
DELETE FROM model WHERE name='a'
```

**参数:**

* field : 属性名
* value : 属性值

**结果:**

* 返回受影响的数据库行数

------

## <a id="object_query"></a>查询对象方法

### <a id="entity_query"></a>`EntityQuery`查询

```java
query(String sqlOrKey);
```

通过Model的`<T extends Model> EntityQuery<T> query(String sqlOrKey)`方法可以创建一个Model类的`EntityQuery`查询对象.

##### `<T extends Model> EntityQuery<T> query(String sqlOrKey)`

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


##### `EntityQuery`查询对象

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
* `T single()`
* `T singleOrNull()`

以上接口可以参考Model对应的接口说明.

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

需要注意的是,`scalar()`相当于`Model.single()`,当没有查找到记录或者查找到的记录多于一行都会抛出异常,`scalarOrNull()`类似`Model.singleOrNull()`.  
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

------

### <a id="criteria_query"></a>`CriteriaQuery`查询

```java
query();
where();
where(Condition<T> condition);
where(String expression);
where(String expression,Object... args);
```

##### `<T extends Model> CriteriaQuery<T> query()`

创建一个无查询条件的CriteriaQuery查询对象

##### `<T extends Model> CriteriaWhere<T> where()`

创建一个无查询条件的CriteriaQuery查询对象,和`<T extends Model> CriteriaQuery<T> query()`效果相同

##### `<T extends Model> CriteriaQuery<T> where(Condition<T> condition)`

// 预留接口,暂不可用

##### `<T extends Model> CriteriaQuery<T> where(String expression)`

创建一个CriteriaQuery查询对象并添加参查询条件expression

##### `<T extends Model> CriteriaQuery<T> where(String expression,Object... args)`

创建一个CriteriaQuery查询对象并添加包含参数占位符的查询条件,args表示参数值

##### `CriteriaQuery`查询对象

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

------

## <a id="transaction"></a>事务控制方法

```java
doTransaction(TransactionCallback callback);
doTransaction(TransactionCallbackWithResult<T> callback);
doTransaction(TransactionCallback callback, boolean requiresNew);
doTransaction(TransactionCallbackWithResult<T> callback, boolean requiresNew);
```

##### `void doTransaction(TransactionCallback callback)`

通过函数接口TransactionCallback执行数据库操作,保证所有操作都在同一个事务控制下,示例如下:

```java
Model model1 = new Model();
Model model2 = new Model();
Model.doTransaction((TransactionStatus s)->{
	model1.create();
	model2.create();
});
```

此时model1的插入和model2的插入记录在一个事务控制中,如果任何一个语句抛出异常了,都会导致整个事务回滚.  
也可以通过`TransactionStatus.setRollbackOnly()`设置回滚.

**参数:**

* callback:事务控制回调对象,是函数接口TransactionCallback的实例对象,在`callback.doInTransaction()`内部实现的业务逻辑都会受事务控制

##### `static <T> T doTransaction(TransactionCallbackWithResult<T> callback)`

和`void doTransaction(TransactionCallback callback)`相似,区别在于这个接口可以返回值.

**参数:**

* callback:事务控制回调对象,是函数接口TransactionCallbackWithResult的实例对象,在`callback.doInTransaction()`内部实现的业务逻辑都会受事务控制,并且`callback.doInTransaction()`的返回值将直接作为这个接口的返回值.

**结果:**

* callback的返回结果.

##### `void doTransaction(TransactionCallback callback, boolean requiresNew)`

和`void doTransaction(TransactionCallback callback)`相似,区别参数`requiresNew`,这个参数表示是否使用目前生存的事务对象还是新建一个事务对象.

`void doTransaction(TransactionCallback callback)`相当于`void doTransaction(TransactionCallback callback, false)`

**参数:**

* callback:事务控制回调对象,是函数接口TransactionCallbackWithResult的实例对象,在`callback.doInTransaction()`内部实现的业务逻辑都会受事务控制,并且`callback.doInTransaction()`的返回值将直接作为这个接口的返回值.
* requiresNew:是否创建一个新的事务对象,true表示创建新的事务对象,false表示不创建新的事务对象


##### `<T> T doTransaction(TransactionCallbackWithResult<T> callback, boolean requiresNew)`

和`void doTransaction(TransactionCallback callback, boolean requiresNew)`相似,区别在于这个接口可以返回值.

**参数:**

* callback:事务控制回调对象,是函数接口TransactionCallbackWithResult的实例对象,在`callback.doInTransaction()`内部实现的业务逻辑都会受事务控制,并且`callback.doInTransaction()`的返回值将直接作为这个接口的返回值.
* requiresNew:是否创建一个新的事务对象,true表示创建新的事务对象,false表示不创建新的事务对象

**结果:**

* callback的返回结果.

-------

## <a id="cmd"></a>指令方法

```java
cmdInsert();
cmdUpdate(Object id);
```

##### `InsertCommand cmdInsert()`

创建一个插入指令对象,这个对象可以链式调用设置属性并最终执行插入记录的sql.示例如下:

```java
Map map = new HashMap();
map.put("name", "a");
Model.cmdInsert().setAll(map).set("age",3).execute();
```

最终执行的sql如下:

```sql
INSERT INTO model (name, age) VALUES ('a', 3);
```

这个插入指令的几个接口的简单说明如下:

* `getGeneratedId()`:生成并返回id
* `set(String name,Object value)`:设置属性值
* `setAll(Map<String, Object> map)`:将key/value对应为属性/值设置到插入指令中
* `setAll(Object bean)`:将bean的属性名和属性值按照key/value的方式设置到指令中,类似`setAll(Map<String, Object> map)`
* `int execute()`:执行指令,返回结果是受影响的数据库行数

**结果:**

* 返回创建的插入指令对象

##### `UpdateCommand cmdUpdate(Object id)`

创建一个更新指令对象,这个对象可以链式调用设置属性并最终执行更新记录的sql.示例如下:

```java
Map map = new HashMap();
map.put("name", "a");
Model.cmdUpdate().id("id").setAll(map).set("age",3).execute();
```

最终执行的sql如下:

```sql
UPDATE model SET name='a', age=3 WHERE id='id'
```

这个插入指令的几个接口的简单说明如下:

* `id(Object id)`:设置更新记录的id
* `set(String name,Object value)`:设置属性值
* `setAll(Map<String, Object> map)`:将key/value对应为属性/值设置到插入指令中
* `setAll(Object bean)`:将bean的属性名和属性值按照key/value的方式设置到指令中,类似`setAll(Map<String, Object> map)`
* `int execute()`:执行指令,返回结果是受影响的数据库行数

**结果:**

* 返回创建的更新指令对象

-------

## <a id="other"></a>其他方法

### <a id="newinstance"></a>实例创建方法

```java
newInstance();
newInstance(Object id);
```

##### `<T extends Model> T newInstance()`

创建Model的实例对象.

**结果:**

* 返回创建的Model实例对象

##### `<T extends Model> T newInstance(Object id)`

创建Model的实例对象,并给该对象的id设置值.

**参数:**

* id:创建的实例对象的id

**结果:**

* 返回创建的Model实例对象

-------

### <a id="database"></a>数据库相关方法

```java
dao();
dmo();
metamodel();
db();
count();
```

##### `Dao dao()`

返回当前Model类的dao对象

**结果:**

* 返回当前Model类的dao对象

##### `Dmo dmo()`

返回当前Model类的dmo对象

**结果:**

* 返回当前Model类的dmo对象

##### `EntityMapping metamodel()`

返回当前Model类的实体映射对象,这个对象用于记录Model类和数据库表的映射关系

**结果:**

* 返回当前Model类的实体映射对象

##### `Db db()`

返回当前Model类的Db对象,这个对象表示的是数据库对象,包含了数据库的信息

**结果:**

* 返回当前Model类的Db对象

##### `long count()`

返回当前Model类对应数据库表的记录数

**结果:**

* 返回当前Model类对应的数据库表记录行数

-------

### <a id="operate"></a>普通操作相关方法

```java
getEntityName();
contains(String field);
get(String field);
get(Named field);
getString(String field);
getInteger(String field);
fields();
id();
id(Object id);
set(String field,Object value);
createdAt();
updatedAt();
set(Named field,Object value);
setAll(Map<String,? extends Object> fields);
refresh();
validate();
errors();
validate(int maxErrors);
getProperty(String name);
setProperty(String name, Object value);
getProperties();
```

##### `String getEntityName()`

获取Model类的类名

**结果:**

* 返回Model类名

##### `boolean contains(String field)`

检验Model类是否包含指定属性

**参数:**

* field:需要校验的属性名

**结果:**

* 如果Model类包含属性`field`则返回true,否则返回false

##### `Object get(String field)`

从model对象中获取指定属性名的属性值.

这里要特别注意,这个属性值并不一定是Model类中显式声明的属性值,举例如下:

```java
public class model1 extends Model{
	private String id;
	private String name;

	// 省略get和set方法
}
public class model2 extends Model{
	private String id;
	private Integer age;

	// 省略get和set方法
}
```

假设我们执行sql如下:

```sql
SELECT m.name,m.id,n.age FROM model1 m JOIN model2 n ON m.id=n.id
```

将最终返回的结果解析到`List<Model1> list`中的话,我们会发现,`n.age`的属性通过model1的实例属性是无法获取的,这个时候我们就可以通过这个方法来获取了:

```java
model1.get("age")
```

此时就可以获得这个字段的查询结果了.

另外,如下两个方式获取字段值的结果是等价的:

```java
model1.getName();
model1.get("name");
```

**参数:**

* field:需要获取值的属性名

**结果:**

* 属性`field`的值


##### `Object get(Named field)`

效果与`Object get(String field)`一致,参数类型不同而已.

**参数:**

* field:Named的实例对象,Named是一个接口,这个实例对象的`field.getName()`返回属性名.

**结果:**

* 属性`field`的值

##### `String getString(String field)`

获取属性`field`的值并转换成String类型返回

**参数:**

* field:属性名.

**结果:**

* 属性`field`的值

##### `Integer getInteger(String field)`

获取属性`field`的值并转换成Integer类型返回

**参数:**

* field:属性名.

**结果:**

* 属性`field`的值

##### `Map<String, Object> fields()`

以Map的方式返回Model对象实例的所有属性值(包括没有显式声明但是有设置的值),这里返回的是属性副本,对这个副本的操作不会影响model实例对象.

**结果:**

* Model实例对象的所有属性值.

##### `Object id()`

返回Model实例对象的id

**结果:**

* Model实例对象的id

##### `<T extends Model> T id(Object id)`

设置Model实例对象的id

**结果:**

* Model实例对象本身

##### `<T extends Model> T set(String field,Object value)`

设置Model实例对象的属性,这个属性不一定是要Model类显式声明的属性.

**参数:**

* field:要设置的属性名
* value:要设置的属性值

**结果:**

* 返回Model实例对象本身

##### `Timestamp createdAt()`

返回Model实例对象的创建时间,如果实例对象没有`createdAt`属性则抛出异常

**结果:**

返回Model实例对象的`createdAt`属性值


##### `Timestamp updatedAt()`

返回Model实例对象的创建时间,如果实例对象没有`updatedAt`属性则抛出异常

**结果:**

* 返回Model实例对象的`updatedAt`属性值

##### `<T extends Model> T set(Named field,Object value)`

与`<T extends Model> T set(String field,Object value)`类似

##### `<T extends Model> T setAll(Map<String,? extends Object> fields)`

同时设置多个属性值到Model的实例对象中,效果与多次调用`<T extends Model> T set(String field,Object value)`相同

##### `boolean refresh()`

刷新Model实例对象,重新从数据库读取各属性的值.

**结果:**

* 刷新成功则返回true.

##### `boolean validate()`

根据Model类的字段限制(@Column注解或者数据库字段的其他限制)校验Model实例对象.

**结果:**

* 校验不通过则返回false,通过则返回true

##### `Errors errors()`

返回Model的校验出错的结果,当调用`validate()`等校验方法时,如果校验失败,就会将校验失败的信息放到这个error对象中.

**结果:**

* Model实例对象校验失败的错误信息

##### `boolean validate(int maxErrors)`

根据Model类的字段限制(@Column注解或者数据库字段的其他限制)校验Model实例对象.

**参数:**

maxErrors:最大校验失败数

**结果:**

* 校验不通过的属性值大于`maxErrors`时返回false,反之这返回true

##### `Object getProperty(String name)`

与`Object get(String field)`效果相同

##### `void setProperty(String name, Object value)`

与`<T extends Model> T set(String field,Object value)`效果相同,但是没有返回值

##### `Map<String, Object> getProperties()`

与`Map<String, Object> fields()`效果相同