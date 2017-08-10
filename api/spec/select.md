# 字段选择

字段过滤用于选择只返回需要的字段，减少数据传输大小，适用于移动环境。

字段过滤请求参数描述见下表：

| 参数 | 类型 | 作用 | 必填 |
| --- | --- | --- | --- |
| select | string | 返回字段，可以指定返回的字段，如 `select=name,id`。多个字段之间使用英文空格分隔。 | 否