# bean配置

## bean配置的目录结构

bean配置的入口是`beans.xml`，leap默认会扫描`conf/beans.xml`文件和`conf/beans`目录作为bean的配置。

比如：

```
conf
 - beans
    - bean1.xml
    - bean2.xml
 - beans.xml
 - bean3.xml
```

这里`bean1.xml`,`bean2.xml`,`beans.xml`会被扫描并读取bean配置，`bean3.xml`不会被扫描。

