# 模板引擎
在[MVC应用](../chapter_3/index.md)中,我们已经了解了leap的mvc规则,本章我们将详细介绍leap内置的模板引擎----htpl.  
htpl是一个高效的,给予html注释语法的模板引擎.能够最大限度的保证html页面代码不受模板引擎语法影响结构,同时提供了多种极大减轻开发人员负担的特性.
### 基础概念
* 布局：当前访问页面的结构，可以使用视图文件定义
* attribute：在html标签中添加的自定义attribute,如：ht-fragment,ht-layout
* properties：在页面中可以使用的变量，或者在页面中设置的变量，使用${page.variable}的方式获取
* 指令：在页面中使用，指定程序处理逻辑的代码，如：`#if(iserror)` `#set title=标题` 等。 

### 基础语法
#### 属性语法
```html
<div class="body container" ht-fragment="content" ht-layout="admin/layout">
这个div应用了admin/layout布局文件，并且这个div作为当前页面中的一个名称为content的片段
</div>
```
#### 指令语法
##### 注释语法

判断指令
```html
<!-- #if(empty) -->
	<div>当empty是true是，输出这段文字<div>
<!-- #endif -->
```
设置局部变量指令
        
```html
<!-- #set name='名字' -->
```
设置页面变量指令
```html
<!-- @title=管理文章 -->	
```
##### 属性语法
```html
<li class="current" ht-class-if="i == pi.current">
	<a href="/admin/posts?page=${i}">${i}</a>
</li>
```
##### 变量语法  

打印变量  
    
```html
<div>${name}<div>
```
支持国际化
```html
<div>#{名字}<div>
```
##### 引用语法
```html
<!-- #include footer -->
```
### 指令集
|指令|作用|备注|
|:----|----|:----|
|@|定义页面变量|可以写在页面的任意位置|
|#if|用于判断真假|根据条件真假决定渲染内容，需要用#endif结束|
|#for|用于循环遍历|遍历局部内置变量loop，需要用#endfor结束|
|#set|用于定义页面局部变量|只在定义的局部内有效，需要用#endset表示局部结束|
|#break|跳出循环|可以用来结束循环|
|#empty|清空遍历|未知|
|#include|引用页面|将另一个页面引用到当前页面|
|#load|加载对象|将json文件或者yaml文件中定义的对象加载到页面中|
|#fragment|定义代码片段|需要用#endfragment结束|
|#render-fragment|指定渲染片段|在指定的地方加入指定片段渲染|
|/* |多行注释| 必须与`*/`多行注释成对出现|
| // |单行注释|只能在一个注释块中使用|

- `@`指令
	`@`指令可以定义页面properties，语法有两种方式`@title=首页`或者`@title="首页"`，需要定义多个properties时可以用空格分隔：
	```
	@title=首页 @message=提示
	```
	需要注意的是，这里定义的properties全部都是字符串，并且不能用单引号`'`代替双引号`"`,这里是按照html的语法规则设计的,使用`@`指令定义页面properties之后，所有定义的properties都放在page对像中，可以用EL表达式获取这个变量：
```
${page.title}
${page.message}
```
- `#if` `#else` `#endif`指令
	真假指令用于判断真假，根据真假决定页面渲染的内容，语法如下：
```
<!-- #if(isA) -->
<p>A is true</p>
<!-- #elseif(isB)  -->
<p>B is true</p>
<!-- #else -->
<p>Both A and B is false</p>
<!-- #endif -->
```
相当于
```html
if(isA){
  <p>A is true</p>
}else if(){
  <p>B is true</p>
}else{
  <p>Both A and B is false</p>
}
```
当isA是true的时候，页面输出为：
```html
<p>A is true</p>
```
当isB是true的时候，页面输出为：
```html
<p>B is true</p>
```
当isA和isB都是false时，页面输出
```html
<p>A is true</p>
```
需要注意的是，当增加判断结束时，必须用`#endif`指令表示结束判断，如果没有`#endif`指令，重新加载时会报错，同时保留上一份正确的文件.
真假指令支持嵌套：
```html
<!-- #if(isA) -->
  <p>isA</p>
	<!-- #if(isAA) -->
		<p>isAA</p>
	<!-- else -->
		<p>isAA false</p>
	<!-- #endif -->
<!-- #else -->
  <p>isA false</p>
<!-- #endif -->
```
注意：这里`#if`指令的条件表达式中，不一定是boolean的值，也可以是数字或者对象，对应真假映射如下：

|对象类型|值|结果|
|:----|:----|:----|
|boolean|true|true|
|boolean|false|false|
|Number|0|false|
|Number|!0|true|
|Object|null|false|
|CharSequence|长度为0|false|
|CharSequence|长度不为0|true|
|Object[]|数组长度为0|false|
|Object[]|数组长度不为0|true|
|Collection|集合为空|false|
|Collection|集合不为空|true|
|Object|不属于上面的任何其他情况|true|

- `#for` `#endfor`指令
	`#for` `#endfor`指令用于循环，语法如下：
```html
<!-- #for(user : users) -->
  <div> username : ${user.name}</div>
  <div> nickname : ${user.nickname}</div>
<!-- #endfor -->
```
效果类似于：
```
for(User user : users){
  <div>username : user.getName()</div>
  <div>nickname : user.getNickname()</div>
}
```
在使用`#for`指令时，for循环内部默认定义了loop对象，可以使用`${loop}`获取改对象及其属性，比如：
```html
<!-- #for(user : users) -->
<div> index : ${loop.index}</div>
<div> username : ${user.name}</div>
<div> nickname : ${user.nickname}</div>
<!-- #endfor -->
```
loop对象是LoopVariable类的实例，包含了以下属性：

    |属性|值|
    |----|----|
    |index|当前循环对象的索引值|
    |count|当前循环对象的总数|
    |item|当前循环对象的索引对象|

    因此上面的循环实际也可以写成：   
```html
<!-- #for(user : users) -->
<div> index : ${loop.index}</div>
<div> username : ${loop.item.name}</div>
<div> nickname : ${loop.item.nickname}</div>
<!-- #endfor -->
```
`#for`指令支持嵌套使用，比如：
```html
//以这种方式构造的对象
List<List<String>> strsList = new ArrayList<List<String>>();
	for(int i = 0; i < 3; i++){
		List<String> strs = new ArrayList<String>();
		for(int j = 0; j < 3; j++){
			strs.add("str"+j);
		}
		strsList.add(strs);
}
```
由上面这段代码构造的对象，可以使用嵌套的`#for`指令遍历：
```html
	<!-- #for(strs : strsList) -->
	<!-- //注意loop是一个局部变量，这里的loop是第一层的loop对象 -->
	<h2>${loop.index}:</h2>
		<!-- #for(str : strs) -->
		<!-- //注意loop是一个局部变量，这里的loop是第二层的loop对象 -->
		<span>index : ${loop.index}</span>
		<span> name : ${loop.item}</span>
		<br/>
		<!-- #endfor -->
	<!-- #endfor -->
```
输出的结果如下：
```
	1:
	index : 1 name : str0 
	index : 2 name : str1 
	index : 3 name : str2 
	2:
	index : 1 name : str0 
	index : 2 name : str1 
	index : 3 name : str2 
	3:
	index : 1 name : str0 
	index : 2 name : str1 
	index : 3 name : str2 
```

- `#break`指令
	`#break`指令用于跳出循环，语法如下：
```html
#break condition
```
条件是用于判断是否执行break的条件，如下：
```html
<!-- #for(strs : strsList) -->
  <div>${strs}</div>	
  <!-- #break loop.index>=2 -->
<!-- #endfor -->
```
等价于
```java
for(strs : strsList){
  if(loop.index>=2){
        break;
  }
}
```
所以实际上也可以这么写：
```html
<!-- #for(strs : strsList) -->
  <div>${strs}</div>
  <!-- #if(loop.index > 1) -->
    <!-- #break  -->
  <!-- #endif -->
<!-- #endfor -->
```
需要注意的是，`#break`指令的condition没有输入时，等价于`#break true`，当condition有输入并且为null时，等价于`#break false`

- `#set` `#endset`指令
	`#set`指令用于设置局部变量，需要使用`#endset`结束，语法如下：
```
<!-- #set name="admin";name1=username -->
  <div>name:${name}</div>
  <div>name1:${name1}</div>
<!-- #endset -->
```
相当于
```java
{
  String name = "admin";
  Object name1 = username;
}
```
变量仅在花括号内部有效
输出的结果是：
```
name:admin
name1:penghj
```
`#set`指令定于多个变量时需要用分号；分隔。
在`#if`指令或者`#for`指令内部也允许使用`#set`指令设置变量，如下：
```html
<!-- #for(strs : strsList) -->
<div>${loop.index}:</div>
<!-- #set variable=strs[loop.index-1] -->
<div>${variable}</div>
  <!-- #for(str : strs) -->
  <span>index : ${loop.index}</span>
  <span> name : ${loop.item}</span>
  <br/>
  <!-- #endfor -->
<!-- #endset -->
<!-- #endfor -->
```
要注意外层每次循环都重新定义了variable变量。
`#set`指令和`@`指令的区别：

    |||
    |----|----|
    |&#8194;|@指令|#set指令|
    |范围|定义页面全局的变量|定于页面局部的变量|
    |定义对象|只能定义字符串变量|可以定义对象变量，也可以调用对象方法|
    |语法|不需要结束指令|需要#endset指令结束|
	
- `#include`指令
	用于引用某个页面到本页，引用的方式是直接将页面内容拼接到指令所在的位置，是静态引用。
	指令的语法如下：
		<!-- #include pagePath -->
	注意，`#include`指令的参数pagePath是所引用页面相对当前页面的相对路径，比如在如下路径中：
```
views
　├　index.html
　├　head.html
　└　folder
		  └foot.html
```
那么在`index.html`页面中要引用`head.html`和`foot.html`的语法如下：
```html
<body>
  <!-- #include head -->
  <div>index的内容<div>
  <!-- #include folder/foot -->
</body>
```
> **注意:**
> 这里是静态引用，因此在`head.html`和`foot.html`中，可以用EL表达式访问到`index.html`页面中的所有变量，即在`index.html`中可以访问变量`${username}`，在`head.html`和`foot.html`中也可以访问变量`${username}`，并且三个页面访问的是同一个变量

    对于引用页面，有时候并不需要引用整个html，而只需要引用某个特定的片段，这个时候可以在被引用的页面上用attribute：`ht-fragment`标注出被引用的片段，然后在pagePath中用`#`标注说引用的片段，如下：
    
    include.html的内容
```html
<!-- #fragment include -->
<div>
  <div>include</div>
  <div>${username}</div>
</div>
<!-- #endfragment-->
<div>nice</div>
```
index.html的内容
```html
<!-- #include include#include -->
```
此时会发现，引用的时候实际上只引用了`include.html`中的`include`片段，输出结果如下：
```
include
jim
```
如果将`index.html`的内容改成：
```html
<!-- #include include -->
```
则输出的结果为：
```
include
jim
nice
```
实际上引用了`include.html`的所有内容

- `#load`指令
	用于加载json文件或者yaml文件并解析成页面变量供页面使用。语法如下：
```html
<!-- #load resource -->
```
这里resource可以是一个文件的路径，也可以是一个其他的资源路径，以文件路径为例，路径是相对于index.html的相对路径，与#include指令的pagePath相同。
假设在与index.html相同路径下，有一个load.json文件，文件内容如下：
```
{
  name : 'jsonname',
  list : 
			[
				{name : 'list0', index : 1},
				{name : 'list1', index : 2}
			]
}
```
则在index.html中使用如下代码加载load.json文件
```html
<!-- #load load.json -->
<div>${load.name}</div>
<!-- #for(list : load.list) -->
  <div>${list.name}:${list.index}</div>
<!-- #endfor -->
```
输出结果如下：
```
jsonname
list0:1
list1:2
```
同样的，如果是`load.yaml`也可以达到相同的效果，**加载进来的对象名与文件名一致**。
> **注意：**
> 1. `#load`指令加载的对象是页面全局的，即整个页面都可以使用，但是在`#load`指令执行之前，对象并没有加载进来，因此在`#loa`d指令执行之前对象是不存在的。  
> 2. 目前只支持用json文件或者yaml文件定义的对象。
	
- `#fragment`指令
	定义代码片段，用于定义在页面中的代码片段，以便使用#include指令引用,语法如下：
```html
<!-- #fragment include1 -->
  <!-- #fragment include -->
  <div >
    <div>include</div>
    <div>${username}</div>
  </div>
  <!-- #endfragment -->
  <div>nice</div>
<!-- #endfragment -->
```
这里代码片段是支持嵌套定义的。

- `#render-fragment`指令
	用于在布局页面中指定渲染片段，语法如下：
```html
<!-- #rander-fragment name=fragmentname required=required -->
```
参数名称name表示片段名，required参数是一个布尔值，表示是否必须的片段。这个指令主要用在布局页面中，指定在布局页面中的位置渲染使用此布局的页面的片段。举例如下：
在view文件夹中，有两个文件，如下：		
```
views
  ├　fragment.html
  └　layout.html
```
其中，fragment.html的内容如下：
```html
<div ht-layout="layout">
  <!-- #fragment content -->
  <div>this is fragment name content! used layout.html as layout</div>
  <!-- #endfragment -->
  <!-- #fragment foot -->
  <div>this is fragment name foot! used layout.html as layout</div>
  <!-- #endfragment -->
</div>
```
layout.html的内容如下：
```
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
  <div class="main">
        <!-- #render-fragment name=content -->
        <div>other content</div>
        <!-- #render-fragment name=foot -->
  </div>
</body>
</html>
```
在`layout.html`中，在两个位置定义了渲染不同的片段，这里访问`fragment.html`页面时，返回的显示结果如下：
```
this is fragment name content! used layout.html as layout
other content
this is fragment name foot! used layout.html as layout
```
可以看到fragment的片段被分开插入到`layout.html`中并渲染出来.
### attribute集
||||
|----|----|----|
|attribute|作用|备注|
|ht-layout|定义当前页面使用的布局|必须写在当前页面的根元素上|
|ht-fragment|定义当前页面的片段名称|效果与#fragment相同|
|ht-if|判断真假，条件为真时渲染当前元素，为假时不渲染|效果与#if相同|
|ht-for|循环遍历元素|效果与#for相同|
|ht-include|引用页面|效果与#include相同|
|ht-render-fragment|渲染指定片段|效果与#render-fragment相同|

    由于attribute集与指令集大多数是有相同作用的，这里不再重复介绍。

### 指令应用
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

### htpl的开发优化
#### 应用根目录优化
在web应用开发的过程中,html页面经常需要引用各类静态资源和设置各种跳转链接,使用相对路径往往会导致各个页面的关系非常混乱.使用绝对路径又会因为应用部署的时候应用上下文不一致而导致在不同的环境部署需要修改链接的地址.这一点htpl做了细致的考虑.  
在htpl中,所有以`/`开头的链接都认为是应用根目录,htpl编译渲染时会自动添加应用部署时的上下文,比如:
模板源代码为:
```html
<a href="/user_list">用户列表</a>
```
经过htpl编译之后,会转化为:
```html
<a href="/leap/user_list">用户列表</a>
```
这样我们就可以全部使用绝对路径而不用在关注应用部署的上下文了.
这个特性在静态资源中一样有效:
```html
<link href="/assets/plugins/jPlayer/css/jplayer.css" rel="stylesheet" type="text/css" />
<script src="/config.js" type="text/javascript"></script> 
<img src="/assets/img/logo.png" /><span>leap logo</span> </a>
```
会转化为:
```html
<link href="/leap/assets/plugins/jPlayer/css/jplayer.css" rel="stylesheet" type="text/css" />
<script src="/leap/config.js" type="text/javascript"></script> 
<img src="/leap/assets/img/logo.png" /><span>leap logo</span> </a>
```
#### 静态资源缓存优化
web应用在开发过程中,页面经常需要应用各种各样的静态资源,尤其是js文件,当我们对js文件进行修改时,客户端浏览器通常是不知道的,应用更新后,经常会由于客户端脚本缓存导致应用异常,脚本错误等等.  
htpl针对这类问题做了一个非常完美的处理:计算静态文件hash值并作为资源版本标识后缀.  
举例如下:
当页面中引用如下js文件时:
```html
<script src="/assets/scripts/common.js" type="text/javascript"></script>
```
经过htpl编译后,会变成:
```html
<script src="/leap/assets/scripts/common-3506244270.js" type="text/javascript"></script>
```
后缀`-3506244270`是common.js的hash值,当`common.js`的内容变化时,这个哈希值会变化,从而导致客户端缓存失效,开发人员也就不用再去关心如何清理客户端缓存的问题了.

这里我们注意到,`common.js`的src是在`/leap/assets/scripts/`下的,但是实际上我们的资源文件是在`/leap/static/scripts/`下的,这里就是htpl的默认协议,如果以`assets`代替`static`,则表示需要htpl加上hash后缀,如果使用`static`,则表示不需要htpl加上hash后缀.