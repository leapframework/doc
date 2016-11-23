# 快速开始

本文将指引你如何快速从零搭建leap的开发环境。

## 创建maven工程

首先，需要用你熟悉的方式创建一个空的maven工程，这里以idea为例，创建后的工程结构如下：

![工程结构](/assets/demo-web1.png)

## 配置leap的maven资源库

打开pom.xml，添加如下配置：

```xml
<repositories>
    <!-- leap快照资源库 -->
    <repository> <id>leap-snapshots</id>
        <url>https://raw.githubusercontent.com/leapframework/repo/master/snapshots</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
            <checksumPolicy>warn</checksumPolicy>
        </snapshots>
    </repository>
    <repository>
        <id>leap-releases</id>
        <url>https://raw.githubusercontent.com/leapframework/repo/master/releases</url>
        <releases>
        <enabled>true</enabled>
        <updatePolicy>never</updatePolicy>
        <checksumPolicy>warn</checksumPolicy>
        </releases>
    </repository>
</repositories>
```

添加完成之后就可以使用maven配置leap的依赖了。

## 添加leap的依赖

添加如下依赖：

```xml
<dependency>
    <groupId>org.leapframework</groupId>
    <artifactId>leap</artifactId>
    <version>${leap.version}</version>
    <type>pom</type>
</dependency>
```

添加完成之后，就可以在idea中看到相关的依赖包了。

![依赖包](/assets/demo-web2.png)

这里使用的示例是`0.4.0b-SNAPSHOT`的版本，具体使用哪个版本可以自行决定，建议使用最新的版本。

## 初始化配置

现在我们开始初始化我们一些必须的配置。

在`src/main/resources`目录下创建一个`conf`文件夹，这是leap指定的配置文件目录。

接着在`conf`目录下创建`config.xml`文件，这个是leap默认的配置文件。

内容如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<config xmlns="http://www.leapframework.org/schema/config"
xmlns:orm="http://www.leapframework.org/schema/orm/config">
    
</config>
```