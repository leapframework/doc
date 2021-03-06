# Summary

* [概述](README.md)

-----

* [快速开始](/quick_start/README.md)
    * [搭建 Web 工程](/quick_start/web/README.md)
        * [创建工程](quick_start/web/proj.md)
        * [配置 pom.xml](quick_start/web/pom.md)
        * [配置 web.xml](quick_start/web/web.md)
        * [配置 config.xml](quick_start/web/conf.md)
        * [配置数据源](quick_start/web/ds.md)
        * [创建数据表](quick_start/web/sql.md)
        * [创建 Model](quick_start/web/model.md)
        * [创建 Service](quick_start/web/service.md)
        * [创建 Controller](quick_start/web/controller.md)
        * [运行测试](quick_start/web/run.md)
    * [搭建 API 工程](/quick_start/api/README.md)
        * [创建工程](quick_start/api/proj.md)
        * [配置 pom.xml](quick_start/api/pom.md)
        * [配置 web.xml](quick_start/api/web.md)
        * [配置 config.xml](quick_start/api/conf.md)
        * [配置数据源](quick_start/api/ds.md)
        * [创建数据表](quick_start/api/sql.md)
        * [创建 API](quick_start/api/api.md)
        * [创建 Model](quick_start/api/model.md)
        * [创建 Controller](quick_start/api/controller.md)
        * [运行测试](quick_start/api/run.md)
        * [使用 Restd](quick_start/api/restd.md)

-----

* [工程配置](/config/README.md)
    * [工程结构](/config/structure.md)
    * [配置依赖](/config/dependency.md)
    * [配置 web.xml](/config/web.md)
    * [通用配置约定](/config/common.md)
    * [基本配置](/config/base.md)
    * [使用 profile 切换配置](/config/profile.md)
    * [多模块工程](/config/multi_module.md)

-----

* [依赖注入](/bean/README.md)
    * [Bean 声明](/bean/declare.md)
    * [Bean 注入](/bean/inject.md)

-----

* [数据访问](/orm/README.md)
    * [数据源配置](/orm/datasource.md)
    * [实体映射](/orm/mapping.md)
    * [实体操作](/orm/entity/README.md)
        * [增删改查](/orm/entity/crud/README.md)
            * [新增数据](/orm/entity/crud/create.md)
            * [删除数据](/orm/entity/crud/delete.md)
            * [更新数据](/orm/entity/crud/update.md)
            * [查询数据](/orm/entity/crud/query.md)
        * [实体关系](/orm/entity/relation/README.md)
            * [多对一关系](/orm/entity/relation/many_to_one.md)
            * [多对多关系](/orm/entity/relation/many_to_many.md)
    * [SQL 操作](/orm/sql/README.md)
        * [SQL 配置](/orm/sql/config.md)
        * [SQL 占位符](/orm/sql/place_holder.md)
        * [SQL 动态语句](/orm/sql/dyna.md)
        * [SQL 执行](/orm/sql/exec.md)
        * [SQL 查询接口](/orm/sql/query.md)
        * [在 SQL 中使用环境变量](/orm/sql/env_var.md)
    * [事务控制](/orm/transaction.md)
    * [动态表名](/orm/dynamic_table_name.md)
    * [SQL 配置重载](/orm/sql_monitor.md)

-----

* [MVC](/mvc/README.md)
    * [路由映射](/mvc/route/README.md)
        * [控制器类](/mvc/route/controller.md)
        * [路由方法](/mvc/route/action.md)
        * [路由映射规则](/mvc/route/rule/README.md)
            * [默认规则](/mvc/route/rule/default.md)
            * [自定义路由映射](/mvc/route/rule/custom.md)
    * [接口参数](/mvc/params/README.md)
        * [简单参数类型](/mvc/params/simple.md)
        * [数组列表参数类型](/mvc/params/list.md)
        * [对象参数类型](/mvc/params/object.md)
        * [内置参数类型](/mvc/params/context.md)
        * [映射规则](/mvc/params/mapping.md)
        * [参数校验](/mvc/params/valid.md)
        <!-- * [自定义参数解析](/mvc/params/custom.md) -->
    * [接口返回](/mvc/return/README.md)
        * [返回数据](/mvc/return/data.md)
        * [返回数据序列化配置](/mvc/return/data_json.md)
        * [返回默认视图](/mvc/return/def_view.md)
        * [返回指定视图](/mvc/return/pointed_view.md)
    * [视图](/mvc/view.md)
    * [静态资源](/mvc/static.md)
    * [拦截器](/mvc/interceptor.md)
    * [多主题](/mvc/theme.md)

-----

* [RESTful API](/api/README.md)
    * [API 规范](/api/spec/README.md)
       * [描述文件](/api/spec/desc.md)
       * [错误处理](/api/spec/errors.md)
       * [安全认证](/api/spec/security.md)
       * [跨域资源共享](/api/spec/cors.md)
       * [JSONP 回调](/api/spec/jsonp.md)
       * [数据分页](/api/spec/pagination.md)
       * [数据过滤](/api/spec/filtering.md)
       * [数据排序](/api/spec/sorting.md)
       * [字段选择](/api/spec/select.md)
       * [数据展开](/api/spec/expand.md)
    * [API 配置](/api/config.md)
    * [基础 API 开发](/api/basic.md)
    * [数据 API 开发](/api/data/README.md)
        * [实体查询](/api/data/query/README.md)
            * [使用 filters 参数](/api/data/query/filters.md)
            * [使用 orderby 参数](/api/data/query/orderby.md)
            * [使用 expand 参数](/api/data/query/expand.md)
            * [使用 joins 参数](/api/data/query/joins.md)
        * [实体删除](/api/data/delete.md)
        * [实体操作](/api/data/partial.md)
    * [错误处理](/api/exception.md)
    * [生成文档](/api/doc/README.md)
        * [基于配置生成文档](/api/doc/conf.md)
        * [基于注解编写文档](/api/doc/annotation.md)
        * [基于 Markdown 编写文档](/api/doc/md.md)
            * [注解引用文件](/api/doc/md/file.md)
            * [注解引用文件片段](/api/doc/md/fragment.md)
            * [自动映射](/api/doc/md/auto.md)
        * [编写全局文档](/api/doc/global.md)
        * [扩展](/api/doc/extend/README.md)
            * [使用时间类型](/api/doc/extend/time.md)
            * [实体字段的操作控制](/api/doc/extend/able.md)
            * [路由 CORS 控制](/api/doc/extend/cors.md)
    * [生成接口](/api/restd/README.md)
        * [Restd 配置](/api/restd/conf/README.md)
            * [启用 Restd](/api/restd/conf/enable.md)
            * [配置实体](/api/restd/conf/entity.md)
            * [配置实体接口](/api/restd/conf/action.md)
            * [配置 SQL 接口](/api/restd/conf/sql.md)
        * [Restd 安全](/api/restd/security.md)

-----

* [HTPL 视图](/htpl/README.md)
    * [表达式](/htpl/expr/README.md)
        * [使用 ${} 解析表达式](/htpl/expr/expr.md)
        <!-- * [使用 #{} 做国际化](/htpl/expr/message.md) -->
        * [使用 @{} 处理路径上下文](/htpl/expr/url.md)
        * [转义处理](/htpl/expr/escape.md)
    * [HTPL 语法](/htpl/syntax/README.md)
        * [基于注释的 HTPL 语法](/htpl/syntax/comment_base.md)
        * [设置变量](/htpl/syntax/set.md)
        * [逻辑控制](/htpl/syntax/logic.md)
        * [模板引用](/htpl/syntax/ref.md)
        * [页面布局](/htpl/syntax/layout.md)
        * [合并资源](/htpl/syntax/bundle.md)
    * [HTPL 属性](/htpl/attr/README.md)
        * [等效语法属性](/htpl/attr/equals.md)
        * [增强语法属性](/htpl/attr/enhance.md)
    * [资源文件指纹](/htpl/fingerprint.md)
    * [与 JSP 混合使用](/htpl/with_jsp.md)

-----

* [安全控制](/security/README.md)
    * [接入授权服务器](/security/server.md)
    * [本地开发调试](/security/debug.md)
    * [获取用户信息](/security/userinfo.md)
        * [获取用户详细信息](/security/userdetails.md)
    * [设置匿名访问](/security/anonymous.md)
    * [操作权限控制](/security/op_perm.md)
    * [数据权限控制](/security/data_perm.md)
    * [生成操作日志](/security/op_log.md)

-----

* [单元测试](/unit_test/README.md)
    * [配置](/unit_test/config.md)
    * [针对类的单元测试](/unit_test/class_test.md)
    * [针对 web 接口的单元测试](/unit_test/web_test.md)

-----

* [常见问题](FAQ.md)