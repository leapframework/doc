# 快速开始

此章节从创建工程开始到运行测试，快速介绍了如何使用 Leap 开发一个简单的实现数据增删改查的 RESTful 数据 API 工程。

> 不需要进行数据 API 开发的读者可以跳过这个章节。只看前面的快速搭建 Web 工程的内容即可，之后就可以直接看后面的详细文档说明了。

完成此章节后，我们将实现一个可以对数据库中 `users` 表进行增删改查的 API ，该 API 具有以下 HTTP 操作：

Method   | Path                      | Description
------- | --------------- | -------
GET        | /user                    | 查询所有用户
POST      | /user                    | 创建新的用户
PATCH    | /user/{id}              | 更新某个用户
DELETE  | /user/{id}              | 删除某个用户
GET        | /user/{id}              | 查询某个用户

如浏览器访问 `http://localhost:8080/users` 将会返回 JSON 数组：

```json
[{"id":"i6wm@hoKf","name":"User1"}]
```

> 上面的 JSON 数据表示返回了一条用户记录

## 环境准备

**在继续之前，我们需要准备好以下环境：**
* 至少 [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) 或之后版本
* 至少 [Maven 3.0](https://maven.apache.org/download.cgi)
* 一个熟悉的IDE，推荐使用 [IntelliJ IDEA](https://www.jetbrains.com/idea/download)

> IntelliJ IDEA 的免费版本可以很好的完成此文档中的所有内容

## 快速体验

在继续阅读前，还可以直接下载已经开发完成的工程源码进行快速体验。

* [下载](https://github.com/leap-guides/gs-webapi/archive/master.zip)源码并解压，或使用 git 进行 clone ：

```
**[terminal]
git clone https://github.com/leap-guides/gs-webapi.git
```
   
* 进入 `gs-webapi/users` 目录：

```
**[terminal]
cd gs-webapi/users
```

* 启动 web server ：
    
```
**[terminal]
mvn jetty:run
```

* 浏览器访问 `http://localhost:8080/users` 可看到返回一个空的 JSON 数组：

```json
[]
```