package com.vdzon.samples.uselesscontacts.service;

import com.vdzon.samples.uselesscontacts.data.User;

import javax.ejb.Stateless;
import javax.persistence.*;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Stateless(name = "UserService")
public class UserServiceBean implements UserService {

    @PersistenceContext
    private EntityManager entityManager;

    public User findUser(String username, String passwd) {
        try {
            TypedQuery<User> query = entityManager.createQuery(
                    "SELECT u FROM User u WHERE u.username = :username and u.passwd=:passwd",User.class);
            query.setParameter("username", username);
            query.setParameter("passwd", passwd);
            User u = query.getSingleResult();
            u.setPermissions("root");// TODO: dit moet natuurlijk anders! (moet uit de DB komen)
            return u;
        } catch (NoResultException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User findUser(long id) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.id = " + id + "",
                User.class);
        try {
            User u = query.getSingleResult();
            u.setPermissions("root");// TODO: dit moet natuurlijk anders! (moet uit de DB komen)
            return u;
        } catch (NoResultException e) {
        }
        return null;
    }

    public void syncUser(final User user){
        entityManager.merge(user);
    }


}
