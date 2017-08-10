# 等效语法属性

之前我们在 HTPL 语法一章中介绍的基于注释的渲染语法，大部分都支持改写成属性的形式。

这两种写法都是等效的。

目前支持两种写法的语法如下表所示：

| 功能 |注释语法 | 对应属性名称 | 示例
| ---- | ---- | ----| ----
| 设置变量 |#set | ht-set | `<div ht-set="a:b"></div>`
| 条件判断 |#if | ht-if | `<div ht-if="true"></div>`
| 循环处理 |#for | ht-for |`<div ht-for="node: nodes"></div>`
| 引入页面 |#include | ht-include | `<div ht-include="include#fragment"></div>`
| 定义片段 |#fragment | ht-fragment | `<div ht-fragment="content"></div>`
| 布局占位 |#render-fragment | ht-render-fragment | `<div ht-render-fragment="content"></div>`

> 需要注意的是，属性形式的写法就不需要写闭合标签了，因为 HTML 元素本身就是结构化自带闭合标签的。