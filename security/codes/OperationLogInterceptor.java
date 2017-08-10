package hello.interceptors;
import hello.models.OperationLog;
import leap.core.security.UserPrincipal;
import leap.core.validation.Validation;
import leap.lang.Strings;
import leap.web.action.ActionContext;
import leap.web.action.ActionExecution;
import leap.web.action.ActionInterceptor;
import leap.web.api.meta.model.MApiOperation;
import leap.web.api.meta.model.MApiPath;
import leap.web.route.Route;

import java.sql.Timestamp;
public class OperationLogInterceptor implements ActionInterceptor {

    @Override
    public void completeExecuteAction(ActionContext context, Validation validation, ActionExecution execution) throws Throwable {
        Route route = context.getRoute();
        MApiPath path = route.getExtension(MApiPath.class);
        if(null != path) {
            MApiOperation op = route.getExtension(MApiOperation.class);
            UserPrincipal user = context.getRequest().getUser();
            if(null == user || user.isAnonymous()) {
                //do not save the operation log for anonymous user.
                return;
            }
            if(op.getMethod().isGet()) {
                //do not save the operation log for query.
                return;
            }
            OperationLog log = new OperationLog();
            log.setVerb(context.getMethod());
            log.setPath(context.getPath());
            log.setName(op.getName());
            log.setTitle(op.getTitle());
            log.setSuccess(execution.isSuccess());
            log.setUserId(user.getId().toString());
            log.setLoginName(user.getLoginName());
            log.setTimestamp(new Timestamp(System.currentTimeMillis()));
            if(null != execution.getStatus()) {
                log.setStatus(execution.getStatus().value());
            }
            if(!Strings.isEmpty(log.getTitle())) {
                log.setTitle(op.getSummary());
            }
            log.create();
        }
    }
}