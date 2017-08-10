# SQL 查询接口

## 创建 Query 对象

在 Model 对象中有以下方法可供子类调用以创建 Query 对象。

示例代码：

```java
// 通过指定 sqlKey 创建
String sqlKey = "user.find"
Query<User> query = User.<User>query(sqlKey);

// 或者直接在代码中写 sql 创建
String sql = "select * from user where name = 'lisi'";
Query<User> query = User.<User>query(sql);
```

这里 query 方法会自动判断传入的是 sqlkey 还是具体 SQL。

## 使用 Query 对象

Query 对象是一个实体查询对象接口，这个接口对象允许我们在真正查询前做一系列的sql配置，包括：设置参数值、排序、分页等操作。

这些配置接口支持我们链式调用。

### 使用示例

命名参数、查询行数、排序、列表结果使用示例：

```java
Map map = new Map();
map.put("age",3);

List<User> list = User.<User>query("SELECT * FROM user WHERE name=:name AND age > :age")
    .param("name","jim")
    .param(map)
    .limit(2)
    .orderBy("id DESC")
    .list();
```

最终执行的sql如下:
```sql
SELECT * FROM user WHERE name='jim' AND age > 3 ORDER BY id DESC limit 0,2 
```

下面是 Query 常用接口介绍。

### 参数设置

上一节介绍了参数占位符在 SQL 配置文件中的使用，在这一节当我们调用 SQL 准备执行时，就需要给对应的参数占位符指定具体的值了。

为**匿名参数占位符**传入参数值:

```java
// 为单个匿名参数占位符指定值
Query<User> query = User.<User>query("select * from User where name = ?");
query.param("lisi");

// 为多个匿名参数占位符指定值
Query<User> query = User.<User>query("select * from User where name = ? and age = ?");
Object[] params = new Object[] {"lisi", 18};
query.params(params);
```

为**命名参数占位符**传入参数值:

```java
Query<User> query = User.<User>query("select * from User where name = :name and age = :age");

// 直接指定参数
query.param("name", "lisi").param("age", 18);

// 通过 Map 对象指定参数
Map<String, Object> map = new HashMap<>();
map.put("name", "lisi");
map.put("age", 18);
query.params(map);
```

**注意！**设置参数的时候需要注意区分 `params` 方法和 `param` 方法之间的区别。

- `params` 方法主要针对多个参数进行设置，例如传入一个 `Map` 或者一个数组等等；

- `param` 方法主要针对单个参数进行设置，例如设置某个命名参数的值或者传入一个匿名参数值等等。

实际写代码调用的时候由于这两个方法名很相近以及支持参数类型都差不多，很容易导致用错，所以在这里我们特别强调一下。

### 限制行数

用于在查询前设置限制行数,类似于mysql数据库的limit关键字。

```java
Query<User> query = User.<User>query("select * from User");

// 查询前10个
int num = 10;
query.limit(num);

// 查询某个区间如7到12之间的数据
int start = 7;
int end = 12;
query.limit(start, end);

```

### 分页查询

这里可以设置分页常用的 index 和 size 参数。

```java
Query<User> query = User.<User>query("select * from User");
int index = 1;
int size = 10;
PageResult<User> result = query.pageResult(index,size);
```

### 排序

这个方法可以设置查询的排序参数。

```java
Query<User> query = User.<User>query("select * from User");

query.orderBy("id asc, name desc");
```

### 执行查询

除了上面分页查询里的 pageResult 方法会立刻执行数据库查询之外，其他之前介绍的方法都只是设置了参数，只有执行了下面的方法才会对数据库发起查询。

```java
Query<User> query = User.<User>query("select * from User");

// 返回符合查询结果的所有记录
List<User> users = query.list();

// 返回符合过滤条件的记录数
long count = query.count();

// 返回符合过滤条件的第一条记录，如果一条都没有将会抛错，需要不抛错请使用下面那个方法
User user = query.first();

// 返回符合过滤条件的第一条记录，没有则返回null
User user = query.firstOrNull();

// 返回查询结果的唯一一条记录，不足一条或不只一条将报错
User user = query.single();

// 返回查询结果的唯一一条记录或者没记录时返回null，多于一条记录将抛错
User user = query.singleOrNullk();
```
