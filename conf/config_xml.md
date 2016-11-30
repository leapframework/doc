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

在`config`元素下我们可以配置property属性和系统属性。

## property配置

除了leap的一些系统配置外，我们可以自定义属性变量，即property，示例如下：

```
<property name="property1">property1</property>
<property name="property2" value="property2"></property>
<properties>
    <property name="property3">property3</property>
</properties>
<properties prefix="prop">
    <property name="property4">property4</property>
</properties>
```

上述配置最终可以得到如下几个属性变量：

|key|value|
|----|----|
|property1|property1|
|property2|property2|
|property3|property3|
|prop.property4|property4|

## 系统配置

leap提供了比较多的系统配置，除了我们前面用过的`base-package`，还有很多：

**base-package**

配置leap的扫描根目录，如：

```xml
<base-package>demo</base-package>
```

**debug**

设置应用是否使用调试模式启动，如果不指定，leap会自动判断当前是生产环境还是开发环境决定是否启用调试模式，如：

```xml
<debug>true</debug>
```

**additional-packages**

补充扫描包，某些情况下我们除了leap的根目录之外，还需要leap帮我们扫描其他的包，可以使用这个配置：

```
<additional-packages>
    package1
    package2
</additional-packages>
```

**default-charset**

默认编码，不指定的情况下为UTF-8，如下：

```xml
<default-charset>UTF-8</default-charset>
```

**default-locale**

默认语言，不指定的情况下，会自动使用当前系统的语言，如：

```xml
<default-locale>zh_CN</default-locale>
```