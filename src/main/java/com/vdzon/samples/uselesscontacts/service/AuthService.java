package com.vdzon.samples.uselesscontacts.service;

import com.vdzon.samples.uselesscontacts.data.AuthAccessElement;
import com.vdzon.samples.uselesscontacts.data.AuthLoginElement;

import java.util.Set;

/**
 * Created by robbert on 1-11-2014.
 */
public interface AuthService {
    AuthAccessElement login(AuthLoginElement loginElement);
    boolean isAuthorized(String authId, String authToken, Set<String> rolesAllowed);

    }
