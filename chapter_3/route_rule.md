# 路由规则
leap的设计原则是遵循约定大于配置的原则的,因此leap中有许多默认的约定,其中我们最经常接触的默认约定应该就是路由规则了.
#### 寻找controller的规则
在`config.xml`中,我们配置了`base-package`,在`base-package`包及其子包下,leap按照如下规则确定控制器:
> 1. 类名以`Controller`结束的类都视为控制器
> 2. 类上有`leap.web.annotation.Controller`注解的类都视为控制器
> 3. 类名上有`leap.web.annotation.NonController`注解的类都视为不是控制器

#### 寻找action的规则
leap按照如下规则确定action:
> 1. controller下所有的public方法默认都为action;
> 2. controller下所有的注解了`@NonAction`的函数都默认不是action.

#### 路由规则
确定controller和action之后,我们来看看url如何映射controller和action.
##### controller路由规则
默认情况下,controller的url映射规则如下:

> 1. 去掉controller类名的Controller后缀得到控制器名称,如UserController → User;
> 2. 控制器名称按照[转换规则](#mapping-rule)转换成url.
> 3. 应用根目录的url默认映射的controller为`HomeController`

按照上面的默认规则,举例如下:
```
UserController → /user
UserPostController → /user_post
User$PostController → /userpost
```
##### action路由规则
默认情况下,action的url映射规则如下:
> 1. 将action名称按照[转换规则](#mapping-rule)转换成url;
> 2. 在action的url前拼上action所在的controller的url即是action的url.
> 3. 默认直接访问controller的url相当于访问controller/index

按照上面的默认规则,举例如下:
```
UserController.list → /user/list
UserController.listAll → /user/list_all
UserController.list$Name → /user/listname
```

> #### <a id="mapping-rule"></a>mvc转换规则
> * 对于全为小写字母的单词,转换后为原单词,如:user → user;userdelete → userdelete.                         
> * 对于多个单词组成的驼峰式名称,将驼峰式转换为下划线式名称,如:createUser → create_user.
> * 若连续两个以上的字母大写,则将全部转为小写,并不添加下划线,如:userUUID → useruuid.
> * 若有特殊字符,则特殊字符前后均变为小写,并不添加下划线,如:create$User → create$user.

##### 覆盖默认规则
除了默认的规则,leap提供了许多自定义路由规则的方式,主要是采用annotation的方式实现的.
###### controller自定义路由
在controller可以添加`@leap.web.annotation.Path`注解来更改默认的url映射,注解的参数是一个字符串类型的数组,可以表示这个controller同时映射多个url.
举例如下:
我们在新建的空工程的包`leap.project.controller`下创建类`UserController`.  
`UserController.java`的代码如下:
```java
@Path("/user_controller")
public class UserController {
    public void index(){

    }
    public void list(){

    }
}
```
最终生成的路由表如下:
```
METHOD  PATH                     ACTION                 DEFAULT VIEW
------  ----------------------   --------------------   ------------------------------
*       /user_controller         UserController.index   (none)
*       /user_controller/list    UserController.list    (none)
*       /user_controller/index   UserController.index   (none)
```
这里实际上`/user_controller`和`/user_controller/index`最终都是有`UserController.index`这个action处理的.
###### action自定义路由
在action上同样可以添加`@leap.web.annotation.Path`注解来更改默认url映射,规则和controller几乎是一致的,我们还是在`UserController.java`上做改动:
```java
@Path("/user_controller")
public class UserController {
    @Path("user_index")
    public void index(){

    }
    @Path("/user_list")
    public void list(){

    }
}
```
可以看到路由表如下:
```
METHOD  PATH                          ACTION                 DEFAULT VIEW
------  ---------------------------   --------------------   ------------------------------
*       /user_list                    UserController.list    (none)
*       /user_controller/user_index   UserController.index   (none)
```
从上面的路由表中,我们可以看出如下规则:
> 1. action上的Path注解,以`/`开头时,action的访问url是以应用根路径为根路径的,比如`UserController.list`的访问路径是`/user_list`;
> 2. action上的Path注解,不以`/`开头时,则action的访问url是以controller的url为根路径的,比如`UserController.index`的访问路径为`/user_controller/user_index`;
> 3. 默认的`index()`action被注解自定义之后,controller的根目录也不再指向`index()`action了.

#### 寻找view的规则
现在我们已经清楚leap路由表的映射规则了,接下来让我们看看leap是如何寻找对应的视图的.  
首先,在`WEB-INF/views/`下创建`user_list.html`和文件夹`user_controller`,并在`user_controller`下创建`user_index.html`:
```
WEB-INF
  ├　views
  │　　├　user_controller
  │　　│　　　└　user_index.html
  │　　├　user_list.html
  │　　└　index.html
  └　web.xml　　
```
其中,`user_list.html`的内容如下:
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>user list</title>
</head>
<body>
    user list!
</body>
</html>
```
`user_index.html`的内容如下:
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>user index</title>
</head>
<body>
    user index!
</body>
</html>
```
重启应用,我们可以看到路由表如下:
```
METHOD  PATH                          ACTION                 DEFAULT VIEW
------  ---------------------------   --------------------   ------------------------------
*       /user_list                    UserController.list    htpl:/user_list
*       /user_controller/user_index   UserController.index   htpl:/user_controller/user_index
```
你可能已经注意到:  
> * `UserController.list`对应的url为`/user_list`,视图为`htpl:/user_list`;  
> * `UserController.index`对应的url为:`/user_controller/user_index`,视图为`htpl:/user_controller/user_index`.  

这里大概能猜测到leap的默认视图规则:
> 应用根目录对应的视图根目录是`WEB-INF/views`;  
> 浏览器访问url对应根目录的路径即是视图对应`WEB-INF/views`的目录.

路由表中的`htpl:`表示这个视图是一个htpl视图模板.
举例如下:
用户访问`http://localhost:8080/leap/user_controller/user_index`,则对应的视图位置是`WEB-INF/views/user_controller/user_index.html`或者`WEB-INF/views/user_controller/user_index.jsp`

#### 请求方法声明
在路由表中我们可以看到,所有的请求`METHOD`都是`*`,这个表示接受所有的HTTP请求类型,但是在实际应用中我们往往希望限制请求类型,尤其是在restful的应用中.因此leap也提供了声明请求方法的注解:
```java
@leap.web.http.POST
@leap.web.http.GET
@leap.web.http.PUT
...
```
现在我们在`UserController.index上添加@leap.web.http.GET`注解,并重新启动应用,可以看到路由表:
```
METHOD  PATH                          ACTION                 DEFAULT VIEW
------  ---------------------------   --------------------   ------------------------------
GET     /user_controller/user_index   UserController.index   htpl:/user_controller/user_index
```
此时我们可以看到`METHOD`一栏已经变成`GET`了,表示这个接口不接受其他方法的请求.
