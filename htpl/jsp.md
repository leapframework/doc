htpl 主要通过 "引用"(include) 的方式与 JSP 混合使用。

# 在 htpl 模板页里引用 JSP 页面

假设有文件结构如下:

```
|- views
    |- htpl-1.html
    |- jsp-1.jsp
```

`jsp-1.jsp` 有如下内容:

```jsp
<%
    out.write("Content in jsp.");
%>
```

`htpl-1.html` 可以通过 `include` 指令引用 `jsp-1.jsp` 的内容，如:

```html
<h1><!-- #include inc1.jsp --></h1>
<div>Content in htpl.</div>
```

将输出:

```html
<h1>Content in jsp.</h1>
<div>Content in htpl.</div>
```

JSP 里对 `request` 设置的 `attribute` 也可以在引入后直接访问，例如将 `jsp-1.jsp` 的内容改为:

```jsp
<%
    request.setAttribute("jsp_var", "Attribute of request in jsp.");
%>
```

`htpl-1.html` 可以直接通过表达式访问变量 `jsp_var`，如:

```html
<h1><!-- #include inc1.jsp -->${jsp_var}</h1>
<div>Content in htpl.</div>
```

会输出

```html
<h1>Attribute of request in jsp.</h1>
<div>Content in htpl.</div>
```

# 在 JSP 页面里引用 htpl 模板

要在 JSP 页面里引用 htpl 页面，应先声明 htpl 的标签库:

```jsp
<%@ taglib prefix="htpl" uri="/WEB-INF/htpl.tld"%>
```

目前 htpl 提供 `include` 标签以引入 htpl 页面，例如，有 `jsp-2.jsp` 内容如下:

```jsp
<%@ taglib prefix="htpl" uri="/WEB-INF/htpl.tld"%>
<%
    request.setAttribute("message", "Hello world!");
%>
<htpl:include file="htpl-2.html"/>
```

同目录下的 `htpl-2.html` 的内容为

```html
htpl says: ${message}
```

最终访问 `jsp-2.jsp` 将输出:

```html
htpl says: Hello world!
```

# htpl 自动引入 jsp

htpl 会自动为每个 htpl 文件引入同目录下名字相同且以 "~" 前缀的 JSP 文件，例如有文件如下:

```
|- views
    |- index.html
    |- ~index.jsp
```

`index.html` 内容为:

```html
${message}
```

`~index.jsp` 内容为

```jsp
<%
    request.setAttribute("message", "Messages from ~index.jsp.");
%>
```

htpl 会自动为 `index.html` 引入 `~index.jsp`，因此访问 `index.html` 将输出

```
Messages from ~index.jsp.
```
