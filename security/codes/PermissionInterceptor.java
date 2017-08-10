package hello.interceptors;
import leap.core.annotation.Inject;
import leap.core.validation.Validation;
import leap.lang.Strings;
import leap.lang.intercepting.State;
import leap.web.action.ActionContext;
import leap.web.action.ActionInterceptor;
import leap.web.api.mvc.ApiErrorHandler;
public class PermissionInterceptor implements ActionInterceptor {
    protected @Inject ApiErrorHandler errorHandler;

    @Override
    public State preExecuteAction(ActionContext context, Validation validation) throws Throwable {
        String path = context.getPath();
        if(path.equals("/greeting/perm")) {
            if(Strings.isEmpty(context.getRequest().getParameter("secret"))) {
                errorHandler.forbidden(context.getResponse(), "No permission");
                return State.INTERCEPTED;
            }
        }
        return State.CONTINUE;
    }
}