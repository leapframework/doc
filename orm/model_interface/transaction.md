# 事务控制方法

## <a id="transaction"></a>事务控制方法

<!--sec data-title="doTransaction(TransactionCallback callback)" data-id="doTransaction1" data-show=true ces-->

```java
void doTransaction(TransactionCallback callback)
```

------

通过函数接口TransactionCallback执行数据库操作,保证所有操作都在同一个事务控制下,示例如下:

```java
Model model1 = new Model();
Model model2 = new Model();
Model.doTransaction((TransactionStatus s)->{
	model1.create();
	model2.create();
});
```

此时model1的插入和model2的插入记录在一个事务控制中,如果任何一个语句抛出异常了,都会导致整个事务回滚.  
也可以通过`TransactionStatus.setRollbackOnly()`设置回滚.

**参数:**

* callback:事务控制回调对象,是函数接口TransactionCallback的实例对象,在`callback.doInTransaction()`内部实现的业务逻辑都会受事务控制

<!--endsec-->

<!--sec data-title="doTransaction(TransactionCallbackWithResult &lt; T &gt; callback)" data-id="doTransaction2" data-show=true ces-->

```java
static <T> T doTransaction(TransactionCallbackWithResult<T> callback)
```

------

和`void doTransaction(TransactionCallback callback)`相似,区别在于这个接口可以返回值.

**参数:**

* callback:事务控制回调对象,是函数接口TransactionCallbackWithResult的实例对象,在`callback.doInTransaction()`内部实现的业务逻辑都会受事务控制,并且`callback.doInTransaction()`的返回值将直接作为这个接口的返回值.

**结果:**

* callback的返回结果.

<!--endsec-->

<!--sec data-title="doTransaction(TransactionCallback callback, boolean requiresNew)" data-id="doTransaction3" data-show=true ces-->

```java
void doTransaction(TransactionCallback callback, boolean requiresNew)
```

------

和`void doTransaction(TransactionCallback callback)`相似,区别参数`requiresNew`,这个参数表示是否使用目前生存的事务对象还是新建一个事务对象.

`void doTransaction(TransactionCallback callback)`相当于`void doTransaction(TransactionCallback callback, false)`

**参数:**

* callback:事务控制回调对象,是函数接口TransactionCallbackWithResult的实例对象,在`callback.doInTransaction()`内部实现的业务逻辑都会受事务控制,并且`callback.doInTransaction()`的返回值将直接作为这个接口的返回值.
* requiresNew:是否创建一个新的事务对象,true表示创建新的事务对象,false表示不创建新的事务对象

<!--endsec-->

<!--sec data-title="doTransaction(TransactionCallbackWithResult &lt; T &gt; callback, boolean requiresNew)" data-id="doTransaction4" data-show=true ces-->

```java
<T> T doTransaction(TransactionCallbackWithResult<T> callback, boolean requiresNew)
```

------

和`void doTransaction(TransactionCallback callback, boolean requiresNew)`相似,区别在于这个接口可以返回值.

**参数:**

* callback:事务控制回调对象,是函数接口TransactionCallbackWithResult的实例对象,在`callback.doInTransaction()`内部实现的业务逻辑都会受事务控制,并且`callback.doInTransaction()`的返回值将直接作为这个接口的返回值.
* requiresNew:是否创建一个新的事务对象,true表示创建新的事务对象,false表示不创建新的事务对象

**结果:**

* callback的返回结果.

<!--endsec-->
