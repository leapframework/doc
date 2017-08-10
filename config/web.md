# 配置 web.xml

开发 Web 工程，我们需要让 Leap 处理接管我们的请求。

因此需要在 `src/main/webapp/WEB-INF/web.xml` 文件中配置如下内容：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://java.sun.com/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
	version="3.0">

	<display-name>Hello</display-name>

    <filter>
    	<filter-name>app-filter</filter-name>
        <filter-class>leap.web.AppFilter</filter-class>
    </filter>

    <filter-mapping>
        <filter-name>app-filter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

</web-app>
```