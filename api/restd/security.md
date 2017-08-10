# Restd 安全

使用 Restd 生成的接口默认是非匿名访问的，如果我们需要某些接口可以匿名访问，可以有两种配置方式。

## 全局配置匿名访问

即在 apis.api 配置元素上指定 default-anonymous 属性，这种方式自动生成的所有接口都是可以匿名访问的。示例如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<apis xmlns="http://www.leapframework.org/schema/webapi">
    <api name="restd" base-path="/" default-anonymous="true" restd-enabled="true">
    </api>
</apis>
```

## 在具体实体上配置匿名访问

即在 apis.api.restd.model 配置元素上指定 anonymous 属性，这种方式只对单个实体进行匿名控制，对单个实体指定该属性会覆盖全局属性。示例如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<apis xmlns="http://www.leapframework.org/schema/webapi">
    <api name="restd" base-path="/" default-anonymous="false" restd-enabled="true">
        <restd>
            <model name="User" anonymous="true"/>
        </restd>
    </api>
</apis>
```

