# 创建 Service

在 `src/main/java/hello/service` 目录中创建一个 UserService 类：

```java
package hello.service;

import hello.model.User;
import leap.core.annotation.Bean;

@Bean
public class UserService {

    public User getHelloUser() {
        User user = User.findOrNull("hello");
        if (null == user) {
            user = new User();
            user.setId("hello");
            user.setName("hello world");
            user.create();
        }
        return user;
    }
}
```

在 Leap 中创建一个服务类很简单，只需要在类上使用注解声明为 Bean 即可。

在上面的服务方法中，我们从数据库查询 id 为 `hello` 的用户，找不到则新建一个，最后返回该用户。