# 表达式

## ElementExpression

ElementExpression 是 htpl 的基础表达式，可以在页面任意地方使用，例如

```html
<!-- 假设已经设置了变量 a = "text" -->
<div>${a}</div>
<!-- 将转化为 -->
<div>text</div>
<!-- 如果变量 a 未定义，则整个表达式会被忽略，即转化为 -->
<div></div>
```

如果希望 htpl 不解析 `${}` 内的内容，比如使用 jquery 模版的时候，可以通过以下几种方式进行跳脱(escape):

* 前缀 `\` 符: `<div>\${v}</div>` 将转化为 `<div>${v}</div>`。
* 通过 `ht-inline-el` 参数控制: `<div ht-inline-el=false>${v}</div>` 将转化为 `<div>${v}</div>`

同时，为了防止脚本注入攻击，htpl 默认会对表达式的内容进行跳脱，可以通过传入 escape 参数的形式控制是否进行跳脱，例如:

```html
<!-- 假设已经设置了变量 a = "<" -->
${a}
<!-- 将转化为 -->
&lt;

${a;escape=false}
<!-- 将转化为 -->
<
```

## MessageExpression

形如`#{message}`, MessageExpression 用于 Leap 应用的多语言国际化，在配置好各语言的信息源之后，可以在页面通过 `#{message}` 的形式根据不同语言显示不同的内容。(TODO: 还未明确怎么配置多语言信息源)。

## UrlExpression

Java Web 应用在部署时会有 "Context Path" 的概念，因此应用应避免在页面中写死 url 的值，而是通过 UrlExpression 处理，例如:

```html
<!-- 假设 context path 分配为 "/context" -->
<a href="@{/a}"></a>
<!-- 将转化为 -->
<a href="/context/a"></a>
```

UrlExpression 根据表达式中的第一个字符判断该 url 相对的路径:

首字符 | 相对路径 | 例子
--- | --- | ---
"~" 或 "/" | 相对 context path | `@{~/a}` -> `/context/a`
"^" | 相对服务器根路径 | `@{^/a}` -> `/a`
"//" | 不作处理 | `@{//a.com}` -> `//a.com`

**注意**: htpl 引擎除了会对 UrlExpression 进行上述规则转换，也会对大部分 html 标签的值为 url 的属性进行转化，例如:

```html
<a href="/a"></a>
<!-- 将转化为 -->
<a href="/context/a"></a>
```

目前会默认进行转化的 html 标签属性有:

属性 | 所属标签
--- | ---
`src` | `<audio>, <embed>, <iframe>, <img>, <input>, <script>, <source>, <track>, <video>`
`href` | `<a>, <area>, <base>, <link>`
`action` | `<form>`