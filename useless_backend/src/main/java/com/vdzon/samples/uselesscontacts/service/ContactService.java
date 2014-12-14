package com.vdzon.samples.uselesscontacts.service;

import com.vdzon.samples.uselesscontacts.data.Contact;

import java.util.List;

/**
 * Created by robbert on 1-11-2014.
 */
public interface ContactService {
    List<Contact> listAll(long userID);
}
