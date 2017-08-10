# 基于注解编写文档

在上一节我们可以看到接口返回示例中，其实很多关键信息是空的，比如模块的说明、接口的说明、请求参数的说明以及返回实体的说明等等。我们需要为接口添加文档说明文字，这样文档的阅读者才可以更清晰地了解我们接口的含义。

那么怎么为接口添加文档说明呢？Leap 采用的是注解的方式，直接在方法、参数或者实体字段上标注文档含义。

而且为了便捷、统一和节省记忆和学习成本，**Leap 提供了唯一一个文档注解 **`@Doc`** 用于文档说明**。

例如我们可以在路由方法中使用它：

```java
@Path("/pet")
public class PetModelController extends ModelController<Pet> {

    @GET
    @Doc(summary = "查询所有宠物",  desc = "查询所有宠物接口的详细介绍")
    public ApiResponse<List<Pet>> all(@Required @Doc("搜索名称") String name) {
        return ApiResponse.ok(Pet.all());
    }
}
```

还可以在实体 Pet 中使用它：

```java
@Table
@Doc("宠物")
public class Pet extends Model {

    @Id
    @Doc("宠物编号")
    private String id;

    @Column
    @Doc("宠物名字")
    private String name;

    @Column
    @Doc("主人编号")
    private String userId;

    // 省略 getter 和 setter
}
```

加上文档说明注解之后，生成的接口文档将会带上这些描述。示例结果就不展示了，读者可以自己试试看。

目前 @Doc 可以应用的位置主要有以下几个：

* **在路由方法上**：作为接口的文档描述，其中可以使用 @Doc 中的 `summary` 属性简要概述接口，这部分内容将在文档页面渲染时作为接口标题突出显示；详细描述可以使用 @Doc 的 `desc` 属性。
* **在方法参数上**：作为接口参数的文档描述，另外方法参数还可以使用 `@Required` 注解标注为必传参数，默认所有参数为非必传。
* **在实体类上**：作为文档实体的描述，当将实体作为参数传递或内容返回时，Leap 会解析实体读取这些文档注解；
* **在实体字段上**：作为文档实体属性的描述，同上。