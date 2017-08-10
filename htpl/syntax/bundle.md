# 合并资源

## 问题

我们知道，浏览器会限制每个页面的最大并发请求数，当需要加载的静态资源过多时，各个资源就需要排队等待下载。

而且在需要加载的静态资源过多时，多个请求其实对于客户端或者服务端来说都多了一些额外的连接开销。

解决这个问题的方法有多种，我们这里提供的一种就是将多个资源文件合并为一个文件，达到减少请求数的目的。

## 使用 #bundle

常见的需要处理的静态资源就是 JS 脚本以及 CSS 样式表。

我们可以使用 `#bundle` 和 `#endbundle` 包裹我们需要合并的静态资源，并在 `#bundle` 中指定 `path` 变量为合并后的新文件路径。

> 注意 JS 脚本不能和 CSS 样式表混合合并。

示例如下：

```
<!-- #bundle path="/assets/css/bundle.css"-->
<link type="text/css" rel="stylesheet" href="/assets/css/css1.css"/>
<link type="text/css" rel="stylesheet" href="/assets/css/css2.css"/>
<!-- #endbundle -->
```

对于脚本文件的合并处理也是类似，这里就不多做示例了。

## 注意事项

对于 CSS  文件可能使用相对路径引用其它 CSS 文件的情况，HTPL 也做了考虑，会自动做处理。

例如：

```
<!-- #bundle path="/assets/css/bundle.css"-->
<link type="text/css" rel="stylesheet" href="/static/css/css1.css"/>
<link type="text/css" rel="stylesheet" href="/assets/css/1/css2.css"/>
<!-- #endbundle -->
```

`/assets/css/1/css2.css` 里的 url 相对路径会根据最终生成 bundle 的路径进行重写，例如

```
a {
    background-image : url(../../image/a.png)
}
```
会被处理为

```
a {
    background-image : url(../image/a.png)
}
```

但是要**注意 JS 脚本中的 URL 不会自动处理**，这一点需要开发人员在使用资源合并功能时多加考虑。