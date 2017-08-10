# 数据权限控制

先举例说明一下数据权限，假设一个**工作任务应用**有以下的需求：

* 普通员工可以查看自己的工作项
* 部门经理可以查看自己管理部门的所有工作项

对于普通员工和部门经理，他们访问应用的入口都是相同的，应用需要根据不同的角色返回不一样的数据结果，这就是数据权限控制。

数据权限是个比较复杂的问题，规则非常灵活，在Leap中并没有内置实现，但提供了一个基础机制，可以相对简单的实现数据权限。

## 查询过滤器（Query Filter）

查询过滤器是 ORM 模块中的功能，默认是关闭的，开启后所有的查询语句都会在 `where` 语句的最后自动加上类似 `@filter(User)` 的表达式。

假设执行查询语句：

```sql
select * from user u where u.name = :name
```

那么开启 `Query Filter` 后将会自动变为：

```sql
select * from user u where ( u.name = :name ){? and ( @filter(User) )}
```

关于查询过滤器的细节在这里不展开细说，下面具体说明如何使用查询过滤器实现数据权限。

## 基于 `Query Filter` 实现数据权限

### 开启

修改 `src/main/resources/conf/config.xml` ，增加以下配置属性：

```xml
<properties prefix="orm">
        <property name="query_filter.enabled" value="true"/>
</properties>
```

### 实现

编写类 `SecurityQueryFilter.java` ：

[import](codes/SecurityQueryFilter.java)

> 返回的表达式语法请看数据访问章节，其中别名 `t.` 是固定写法，在执行中会被替换为真正的别名。

配置 `bean` 生效：

```xml
<bean name="filter" type="leap.orm.sql.SqlTagProcessor" class="hello.beans.SecurityQueryFilter"/>
```