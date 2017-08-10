# 数据分页

数据分页参数需要符合以下规范：

| 参数 | 类型 | 作用 | 必填 |
| --- | --- | --- | --- |
| page\_size | integer | 指定查询页大小 | 否
| page | integer | 指定查询页码，从 1 开始。 | 否
| limit | integer | 指定查询行，类似 MySQL 中的 limit | 否
| offset | integer | 查询偏移量，表示偏移几行开始返回，可以与 limit 配合使用实现分页。从 0 开始。 | 否
| total | boolean | 是否返回查询总数 | 否