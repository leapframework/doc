# 应用监听器

有些时候我们会需要在应用启动之后或者之前进行某些操作,但是不能修改Global的init()方法,或者不能重写init()方法,这种情况下,我们可以自己实现应用监听器来达到这个目的.

## AppListener

AppListener就是leap提供的应用监听器,包含了几个方法:

```java
void preAppConfigure(App app, WebConfigurator c)
void postAppConfigure(App app, WebConfig c)
void preAppInit(App app)
void postAppInit(App app)
void preAppStart(App app)
void postAppStart(App app)
void preAppStop(App app)
void postAppStop(App app)
```

### `void preAppConfigure(App app, WebConfigurator c)`

在web应用配置生效前执行的操作,传入的两个参数分别的当前app的实现对象和web配置的实现对象,这个app的时候一般就是我们的Global的实例对象

### `postAppConfigure(App app, WebConfig c)`

在web应用配置生效后执行的操作

### `postAppInit(App app)`

在应用初始化前执行的操作

### `postAppInit(App app)`

在应用初始化后执行的操作

### `preAppStart(App app)`

在应用启动前执行的操作

### `postAppStart(App app)`

在应用启动后执行的操作

### `preAppStop(App app)`

在应用停止前执行的操作

### `postAppStop(App app)`

在应用停止后执行的操作

通过上面的接口,我们可以在应用初始化的过程的各个环节中加入自己的代码来实现各种功能