# 使用 ${} 解析表达式

一般使用表达式的场景都是这样：我们在控制器方法中将变量值传递给视图，视图解析的时候再动态渲染出来。

## 传递简单类型变量值

因此为了演示 `${...}` 表达式的使用，我们先假设在控制器方法中，我们传递了一些简单变量值给视图。

代码如下：

```java
@Path("/home")
public class HomeController extends ControllerBase {

    @GET
    public void expr(ViewData data) {
        data.put("name", "hello");
    }
}
```

根据之前 MVC 相关章节的介绍，我们知道 expr 方法中的视图数据将会传递给视图目录下的 `home/expr.html` 视图。

因此在`home/expr.html` 视图中我们就可以使用 name 变量了。比如这样：

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>expr</title>
</head>
<body>
    ${name}
</body>
</html>
```

其中 `${name}` 将会被渲染为 `hello` ，因为 name 变量的值已经由控制器方法传递到了视图。

## 传递复杂类型变量值

除了简单类型变量值的解析，这里也支持传递复杂类型变量值，并且支持调用对象值上的 Java 方法。

假设我们将上面的例子稍微改一下，传递给视图的数据改成这样：

```java
@GET
public void expr(ViewData data) {
    User user = new User();
    user.setId("251721");
    user.setName("hello name");
    data.put("user", user);
}
```

这里可以看到我们传递了一个 User 对象给视图，这个对象有 id 和 name 两个属性。

然后我们的视图模板这样写：

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>expr</title>
</head>
<body>
<p>${user}</p>
<p>${user.id} + ${user.name}</p>
<p>${user.name.toUpperCase()}</p>
</body>
</html>
```

启动工程，访问 `/home/expr` 页面，浏览器将返回下面这样的内容：

```
[User@3ad48a89id='251721', name='hello name']

251721 + hello name

HELLO NAME
```

表达式的解析其实就是 EL 表达式的解析，不清楚 EL 表达式的读者可以先去了解一下。