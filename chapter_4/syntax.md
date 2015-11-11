# 基础语法
### 属性语法
```html
<div class="body container" ht-fragment="content" ht-layout="admin/layout">
这个div应用了admin/layout布局文件，并且这个div作为当前页面中的一个名称为content的片段
</div>
```
### 指令语法
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