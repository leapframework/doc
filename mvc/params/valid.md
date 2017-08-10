# 参数校验

<!-- ## 简单校验 -->

Leap 支持使用注解直接定义参数的校验规则。

目前支持的校验注解有：

- `@Required` ：必传；

- `@NotNull` ：不可为 `null` ；

- `@NotEmpty` ：不可为空；

- `@Min` ：最小值，只支持在数值类型上使用；

- `@Max` ：最大值，只支持在数值类型上使用；

- `@Length` ：限定字符串长度的最大值或最小值；

- `@Email` ：限定字符串符合 Email 的格式；

- `@Pattern` ：限定字符串符合自定义的正则表达式。

下面是使用示例：

```java
public class UserController {

    public String create(@Required Integer id, @Required @Email String mail) {
        // do create
    }
}
```

<!-- ## 自定义校验 -->