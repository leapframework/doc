# 其他方法

## <a id="newinstance"></a>实例创建方法

<!--sec data-title="newInstance()" data-id="newInstance1" data-show=true ces-->

```java
<T extends Model> T newInstance()
```

------

创建Model的实例对象.

**结果:**

* 返回创建的Model实例对象

<!--endsec-->

<!--sec data-title="newInstance(Object id)" data-id="newInstance2" data-show=true ces-->

```java
<T extends Model> T newInstance(Object id)
```

------

创建Model的实例对象,并给该对象的id设置值.

**参数:**

* id:创建的实例对象的id

**结果:**

* 返回创建的Model实例对象

<!--endsec-->

## <a id="database"></a>数据库相关方法

<!--sec data-title="dao()" data-id="dao1" data-show=true ces-->

```java
Dao dao()
```

------

返回当前Model类的dao对象

**结果:**

* 返回当前Model类的dao对象

<!--endsec-->

<!--sec data-title="dmo()" data-id="dmo1" data-show=true ces-->

```java
Dmo dmo()
```

------

返回当前Model类的dmo对象

**结果:**

* 返回当前Model类的dmo对象

<!--endsec-->

<!--sec data-title="metamodel()" data-id="metamodel1" data-show=true ces-->

```java
EntityMapping metamodel()
```

------

返回当前Model类的实体映射对象,这个对象用于记录Model类和数据库表的映射关系

**结果:**

* 返回当前Model类的实体映射对象

<!--endsec-->

<!--sec data-title="db()" data-id="db1" data-show=true ces-->

```java
Db db()
```

------

返回当前Model类的Db对象,这个对象表示的是数据库对象,包含了数据库的信息

**结果:**

* 返回当前Model类的Db对象

<!--endsec-->

<!--sec data-title="count()" data-id="count1" data-show=true ces-->

```java
long count()
```

返回当前Model类对应数据库表的记录数

**结果:**

* 返回当前Model类对应的数据库表记录行数

<!--endsec-->

## <a id="operate"></a>普通操作相关方法

<!--sec data-title="getEntityName()" data-id="getEntityName1" data-show=true ces-->

```java
String getEntityName()
```

------

获取Model类的类名

**结果:**

* 返回Model类名

<!--endsec-->

<!--sec data-title="contains(String field)" data-id="contains1" data-show=true ces-->

```java
boolean contains(String field)
```

------

检验Model类是否包含指定属性

**参数:**

* field:需要校验的属性名

**结果:**

* 如果Model类包含属性`field`则返回true,否则返回false

<!--endsec-->

<!--sec data-title="get(String field)" data-id="get1" data-show=true ces-->

```java
Object get(String field)
```

------

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

<!--endsec-->

<!--sec data-title="get(Named field)" data-id="get2" data-show=true ces-->

```java
Object get(Named field)
```

------

效果与`Object get(String field)`一致,参数类型不同而已.

**参数:**

* field:Named的实例对象,Named是一个接口,这个实例对象的`field.getName()`返回属性名.

**结果:**

* 属性`field`的值

<!--endsec-->

<!--sec data-title="getString(String field)" data-id="getString1" data-show=true ces-->

```java
String getString(String field)
```

------

获取属性`field`的值并转换成String类型返回

**参数:**

* field:属性名.

**结果:**

* 属性`field`的值

<!--endsec-->

<!--sec data-title="getInteger(String field)" data-id="getInteger1" data-show=true ces-->

```java
Integer getInteger(String field)
```

------

获取属性`field`的值并转换成Integer类型返回

**参数:**

* field:属性名.

**结果:**

* 属性`field`的值

<!--endsec-->

<!--sec data-title="fields()" data-id="fields1" data-show=true ces-->

```java
Map<String, Object> fields()
```

------

以Map的方式返回Model对象实例的所有属性值(包括没有显式声明但是有设置的值),这里返回的是属性副本,对这个副本的操作不会影响model实例对象.

**结果:**

* Model实例对象的所有属性值.

<!--endsec-->

<!--sec data-title="id()" data-id="id1" data-show=true ces-->

```java
Object id()
```

------

返回Model实例对象的id

**结果:**

* Model实例对象的id

<!--endsec-->

<!--sec data-title="id(Object id)" data-id="id2" data-show=true ces-->

```java
<T extends Model> T id(Object id)
```

------

设置Model实例对象的id

**结果:**

* Model实例对象本身

<!--endsec-->

<!--sec data-title="set(String field,Object value)" data-id="set1" data-show=true ces-->

```java
<T extends Model> T set(String field,Object value)
```

------

设置Model实例对象的属性,这个属性不一定是要Model类显式声明的属性.

**参数:**

* field:要设置的属性名
* value:要设置的属性值

**结果:**

* 返回Model实例对象本身

<!--endsec-->

<!--sec data-title="createdAt()" data-id="createdAt1" data-show=true ces-->

```java
Timestamp createdAt()
```

------

返回Model实例对象的创建时间,如果实例对象没有`createdAt`属性则抛出异常

**结果:**

返回Model实例对象的`createdAt`属性值

<!--endsec-->

<!--sec data-title="updatedAt()" data-id="updatedAt1" data-show=true ces-->

```java
Timestamp updatedAt()
```

------

返回Model实例对象的创建时间,如果实例对象没有`updatedAt`属性则抛出异常

**结果:**

* 返回Model实例对象的`updatedAt`属性值

<!--endsec-->

<!--sec data-title="set(Named field,Object value)" data-id="set2" data-show=true ces-->

```java
<T extends Model> T set(Named field,Object value)
```

------

与`<T extends Model> T set(String field,Object value)`类似

<!--endsec-->

<!--sec data-title="setAll(Map &lt; String,? extends Object &gt; fields)" data-id="setAll1" data-show=true ces-->

```java
<T extends Model> T setAll(Map<String,? extends Object> fields)
```

------

同时设置多个属性值到Model的实例对象中,效果与多次调用`<T extends Model> T set(String field,Object value)`相同

<!--endsec-->

<!--sec data-title="refresh()" data-id="refresh1" data-show=true ces-->

```java
boolean refresh()
```

------

刷新Model实例对象,重新从数据库读取各属性的值.

**结果:**

* 刷新成功则返回true.

<!--endsec-->

<!--sec data-title="validate()" data-id="validate1" data-show=true ces-->

```java
boolean validate()
```

------

根据Model类的字段限制(@Column注解或者数据库字段的其他限制)校验Model实例对象.

**结果:**

* 校验不通过则返回false,通过则返回true

<!--endsec-->

<!--sec data-title="errors()" data-id="errors1" data-show=true ces-->

```java
Errors errors()
```

------

返回Model的校验出错的结果,当调用`validate()`等校验方法时,如果校验失败,就会将校验失败的信息放到这个error对象中.

**结果:**

* Model实例对象校验失败的错误信息

<!--endsec-->

<!--sec data-title="validate(int maxErrors)" data-id="validate2" data-show=true ces-->

```java
boolean validate(int maxErrors)
```

------

根据Model类的字段限制(@Column注解或者数据库字段的其他限制)校验Model实例对象.

**参数:**

maxErrors:最大校验失败数

**结果:**

* 校验不通过的属性值大于`maxErrors`时返回false,反之这返回true

<!--endsec-->

<!--sec data-title="getProperty(String name)" data-id="getProperty1" data-show=true ces-->

```java
Object getProperty(String name)
```

------

与`Object get(String field)`效果相同

<!--endsec-->

<!--sec data-title="setProperty(String name, Object value)" data-id="setProperty1" data-show=true ces-->

```java
void setProperty(String name, Object value)
```

------

与`<T extends Model> T set(String field,Object value)`效果相同,但是没有返回值

<!--endsec-->

<!--sec data-title="getProperties()" data-id="getProperties1" data-show=true ces-->

```java
Map<String, Object> getProperties()
```

------

与`Map<String, Object> fields()`效果相同

<!--endsec-->