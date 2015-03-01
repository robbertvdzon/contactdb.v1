package com.vdzon.samples.uselesscontacts.model;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.UUID;

public class ContactModel {
    private UUID uuid;
    private String name;
    private String email;

    private static ObjectMapper mapper = new ObjectMapper(); //Jackson's JSON marshaller

    // the fromString is needed to convert json to object. This is needed for using this object as a PathParam in REST
    public static ContactModel fromString(String jsonRepresentation) throws IOException {
        return mapper.readValue(jsonRepresentation, ContactModel.class );
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
