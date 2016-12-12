Leap 作为面向 Web 开发的应用框架，提供 "htpl" 模版引擎以驱动应用的 View 视图层。不需要经过特别的配置，应用的 html 页面即可使用 htpl 提供的功能特性，而不打算使用 htpl 特性的应用也无需担心自身的 html 页面会被 htpl 错误处理，因为 htpl 的语法基本不影响正常 html 的文档结构。

Leap 应用可以从以下三个方面使用 htpl 引擎。

## Expression 表达式

作为模版引擎自然少不了表达式，htpl 的表达式主要有三种

* [ElementExpression](expression.md#elementexpression)，形如 `${variable}`，可以在 html 页面的任意地方使用，以 `variable` 变量的实际值替换 `${variable}`。 
* [MessageExpression](expression.md#messageexpression)，形如 `#{message}`，Leap 应用的多语言国际化可以在配好各语言的信息源之后，在页面通过 MessageExpression 实现。 
* [UrlExpression](expression.md#urlexpression)，形如 `@{~/path/to/somewhere}`，Java Web 应用都不得不考虑服务器部署时的 Context 问题，应用可以使用 UrlExpression 以避免在页面里硬编码 URL 路径，减少维护上的问题。

## Directive 指令

htpl 利用指令在页面中实现像 IfElse、For 的逻辑控制，指令一般以 html 注释的形式使用，例如:

```html
<!-- #if(true) -->
    It is true.
<!-- #endif -->
```

部分指令也提供以 html 属性使用的形式，效果相同。

## Attribute 属性

htpl 会处理带有特殊前缀的 html 元素属性，例如:

```html
<div ht-if="true">It is true</div>
```

## 与 JSP 混合使用

除了三述上方面，目前 htpl 支持以 "include" 的方式混合使用，比如在 html 里引用 jsp 文件内容，或者在 jsp 页面里引用 html 模板。形如:

```jsp
<%@taglib prefix="htpl" uri="/WEB-INF/htpl.tld"%>
<%
    int i = 0;
%>
<htpl:include file="include.html"/>
```

和

```html
<h1>Below is content from jsp.</h1>
<!-- #include inc1.jsp -->
```