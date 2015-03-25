package com.vdzon.samples.uselesscontacts.rest;

import com.vdzon.samples.uselesscontacts.data.AuthAccessElement;
import com.vdzon.samples.uselesscontacts.data.Contact;
import com.vdzon.samples.uselesscontacts.mapper.ContactModelMapper;
import com.vdzon.samples.uselesscontacts.model.ContactModel;
import com.vdzon.samples.uselesscontacts.service.ContactService;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Stateless
@Path("/contacts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ContactsResource extends AbstractResource {
    @EJB
    ContactService contactService;

    @Resource
    ManagedExecutorService executor;

    @GET
    @Path("getContacts")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getContacts(@Context HttpHeaders headers) throws Exception {
        System.out.println("\n\n\n GET CONTACTS USING FUTURES \n\n\n\n");
        if (!checkAuth("root", headers)) {
            return Response.status(403).type("text/plain").entity("Geen toegang!!").build();
        }
        long userId = Long.parseLong(headers.getHeaderString(AuthAccessElement.PARAM_AUTH_ID));
        return Response.accepted(ContactModelMapper.toModel(contactService.listAll(userId))).build();
    }

    @GET
    @Path("{id}")
    public Contact getContact(@PathParam("id") Long id) {
        return null;
    }

    @POST
    @Path("saveContact")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response saveContact(@QueryParam("contact") ContactModel model, @Context HttpHeaders headers) throws Exception {
        if (!checkAuth("root", headers)) {
            return Response.status(403).type("text/plain").entity("Geen toegang!!").build();
        }
        System.out.println("SAVE USer:" + model.getName());
        Contact contact = contactService.getContact(model.getUuid());
        ContactModelMapper.mergeModel(model, contact);
        contactService.saveContact(contact);

        long userId = Long.parseLong(headers.getHeaderString(AuthAccessElement.PARAM_AUTH_ID));
        return Response.accepted(ContactModelMapper.toModel(contactService.listAll(userId))).build();
    }

    @PUT
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response addContact(@QueryParam("contact") ContactModel model, @Context HttpHeaders headers) throws Exception {
        if (!checkAuth("root", headers)) {
            return Response.status(403).type("text/plain").entity("Geen toegang!!").build();
        }
        long userId = Long.parseLong(headers.getHeaderString(AuthAccessElement.PARAM_AUTH_ID));
        System.out.println("ADD USer:" + model.getName());
        Contact contact = new Contact();
        ContactModelMapper.mergeModel(model, contact);
        contact.setUserId(userId);
        contact.setUuid(UUID.randomUUID());
        contactService.addContact(contact);
        return Response.accepted(ContactModelMapper.toModel(contactService.listAll(userId))).build();
    }

    @DELETE
    @Path("{uuid}")
    @PermitAll
    public Response deleteContact(@PathParam("uuid") UUID uuid, @Context HttpHeaders headers) {
        System.out.println("Delete USer:" + uuid);
        if (!checkAuth("root", headers)) {
            return Response.status(403).type("text/plain").entity("Geen toegang!!").build();
        }
        long userId = Long.parseLong(headers.getHeaderString(AuthAccessElement.PARAM_AUTH_ID));
        contactService.deleteContact(uuid);
        return Response.accepted(ContactModelMapper.toModel(contactService.listAll(userId))).build();
    }

    @GET
    @Path("test2")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response test2(@Context HttpHeaders headers) {
        return Response.accepted(ContactModelMapper.toModel(contactService.listAll(1))).build();
    }

    @GET
    @Path("test1")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response test1(@Context HttpHeaders headers) {
        String result = "hallo! test1!, 25-3 21:09";
        return Response.accepted(result).build();
    }


}
