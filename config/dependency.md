# 配置依赖

## 基本依赖

对于使用 Leap 开发的项目，最基本的依赖只有一个：

```xml
<dependency>
    <groupId>org.leapframework</groupId>
    <artifactId>leap</artifactId>
    <version>0.6.2b</version>
    <type>pom</type>
</dependency>
```

> 0.6.2b 是撰写本文档时 Leap 的最新稳定版本，当前最新版本请参考[概述](/README.md)。

## 可选依赖

由于 Leap 内部使用 logback 日志系统打印输出，因此如果项目希望打印出来的日志更加详细以及格式化，可以引入相关的日志依赖：

```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.5</version>
</dependency>

<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.0.13</version>
</dependency>
```