package com.vdzon.samples.uselesscontacts.service;

/*
@Provider
@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class AuthSecurityInterceptor implements ContainerRequestFilter {

    // 401 - Access denied
    private static final Response ACCESS_UNAUTHORIZED = Response.status(Response.Status.UNAUTHORIZED).entity("Not authorized.").build();

    @EJB
    AuthService authService;

    @Context
    private HttpServletRequest request;

    @Context
    private ResourceInfo resourceInfo;

/*
    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {

        requestContext.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return new Principal() {
                    @Override
                    public String getName() {
                        return "Joe";
                    }
                };
            }

            @Override
            public boolean isUserInRole(String string) {
                return isAuthenticated(requestContext);
            }

            @Override
            public boolean isSecure() {
                return requestContext.getSecurityContext().isSecure();
            }

            @Override
            public String getAuthenticationScheme() {
                return requestContext.getSecurityContext().getAuthenticationScheme();
            }
        });

        if (!isAuthenticated(requestContext)) {
            requestContext.abortWith(ACCESS_UNAUTHORIZED);
        }
    }

    private boolean isAuthenticated(final ContainerRequestContext requestContext) {
        String authId = requestContext.getHeaderString(AuthAccessElement.PARAM_AUTH_ID);
        String authToken = requestContext.getHeaderString(AuthAccessElement.PARAM_AUTH_TOKEN);
try {
    // Get method invoked.
    Method methodInvoked = resourceInfo.getResourceMethod();

    if (methodInvoked.isAnnotationPresent(RolesAllowed.class)) {
        RolesAllowed rolesAllowedAnnotation = methodInvoked.getAnnotation(RolesAllowed.class);
        Set<String> rolesAllowed = new HashSet<>(Arrays.asList(rolesAllowedAnnotation.value()));

        if (authService.isAuthorized(authId, authToken, rolesAllowed)) {
            return true;
        }
        return false;
    }
}
catch(Exception ex){
    ex.printStackTrace();
}
        return true;
    }
/

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Get AuthId and AuthToken from HTTP-Header.
        String authId = requestContext.getHeaderString(AuthAccessElement.PARAM_AUTH_ID);
        String authToken = requestContext.getHeaderString(AuthAccessElement.PARAM_AUTH_TOKEN);

        // Get method invoked.
        Method methodInvoked = resourceInfo.getResourceMethod();

        if (methodInvoked.isAnnotationPresent(RolesAllowed.class)) {
            RolesAllowed rolesAllowedAnnotation = methodInvoked.getAnnotation(RolesAllowed.class);
            Set<String> rolesAllowed = new HashSet<>(Arrays.asList(rolesAllowedAnnotation.value()));

            if (authService.isAuthorized(authId, authToken, rolesAllowed)) {
                return;
            }
            requestContext.abortWith(ACCESS_UNAUTHORIZED);
        }
    }

}
*/