# 使用 profile 切换配置

在实际开发中，我们需要根据不同环境切换不同的 profile 来管理一些配置信息，如数据库连接等。

## 基本配置

之前我们在快速开始搭建 Web 工程章节中配置了以下数据源信息：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<config xmlns="http://www.leapframework.org/schema/config">
    <base-package>hello</base-package>

    <properties>
        <property name="db.driverClassName">com.mysql.jdbc.Driver</property>
        <property name="db.url">jdbc:mysql://localhost:3306/demo?useUnicode=true</property>
        <property name="db.username">root</property>
        <property name="db.password">123456</property>
    </properties>
</config>
```

## profile 配置

现在我们改成多 profile 配置，假设现在有本地和开发环境的 profile 分别是 local 和 dev。示例如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<config xmlns="http://www.leapframework.org/schema/config">
	<base-package>my.project.core</base-package>

	<properties if-profile="dev">
		<property name="db.url">jdbc:mysql://1.2.3.4:3306/demo?useUnicode=true</property>
		<property name="db.username">root</property>
		<property name="db.password">123456</property>
	</properties>

	<properties if-profile="local">
		<property name="db.url">jdbc:mysql://localhost:3306/demo?useUnicode=true</property>
		<property name="db.username">root</property>
		<property name="db.password">123456</property>
	</properties>
	
	<properties>
		<!-- db common properties -->
		<property name="db.driverClassName">com.mysql.jdbc.Driver</property>
		<property name="db.validationInterval">10000</property>
		<property name="db.timeBetweenEvictionRunsMillis">10000</property>
		<property name="db.maxActive">1000</property>
		<property name="db.initialSize">10</property>
		<property name="db.maxWait">10000</property>
		<property name="db.removeAbandonedTimeout">60</property>
		<property name="db.minEvictableIdleTimeMillis">10000</property>
		<property name="db.minIdle">0</property>
	</properties>
</config>
```

在 `if-profile="dev"` 块中的配置只有在 profile 为 dev 的时候才会使用，同理也适用于 `if-profile="local"`。

## 指定 profile

然后我们可以在 src/main/resource 下新建一个叫 profile 的无后缀名文件，里面直接指定 profile 名称，如：

```xml
dev
```

这样在运行时将使用对应名称的 profile 。

这是 profile 最简单的用法，Leap 也支持结合 maven 的 profile 打包，不过这对于配置文件的目录结构与 pom.xml 文件有额外的配置要求，后面 Leap 会另外再出指引文档。