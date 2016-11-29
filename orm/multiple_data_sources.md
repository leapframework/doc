# 多数据源应用

leap默认是连接一个数据库,并在一个数据库内操作,但是有些时候,我们的应用会同时连接多个数据库,并在不同的数据库中操作.

### 单个Model的指定数据源

在本章第一节的时候,我们已经知道如何在`beans.xml`中配置bean数据源,现在我们可以多配置一个数据源:
```xml
<bean name="other" type="javax.sql.DataSource" class="leap.db.cp.PooledDataSource" primary="true">
	<property name="driverClassName" value="${jdbcDriver}"></property>
	<property name="jdbcUrl" value="${jdbcUrl}"></property>
	<property name="username" value="${jdbcUsername}"></property>
	<property name="password" value="${jdbcPassword}"></property>
</bean>
```
这样我们就配置了两个数据源了,一个是`default`,另一个是`other`.  
默认情况下,我们使用的数据源都是`default`,如果某个特定的Model是另外一个数据源的Model,此时我们可以在这个Model上添加注解:
```java
@Table("leap_user")
@DataSource("other")
public class User extends Model {
}
```
则这个User的数据源就是`other`了.

### 多个Model的指定数据源
当然,对于需要多个数据源的应用,一般情况下不会只有一个Model是另一个数据源的,所以我们需要配置多个Model的数据源,单独每一个添加注解虽然可行,但却非常麻烦,这里我们可以通过配置指定类或者包下的所有Model都属于某个特定的数据源,如下:
在config.xml中添加如下配置:

```xml
<orm:models datasource="other">
	<orm:package name="leap.project.other.model" />
	<orm:class name="leap.project.model.User" />
</orm:models>
```

此时指定的包`leap.project.other.model`下所有的Model都将会使用`other`数据源.  
指定的类`leap.project.model.User`也会使用`other`数据源.