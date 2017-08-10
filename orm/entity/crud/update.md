# 更新数据

## update

当我们获取或创建了实体对象之后，更新实体数据很容易：

```java
user.update();
```

这个操作将会把 user 对象所有持久化属性保存到 user.id 对应的数据中。

如果我们需要只更新这个 id 对应的数据的某些字段，我们可以调用静态方法 update：

```java
// 使用 Map 传递更新字段和值
Map<String, Object> fields = new HashMap<>();
fields.put("name", "lisi");
User.update("id1", fields);

// 在只更新一个字段值的场景下，也可以省略 Map
User.update("id1", "name", "lisi");
```

## updateAll

指定 id 的更新操作在实际中其实比较少，更多的是指定过滤条件的批量更新。示例代码：

```java
Map<String, Object> fields = new HashMap<>();
fields.put("name", "lisi");
String where = "name = ?";
User.update(fields, where, "zhangsan"); // 这里最后一个参数是可变参数
```