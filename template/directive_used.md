# 指令应用

htpl的指令功能非常强大,通过指令的组合可以非常简单地实现其他模板引擎难以实现的功能.  
这里我们以不限层级的菜单为例,说明htpl指令的实际应用.

对于不限层级的菜单,无法通过简单的`#if`指令来判断需要几层,因此必须使用递归的方式渲染数据,我们假设菜单的列表对象为`menus`,模板代码如下:
```html
<ul class="root">
<!-- #endif -->
  <li ht-fragment="tree" ht-for="menu : menus">
  	<span>${menu.name}</span>
  	<!-- #if(menu.children) -->
  	<ul>
  	  <!-- #set menus=org.children -->
      <!--#render-fragment tree-->
      <!-- #endset -->
    </ul>
    <!-- #endif -->
  </li>
</ul>
```
这里我们可以看到,其实定义了`<li ht-fragment="tree" ht-for="menu : menus"></li>`为模板片段,然后递归这个模板片段实现了.一般的模板引擎无法实现递归,这也是htpl的众多优势之一.