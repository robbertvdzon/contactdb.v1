package com.vdzon.samples.uselesscontacts.service;

import com.vdzon.samples.uselesscontacts.data.AuthAccessElement;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.util.Base64;

@Provider
public class AuthSecurityInterceptor2 implements ContainerRequestFilter {
    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";
    private static final ServerResponse ACCESS_DENIED = new ServerResponse("Access denied for this resource", 401, new Headers<Object>());;
    private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse("Nobody can access this resource", 403, new Headers<Object>());;
    private static final ServerResponse SERVER_ERROR = new ServerResponse("INTERNAL SERVER ERROR", 500, new Headers<Object>());;

    @EJB
    AuthService authService;


    @Override
    public void filter(ContainerRequestContext requestContext)
    {

        /*

        docs: http://docs.jboss.org/resteasy/docs/3.0.5.Final/userguide/html/

         hier een voorbeeld: http://howtodoinjava.com/2013/07/25/jax-rs-2-0-resteasy-3-0-2-final-security-tutorial/


         http://www.aschua.de/blog/pairing-angularjs-and-javaee-for-authentication/
         */

        ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
        Method method = methodInvoker.getMethod();
        //Access allowed for all
        if( ! method.isAnnotationPresent(PermitAll.class))
        {
            //Access denied for all
            if(method.isAnnotationPresent(DenyAll.class))
            {
                requestContext.abortWith(ACCESS_FORBIDDEN);
                return;
            }

            //Get request headers
            final MultivaluedMap<String, String> headers = requestContext.getHeaders();

            //Fetch authorization header
            String authId = requestContext.getHeaderString(AuthAccessElement.PARAM_AUTH_ID);
            String authToken = requestContext.getHeaderString(AuthAccessElement.PARAM_AUTH_TOKEN);


            if(authId == null || authId.isEmpty() || authToken == null || authToken.isEmpty())
            {
                requestContext.abortWith(ACCESS_DENIED);
                return;
            }

            //Verify user access
            if(method.isAnnotationPresent(RolesAllowed.class))
            {
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

                //Is user valid?
                if( ! authService.isAuthorized(authId,authToken,rolesSet))
                {
                    requestContext.abortWith(ACCESS_DENIED);
                    return;
                }
            }
        }
        System.out.println("access allowed-1!");
        System.out.println("access allowed-2!");
    }
}
