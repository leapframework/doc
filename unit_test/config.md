# 配置

编写单元测试，首先需要配置项目引入 Leap 的单元测试依赖：

```xml
<dependency>
    <groupId>org.leapframework</groupId>
    <artifactId>leap-webunit</artifactId>
    <version>${leap.version}</version>
    <scope>test</scope>
</dependency>
```

> ${leap.version} 跟随 Leap 的主版本。