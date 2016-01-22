# leap的maven资源库

leap强烈推荐使用maven进行依赖管理,目前leap建立了的maven库,暂时没有发布到中心库,我们可以通过maven配置leap的资源库来获取leap的jar包.

## 在全局配置leap的maven资源库(推荐)

我们可以在全局的`setting.xml`中配置leap的maven资源库,`setting.xml`的默认路径如下:

```
windows : ${user.dir}/.m2/settings.xml
mac : /Users/usename/.m2/settings.xml或者~/.m2/settings.xml
linux : ~/.m2/settings.xml
```

我们可以在这个配置中添加maven的资源库:

```xml
<!-- leap快照资源库 -->
<repository>
	<id>leap-snapshots</id>
	<url>https://raw.githubusercontent.com/leapframework/repo/master/snapshots</url>
	<snapshots>
		<enabled>true</enabled>
		<updatePolicy>always</updatePolicy>
		<checksumPolicy>warn</checksumPolicy>
	</snapshots>
</repository>
<!-- leap正式资源库 -->
<repository>
	<id>leap-releases</id>
	<url>https://raw.githubusercontent.com/leapframework/repo/master/releases</url>
	<releases>
		<enabled>true</enabled>
		<updatePolicy>never</updatePolicy>
		<checksumPolicy>warn</checksumPolicy>
	</releases>
</repository>
```

将leap的资源库配置添加到`setting.xml`中即可用maven获取到leap的依赖jar包.

## 为项目单独配置maven资源库

如果不想在全局的配置中添加leap的maven资源库,我们也可以单独给工程配置leap的maven资源库.

新建工程项目后,在`pom.xml`中添加如下代码:

```xml
<repositories>
	<!-- leap快照资源库 -->
	<repository>
		<id>leap-snapshots</id>
		<url>https://raw.githubusercontent.com/leapframework/repo/master/snapshots</url>
		<snapshots>
			<enabled>true</enabled>
			<updatePolicy>always</updatePolicy>
			<checksumPolicy>warn</checksumPolicy>
		</snapshots>
	</repository>
	<!-- leap正式资源库 -->
	<repository>
		<id>leap-releases</id>
		<url>https://raw.githubusercontent.com/leapframework/repo/master/releases</url>
		<releases>
			<enabled>true</enabled>
			<updatePolicy>never</updatePolicy>
			<checksumPolicy>warn</checksumPolicy>
		</releases>
	</repository>
</repositories>
```

完整的`pom.xml`参考如下:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>org.leapframework</groupId>
	<artifactId>leap-user-guid</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>war</packaging>
	<repositories>
		<!-- leap快照资源库 -->
		<repository>
			<id>leap-snapshots</id>
			<url>https://raw.githubusercontent.com/leapframework/repo/master/snapshots</url>
			<snapshots>
				<enabled>true</enabled>
				<updatePolicy>always</updatePolicy>
				<checksumPolicy>warn</checksumPolicy>
			</snapshots>
		</repository>
		<!-- leap正式资源库 -->
		<repository>
			<id>leap-releases</id>
			<url>https://raw.githubusercontent.com/leapframework/repo/master/releases</url>
			<releases>
				<enabled>true</enabled>
				<updatePolicy>never</updatePolicy>
				<checksumPolicy>warn</checksumPolicy>
			</releases>
		</repository>
	</repositories>
	<properties>
		<leap.version>0.1.0b</leap.version>
	</properties>
	
	<dependencies>
		<!-- leap框架依赖 依赖leap框架 -->
		<dependency>
			<groupId>org.leapframework</groupId>
			<artifactId>leap</artifactId>
			<version>${leap.version}</version>
			<type>pom</type>
		</dependency>
	</dependencies>
</project>
```