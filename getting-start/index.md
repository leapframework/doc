# 2. 你好 LEAP
-------
&emsp;&emsp;本章将详细介绍leap的示例工程和如何搭建一个使用leap框架的工程开发环境,在开始之前,需要明确一点,本文针对leap的介绍基于使用者具备**基础的JAVA EE开发能力**,了解**tomcat和数据库的使用**的基础上进行介绍,因此请读者在阅读本文时,请先了解相关知识。

需要说明的是,leap框架本身对开发环境有一定的要求:
1. leap框架基于java8开发,因此jdk版本至少要求jdk8或以上;
2. 快速教程基于tomcat进行配置和分析,并且要求tomcat支持jdk8,因此请先安装tomcat 7或以上的版本,推荐安装tomcat 8.
3. leap中使用了jdk8的新特性和语法,包括*接口默认方法*,*Lambda 表达式*,*函数式接口*等,因此请先了解这类特性.

了解上述几点要求,我们现在可以开始搭建leap的示例工程环境了.  
* [快速环境搭建](construction.md)
* [示例工程说明](introduce.md)
* [自定义第一个action](first_action.md)
* [简单的配置连接数据库](connect_db.md)
* [第一个htpl模板](first_htpl.md)