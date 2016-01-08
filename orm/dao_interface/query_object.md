# Query查询

dao接口的多行查询一般采用Query对象查询的方式,对象的使用和Model接口完全一致.

### EntityQuery对象创建

<!--sec data-title="createNamedQuery(Class &lt; T &gt; entityClass,String queryName)" data-id="createNamedQuery1" data-show=true ces-->

```java
<T> EntityQuery<T> createNamedQuery(Class<T> entityClass,String queryName)
```

------

指定sqlKey和数据库映射类,并返回该映射类的EntityQuery对象

**参数:**

* entityClass : 实体类实例
* queryName : xml中配置的sql的key属性

**结果:**

* 返回resultClass类型的EntityQuery对象.

<!--endsec-->

<!--sec data-title="createNamedQuery(String entityName,String queryName)" data-id="createNamedQuery2" data-show=true ces-->

```java
EntityQuery<EntityBase> createNamedQuery(String entityName,String queryName)
```

------

指定sqlKey,并返回EntityBase类型的EntityQuery对象

**参数:**

* entityName : 实体类的简单类名
* queryName : xml中配置的sql的key属性

**结果:**

* 返回EntityBase类型的EntityQuery对象.

<!--endsec-->

<!--sec data-title="createNamedQuery(String entityName,Class &lt; T &gt; resultClass, String queryName)" data-id="createNamedQuery3" data-show=true ces-->

```java
<T> EntityQuery<T> createNamedQuery(String entityName,Class<T> resultClass, String queryName)
```

------

指定sqlKey和数据库映射类名,并指定返回EntityQuery的泛型类型为resultClass

**参数:**

* entityName : 实体类的简单类名
* resultClass : 指定EntityQuery的泛型类型
* queryName : xml中配置的sql的key属性

**结果:**

* 返回resultClass类型的EntityQuery对象.

<!--endsec-->

<!--sec data-title="createNamedQuery(EntityMapping em, Class &lt; T &gt; resultClass, String queryName)" data-id="createNamedQuery4" data-show=true ces-->

```java
<T> EntityQuery<T> createNamedQuery(EntityMapping em, Class<T> resultClass, String queryName)
```

------

效果同`<T> EntityQuery<T> createNamedQuery(String entityName,Class<T> resultClass, String queryName)`,但是通过直接指定数据库实体映射对象指定数据库表

<!--endsec-->

<!--sec data-title="createQuery(Class &lt; T &gt; resultClass, SqlCommand command)" data-id="createQuery1" data-show=true ces-->

```java
<T> Query<T> createQuery(Class<T> resultClass, SqlCommand command)
```

------

以SqlCommand的方式创建Query对象,并指定返回的Query的泛型类型

SqlCommand对象在`sql执行的小节详细分析`

<!--endsec-->

### <a id="criteria_query"></a>CriteriaQuery对象创建

<!--sec data-title="createCriteriaQuery(Class & lt; T &gt; entityClass)" data-id="createCriteriaQuery1" data-show=true ces-->

```java
<T> CriteriaQuery<T> createCriteriaQuery(Class<T> entityClass)
```

------

指定实体映射类生成CriteriaQuery对象

**参数:**

* entityClass : 实体映射类

**结果:**

* 返回生成的CriteriaQuery对象

<!--endsec-->

<!--sec data-title="createCriteriaQuery(EntityMapping em, Class &lt; T &gt; resultClass)" data-id="createCriteriaQuery2" data-show=true ces-->

```java
<T> CriteriaQuery<T> createCriteriaQuery(EntityMapping em, Class<T> resultClass)
```

------

指定实体映射对象生成CriteriaQuery,并指定CriteriaQuery的泛型类型为resultClass

**参数:**

* em : 实体映射对象
* resultClass : 泛型的类型

**结果:**

* 返回resultClass类型的CriteriaQuery

<!--endsec-->
