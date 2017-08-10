# API配置

## 配置

创建 `src/main/resource/conf/apis.xml` 文件，最简的配置如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<apis xmlns="http://www.leapframework.org/schema/webapi">
    <api name="api" base-path="/">
    </api>
</apis>
```

这里只需要配置 `<api>` 的 `name` 和 `base-path` ，一般情况下固定为以上值就可以。注意 `base-path` 必须为根目录。

> **注意**：
> 1. 这里的配置与前面快速搭建 API 工程开始中的代码配置作用相同。
> 2. 不要在 `Global.java` 代码和 XML 两个地方同时配置。

## 默认开启的功能

当工程开启 API 模块时，默认也开启了以下功能支持：

- 跨域资源共享：参见 API 规范中的[跨域资源共享](/api/spec/cors.md)。

- JSONP 回调：参见 API 规范中的 [JSONP 回调](/api/spec/jsonp.md)。

- 自动生成接口文档。