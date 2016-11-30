# API文档生成

leap可以根据代码自动按照swagger的标准生成api文档，当我们配置好一个api之后，leap会自动在该api的`base-path`下增加一个`/swagger.json`的接口，访问这个接口即可看到生成的swagger文档，路由表示例如下：

```
METHOD PATH                   ACTION                                       DEFAULT VIEW
------ ---------------------- -------------------------------------------- ------------------------------
GET    /demo-api/swagger.json SwaggerProcessor$$Lambda$93/331422133.handle (none)
```

在浏览器中访问这个url，可以得到如下json:

```
http://{host}:{}/{}
```

```javascript
{
    swagger: "2.0",
    info: {
        title: "demo-api",
        description: "",
        version: "1.0"
    },
    host: "localhost:8080",
    basePath: "/demo/demo-api",
    consumes: ["application/json"],
    produces: ["application/json"],
    paths: {},
    definitions: {}
} 
```

这个时候`paths`为空，表示没有任何接口，我们可以打开swagger ui的[官方示例页面](http://petstore.swagger.io/)，并把leap自动生成swagger的url传入示例应用，可以看到如下界面：

![自动生成的api文档](/assets/swagger.json.png)

这个时候还没有接口，所以文档是空的。