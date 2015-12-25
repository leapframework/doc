# LEAP MVC应用
本章将详细讲解leap框架的MVC如何应用.

## 基础概念
在leap中,有几个特殊的概念需要我们先了解一下:
> **controller**:控制器,表示的是所有可以直接接收http请求的类,比如HomeController.  
> **action**:表示的是控制器下所有真正处理http请求的函数.比如HomeController.index.  
> **view**:视图,指的是视图模板,可以是html,也可以是jsp.