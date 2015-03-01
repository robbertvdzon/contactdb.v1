package com.vdzon.samples.uselesscontacts.service;

import com.vdzon.samples.uselesscontacts.data.Contact;

import java.util.List;
import java.util.UUID;

/**
 * Created by robbert on 1-11-2014.
 */
public interface ContactService {
    List<Contact> listAll(long userID);
    void deleteContact(UUID uuid);
    Contact getContact(UUID uuid);
    void saveContact(Contact contact);
    void addContact(Contact contact);

}
