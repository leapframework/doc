# 多模块工程

在实际项目开发中，多模块工程更有利于划分清楚项目结构。

因此本节将介绍 Leap 推荐的多模块工程结构，指引读者创建一个 maven 多模块工程。

## 结构

与之前工程结构一节介绍的工程结构不同，多模块工程在这些工程结构之上加多了一层结构，如下图所示：

```
myproject
|----api
|    |----src
|    |----pom.xml
|----core
|    |----src
|    |----pom.xml
|----deploy
|    |----src
|    |----pom.xml
|----pom.xml
```

以上结构中：

- myproject 是我们多模块工程的父模块目录，该目录下除了子模块之外只有一个属于它的 pom.xml 文件；
- api 是子模块之一，我们将用它来存放 API 开发相关代码和配置；
- core 是子模块之一，我们将用它来存放项目所有核心代码和配置；
- deploy 是子模块之一，是一个 web 模块，本身不存放代码，可存放部署相关的配置。主要用于引入其他模块一起打包部署；
- 以上各个模块都各自有自己的 pom.xml文件。

各个子模块内部的结构与工程结构一节介绍的工程结构基本一致，不一致的地方在于：多模块工程中，只需要 deploy 子模块需要配置 web.xml 。

在实际开发中根据项目需要可能添加其他子模块，也是按照以上子模块的格式建立目录结构。

## 配置 pom.xml

**父模块**的 pom.xml 示例如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.demo.myproject</groupId>
    <artifactId>myproject</artifactId>
    <packaging>pom</packaging>
    <version>1.0-SNAPSHOT</version>
    <modules>
        <module>core</module>
        <module>api</module>
        <module>deploy</module>
    </modules>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <leap.version>0.6.2b</leap.version>
        <slf4j.version>1.7.10</slf4j.version>
        <logback.version>1.0.13</logback.version>
    </properties>

    <dependencies>
        <!-- leap -->
        <dependency>
            <groupId>org.leapframework</groupId>
            <artifactId>leap</artifactId>
            <version>${leap.version}</version>
            <type>pom</type>
        </dependency>
    </dependencies>
</project>
```

- 在父模块 pom.xml 文件中我们需要使用 modules 元素列出所有子模块。

- 我们可以在父模块中定义一些 properties 变量，这些变量在子模块中可直接使用。

- 我们还可以在父模块中定义全局的依赖，所有子模块也会自动拥有这些依赖。在这里我们引入了 Leap 的依赖，因为几乎全部子模块都可能用到 Leap ，为了方便，我们直接在父模块引入。

-----------------

**api** 子模块 pom.xml 文件示例如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <groupId>com.demo.myproject</groupId>
        <artifactId>myproject</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>api</artifactId>

    <dependencies>
        <dependency>
            <groupId>com.demo.myproject</groupId>
            <artifactId>core</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>

</project>
```

- 在子模块 pom.xml 中我们使用 parent 元素指定了父模块的 maven 坐标。

- 由于 api 模块一般我们会用来放置 API controller 以及其它 api 相关的类，因此将可能需要调用 service 类完成操作，这些类一般会写在 core 子模块中，因此我们这里引用了 core 模块。

- 实际项目中根据项目需要引用其它子模块依赖，需要注意要避免循环引用。

-----------------

**core** 子模块 pom.xml 文件示例如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <groupId>com.demo.myproject</groupId>
        <artifactId>myproject</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>core</artifactId>

    <dependencies>

        <!-- db -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.26</version>
        </dependency>

        <!-- test -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.11</version>
            <scope>test</scope>
        </dependency>

        <!-- log -->
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
    </dependencies>
</project>
```

- core 子模块由于是核心模块，一般不会再引用其它子模块，因此这里的示例只是引入了一些项目常用外部依赖如单元测试、日志、数据库连接等依赖。

-----------------

**deploy** 子模块的 pom.xml 示例文件如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <groupId>com.demo.myproject</groupId>
        <artifactId>myproject</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <packaging>war</packaging>

    <artifactId>deploy</artifactId>

    <dependencies>
        <dependency>
            <groupId>com.demo.myproject</groupId>
            <artifactId>core</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>

        <dependency>
            <groupId>com.demo.myproject</groupId>
            <artifactId>api</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.eclipse.jetty</groupId>
                <artifactId>jetty-maven-plugin</artifactId>
                <version>9.4.5.v20170502</version>
            </plugin>
        </plugins>
    </build>
</project>
```

- 这里 deploy 子模块的打包格式必须为 war。

- 依赖引用包含了 core 和 api 子模块，其实这里 core 可以不引用，因为根据 maven 的引用传递规则， api 引用了 core，那这里 deploy 也就间接地引用了 core。但是为了明确引用，我们还是写上来。

- 这里可选配置一个 jetty 插件，方便在 IDEA 中调试运行。

## 其他配置

由于剩下的配置其实与单模块工程差别不大，只是不同的配置需要配置到不同子模块中，因此这里只是做简单介绍：

- deploy 子模块需要配置 web.xml 文件。

- core 子模块需要配置 Leap 以及数据源。

- api 子模块需要配置 API 相关配置。

需要注意的是，api 子模块的配置，除了按照之前这样配置：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<apis xmlns="http://www.leapframework.org/schema/webapi">
    <api name="api" base-path="/">
    </api>
</apis>
```

还有另外一种配置方式，就是可以通过 base-package 属性指定一个包名，这个包名**与 config.xml 文件中 base-package 指定的包名必须不同，且不能为包含关系**。

这样指定之后，这里的 base-path 就不需要固定为根目录，可以根据需求更改。示例如下：

```xml

<?xml version="1.0" encoding="UTF-8"?>
<apis xmlns="http://www.leapframework.org/schema/webapi">
    <api name="api" base-path="/myapi" base-package="myapi">
    </api>
</apis>
```

通过以上配置，我们在写代码的时候就需要注意将该模块相关的 Controller 类放到相关的 base-package 包下。