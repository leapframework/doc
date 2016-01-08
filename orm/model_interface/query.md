# 查询方法

## <a id='single_query'></a>单行查询

<!--sec data-title="find(Object id)" data-id="find1" data-show=true ces-->

```java
<T extends Model> T find(Object id)
```

---------

这个方法是传入一个id查找记录并返回查询的结果对象.

**参数:**

* id : 查找记录的id

**结果:**

* 查询的结果对象,不会为null

**异常:**

* `IllegalArgumentException`:id为null时抛出  
* `RecordNotFoundException`:查询结果为空时抛出  
* `IllegalStateException`:查询结果包含多条记录时抛出

<!--endsec-->

<!--sec data-title="findOrNull(Object id)" data-id="findOrNull1" data-show=true ces-->

```java
<T extends Model> T findOrNull(Object id)
```

---------

这个方法是传入一个id查找记录并返回查询的结果对象.

**参数:**

* id : 查找记录的id

**结果:**

* 查询的结果对象,查找不到记录时返回null

**异常:**  

* `IllegalArgumentException`:id为null时抛出  
* `IllegalStateException`:查询结果包含多条记录时抛出

<!--endsec-->

<!--sec data-title="first()" data-id="first1" data-show=true ces-->

```java
<T extends Model> T first()
```

------

这个方法会根据id排序之后查询第一条记录.

**结果:**

* 查询的结果对象,查询结果不会为null

**异常:**

* `EmptyRecordsException`:查询结果为空时抛出

<!--endsec-->

<!--sec data-title="firstOrNull()" data-id="firstOrNull1" data-show=true ces-->

```java
<T extends Model> T firstOrNull()
```

------

这个方法会根据id排序之后查询第一条记录.

**结果:**

* 查询的结果对象,查询结果为空时返回null

<!--endsec-->

<!--sec data-title="last()" data-id="last1" data-show=true ces-->

```java
<T extends Model> T last()
```

------

这个方法会根据id倒序排序并去第一条记录

**结果:**

* 查询的结果对象,查询结果不会为null

**异常:**

* `EmptyRecordsException`:查询结果为空时抛出

<!--endsec-->

<!--sec data-title="lastOrNull()" data-id="lastOrNull1" data-show=true ces-->

```java
<T extends Model> T lastOrNull()
```

------

这个方法会根据id倒序排序之后查询第一条记录.

**结果:**

* 查询的结果对象,查询结果为空时返回null

<!--endsec-->

<!--sec data-title="findBy(String field,Object value)" data-id="findBy1" data-show=true ces-->

```java
<T extends Model> T findBy(String field,Object value)
```

------

这个方法会根据传入的属性值创建查询条件并查询结果,比如传入("name","tomcat"),相当于创建查询条件:WHERE name='tomcat'.

**参数:**

* field : 要查找的属性名
* value : 要查找的属性值

**结果:**

* 查询的结果对象,有可能为null.

**异常:**  

* `TooManyRecordsException`:查询结果到两条以上的结果时会抛出

<!--endsec-->

## <a id="multi_query"></a>多行查询

<!--sec data-title="findList(Object[] ids)" data-id="findList1" data-show=true ces-->

```java
<T extends Model> List<T> findList(Object[] ids)
```

------

这个方法通过传入id数组查询多行记录并返回列表对象.

**参数:**

* ids : 查找记录的id数组

**结果:**

* 查询的结果对象列表,如果查询结果的列表的长度不等于ids数组的长度,则抛出异常

**异常:**  

* `IllegalArgumentException`:ids为null时抛出  
* `TooManyRecordsException`:查询结果长度大于ids数组的长度时抛出  
* `RecordNotFoundException`:查询结果长度小于ids数组的长度时抛出

<!--endsec-->

<!--sec data-title="findListIfExists(Object[] ids)" data-id="findListIfExists1" data-show=true ces-->

```java
<T extends Model> List<T> findListIfExists(Object[] ids)
```

------

这个方法通过传入id数组查询多行记录并返回列表对象.

**参数:**

* ids : 查找记录的id数组

**结果:**

* 查询的结果对象列表,在ids数组中,能查到几条记录就返回几条记录

**异常:**  

* `IllegalArgumentException`:ids为null时抛出    
* `TooManyRecordsException`:查询结果长度大于ids数组的长度时抛出

<!--endsec-->

<!--sec data-title="findAll()" data-id="findAll1" data-show=true ces-->

```java
<T extends Model> List<T> findAll()
```

------

这个方法的作用是直接查询整个表的全部数据并返回对象列表

**结果:**

* 查询数据库表的结果对象列表

<!--endsec-->

<!--sec data-title="all()" data-id="all1" data-show=true ces-->

```java
<T extends Model> List<T> all()
```

------

这个方法的作用是直接查询整个表的全部数据并返回对象列表

**结果:**

* 查询数据库表的结果对象列表

<!--endsec-->

<!--sec data-title="first(int num)" data-id="first2" data-show=true ces-->

```java
<T extends Model> List<T> first(int num)
```

------

这个方法会按照id排序之后查找前num行数据

**参数:**

* num : 要查找的行数

**结果:**

* 查询的结果对象列表,查询结果小于num也不会抛出异常

**异常:**

* `IllegalArgumentException`:当传入的num值不大于0时抛出

<!--endsec-->

<!--sec data-title="last(int num)" data-id="last2" data-show=true ces-->

```java
<T extends Model> List<T> last(int num)
```

------

这个方法会按照id倒序排序之后查找前num行数据

**参数:**

* num : 要查找的行数

**结果:**

* 查询的结果对象列表,查询结果小于num也不会抛出异常

**异常:**

* `IllegalArgumentException`:当传入的num值不大于0时抛出

<!--endsec-->

<!--sec data-title="lastCreated(int num)" data-id="lastCreated1" data-show=true ces-->

```java
<T extends Model> List<T> lastCreated(int num)
```

------

这个方法会查询最后创建的几行记录并返回,实际上就是根据`createdAt`字段倒序排序查询的.

**参数:**

* num : 要查找的行数

**结果:**

* 查询的结果对象列表,查询结果小于num也不会抛出异常

**异常:**

* `IllegalArgumentException`:当传入的num值不大于0时抛出
* `ObjectNotFoundExceptiony`:找不到`createdAt`属性时会抛出

<!--endsec-->

<!--sec data-title="lastUpdated(int num)" data-id="lastUpdated1" data-show=true ces-->

```java
<T extends Model> List<T> lastUpdated(int num)
```

------

这个方法会查询最后修改的几行记录并返回,实际上就是根据`updatedAt`字段倒序排序查询的.

**参数:**

* num : 要查找的行数

**结果:**

* 查询的结果对象列表,查询结果小于num也不会抛出异常

**异常:**

* `IllegalArgumentException`:当传入的num值不大于0时抛出
* `ObjectNotFoundExceptiony`:找不到`updatedAt`属性时抛出

<!--endsec-->
