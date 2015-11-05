# 拦截器
leap提供了自己的拦截器机制,目前有三种拦截器:
> 1. 全局拦截器:拦截所有请求;
> 2. controller拦截器:拦截指定controller下的所有action;
> 3. action拦截器:拦截指定action的请求.

### 全局拦截器
我们先创建一个拦截器的包`leap.project.inteceptor`,并在包下创建一个`GlobalInteceptor.java`:
```
leap.project
      ├　controller
      └　inteceptor
          └　GlobalInteceptor.java
```
全局拦截器有两种实现方式,第一种是继承`leap.web.RequestInterceptorAdpater`,另一种是实现`leap.web.RequestInterceptor`接口,两种方式本质上是一样的(`RequestInterceptorAdpater`实现了`RequestInterceptor`接口).
这里我们以继承`leap.web.RequestInterceptorAdpater`为例,`GlobalInteceptor`代码如下:
```java
package leap.project.inteceptor;

import leap.lang.intercepting.State;
import leap.web.Request;
import leap.web.RequestExecution;
import leap.web.RequestInterceptorAdpater;
import leap.web.Response;

public class GlobalInteceptor extends RequestInterceptorAdpater {
	@Override
	public State preRequest(Request request, Response response)
			throws Throwable {
		System.out.println("GlobalInteceptor.preRequest");
		return State.CONTINUE;
	}
	
	@Override
	public State postRequest(Request request, Response response,
			RequestExecution execution) throws Throwable {
		System.out.println("GlobalInteceptor.postRequest");
		return State.CONTINUE;
	}
	@Override
	public void completeRequest(Request request, Response response,
			RequestExecution execution) throws Throwable {
		System.out.println("GlobalInteceptor.completeRequest");
	}
}
```
接下里需要将这个拦截器添加到我们的应用中.
修改`leap.project.Global`的`init()`方法,代码如下:
```java
@Override
protected void initApp() throws Throwable {
	interceptors().add(new GlobalInteceptor());
}
```
这里`interceptors()`接口是获取应用所有拦截器的接口,在这里添加我们自定义的拦截器即可实现全局拦截器.
启动应用,访问`http://localhost:8080/leap/`,可以看到控制台打印的日志:
```
GlobalInteceptor.preRequest
15:26:57.605 [http-nio-8080-exec-2] DEBUG leap.web.DefaultAppHandler - Routing path '/'
15:26:57.606 [http-nio-8080-exec-2] DEBUG leap.web.DefaultAppHandler - Handling request '/' by action 'HomeController.index'...
15:26:57.614 [http-nio-8080-exec-2] DEBUG leap.web.DefaultAppHandler - Rendering result : {status:200,content:htpl:/index} , locale : zh_CN
15:26:57.632 [http-nio-8080-exec-2] DEBUG leap.htpl.web.WebHtplView - Rendering htpl template(pjax=false) : /index
GlobalInteceptor.postRequest
15:26:57.635 [http-nio-8080-exec-2] DEBUG leap.web.DefaultAppHandler - Request '/' executed 32ms
GlobalInteceptor.completeRequest
```
我们可以看到,请求按照:GlobalInteceptor.preRequest → GlobalInteceptor.postRequest → GlobalInteceptor.completeRequest执行了拦截器的几个接口.
### controller拦截器
controller拦截器仅拦截指定controller下的action,这里有三种方式配置controller拦截器:
> 1. controller继承`leap.web.action.ActionInterceptorAdapter`;
> 2. controller实现`leap.web.action.ActionInterceptor`接口;
> 3. controller添加`leap.web.annotation.InterceptedBy`注解.

在上面的三种配置方式中,1和2本质上是一样的,我们现在以1为例,在`leap.project.controller`包下创建`PostController`类,并重写拦截器的几个接口,代码如下:
```java
package leap.project.controller;

import leap.core.validation.Validation;
import leap.lang.intercepting.State;
import leap.web.action.ActionContext;
import leap.web.action.ActionExecution;
import leap.web.action.ActionInterceptorAdapter;
public class PostController extends ActionInterceptorAdapter {
	public void index(){
		
	}
	@Override
	public State preActionExecuting(ActionContext context, Validation validation)
			throws Throwable {
		System.out.println("PostController.preActionExecuting");
		return State.CONTINUE;
	}
	
	@Override
	public State postActionExecuting(ActionContext context,
			Validation validation, ActionExecution execution) throws Throwable {
		System.out.println("PostController.postActionExecuting");
		return State.CONTINUE;
	}
	
	@Override
	public void completeActionExecuting(ActionContext context,
			Validation validation, ActionExecution execution) throws Throwable {
		System.out.println("PostController.completeActionExecuting");
	}
	
}
```
启动应用访问`http://localhost:8080/leap/post`,可以看到如下日志:
```
GlobalInteceptor.preRequest
15:43:07.573 [http-nio-8080-exec-2] DEBUG leap.web.DefaultAppHandler - Routing path '/post'
15:43:07.574 [http-nio-8080-exec-2] DEBUG leap.web.DefaultAppHandler - Handling request '/post' by action 'PostController.index'...
PostController.preActionExecuting
PostController.postActionExecuting
PostController.completeActionExecuting
15:43:07.579 [http-nio-8080-exec-2] DEBUG leap.web.DefaultAppHandler - Rendering result : {status:200,content:null} , locale : zh_CN
GlobalInteceptor.postRequest
15:43:07.580 [http-nio-8080-exec-2] DEBUG leap.web.DefaultAppHandler - Request '/post' executed 11ms
GlobalInteceptor.completeRequest
```
可以看到,除了全局拦截器之外,PostController的拦截器按照PostController.preActionExecuting → PostController.postActionExecuting → PostController.completeActionExecuting顺序执行了拦截器的接口.

> 通过注解配置controller拦截器的方式,与action级别的拦截器的配置方式是一致的,请参考action拦截器的配置方式.
### action拦截器
action拦截器只拦截指定的action,我们先在`leap.project.inteceptor`包下创建拦截器`ActionInteceptor`,同样重写拦截器的几个重要接口,代码如下:
```java
package leap.project.inteceptor;

import leap.core.validation.Validation;
import leap.lang.intercepting.State;
import leap.web.action.ActionContext;
import leap.web.action.ActionExecution;
import leap.web.action.ActionInterceptorAdapter;

public class ActionInteceptor extends ActionInterceptorAdapter {
	@Override
	public State preActionExecuting(ActionContext context, Validation validation)
			throws Throwable {
		System.out.println("ActionInteceptor.preActionExecuting");
		return State.CONTINUE;
	}
	
	@Override
	public State postActionExecuting(ActionContext context,
			Validation validation, ActionExecution execution) throws Throwable {
		System.out.println("ActionInteceptor.postActionExecuting");
		return State.CONTINUE;
	}
	
	@Override
	public void completeActionExecuting(ActionContext context,
			Validation validation, ActionExecution execution) throws Throwable {
		System.out.println("ActionInteceptor.completeActionExecuting");
	}
}

```
修改`UserController`的`list()`action,添加注解,代码如下:
```java
@Path("/user_list")
@InterceptedBy(ActionInteceptor.class)
public String list(){
    return "redirect:/user_controller/user_index";
}
```
启动后即可从终端看出执行顺序,与controller级别的拦截器一致.
