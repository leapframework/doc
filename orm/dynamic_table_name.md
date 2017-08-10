# 动态表名

动态表名，顾名思义，就是在实体上指定一个动态表名，在执行该实体相关 SQL 时自动解析动态表名，因此指定的动态表名一般是一个动态表达式。

Leap 提供动态表名功能的出发点是**针对简单的分表功能进行支持**，实际项目可以借助这个功能实现自己的分表逻辑。

下面就以一个简单的分表实现介绍动态表名的使用。

假设我们现在有一个记录用户订单的数据库表 `customer_order` 需要做分表。

## 配置实体

首先我们在用户订单的实体上 `CustomerOrder` 配置好我们需要的动态表名。

即在 `@Table` 注解上指定 `dynamicTableName` 属性。

```java
@Table(dynamicTableName = "customer_order_${env.getYearByCreatedAt}")
public class CustomerOrder extends Model {

    @Id
    private String id;

    @Column
    private String name;

    @Column
    private Date createdAt;

    // 省略 getter 和 setter
}

```

为简化代码，我们假设订单实体上只有以上几个字段：`id` 作为主键，`name` 作为订单名称，`createdAt` 记录下单时间。

## 配置环境变量

分表逻辑一般需要根据具体的业务情况制定。

在这个例子里面我们的订单数据的时间相关性较大，因此我们假定按照下单时间的年份分表。

上例可以看到我们给订单实体指定的动态表名是：`customer_order_${env.getYearByCreatedAt}`。

里面使用了一个环境变量 `getYearByCreatedAt`，因此我们新建并配置这个环境变量：

```java
@Bean(name = "getYearByCreatedAt")
public class GetYearByCreatedAt implements VariableWithContext {

    @Override
    public Object getValue(ElEvalContext context) {

        if(context.isVariableResolved("createdAt")) {

            Object obj = context.resolveVariable("createdAt");

            if(obj instanceof Date) {

                Date date = (Date) obj;

                String year = Dates.format(date, "yyyy");

                return year; // sharding table by year
            }
        }

        return "default";
    }
}
```

在这个类里面，我们做了以下这些事情：

- 使用 `@Bean` 标注这个类是一个 bean 并且指定 bean 名称；

- 实现 `VariableWithContext` 接口，这样我们可以在代码中获取上下文。当然也可以改为实现 `Variable`，只是在代码中将获取不到上下文；

- `getValue` 方法中，我们判断传递参数中是否有类型为 Date 的 `createdAt` 参数，如果有则提取年份，并返回年份字符串。如果没有，则返回默认字符串。

因此当订单实体相关的 SQL 执行时，Leap 将会把 sql 参数传递给这个环境变量，通过环境变量的返回值构造好最终的表名，并执行。

## 使用示例

配好以上配置之后，假设我们现在针对用户订单实体做一些数据操作。

例如新增订单数据：

```java
CustomerOrder order = new CustomerOrder();
order.setName("name1");
order.setCreatedAt(new Date());
order.create();
```

假设今年是2017年，则执行以上操作后控制台将打印出以下 SQL 信息并执行：

```
 SQL  : insert into customer_order_2017(id_,name_,created_at) values (?,?,?)
 ARGS : ['bf862850-1048-4ca1-a5d2-7727baa81fd0','name1',2017-10-07 00:00:00]
```

可以看到表名被解析为 `customer_order_2017`。

## 使用注意事项

- 构造好最终表名之后，Leap 会自动检测数据库中是否有该表，如果没有，则会**自动创建**。

- 以上分表逻辑只是一个示例，Leap 不提供具体的分表逻辑，实际项目需要自己指定并维护。

- 在环境变量中除了可以获取 SQL 参数之外，还可以获取其他相关配置，如果在 web 环境中，还可以获取请求上下文，如前端请求参数等等。

- 在针对分表实体的增删改查，或者实体的 SQL 查询时，都会触发动态表名的解析。

> 分表是一个复杂的功能，Leap 目前只考虑以这种动态表名的方式提供分表支持，实际项目在使用这种方式分表之前需要考虑清楚是否符合项目需求再使用。