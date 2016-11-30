# 名词概念

## operation

`operation`指的是http请求的方法，如`get`、`post`等。


## path

这里的`path`指的是一个http请求的uri，如：`/api`，每一个`path`都包含多个`operation`。

## API

API的概念和我们常说的API有一点差别，这里的API实际上是一组`path`和`operation`的统称。


我们经常说的rest api，实际上指的是一个特定的`path`和一个特定的`operation`，如：

```
GET /api
```

而这里的API，实际上指的是一组`path`和`operation`的整体，是一个完整的微服务模块，如用户管理的API，实际上包含多个rest api：

```
GET    /user
POST   /user
PATCH  /user
DELETE /user
```