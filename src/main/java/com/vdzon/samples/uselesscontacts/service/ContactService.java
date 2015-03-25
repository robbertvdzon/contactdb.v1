package com.vdzon.samples.uselesscontacts.service;

import com.vdzon.samples.uselesscontacts.data.Contact;

import java.util.List;
import java.util.UUID;

public interface ContactService {
    List<Contact> listAll(long userID);
    void deleteContact(UUID uuid);
    Contact getContact(UUID uuid);
    void saveContact(Contact contact);
    void addContact(Contact contact);

}
