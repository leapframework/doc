# 针对类的单元测试

假设我们现在有以下 service 方法需要做单元测试：

```java
package hello.service;

@Bean
public class UserService {
    public String hello(String a) {
        return "hello " + a;
    }
}
```

针对这个方法我们编写了以下单元测试：

```java
package test;

import hello.UserService;
import leap.core.annotation.Inject;
import leap.core.junit.AppTestBase;
import org.junit.Test;
public class UserTest extends AppTestBase {
    @Inject
    private UserService userService;

    @Test
    public void testHello() {
        String who = "world";
        String re = userService.hello(who);
        assertEquals(re, "hello " + who);
    }
}
```

执行以上单元测试，Leap 将会以 standalone 的方式启动工程，并且会在执行测试代码前注入测试类 UserService 的依赖，然后执行单元测试方法，根据逻辑断言 service 方法的返回。

_编写单元测试要注意以下几点_：

- **单位测试代码的位置**：必须是在打包类型为 war 的工程的 `/src/test/java` 目录下。
- **单元测试类的父类**：必须继承 `leap.core.junit.AppTestBase` 。这个类为我们的单元测试做了很多事情，包括启动工程以及提供很多便捷方法供我们测试使用。
- **单元测试方法**：必须标注 `@Test`。