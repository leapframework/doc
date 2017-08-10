# 获取用户信息

在 Leap 中用户由 `leap.core.security.UserPrincipal` 类表示，该类有以下几个基本信息：

```java
public interface UserPrincipal ... {
    //用户ID
    Object getId();

    //用户姓名
    String getName();
    
    //用户登录名
    String getLoginName();
    
    //是否匿名用户
    boolean isAnonymous();
}
```

**在代码中获取该用户对象有三种方式：**

1. 路由方法参数绑定
    
    ```java
    public void userinfo(UserPrincipal user) {..}
    ```
    > `UserPrincipal` 对象在Leap中是默认的上下文参数，会自动绑定。
    
2. 通过 `leap.web.Request` 对象获取

    ```java
    public void userinfo(Request request) {
        UserPrincipal user = request.getUser();
    }
    ```

    > `Request` 对象在 Leap 中是默认的的上下文参数，会自动绑定。
    
    或者：
    
    ```java
    public void userinfo() {
        UserPrincipal user = Request.current().getUser();
    }
    ```
    
3. 通过 `leap.core.security.SecurityContext` 对象获取
    
    ```java
    public void userinfo() {
        UserPrincipal user = SecurityContext.user();
    }
    ```
