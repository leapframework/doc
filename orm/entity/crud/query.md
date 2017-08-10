# 查询数据

## find 与 findOrNull

根据 id 查找数据的示例代码如下：

```java
String id = "id1";
User user = User.find(id);
```

这个方法的参数 id 如果在数据库中没有对应的记录，此方法将抛错。如果不希望这种情况抛错，可以改为使用 findOrNull：

```java
String id = "id1";
User user = User.findOrNull(id);
```

这里 user 对象在找不到对应 id 的情况下，将为 null。

## findBy

对于简单的单条件查询，可以调用 findBy ：

```java
User user = User.findBy("name", "lisi");
```

复杂的条件查询将在下一节介绍。

## all

如果需要列出数据库表中的所有记录，可以使用 all 方法：

```java
List<User> users = User.all();
```
