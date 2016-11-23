# 快速开始

本文将指引你如何快速从零搭建leap的开发环境。

## 创建maven工程

首先，需要用你熟悉的方式创建一个空的maven工程，这里以idea为例，创建后的工程结构如下：

![工程结构](/assets/demo-web1.png)

如果你创建的工程中没有`webapp/WEB-INF`的话，请自行创建这两个目录，并在pom.xml中修改打包方式为war:

```xml
<packaging>war</packaging>
```

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

> **注意：**这里依赖的`<type>pom</type>`必须指定。

## 初始化配置

现在我们开始初始化我们一些必须的配置。

在`src/main/resources`目录下创建一个`conf`文件夹，这是leap指定的配置文件目录。

接着在`conf`目录下创建`config.xml`文件，这个是leap默认的配置文件。

内容如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<config xmlns="http://www.leapframework.org/schema/config" xmlns:orm="http://www.leapframework.org/schema/orm/config">
    <base-package>demo</base-package>
</config>
```

现在我们的配置已经初始化好了，注意，这里的`base-package`表示leap扫描的顶级包名，需要按照自己实际定义的包名配置。

## 创建控制器和全局对象

我们先创建一个顶级包，由于前面我们配置了`base-package`为`demo`，所以这里我们也创建一个`demo`的顶级包，然后在`demo`包下创建`Global.java`类，类的内容如下：

```java
package demo;

import leap.web.App;

public class Global extends App {
    @Override
    protected void init() throws Throwable {
        super.init();
    }
}
```

> **注意：**这里的类名必须为`Global`并且必须是在顶级包下（`base-package`所指定的包），不能是其子包。

现在我们再在`demo`包下创建一个子包`demo.controller`，用来放控制器的类。

在这个包下我们创建一个`HomeController`类：

```java
package demo.controller;

import leap.web.view.ViewData;

public class HomeController {
    public void index(ViewData vd, String name){
        vd.put("name", name);
    }
}
```

> **说明：**leap并没有对controller所在的包做限制，只要是在`base-package`下的类即可，leap判断controller的标准是类名以`Controller`为后缀的非抽象类，因此我们不需要做其他特殊的配置。

## 创建应用根页面

现在我们可以创建项目的根页面了，在`webapp/WEB-INF`目录下创建`views`文件夹，并在`views`文件夹下创建`index.html`文件：

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>leap</title>
</head>
<body>
    hello ${name}!
</body>
</html>
```

## 配置web.xml

最后，我们需要配置leap接管所有的http请求，在`WEB-INF`目录的web.xml(如果没有请自行创建)中，配置leap的拦截器:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://java.sun.com/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
    version="3.0">
    <display-name>leap demo</display-name>
    <filter>
        <filter-name>app-filter</filter-name>
        <filter-class>leap.web.AppFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>app-filter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping> 
</web-app>
```

## 启动测试

到这里我们全部的配置就完成了，接下来只要把应用部署到tomcat即可，访问`http://localhost:8080/demo?name=leap`（这里可以（换成你实际的部署路径），结果页面打印了：

```
hello leap!
```

这里的leap是参数`name`指定的。

最终整个工程的目录结构如下：

![完整的目录结构](/assets/demo-web3.png)

pom.xml如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>leap.guid</groupId>
    <artifactId>demo-web</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>war</packaging>
    <repositories>
        <!-- leap快照资源库 -->
        <repository>
            <id>leap-snapshots</id>
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
    <dependencies>
        <dependency>
            <groupId>org.leapframework</groupId>
            <artifactId>leap</artifactId>
            <version>0.4.0b-SNAPSHOT</version>
            <type>pom</type>
        </dependency>
    </dependencies></project>
```