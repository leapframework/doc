# 事务控制

在涉及复杂操作的时候，我们需要做事务控制。这里我们主要介绍简单的事务控制方式。

## 代码式事务控制

代码式事务的好处在于灵活控制事务边界，不足之处则在于侵入了业务代码。

假设我们在一个 UserService 类中的某方法使用了事务控制。以下是示例：

```java
package hello.service;
import hello.entity.User;
import leap.core.annotation.Bean;
import leap.orm.dao.Dao;
@Bean
public class UserService {
    public void doSomething() {
        User user = User.find("id1");
        Dao.get().doTransaction((s) -> {
            User.deleteAll();
            user.setName("admin");
            user.create();
            // and so on.
        });
    }
}
```

`doTransaction` 包围的代码块将处于同一个事务管理，当代码块中任意一个地方抛出未处理错误时，事务将回滚。

## 注解式事务控制

注解式事务的优缺点正好与代码式事务相反，它只能以方法为最小粒度控制事务边界，但是好处在于不污染业务代码。

我们将上面的例子改写成注解式事务控制，代码如下：

```java
package hello.service;
import hello.entity.User;
import leap.core.annotation.Bean;
import leap.core.annotation.Transactional;
@Bean
public class UserService {
    @Transactional
    public void doSomething() {
        User user = User.find("id1");
        User.deleteAll();
        user.setName("admin");
        user.create();
        // and so on.
    }
}

```

具体使用哪种方式控制事务，可视实际项目情况自行决定。