# 工程结构

Leap 推荐使用以下工程结构进行项目开发：

```
|----src
|    |----main
|    |    |----java
|    |    |    |----hello
|    |    |----resources
|    |    |    |----conf
|    |    |
|    |    |----webapp
|    |         |----WEB-INF
|    |              |----web.xml
|    |----test
|         |----java
|         |----resources
|----pom.xml
```

以下是各个目录或文件的作用说明：

| 路径 | 说明 |
| :--- | :--- |
| src/main/java | 代码根目录 |
| src/main/resources | 资源根目录 |
| src/main/resources/conf | Leap 配置文件存放目录 |
| src/main/webapp | web 资源根目录 |
| src/main/webapp/WEB-INF/web.xml | web 应用配置 |
| src/test/java | 测试代码根目录 |
| src/test/resources | 测试资源根目录 |
| pom.xml | 项目 POM 文件 |

注意 **Leap 约定配置文件存放目录名称一定要为 `conf`** ，不然项目启动将无法读取相关配置。