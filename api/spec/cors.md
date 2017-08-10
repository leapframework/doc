# 跨域资源共享

[跨域资源共享](https://www.w3.org/TR/cors/)（ Cross-Origin Resource Sharing ）可以让调用者在浏览器中访问非当前域名的 API ，对于 API 来说需要支持此功能。

下面是 Github API 的一个示例请求（假设当前浏览器地址是 `http://example.com` ）：

```
**[terminal]
curl -i https://api.github.com -H "Origin: http://example.com"
HTTP/1.1 302 Found
Access-Control-Allow-Origin: *
Access-Control-Expose-Headers: ETag, Link, X-GitHub-OTP, X-RateLimit-Limit, X-RateLimit-Remaining, X-RateLimit-Reset, X-OAuth-Scopes, X-Accepted-OAuth-Scopes, X-Poll-Interval
```