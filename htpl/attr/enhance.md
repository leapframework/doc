# 增强语法属性

这部分是基于注释的渲染语法没有提供的功能。

主要是由于这部分功能的控制渲染 Leap 认为使用属性来完成更加方便和明确。

## ht-class-append 属性

使用 `ht-class-append` 可以动态地为 HTML 元素**附加** `class` 属性值。

注意是附加而不是替换，原有 `class` 属性的值会被保留。代码如下：

```
<li class="child" ht-class-append="${true ? 'x' : ''}">a</li>
```

以上代码渲染后是这样的：

```
<li class="child x">a</li>
```

## ht-xxx-if 属性

这里的 `xxx` 代指任意名称的属性，该属性可视为原有的 `xxx` 属性的开关，如：

```
<div class="a" ht-class-if="i == 0"></div>
```

如果变量 `i` 的值为 0，则渲染结果为：

```
<div class="a"></div>
```

如果不为 0，则为：

```
<div></div>
```

一些 HTML 元素里会存在表示布尔值的属性，如 `<input>` 的 `disabled` 属性。

这些属性的名字只要出现，就表示布尔真值，它的属性值基本无关紧要。

使用 `ht-xxx-if` 可以方便地处理这些属性，例如:

```
<option ht-selected-if="true">1</option>`
```

将渲染为：

```
<option selected="selected">1</option>
```

注意这里 `selected` 的属性值与属性名一样，这是因为 HTPL 默认会对所有表示布尔值的元素属性进行标准化，将空的属性值替换为属性名，例如:

```
<input disabled=""/>
<input disable>
```

都会被渲染为：

```
<input disabled="disabled">
```

但是非空值的属性例如

```
<input disabled="foo">
```

则不会被处理。

目前会被 HTPL 处理的布尔属性包括: `compact` ，`checked` ，`declare` ，`readonly` ，`disabled` ，`selected` ，`defer` ，`ismap` ，`nohref` ，`noshade` ，`nowrap` ，`multiple` ，`noresize` 。

以上属性除了使用 `ht-xxx-if` 来控制布尔属性，也可以直接用表达式控制，例如

```
<input disabled="${true}">
```

## ht-inline-el 属性

`ht-inline-el` 属性用于控制 HTPL 不解析元素内部的内容，例如:

```
<div ht-inline-el=false>${v}</div>
```

将渲染为：

```
<div>${v}</div>
```