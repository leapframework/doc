# 基础 API 开发

开发 API 接口与开发普通接口一样，都是基于前面 MVC 章节提到的控制器类 Controller 和路由方法 Action。

只是 API 接口在这个基础上有一些额外的配置。

## 继承 ApiController

所有 API 接口的 Controller 都需要继承于 `leap.web.api.mvc.ApiController` 。

API Controller 示例代码如下：

```java
public class UserController extends ApiController {
    // methods
}
```

## 创建路由方法

API 路由方法的示例代码如下：

```java
public class UserController extends ApiController {
    @GET
    public ApiResponse<List<UserModel>> all(){
        return ApiResponse.ok(UserModel.all());
    }
}
```

与创建普通接口一样，创建 API 接口也是在 Controller 类中新增公有非静态非抽象方法，不过有以下几点区别于普通接口的创建。

### 指定接口对应的 HttpMethod 注解

根据 RESTful API 的理念，接口 path 只是一个名词描述，真正表达请求方需要执行的操作是由 HTTP METHOD 指定的。

因此当我们创建一个 API 接口时，我们需要对每个 API Controller 类中的路由方法通过注解的方式，指定该接口处理哪种 HTTP 请求。

**当没有 Http Method 注解的时候，程序启动的时候将不会把该方法映射为路由方法。**

为了定义清晰的 API 路由接口，推荐使用以下 HttpMethod 注解标注路由方法：

* @GET ：建议标注在**获取数据**的路由方法上；
* @POST ：建议标注在**新增数据**的路由方法上；
* @PATCH ：建议标注在**修改数据**的路由方法上；
* @DELETE ：建议标注在**删除数据**的路由方法上。

> 注意：当我们在路由方法上标注了以上注解，并且没有指定注解属性中的 value 值时，Leap在加载 API 路由时是不会把路由方法的名称加入到路由路径中的，这与普通路由的映射规则是不一样的。如以上代码所示，当客户端要请求 UserController.all 这个方法的时候，它发送的请求应该是 `GET /user` 而不是 `GET /user/all`。
>
> 这是针对 API 开发 Leap 所做的支持，因为在 API 开发中一般一个 Controller 就代表了对一个业务对象对外的 API 接口。既然名词描述已经在 Controller 上定义了，那么方法只要定义动作就可以完整地描述出一个简单的增删改查接口了。对于复杂的 API 接口，还是可以在注解中指定 value，这与普通路由是一致的。

### 使用 ApiResponse 作为返回类型

推荐使用这个类作为返回类型主要有以下几点考虑：

* `ApiResponse` 其实是一个包装类，除了可以在里面设置返回对象之外，还可以指定返回响应的状态码；
* `ApiResponse` 的泛型参数**用于自动生成接口文档时，确定接口返回的类型**。关于生成接口文档的细节，后面章节会详细介绍。

`ApiResponse` 类提供了很多静态方法帮助开发人员便捷地创建 `ApiResponse` 对象，其实都是围绕着 `status` 属性和 `entity` 属性。在这里列举几个最常用的静态方法：

```java
    /**
     * 返回指定响应与对象
     * @param status 具体响应状态码
     * @param entity 具体返回对象
     */
    public static ApiResponse of(HTTP.Status status, Object entity);

    /**
     * 返回 200 成功状态码的响应
     * @param entity 响应中附带的对象
     */
    public static ApiResponse ok(Object entity);

    /**
     * 返回错误静态响应
     * @param status 具体错误状态码，如400、404等
     * @param message 错误消息
     */
    public static ApiResponse err(HTTP.Status status, String message);
```

### 路由方法 Path 的值

在前面 MVC 章节我们介绍过路由方法上注解的 Path 带前缀斜杠 `/` 与不带前缀斜杠的区别，这种区别会导致最终映射路由的不同。

但是在 API 开发中这种区别将不存在。

在 API 路由方法上注解 `GET("/find")` 和 `GET("find")` 效果是一样的，Leap 获取 Path 值的时候如果发现没有斜杠前缀，会自动补上，再和控制器类 Path 组成最终路由路径。