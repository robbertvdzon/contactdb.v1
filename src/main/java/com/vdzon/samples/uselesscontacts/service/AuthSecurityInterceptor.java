package com.vdzon.samples.uselesscontacts.service;

import com.vdzon.samples.uselesscontacts.data.AuthAccessElement;

import javax.annotation.Priority;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Provider
@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class AuthSecurityInterceptor implements ContainerRequestFilter {

    //http://www.developerscrappad.com/1814/java/java-ee/rest-jax-rs/java-ee-7-jax-rs-2-0-simple-rest-api-authentication-authorization-with-custom-http-header/

    // 401 - Access denied
    private static final Response ACCESS_UNAUTHORIZED = Response.status(Response.Status.UNAUTHORIZED).entity("Not authorized.").build();

    @EJB
    AuthService authService;

    @Context
    private HttpServletRequest request;

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {
        System.out.println("\n\n\n\n----------- filter  \n\n\n\n");

        requestContext.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                System.out.println("\n\n\n\n----------- getUserPrincipal  \n\n\n\n");
                return new Principal() {
                    @Override
                    public String getName() {
                        return "Joe";
                    }
                };
            }

            @Override
            public boolean isUserInRole(String string) {
                System.out.println("\n\n\n\n----------- isUserInRole  \n\n\n\n");
                return isAuthenticated(requestContext);
            }

            @Override
            public boolean isSecure() {
                System.out.println("\n\n\n\n----------- isSecure  \n\n\n\n");
                return requestContext.getSecurityContext().isSecure();
            }

            @Override
            public String getAuthenticationScheme() {
                System.out.println("\n\n\n\n----------- getAuthenticationScheme  \n\n\n\n");
                return requestContext.getSecurityContext().getAuthenticationScheme();
            }
        });

        if (!isAuthenticated(requestContext)) {
            System.out.println("\n\n\n\n----------- filter  \n\n\n\n");
            requestContext.abortWith(ACCESS_UNAUTHORIZED);
        }
    }

    private boolean isAuthenticated(final ContainerRequestContext requestContext) {
        System.out.println("\n\n\n\n----------- isAuthenticated  \n\n\n\n");
        String authId = requestContext.getHeaderString(AuthAccessElement.PARAM_AUTH_ID);
        String authToken = requestContext.getHeaderString(AuthAccessElement.PARAM_AUTH_TOKEN);
        try {
            // Get method invoked.
            System.out.println(authId);
            System.out.println(authToken);
            System.out.println(requestContext.getMethod());
            return true;
//            Method methodInvoked = resourceInfo.getResourceMethod();
//
//            if (methodInvoked.isAnnotationPresent(RolesAllowed.class)) {
//                RolesAllowed rolesAllowedAnnotation = methodInvoked.getAnnotation(RolesAllowed.class);
//                Set<String> rolesAllowed = new HashSet<>(Arrays.asList(rolesAllowedAnnotation.value()));
//
//                if (authService.isAuthorized(authId, authToken, rolesAllowed)) {
//                    return true;
//                }
//                return false;
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }


//    @Override
//    public void filter(ContainerRequestContext requestContext) throws IOException {
//        // Get AuthId and AuthToken from HTTP-Header.
//        String authId = requestContext.getHeaderString(AuthAccessElement.PARAM_AUTH_ID);
//        String authToken = requestContext.getHeaderString(AuthAccessElement.PARAM_AUTH_TOKEN);
//
//        // Get method invoked.
//        Method methodInvoked = resourceInfo.getResourceMethod();
//
//        if (methodInvoked.isAnnotationPresent(RolesAllowed.class)) {
//            RolesAllowed rolesAllowedAnnotation = methodInvoked.getAnnotation(RolesAllowed.class);
//            Set<String> rolesAllowed = new HashSet<>(Arrays.asList(rolesAllowedAnnotation.value()));
//
//            if (authService.isAuthorized(authId, authToken, rolesAllowed)) {
//                return;
//            }
//            requestContext.abortWith(ACCESS_UNAUTHORIZED);
//        }
//    }

}
