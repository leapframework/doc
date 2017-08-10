# 返回数据序列化配置

## 默认配置

当接口直接返回 JSON 数据时，在默认情况下，JSON 序列化的格式如下：

- 键名（即 key ）使用双引号括起来；

- 不忽略 null 值或空字符串值的属性；

- 属性命名风格采用驼峰式命名风格。

例如我们定义一个 POJO 如下：

```java
public class User {

    private String id;

    private String name;

    private String fullName;

    private Integer age;

    private Date birthday;

    // 省略 getter 和setter

}
```

然后路由方法的返回类型就是这个类的对象，对象各个字段都为空：

```java
public class UserController {

    public User find() {
        User user = new User();
        return user;
    }
}
```

那么启动工程，当我们访问部署路径下的 `/user/find` 时，将会返回如下内容：

```json
{"id":null,"name":null,"fullName":null,"age":null,"birthday":null}
```

## 自定义配置

如果需要对以上序列化格式进行配置，例如忽略所有 null 值的属性，可以使用 `leap.web.json.JsonSerialize` 注解实现。

我们可以直接查看注解 `@JsonSerialize` 的源码了解目前有哪些可配置项：

```java
package leap.web.json;

import leap.lang.enums.Bool;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface JsonSerialize {

	Bool keyQuoted() default Bool.NONE;

	Bool ignoreNull() default Bool.NONE;

	Bool ignoreEmpty() default Bool.NONE;

	Bool nullToEmptyString() default Bool.FALSE;

	String namingStyle() default "";

    String dateFormat() default "";

}
```

- `keyQuoted` ：配置键名是否使用双引号括起来；

- `ignoreNull` ：是否忽略值为 `null` 的属性；

- `ignoreEmpty` ：是否忽略值为空字符串的属性；

- `nullToEmptyString` ：是否自动把 `null` 值转为空字符串值；

- `namingStyle` ：配置序列化出来的 JSON 的命名风格；

    > 目前支持多种命名风格，可以参考 Leap 内置的伪枚举类 `leap.lang.naming.NamingStyles` 。

    > 一般情况下常用的有两种：

    - `NamingStyles.NAME_LOWER_CAMEL` ，即上面介绍的默认驼峰式命名风格；

    - `NamingStyles.NAME_LOWER_UNDERSCORE` ，小写下划线命名风格。

- `dateFormat` ：配置日期类型的值的日期格式化形式

现在我们把上面的例子做一下更改，改成使用自定义配置：

```java
public class UserController {

    @JsonSerialize(ignoreNull = Bool.TRUE, namingStyle = NamingStyles.NAME_LOWER_UNDERSCORE, dateFormat = "YYYY年")
    public User find() {
        User user = new User();
        user.setFullName("zhangsan");
        user.setBirthday(new Date());
        return user;
    }
}
```

此时重新启动工程，还是访问之前的接口，则将返回：

```json
{"full_name":"zhangsan","birthday":"2017年"}
```

因为我们在 `@JsonSerialize` 中配置 `ignoreNull` 为 `true` ，所以值为 `null` 的属性不会序列化出来。

命名风格改为小写下划线，所以原本的 `fullName` 属性序列化为 `full_name`。

日期格式化形式设为 `YYYY年` ，因此序列化的时候输出了 `2017年` 。

## 自定义配置的位置

通过源码我们也知道，`@JsonSerialize` 注解支持在类上和方法上，因此我们可以在以下位置配置接口返回 JSON 的序列化配置：

- 路由方法上：例如以上示例，对单个接口进行定义；

- 控制器类上：在 Controller 类上加注解，则类中所有路由方法都使用该配置；

- 控制器类的父类或接口上：这种适用于某模块的接口需要统一配置序列化格式的场景。

需要明确的是，`@JsonSerialize` 注解不支持在返回对象的类上进行标注，如上例的 `User` 类，在返回对象类上进行标注是无效的。

> 主要是由于 Leap 认为返回对象有可能还包含其他对象属性，如果这多个对象各自都有自己的序列化配置，那么在进行序列化的时候逻辑就会变得复杂，不好理解和使用。

## 自定义默认配置

以上自定义配置分别是从小到大的范围去做配置，那如果是整个项目都需要统一一种序列化配置呢？

这个时候我们就需要自定义默认配置了。

我们可以在配置根包 `base-package` 路径下新建 `Global` 类，内容如下：

```java
import leap.core.annotation.Inject;
import leap.lang.naming.NamingStyles;
import leap.web.App;
import leap.web.json.JsonConfigurator;

public class Global extends App {

    @Inject
    protected JsonConfigurator jc;

    @Override
    protected void init() throws Throwable {
        jc.setDefaultNamingStyle(NamingStyles.LOWER_UNDERSCORE);
        jc.setDefaultDateFormat("YYYY年");
        jc.setDefaultSerializationIgnoreNull(true);
    }
}
```

通过注入 `JsonConfigurator` 对象我们就可以更改序列化的默认配置。

以上效果与上例完全一样，这里就不多做演示了。

注意这些**配置的优先级是越具体越优先**，所以默认配置的优先级是最低的，路由方法上的配置优先级是最高的。