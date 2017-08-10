package hello.models;

import leap.orm.annotation.Column;
import leap.orm.annotation.Id;
import leap.orm.annotation.Table;
import leap.orm.model.Model;

@Table(name = "groups", autoCreate = true)
public class Group extends Model {

    @Id(generator = "shortid")
    protected String id;

    @Column
    protected String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
