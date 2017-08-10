package hello.controllers;

import hello.models.User;
import leap.web.annotation.http.*;
import leap.web.api.mvc.ApiResponse;
import leap.web.api.mvc.ModelController;
import leap.web.api.mvc.params.DeleteOptions;
import leap.web.api.mvc.params.Partial;
import leap.web.api.mvc.params.QueryOptions;
import leap.web.api.mvc.params.QueryOptionsBase;

public class UserController extends ModelController<User> {

    @POST
    public ApiResponse createUser(Partial<User> user) {
        return createAndReturn(user);
    }

    @GET("/{id}")
    public ApiResponse retrieveUser(String id, QueryOptionsBase options) {
        return get(id);
    }

    @PATCH("/{id}")
    public ApiResponse updateUser(String id, Partial<User> user) {
        return updatePartial(id, user);
    }

    @DELETE("/{id}")
    public ApiResponse deleteUser(String id) {
        return delete(id);
    }

    @GET
    public ApiResponse queryUsers(QueryOptions options) {
        return queryList(options);
    }

}