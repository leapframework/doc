# 配置实体

默认情况下，如果只是**将 rest-enabled 置为 true，Leap 会扫描所有实体并为这些实体生成 API 接口**。

## 白名单

当我们需要指定**只对某些实体生成接口时，我们可以使用 included-models 配置项指定实体名，多个使用英文逗号分隔**。例如：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<apis xmlns="http://www.leapframework.org/schema/webapi">
    <api name="restd" base-path="/" restd-enabled="true">
        <restd>
            <included-models>User,Pet</included-models>
        </restd>
    </api>
</apis>
```

## 黑名单

当我们默认**对所有实体生成接口，只是排除部分实体时，我们可以使用 excluded-models 配置项指定排除的实体名，多个使用英文逗号分隔**。例如：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<apis xmlns="http://www.leapframework.org/schema/webapi">
    <api name="restd" base-path="/" restd-enabled="true">
        <restd>
            <excluded-models>User,Pet</excluded-models>
        </restd>
    </api>
</apis>
```

> 建议：以上配置已经满足日常使用上的两种情况，因此不建议 included-models 和 excluded-models 两个配置项一起使用，以免造成配置错误。
