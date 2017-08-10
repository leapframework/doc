# 快速开始

此章节从创建工程开始到运行测试，快速介绍了如何使用 Leap 开发一个简单的 Web 应用。

## 环境准备

**在继续之前，我们需要准备好以下环境：**
* 至少 [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) 或之后版本
* 至少 [Maven 3.0](https://maven.apache.org/download.cgi)
* 一个熟悉的IDE，推荐使用 [IntelliJ IDEA](https://www.jetbrains.com/idea/download)

> IntelliJ IDEA 的免费版本可以很好的完成此文档中的所有内容

## 快速体验

在继续阅读前，还可以直接下载已经开发完成的工程源码进行快速体验。

* 下载[源码](https://github.com/leap-guides/gs-leap/archive/master.zip)并解压，或使用 git 进行 clone ：

```
**[terminal]
git clone https://github.com/leap-guides/gs-leap.git
```
   
* 进入```gs-leap```目录：

```
**[terminal]
cd gs-leap
```

* 启动 web server：
    
```
**[terminal]
mvn jetty:run
```

* 浏览器访问 ```http://localhost:8080/user/get_hello_user``` 可看到返回一个 JSON 对象：

```json
{
    id: "hello",
    name: "hello world"
}
```