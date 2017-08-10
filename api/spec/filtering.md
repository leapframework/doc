# 数据过滤

数据过滤的参数描述如下：

| 参数 | 类型 | 作用 | 必填 |
| --- | --- | --- | --- |
| filters | string | 查询表达式，通过特定的语法实现查询过滤 | 否

filters 参数值内可使用的查询操作符见下表：

| 操作符 | SQL | 说明 | 示例 |
| :--- | :--- | :--- | :--- |
| eq | = | 等于 | filters=name **eq** Tom |
| lt | &lt; | 小于 | filters=age **lt** 20 |
| le | &lt;= | 小于或等于 | filters=age **le** 20 |
| gt | &gt; | 大于 | filters=age **gt** 20 |
| ge | &gt;= | 大于或等于 | filters=age **ge** 20 |
| ne | != | 不等于 | filters=age **ne** 20 |
| like | like | 模糊查询 | filters=name **like** T% |
| in | in | 在指定的值内查询 | filters=name **in** n1,n2  |
| is | is | 相当于sql关键字is | filters=name **is** null |
| not | is not | 相当于sql关键字is not | filters=name **not** null |
| and | and | 表示多条件间的且关系 | filters=name **like** T% **and** age **lt** 20 |
| or | or | 表示多条件间的或关系 | filters=name **like** T% **or** age **lt** 20 |
| \(\) | \(\) | 表达式组，可以用来改变表达式的优先级关系 | filters=name **like** T% **and** \(\(age **lt** 20\) **or** \(name **like** %y\)\) |

> 注意：
> - 参数、操作符和值之间需要空格。
> - 百分号 `%`、空格等特殊字符在传递之前需要做 URL 编码转换。