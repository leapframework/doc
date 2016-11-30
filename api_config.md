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

**leap中对api的配置统一放在`conf/apis.xml`和`conf/apis/*.xml`中。**api的配置文件结构如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<apis xmlns="http://www.leapframework.org/schema/web/apis">

</apis>

```

leap的api是模块化的，以包为基础进行扫描，并且支持指定特定的URI，一个标准的api配置如下：

```xml

```