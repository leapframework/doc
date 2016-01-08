# 新增方法

## <a id="single_create"></a>单行新增

<!--sec data-title="create(Map &lt; String, Object &gt; fields)" data-id="create1" data-show=true ces-->

```java
<T extends Model> T create(Map<String, Object> fields)
```

------

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

<!--endsec-->

<!--sec data-title="save()" data-id="save1" data-show=true ces-->

```java
<T extends Model> T save()
```

------

保存Model对象,这里的保存有可能是insert也有可能是update,根据model的id判断,如果id存在,则执行update,如果id不存在,则执行insert.

**结果:**

* 返回保存后的对象

**异常:**

* `RecordNotSavedException`:保存记录失败时抛出

<!--endsec-->

<!--sec data-title="trySave()" data-id="trySave1" data-show=true ces-->

```java
boolean trySave()
```

------

保存Model对象,这里的保存有可能是insert也有可能是update,根据model的id判断,如果id存在,则执行update,如果id不存在,则执行insert.

**结果:**

* 保存成功则返回true,保存失败则返回false

<!--endsec-->

<!--sec data-title="create()" data-id="create2" data-show=true ces-->

```java
<T extends Model> T create()
```

------

将Model对象插入数据库,如果没有id则根据默认规则自动生成id.

**结果:**

* 返回插入数据库后的对象

**异常:**

* `RecordNotSavedException`:插入记录失败时抛出

<!--endsec-->

<!--sec data-title="insert()" data-id="insert1" data-show=true ces-->

```java
<T extends Model> T insert()
```

------

将Model对象插入数据库,如果没有id则根据默认规则自动生成id.

**结果:**

* 返回插入数据库后的对象

**异常:**

* `RecordNotSavedException`:插入记录失败时抛出

<!--endsec-->

<!--sec data-title="create(CreateCallback &lt; T &gt; callback)" data-id="create3" data-show=true ces-->

```java
<T extends Model> void create(CreateCallback<T> callback)
```

------

将Model对象插入数据库,在插入前进行回调操作.

**参数:**

* callback:CreateCallback的实例,在Model对象保存之前会调用`callback.preCreate()`,保存之后会调用`callback.postCreate()`,这个过程受事务控制,可以通过抛出异常回滚保存.

**异常:**

* `RecordNotSavedException`:插入记录失败时抛出

<!--endsec-->

<!--sec data-title="create(PostCreateCallback &lt; T &gt; callback)" data-id="creat4" data-show=true ces-->

```java
<T extends Model> void create(PostCreateCallback<T> callback)
```

------

将Model对象插入数据库,在插入前进行回调操作.

**参数:**

* callback:PostCreateCallback的实例,PostCreateCallback是CreateCallback的子接口,提供了`preCreate(T record, TransactionStatus s)`的默认实现,因此只需要实现`postCreate(T record, TransactionStatus s)`即可

**异常:**

* `RecordNotSavedException`:插入记录失败时抛出

<!--endsec-->

<!--sec data-title="tryCreate()" data-id="tryCreate1" data-show=true ces-->

```java
boolean tryCreate()
```

------

将Model对象插入数据库,如果没有id则根据默认规则自动生成id.

**结果:**

* 插入成功则返回true,失败则返回false

<!--endsec-->

<!--sec data-title="tryCreate(CreateCallback<T> callback)" data-id="tryCreate2" data-show=true ces-->

```java
<T extends Model> boolean tryCreate(CreateCallback<T> callback)
```

------

将Model对象插入数据库,在插入前进行回调操作.

**参数:**

* callback:CreateCallback的实例,在Model对象保存之前会调用`callback.preCreate()`,保存之后会调用`callback.postCreate()`,这个过程受事务控制,可以通过抛出异常回滚保存.

**结果:**

* 插入成功则返回true,失败则返回false

<!--endsec-->

## <a id="multi_create"></a>多行新增

<!--sec data-title="createAll(Object[] records)" data-id="createAll1" data-show=true ces-->

```java
int[] createAll(Object[] records)
```

------

批量插入数据库记录.

**参数:**

* records:需要插入数据库的Model对象数组

**结果:**

* 插入成功则返回每一行执行结果的影响行数(数据库的返回结果),失败则抛出异常.

**异常:**

* `IllegalArgumentException`:插入records为null时抛出

<!--endsec-->
