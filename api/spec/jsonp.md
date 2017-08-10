# JSONP 回调

API 需要支持 JSONP 回调，调用端可以发送一个 `callback` 请求参数给任意一个 API 操作，返回的结果将会被包装在 `callback` 参数传入的 JSON 函数中。

下面是 Github API 的一个示例：

```
**[terminal]
curl https://api.github.com?callback=foo

/**/foo({
  "meta": {
    "status": 200
  },
  "data": {
    // the data
  }
})
```