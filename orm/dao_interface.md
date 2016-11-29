# dao接口

前面我们已经清楚了Model接口的强大功能,也能满足大多数的数据库操作了,但是Model对象的功能实在太强大了,有些时候我们不希望暴漏这么强大的数据操作功能给业务层,而希望业务层只能使用我们提供的数据操作结果.

这个时候如果将Model对象返回给业务层那就无疑给了业务层全部的数据操作功能了,这和我们的需求不符.因此leap也提供了传统的dao接口来满足这类需求,通过dao接口,我们可以将没有继承Model类的类也和数据库表映射起来,并且通过dao对该类的实例对象进行操作.

> **注意:**  
> 要使用dao接口将类和数据库表映射,需要在类上注解`@leap.orm.annotation.Entity`

## 导航

* [新增方法](dao_interface/create.md)
* [更新方法](dao_interface/update.md)
* [删除方法](dao_interface/delete.md)
* [查询方法](dao_interface/query.md)
* [Query查询](dao_interface/query_object.md)
* [指令对象](dao_interface/cmd.md)
* [事务控制](dao_interface/transaction.md)
* [校验方法](dao_interface/validate.md)
* [执行sql](dao_interface/execute.md)
* [探测方法](dao_interface/test.md)
* [其他方法](dao_interface/other.md)

### <a id="getdao"></a>dao对象获取

<!--sec data-title="get()" data-id="get1" data-show=true ces-->

```java
Dao get()
```

------

获取默认的Dao的实例对象相当于`Dao.get("default")`

<!--endsec-->

<!--sec data-title="get(String name)" data-id="get2" data-show=true ces-->

```java
Dao get(String name)
```

------

获取指定名称的Dao实例对象,这里主要是用于多数据源的情况下.

<!--endsec-->
