# 获取用户详细信息

在上一章节中介绍了如何获取用户的基础信息，如果希望访问到更详细的用户信息，那么需要自行实现一个 `UserDetailsLookup` 接口。

## 实现 `UserDetailsLookup` 接口

下面是一个示例实现：

[import](codes/RemoteUserDetailsLookup.java)

> 这个示例实现需要接入到 `debug-auth-server` 才能使用。

## 配置 `UserDetailsLookup` 生效

修改 `src/main/resources/conf/beans.xml` ：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.leapframework.org/schema/beans">
    
    ...

    <bean type="leap.oauth2.webapp.user.UserDetailsLookup"
          class="hello.beans.RemoteUserDetailsLookup" override="true" primary="true"/>

</beans>
```
> 框架中已经有一个内置的实现，需要加上 `override="true"` 表示覆盖已有的实现类。

    

