# WEB应用开发

经过[快速开始](../getting-start/index.md)的章节,我们对leap框架已经能有一个直观的感受了,本章将详细解释如何从零开始搭建leap的开发环境.
 
* [新建工程](#new_project)
    - [创建工程](#created_porject)
    - [补充web应用工程结构](#web_constructure)
    - [补充leap工程结构](#leap_constructure)
* [初始化配置](#init_config)
    - [添加leap依赖](#add_dependency)
    - [添加`base-package`目录配置](#add_base_package)
    - [修改web.xml添加框架入口](#config_webxml)
* [创建Global](#new_global)
* [创建Controller](#new_controller)
* [创建视图](#new_view)
* [日志配置](#add_log)

## <a id="new_project"></a>新建工程

leap推荐使用标准的maven工程环境,可以使用任何支持maven的IDE创建开发工程.
#### <a id="created_porject"></a>创建工程 
首先创建一个空的maven工程,标准的maven源代码工程结构如下:
```
leap-project
├　src
│　├　main
│　│　├　java
│　│　├　resources
│　│　└　webapp
│　└　test
│　 　├　java
│　 　└　resources
└　pom.xml
```
#### <a id="web_constructure"></a>补充web应用工程结构
默认的maven是一个普通的工程,不包含web应用的标准文件结构,这里我们补充一下需要的目录和文件:
在`webapp`目录下创建`WEB-INF`文件夹,并在`WEB-INF`目录下创建`web.xml`文件:
```
└　webapp
    └　WEB-INF
    　　└　web.xml
```
`web.xml`是j2ee的web应用中指定的web配置文件(在web3.0标准以后不再是必须的).标准的`web.xml`配置可以去[oracle的官网](https://docs.oracle.com/middleware/1212/wls/WBAPP/web_xml.htm#WBAPP502)查看,这里提供一个简单的空配置:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  
	xmlns="http://java.sun.com/xml/ns/javaee"  
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
	version="3.0">
	
</web-app>
```
#### <a id="leap_constructure"></a>补充leap工程结构
在`src/main/resources`目录下创建文件夹`conf`,并创建一个普通的xml文件`config.xml`:
```
└　resources
    └　conf
    　　└　config.xml
```
`config.xml`的内容如下:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<config xmlns="http://www.leapframework.org/schema/config">

</config>
```
> **注**:  
> 这里leap有自己的XML文档结构定义(xsd)文件,在`leap-core-0.1.0-SNAPSHOT.jar`包下的`leap.core`包内,有一个`conf.xsd`文件,将这个文件导入IDE中即可实现配置文件的自动完成功能.

继续在`webapp`目录下创建文件夹`static`,在`WEB-INF`目录下创建`views`文件夹
```
└　webapp
    ├　static
    └　WEB-INF
    　　├　views
    　　└　web.xml
```
至此,完整的工程目录已经搭建完成,我们继续看下一步.


## <a id="init_config"></a>初始化配置
上一步我们已经创建好工程目录,现在我们开始做一些初始化的配置.
leap遵循约定大于配置的原则,因此所需要的初始化配置非常少.
#### <a id="add_dependency"></a>添加leap依赖
在pom.xml中添加依赖:
```xml
<dependency>
	<groupId>org.leapframework</groupId>
	<artifactId>leap-fw</artifactId>
	<version>0.1.0-SNAPSHOT</version>
	<type>pom</type>
</dependency>
```
这是使用leap的第一步.
依赖添加完成后,在maven依赖关系中可以看到对leap的jar包的引用(**不同版本jar包可能不一样**).
#### <a id="add_base_package"></a>添加`base-package`目录配置
在`resources/conf/config.xml`中添加如下配置:
```xml
<base-package>leap.project</base-package>
```
这个配置表示leap将会以`leap.project`为根目录,扫描这个包以及其子包下的所有类来初始化整个web应用.
#### <a id="config_webxml"></a>修改web.xml添加框架入口
在`web.xml`中添加如下拦截器:
```xml
<filter>
	<filter-name>app-filter</filter-name>
	<filter-class>leap.web.AppFilter</filter-class>
</filter>
<filter-mapping>
	<filter-name>app-filter</filter-name>
	<url-pattern>/*</url-pattern>
</filter-mapping>
```
这里`leap.web.AppFilter`是框架的拦截器,一般配置`url-pattern`为`/*`表示拦截所有的请求.

leap工程的初始化配置完成,我们开始写应用的初始类.

## <a id="new_global"></a>创建Global
在[初始化配置](#init_config)中,我们配置了`<base-package>leap.project</base-package>`,因此leap框架将以`leap.project`为根包扫描所有的类,因此我们先创建一个包:
```
leap.project
```
在这个包下创建一个普通的java类:
```
leap.project
      └　Global.java
```
`Global.java`继承自`leap.web.App`,源码如下:
```java
package leap.project;

import leap.web.App;

public class Global extends App {

}
```
在这个类中,我们可以重写`init()`函数,这个函数将在应用初始化完成之后调用,可以做一些定制化的初始化功能(比如用户登录校验等).  
为了可以在这里配置应用的安全校验,我们需要在这里使用leap提供的安全配置器,在`Global`中添加一个私有属性`private SecurityConfigurator sc`,代码如下:
```java
package leap.project;

import leap.web.App;
import leap.web.security.SecurityConfigurator;

public class Global extends App {
	private SecurityConfigurator sc;
	
	@Override
	protected void init() throws Throwable {
		
	}
}
```
> 这里`SecurityConfigurator`就是安全配置器接口,leap提供的默认的实现.


这里的配置在后续的章节我们再详细讲解,接下来我们创建一个控制器.

## <a id="new_controller"></a>创建Controller
我们已经创建了一个`leap.project`的包,现在我们在这个包下创建一个`controller`包,并在`controller`包下创建一个`HomeController.java`的类,结构如下:
```
leap.project
      ├　Global.java
      └　controller
          └　HomeController.java
```
`HomeController`类内,包含一个`public void index()`的函数,代码如下:
```java
package leap.project.controller;

public class HomeController {
	public void index(){
		
	}
}
```
我们可以看到`index()`函数是一个空实现.这个方法仅作为`HomeController`的入口函数,因此不需要特殊的实现,当然也可以不写这个方法.  

## <a id="new_view"></a>创建视图
在[新建工程](#new_project)中,我们在`WEB-INF`下创建了一个`view`目录,这个目录就是我们所有视图文件的根目录了.  
现在我们在`views`目录下创建一个`index.html`文件:
```
└　WEB-INF
      ├　views
      │　　└　index.html
      └　web.xml
```
`index.html`的代码如下:
```html
<!DOCTYPE html>
<html>
  <head>
  <meta charset="UTF-8">
  <title>leap project</title>
  </head>
<body>
	hello leap!
</body>
</html>
```
视图创建完成,至此我们新的工程环境已经搭建完成.

讲这个工程部署到tomcat(或其他servlet容器)中,启动并访问应用根目录,即可看到:
```
hello leap!
```
至此应用开发环境搭建完成,但是现在还不能正在进行开发,我们还需要添加日志以便开发过程的调试.

## <a id="add_log"></a>日志配置
leap已经提供了日志的接口,默认是支持slf4j日志的,因此我们只要选择自己想要使用的日志框架即可.推荐使用的是logback框架.  
在pom.xml中添加依赖:
```xml
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>${logback.version}</version>
    <type>jar</type>
</dependency>
```
添加了依赖之后,我们根据logback的官方文档,在`src/main/resources`源文件夹下添加`logback.xml`,
```
└　resources
    ├　conf
    │　　└　config.xml
    └　logback.xml
```
参考示例如下:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/2002/xmlspec/dtd/2.10/xmlspec.dtd">
<configuration scan="true" scanPeriod="10 seconds">
	<appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
		<encoder>
			<!-- do not add line number output , it will slow down the execution speed -->
			<pattern>%d{HH:mm:ss.SSS} [%thread] %level %logger{36} - %msg%n</pattern>
		</encoder>
	</appender>
  
	<logger name="bingodrive" level="TRACE"/>
	<logger name="leap"       level="DEBUG"/>
 	 
	<root level="WARN">
		<appender-ref ref="STDOUT" />
	</root>
</configuration>
```
再次启动应用,就可以看到在终端输出了日志.这里我们需要注意的是,在最后有如下的一段日志:
```
METHOD  PATH     ACTION                 DEFAULT VIEW
------  ------   --------------------   ------------------------------
*       /        HomeController.index   htpl:/index
*       /index   HomeController.index   htpl:/index
```
这个就是leap打印的路由表,可以告诉我们访问哪个url会对应哪个action和哪个view.
比如在路由表的第一行:
```
*       /        HomeController.index   htpl:/index
```
第一列`*`表示支持所有的请求类型,如`POST`,`GET`,`PUT`等等;  
第二列`/`表示应用根路径,在这里实际上指的就是`http://localhost:8080/leap-project/`这个路径;  
第三列`HomeController.index`表示的是这个路径处理的action就是`HomeController`类下的`index`方法;  
第四列`htpl:/index`表示的是视图类型和位置.
> `htpl`表示该视图是htpl模板;  
> `/index`表示是在根目录下的`index.html`文件或`index.jsp`文件;  
> 这里的根目录指的是`webapp/WEB-INF/views/`.


这个路由表也可以作为应用启动成功的标志.
