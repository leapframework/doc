# htpl的开发优化

### 应用根目录优化
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
### 静态资源缓存优化
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