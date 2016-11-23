# 日志配置

leap中内置了简单的日志模块，只支持打印到控制台，因此一般我们都需要单独配置日志模块，leap支持使用slf4j接口的日志框架，只需要简单的配置即可完美集成，推荐使用logback日志框架。

## 添加slf4j和logback的依赖

```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>${slf4j.version}</version>
    <type>jar</type>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>jcl-over-slf4j</artifactId>
    <version>${slf4j.version}</version>
    <type>jar</type>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>${logback.version}</version>
    <type>jar</type>
    <scope>runtime</scope>
</dependency>
```

这里可以自行选择版本，slf4j需要使用`1.7.5`或以上的版本，logback需要使用`1.0.13`或以上的版本。

## 添加logback的配置

在`src/main/resource`下创建`logback.xml`（或者`logback-test.xml`），如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration scan="true" scanPeriod="10 seconds">
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <!-- do not add line number output , it will slow down the execution speed -->
            <pattern>%d{HH:mm:ss.SSS} [%thread] %level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    <root level="WARN">
        <appender-ref ref="STDOUT" />
    </root>
</configuration>
```