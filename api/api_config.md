# API配置

leap的api开发是一个单独的模块，因此使用leap的api开发功能需要单独添加api模块的依赖:

```xml
<dependency>
    <groupId>org.leapframework</groupId>
    <artifactId>leap-webapi</artifactId>
    <version>${leap.version}</version>
</dependency>
```

api模块的版本号一般跟随leap主版本号。

## 配置API

**leap中对api的配置统一放在`conf/apis.xml`和`conf/apis/*.xml`中。**一个空的api的配置文件结构如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<apis xmlns="http://www.leapframework.org/schema/web/apis">

</apis>
```

leap的api是模块化的，以包为基础进行扫描，并且支持指定特定的URI，一个标准的api配置如下：

```xml
<api name="demo-api" base-path="/demo-api" base-package="demo.api">
</api>
```

* 这里`name`是给api定义的名字；
* `base-path`表示的是这个api访问的uri上下文(即`${应用上下文}/${base-path}`)；
* `base-package`不是一个必配属性，这个属性表示这个api扫描的相关包，如果没有配置，则根据`base-path`找到应用所有匹配的接口作为这个api的接口，如果配置了，则扫描这个包下所有的接口。

