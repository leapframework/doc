# 返回指定视图

一般情况下我们推荐**视图的存放路径与视图的路由方法的路径是一致的**，这也是前面介绍的 Leap 返回默认视图的规则。

但是在一些特殊情况下，比如某个公共视图页面允许被多个路由方法返回，Leap 也支持路由方法返回指定的视图。

返回指定视图主要通过前面介绍的 `Result` 类的 `renderView` 方法指定，目前获取当前请求 `Result` 对象主要有两种方式。

## 通过继承 `leap.web.action.ControllerBase` 获取

下面是示例代码：

```java
public class UserController extends ControllerBase {

    public void renderOtherView() {
        super.result().renderView("/other/view");
    }
}
```

## 通过内置参数获取

下面是示例代码：

```java
public class UserController {

    public void renderAnotherView(Result result) {
        result.renderView("/another/view");
    }
}
```

需要注意的是，以上两种方式返回指定视图时，可以省略视图后缀，并且指定视图的路径是有两种形式的：

- 带斜杠 `/` 前缀的路径：这类路径是**基于视图根目录**的；

- 不带斜杠 `/` 前缀的路径：这类路径是**基于当前控制器类路径**的。

    > 例如在 `UserController` 中的某个路由方法 `view1()` 我们调用了 `result.renderView("view2")` 。

    > 那么最终我们调用了接口 `/user/view1` 后 Leap 会去寻找视图根目录下路径为 `/user/view2.html` 的视图进行渲染。