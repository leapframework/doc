# 静态资源

## 默认静态资源根目录

Leap 约定的**默认静态资源根目录是 webapp 下的 `/static/` 目录**。

当然其实静态资源放在 webapp 目录下任意一个地方（除 `WEB-INF` 目录和 `assets` 目录下）前端也是可以访问得到的，但是将静态资源放在约定的根目录下，Leap 会提供一些额外的功能支持。

## 虚拟路径前缀 /assets

前面提到将静态资源放在 webapp 目录下有两个地方是前端访问不到的，一个是 `WEB-INF` ，这个好理解，学过 Web 开发的应该都知道。

另外一个是 `assets` 目录，这是因为 Leap 约定了一个虚拟路径前缀 `/assets` 作为静态资源根目录的别名。

也就是说，访问以下两个路径：

- `/static/css/global.css`

- `/assets/css/global.css`

其实是等价的，最后返回的都是 `/static/css/global.css` 文件。

路径中 `/assets` 即代表了我们的默认静态资源根目录 `/static` 。

> 要注意 `/assets` 是虚拟路径，在工程中不需要存在这个目录。

约定这个虚拟路径前缀的别名的目的：

- 一个是可以**解耦静态资源的请求路径与实际存放路径的强依赖关系**；

- 另外一个是便于 Leap 识别静态资源请求，以做后续的一些增强功能，比如在 HTPL 章节会介绍到的资源文件指纹自动生成等等。

## 自定义静态资源根目录

如果使用了虚拟路径前缀，那对于我们修改静态资源根目录的位置就方便多了。

对于前端来说不需要做任何更改，而后端只需要更改静态资源到新的目录，并且在 Leap 配置中指定新的静态资源根目录即可。

自定义静态资源根目录的配置示例如下，假设我们修改 `conf/config.xml` 文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<config xmlns="http://www.leapframework.org/schema/config">

    ...

    <properties prefix="webassets">
        <property name="publicDirectory">/new_static</property>
    </properties>
</config>
```