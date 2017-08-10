package hello.beans;
import leap.lang.params.Params;
import leap.orm.sql.Sql;
import leap.orm.sql.SqlContext;
import leap.orm.sql.SqlTag;
import leap.orm.sql.SqlTagProcessor;
public class SecurityQueryFilter implements SqlTagProcessor {

    @Override
    public String processTag(SqlContext context, Sql sql, SqlTag tag, Params params) {
        String entityName = tag.getContent();
        if(entityName.equals("User")) {
            return "t.id = #{env.user.id}";
        }
        return null;
    }
}