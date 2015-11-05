# 2.2 示例工程说明
上一节我们已经搭建好了示例工程的环境,这一节我们将对工程目录做一些简单的介绍以便大家后续进行开发的练习.
这里我们先看工程的文件结构:  
在src目录下:
> leap-user-guid-web  
> ├ src  
> │ ├ leap.demo.controller  
> │ ├ leap.demo.db  
> │ ├ leap.demo.form  
> │ └ leap.demo.model  

src目录下是java的包.

> ├ resources  
> │ ├ conf  
> │ │ ├ beans.xml  
> │ │ └ config.xml  
> │ ├ META-INF  
> │ │ └ services  
> │ │　　└ leap  
> │ │　　　└ core  
> │ │　　　　└ AppPropertyProcessor  
> │ │　　　　　　└ H2DbUrlParser  
> │ └ logback.xml  

resources目录下放置的是配置文件,conf目录下放的是leap的入口配置的文件夹,`config.xml`就是我们的入口配置,也是leap第一个加载的配置.  
META-INF文件夹下,这里有一个特殊的目录结构`services/leap/core/AppPropertyProcessor`这个目录是用于配置指定的配置加载预处理器的目录.

> └ WebContent  
> 　├ META-INF  
> 　│ └ MANIFEST.MF  
> 　├ static  
> 　│ ├ js  
> 　│ │ └ index.js  
> 　│ └ index.css  
> 　└ WEB-INF  
> 　　├ lib  
> 　　│　└ *.jar  
> 　　├ views  
> 　　│　├ user  
> 　　│　│  ├ create_user.html  
> 　　│　│  ├ edit_user.html  
> 　　│　│  └ index.html  
> 　　│　└ index.html  
> 　　└ web.xml

最后是WebContent目录  
1. WebContent是标准的web应用的结构.  
2. 在WEB-INF目录下有一个叫做`views`的文件夹,这个文件夹用来存放整个web应用的视图文件,这个结构是leap web应用的标准结构.  
3. 在WebContent目录下有一个static文件夹,建议所有静态资源文件(js,css)都放在这个目录下.
  

### 2.1.1 基础配置  ###

在resources目录下的config.xml便是leap框架的入口配置,config.xml的配置如下图所示:  
  ```xml
  <config xmlns="http://www.leapframework.org/schema/config">
  	<base-package>leap.demo</base-package>
  	<properties>
  		<property name="h2.driverClassName">org.h2.Driver</property>
          <property name="h2.jdbcUrl">jdbc:h2:classpath:/resources/h2db</property>
          <property name="h2.username">test</property>
          <property name="h2.password">test</property>
  	</properties>
  </config>
  ```
  在这里有一个`<base-package>leap.demo</base-package>`的配置,这个配置是leap框架扫描包的根目录,即所有需要leap框架自动处理的类都必须在这个包或者其子包内.这里其他的几项`properties`配置是数据库相关的属性配置,不难理解.  
> * `config.xml`是必须存在的,并且必须是`conf`文件夹内,不可在其他位置(也不能在conf的子目录内)    
> * 可以在`conf`文件夹下创建子文件`config`.  

  我们继续看`beans.xml`的配置文件:  
  ```xml
  <beans xmlns="http://www.leapframework.org/schema/beans" default-lazy-init="true">
      <bean name="h2" type="javax.sql.DataSource" class="leap.core.ds.UnPooledDataSource" primary="true">
          <property name="driverClassName" value="${h2.driverClassName}" />
          <property name="jdbcUrl"         value="${h2.jdbcUrl}" />
          <property name="username"        value="${h2.username}" />
          <property name="password"        value="${h2.password}" />
      </bean>
  </beans>
  ```
这里配置了一个name为h2的bean,这个bean的类型是`javax.sql.DataSource`,真正的实现类是`leap.core.ds.UnPooledDataSource`,`primary="true"`表示这个bean是单例的.  
注意到这里的属性值采用了如`${h2.driverClassName}`的配置,这个配置的值我们可以在config.xml中看到,相信大家不难理解这里的含义.  

> * beans.xml并不是必须的  
> * beans.xml也和config.xml类似,允许在`conf`目录下创建子目录`beans`.

### 2.1.2 mvc映射规则  ###
  
这里我们以`leap.demo.controller.UserController`为例说明leap的源代码.
先看 [快速环境搭建](construction.md) 中看过的路由表:  
```
METHO  PATH                ACTION                             VIEW
-----  -----------------   --------------------------------   ------------------------------
*      /user/delete_user   action:UserController.deleteUser
GET    /user/create_user   action:UserController.createUser   htpl:/user/create_user
POST   /user/create_user   action:UserController.createUser   htpl:/user/create_user
GET    /user/edit_user     action:UserController.editUser     htpl:/user/edit_user
POST   /user/edit_user     action:UserController.editUser     htpl:/user/edit_user
*      /user/index         action:UserController.index        htpl:/user/index
*      /index              action:HomeController.index        htpl:/index
*      /user               action:UserController.index        htpl:/user/index
*      /                   action:HomeController.index        htpl:/index
```
我们从路由表的第一行可以看到,访问路径`/user/delete_user`就会调用`UserController.deleteUser`这个action(注:**在leap中,负责处理请求的方法我们称为action**.),这个action如下:
```java
public class UserController extends ControllerBase {
	...
	public void deleteUser(String loginId){
		User.delete(loginId);
		response().sendRedirect("/user/index");
	}
	
}
```
你一定很奇怪在没有任何配置和注解的情况下,leap是如何找到这个action来处理对应的请求的.  
这实际上是leap的mvc默认约定的映射规则:
> * 在`base-package`包下的所有以*Controller命名的类全部为控制器类.
> * 所有控制器的类名去掉Controller后缀经leap按[转换规则](#mapping-rule)后即作为该控制器的访问路径.如:`UserController`转换后变成user,因此访问路径是`/user/`.
> * 控制器下所有public方法都会被转换为action.
> * action的访问路径为控制器路径加上action按[转换规则](#mapping-rule)后的名字,如:`UserController.deleteUser`的访问路径是:`/user/delete_user`.
> * 如果访问controller的根路径,默认调用controller名为index的action.

接下来我们看到路由表中第二行的路由信息,以`GET`请求访问路径`/user/create_user`会调用`UserController.createUser`并返回视图`/user/create_user`
```java
	@GET
	public void createUser(ViewData vd){
		
	}
```
可以看到action中并没有任何处理,我们已经知道路径和action的映射关系,那么action和view的映射关系又是如何确定的?
由action到view的默认规则是:
> * 根据action的访问路径,在`WEB-INF/views`目录下寻找对应路径的视图文件.如:访问`/user/create_user`会映射到/WEB-INF/views/user/create_user.html
> * 在对应目录下优先寻找html类型的文件,其次寻找jsp类型的文件.
  
> #### <a id="mapping-rule"></a>mvc转换规则
> * 对于全为小写字母的单词,转换后为原单词,如:user → user;userdelete → userdelete.                         
> * 对于多个单词组成的驼峰式名称,将驼峰式转换为下划线式名称,如:createUser → create_user.
> * 若连续两个以上的字母大写,则将全部转为小写,并不添加下划线,如:userUUID → useruuid.
> * 若有特殊字符,则特殊字符前后均变为小写,并不添加下划线,如:create$User → create$user.


### 2.1.3 视图文件 ###  

leap框架包含了自己的模板引擎,我们称为htpl模板引擎,htpl是一个高效的,基于html注释的无冗余元素和属性的模板引擎.  
前面我们已经清楚leap的mvc默认映射规则了,让我们先看看/user/index的视图是怎么样的.
打开`WebContent/WEB-INF/views/user/index.html`文件,可以看到如下代码:  
  ```html
  <table border="1">
    <thead>
      <tr>
        <th>序号</th>
        <th>用户名</th>
        <th>用户账号</th>
        <th>用户密码</th>
        <th>操作</th>
      </tr>
    </thead>
    <tbody>
      <!-- #for(user : users) -->
      <tr>
        <td>${loop.index}</td>
        <td>${user.name}</td>
        <td>${user.loginId}</td>
        <td>${user.password}</td>
        <td>
          <a href="edit_user?loginId=${user.loginId}">修改</a>
          <a href="delete_user?loginId=${user.loginId}">删除</a>
        </td>
      </tr>
      <!-- #endfor -->
    </tbody>
  </table>
  ```
  可以看到,这里对用户的列表使用了`#for`指令循环打印了用户信息,使用`#endfor`指令表示循环结束.htpl的指令语法和java极为相似,不难理解,这里细心的用户可能已经注意到`${loop.index}`这个代码了,这里`loop`对象是htpl循环中提供的特殊对象,保存了当前循环的信息,比如这里用的`loop.index`就是表示当前循环的下标.

### 2.1.4 leap-orm ###  

接下来我们看一下,controller接收到请求之后,如何与数据库交互.  
打开`UserController`类,看到`public void editUser(ViewData vd, UserForm userForm)`函数,代码如下:
  ```java
  @POST
	public void editUser(ViewData vd, UserForm userForm){
		if(vd.validation().hasErrors() || vd.validation().validate(userForm).hasErrors()){
			return;
		}
		User user = User.find(userForm.loginId);
		user.setName(userForm.name);
		user.setPassword(userForm.password);
		try {
			user.update();
			response().sendRedirect("/user/index");
		} catch (Exception e) {
			vd.validation().addError("保存失败", e.getMessage());
		}
	}
  ```
  这里我们主要看的是`User user = User.find(userForm.longinId)`这一行,还有`user.update()`这一句代码,这里我们发现,作为模型类的`User`,可以直接跟数据库交互,这个也是leap提供的ActiveModel模式的orm模块的主要特点.  
> * UserForm是一个参数接收类,将参数绑定到这个类上可以使用leap的后端校验功能

  打开`User`类:
  ```java
  @Table("leap_user")
  public class User extends Model {
  	@Column(name="user_name")
  	private String name;
  	private String loginId;
  	@Column(name="password")
  	private String password;
  	public String getName() {
  		return name;
  	}
  	public void setName(String name) {
  		this.name = name;
  	}
  	public String getLoginId() {
  		return loginId;
  	}
  	public void setLoginId(String loginId) {
  		this.loginId = loginId;
  	}
  	public String getPassword() {
  		return password;
  	}
  	public void setPassword(String password) {
  		this.password = password;
  	}
  }
  ```
  这个类上的注解很好理解,`@Table`表示这个模型类映射的数据库表,`@Column`表示指定的字段映射到数据库的字段.   
  `Model`类是leap-orm提供的基类,所有与数据库表有映射关系的模型类都要继承这个类以获得与数据库交互的能力.当然leap-orm也支持使用习惯的dao方式与数据库交互.  
> * @Column和@Table注解并不是必须的,默认继承`Model`类的类都会识别为Model并按照[转换规则](#mapping-rule)转换为数据库字段名和表名.  

### 2.1.5 leap工程的入口 ###  

前面我们已经明白leap的工程是如何进行工作的了,现在我们来看看当web容器接收到请求之后,leap是如何接管请求处理的,打开`web.xml`可以看到如下配置:
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
这个配置就是leap的入口拦截器,所有的请求都将从这里被leap的拦截器拦截之后交由leap进行处理,当然,对于某些leap在控制路由表中找不到对应的action的请求,leap就会重新将请求交回给web容器,进入下一个拦截器或者servlet处理.

至此,我们对示例工程和使用leap开发的基本工程结构都有了一定的了解了.可以继续下一章节,开始创建我们自己的action了. 
  
  [自定义第一个action](first_action.md)