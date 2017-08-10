# 多主题

这里的多主题不是指的 CSS 样式的多主题，而是针对视图和静态资源一整套的多主题。

在前面我们介绍的视图和静态资源都有默认的根目录位置，当我们启用多主题之后，每个主题也拥有自己的视图和静态资源根目录。

## 配置

启用多主题支持不需要在配置文件中进行配置，只需要向 Leap 注册一个实现 `leap.web.theme.ThemeResolver` 接口的主题解析 Bean 即可。

比如下面我们简单实现了一个主题解析器：

```java
@Bean
public class MyThemeResolver implements ThemeResolver {
    @Override
    public String resolveThemeName(Request request) throws Throwable {
        String name = request.getParameter("theme");
        if(Strings.isNotBlank(name)) return name;
        return null;
    }
}
```

在类上我们使用注解将这个类声明为 Bean 注册到 Leap 中。

在接口方法中我们获取请求参数 `theme` 作为主题名称并返回，如果没有这个参数则返回 `null` 。

**这个方法会在所有请求中被调用，而实现这个接口的目的就是为了让 Leap 知道返回哪个主题下的资源。**

## 位置

主题资源根目录的**默认位置**是在 `webapp` 下的 `/WEB-INF/themes` 目录。

在这个目录下的所有直接子目录都会被当做主题目录。

例如以下目录结构：

```
webapp
|----WEB-INF
|    |----themes
|    |    |-----themeA
|    |    |-----themeB
|    |    |-----themeC
|    |    |-----themeD
```

则代表工程中一共有四套主题：`themeA`、`themeB`、`themeC`、`themeD` 。

在上面的主题解析类方法返回的名称必须是主题名称之一或者为 `null` ，**为 `null` 时表示资源不从主题中获取，而是按照之前章节介绍的从默认位置获取**。

## 规则

既然主题可以拥有自己的视图和静态资源，那么应该怎么组织呢？

Leap 约定：

- 主题目录下的 `views` 目录作为主题视图根目录；

- 主题目录下的 `static` 目录作为静态资源根目录。

> 这里主题所拥有的视图根目录名称和静态资源根目录名称是**固定**的，不受前面自定义视图根目录和自定义静态资源根目录的影响。

当我们的主题解析器返回了具体的主题名称，在接口需要返回视图或者返回静态资源时，将会到该主题的资源根目录下按照路径去寻找对应资源。

如果找到了，则返回对应资源；**如果找不到，将尝试查找并返回默认资源根目录下的资源**，如果还不存在则会报错。

## 示例

根据以上描述我们举一个例子。

假设现在有如下目录文件结构：

```
webapp
|----WEB-INF
|    |----themes
|    |    |-----themeA
|    |    |     |-----views
|    |    |     |     |-----my_view.html
|    |    |     |
|    |    |     |-----static
|    |    |           |-----global.css
|    |    |
|    |    |-----themeB
|    |    |     |-----views
|    |    |           |-----my_view.html
|    |    |
|    |    |-----themeC
|    |
|    |----views
|         |-----my_view.html
|
|----static
     |----global.css
```

并且假设我们的服务器是搭在本地 `http://localhost:8080/demo` 上。

那么：

- 当我们访问 `http://localhost:8080/demo/my_view?theme=themeA` 时，将返回 `WEB-INF/themes/themeA/views/my_view.html` 文件；
- 当我们访问 `http://localhost:8080/demo/my_view?theme=themeB` 时，将返回 `WEB-INF/themes/themeB/views/my_view.html` 文件；
- 当我们访问 `http://localhost:8080/demo/my_view?theme=themeC` 时，将返回 `WEB-INF/views/my_view.html` 文件。

> 最后一个是由于 themeC 主题下没有对应文件，因此返回默认视图根目录下的文件。