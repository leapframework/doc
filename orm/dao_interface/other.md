# 其他方法

<!--sec data-title="getOrmContext()" data-id="getOrmContext1" data-show=true ces-->

```java
OrmContext getOrmContext()
```

------

获取当前ORM映射的上下文

<!--endsec-->

<!--sec data-title="getJdbcExecutor()" data-id="getJdbcExecutor1" data-show=true ces-->

```java
JdbcExecutor getJdbcExecutor()
```

------

获取JDBC执行器, JDBC执行器可以用来写原始的jdbc代码,和其他不经过leapQL翻译的sql代码.

<!--endsec-->