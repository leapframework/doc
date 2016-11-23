# 快速开始

本文将指引你如何快速从零搭建leap的开发环境。

## 创建maven工程

首先，需要用你熟悉的方式创建一个空的maven工程，这里以idea为例，创建后的工程结构如下：

![工程结构](/assets/demo-web1.png)

## 配置leap的maven资源库

打开pom.xml，添加如下配置：

```xml
<repositories>
    <!-- leap快照资源库 -->
    <repository> <id>leap-snapshots</id>
        <url>https://raw.githubusercontent.com/leapframework/repo/master/snapshots</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
            <checksumPolicy>warn</checksumPolicy>
        </snapshots>
    </repository>
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