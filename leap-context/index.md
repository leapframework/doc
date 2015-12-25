# 控制反转和依赖注入

## 控制反转

leap的控制反转有两种方式,第一种是在xml中配置bean类,比如在`beans.xml`中可以配置如下bean:

```xml
<bean name="h2" type="javax.sql.DataSource" class="leap.core.ds.UnPooledDataSource" primary="true">
    <property name="driverClassName" value="${h2.driverClassName}" />
    <property name="jdbcUrl"             value="${h2.jdbcUrl}" />
    <property name="username"        value="${h2.username}" />
    <property name="password"        value="${h2.password}" />
</bean>
```

此时leap会在bean对象工厂中创建并管理这个对象.

另一种方式是在bean类的类名上注解`@leap.core.annotation.Bean`,也可以让leap的对象工厂创建并管理这个对象,如:

```java
import leap.core.annotation.Bean;

@Bean
public class BeanService extends BaseService {
	
}

```

通过`@Bean`注解的bean对象默认名称是将类名首字母小写,当然我们也可以通过注解的`name`属性指定.

无论是xml配置还是注解的bean,我们需要关注的主要有如下几个属性:

```
id:   bean在对象工厂中的id,也是bean的唯一标识
type: bean在对象工厂中的识别类型,默认是实现类本身,也可以通过配置指定为其父类或者父接口
name: bean在对象工程中的名称,可以不唯一,但是name和type的组合必须是唯一的
```

## 依赖注入

有了上面的控制反转,现在我们可以将对象工厂中的bean注入到其他地方使用了.

依赖注入的方式一般是通过`@leap.core.annotation.Inject`注解在需要使用bean的地方实现的,如:

```java
import leap.core.annotation.Inject

public class HomeController{
	
	@Inject
	private BeanService service;

}

```

当然,我们也可以通过配置bean的方式实现依赖注入:

```xml
<bean id="htpl" name="htpl" type="leap.performance.test.engine.TemplateEngine" 
	class="leap.performance.test.engine.instance.Htpl">
	<constructor-arg type="java.lang.String" value="/"></constructor-arg>
	<property name="template" value="htpl/test.html"></property>
</bean>

<bean name="testEngine" type="leap.performance.test.engine.PerformanceTestEngine" 
	class="leap.performance.test.engine.instance.DefaultPerformanceTestEngine">
	<property name="engine">
		<ref bean="htpl"/>
	</property>
	<property name="times" value="100"></property>
</bean>
```

上面的配置会创建一个id为htpl的bean对象,并将这个对象注入到name为testEngine的实例对象的属性engine中去.  
类似的配置方式还有

```xml
<property name="engine" ref="htpl" ></property>
```
或者

```xml
<property name="engine">
	<idref bean="htpl" />	
</property>
```
这里的配置方式都是等效的.

> **注意:**  
> 使用xml配置的方式注入对象时,目前只能以bean ID的方式注入,而通过注解的方式注入时,leap会自动根据类型和名称找到bean对象并注入.

