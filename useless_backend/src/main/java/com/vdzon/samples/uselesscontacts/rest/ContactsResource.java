package com.vdzon.samples.uselesscontacts.rest;

import com.vdzon.samples.uselesscontacts.data.AuthAccessElement;
import com.vdzon.samples.uselesscontacts.data.Contact;
import com.vdzon.samples.uselesscontacts.service.ContactService;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * REST Service to expose the data to display in the UI grid.
 *
 * @author Roberto Cortez
 */
//@DeclareRoles({"root", "Manager", "Employee"})
@Stateless
@Path("/contacts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ContactsResource {
    @EJB
    ContactService teamService;

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed("root")
    @PermitAll
    public List<Contact> listAll(@Context HttpHeaders headers) throws Exception {
        long userId = Long.parseLong(headers.getHeaderString(AuthAccessElement.PARAM_AUTH_ID));
        String authToken = headers.getHeaderString(AuthAccessElement.PARAM_AUTH_TOKEN);
        return teamService.listAll(userId);
    }

    @GET
    @Path("all2")
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed("root")
    @PermitAll
    public List<Contact> listAll2(@Context HttpHeaders headers) throws Exception {
        long userId = 1;
        return teamService.listAll(userId);
    }

    @GET
    @Path("test1")
    @PermitAll
    public String test1() {
        System.out.println("HALLO2! ");
        return "hallo2";
    }



    @GET
    @Path("{id}")
    public Contact getTeam(@PathParam("id") Long id) {
        return null;
    }

    @POST
    public Contact saveTeam(Contact team) {
        return team;
    }

    @DELETE
    @Path("{id}")
    public void deleteTeam(@PathParam("id") Long id) {
    }
}
