package com.vdzon.samples.uselesscontacts.service;

import com.vdzon.samples.uselesscontacts.data.User;

/**
 * Created by robbert on 1-11-2014.
 */
public interface UserService {
    User findUser(String username, String passwd);
    User findUser(long id);
    void syncUser(User user);
}
