# 动态sql应用

前面我们已经知道如何配置sql了,现在让我们来看看leap-orm更加强大的动态sql功能.

### 命名参数
前面我们已经知道,可以用过`:`前缀来给sql的参数设置命名占位符.在动态sql中,我们提供了更加完整的命名参数:
```
#param#:已参数传递的方式执行sql
$param$:以值替换的方式执行sql
```
简单的说,如果我们配置了如下的sql:
```sql
SELECT * FROM leap_user WHERE name like #name# OR login_id like '%$loginId$%'
```
那么当我们好传入参数`{name:"张三",loginId:"zhangsan"}`执行sql的时候,最终执行的sql如下:
```
SQL  : SELECT * FROM leap_user WHERE 1=1 and name like ? OR login_id like '%zhangsan%'
ARGS : ['张三']
```
从上面我们可以看出,`#name#`相当于设置占位符,最后通过传参的方式设置参数,`$param$`则采用的是sql拼凑的方式.

> **注意:**
> 采用sql拼凑的方式设置动态sql,是很有可能导致sql注入的,因此建议在命名参数的前后加上单引号防止sql注入.

### 条件占位符
在sql应用中,很多时候我们通过sql查询的时候,会有一些动态参数,我们希望当这个参数有值的时候,这个值就作为sql的条件语句,当这个参数没有值的时候,就不要在sql中添加这个条件语句,单独为了一个条件语句写两个相差无几的sql显然是不合适的,也不利于维护,因此leap-orm中提供了动态参数占位符的语法:
```sql
SELECT * FROM table where 1=1 {?and field like #field#}
```
这其中`{?and field like #field#}`就是条件占位符.

这个条件占位符以`{?`开始,`}`结束, 中间部分均为条件, 每个条件占位符只能有一个参数,当这个参数有传给sql时,这个条件占位符会生效,即会将条件拼入sql,如果没有传入这个参数,或者传入参数为null时,则整个条件占位符都会被替换为空字符串.  
在示例的条件占位符中,假设执行sql的时候,我们的参数是`{field:"name"}`,则最后执行的sql是:
```
SQL  : SELECT * FROM table where 1=1 and field like ?
ARGS : ['name']
```
如果传入的参数是`{}`,则最后执行的sql是:
```
SQL  : SELECT * FROM table where 1=1
ARGS : []
```
现在你可能已经明白为什么要加一个`1=1`的条件了.因为在动态sql中,我们不能确定field参数是否会传,因此也无法确定sql中是否需要`where`,这里加上`1=1`这个条件,就保证一定需要加上`where`,参数无论怎么传都不会导致sql语法错误.

> **注意:**
> 这里的动态参数有一个特殊的情况,就是参数为null和没有传递这个参数,这是两种情况,也就是说,如果不希望条件表达式生效,必须不传指定参数,而不能传null.
