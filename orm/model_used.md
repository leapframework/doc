# 模型对象

现在数据库创建和数据源配置已经完成,我们的应用可以直接连接数据库了,现在我们将开始使用leap-orm来操作数据库.
leap-orm支持使用ActiveRecord模式.ActiveRecord是由Rails提出的一种ORM模式,在单表的数据操作中优势非常明显.
同时leap也支持使用传统的dao模式的数据库操作方式,弥补了ActiveRecord模式在多表查询中的劣势。

### 数据库表和对象模型的映射

在进行数据库的增删改查之前,我们需要先创建应用与数据库表映射的模型,在`leap-project`中新建一个包`leap.project.model`,并在该包下创建对应的两个java类,如下:

```
src/main/java
    └　leap.project
            ├　controller
            ├　inteceptor
            ├　param
            └　model
                ├　User.java
                └　Post.java
```

这两个类都必须继承`leap.orm.model.Model`类,这个类提供了所有ActiveRecord模式的所有数据操作接口.  
为了保证映射关系,`User.java`和`Post.java`需要相应的属性和数据库表字段对应,`User.java`和`Post.java`的源代码如下:

User.java:

```java
package leap.project.model;

import java.sql.Timestamp;

import leap.orm.annotation.Column;
import leap.orm.annotation.Id;
import leap.orm.annotation.Table;
import leap.orm.model.Model;
@Table("leap_user")
public class User extends Model {
	@Id
	@Column(name="id")
	private String id;
	@Column(name="name")
	private String name;
	@Column(name="age")
	private Integer age;
	private String loginId;
	@Column(name="password")
	private String password;
	private Timestamp createdAt;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
  public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
}
```

Post.java:

```java
package leap.project.model;

import java.sql.Timestamp;

import leap.orm.annotation.Column;
import leap.orm.annotation.Id;
import leap.orm.annotation.Table;
import leap.orm.model.Model;
@Table("leap_post")
public class Post extends Model {
	@Id
	@Column(name="id")
	private String id;
	@Column(name="name")
	private String name;
	@Column(name="descript")
	private String descript;
	private Timestamp createdAt;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
}
```

从源码中我们可以看到,在类名上有一个`@Table`的注解,在各个属性上也有`@Column`的注解,相信这里非常好理解各个注解的意思.  
需要说明的是,实际上注解并非必须的,leap会扫描所有的Model类并自动和数据库映射,只是在映射的时候遵循[ORM默认映射规则](#orm_mapping_rule),这里因为我们的数据库表有前缀`leap_`因此不能按照默认规则映射,所以需要添加注解.

根据数据库模型图,User和Post是多对多关系,我们还需要一个`UserPost.java`的Model来映射中间表:

```java
package leap.project.model;

import java.sql.Timestamp;

import leap.orm.annotation.Id;
import leap.orm.annotation.Table;
import leap.orm.model.Model;
@Table("leap_user_post")
public class UserPost extends Model {
	@Id
	private String userId;
	@Id
	private String postId;
	
	private Timestamp createdAt;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
}
```



> 1. 默认情况下,所有的Model子类和属性都会被对应成数据库的表或者字段
> 2. 如果某些类或者属性不希望被映射到数据库表或字段,则可以用`@NonEntity`和`@NonColumn`注解来标示指定的字段或类不做数据库映射.

#### Model属性自动填充

细心的你可能已经注意到,在我们创建的Model类对应的数据库表中,有两个字段:

```
created_at
updated_at
```

其中`created_at`字段是我们定义的`createdAt`字段的映射,那么`updated_at`字段是怎么来的呢?  

实际上,这两个字段都是leap orm默认生成的字段,当一个非抽象类继承了Model类时,如果没有注解该类不是数据库实体(通过`@NonEntity`可以表示该类不是数据库实体),那么leap会检查这个类是否有`createdAt`和`updatedAt`字段,如果没有,则自动添加这两个属性,这两个属性分别表示数据库记录的创建时间和最后更新时间.

当然有时候我们并不希望leap自动给我们添加这两个属性,那么可以采取如下两种方式来关闭leap的属性自动生成:

* **`@AutoGenerateColumns`注解**

如果某个Model的子类不希望自动生成`createdAt`和`updatedAt`字段,可以在这个类上添加注解`@AutoGenerateColumns(false)`,这种方式只针对单独某个类不需要生成的,如果全局情况下不希望生成这两个字段,可以使用配置的方式.

* **`orm.autoGeneateModelFields`配置**

在`config.xml`中添加如下配置:

```xml
<property name="orm.autoGeneateModelFields">false</property>
```

此时全局范围内所有的Model子类都不再自动生成`createdAt`和`updatedAt`字段.

> ***注意:***  
> 1. 配置了全局不自动生成字段后,即使对单独某个类注解了`@AutoGenerateColumns(true)`,也不会自动生成,此时建议自己在类中显式声明这两个属性.  
> 2. 如果在应用启动之前,数据库的映射的物理表已经创建好了,并且该物理表没有这两个字段,那么也不会自动生成.

##### <a id="orm_mapping_rule"></a>ORM默认映射规则
> 1. 对于全小写的属性名,默认映射属性名和数据库字段名一致;
> 2. 对于驼峰式命名的属性名,默认将属性名转化为下划线式,再与相同的数据库字段名一致.  
> 注意:属性名强烈建议不要使用特殊字符.

### Model对象的使用
现在我们已经创建好实体和映射关系了,接下来让我们看看如何使用leap的ActiveRecord.
我们先在`UserController`中添加一个action:

```java
public User create(String name,Integer age, String loginId, String password){
	User user = new User();
	user.setName(name);
	user.setAge(age);
	user.setLoginId(loginId);
	user.setPassword(password);
	try {
  	user.create();
  	return user;
  } catch (RecordNotSavedException e) {
  	e.printStackTrace();
  	return null;
  }
}
```

这个接口我们用来创建一个用户,现在我们启动应用,测试请求如下:

```
http请求:
url:
  http://localhost:8080/leap/user_controller/create
params:
  name:张三
  age:20
  loginId:zhangsan
  password:123123
  
返回结果:
{
  "id": "990104a3-d47e-472d-86c0-5d6e13a02b4f",
  "name": "张三",
  "age": 20,
  "password": "123123",
  "createdAt": 1447125740861,
  "loginId": "zhangsan"
}
```
这个结果告诉我们创建用户成功了.并且帮我们自动填充了id和创建时间.

现在我们回头看看,将用户插入数据库的操作,其实只有一句代码:

```java
user.create();
```

这里我们根本不用关注如何获取datasource,也不用关注如何获取dao对象,每一个User的实例就对应一行数据库记录,并且记录自身可以直接操作数据库,非常简单方便.
另外,还有对应的几个接口:

```java
user.update();//更新记录
user.delete();//删除记录
user.save();//根据user的id是否存在判断是更新还是插入记录
```

除了上面基本的操作之外,leap还提供了强大的记录查询功能,现在我们先在`UserController`中添加另一个action:

```java
public List<User> query(String name, Integer age, String loginId){
	if(name == null && age == null && loginId == null){
		return User.all();
	}
	if(name == null){
		name = "";
	}
	if(loginId == null){
		loginId = "";
	}
	CriteriaQuery<User> cq = User.<User>query();
	cq.where("name like ? and age like ? and loginId like ?", 
			"%"+name+"%",age==null?"%%":"%"+age+"%","%"+loginId+"%");
	return cq.list();
}
```

启动应用后发http请求如下:

```
http请求:
url:
  http://localhost:8080/leap/user_controller/query
params:

返回结果:
[
  {
    "id": "15583768-8b55-475f-87e0-6ddaee3cb910",
    "name": "王五",
    "age": 32,
    "password": "123123",
    "createdAt": 1447134433000,
    "loginId": "wangwu"
  },
  {
    "id": "8bcaa593-fddd-4c6f-8443-b5091cae4388",
    "name": "赵六",
    "age": 19,
    "password": "123123",
    "createdAt": 1447134462000,
    "loginId": "zhaoliu"
  },
  {
    "id": "990104a3-d47e-472d-86c0-5d6e13a02b4f",
    "name": "张三",
    "age": 20,
    "password": "123123",
    "createdAt": 1447125740000,
    "loginId": "zhangsan"
  },
  {
    "id": "d8e4be73-e9f8-4c70-bb85-cbe06d2341e5",
    "name": "李四",
    "age": 21,
    "password": "123123",
    "createdAt": 1447134405000,
    "loginId": "lisi"
  }
]
```

这里我们没有任何参数,返回的是所有数据,如果再添加一个`name=张三`的参数,可以看到返回结果如下:

```
[
  {
    "id": "990104a3-d47e-472d-86c0-5d6e13a02b4f",
    "name": "张三",
    "age": 20,
    "password": "123123",
    "createdAt": 1447125740000,
    "loginId": "zhangsan"
  }
]
```

从控制台日志我们也可以看到执行的sql:

```
SQL  : select t.* from leap_user t where name like ? and age like ? and login_id like ?
ARGS : ['%张三%','%%','%%']
```

通过代码我们可以看到,查询是通过Model的`query()`接口生成的`CriteriaQuery`对象来处理的,这个对象包含很多查询接口:

```java
cq.pageResult(page);//设置分页
cq.delete();//批量删除
cq.count();//查询数量
```

除了上述例子,还有许多接口,可以查询api文档.

现在我们按照同样的方式,`在PostController`中添加如下action:

```java
public Post create(String name, String descript){
	Post post = new Post();
	post.setName(name);
	post.setDescript(descript);
	post.create();
	return post;
}
```

在`UserController`中添加如下action:

```java
public boolean addUserPost(String userId, String postId){
	UserPost up = new UserPost();
	up.setUserId(userId);
	up.setPostId(postId);
	up.create();
	return true;
}
```

启动应用,调用这两个接口创建一些测试数据.以便后续使用.