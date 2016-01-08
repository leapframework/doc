# 指令对象

指令对象与Model接口中的指令对象使用方法完全相同,可以参考Model接口对指令对象的使用说明.

<!--sec data-title="cmdInsert(Class &lt; ? &gt; entityClass)" data-id="cmdInsert1" data-show=true ces-->

```java
InsertCommand cmdInsert(Class<?> entityClass)
```

------

通过指定数据库映射类生成插入指令

**参数:**

* entityClass : 数据库映射类

**结果:**

* 返回指定类型的插入指令对象

**异常:**

* MappingNotFoundException:如果没有找到实体映射对象则抛出

<!--endsec-->

<!--sec data-title="cmdInsert(String entityName)" data-id="cmdInsert2" data-show=true ces-->

```java
InsertCommand cmdInsert(String entityName)
```

------

效果同`InsertCommand cmdInsert(Class<?> entityClass)`,仅指定数据库映射类使用简单类名指定.

<!--endsec-->

<!--sec data-title="cmdInsert(EntityMapping em)" data-id="cmdInsert3" data-show=true ces-->

```java
InsertCommand cmdInsert(EntityMapping em)
```

------

效果同`InsertCommand cmdInsert(Class<?> entityClass)`,仅指定数据库映射类使用实体映射对象

<!--endsec-->

<!--sec data-title="cmdUpdate(Class &lt; ? &gt; entityClass)" data-id="cmdUpdate1" data-show=true ces-->

```java
UpdateCommand cmdUpdate(Class<?> entityClass)
```

------

通过指定数据库映射类生成更新指令

**参数:**

* entityClass : 数据库映射类

**结果:**

* 返回指定类型的更新指令对象

**异常:**

* MappingNotFoundException:如果没有找到实体映射对象则抛出

<!--endsec-->

<!--sec data-title="cmdUpdate(String entityName)" data-id="cmdUpdate2" data-show=true ces-->

```java
UpdateCommand cmdUpdate(String entityName)
```

------

效果同`UpdateCommand cmdUpdate(Class<?> entityClass)`,仅指定数据库映射类使用简单类名指定.

<!--endsec-->

<!--sec data-title="cmdUpdate(EntityMapping em)" data-id="cmdUpdate3" data-show=true ces-->

```java
UpdateCommand cmdUpdate(EntityMapping em)
```

------

效果同`UpdateCommand cmdUpdate(Class<?> entityClass)`,仅指定数据库映射类使用实体映射对象

<!--endsec-->
