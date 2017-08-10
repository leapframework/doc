# 删除数据

## delete

通过实体 id 删除数据：

```java
// 通过静态方法
User.delete("id1");

// 通过实例方法
User user = new User();
user.setId("id1");
user.delete();
```

需要注意的是 delete 方法在找不到指定 id 的记录可以删除的时候将会报错 RecordNotDeletedException 。

在不需要知道执行结果只管执行删除语句的场景下可以将 delete 方法换成 tryDelete 方法，用法与上面一致。

## deleteAll

deleteAll 方法是一个比较危险的方法，在什么参数都不传的情况下，它会删除数据库表里所有记录。

但是我们一般不会这么干，常用的场景是加上过滤条件，如下示例：

```java
// 谨慎调用
User.deleteAll();

// 过滤删除方式1，参数使用变长数组
User.deleteAll("id = ? or name = ?", "id1", "lisi");

// 过滤删除方式2，参数使用 Map
Map<String, Object> params = new HashMap<>();
params.put("name", "lisi");
User.deleteAll("name = :name", params);
```