# controller和action的使用

前面我们已经了解了leap的默认路由规则和视图映射规则,本节开始,我们来看看controller和action有哪些常见的用法.
### ControllerBase
`leap.web.action.ControllerBase`是leap提供的一个包含许多常用接口的抽象类,一般的controller可以继承这个类以便使用它提供的各种功能的接口,下面对这个类的几个常用接口做一下简单的介绍:

```java
request();
```
获取当前请求的实例对象,注意这个实例是`leap.web.Request`的实例.若想要获取servlet的request可以调用`request().getServletRequest()`.`leap.web.Request`实际上与`javax.servlet.http.HttpServletRequest`用法相差不多,但是包含了更多的接口和功能.
```java
response();
```
获取当前请求的响应对象实例,同样这个实例对象是`leap.web.Response`的对象,用法和`javax.servlet.http.HttpServletResponse`相似.
```java
result();
```
获取当前请求的结果对象,这个结果对象是`leap.web.Result`的实例,可以设置一些渲染的属性和返回状态等.
```java
validation();
```
获取当前请求的校验结果,这个校验结果是来自多方面的,有leap自身提供的校验机制添加的校验错误,也可以是我们通过拦截器或其他方式添加的校验错误,都可以在这里获取到.
```java
params();
```
获取当前请求的所有参数,返回结果是一个`leap.web.Params`的对象,用法非常简单.
```java
redirect(String location);
forward(String path);
```
页面重定向和请求分发,这两个接口的用法类似`HttpsServletResponse.sendRedirect(String location)`和`javax.servlet.RequestDispatcher.forward(ServletRequest request, ServletResponse response)`.
```java
render(Content cont);
renderView(String path);
```
指定渲染视图路径,在前面我们了解了默认的视图映射路径,但是通过`render()和renderView()`接口我们可以覆盖这个默认规则.

现在,我们修改一下上一节的`UserController`,让它继承`ControllerBase`并且修改`index()`和`list()`两个action如下:
```java
package leap.project.controller;

import leap.web.action.ControllerBase;
import leap.web.annotation.Path;
import leap.web.annotation.http.GET;

/**
 * Created by KAEL on 2015/11/4.
 */
@Path("/user_controller")
public class UserController extends ControllerBase {
    @Path("user_index")
    @GET
    public void index(){
        renderView("/user_list.html");
    }
    @Path("/user_list")
    public void list(){
        redirect("/user_controller/user_index");
    }
}

```
> **注意**:使用leap的所有接口,涉及路径的参数,以`/`开头的都表示应用根目录,而不是域名根目录.

现在我们启动应用服务,访问`http://localhost:8080/leap/user_list`,可以看到页面最终打印的结果是:
```
user list!
```
但是浏览器的地址栏是:
```
http://localhost:8080/leap/user_controller/user_index
```
这说明`/user_list`的请求被重定向到`/user_controller/user_index`下了,但是新的地址又指定了渲染`/user_list`的页面.

### action的返回值
除了通过`ControllerBase`的接口去实现重定向和自定义指定视图的功能,在action上也可以实现类似的功能.  
在leap内部实现了几个特定的返回值标识,以便区分返回结果的意图:
```java
* redirect:返回重定向
* forward:返回请求分发
* view:返回指定视图路径
* download:返回下载
```
举例如下,我们把`UserController`的代码修改如下:
```java
package leap.project.controller;

import leap.web.action.ControllerBase;
import leap.web.annotation.Path;
import leap.web.annotation.http.GET;

/**
 * Created by KAEL on 2015/11/4.
 */
@Path("/user_controller")
public class UserController extends ControllerBase {
    @Path("user_index")
    @GET
    public String index(){
        return "view:/user_list.html";
    }
    @Path("/user_list")
    public String list(){
        return "redirect:/user_controller/user_index";
    }
}
```
可以看到测试的结果和使用`ControllerBase`的接口是一样的.

### ViewData视图参数传递
现在我们已经可以访问视图了,但是在action中,我们经常需要给`view`传递参数以便视图渲染.  
在leap中,这个参数传递非常简单,我们使用了`leap.web.view.ViewData`作为视图的参数对象,一般我们只需要把想传递给视图的参数放到这个对象中即可,这个对象的获取方式可以以参数的方式传递给action,也可以从`result`中获取.
举例如下:
```java
public String index(ViewData vd){
	vd.put("name", "jim");
	result().getViewData().put("age", 20);
  return "view:/user_list.html";
}
```
这里实际上`vd`和`result().getViewData()`获取的是同一个对象,最终都会将参数`name`和`age`传递给`view`.