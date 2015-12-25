# 应用初始化

一般来说,使用leap框架的web应用我们都会写一个`Global.java`继承`leap.web.App`类并重写`init()`方法,如下:

```java
import leap.web.App;

public class Global extends App {
	@Override
	protected void init() throws Throwable {
		super.init();
	}
}
```

这个`init()`方法会在应用配置和bean初始化完成之后执行,我们可以在这里执行包含特定业务逻辑的代码,在前面的示例中,我们已经在这里配置了登录校验功能,实际上,这里还有许多可以做的事情,比如初始化拦截器,初始化数据,修改默认配置等等.

`leap.web.App`内置了许多常用的对象:

* AppContext context:应用上下文
* AppHome home:应用根目录对象
* AppConfig config:应用配置对象
* BeanFactory factory: 对象工厂
* ServletContext servletContext:servlet上下文

## 内置对象的使用

### AppContext

Appcontext是整个应用的上下文,这个对象可以获取到当前整个应用的所有信息

### AppHome

AppHome是表示应用的根目录的对象,也就是当前web应用的根目录,这个接口可以让我们获取当前应用根目录下所有的文件和真实路径,包含如下几个方法:

```java
boolean exists():判断应用根目录是否存在
File dir():获取应用根目录的File对象
AppHome forceCreate():创建应用根目录并且返回自身
FileResource getResource(String relativePath):获取应用根目录的相对目录relativePath下的文件资源
```

### AppConfig

AppConfig是整个应用的配置信息,可以获取到所有的配置,包括在xml中配置的属性,这里我们简单介绍一下几个获取属性的方法:

```java
String getProperty(String name);//获取xml中配置的属性值,如果没有该属性
String getProperty(String name,String defaultValue);//获取xml中配置的属性值,如果没有该属性,则返回默认值defaultValue
<T> T getProperty(String name,Class<T> type);//获取xml中配置的属性值,并转换为type类型
<T> T getProperty(String name,Class<T> type,T defaultValue);//获取xml中配置的属性值,并转换为type类型,如果不存在则返回defaultValue
boolean hasProperty(String name);//判断是否有配置属性name
Map<String,String> getProperties();//获取所有配置的属性
```

### BeanFactory

BeanFactory是整个应用的对象工厂,所有的bean都可以通过对象工厂获取.常用的方法如下:

```java
<T> T getBean(Class<? super T> type,String name);//获取类型为type且name为name的bean
<T> List<T> getBeans(Class<? super T> type);//获取所有类型为type的bean,并返回列表
<T> T getBean(String id);//获取id为id的bean
<T> T getBean(Class<? super T> type);//获取类型为type的bean,如果存在多个的话,会抛出异常
```

### ServletContext

ServletContext是J2EE的标准对象,代表整个servlet的上下文.具体用法可以查看Oracle官方文档.

## App内置方法

### 路由表

```java
Routes routes();
```

App内置了路由表的获取方法,直接调用如上方法即可获取到这个web应用的路由表并对其进行处理

### 过滤器

```
filters();
```

获取应用的过滤器列表,这个过滤器类似servlet的filter,可以在整个应用的拦截器发挥作用前进行拦截处理.我们可以在这里添加AppFilter之前的拦截处理操作.

这里的filter是FilterMapping对象,我们可以在这里添加自己的FilterMapping实现进行拦截处理.

FilterMapping有两个方法:

```java
boolean matches(RequestBase request);//判断是否拦截此请求,返回true则拦截,false则不拦截
Filter getFilter();//当需要拦截当前请求时,会调用此方法获取拦截器并执行拦截器的doFilter方法
```

### 拦截器

```java
inteceptor();
```

这里是获取应用拦截器的列表,这个拦截器实在AppFilter中执行的,相对Filter的级别更小的拦截.

拦截器我们后续会有章节更加详细地介绍,这里不做深入的讨论.