htpl 会对带有特殊前缀的 html 元素属性进行处理，例如

```html
<div ht-if="true">It is true</div>
```

这里的前缀即为 `ht`，而前缀与属性名 `if` 之间的分隔符既可以是 `-` 也可以是 `:`。



# `ht-`属性

在[指令](directive.md)里提到的指令，部分可以写成`ht-`属性的形式:

指令 | 对应属性 | 例子
--- | --- | ---
#set | ht-set | `<div ht-set="a:b"></div>`
#if | ht-if | `<div ht-if="true"></div>`
#for | ht-for | `<div ht-for"node: nodes"></div>`
#include | ht-include | `<div ht-include="include#fragment"></div>`
#fragment | ht-fragment | `<div ht-fragment="content"></div>`
#render-fragment | ht-render-fragment | `<div ht-render-fragment="content"></div>`

另外也有一些指令只能以属性的方式使用:

## `ht-class-append`

使用 `ht-class-append` 可以动态地为 html 标签附加 class。 如:

```html
<li class="child" ht-class-append="${true ? 'x' : ''}">a</li>
```

将转化为

```html
<li class="child x">a</li>
```

## `ht-xxx-if`

该属性可视为原有的 `xxx` 属性的开关，如:

```html
<div class="a" ht-class-if="i == 0"></div>
```

将转化为

```html
<!-- i 等于 0 时 -->
<div class="a"></div>

<!-- i 不等于 0 时 -->
<div></div>
```

一些 html 标签里会存在表示布尔值的属性，如 `<input>` 的 `disabled` 属性。这些属性的名字只要出现，就表示布尔真值，它的属性值基本无关紧要。

使用 `ht-xxx-if` 可以方便地处理这些属性，例如:

```html
<option ht-selected-if="true">1</option>`
```

将转化为

```html
<option selected="selected">1</option>
```

注意这里 `selected` 的属性值与属性名一样，这是因为 htpl 默认会对所有表示布尔值的标签属性进行标准化，将空的属性值替换为属性名，例如:

```html
<input disabled=""/>
<input disable>
```

都会被转化为

```html
<input disabled="disabled">
```

但是非空值的属性例如

```html
<input disabled="foo">
```

则不会被处理。目前会被 htpl 处理的布尔属性包括:

`compact`,`checked`,`declare`,`readonly`,`disabled`,`selected`,`defer`,`ismap`,`nohref`,`noshade`,`nowrap`,`multiple`,`noresize`.

除了使用 `ht-xxx-if` 来控制布尔属性，也可以直接用表达式控制，例如 

```html
<input disabled="${true}">
```

## `ht-inline-el`

`ht-inline-el` 用以控制 htpl 跳脱内容，即让 htpl 不解析里面的内容，例如:

```html
<div ht-inline-el=false>${v}</div>
```

将转化为

```html
<div>${v}</div>
```

# `assets-`属性

Leap 有 "assets" 这一概念，所有 assets 下的资源文件都会被特殊处理，例如会根据文件内容自动生成哈希后缀，同样的路径 `/assets/js/bundle.js` 会因为内容的改变而生成不同的 url ，例如 `/assets/js/bundle-1137900297.js`、`/assets/js/bundle-2247911288.js`，这样就不用担心客户端缓存旧的资源文件了。

htpl 会对页面与 assets 相关的 url 进行处理，开发者只需要告诉 htpl 哪些资源属于 assets，htpl 就会将其指向正确的 url，例如

```html
<script type="text/javascript" assets-src="/js/hello.js"></script>
```

将转化为

```html
<script type="text/javascript" src="/assets/js/hello-2247911288.js"></script>
```

除了用 `assets-` 属性显式地声明 assets 资源，以 assets 路径开头的 url 也会默认被视为 assets 资源处理:

```html
<!-- 与 assets-src="/js/hello.js" 效果一致 -->
<script type="text/javascript" src="/assets/js/hello.js"></script>
```

assets 路径是需要配置的，上例中即将 assets 路径配置为 `/assets`。(TODO: 描述 assets 路径的配置方法)