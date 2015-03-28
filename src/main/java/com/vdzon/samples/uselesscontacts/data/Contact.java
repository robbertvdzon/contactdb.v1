package com.vdzon.samples.uselesscontacts.data;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;

import javax.persistence.*;
import javax.ws.rs.WebApplicationException;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.UUID;

@XmlRootElement
@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(generator="CONTACT_TABLE_SEQ",strategy=GenerationType.TABLE)
    @TableGenerator(name="CONTACT_TABLE_SEQ",
            table="sequences",
            pkColumnName="SEQ_NAME", // Specify the name of the column of the primary key
            valueColumnName="SEQ_NUMBER", // Specify the name of the column that stores the last value generated
            pkColumnValue="CONTACT_ID", // Specify the primary key column value that would be considered as a primary key generator
            allocationSize=1)
    private Long id;
    private UUID uuid;
    private Long userId;
    private String name;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Contact person = (Contact) o;

        return id.equals(person.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
