package com.vdzon.samples.uselesscontacts.service;

import com.vdzon.samples.uselesscontacts.data.User;

import javax.ejb.Stateless;
import javax.persistence.*;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created by robbert on 1-11-2014.
 */
@Stateless(name = "UserService")
public class UserServiceBean implements UserService {

    @PersistenceContext
    private EntityManager entityManager;

    public User findUser(String username, String passwd) {
        try {
//            String md5PasswdHex = bytesToHex(MessageDigest.getInstance("MD5").digest(passwd.getBytes("UTF-8")));

            TypedQuery<User> query = entityManager.createQuery(
                    "SELECT u FROM User u WHERE u.username = :username and u.passwd=:passwd",User.class);
            query.setParameter("username", username);
            query.setParameter("passwd", passwd);
            User u = query.getSingleResult();
            u.setPermissions("root");// TODO: dit moet natuurlijk anders!
            return u;
        } catch (NoResultException e) {
//        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
        }
        return null;
    }

    final protected static char[] hexArray = "0123456789abcdef".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public User findUser(long id) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.id = " + id + "",
                User.class);
        try {
            User u = query.getSingleResult();
            u.setPermissions("root");// TODO: dit moet natuurlijk anders!!
            return u;
        } catch (NoResultException e) {
        }
        return null;
    }
}
