# 探测方法

<!--sec data-title="exists(Class &lt; ? &gt; entityClass,Object id)" data-id="exists1" data-show=true ces-->

```java
boolean exists(Class<?> entityClass,Object id)
```

------

探测实体映射类实例是否存在指定的id的记录

**参数:**

* entityClass : 实体映射类
* id : 需要探测的id

**结果:**

* 存在则返回true,不存在则返回false

**异常:**

* MappingNotFoundException:找不到实体映射类的实体映射对象则抛出

<!--endsec-->

<!--sec data-title="count(Class &lt; ? &gt; entityClass)" data-id="count1" data-show=true ces-->

```java
long count(Class<?> entityClass)
```

------

探测实体映射类的数据库记录行数

**参数:**

* entityClass : 实体映射类

**结果:**

* 返回该实体映射类对应数据库表的行数

<!--endsec-->