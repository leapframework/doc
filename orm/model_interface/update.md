# 更新方法

## <a id="single_update"></a>单行更新

<!--sec data-title="update(Object id,Map &lt; String, Object &gt; fields)" data-id="update1" data-show=true ces-->

```java
boolean update(Object id,Map<String, Object> fields)
```

------

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

<!--endsec-->

<!--sec data-title="update(Object id,String field,Object value)" data-id="update2" data-show=true ces-->

```java
boolean update(Object id,String field,Object value)
```

------

更新指定id的数据库记录,field表示的是需要更新的字段,value表示目标值,这个方法相当于`boolean update(Object id,Map<String, Object> fields)`仅设置一组key/value时的效果一致.

**参数:**

* id : 需要更新的记录id
* field : 需要更新的记录对应的属性
* value : 需要将属性修改的目标值

**结果:**

* 有记录被更新则返回true,没有记录被更新则返回false

<!--endsec-->

<!--sec data-title="update()" data-id="update3" data-show=true ces-->

```java
<T extends Model> T update()
```

------

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

<!--endsec-->

<!--sec data-title="update(UpdateCallback &lt; T &gt; callback)" data-id="update4" data-show=true ces-->

```java
<T extends Model> T update(UpdateCallback<T> callback)
```

------

更新Model对象实例,在更新前进行回调操作.

**参数:**

* callback:UpdateCallback的实例,在Model对象保存之前会调用`callback.preCreate()`,保存之后会调用`callback.postCreate()`,这个过程受事务控制,可以通过抛出异常回滚保存.

**结果:**

* 更新成功则返回被更新的对象,失败则抛出异常

**异常:**

* `IllegalStateException`:当被更新的实例对象id为null时抛出
* `RecordNotSavedException`:更新记录失败时抛出

<!--endsec-->

<!--sec data-title="update(PostUpdateCallback &lt; T &gt; callback)" data-id="update5" data-show=true ces-->

```java
<T extends Model> T update(PostUpdateCallback<T> callback)
```

------

更新Model对象实例,在更新前进行回调操作.

**参数:**

* callback:PostUpdateCallback的实例,在Model对象保存之前会调用`callback.preCreate()`,保存之后会调用`callback.postCreate()`,这个过程受事务控制,可以通过抛出异常回滚保存.  
`callback.preCreate()`已经提供了默认实现,因此只需要实现`callback.postCreate()`即可.

**结果:**

* 更新成功则返回被更新的对象,失败则抛出异常

**异常:**

* `IllegalStateException`:当被更新的实例对象id为null时抛出
* `RecordNotSavedException`:更新记录失败时抛出

<!--endsec-->

<!--sec data-title="tryUpdate()" data-id="tryUpdate1" data-show=true ces-->

```java
boolean tryUpdate()
```

------

更新Model对象实例.

**结果:**

* 有记录被更新则返回true,没有记录被更新则返回false

**异常:**

* `IllegalStateException`:当被更新的实例对象id为null时抛出

<!--endsec-->

<!--sec data-title="tryUpdate(UpdateCallback &lt; T &gt; callback)" data-id="tryUpdate2" data-show=true ces-->

```java
<T extends Model> boolean tryUpdate(UpdateCallback<T> callback)
```

------

更新Model对象实例,在更新前进行回调操作.

**参数:**

* callback:UpdateCallback的实例,在Model对象保存之前会调用`callback.preCreate()`,保存之后会调用`callback.postCreate()`,这个过程受事务控制,可以通过抛出异常回滚保存.  

**结果:**

* 有记录被更新则返回true,没有记录被更新则返回false

**异常:**

* `IllegalStateException`:当被更新的实例对象id为null时抛出

<!--endsec-->

## <a id="multi_update"></a>多行更新

<!--sec data-title="updateAll(Object[] records)" data-id="updateAll1" data-show=true ces-->

```java
int[] updateAll(Object[] records)
```

------

同时更新多个Model实例对象.

**参数:**

* records:需要更新的实例对象数组

**结果:**

* 每个对象更新后影响的行数的数组`

**异常:**

* `IllegalStateException`:当被更新的实例对象中包含

<!--endsec-->

<!--sec data-title="updateAll(Condition<T> condition)" data-id="updateAll2" data-show=true ces-->

```java
<T extends Model> int updateAll(Condition<T> condition)
```

-----

// 预留接口,暂不可用

<!--endsec-->

<!--sec data-title="updateAll(String whereExpression)" data-id="updateAll3" data-show=true ces-->

```java
int updateAll(String whereExpression)
```

------

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

<!--endsec-->

<!--sec data-title="updateAll(String whereExpression,Object... args)" data-id="updateAll4" data-show=true ces-->

```java
int updateAll(String whereExpression,Object... args)
```

------

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

<!--endsec-->

<!--sec data-title="updateAll(String whereExpression,Map &lt; String, Object &gt; params)" data-id="updateAll5" data-show=true ces-->

```java
int updateAll(String whereExpression,Map<String, Object> params)
```

------

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

<!--endsec-->

<!--sec data-title="updateAll(Map<String, Object> fields, String whereExpression, Object... args)" data-id="updateAll6" data-show=true ces-->

```java
int updateAll(Map<String, Object> fields, String whereExpression, Object... args)
```

------

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

<!--endsec-->

