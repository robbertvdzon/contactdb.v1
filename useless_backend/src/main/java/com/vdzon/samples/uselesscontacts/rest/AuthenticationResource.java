package com.vdzon.samples.uselesscontacts.rest;

import com.vdzon.samples.uselesscontacts.data.AuthAccessElement;
import com.vdzon.samples.uselesscontacts.data.AuthLoginElement;
import com.vdzon.samples.uselesscontacts.service.AuthService;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


//@DeclareRoles({"root", "user","anonymous" })
@ApplicationPath("/resources")
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationResource {

    @EJB
    AuthService authService;

    @POST
    @Path("login")
    @PermitAll
    public AuthAccessElement login(@Context HttpServletRequest request, AuthLoginElement loginElement) {

        //TODO: DIT MOET NATUURLIJK WEG !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //TODO: DIT MOET NATUURLIJK WEG !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        boolean autoLogin = true;//TODO: DIT MOET NATUURLIJK WEG !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if (autoLogin){
            request.getSession().setAttribute(AuthAccessElement.PARAM_AUTH_ID, "1");
            request.getSession().setAttribute(AuthAccessElement.PARAM_AUTH_TOKEN, "1");
            AuthAccessElement el = new AuthAccessElement();
            el.setAuthId(1);
            el.setAuthPermission("root");
            el.setAuthToken("1");
            return el;
        }

        AuthAccessElement accessElement = authService.login(loginElement);
        if (accessElement != null) {
            request.getSession().setAttribute(AuthAccessElement.PARAM_AUTH_ID, accessElement.getAuthId());
            request.getSession().setAttribute(AuthAccessElement.PARAM_AUTH_TOKEN, accessElement.getAuthToken());
        }
        return accessElement;
    }


}
