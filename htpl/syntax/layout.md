# 页面布局

在 Web 页面中，页面布局，即 layout 是一个很重要的概念。

很多页面都会共用一些页面资源比如引用脚本、页头、页脚、导航等等，如果每个页面都需要重复地声明这些，不仅工作量繁重，而且维护更新也很困难。

因此定义页面布局供页面使用就可以避免这些问题。

## 定义布局

定义布局很简单，只需要在页面中使用 `#render-fragment` 标记主页面内容位置即可。

比如下面我们在视图根目录下定义一个 `layout.html` 文件：

```
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
</head>
<body>
    <div class="header">some common header</div>
    <div class="body">
        <!-- #render-fragment content -->
    </div>
    <div class="footer">some common footer</div>
</body>
</html>
```

注意 `#render-fragment` 后面的 `content` 名称不是固定的，你也可以叫其他名字，但需要与后面主页面使用时一一对应。

## 使用布局

定义完布局，我们就可以在具体的页面中使用它。比如现在有一个 `hello.html` 文件，我们希望它使用上面定义的布局。

那么可以这样设置使用：

```
<!-- @ layout = layout -->
<!-- #fragment content-->
    <p>Hello World</p>
<!-- #endfragment -->
```

首先设置页面变量 layout 的值指向我们的布局文件，然后定义名为 `content` 的页面片段。

当我们访问并渲染 `hello.html` 文件的时候，页面片段中的内容将会被迁入布局文件中并返回。

> 页面变量的设置可以参考设置变量那一节的内容。

最终 `hello.html` 渲染结果如下：

```
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
</head>
<body>
    <div class="header">some common header</div>
    <div class="body">
        <p>Hello World</p>
    </div>
    <div class="footer">some common footer</div>
</body>
</html>
```

## 多处填充

值得一提的是，在布局页面中，可以定义不只一个 `#render-fragment`。

这也是之前提到的 `#render-fragment` 需要指定一个名称的原因，这样在具体页面中就可以区分多个块分别进行内容填充。

当布局页面中有多个 `#render-fragment` ，具体页面可以不定义对应数量的 `#fragment`，Leap 会自动填充空白内容，不会报错。

## 默认布局

Leap 暂时不提供默认布局的设置，需要开发人员的页面上使用 `@layout=xxx` 语法明确指定当前页面要使用的布局，不指定布局则按照普通页面渲染。

这样做的原因主要有几个：

- 明确每个页面是否使用了布局以及使用了什么布局；

- 规则简单：如果提供了默认布局的设置，那必然需要提供另外一种设置去配置哪些页面不需要默认布局，配置规则就会变得很复杂。