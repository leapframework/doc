package hello.models;

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

    // 省略 getter 和 setter
}