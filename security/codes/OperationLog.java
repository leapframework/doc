package hello.models;
import leap.orm.annotation.Column;
import leap.orm.annotation.Id;
import leap.orm.annotation.Table;
import leap.orm.model.Model;

import java.sql.Timestamp;
@Table(name = "op_log", autoCreate = true)
public class OperationLog extends Model {
    protected Integer   id;
    protected String    verb;
    protected String    path;
    protected String    name;
    protected String    title;
    protected String    userId;
    protected String    loginName;
    protected boolean   success;
    protected Integer   status;
    protected Timestamp timestamp;

    // 省略 getter 和 setter
}