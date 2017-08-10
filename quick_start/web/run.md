# 运行测试

在命令行启动 Jetty Web Server ：

```
**[terminal]
mvn jetty:run
```

<p/>
浏览器访问 `http://localhost:8080/user/get_hello_user` ，将会返回 JSON 对象：

```json
{
    id: "hello",
    name: "hello world"
}
```

到这里我们大概了解了一个简单的 Web 工程使用 Leap 是怎么开发的，想了解更多细节，请继续阅读后面的文档。