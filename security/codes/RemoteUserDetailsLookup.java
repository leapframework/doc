package hello.beans;

import hello.models.UserDetailsImpl;
import leap.core.annotation.Inject;
import leap.lang.http.HTTP;
import leap.lang.http.client.HttpClient;
import leap.lang.http.client.HttpResponse;
import leap.lang.json.JSON;
import leap.oauth2.webapp.token.AccessToken;
import leap.oauth2.webapp.user.UserDetailsLookup;
import leap.web.exception.InternalServerErrorException;
import leap.web.security.user.UserDetails;

public class RemoteUserDetailsLookup implements UserDetailsLookup {

    protected @Inject HttpClient httpClient;

    @Override
    public UserDetails lookupUserDetails(AccessToken at, String userId) {
        HttpResponse response = httpClient
                                    .request("http://localhost:8088/restd/user/" + userId)
                                    .send(HTTP.Method.GET);

        if(!response.isOk()) {
            throw new InternalServerErrorException("Unexpected response from remote user endpoint : " + response.getString());
        }

        String json = response.getString();

        return JSON.decode(json, UserDetailsImpl.class);
    }

}