# 与 JSP 混合使用

虽然 Leap 提供了 HTPL 模板可以满足绝大部分开发需求，但是不排除在一些场景中需要与 JSP 混合使用的情况。

因此本节主要说明 HTPL 如何与 JSP 混合使用。

## HTPL 引用 JSP

假设现在有文件结构如下:

```
|- views
    |- htpl-1.html
    |- jsp-1.jsp
```

在 `jsp-1.jsp` 文件中有如下内容:

```
<%
    out.write("Content in jsp.");
%>
```

在 `htpl-1.html` 文件中我们可以通过 `include` 指令引用 `jsp-1.jsp` 的内容，如：

```
<h1><!-- #include inc1.jsp --></h1>
<div>Content in htpl.</div>
```

将渲染为：

```
<h1>Content in jsp.</h1>
<div>Content in htpl.</div>
```

JSP 里对 request 设置的 attribute 也可以在引入后直接访问，例如将 `jsp-1.jsp` 的内容改为：

```
<%
    request.setAttribute("jsp_var", "Attribute of request in jsp.");
%>
```

在 `htpl-1.html` 文件中可以直接通过表达式访问变量 `jsp_var`，如：

```
<h1><!-- #include inc1.jsp -->${jsp_var}</h1>
<div>Content in htpl.</div>
```

则会输出：

```
<h1>Attribute of request in jsp.</h1>
<div>Content in htpl.</div>
```

## JSP 引用 HTPL

**要在 JSP 页面里引用 HTPL 页面，应先声明 HTPL 的标签库**。

示例如下：

```
<%@ taglib prefix="htpl" uri="/WEB-INF/htpl.tld"%>
```

上面的 `htpl.tld` 文件可以在项目中新建对应文件，内容如下：

```xml
<taglib xmlns="http://java.sun.com/xml/ns/javaee"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xsi:schemaLocation="http://java.sun.com/xml/ns/javaee/web-jsptaglibrary_2_1.xsd"
		version="2.1">

	<description>Htpl JSP Tag Library</description>
	<tlib-version>0.6</tlib-version>
	<short-name>htpl</short-name>
	<uri>http://www.leapframework.org/htpl/tags</uri>

	<tag>
		<name>include</name>
		<tag-class>leap.htpl.jsp.IncludeTag</tag-class>
		<body-content>empty</body-content>
		<attribute>
			<name>file</name>
			<required>true</required>
			<rtexprvalue>true</rtexprvalue>
		</attribute>
	</tag>
</taglib>
```

可以看到目前 HTPL 提供 `include` 标签以引入 HTPL 页面。

例如，有 jsp-2.jsp 内容如下:

```
<%@ taglib prefix="htpl" uri="/WEB-INF/htpl.tld"%>
<%
    request.setAttribute("message", "Hello world!");
%>
<htpl:include file="htpl-2.html"/>
```

同目录下的 `htpl-2.html` 的内容为：

```
htpl says: ${message}
```

最终 `jsp-2.jsp` 渲染后将输出:

```
htpl says: Hello world!
```

## HTPL 自动引入 JSP

Leap 会**自动为每个 HTPL 文件引入同目录下名字相同且以 “~” 前缀的 JSP 文件**。

例如有文件如下:

```
|- views
    |- index.html
    |- ~index.jsp
```

`index.html` 内容为：

```
${message}
```

`~index.jsp` 内容为：

```
<%
    request.setAttribute("message", "Messages from ~index.jsp.");
%>
```

Leap 会自动为 `index.html` 在文件开始处引入 `~index.jsp`，因此访问 `index.html` 将输出：

```
Messages from ~index.jsp.
```
