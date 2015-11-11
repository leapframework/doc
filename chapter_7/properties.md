# 属性配置
本节目录

- [config.xml结构](#docstructure)
- [系统属性](#sysproperty)
- [自定义属性](#customproperty)
- [profile配置](#profile)

#### <i id="docstructure"></i>config.xml结构 ####
leap的属性配置文档,一般是如下结构:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<config xmlns="http://www.leapframework.org/schema/config">
  <!-- 系统属性 -->
  <base-package>leap.demo</base-package>
  <!-- 自定义属性组 -->
  <properties>
    <!-- 自定义属性 -->
    <property name="h2.driverClassName">org.h2.Driver</property>
  </properties>
</config>
```
- 第一行是xml的版本和编码声明
- 根节点是`config`标签
- 根节点下有系统属性标签和自定义的属性组标签
- 属性组标签下可以配置多个自定义属性标签

#### <i id="sysproperty"></i>系统属性 ####
1. `base-package`

    `base-package`属性是leap中最重要的系统属性之一,这个属性的值是一个包路径,作用是配置扫描class的根路径,在这个包或其子包下的所有类都会被leap扫描,并按照leap的默认规则和配置初始化各个bean类和model类.默认值是`app`
示例如下:
```xml
<config xmlns="http://www.leapframework.org/schema/config">
  <base-package>leap.demo</base-package>
</config>
```
2. `debug`

    `debug`属性是一个提供开发测试的属性,值是布尔值(true或false),这个属性的作用是配置系统运行的环境是开发环境(true)还是生产环境(false), `debug`属性的默认值是根据环境变化的,在开发环境(maven环境)中默认值是true,打包部署后默认值是false
> - 在开发环境下,leap会自动检测工程文件的状态,自动加载有变化的资源文件,使得修改资源文件不需要重启应用容器.
> - 在生产环境下,leap会自动停止检测工程文件,同时减少不必要的调试日志输出,提高系统的性能
> - 在开发环境中,leap通过是否maven环境来判断默认值,如果不是maven环境,`debug`的默认值将是false

    示例如下:
```xml
<config xmlns="http://www.leapframework.org/schema/config">
  <debug>false</debug>
</config>
```

3. `default-charset`

    `default-charset`是设置应用默认编码的属性,这个属性用于应用中所有需要编码的位置,比如web应用的编码拦截器,读取模板视图时的文件编码等等.如果没有配置的话,默认是UTF-8.
    
    示例如下:
```xml
<config xmlns="http://www.leapframework.org/schema/config">
  <default-charset>UTF-8</default-charset>
</config>
```
4. `default-locale`

    `default-locale`是设置方言的属性,主要用于设置应用国际化,包括视图和信息.
    
    示例如下:
```xml
<config xmlns="http://www.leapframework.org/schema/config">
  <default-locale>zh_CN</default-locale>
</config>
```
5. 
#### <i id="customproperty"></i>自定义属性 ####
#### <i id="profile"></i>profile配置 ####