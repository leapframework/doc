package hello.controller;

import hello.model.User;
import hello.service.UserService;
import leap.core.annotation.Inject;
import leap.web.action.ControllerBase;
import leap.web.annotation.Path;
import leap.web.annotation.http.GET;

@Path("/user")
public class UserController extends ControllerBase {

    @Inject
    private UserService userService;

    @GET
    public User getHelloUser() {
        return userService.getHelloUser();
    }
}
