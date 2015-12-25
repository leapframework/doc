# LEAP 配置详解
leap在设计的过程便严格遵循约定大于配置的原则,因此需要的初始化配置非常的少,但是为了保证框架的灵活性和适应产品化的开发,leap还是提供了非常强大的配置功能,绝大多数的默认规则可以通过配置来覆盖,在本章我们将详细解析leap的配置功能.

在了解leap框架的配置功能之前,需要先了解leap的工程目录.

- leap的入口配置文件是config.xml,这个配置文件必须在`源文件根目录/conf`目录下.
- leap的其他配置全部必须在`源文件根目录/conf`目录下或其子目录下.
- 属性预处理配置比较特殊,放在`源文件根目录/META-INF/service`目录下.

以上的几个工程目录规则可以直观的理解为如下的目录结构:

    project
      ├　src
      └　resource
          ├　conf
          │　　├　config.xml
          │　　└　other
          │　　　　└　other.xml
          └　META-INF
          　　 └　pre_properties_process
这里`project`是工程根目录,`src`和`resource`是源代码文件夹,`conf`是配置文件目录,`config.xml`是入口配置文件,`other`是`conf`的子目录,`other.xml`是其他配置文件,`pre_properties_process`是属性预处理配置文件.
> 属性预处理配置文件是一个无后缀的文本文件.

了解了工程的配置文件结构,接下来我们继续看具体的配置功能.

- [属性配置](properties.md)
- [bean配置](beans.md)
- [sql配置](sqls.md)
- [环境变量配置](environment.md)
- [属性预处理配置](property_pre_process.md)
- [覆盖leap默认配置](override_default.md)