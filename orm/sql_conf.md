# sql配置

我们已经了解ActiveRecord的一些基本用法了,但是ActiveRecord的优势在于单表查询和操作,联表查询目前不能直接通过ActiveRecord进行,leap-orm提供了sql配置的方式进行联表查询.

### sql配置和使用

在`conf`目录下添加`sqls.xml`,也可以添加`sqls`目录,并在目录下添加任意名称的'.xml'文件:

```
src/main/resources
      └　conf
          ├　sqls
          │   ├　user.xml
          │   └　post.xml
          ├　beans.xml
          ├　config.xml
          └　sqls.xml
```

这里我们创建了`sqls.xml`用来保存配置的sql，同时为了预防未来sql配置比较多，在`sqls`目录下创建了`user.xml`和`post.xml`，按照模块划分sql配置。

现在我们先看看`sqls.xml`的内容:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<sqls xmlns="http://www.leapframework.org/schema/sqls">
	<command key="user-post-list">
		SELECT 
		  u.*,
		  p.`name` pname,
		  p.`descript` 
		FROM
		  leap_user u 
		  JOIN leap_user_post up 
		    ON up.`user_id` = u.id 
		  JOIN leap_post p 
		    ON up.`post_id` = p.`id`
	</command>
</sqls>
```

`sqls.xml`配置的根节点是`sqls`标签,这个标签下每一个sql都是一个单独的`command`标签,`command`标签的`key`属性是这个sql的唯一标示,不能包含空格.

现在我们在`UserModelController`下添加一个action:

```java
public List<User> userPost(){
	return User.<User>query("user-post-list")
			.orderBy("name").list();
}
```

现在我们可以测试请求了:

```
http请求:
url:
   /user_model/user_post

返回结果:
[
  {
    "id": "990104a3-d47e-472d-86c0-5d6e13a02b4f",
    "descript": "软件工程师岗位",
    "name": "张三",
    "age": 20,
    "password": "123123",
    "createdAt": 1447125740000,
    "pname": "软件工程师",
    "loginId": "zhangsan"
  },
  {
    "id": "15583768-8b55-475f-87e0-6ddaee3cb910",
    "descript": "软件工程师岗位",
    "name": "王五",
    "age": 32,
    "password": "123123",
    "createdAt": 1447134433000,
    "pname": "软件工程师",
    "loginId": "wangwu"
  },
  {
    "id": "8bcaa593-fddd-4c6f-8443-b5091cae4388",
    "descript": "部门经理的岗位",
    "name": "赵六",
    "age": 19,
    "password": "123123",
    "createdAt": 1447134462000,
    "pname": "部门经理",
    "loginId": "zhaoliu"
  }
]
```
这里我们可以看到联表查询的结果了.

> **注意:**
> 1. 这里我们用User的列表接收参数结果,但实际上User并没有pname和descript这两个字段.不过这两个字段也会保存在User对象中,  
> 在java中可以用过`user.get("pname")`的方式获取到这个字段的值.

### sql参数占位符

在[模型对象](model_used.md)的章节里,我们已经知道可以通过`?`来设置sql的参数占位符,对应的参数按照顺序传入即可,这是一种比较简单的方式,并不能满足所有的参数传递的需求,leap-orm提供了另一种参数传递的方式.

在sql中,可以使用`:paramName`做为参数占位符.传入参数的时候,按照`paramName:paramValue`的方式传入即可.比如有如下的sql:

```sql
SELECT *　FROM leap_user WHERE name = :userName
```

调用传参的时候可以有两种方式:

```java
//方法一
User.query(sqlKey).param("userName", "张三").list();
//方法二
Map<String,Object> params = new HashMap<String,Object>();
params.put("userName", "张三");
User.query(sqlKey).params(params).list();
```
最终执行的sql如下:
```
 SQL  : select t.* from leap_user t where name = ?
 ARGS : ['张三',]
```

当然,实际上对于如此简单的sql,我们也可以不写在sql配置文件里,而直接使用查询:

```java
User.query("SELECT *　FROM leap_user WHERE name = :userName").param("userName", "张三").list();
```

sql的配置不仅仅是提供可以在不同的部署环境配置sql的功能,leap的sql配置提供了更加强大的动态sql功能,我们在下一节进行详细的介绍.