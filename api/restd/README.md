# 生成接口

在前面章节我们知道了使用 `ModelController` 可以为每个 API 实体简单地创建可供实际项目使用的 API 接口。

但是我们也可以发现当需要开放 API 接口的实体很多的时候，其实每个 API Controller 的内容都是大同小异的。尤其是当很多实体除了开放增删改查之外并没有其他接口时，每个路由方法除了实体名不一样之外其余都是一样的。

既然如此，这部分代码可以省略不写，只需要通过配置的方式，就能让 Leap 为我们生成接口。

这个功能我们简称为 Restd ，即 RESTful Data 的意思。