# 路由方法

当我们创建好控制器类，就需要创建具体的路由方法了。

## 定义

当 Leap 扫描出符合规则的控制器类之后，就会遍历控制器中的方法，将符合以下规则的方法作为路由方法。

- 必须是 `public` 方法；

- 必须是非抽象方法；

- 必须是非静态方法；

如果有符合以上规则的方法但其实并不是作为路由方法，可以使用 `@NonAction` 注解标注，Leap 就会忽略这个方法。

> `@NonAction` 注解的完整路径名称是 `leap.web.annotation.NonAction`。

## 示例

定义路由方法也是很简单的，在我们上一节创建的控制器类中，新增一个路由接口。代码如下：

```java
public class HomeController{
    public void index(){
        // do something
    }

    private void user(){
        // do something
    }
}
```

上例中 index 方法将会作为路由方法被 Leap 扫描，而 user 方法由于是私有方法所以 Leap 扫描的时候会略过。