# 数组列表参数类型

## 服务端

这里的数组列表指的是简单类型的数组列表，复杂类型的数组列表参数不在本节的讨论范围。

定义的代码示例如下：

```java
public class UserController{
    public String list(List<String> names){
        return Strings.join(names, " -> ");
    }
}
```

> 以上是参数类型为列表 List 的例子，其实也可以替换为 Java 原生数组 Array 的形式，两者是等价的。

> 但是由于 List 的类型提供了更多接口方法方便我们程序的处理，因此我们更推荐使用 List 类型定义列表参数。

以上示例创建了一个路径为 `/user/list` 的接口，它接受所有 HTTP 请求方法，并且可接收一个参数 `names` 。

路由方法的处理也很简单，它使用了 Leap 内置的工具类 Strings ，将列表 `names` 中的元素通过指定字符串拼接在一起，再返回给前端。

## 客户端

对于客户端来说，传递简单类型参数和传递数组列表参数其实差不多。

首先传递方式是一致的，可参考前一节的传递方式说明。

唯一不同的是数组列表的组织格式，Leap 支持两种：

- **同参数名**：Leap 会将同参数名的参数值组织成数组列表。

    > 如上例当我们调用的 URL 为 `/user/list?names=zhangsan&names=lisi` 时，服务端将返回 `zhangsan -> lisi`。

- **同参数名加数组下标**：与上一种方式类似，只不过加了数组下标确定各元素值的位置。

    > 我们把调用的 URL 改为 `/user/list?names[1]=zhangsan&names[0]=lisi` 时，服务端将返回 `lisi -> zhangsan`。