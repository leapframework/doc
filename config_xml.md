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

## config配置文件

config配置文件的根元素是`config`，leap也包含了自己的xsd文件帮助我们编写配置文件，一个空的配置如下：

```xml
<?xml version="1.0" encoding="UTF-8"?><config xmlns="http://www.leapframework.org/schema/config"    
    mlns:orm="http://www.leapframework.org/schema/orm/config">

</config>

```

## property配置

除了leap的一些系统配置外，我们可以自定义属性变量，即property。