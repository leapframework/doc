# dao对象和事务控制

### dao对象获取
现在我们已经了解Model对象的基本应用,这已经可以完成我们平时的绝大多数的操作,但是有些时候我们可能希望使用dao对象直接操作数据库,这一点leap-orm也考虑到了,我们可以在任何地方通过如下代码获取dao对象:
```java
Dao dao = Dao.get();
```
dao对象中包含了非常全面的操作接口,可以非常灵活地进行各种数据库操作,这里就不一一举例了.

### 事务控制
在与数据库交互的过程中,我们经常会遇到多项操作的原子性问题,这也是数据库提供事务控制的原因,leap-orm自然也考虑到事务的控制,为了避免复杂混乱的事务控制,leap推荐使用代码式的事务控制,事务接口是:
```java
Dao.doTransaction(TransactionCallback callback);
```
`TransactionCallback`是一个函数式接口,里边只含有一个接口方法:
```java
void doInTransaction(TransactionStatus status) throws Throwable;
```
将所有需要事务控制的代码都放在这个接口方法的实现里即可实现这段代码的事务控制,示例如下:
```java
Dao.get().doTransaction((TransactionStatus status)->{
	user.create();
  post.create();
});
```