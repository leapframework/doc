# 实体操作

在前面章节里我们让实体 `User` 类继承了 `leap.orm.model.Model` ，因此在后续开发中我们可以使用 `Model` 类的方法进行数据操作。

比如我们可以在 `UserService` 类中使用 `Model` 做这些操作：

```java
package hello.service;

@Bean
public class UserService {
    
	public void operateDemo() {

		// 创建一个 User，并保存到数据库
		User user = new User();
		user.setId("id1");
		user.setName("zhangsan");
		user.create();

		// 查询该 User
		user = User.findOrNull("id1");

		// 查询 user 表中所有记录
		List<User> users = User.all();

		// 更新 user
		user.setName("new name");
		user.update();

		// 删除 user
		user.delete();
	}
}
```

需要注意 `Model` 类的这些方法需要通过子类实体调用，不能直接通过 `Model` 调用。

下面介绍各个常用接口。