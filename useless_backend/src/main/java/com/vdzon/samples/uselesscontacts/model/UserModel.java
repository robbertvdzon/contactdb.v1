package com.vdzon.samples.uselesscontacts.model;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.UUID;

public class UserModel {
    private UUID uuid;
    private String username;

    private static ObjectMapper mapper = new ObjectMapper(); //Jackson's JSON marshaller

    // the fromString is needed to convert json to object. This is needed for using this object as a PathParam in REST
    public static UserModel fromString(String jsonRepresentation) throws IOException {
        return mapper.readValue(jsonRepresentation, UserModel.class );
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
