package com.vdzon.samples.uselesscontacts.rest;

import com.vdzon.samples.uselesscontacts.data.AuthAccessElement;
import com.vdzon.samples.uselesscontacts.data.Contact;
import com.vdzon.samples.uselesscontacts.mapper.ContactModelMapper;
import com.vdzon.samples.uselesscontacts.model.ContactModel;
import com.vdzon.samples.uselesscontacts.service.ContactService;
import com.vdzon.samples.uselesscontacts.service.Sleeper;
import com.vdzon.samples.uselesscontacts.service.SleeperImpl;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.TimeUnit.SECONDS;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;
import static javax.ws.rs.core.Response.Status.FORBIDDEN;
import static javax.ws.rs.core.Response.status;

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
public class ContactsResource extends AbstractResource{
    @EJB
    ContactService contactService;

    @Resource
    ManagedExecutorService executor;

    @EJB
    Sleeper sleeper;

    @GET
    @Path("getContacts")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public void getContacts(@Suspended AsyncResponse response, @Context HttpHeaders headers) throws Exception {
        System.out.println("\n\n\n GET CONTACTS USING FUTURES \n\n\n\n");
        if (!checkAuth ("root",headers)){
            response.resume(new WebApplicationException(FORBIDDEN));

//            return Response.status(403).type("text/plain").entity("Geen toegang!!").build();
        }
        long userId = Long.parseLong(headers.getHeaderString(AuthAccessElement.PARAM_AUTH_ID));

        CompletableFuture<List<Contact>> customerFuture =
                CompletableFuture.completedFuture(userId)
                        .thenComposeAsync(this::getContacts, executor);

        CompletableFuture<List<ContactModel>> model = customerFuture.thenComposeAsync(this::toModel, executor);

        customerFuture
                .whenCompleteAsync(
                        (modelresult, throwable) -> {
                            System.out.println("\n\n\n FILL RESULT \n\n\n\n");
                            boolean b = modelresult == null ? response.resume(throwable) : response.resume(modelresult);
                        }
                );

        response.setTimeout(1, SECONDS);
        response.setTimeoutHandler(
                r -> r.resume(new WebApplicationException(SERVICE_UNAVAILABLE)));


//        return Response.accepted( ContactModelMapper.toModel(contactService.listAll(userId))).build();
    }


    @GET
    @Path("test2")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response test2(@Context HttpHeaders headers) {
        return Response.accepted( ContactModelMapper.toModel(contactService.listAll(1))).build();
    }

    @GET
    @Path("test1")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response test1(@Context HttpHeaders headers) {
        String result = "hallo vanuit jee!";
        return Response.accepted(result).build();
    }




    private CompletableFuture<List<Contact>> getContacts(final long userId) {
        CompletableFuture cf = new CompletableFuture();
        sleeper.schedule(
                () -> cf.complete(contactService.listAll(userId)));
        return cf;
    }

    private CompletableFuture<List<ContactModel>> toModel(final List<Contact> contacts) {
        CompletableFuture cf = new CompletableFuture();
        sleeper.schedule(
                () -> cf.complete(ContactModelMapper.toModel(contacts)));
        return cf;
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
    public Response saveContact(@QueryParam("contact") ContactModel model , @Context HttpHeaders headers) throws Exception {
        if (!checkAuth ("root",headers)){
            return Response.status(403).type("text/plain").entity("Geen toegang!!").build();
        }
        System.out.println("SAVE USer:"+model.getName());
        Contact contact = contactService.getContact(model.getUuid());
        ContactModelMapper.mergeModel(model,contact);
        contactService.saveContact(contact);
//        System.out.println("SAVE USer:"+model.getName());

        long userId = Long.parseLong(headers.getHeaderString(AuthAccessElement.PARAM_AUTH_ID));
        return Response.accepted( ContactModelMapper.toModel(contactService.listAll(userId))).build();
    }

    @PUT
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response addContact(@QueryParam("contact") ContactModel model , @Context HttpHeaders headers) throws Exception {
        if (!checkAuth ("root",headers)){
            return Response.status(403).type("text/plain").entity("Geen toegang!!").build();
        }
        long userId = Long.parseLong(headers.getHeaderString(AuthAccessElement.PARAM_AUTH_ID));
        System.out.println("ADD USer:" + model.getName());
        Contact contact = new Contact();
        ContactModelMapper.mergeModel(model,contact);
        contact.setUserId(userId);
        contact.setUuid(UUID.randomUUID());
        contactService.addContact(contact);
        return Response.accepted( ContactModelMapper.toModel(contactService.listAll(userId))).build();
    }

    @DELETE
    @Path("{uuid}")
    @PermitAll
    public Response deleteContact(@PathParam("uuid") UUID uuid, @Context HttpHeaders headers) {
        System.out.println("Delete USer:"+uuid);
        if (!checkAuth ("root",headers)){
            return Response.status(403).type("text/plain").entity("Geen toegang!!").build();
        }
        long userId = Long.parseLong(headers.getHeaderString(AuthAccessElement.PARAM_AUTH_ID));
        contactService.deleteContact(uuid);
        return Response.accepted( ContactModelMapper.toModel(contactService.listAll(userId))).build();
    }
}
