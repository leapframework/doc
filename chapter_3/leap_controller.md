# 控制器和action
### 新建源文件包
我们在[初始化配置](init_config.md)的时候,配置的`base-package`是`leap.project`,因此我们所有需要leap管理的类都应该放在这个包或者其子包下(这也是leap推荐的做法).  
我们先创建一个包:
```
leap.project
```
在这个包下创建一个名为`Global`的类,这个类继承自`leap.web.App`:
```
leap.project
　　　　└　Global.java
```
`Global.java`的内容如下:
```java
package leap.project;

import leap.web.App;

public class Global extends App {

}
```
