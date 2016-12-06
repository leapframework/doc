# ModelController&lt;T&gt;

前面我们已经了解到如何使用leap开发api了，但是仅仅是自动生成文档还远远不够，leap还提供了更方便的方式让我们开发api。

所有的控制器，只要继承`ModelController<T>`类，就可以获得强大且简单易用的操作接口。

前面我们说过，要使控制器称为API，必须继承`ApiController`，我们来看一下`ModelController<T>`的声明：

```java
@ResourceWrapper
public abstract class ModelController<T> extends ApiController implements ApiInitializable {
// ....
}
```

这里其实也继承了`ApiController`，因此只要继承`ModelController<T>`的控制器一样可以解析成API。

## 模型的增删改查

继承`ModelController<T>`之后，我们的控制器同时也继承了`ModelController<T>`提供的接口方法，其中，对泛型模型的增删改查非常强大，假设我们有如下控制器：

```java
@Path("/user")
public class UserController extends ModelController<UserModel> {

}
```

并定义了用户和宠物模型如下：

```java
public class UserModel extends Model {
    private String id;
    private String name;
    private String createdAt;
    @Relational
    private List<PetModel> pets;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<PetModel> getPets() {
        return pets;
    }

    public void setPets(List<PetModel> pets) {
        this.pets = pets;
    }
}

public class PetModel extends Model {
    private String id;
    private String name;
    @ManyToOne(target = UserModel.class)
    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

```

这里宠物和用户是多对一的关系。

我们给这个控制器增加一个查询接口：

```java
@GET
public ApiResponse<List<UserModel>> queryUser(QueryOptions options){
    return queryList(options);
}
```

这里我们使用了从`ModelController<T>`继承的`queryList(options)`方法，即可直接实现对`UserModel`模型的查询功能，这个查询功能使用了`QueryOptions`中的参数，各个参数功能如下：

|参数|类型|作用|
|---|---|---|
|page_size|integer|指定查询页大小|
|page|integer|指定查询页码|
|limit|integer|指定查询行，类似mysql中的limit|
|offset|integer|查询偏移量，表示偏移几行开始返回，可以了limit配合使用实现分页|
|total|boolean|是否返回查询总数|
|orderby|string|排序表达式，如：orderby=name desc或orderby=name asc|
|filters|string|查询表达式，通过特定的语法实现查询过滤|
|select|string|查询字段，可以指定查询的字段，如select=name,id|
|expand|string|展开表达式，可以指定有关联的对象展开查询|

### select表达式

select表达式的作用是指定查询的属性，类似SQL的`select`关键字，我们先来看一个例子:

```
GET /user/7D82fxxw1jn                :请求1
GET /user/7D82fxxw1jn?select=id,name :请求2
```

请求1返回的结果如下：

```javascript
{
    id:"7D82fxxw1jn",
    name:"Tom",
    createdAt:'2016-12-05'
}
```

请求2返回的结果如下：

```javascript
{
    id:"7D82fxxw1jn",
    name:"Tom"
}
```

从上面两个请求的结果我们可以知道，select其实是指定了只要查询哪些属性即可。

注意，无论是查询单行记录还是查询多行记录，select的使用方法是一样的，区别只是返回的结果是一个json对象还是一个json数组对象。

## filters表达式

filter表达式的作用是对查询结果进行过滤，类似SQL中的where关键字，我们先来看一个例子：

```
GET /user?select=id,name                     :请求1
GET /user?filters=name eq Tom&select=id,name :请求2
```

请求1的返回结果如下：

```javascript
[
    {
        id:"7D82fxxw1jn",
        name:"Tom"
    },
    {
        id:"7hvq3wggMsn",
        name:"Jerry"
    }
]
```

请求2的返回结果如下:

```javascript
[
    {
        id:"7D82fxxw1jn",
        name:"Tom"
    }
]
```

从上面的例子我们可以看到，filters表达式是通过一定的语法规则来定义查询过滤条件的，这个表达式和SQL的`where`表达式有些相似，但是由于html对特殊符号的限制，filters表达式是通过*操作符*来指定操作的，而不是通过符号。

filters表达式中规定的操作符和作用如下：

|操作符|SQL|说明|示例|
|----|----|----|----|
|eq|=|等于|filters=name **eq** Tom|
|lt|<|小于|filters=age **lt** 20|
|le|<=|小于或等于|filters=age **le** 20|
|gt|>|大于|filters=age **gt** 20|
|ge|>=|大于或等于|filters=age **ge** 20|
|ne|!=|不等于|filters=age **ne** 20|
|like|like|模糊查询|filters=name **like** T%|
|in|in|在指定的值内查询|filters=name |
|is|is|相当于sql关键字is|filters=name **is** null|
|not|is not|相当于sql关键字is not|filters=name **not** null|
|and|and|表示多条件间的且关系|filters=name **like** T% **and** age **lt** 20|
|or|or|表示多条件间的或关系|filters=name **like** T% **or** age **lt** 20|
|()|()|表达式组，可以用来改变表达式的优先级关系|filters=name **like** T% **and** **((**age **lt** 20**)** **or** **(**name **like** %y**))**|

从上面的表格中我们也可以看出，操作符之间是可以组合的。

## orderby表达式

orderby表达式主要是用于排序，类似sql的`order by`关键字。

我们先来看一个例子：

```
GET /user?select=id,name                   :请求1
GET /user?orderby=name desc&select=id,name :请求2
```

请求1的返回结果:

```javascript
[
    {
        id:"7D82fxxw1jn",
        name:"Tom"
    },
    {
        id:"7hvq3wggMsn",
        name:"Jerry"
    }
]
```

请求2的返回结果：

```javascript
[
    {
        id:"7hvq3wggMsn",
        name:"Jerry"
    },
    {
        id:"7D82fxxw1jn",
        name:"Tom"
    }
]
```

从上面的结果可以看出，我们可以通过指定排序属性和排序方向来修改查询结果的排序。

## expand表达式

expand表达式也叫展开表达式，主要是用来解决一些需要额外返回的补充信息的需求的。我们先来看一个例子：

```
GET /user/7h72GggUMsn?            :请求1
GET /user/7h72GggUMsn?expand=pets :请求2
```

请求1的查询结果如下：

```javascript
{
    id:"7D82fxxw1jn",
    name:"Tom",
    createdAt:"2016-12-06"
}
```

请求2的查询结果如下：

```javascript
{
    id:"7h72GggUMsn",
    name:"Tom",
    createdAt:"2016-12-06"
    pets: [
        {
            id: "7382f3x4jn",
            name: "dog",
            userId: "7h72GggUMsn"
        }
    ]
}
```

这里我们可以看出expand表达式的作用，即指定展开某个关系关联起来的记录信息，在本次查询中返回。展开参数还能指定只展开某些属性，比如：

```
GET /classes/7h72GggUMsn?expand=pet(id,name)
```

查询结果如下：

```javascript
{
    id:"7h72GggUMsn",
    name:"Tom",
    createdAt:"2016-12-06"
    pets: [
        {
            id: "7382f3x4jn",
            name: "dog"
        }
    ]
}
```

> **注意：** expand表达式虽然很方便，但是是基于模型之间的关系查询的，也就是说，如果要使用expand表达式，模型的定义必须是有关系的，比如这里的
>
> ```java
> // user 
> @Relational
> private List<PetModel> pets;
>
> // pet
> @ManyToOne(target = UserModel.class)
> private String userId;
> ```
>
> 这里是声明了pet和用户是多对一的关系，而在用户模型中声明了一个pet的属性列表，并且注解了该属性是一个关系属性(通过`@Relational`)声明。

