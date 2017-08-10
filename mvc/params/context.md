# 内置参数类型

在一些请求方法中我们需要获取请求的其他信息，或者是需要自己从请求对象中解析参数，这个时候我们就需要获取到一些请求相关的信息。

想要获取这些信息，我们可以**在路由方法中添加指定类型的方法参数**，这样在请求发起时，Leap 就会为这些指定类型的参数赋予对应的值，这样我们就可以在方法中使用了。

> 下面介绍的这些对象其实也可以在定义控制器类的时候通过继承 `leap.web.action.ControllerBase` 类，就可以在路由方法中调用父类方法获取，两种获取方式各有优劣但相差不大，开发人员可以自行决定使用哪种。

## 获取请求 Request 对象

这里的请求 Request 对象支持两种：

- `javax.servlet.http.HttpServletRequest` ：Servlet API 中原始的请求类型；

- `leap.web.Request` ：Leap 内置的 API 更加丰富易用的 Request 对象，通过此对象也可以获取到原始的 `HttpServletRequest` 。

两相对比，我们更推荐使用 `leap.web.Request` 作为路由方法的 Request 对象类型。

简单的使用示例如下：

```java
public class UserController {

    public String getContextUrl(Request request) {
        return request.getContextUrl();
    }
}
```

我们在 `getContextUrl` 方法中使用 `request` 对象获取到了请求的上下文路径。

假设当前服务器部署在 `http://localhost:8080` 下，应用上下文为 `/demo` ，则这个接口访问时将返回 `http://localhost:8080/demo` 。

## 获取响应 Response 对象

响应对象与请求对象一样，也是支持两种类型：

- `javax.servlet.http.HttpServletResponse` ：Servlet API 中原始的请求类型；

- `leap.web.Response` ：Leap 内置的 API 更加丰富易用的 Response 对象，通过此对象也可以获取到原始的 `HttpServletResponse` 。

简单的使用示例如下：

```java
public class UserController {

    public void getContextUrl(Request request, Response response) {
       String contextUrl = request.getContextUrl();
       PrintWriter pw = response.getWriter();
       pw.print(contextUrl);
       pw.flush();
       pw.close();
    }
}
```

以上示例与前一个示例作用完全相同，只不过这里我们返回结果是直接调用 `response` 对象的方法直接写回去。

## 获取参数 Params 对象

这个 Params 对象主要适用于变参或者不定参的接口，需要路由方法自己处理参数解析，因此提供的这个类型其实相当于一个参数的 Map 。

我们可以通过这个对象很方便地获取接口参数，不过需要注意这里只能获取普通类型的参数，之前介绍的复杂对象类型是无法获取的。

下面是使用示例：

```java
public class UserController {

    public String params(Params params) {
        String name = params.get("name");
        return name;
    }
}
```

示例中我们在路由方法中简单地获取名为 `name` 的参数的值，并直接返回给前端。

## 获取返回结果 Result 对象

Result 对象主要用于在路由方法中对返回结果进行处理和设置，例如指定返回内容、指定渲染视图、指定响应状态码等等。

下面是使用示例：

```java
public class UserController {

    public void result(Result result) {
        result.setStatus(404);
    }
}
```

上例中我们对这个接口简单地返回了 404 状态码。

## 获取视图数据 ViewData 对象

对于需要渲染视图返回的接口，我们主要是通过 ViewData 对象来传递视图数据。

下面是使用示例：

```java
public class UserController {

    public void viewData(ViewData vd, Result result) {
        vd.put("name", "zhangsan");
        result.renderView("view");
    }
}
```

上例我们定义了一个视图变量 `name` 并指定其值为 `zhangsan`，然后再使用 `result` 对象指定要渲染的视图名称。这样这个变量就可以在视图渲染的时候使用。

## 获取上下文 ActionContext 对象

这个对象是 Leap 中的路由上下文对象，通过这个对象可以获取到前面介绍到的其他所有 Leap 内置对象。

示例如下：

```java
public class UserController {

    public void actionContext(ActionContext ac) {
        Request request = ac.getRequest();
        Response response = ac.getResponse();
        Params params = ac.getRequest().params();
        Result result = ac.getResult();
        ViewData viewData = ac.getResult().getViewData();
    }
}
```

> 如果希望了解以上介绍的 Leap 内置类型都有哪些 API ，推荐借助 IDE 的方法提示功能了解，或者是直接通过源码了解。