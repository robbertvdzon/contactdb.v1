package com.vdzon.samples.uselesscontacts;


import com.vdzon.samples.uselesscontacts.rest.AuthenticationResource;
import com.vdzon.samples.uselesscontacts.rest.ContactsResource;
import com.vdzon.samples.uselesscontacts.rest.UsersResource;
import com.vdzon.samples.uselesscontacts.service.AuthSecurityInterceptor;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/resources")
public class ConfigApplication extends Application {

    @Override
    public java.util.Set<java.lang.Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<Class<?>>();
        s.add(ContactsResource.class);
        s.add(AuthenticationResource.class);
        s.add(UsersResource.class);
        s.add(AuthSecurityInterceptor.class);
        return s;
    }
}
