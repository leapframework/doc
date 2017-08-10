# 针对 web 接口的单元测试

一个 web 单元测试的例子：

```java
package test;

import leap.webunit.WebTestBase;
import org.junit.Test;

public class OrgTests extends WebTestBase {

    @Test
    public void testOrg() {
        get("/org").assertContentNotEmpty().assertSuccess();
    }
}
```

执行以上单元测试，Leap 将会启动代码所在 web 工程，并在启动完成之后执行单元测试方法。在本例中发送了 GET 请求到 `/org` 路径下，并断言返回结果不为空而且是成功的。

_编写 web 单元测试要注意以下几点_：

- **单位测试代码的位置**：必须是在打包类型为 war 的工程的 `/src/test/java` 目录下。
- **单元测试类的父类**：必须继承 `leap.webunit.WebTestBase` 。这个类为我们的 web 单元测试做了很多事情，包括启动工程以及提供很多便捷方法供我们测试使用。
- **单元测试方法**：必须标注 `@Test`。

`WebTestBase` 类提供了 web 请求相关的方法，除了上面示例中的 get ，还有 post、delete、put 等常见 HTTP 方法，这些方法执行后返回 `THttpResponse` 对象，该对象里有单元测试常见的各种断言。这里的内容都比较简单，就不对这一块做过多介绍，需要了解的请查看源码或借助 IDE 遍历方法的功能了解。
