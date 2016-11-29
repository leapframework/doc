# ORM接口说明

leap orm提供了非常丰富的接口,可以帮助我们完成绝大部分的数据库查询和操作功能,少数无法通过直接接口完成的功能,也可以用过sql配置的方式完成,本节我们将详细介绍leap orm的相关接口.

leap orm的接口包含了两大部分,Model接口和DAO接口:  

* Model接口是ActiveRecord模式的核心,操作单行记录和单表查询非常便捷和快速.
* DAO接口是传统的dao操作数据库模式,使用上没有Model接口方便,但是DAO接口灵活多变,能够弥补ActiveRecord在联表查询方面的劣势.

在说明两种接口的使用方式之前,我们需要先了解一些相关的概念.

## leapQL

leap orm可以完成大多数的数据库操作,但是有些时候leap的提供的接口也不能完全避免写sql,因此leap有一套自定义的类SQL查询语法,我们称为leapQL,这种查询语法和标准SQL几乎是相同的,但是相比普通的SQL更加强大,主要表现为:

* 允许对象查询
* 完全兼容标准sql
* 允许sql传参
* 允许动态sql

### SQL翻译

在leapQL执行之前,会先通过leapQL的语法解析器将leapQL变成标准的sql进行查询,这个过程我们称为SQL翻译

### 对象查询

leapQL的对象查询指的是可以在sql语句中直接使用对象的属性和类名进行查询,举例如下:

假设我们有一个Model类:

```java
@Table("t_user")
public class User{
	@Column(name="user_id")
	public String id;
	@Column(name="user_name")
	public String name;
	@Column(name="user_age")
	public Integer age;
}
```

当我们执行如下代码时:

```java
User.query("SELECT name, age FROM User").list();
```

上面调用的方法是leap orm提供的Model类接口,这里暂时不用去关注,重点是上面的SQL(`SELECT name, age FROM User`)虽然看起来是标准的SQL语法,但是实际上是leapQL,经过sql翻译之后,会变成如下标准sql:

```sql
SELECT user_name, user_age FROM t_user
```

这就是leap的对象查询,可以直接在SQL中写对象的属性和对象的类名即可自动翻译成该对象映射的数据库表的属性和表名.

### 兼容标准sql

上面的java查询代码和下面的代码结果是一致的:

```java
User.query("SELECT user_name, user_age FROM t_user").list();
```

这就是leapQL的强大之处,既能使用leapQL的对象查询,也能完全兼容标准的sql语法.

### sql传参

leapQL允许我们在sql中设置参数占位符,并在实际运行过程中动态设置参数,这里动态设置参数有两种方式:

* 参数占位符
* 命名参数占位符

#### 参数占位符

参数占位符一般使用`?`号表示,我们可以在一个leapQL中保留一个`?`作为占位符,我们称为**参数占位符**,在这个占位符表示这里需要一个参数,这个参数会在sql执行的时候传进来,各个参数占位符的唯一区别方式就是参数占位符的位置,因此使用参数占位符的时候,需要注意传递参数的顺序,如下:

```java
User.where("name=? AND id=?","jim","123").list();
```

上面这段代码不是完整的sql,但也是leapQL的语法,这里我们不用关注`where()`方法,这是Model类的方法,我们只需要在`where`方法中传入leapQL的查询条件即可,相当于`where()`方法会帮我们拼凑sql的前半部分`SELECT * FROM User WHERE `.

这里我们注意到,where条件中包含了两个`?`参数占位符,在后边的可变传参中我们传入了两个参数`jim`和`123`,那么上面的代码最终经过sql翻译后如下:

```sql
SELECT * FROM t_user WHERE user_name='jim' AND user_id='123'
```

这里要注意,可变传参的值的顺序决定了leapQL中每一个参数占位符的值.

#### 命名参数占位符

参数占位符虽然可以让leapQL能进行动态传参,但是很多时候我们可能无法确定参数的顺序,这种情况下就没办法使用参数占位符了,leapQL已经考虑到这点了,因此提供了另一个参数占位符:**命名参数占位符**.

命名参数占位符的语法如下:

```
:paramName
```

以冒号`:`开头表示参数占位符后边跟一个字符串表示参数名,以空格结束

命名参数占位符相当于给参数占位符起一个名称,后续参数通过名称对应到参数占位符中去,示例如下:

```java
Map map = new HashMap();
map.put("id","123");
map.put("name","jim");
User.where(name=:name AND id=:id").param(map).list();
```

上面的代码最终经过sql翻译之后的结果如下:

```sql
SELECT * FROM t_user WHERE user_name='jim' AND user_id='123'
```

这里我们可以注意到,命名参数的值通过key/value的方式传参数之后,leap会将key与命名参数的参数名相同的value设置为该命名参数的值.


### 动态sql

leapQL的动态sql支持才是真正有威力的功能.很多时候我们需要根据不同的参数生成不同的sql,一个最常见的例子就是动态查询功能,
假设有三个查询条件:

* 用户名
* 用户年龄
* 用户id

我们希望当用户数据查询数据时,如果某个字段有输入,则作为查询条件,如果没有输入,则不作为查询条件,这种情况下,3个查询条件的组合方式非常多,如果使用if-else判断会变得非常麻烦,这个时候动态sql的强大之处就体现出来了,我们写如下leapQL:

```sql
SELECT * FROM User WHERE 1=1
{? AND name=:name}
{? AND age=:age}
{? AND id=:id}
```

上面的sql一共有三个参数,假设我们传入`name="jim",age="20"`,那么经过sql翻译后,最终执行的sql如下:

```
SELECT * FROM User WHERE 1=1 AND name='jim' AND age=20
```

也就是说当参数没有传入时,动态sql表达式就会失效.这就是动态sql的强大之处.

关于动态sql的用法,后续的章节会有更加详细的说明.

## leapQL语法特性

### SQL IN处理

在使用leapQL的过程中,我们经常会遇到类似如下的查询:

```sql
SELECT * FROM User WHERE id IN (id1,id2,....)
```

假设我们通过参数将id的列表传给leapQL,这时我们无法确定id列表的长度,因此无法用参数占位符,如果自己循环拼凑leapQL,又会增加一段繁琐且毫无意义的代码,这点leapQL其实已经考虑到了,我们可以使用sql参数的方式传递一个id数组作为参数值,leapQL进行sql翻译的过程会自动添加`()`并为我们设置参数占位符,比如如下leapQL:

```sql
SELECT * FROM User WHERE id IN :ids
```

假设我们传入的ids参数是如下数值:

```
{"id1","id2","id3"}
```

sql翻译后会变成:

```
SELECT * FROM User WHERE id IN (?,?,?)
```

并将id1,id2,id3分别设置到三个参数占位符中去.

## 数据库映射的相关名称

* Model类:所有继承Model类的类都默认会跟数据库表建立映射关系这种类我们称为**Model类**
* 实体映射类:没有继承Model类,但是以`@Entity`注解的类也会与数据库表建立映射关系,我们称为实体映射类
* 实体映射对象: EntityMapping类型表示实体映射类和数据库表之间的映射关系,这中类我们称为实体映射类