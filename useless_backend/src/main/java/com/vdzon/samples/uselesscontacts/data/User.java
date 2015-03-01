package com.vdzon.samples.uselesscontacts.data;

import org.codehaus.jackson.map.ObjectMapper;

import javax.persistence.*;
import javax.ws.rs.WebApplicationException;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@XmlRootElement
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(generator="CONTACT_TABLE_SEQ",strategy=GenerationType.TABLE)
    @TableGenerator(name="CONTACT_TABLE_SEQ",
            table="SEQUENCES",
            pkColumnName="SEQ_NAME", // Specify the name of the column of the primary key
            valueColumnName="SEQ_NUMBER", // Specify the name of the column that stores the last value generated
            pkColumnValue="CONTACT_ID", // Specify the primary key column value that would be considered as a primary key generator
            allocationSize=1)
    private Long id;
    private UUID uuid;

    private String username;
    private String passwd;

    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Contact> contacts;

    @Transient
    private String permissions;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

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

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}