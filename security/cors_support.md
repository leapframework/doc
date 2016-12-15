# CORS支持

维基百科对CORS的定义是:
跨域资源共享（CORS ）是一种网络浏览器的技术规范，它为Web服务器定义了一种方式，允许网页从不同的域访问其资源。而这种访问是被同源策略所禁止的。CORS系统定义了一种浏览器和服务器交互的方式来确定是否允许跨域请求。 它是一个妥协，有更大的灵活性，但比起简单地允许所有这些的要求来说更加安全。

简单地说,CORS就是让AJAX可以实现可控的跨域访问。

其实我们很早的时候就已经接触跨域资源共享了,不过之前并没有标准,所以以往一般有三种解决方案:

1. JSONP
2. Flash
3. 服务器中转

实际上无论哪种方式,都有很多限制,如下:

1. JSONP只能实现GET请求,并且错误无法捕获处理.
2. Flash要求浏览器必须安装flash插件.
3. 服务器中转,需要搭建中转服务器,费时费力

CORS的出现无疑解决了上面的所有问题.

不过目前国内对CORS提供支持的框架几乎没有,因此当我们的应用需要提供前端跨域资源请求的支持的时候,只能自己去处理这种请求,相当于每个应用单独去实现cors的标准,这对开发人员来说是很痛苦的,也会增加维护的成本.

leap已经为我们解决了这个麻烦.

当某个接口需要提供CORS支持的时候,我们只需要在接口上添加`@leap.web.annotation.Cors`注解即可,如下:

```java
import leap.web.annotation.Cors;

public class CorsTestController {

	public String normal() {
		return "normal";
	}
	
	@Cors
	public String enabled() {
		return "enabled";
	}
	
	@Cors(false)
	public String disabled() {
		return "disabled";
	}
	
}
```

> `@Cors`中有一个`value`属性,这个属性表示是否支持CORS,默认为true,设置为false时表示不支持CORS.  
> 如果没有注解`@Cors`是默认状态,即跟随全局配置.

有了上述注解之后,我们的接口就允许跨域访问了,对于没有注解的接口,我们已经知道是跟随全局配置的,全局配置默认的情况下是不允许跨域访问的,因此我们可以在全局添加允许跨域的配置,在`Global.java`的`init()`方法中添加如下代码:

```java
public class Global extends App {
	@Inject
	private CorsConfigurator cc;
	@Override
	protected void init() throws Throwable {
		cc.setAllowedMethods(new String[]{"*"});
		cc.setAllowedHeaders(new String[]{"*"});
		cc.setAllowedOrigins(new String[]{"*"});
		super.init();
	}
}
```

这里`*`表示允许所有的方法,请求头和域名跨域访问,当然,如果实际应用中不支持所有的话,可以修改为我们自己指定的域名/方法/请求头.

> **注意:**  
> 如果要允许全部域名/请求头/方法,字符串数组必须长度为1且为`*`.  
> 如果字符串数组长度不为1,即使包含`*`也不会允许全部域名/请求头/方法.