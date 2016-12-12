# 介绍

这是leap 1.0.0的文档，leap是一个开源的java web api开发框架，同时也是一个全栈式的web开发框架，欢迎所有对leap感兴趣的开发者参与到leap的源码开发中来。

## 如何参与项目

Leap 欢迎所有有意愿参与项目的人贡献源代码，提交 bug 报告，在 issue 页上提建议或展开讨论，以及其它任何帮助项目发展的事情。

但是在提 issue 或贡献源代码之前，参与者应先阅读本文档，并遵循其中规范。

### 新建 Issue

Leap 的源代码托管在 [GitHub 上](https://github.com/leapframework/framework)，如果在使用 Leap 的过程中遇到任何问题，或者对 Leap 有任何建议，都可以新建一个 issue 以方便开发者跟踪和讨论。

关于 issue 需要注意几点:

* **新建 issue 之前请先搜索**。谁都不愿意看到 issue 页面出现大量重复的、无意义的 issue，在新建 issue 之前你应该: 

    * 将问题的错误信息先 Google 一下，Stackoverflow 上或许早有答案。
    * 如果 Google 无法解决你的问题，这个问题可能是 Leap 自身的问题，此时应在 GitHub 的 [issue 页](https://github.com/leapframework/framework/issues)上搜索。
    * 尝试更换关键字搜索

* 如果无法搜索到你的问题，欢迎新建 issue，但在新建 issue 时应注意:

    * 简洁明了地描述你的问题内容
    * 附加错误输出内容
    * 附加环境信息（例如操作系统版本、JVM版本、使用的 Leap 的版本等）。

### 提交 Pull Request（贡献源代码）

为 Leap 贡献源代码，应通过提交 Pull Request 的方式进行:

1. 先 **Fork** 项目，此时你有了属于你自己的本地 Leap 源码。
2. 在本地源码里修改代码，最好新建一个 branch ，简洁地命名该 branch 的作用（是 bug-fix 还是 new-feature？）。
3. 完成你预期的功能之后，最佳实践是为你的功能编写测试用例。
4. 将项目的所有单元测试运行一遍，确认你的代码更改没有导致失败的测试用例。
5. 注意你的 git commit 信息是否得当，可以参考 Git 官方的 [commit 指引](https://git-scm.com/book/en/v2/Distributed-Git-Contributing-to-a-Project)。如果有需要改进的地方，可以通过 `rebase`、`squash`等命令修改历史。
6. 在 GitHub 上提交 Pull Request，项目的维护者会审阅你提交的代码，进一步合并你的代码进主仓库，或者向你反馈发现的问题或需要改进的地方。

### TODO: 

* 代码风格指南? 比如 Google 的 [Java Style Guide](https://google.github.io/styleguide/javaguide.html)
* [contributor-covenant](http://contributor-covenant.org/version/1/4/)?