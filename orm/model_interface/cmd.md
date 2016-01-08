# 指令方法

<!--sec data-title="cmdInsert()" data-id="cmdInsert1" data-show=true ces-->

```java
InsertCommand cmdInsert()
```

------

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

<!--endsec-->

<!--sec data-title="cmdUpdate(Object id)" data-id="cmdUpdate1" data-show=true ces-->

```java
UpdateCommand cmdUpdate(Object id)
```

------

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

<!--endsec-->