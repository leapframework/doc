# 使用 filters 参数

filters 参数可以实现查询结果过滤，其实就相当于 SQL 中的 where 从句。

由于查询接口一般都是通过 GET 请求调用的，请求参数是在 URL 中，因此一般 where 从句中会用到的操作符都无法直接使用，需要使用[数据过滤](/api/spec/filtering.md)一节中的查询操作符进行指定。

## 配置

在默认情况下，实体字段都是无法在 filters 参数中使用的。

只有明确在实体字段上标注 **`@Filterable`** 注解，才可以使用这个字段的过滤查询功能。

如下例配置 User 实体可以通过 name 字段做数据过滤：

```java
@Table
public class User extends Model {
    @Id
    private String id;

    @Column
    @Filterable
    private String name;

    // 省略 getter 和 setter
}
```