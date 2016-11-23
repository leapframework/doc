# config配置

## config配置的目录结构

config配置的入口是`config.xml`，leap默认会扫描`conf/config.xml`文件和`conf/config`目录下所有的xml作为config配置。

比如：

```
conf
    - config
        - config1.xml
        - config2.xml
    - config.xml
    - config3.xml
```

在上面这个目录结构下，`config1.xml`、`config2.xml`、`config.xml`三个文件都会被作为config配置读取，config3.xml则不会读取。

## property配置

config配置的xml