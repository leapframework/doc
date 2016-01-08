# 校验方法

<!--sec data-title="validate(Object entity)" data-id="validate1" data-show=true ces-->

```java
Errors validate(Object entity)
```

------

校验映射实体类实例并返回校验结果对象

**参数:**

* entity:需要校验的实体类对象实例

**结果:**

* 返回校验结果的Error对象

<!--endsec-->

<!--sec data-title="validate(Object entity, int maxErrors)" data-id="validate2" data-show=true ces-->

```java
Errors validate(Object entity, int maxErrors)
```

------

校验映射实体类实例并返回校验结果对象,同时指定在校验的最大错误数,当校验错误数达到最大错误数时停止校验并返回校验结果,0表示校验全部错误

**参数:**

* entity:需要校验的实体类对象实例
* maxErrors: 最大错误数

**结果:**

* 返回校验结果的Error对象

<!--endsec-->

<!--sec data-title="validate(EntityMapping em,Object entity)" data-id="validate3" data-show=true ces-->

```java
Errors validate(EntityMapping em,Object entity)
```

------

以指定实体映射对象的配置校验映射类实例

**参数:**

* em:实体映射对象
* entity: 需要校验的对象

**结果:**

* 返回校验结果的Error对象

<!--endsec-->

<!--sec data-title="validate(EntityMapping em,Object entity, int maxErrors)" data-id="validate4" data-show=true ces-->

```java
Errors validate(EntityMapping em,Object entity, int maxErrors)
```

------

以指定实体映射对象的配置校验映射类实例,并指定最大校验失败数量,当校验错误数达到最大错误数时停止校验并返回校验结果,0表示校验全部错误

**参数:**

* em:实体映射对象
* entity: 需要校验的对象
* maxErrors : 最大错误数

**结果:**

* 返回校验结果的Error对象

<!--endsec-->
