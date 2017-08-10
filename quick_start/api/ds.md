# 配置数据源

在进行 Web 开发之前，需要准备一个数据库以及访问数据的 `DataSource` ，为了方便演示，我们使用嵌入式数据库 [H2 Database](http://h2database.com/) 。

## 配置依赖

修改`pom.xml`，添加以下依赖：

```xml
   <dependency>
       <groupId>com.h2database</groupId>
       <artifactId>h2</artifactId>
       <version>1.3.172</version>
   </dependency>
```

> 如果依赖已经存在请跳过此步骤

## 配置DataSource

创建 `src/main/resources/conf/beans.xml` 文件：

[import](codes/beans.xml)

修改 `src/main/resources/conf/config.xml` ，添加 `<properties>` 节点：

```xml
<config>
    <base-package>hello</base-package>

    <properties>
        <property name="jdbc.driverClassName">org.h2.Driver</property>
        <property name="jdbc.url">jdbc:h2:./target/test;DB_CLOSE_ON_EXIT=FALSE</property>
        <property name="jdbc.username">sa</property>
    </properties>

</config>
```