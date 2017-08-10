# 使用 orderby 参数

orderby 参数对查询的数据进行排序，它的参数格式是：

`字段名  排序方向`

排序方向有 asc 和 desc 可选。例如 `orderby=name desc` 表示按名称倒序排序，注意中间的空格。多个排序字段使用英文逗号分隔。

## 配置

在默认情况下，实体字段都是无法在 orderby 参数中使用的。

只有明确在实体字段上标注 **`@Sortable`** 注解，才可以使用这个字段的排序功能。

如下例配置 User 实体可以通过 name 字段做排序：

```java
@Table
public class User extends Model {
    @Id
    private String id;

    @Column
    @Sortable
    private String name;

    // 省略 getter 和 setter
}
```