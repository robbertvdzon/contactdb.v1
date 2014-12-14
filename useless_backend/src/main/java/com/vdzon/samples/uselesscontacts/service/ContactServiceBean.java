package com.vdzon.samples.uselesscontacts.service;

import com.vdzon.samples.uselesscontacts.data.Contact;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;


/**
 * Created by robbert on 1-11-2014.
 */
@Stateless(name = "TeamService")
public class ContactServiceBean implements ContactService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Contact> listAll(long userID) {
        Query query =
                entityManager.createQuery("SELECT t FROM Contacts t");
        return query.getResultList();
    }

}
