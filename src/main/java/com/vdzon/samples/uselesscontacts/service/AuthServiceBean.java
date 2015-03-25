package com.vdzon.samples.uselesscontacts.service;

import com.vdzon.samples.uselesscontacts.data.AuthAccessElement;
import com.vdzon.samples.uselesscontacts.data.AuthLoginElement;
import com.vdzon.samples.uselesscontacts.data.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Stateless(name = "AuthService")
public class AuthServiceBean implements AuthService {

    @EJB
    UserService userService;

    private static Map<String,AuthAccessElement> tokens = new HashMap<>();

    @Override
    public AuthAccessElement login(AuthLoginElement loginElement) {
        User user = userService.findUser(loginElement.getUsername(), loginElement.getPassword());
        if (user != null) {
            String token = UUID.randomUUID().toString();
            AuthAccessElement res = new AuthAccessElement(user.getId(), token, user.getPermissions());
            tokens.put(token,res);
            return res;
        }
        return null;
    }

    @Override
    public boolean isAuthorized(String authId, String authToken, Set<String> rolesAllowed){
        AuthAccessElement authAccessElement = tokens.get(authToken);
        if (authAccessElement==null) return false;
        for (String roleAllowed:rolesAllowed){
            for (String userRole:authAccessElement.getAuthPermission().split(",")){
                if (userRole.equals(roleAllowed))
                    return true;
            }
        }
        return false;
    }

}