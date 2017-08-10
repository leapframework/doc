# 安全认证

API 需要支持四种认证类型：

* 无需认证（ No Authentication ）
* 客户端认证（ Client Only Authentication ）
* 用户认证（ User Only Authentication ）
* 客户端+用户双认证（ Client + User Authentication ）

后三种认证需要支持 [OAuth2.0](https://oauth.net/2/) [Bearer Token](http://self-issued.info/docs/draft-ietf-oauth-v2-bearer.html) 方式。

例如：

```
**[terminal]
curl -H "Authorization: Bearer OAUTH-TOKEN" https://api.example.com/hello
```

> 如何获取 `OAUTH-TOKEN` 不在此文档的描述范围。

**其中 `OAUTH-TOKEN` 支持两个格式：**
* 访问令牌（ Access Token ）
* JWT令牌（ JSON Web Token ）

两个格式的细节请看开发指南中的详细内容。