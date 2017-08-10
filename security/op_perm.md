# 操作权限控制

## 实现示例

Leap 本身没有提供内置的操作权限控制，我们可以通过**路由拦截器**来拦截所有请求，并在拦截器中实现您的权限控制规则。

下面是路由拦截器的示例代码：

[import](codes/PermissionInterceptor.java)

配置 `src/main/resources/beans.xml` 让该拦截器生效：

```xml
<beans xmlns="http://www.leapframework.org/schema/beans">

    <bean type="leap.web.action.ActionInterceptor" class="hello.interceptors.PermissionInterceptor"/>
    
</beans>
```

## 验证测试

发起不带 `secret` 参数的请求，将会访问失败：

```
**[terminal]
curl http://localhost:8080/greeting/perm
```

返回失败信息：

```json
{"code":"FORBIDDEN","message":"No permission"}
```

再次发起带上 `secret` 参数的请求，将会访问成功：

```
**[terminal]
curl http://localhost:8080/greeting/perm?secret=1
```

返回成功信息：

```json
"OK"
```