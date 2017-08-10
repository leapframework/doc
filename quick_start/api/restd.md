# 使用 Restd

**Restd** 是RESTful Data API 的缩写，是 Leap 为简化数据 API 开发而提供的功能。

这一章节将快速演示如何使用 Restd 进行开发。

## 启用 Restd

在 `Global.java` 类中修改一下代码：

```java
    @Override
    protected void configure(WebConfigurator c) {
        apis.add("users", "/").enableRestd();
    }
```

加上 `enableRestd()` 的方法调用即可启用 Restd 功能。

## 创建 Group 对象

在 `src/main/java/hello/models` 包中创建 `Group.java` 类：

[import](codes/Group.java)

> `autoCreate="true"` 是 Leap ORM 模块的功能，如果表不存在将会自动创建。

完成这两步后，对 `Group` 模型对象的增删改查操作已经开发完成，和 `User` 一样：

Method   | Path                      | Description
------- | --------------- | -------
GET        | /group                    | 查询所有群组
POST      | /group                    | 创建新的群组
PATCH    | /group/{id}              | 更新某个群组
DELETE  | /group/{id}              | 删除某个群组
GET        | /group/{id}              | 查询某个群组

## 运行测试

重新启动 Jetty：

```
**[terminal]
mvn jetty:run
```

浏览器访问 `http://localhost:8080/group` ，返回空的 JSON 数组:

```json
[]
```

其他操作的测试和 `User` 一样，这里不再重复说明。

至此对于快速搭建 API 工程的介绍已经结束，如果希望对 Leap 有更进一步的了解，可以阅读后面的详细文档章节。