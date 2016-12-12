htpl 指令一般以 html 注释的形式使用，形如 

```html
<!-- #directive -->
```

当指令接受参数时，可以以这种形式传入: 

```html
<!-- #directive param1=value1 param2=value2 -->
```

如果指令只需要一个参数时，可以省略参数名: 

```html
<!-- #directive value -->
```

部分指令还可以以 html 标签属性的方式使用，效果与以注释的方式基本相同。

# 指令索引

指令 | 作用
--- | ---
[#set](#set) | 用于定义页面局部变量
[@](#at) | 定义页面全局变量
[#load](#load) | 加载 json 或 yaml 文件内容
[#if](#if) | 根据条件决定是否渲染内容
[#for](#for) | 循环遍历
[#break](#break) | 提前跳出循环
[#empty](#empty) | 循环对象为空时展示的内容
[#include](#include-and-fragment) | 引用其它模板页面
[#fragment](#include-and-fragment) | 定义模板片段
[#render-fragment](#layout) | 渲染指定片段
[ht-layout](#layout) | 指定 layout 模板文件
[#bundle](#bundle) | 合并静态资源文件
[/*](#comment) | 多行注释
[//](#comment)| 单行注释

# 变量设置

## `#set` <a id="set"></a>

用于设置局部变量，可以用 `#endset` 闭合:

```html
<!-- #set cond = true -->
<div id="a" ht-if="cond">Cond in a is true.</div>
<!-- #endset -->
<div id="b" ht-if="cond">Cond in b is true.</div>
```

会输出为 

```html
<div id="a">Cond in a is true.</div>
```

设置多个变量可以用空格分隔，例如 `<!-- #set a=1 b="text" -->`。

## `@` <a id="at"></a>

用于设置页面变量，页面变量可以在任何地方通过 `${page}` 访问，例如:

```html
<!-- @ a=b -->
${page.a}
```

**NOTE**: 页面变量中有一个特殊的变量 "layout" 会被 htpl 处理，即将其指定的值作为当前页面的 "layout", 详见 [layout](#layout)。

`@` 与 `#set` 的区别参见下表

|| `@`指令 |`#set`指令|
|:----|:----|:---|
|范围|定义页面全局的变量|定于页面局部的变量|
|定义对象|只能定义字符串变量|可以定义对象变量，也可以调用对象方法|
|语法|不需要结束指令|可以使用`#endset`指令结束|

## `#load` <a id="load"></a>

使用 `#load` 指令可以将 json 或 yaml 格式的文件加载到页面变量中。例如有文件目录结构如下:

```
- views
  |- view1.html
  |- data.json
```

则在 `view1.html` 中可以通过

```html
<!-- #load data.json -->
```

将 data.json 里的内容加载到页面变量中，这之后就可以在页面的任意地方通过 `${data}` 访问 data.json 里的内容。

支持参数 "file"(必须)，指定要加载的文件路径，和参数 "variable"(非必须)，将加载的内容保存到指定的变量名中。例如:

```
<!-- #load file=data.json variable=viewData -->
${viewData.content}
```

**注意**: 目前只支持 json 和 yaml 格式的文件，同时要求文件名以 ".json" 或 ".yaml" 结尾。

# 逻辑控制指令

## `#if`, `#elseif`, `#else` <a id="if"></a>

根据条件决定是否渲染内容，例如:

```html
<!-- #if(a) -->
<p>a is true</p>
<!-- #elseif(b) -->
<p>b is true</p>
<!-- #else -->
<p>neither a nor b is true</p>
<!-- #endif -->
```

## `#for`, `#break`, `#empty` <a id="for"></a>

`#for` 指令用于遍历某个对象，例如:

```html
<!-- #for(user : users) -->
    <div> username : ${user.name}</div>
    <div> nickname : ${user.nickname}</div>
<!-- #endfor -->
```

当遍历的对象是一个数字 n 时，表示循环 n 次:

```html
<!-- #for(i: 2) -->
${i}
<!-- #endfor -->
```

将输出为 `12`。

<a id="break"></a> 可以通过 `#break` 提前退出循环，例如:

```html
<!-- #for(i: 100) -->
${i}
<!-- #break i > 50 -->
<!-- #endfor -->
```

`#for` 内部定义了 `loop` 对象，可以通过该对象访问当前循环的信息，例如:

```html
<!-- #for(user : users) -->
<div> index : ${loop.index}</div>
<div> username : ${user.name}</div>
<!-- #endfor -->
```

**注意**: `${loop.index}` 从 1 开始。

<a id="empty"></a> `#empty` 用于在循环的对象为空时显示内容:

```html
<!-- #for (user: users) -->
${user.name}
<!-- #empty -->
No users.
<!-- #endfor -->
```

假设 users 未定义，则上面将输出为 `No users`。

# 模板控制指令

## `#include`, `#fragment` <a id="include-and-fragment"></a>

使用 `#fragment` 指令定义页面的一个片段，定义了 fragment 之后，其它页面可以只引用当前页面的一个片段，而不需要引用一整个页面，让模板页面之前的引用更细致。例如，`view1.html` 内容如下:

```html
Text in view1.
<!-- #fragment f1 -->
Text in f1.
<!-- #endfragment -->
```

`#fragment` 需要一个 "name" 参数作为标志; `#include` 需要一个 "path" 参数指明要引入的文件路径，可以在文件名后面附加 "#frag" 来指定要引入的片段。例如:

同目录的 `view2.html` 内容为 

```html
<!-- #include view1 -->
```

将输出 

```
Text in view1. Text in f1
```

而

```html
<!-- #include view1#f1 -->
```

则会输出 

```
Text in f1
```

## `#render-fragment`, `ht-layout` <a id="layout"></a>

Web 应用的不同页面间经常会需要共享共同的页面布局 layout，在 htpl 中可以使用 `ht-layout` 配合 `#render-fragment` 实现 layout。

例如，`layout.html` 有如下内容:

```html
<div>
    <!-- #render-fragment content -->
    <p>Common footnote for many pages.</p>
</div>
```

则 `article1.html` 可以像这样使用上面的 layout:

```html
<div ht-layout="layout.html">
    <!-- #fragment content-->
    Content of article1.
    <!-- #endfragment -->
</div>
```

最终 `article1.html` 会转化为

```html
<div>
    Content of article1. 
    <p>Common footnote for many pages.</p>
</div>
```

即作为 layout 的页面 `layout.html` 使用 `#render-fragment content` 为其它页面预留了一个占位符，`article1.html` 页面通过 `ht-layout` 属性指定 layout 页面，并使用 `#fragment` 指令为 layout 页面提供真正要显示的内容。

# 其它指令

## `#bundle` <a id="bundle"></a>

浏览器会限制每个 html 页面的最大并发请求数，因此页面经常会需要减少静态资源的请求数，以避免超过最大并发请求数，导致多个资源排队等待。常用的方法包括将多个 css 或 js 文件合并为一个文件。

使用 `#bundle` 指令可以将多个静态资源文件合并成一个，例如:

```html
<!-- #bundle path="/assets/css/bundle.css"-->
<link type="text/css" rel="stylesheet" href="/assets/css/css1.css"/> 
<link type="text/css" rel="stylesheet" href="/assets/css/css2.css"/>
<!-- #endbundle -->
```

将转化为

```html
<link type="text/css" rel="stylesheet" href="/assets/css/bundle-3416802591.css" />
```

对于 js 文件资源的 bundle 处理，与上类似。

需要注意的是，bundle 里各文件的路径与最终合并生成的文件路径有可能会不一致，htpl 已经对 css bundle 进行了处理，例如

```html
<!-- #bundle path="/assets/css/bundle.css"-->
<link type="text/css" rel="stylesheet" href="/static/css/css1.css"/> 
<link type="text/css" rel="stylesheet" href="/assets/css/1/css2.css"/>
<!-- #endbundle -->
```

`/assets/css/1/css2.css` 里的 url 相对路径会根据最终生成 bundle 的路径进行重写，例如

```css
a {
    background-image : url(../../image/a.png)
}
```

会被处理为


```css
a {
    background-image : url(../image/a.png)
}
```

但是 js bundle 里的 url 不会被处理，因此在使用 `#bundle` 指令合并 js 文件时，要考虑相对路径的改变可能会导致 js 文件里的内容运行出错。

## 注释指令 `//`,`/*`,`*/` <a id="comment"></a>

htpl 级别的单行及多行注释如下:

```html
<!-- // 单行注释 -->
<!-- /* -->
    多行注释
<!-- */ -->
```

上述的输出将为空。
