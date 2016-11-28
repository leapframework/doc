# 参数绑定

leap的参数绑定分两部分，简单参数绑定和复合参数绑定。

## 简单参数绑定

简单参数包括java的8种基本数据类型和String，一共9种简单参数类型，这种参数类型一般是按照参数名称完全匹配来绑定参数的。比如：

```
http请求:
uri:
    /user_model/list_user
params:
    name=tom
    age=20
```

那么对应的,我们在`UserModelController.listUser(String name, Integer age)`Action中，获得的参数就是name:"tom"，age:20。

对于9种简单参数类型，如果请求中没有传对应的参数值，则会使用java的默认值，这个默认值如下表:

| 类型   | 默认值 |
| -----  |:-----:|
| int    | 0     |
| long   | 0     |
| char   | ' '   |
| float  | 0.0   |
| double | 0.0   |
| byte   | 0     |
| short  | 0     |
| boolean| false |
| String | null  |

对于8种基本数据类型的包装类，则默认值全部为null。

在参数值有可能为null的情况下，建议全部基本数据类型都使用其对应的包装类作为参数接收对象。

## 复合类型参数绑定

对于复合类型的参数，其参数绑定规则也非常简单：

> 1. 对于包含简单类型属性的复合对象，会将请求中所有参数名和该复合对象属性名一致的参数值赋值给该对象的属性；
> 2. 对于包含复合对象属性的复合对象，将根据参数名指定的参数路径给对应的属性赋值；

这里所谓的**参数路径**，指的就是指定参数中获取该参数的路径,举例如下：

假设有一个复合参数对象：

```java
package leap.project.param;

public class ParamsModel {
    public String param1;
    public String param2;
    public ParamsModel pm1;
}

```

我们在`UserModelController`中添加一个Action:

```java

public void userParams(ParamsModel pm){
    System.out.println(pm.param1);
    System.out.println(pm.param2);
}

```

此时参数`pm`的属性`param1`的参数路径就是`pm.param1`，对于参数`pm`对象的复合属性`pm1`内部的属性`param1`，其参数路径是`pm.pm1.param1`。

首层的参数路径可以省略，因此这里参数可以分别指定为`param1`和`pm1.param1`，这样对应的值会赋值到`pm.param1`和`pm.pm1.param1`。

> **注意:**  

> 对于首层复合参数可以不指定其属性对应的参数路径，但是一旦有一个参数指定了参数路径,则这个复合参数的所有属性都必须指定参数路径。

> 即是如果指定了`pm.pm1.param1`,那么对应的`param1`也必须指定参数名为`pm.param1`，否则`param1`不会赋值到`pm.param1`。

## 路径参数的设置和绑定

所谓**路径参数**，即是url路径中的参数，我们在`UserController.userParams()`这个action上添加`@Path`注解并添加一个参数：

```java
@Path("user_params/{paramName}")
public void userParams(ParamsModel pm, String paramName){
    System.out.println(paramName);
    System.out.println(pm.param1);
    System.out.println(pm.param2);
}
```

这里的`{paramName}`就是路径参数.此时我们测试如下http请求:

```
http请求:
uri:
    /user_model/user_params/jim
params:
    name=tom
    age=20
```

可以看到对应的paramName的值是`jim`,当然,这个路径改变之后,`paramName`对应的值也会改变,这个即是路径参数,路径参数和普通的参数的使用完全一致。

另外,路径参数可以使用`@leap.web.annotation.PathVariable("paramName")`指定路径参数名绑定的参数对象，如：

```java
@Path("user_params/{paramName}")
public void userParams(ParamsModel pm, @PathVariable("paramName") String paramName)
```

## 自定义参数解析器

leap已经内置了足够强大的参数解析器，不过有些时候我们也需要定制自己的参数解析器。

leap在启动的时候，会解析每个Action的参数，并分析参数确定参数解析器，因此我们需要在启动的时候判断参数是否需要由我们的参数解析器来解析，这个过程需要实现`leap.web.action.ArgumentResolverProvider`接口：

```java
public interface ArgumentResolverProvider {
    ArgumentResolver tryGetArgumentResolver(RouteBase route, Action action, Argument argument);
}
```

只要自己实现这个接口并将实现类配置为bean（可以通过xml配置，也可以直接使用`@Bean`注解）即可。

如：

```java
@Bean
public class MyArgumentResolverProvider implement ArgumentResolverProvider{
    ArgumentResolver tryGetArgumentResolver(RouteBase route, Action action, Argument argument){
        return (context,arg) -> context.getRequest().getParameter(arg.getName());
    }
}
```

这个参数解析器提供器会返回一个参数解析器`leap.web.action.ArgumentResolver`，示例中我们使用的是λ表达式做了简单的时间，当然也可以自己写一个实现类并在这里返回该实现类的对象。