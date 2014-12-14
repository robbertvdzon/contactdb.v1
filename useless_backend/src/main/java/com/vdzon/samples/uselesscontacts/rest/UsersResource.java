package com.vdzon.samples.uselesscontacts.rest;

import com.vdzon.samples.uselesscontacts.data.AuthAccessElement;
import com.vdzon.samples.uselesscontacts.data.User;
import com.vdzon.samples.uselesscontacts.service.UserService;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

/**
 * REST Service to expose the data to display in the UI grid.
 *
 * @author Roberto Cortez
 */
//@DeclareRoles({"root", "Manager", "Employee"})
@Stateless
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsersResource {

    @EJB
    UserService userService;

    @GET
    @Path("getuser")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@Context HttpHeaders headers) {
        long userId = Long.parseLong(headers.getHeaderString(AuthAccessElement.PARAM_AUTH_ID));
        String authToken = headers.getHeaderString(AuthAccessElement.PARAM_AUTH_TOKEN);
        return userService.findUser(userId);

    }

}
