# 创建模型对象

配置好了数据源，我们现在可以根据我们的需要配置业务实体。

业务实体我们推荐是存放在之前配置的 `base-package` 主要包路径下，可以新建一个 `model` 或者 `entity` 包专门存放。

如在 `hello.model` 中新建实体 `User` ：

```java
package hello.model;

import leap.orm.annotation.Column;
import leap.orm.annotation.Id;
import leap.orm.annotation.Table;
import leap.orm.model.Model;

@Table("user")
public class User extends Model {
    @Id
    private String id;
    
    @Column
    private String name;
    
    @NonColumn
    private String nickname;

    // 省略 getter 和 setter
}
```

这里使用了 Leap 的 ORM 功能配置了一个实体，其中：

- `@Table` ：标注了实体对应的数据库表名；

- `@Id` ：标注为 ID 字段；

- `@Column` ：标注为数据库字段，可以指定字段对应的数据库字段名。

- `@NonColumn` ：标注为非数据库字段，此字段将不会进行数据库持久化存储。

- **继承 `Model`** ：这是 **Leap 识别当前类为实体的必要配置**，而且 `Model` 类为实体的增删改查提供了很多便捷的方法。