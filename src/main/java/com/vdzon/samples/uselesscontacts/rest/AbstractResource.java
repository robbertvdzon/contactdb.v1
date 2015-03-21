package com.vdzon.samples.uselesscontacts.rest;

import com.vdzon.samples.uselesscontacts.data.AuthAccessElement;
import com.vdzon.samples.uselesscontacts.data.Contact;
import com.vdzon.samples.uselesscontacts.mapper.ContactModelMapper;
import com.vdzon.samples.uselesscontacts.model.ContactModel;
import com.vdzon.samples.uselesscontacts.service.AuthService;
import com.vdzon.samples.uselesscontacts.service.ContactService;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractResource {

    @EJB
    AuthService authService;

    protected boolean checkAuth(String role, HttpHeaders httpHeaders){
        String userId = httpHeaders.getHeaderString(AuthAccessElement.PARAM_AUTH_ID);
        String authToken = httpHeaders.getHeaderString(AuthAccessElement.PARAM_AUTH_TOKEN);
        if(userId == null || userId.isEmpty() || authToken == null || authToken.isEmpty()) {
            return false;
        }
        Set<String> rolesAllowed = new LinkedHashSet<String>();
        rolesAllowed.add(role);
        if( ! authService.isAuthorized(userId,authToken, rolesAllowed))
        {
            return false;
        }
        return true;

    }
}
