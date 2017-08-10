# 配置实体接口

除了控制哪些实体需要自动生成接口外，我们还可以控制生成实体哪些路由方法的接口。

在前面我们可以从生成效果示例代码中看出，目前 Restd 可以为我们自动生成的接口有五个，分别是：

* 单个查询：find
* 批量查询：query
* 创建实体：create
* 修改实体：update
* 删除实体：delete

前两个 find 和 query 接口可以归类为 read 类接口，就是只做查询不会操作数据的**只读接口**；后三个 create、update 和 delete 接口可以归类为 write 类接口，就是只提交操作不查询的**只写接口**。

明确以上几点后我们就可以来了解具体相关的配置项了。

> 注意：以下配置项会有重叠的地方，优先级以最具体的为最终配置。在本文档中，这些配置项的介绍以优先级先小后大为顺序介绍。

## apis.api.restd\#readonly 表示全局是否只生成只读接口

此配置项默认为 false，即默认自动生成所有接口；当值为 true 时，只自动生成只读接口。

如下例由于 Restd 配置中没有对具体实体指定生成哪些接口，所以按照 apis.api.restd\#readonly 属性会只生成只读接口：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<apis xmlns="http://www.leapframework.org/schema/webapi">
    <api name="restd" base-path="/" restd-enabled="true">
        <restd readonly="true">
        </restd>
    </api>
</apis>
```

## apis.api.restd.readonly-models 指定哪些实体只生成只读接口

此配置项的配置方式与 included-models 和 excluded-models 的配置方式差不多，都是指定实体名列表，使用英文逗号分隔多个。指定的这些实体如果没有更具体的配置，将会只生成只读接口。用法如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<apis xmlns="http://www.leapframework.org/schema/webapi">
    <api name="restd" base-path="/" default-anonymous="true" restd-enabled="true">
        <restd readonly="false">
            <readonly-models>User,Pet</readonly-models>
        </restd>
    </api>
</apis>
```

## 在 apis.api.restd.model 中指定具体实体生成只读接口或只写接口

在 &lt;restd&gt; 元素中可以包含 &lt;model&gt; 元素，主要是用于指定具体实体生成哪些接口，因此需要 model 元素上的 name 属性是必填项，就是指定要配置的单个实体的名称。

通过用布尔值指定 model 元素上 read 和 write 属性就可以控制当前实体是否生成只读接口还是只写接口。用法如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<apis xmlns="http://www.leapframework.org/schema/webapi">
    <api name="restd" base-path="/" restd-enabled="true">
        <restd readonly="false">
            <model name="User" read="true" write="true"/>
        </restd>
    </api>
</apis>
```

## 在 apis.api.restd.model 中指定具体实体生成哪些接口

与以上配置项相比，这一配置项是颗粒度最小的，也是优先级最大的。按照之前提到的，我们可以控制是否生成的接口目前一共有五个。用法如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<apis xmlns="http://www.leapframework.org/schema/webapi">
    <api name="restd" base-path="/" restd-enabled="true">
        <restd readonly="false">
            <model name="User"
                    create="true"
                    delete="true"
                    update="true"
                    find="true"
                    query="true"
            />
        </restd>
    </api>
</apis>
```

> 最佳实践：
>
> * 由于在实际项目中，一个实体的 API 接口很多情况下不仅仅只是增删改查，还会有很多比较复杂的接口，这个时候可以将 Restd 和自定义 API Controller 方式结合：基础接口使用 Restd 生成，复杂接口根据上几章介绍的新建接口的方式编写。
>
> * 遇到一些基础接口需要做其他自定义操作，Restd 自动生成的逻辑无法满足时，可以配置 Restd 不生成该接口，而是开发人员自己实现。
>
> * 开发过程中需要注意自定义的接口路径不能与 Restd 自动生成的接口路径冲突，冲突路径在实际请求发起时 Leap 将会报错。