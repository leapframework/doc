# 删除方法

## <a id="single_delete"></a>单行删除

<!--sec data-title="delete(Object id)" data-id="delete1" data-show=true ces-->

```java
void delete(Object id)
```

------

根据id删除指定记录

**参数:**

* id:需要删除的记录id

**异常:**

* `IllegalStateException`:当id为null时抛出异常
* `RecordNotDeletedException`:当删除失败(包括没有找到指定id的记录)时抛出异常

<!--endsec-->

<!--sec data-title="tryDelete(Object id)" data-id="tryDelete1" data-show=true ces-->

```java
boolean tryDelete(Object id)
```

------

根据id删除指定记录

**参数:**

* id:需要删除的记录id

**结果:**

* 删除成功返回true,没有删除记录或者删除记录失败返回false

**异常:**

* `IllegalStateException`:当id为null时抛出异常
* `RecordNotDeletedException`:当删除失败过程出现错误时抛出

<!--endsec-->

<!--sec data-title="delete()" data-id="delete2" data-show=true ces-->

```java
void delete()
```

------

根据model实例对象id删除数据库中对应当前model对象的记录.

**异常:**

* `IllegalStateException`:当实例对象的id为null时抛出
* `RecordNotDeletedException`:当删除失败或数据库中没有对应model实例的记录是抛出

<!--endsec-->

<!--sec data-title="tryDelete()" data-id="tryDelete2" data-show=true ces-->

```java
boolean tryDelete()
```

------

根据model实例对象id删除数据库中对应当前model对象的记录.

**结果:**

* 成功删除则返回true,没有找到对应的数据库记录则返回false

**异常:**

* `IllegalStateException`:当实例对象的id为null时抛出

<!--endsec-->

## <a id="multi_delete"></a>多行删除

<!--sec data-title="deleteAll()" data-id="deleteAll1" data-show=true ces-->

```java
int deleteAll()
```

------

删除数据库表的全部记录

**结果:**

* 返回受影响的数据库行数

<!--endsec-->

<!--sec data-title="deleteAll(Object[] ids)" data-id="deleteAll2" data-show=true ces-->

```java
int[] deleteAll(Object[] ids)
```

------

根据id数组数据库记录

**参数:**

* ids : 需要删除的记录的id数组

**结果:**

* 返回受影响的数据库行数

<!--endsec-->

<!--sec data-title="deleteAll(Condition<T> condition)" data-id="deleteAll3" data-show=true ces-->

```java
<T extends Model> int deleteAll(Condition<T> condition)
```

------

// 预留接口,暂不可用

<!--endsec-->

<!--sec data-title="deleteAll(String whereExpression)" data-id="deleteAll4" data-show=true ces-->

```java
int deleteAll(String whereExpression)
```

------

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

<!--endsec-->

<!--sec data-title="deleteAll(String whereExpression,Object... args)" data-id="deleteAll5" data-show=true ces-->

```java
int deleteAll(String whereExpression,Object... args)
```

------

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

<!--endsec-->

<!--sec data-title="deleteAll(String whereExpression,Map &lt; String, Object &gt; params)" data-id="deleteAll6" data-show=true ces-->

```java
int deleteAll(String whereExpression,Map<String, Object> params)
```

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

<!--endsec-->

<!--sec data-title="deleteBy(String field,Object value)" data-id="deleteBy1" data-show=true ces-->

```java
int deleteBy(String field,Object value)
```

------

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

<!--endsec-->
