package com.vdzon.samples.uselesscontacts.data;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class AuthAccessElement implements Serializable {

    public static final String PARAM_AUTH_ID = "auth-id";
    public static final String PARAM_AUTH_TOKEN = "auth-token";

    private long authId;
    private String authToken;
    private String authPermission;

    public AuthAccessElement() {
    }

    public AuthAccessElement(long authId, String authToken, String authPermission) {
        this.authId = authId;
        this.authToken = authToken;
        this.authPermission = authPermission;
    }

    // Getters and setters

    public long getAuthId() {
        return authId;
    }

    public void setAuthId(long authId) {
        this.authId = authId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthPermission() {
        return authPermission;
    }

    public void setAuthPermission(String authPermission) {
        this.authPermission = authPermission;
    }
}