# 自动映射

到目前为止，我们介绍的 Leap 文档注解功能其实已经涵盖了简单和复杂文档下的使用场景。从使用上来说是够用了，但是可能在一些人看来，或者是由于习惯了配置文件式开发，或者觉得这样对于代码是一种污染，又或者觉得这样注解与 md 文件混在一起很难看。没关系，Leap 也充分考虑了这一点，**提供了基于 md 文件自动映射的文档说明方式**。对上述基于注解的文档说明方式不满意的读者可以选用这种方式，甚至混合使用都可以，Leap 不做限制，当然使用上还是建议选用其中一种即可。

md 文件要想自动映射为接口文档，依赖的是**包名、类名以及方法名**，因此当接口的以上要素变更时，都要记得更新文档。

需要做自动映射的 md 文件还是在资源 resource 文件夹的 doc 文件夹下，根据包路径创建文件夹路径，根据类名创建 md 文件，根据接口方法名创建一级标题。假设我们有如下路由方法：

```java
package com.demo.myproject.api;

@Path("/pet")
public class PetController extends ModelController<Pet> {
    @GET
    public ApiResponse<List<Pet>> all(@Required String name) {
        return ApiResponse.ok(Pet.all());
    }
}
```

那么我们现在应该在 doc 文件夹下怎么建文件和文件夹才能自动映射为这个接口的文档呢？

按照包名类名全路径建文件夹和文件，例如 `/doc/com/demo/myproject/api/PetController.md` ？可以。

可是这样路径太深了，查找麻烦，改成放在 `/doc/api/PetController.md` 里？也可以。

或者更简单，直接放在 doc 根目录里，像这样 `/doc/PetController.md` ？照样生效。

Leap 查找对应路由类的逻辑是：

1. 解析路由类的包名加类名全路径，如上例的 app.demo.controllers.store.PetController 。
2. 查看 doc 目录中是否有对应文件夹和文件，即查看工程中是否有 `/doc/com/demo/myproject/api/PetController.md`
3. 有的话以此文件作为此路由类的文档。
4. 没有的话将包名加类名全路径去掉最左边根部的一个包名，再查看 doc 目录中是否有对应文件，其实就是回到第二步，找不到的情况下会一直重复直到只剩类名。

这样做的好处是便于开发人员能根据自己项目实际情况控制 doc 文件夹深度。

现在我们找到了路由类对应的文档，但是一般我们一个路由类里面有多个路由方法，这个时候应该怎么组织？还是利用了 Markdown的一级标题和二级标题。

现在我们编辑上面找到的路由类文档，给 all 方法添加文档说明：

```markdown
# all

根据传过来的 name 字段搜索所有的宠物

## param : name

要搜索的宠物名称，支持拼音或者宠物名首字母

## response

返回符合搜索名称的宠物列表

# otherApiRoute

其他路由方法的说明，这里只是为了举例
```

从上面的例子我们可以知道路由方法与各级标题之间的对应关系：

* 一级标题对应路由方法的方法名：`# 方法名`
* 二级标题以 `param :`为前缀的对应路由方法参数：`# param : 参数名`
* 二级标题为 response 的对应路由方法的返回说明：`# response`

各个标题下的内容就是对应的文档说明。

> 注意：由于我们在注解引用 md 文件片段和自动映射接口文档时，使用了一级二级标题作为对应的标识，所以在具体文档撰写中，如果有用到标题的需要，建议使用三级标题及其后的标题，以免造成解析错误。
