# sql配置
我们已经了解ActiveRecord的一些基本用法了,但是ActiveRecord的优势在于单表查询和操作,联表查询目前不能直接通过ActiveRecord进行,leap-orm提供了sql配置的方式进行联表查询.

在`conf`目录下添加`sqls.xml`,也可以添加`sqls`目录,并在目录下添加任意名称的'.xml'文件:
```
src/main/resources
      └　conf
          ├　sqls
          │   ├　user.xml
          │   └　post.xml
          ├　beans.xml
          ├　config.xml
          └　sqls.xml
```
这里我们创建了`sqls.xml`用来保存配置的sql,同时为了预防未来sql配置比较多,在`sqls`目录下创建了`user.xml`和`post.xml`,按照模块划分sql配置.

> leap扫描sql的时候,会同时扫描`sqls.xml`和`sqls`目录下的所有`.xml`文件

现在我们先看看`sqls.xml`的内容:
```xml

```