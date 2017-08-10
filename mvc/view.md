# 视图

## 支持模板

Leap 支持的视图模板有 JSP 和 HTPL 。

HTPL 是 Leap 内置的视图模板，比传统的 JSP 更加易用以及支持更多功能，后面会有专门的章节介绍 HTPL 。

## 默认视图根目录

在前面介绍的返回视图相关章节中，我们返回视图的路径都是**基于视图根目录**的。

视图根目录就是用于存放所有视图文件的根目录。

视图根目录的位置一般每个开发项目都可能会有自己的约定或规定，Leap 也提供了自己推荐的一个默认约定：

**Leap 默认约定 webapp 下的 `/WEB-INF/views` 目录作为视图根目录**。

例如当我们访问获取 `/user/create` 视图的时候，Leap 将会寻找并返回 `/WEB-INF/views/user/create.html` 视图。

## 自定义视图根目录

当有需要更改以上的默认视图根目录路径时，我们只需要在 Leap 配置文件做下路径配置即可。

例如在配置文件 `/conf/config.xml` 中我们做如下配置：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<config xmlns="http://www.leapframework.org/schema/config">

    ...

    <properties prefix="webmvc">
        <property name="viewsLocation">/WEB-INF/other</property>
    </properties>
</config>
```