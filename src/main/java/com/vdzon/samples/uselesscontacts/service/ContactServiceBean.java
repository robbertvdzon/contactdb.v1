package com.vdzon.samples.uselesscontacts.service;

import com.vdzon.samples.uselesscontacts.data.Contact;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;
import java.util.UUID;


@Stateless(name = "TeamService")
public class ContactServiceBean implements ContactService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Contact> listAll(long userID) {
        Query query =
                entityManager.createQuery("SELECT c FROM Contact c");
        return query.getResultList();
    }

    public Contact getContact(UUID uuid) {
        return (Contact) entityManager.createQuery("SELECT c FROM Contact c WHERE uuid= :uuid").setParameter("uuid", uuid).getSingleResult();
    }

    public void deleteContact(UUID uuid) {
        entityManager.remove(getContact(uuid));
    }

    public void saveContact(Contact contact) {
        entityManager.merge(contact);
    }

    public void addContact(Contact contact) {
        entityManager.persist(contact);
    }

}
