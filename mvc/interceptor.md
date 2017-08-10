# 拦截器

在 Web 开发中拦截器的应用场景很多，例如做统一日志记录、统一错误处理等等。

在 Leap 中实现拦截器只需要注册特定类型的 Bean 供 Leap 调用即可。

目前按照拦截作用域分主要有两种拦截器可供实现。

## 路由拦截器

要实现一个路由拦截器，可以让我们的实现类实现 `leap.web.action.ActionInterceptor` 。

查看这个类的源码我们可以看到 `ActionInterceptor` 一共有四个接口方法，不过都提供了默认空实现，项目可以根据需要实现其中的一个或多个方法。

这四个接口方法对应了路由处理中的四个不同阶段：

- `preExecuteAction` ：在执行具体路由方法前调用；

- `postExecuteAction` ：在执行路由方法成功之后调用；

- `onActionFailure` ：在路由方法抛出未捕获异常时调用；

- `completeExecuteAction` ：在路由处理之后最后调用，无论之前执行成功或失败。

需要注意的是，路由拦截器是**在请求可以匹配到具体路由方法的情况下**才生效的。如果一个请求无法找到匹配的路由方法，将不会调用以上任何方法。

一个简单的路由拦截器实现代码如下：

```java
@Bean
public class MyActionInterceptor implements ActionInterceptor {
    @Override
    public State preExecuteAction(ActionContext context, Validation validation) throws Throwable {
        System.out.println("MyActionInterceptor.preExecuteAction");
        return State.CONTINUE;
    }

    @Override
    public State postExecuteAction(ActionContext context, Validation validation, ActionExecution execution) throws Throwable {
        System.out.println("MyActionInterceptor.postExecuteAction");
        return State.CONTINUE;
    }

    @Override
    public State onActionFailure(ActionContext context, Validation validation, ActionExecution execution) throws Throwable {
        System.out.println("MyActionInterceptor.onActionFailure");
        return State.CONTINUE;
    }

    @Override
    public void completeExecuteAction(ActionContext context, Validation validation, ActionExecution execution) throws Throwable {
        System.out.println("MyActionInterceptor.completeExecuteAction");
    }
}
```

## 请求拦截器

请求拦截器是在**请求的生命周期**做拦截的，因此它比路由拦截器覆盖的范围更大。

前面提到路由拦截器是在请求可以匹配到具体路由方法的情况下才生效的，而**请求拦截器对任意非静态资源请求都将生效，无论请求是否能匹配到路由方法**。

> 但是对于大部分项目来说，其实并不需要用到请求拦截器这么大的作用域，使用路由拦截器是更为推荐和通用的做法。

要实现请求拦截器，可以让实现类实现 `leap.web.RequestInterceptor` 。

`RequestInterceptor` 也是有多个对应不同阶段的拦截方法，也都提供了默认实现方便具体项目按需拦截。

主要有以下几个拦截方法：

- `preHandleRequest` ：在请求开始处理之前调用；

- `handleRoute` ：在请求匹配到具体路由之后，在处理路由之前调用；

- `handleNoRoute` ：在请求匹配不到具体路由之后调用；

- `postHandleRequest` ：在请求成功处理之后调用；

- `onRequestFailure` ：在请求处理失败之后调用；

- `completeHandleRequest` ：在请求处理之后最后调用，无论成功或失败。

一个简单的请求拦截器代码示例如下：

```java
@Bean
public class MyRequestInterceptor implements RequestInterceptor {

    @Override
    public State preHandleRequest(Request request, Response response) throws Throwable {
        System.out.println("MyRequestInterceptor.preHandleRequest");
        return State.CONTINUE;
    }

    @Override
    public State handleRoute(Request request, Response response, Route route, ActionContext ac) throws Throwable {
        System.out.println("MyRequestInterceptor.handleRoute");
        return State.CONTINUE;
    }

    @Override
    public State handleNoRoute(Request request, Response response) throws Throwable {
        System.out.println("MyRequestInterceptor.handleNoRoute");
        return State.CONTINUE;
    }

    @Override
    public State postHandleRequest(Request request, Response response, RequestExecution execution) throws Throwable {
        System.out.println("MyRequestInterceptor.postHandleRequest");
        return State.CONTINUE;
    }

    @Override
    public State onRequestFailure(Request request, Response response, RequestExecution execution) throws Throwable {
        System.out.println("MyRequestInterceptor.onRequestFailure");
        return State.CONTINUE;
    }

    @Override
    public void completeHandleRequest(Request request, Response response, RequestExecution execution) throws Throwable {
        System.out.println("MyRequestInterceptor.completeHandleRequest");
    }
}
```

## 返回状态码

在上面介绍的路由拦截器和请求拦截器中，很多接口方法的返回类型都是 `leap.lang.intercepting.State` 类型。

它提供了三个可用的内置对象值，分别代表三种不同的含义：

- `State.CONTINUE` ：让处理流程继续往下走，此次拦截不对处理流程产生任何影响。

    > 一般在统一日志处理或权限验证成功时返回。

- `State.INTERCEPTED` ：中断处理流程，原有处理逻辑不再执行。

    > 一般在权限验证失败时或统一错误处理之后返回。

- `State.CONTINUE_PROCESSED` ：主要用于处理失败拦截的情况，一般情况下比较少用。

    > 这个含义比较特殊，它表示在拦截错误并处理之后依然抛出异常。