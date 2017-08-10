# 接入授权服务器

在进行安全相关的设置和开发前，需要接入一个授权服务器完成用户身份认证。

## 环境准备

* 获取到授权服务器的地址，如```https://sso.example.com```
* 在授权服务器上注册应用，获取```client_id```和```client_secret```

> 如何获取服务器地址以及注册应用不在此文档描述范围

## 参数配置

假设使用获取到的参数如下：

```
server_url     : https://sso.example.com
client_id       : client1
client_secret   : secret1
```
> 其中```server_url```是授权服务器的的地址

修改```src/main/resources/conf/config.xml```，添加一个```properties```配置：

```xml
<config xmlns="http://www.leapframework.org/schema/config">
    
    ...
    
    <properties prefix="oauth2">
        <property name="enabled"        value="true"/>
        <property name="server_url"     value="https://sso.example.com"/>
        <property name="client_id"      value="client1"/>
        <property name="client_secret"  value="secret1"/>
    </properties>
    
    ...
    
</config>
```

## 验证配置是否生效

以[快速开始 - 搭建 Web 工程](/quick_start/web/README.md)中的示例工程为例，添加上面的配置后重新启动应用：

```
**[terminal]
mvn jetty:run
```

打开浏览器访问地址```http://localhost:8080/user/get_hello_user```，返回以下JSON内容：

```json
{"code":"UNAUTHORIZED","message":"Unauthorized"}
```

上面返回的信息表示该请求没有经过身份认证，说明配置已经生效。