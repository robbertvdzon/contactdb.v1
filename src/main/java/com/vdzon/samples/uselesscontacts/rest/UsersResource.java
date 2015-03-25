package com.vdzon.samples.uselesscontacts.rest;

import com.vdzon.samples.uselesscontacts.data.AuthAccessElement;
import com.vdzon.samples.uselesscontacts.data.User;
import com.vdzon.samples.uselesscontacts.mapper.UserModelMapper;
import com.vdzon.samples.uselesscontacts.service.AuthService;
import com.vdzon.samples.uselesscontacts.service.UserService;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@DeclareRoles({"root", "Manager", "Employee"})
@Stateless
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsersResource extends AbstractResource{

    @EJB
    UserService userService;

    @GET
    @Path("getuser")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@Context HttpHeaders headers) {
        if (!checkAuth ("root",headers)){
            return Response.status(403).type("text/plain").entity("Geen toegang!!").build();
        }
        long userId = Long.parseLong(headers.getHeaderString(AuthAccessElement.PARAM_AUTH_ID));
        return Response.accepted( UserModelMapper.toModel(userService.findUser(userId))).build();
    }

}
