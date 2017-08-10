# 注解引用文件片段

有的情况下我们需要用 md 文件编写文档的地方较多，这个时候如果还是一个接口说明文档就用一个 md 文件的话，未免会造成 md 文件过多的情况。

这个时候我们可以将多个同模块的文档片段放到同一个 md 文件中，以 Markdown 一级标题的语法指定文档片段的名称，这个时候引用的语法就是

`doc:md文件名.md#文档片段名称`

例如我们在 /doc/user.md 文件中这样写：

```markdown
# findOneById

通过指定ID查找用户信息

# otherFragment

其他文档片段
```

然后我们就可以在接口方法中这样引用这个片段：

```java
@Doc("doc:user.md#findOneById")
public ApiResponse<User> findById(@Required String id) {
    return null;
}
```

以上 @Doc 注解的效果就相当于 `@Doc("通过指定ID查找用户信息")` 。
