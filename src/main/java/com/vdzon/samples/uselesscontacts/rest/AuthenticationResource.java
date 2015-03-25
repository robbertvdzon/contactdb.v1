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
        AuthAccessElement accessElement = authService.login(loginElement);
        if (accessElement != null) {
            request.getSession().setAttribute(AuthAccessElement.PARAM_AUTH_ID, accessElement.getAuthId());
            request.getSession().setAttribute(AuthAccessElement.PARAM_AUTH_TOKEN, accessElement.getAuthToken());
        }
        return accessElement;
    }


}
