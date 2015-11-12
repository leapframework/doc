# CSRF校验

CSRF校验是防止跨站伪造请求的一个重要手段.

目前leap提供了CSRF校验的配置功能,只要简单的设置启用即可实现.  
### 启用CSRF校验功能
在`Global`的`init()`函数中,添加如下代码即可启用`scrf`校验:
```java
sc.setCsrfEnabled(true);
```
### 获取csrf_token
启用csrf校验之后,所有的ajax请求都必须添加csrf_token,这个token是leap自动生成的,并且每次渲染视图都会将这个token放到ViewData中,因此在页面中可以直接使用如下代码获取csrf_token:
```
${csrf_token}
```
一般来说,我们需要把这个token放在页面中的某个位置,以便页面调用ajax的时候,可以获取到这个token并添加到参数中,以便通过leap的CSFR校验.

### 通过csrf_token校验的方式
leap会在每一个请求中校验csrf_token,我们有两种方式传递这个token:
1. 通过http请求头:在请求中添加header:{X-CSRF-Token:csrf_token}
2. 通过http参数:在http请求中添加参数:csrf_token=csrf_token


