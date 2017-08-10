# 生成文档

Leap 除了对 API 开发提供支持，还对生成 API 文档提供了支持。文档格式我们选择了目前使用较为广泛的 Swagger 文档，关于 Swagger 的详细介绍可以上官网了解。简而言之，Swagger 是一个规范和完整的框架，用于生成、描述、调用和可视化 RESTful 风格的 Web 服务。Leap 遵循了它的格式来生成文档。

Swagger 文档的格式点击 [http://swagger.io/specification/](http://swagger.io/specification/) 可到官网查看。

Swagger 文档支持 json 或者 yaml 格式，但是无论哪种格式，对于阅读其实都是不太友好的。因此Swagger 还提供了一个叫 Swagger UI 的开源项目用于将 Swagger 文档渲染为简洁漂亮的 Web 在线页面，可直接在浏览器中浏览文档，甚至发起测试请求。Swagger UI 的项目地址是 [http://swagger.io/swagger-ui/](http://swagger.io/swagger-ui/)。