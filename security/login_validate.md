# 登录校验

### 登录功能
leap的登录校验功能使用非常简单,首先我们需要在`leap.project.Global`中配置启用登录校验功能,然后添加登录用户查询的实现,修改init方法如下:

```java
@Override
protected void init() throws Throwable {
	interceptors().add(new GlobalInteceptor());
	sc.authorizeAnyRequests().enable();
}
```

此时在浏览器访问`http://localhost:8080/leap`,会跳转到一个登录页面:


```html
<div>
	<h1>Login with your Account</h1>
	<form action="/leap/login" method="post">
		<input type="hidden" name="return_url" value="/leap/"/>
		User name : <input type="text" id="username" name="username" value=""/>
				    <span style="color:red"></span>
		<p></p>
		Password  : <input type="password" name="password"/><span style="color:red"></span>
		<p></p>
		<input type="submit" value="Login Now"/>
	</form>
	<script type="text/javascript">
		document.getElementById('username').focus();
	</script>
</div>
```

这是leap内置的默认登录页面,实际上只能满足最基本的登录要求,一般来说我们需要定制自己的登录页面.  

当leap校验登录失败后,会自动跳转到`/login`这个视图,实际上这个视图需要我们自己创建的(**如果视图不存在,就使用leap内置的默认登录页面 **),在`WEB-INF/views/`目录下创建`login.html`:

```
└　WEB-INF
      ├　views
      │　　├　user_controller
      │　　├　user_list.html
      │　　├　index.html
      │　　└　login.html
      └　web.xml
```

这里`login.html`的内容如下:

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login</title>
</head>
<body>
	<!-- #if(errors.size() > 0) -->
	${errors.first().message}
	<!-- #endif -->
	<form action="/login" method="post">
		<input type="hidden" name="return_url" value="${return_url}"/>
		<table>
			<tr>
				<td>帐号:</td>
				<td><input type="text" name="username"/></td>
			</tr>
			<tr>
				<td>密码:</td>
				<td><input type="password" name="password"/></td>
			</tr>
			<tr>
				<td><button type="submit">登录</button></td>
				<td><button type="reset">重置</button></td>
			</tr>
		</table>
	</form>
</body>
</html>
```

你可能已经注意到,这里除了单纯的登录名和密码之外,还有一个隐藏域`return_url`,这个参数是由leap计算的,参数值是在跳转到登录页面之前用户真正访问的url,当用户输入完成账号密码成功登录之后,就会自动跳转到这个页面,当然,这里我们也可以写任何一个我们想要填写的地址.

> 1. 用户账号密码登录表单的提交地址是"/login",实际上经过leap-htpl的编译,最终地址是{appcontext}/login.这个地址是固定的,使用leap的登录功能的登录地址就是这个;
> 2. form表单必须以post的方式提交才能正常得校验用户名密码;
> 3. 用户名字段的参数名必须是`username`,密码字段参数名必须是`password`.

现在我们注意一下这部分代码:

```
<!-- #if(errors.size() > 0) -->
${errors.first().message}
<!-- #endif -->
```

这个errors对象()是`ViewData`的内置对象,表示一次请求中产生的所有错误信息,这里我们可以将错误信息打印出来,提醒用户.

为了让用户登录成功,我们还需要告诉leap如何查询登录账号对应的用户信息,在`leap.project.Global`中修改`init()`方法,为安全配置器(`SecurityConfigurator`)设置用户存储对象:

```java
@Override
protected void init() throws Throwable {
	interceptors().add(new GlobalInteceptor());
	//设置登录校验生效
	sc.authorizeAnyRequests().enableWebSecurity(true);
	//设置登录时获取用户的方式
	sc.setUserStore(new UserStore() {
		@Override
		public UserDetails findUserDetails(Object userId) {
			User user = User.find(userId);
			return new UserDetails() {
				public User userDetail = user;
				@Override
				public String getName() {
					return userDetail.getName();
				}
				@Override
				public String getLoginName() {
					return userDetail.getLoginId();
				}
				@Override
				public Object getId() {
					return userDetail.getId();
				}
			};
		}
		@Override
		public UserAccount findUserAccount(String username,
				Map<String, Object> parameters) {
			User user = User.findBy("loginId", username);
			return new UserAccount() {
				@Override
				public boolean isEnabled() {
					return true;
				}
				
				@Override
				public String getPassword() {
					return new BCryptPasswordEncoder().encode(user.getPassword());
				}
				@Override
				public Object getId() {
					return user.getId();
				}
			};
		}
	});
}
```

这里`sc.setUserStore()`接口需要接收一个`UserStore`的接口作为参数,但是我们并没有实现这个接口,为了方便起见,这里直接`new`了一个匿名类,同样的,`UserStore`的两个抽象函数,分别需要返回`UserAccount`和`UserDetails`的接口对象,这里我们也采用匿名类的方式.  
上面的代码并没有太多难点,主要的是以下代码:

```java
@Override
public String getPassword() {
	return new BCryptPasswordEncoder().encode(user.getPassword());
}
```

这里我们把获取到的密码加密后返回,交给leap校验,实际上,leap会将form表单提交过来的`password`加密后与我们这里返回的密码进行比较,相同的情况下才认为用户登录密码正确,然而在我们之前的处理中,我们存入数据库的密码字段并没有经过加密,所以这里需要加密返回,`BCryptPasswordEncoder.encode(str)`是用BCrypt算法将字符串加密,这个是leap内置的加密工具.

> 这里虽然通过先将数据库密码加密之后返回给leap做登录校验是可行的,但是实际上这存在安全隐患,数据库密码一般不建议明文存储,因此建议在存入用户密码前先加密,在这里直接返回就好.

到这里,登录功能就已经做好了,现在访问我们测试工程的任何一个目录都会跳转到登录页面.

### 登录后获取用户信息
登录成功之后,我们经常会需要获取当前登录用户的信息,在java中,获取的方式如下:

```java
UserDetails ud = request().getUser().<UserDetails>getDetails();
```

当然这里的`ud`就是我们在`UserStore`中返回的对象,这个对象是`UserDetails`接口的子类.

在html视图模板中,可以直接使用`user`变量获取当前登录用户:

```html
${user.getName()}
```

### 设置免登录路径
在实际应用中,我们常常会有几个特定的路径是不需要登录也能访问的,这时候我们可以在`Global`的`init()`函数中添加:

```java
sc.authenticateAnyRequests().allowAnonymousAccessTo("/post/*");
sc.authenticateAnyRequests().allowAnonymousAccessTo("/post/**");
```

这样所有符合通配符`/post/*`的url都可以不登录访问了.

> 1. `/post/*`匹配路径`/post/`下一级的目录,如:`/post/create`;
> 2. `/post/**`匹配路径`/post/`下所有子孙目录,如:`/post/create/params`