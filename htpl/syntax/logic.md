# 逻辑控制

## 条件判断

页面模板的渲染最常见的情况就是根据不同的条件需要渲染不同的内容。

HTPL 使用 `#if` ，`#elseif` ，`#else` ，`#endif` 来做条件判断的语法标识。

下面是一个简单的例子：

```
<div>
    <!-- #set auth = false -->
    <h1>个人主页</h1>
    <!-- #if(auth) -->
        <p>欢迎回来</p>
    <!-- #else -->
        <p>无权访问</p>
    <!-- #endif -->
</div>
```

注意这里的 `#endif` 是**必须**的闭合标识。

## 循环遍历

当我们根据列表数据渲染页面时，循环遍历渲染是必不可少的。

HTPL 使用 `#for`，`#endfor` 来做循环遍历。

下面是一个简单的例子：

```
<!-- #for(user : users) -->
    <div> username : ${user.name}</div>
    <div> nickname : ${user.nickname}</div>
<!-- #endfor -->
```

当遍历的对象是一个数字 n 时，表示循环 n 次:

```
<!-- #for(i: 2) -->
${i}
<!-- #endfor -->
```

以上代码将输出：`12`。

可以通过 `#break` 提前跳出循环，例如:

```
<!-- #for(i: 100) -->
    ${i}
    <!-- #break i > 50 -->
<!-- #endfor -->
```

`#for` 内部定义了 loop 对象，可以通过该对象访问当前循环的信息，例如:

```
<!-- #for(user : users) -->
    <div> index : ${loop.index}</div>
    <div> username : ${user.name}</div>
<!-- #endfor -->
```

注意: ${loop.index} 从 1 开始。

`#empty` 用于在循环的对象为空时显示内容:

```
<!-- #for (user: users) -->
    ${user.name}
<!-- #empty -->
    No users.
<!-- #endfor -->
```

假设 users 未定义，则上面将输出为 No users。