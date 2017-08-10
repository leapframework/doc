# 配置 config.xml

在 `src/main/resources/conf/` 目录下创建 `config.xml` 文件，输入下面的内容：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<config xmlns="http://www.leapframework.org/schema/config">
    <base-package>hello</base-package>
</config>
```

`config.xml` 是 Leap 的配置文件，`base-package` 是 Leap 进行 classpath 扫描的起始路径。


