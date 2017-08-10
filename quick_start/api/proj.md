# 创建工程

Leap 提供了 maven archetype 用于快速创建工程结构，当然我们也可以通过 IDE 直接进行结构创建。

## 通过 maven archetype 创建

在命令行输入：

```
**[terminal]
mvn archetype:generate \
       -DgroupId=hello \
       -DartifactId=users \
       -DarchetypeGroupId=org.leapframework \
       -DarchetypeArtifactId=archetype-dataapi \
       -DarchetypeVersion=0.6.2b \
       -DinteractiveMode=false \
       -DarchetypeCatalog=internal \
       -DarchetypeRepository=http://maven.aliyun.com/nexus/content/groups/public/
```
            
命令执行完成后会在当前目录下产生一个 `users` 的工程子目录。

## 通过 IDE 创建

我们可以直接使用 IDE 创建一个符合 maven 标准的 web 工程，创建后目录结构如下：

> 注：如果不清楚如何通过 IDE 创建标准的 maven web 工程请先自行了解。

```
users
|----src
|    |----main
|    |    |----java
|    |    |    |----hello
|    |    |----resources
|    |    |    |----conf
|    |              |----beans.xml
|    |              |----config.xml
|    |    |----webapp
|    |         |----WEB-INF
|    |              |----web.xml
|    |----test
|         |----java
|         |----resources
|----pom.xml
```


