# 数据源配置

在使用leap-orm之前,我们需要先进行一些简单的配置,配置数据库的连接和数据源对象.数据源对象的配置有两种方式,一种是配置bean对象,另一种是通过配置默认属性名生成数据源的对象.

我们仍旧以MVC应用中所使用的[示例工程](download/leap-project.zip)为例分析.

### 配置bean对象

首先我们需要一个配置bean对象的配置文件,在`conf`目录下创建一个`beans.xml`配置文件,如下:

```
src/main/resources
     └　conf
          ├　config.xml
          └　beans.xml       
```

`beans.xml`的内容如下:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.leapframework.org/schema/beans">

</beans>
```

在本示例中我们使用mysql数据库,因此先在`pom.xml`中添加mysql的驱动依赖:

```xml
<dependency>
	<groupId>mysql</groupId>
	<artifactId>mysql-connector-java</artifactId>
	<version>5.1.26</version>
</dependency>
```

现在我们需要配置数据库的连接,一般来说,数据库的连接属性在不用的环境部署会有些许不同,通常我们会将经常变化的配置放在`config.xml`中,其他不常变化的配置按模块划分到不同的配置中去,因此这里我们建议将数据库的配置放在`config.xml`中.  
目前`config.xml`里只有一个`base-package`的配置,我们需要配置其他属性,在配置中添加一个`properties`节点:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<config xmlns="http://www.leapframework.org/schema/config">
	<base-package>demo</base-package>
	<properties>
	
	</properties>
</config>
```

以后我们需要添加的自定义配置都可以在`properties`节点下添加,这里我们先添加jdbc的相应配置:

```xml
<property name="jdbcDriver">com.mysql.jdbc.Driver</property>
<property name="jdbcUrl">jdbc:mysql://127.0.0.1:3306/leap</property>
<property name="jdbcUsername">root</property>
<property name="jdbcPassword">root</property>
```

> **注意:**
> `properties`并不是唯一节点,在同一个`config`节点下可以有多个`properties`节点将不同的配置属性分组.

现在我们配置好数据库的连接配置了,回到`beans.xml`,添加如下配置:

```xml
<bean name="default" type="javax.sql.DataSource" class="leap.core.ds.UnPooledDataSource" primary="true">
    	<property name="driverClassName" value="${jdbcDriver}"></property>
    	<property name="jdbcUrl" value="${jdbcUrl}"></property>
    	<property name="username" value="${jdbcUsername}"></property>
    	<property name="password" value="${jdbcPassword}"></property>
</bean>
```

这里使用的数据源对象是leap提供的无连接池数据源对象,当然在实际应用中我们可以使用任何我们想要使用的数据源对象,比如连接池等.

到这里数据源配置就完成了.

### 默认数据源配置
除了在`beans.xml`中显示的配置数据源对象之外,leap也提供了默认数据源配置的方式,可以不需要配置`beans.xml`,只需要在`config.xml`的配置中,将jdbc的属性配置修改为如下即可:

```xml
<property name="db.driverClassName">com.mysql.jdbc.Driver</property>
<property name="db.jdbcUrl">jdbc:mysql://127.0.0.1:3306/leap</property>
<property name="db.username">root</property>
<property name="db.password">root</property>
```

这里需要注意,在属性前加上`db.`前缀表示这是数据库的配置.  
这里的
> **注意:**
> 1. 这里的配置使用的是leap的默认实现,leap的默认实现是集成tomcat的开源连接池dbcp.
> 2. `db.`前缀表示这是数据库的配置,但是实际的属性名必须是dbcp的数据源所包含的属性,否则该配置是不能生效的.
> 3. 这里我们强烈建议使用连接池,不要使用leap提供的`leap.core.ds.UnPooledDataSource`作为生产环境的数据源对象.

### 创建数据库
数据源配置完成之后,我们现在要创建数据库表,作为示例,我们创建两个实体表和一个中间表就可以了,模型图如下:

```
        ┌────────────────────┐                      ┌────────────────────┐
        │     leap_user      │                      │     leap_post      │
        ├────────────────────┤                      ├────────────────────┤
        │id:varchar(36)      │                      │id:varchar(36)      │
        │name:varchar(50)    │1                   1 │name:varchar(50)    │
        │age:int(3)          ├─────┐          ┌─────┤descript:varchar(50)│
        │login_id:varchar(50)│     │          │     │created_at:timestamp│
        │password:varchar(50)│     │          │     └────────────────────┘
        │created_at:timestamp│     │          │     
        └────────────────────┘     │          │     
                                   │          │
                                   │          │
                                   │          │
                                   │n         │n
                              ┌────┴──────────┴────┐
                              │  leap_user_post    │
                              ├────────────────────┤
                              │user_id:varchar(36) │
                              │post_id:varchar(36) │
                              │created_at:timestamp│
                              └────────────────────┘                        
```

对应的数据库创建sql:

```sql
CREATE DATABASE `leap`;

USE `leap`;

DROP TABLE IF EXISTS `leap_post`;

CREATE TABLE `leap_post` (
  `id` varchar(36) NOT NULL,
  `name` varchar(50) NOT NULL,
  `descript` varchar(50) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `leap_user`;

CREATE TABLE `leap_user` (
  `id` varchar(36) NOT NULL,
  `name` varchar(50) NOT NULL,
  `age` int(3) NOT NULL,
  `login_id` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `leap_user_post`;

CREATE TABLE `leap_user_post` (
  `user_id` varchar(36) NOT NULL,
  `post_id` varchar(36) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`,`post_id`),
  KEY `post_id` (`post_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `leap_user` (`id`),
  CONSTRAINT `post_id` FOREIGN KEY (`post_id`) REFERENCES `leap_post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

这里提供上述sql的文本下载:[创建数据库的sql](download/leap_sql.sql)