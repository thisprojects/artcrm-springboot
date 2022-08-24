package com.nathandownes.artcrm.organisations;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.nathandownes.artcrm.contacts.Contact;
import com.nathandownes.artcrm.tags.Tag;
import com.nathandownes.artcrm.utility.JsonModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Organisation {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @JsonView(JsonModel.CoreData.class)
    private UUID id;
    @JsonView(JsonModel.CoreData.class)
    private String name;
    @JsonView(JsonModel.CoreData.class)
    private String postCode;
    @JsonView(JsonModel.CoreData.class)
    private String email;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "organisation_tags",
            joinColumns = @JoinColumn(name = "organisation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private Set<Tag> tags;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "organisation_contacts",
            joinColumns = @JoinColumn(name = "organisation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id", referencedColumnName = "id"))
    private Set<Contact> contacts;

    public Organisation(UUID id, String name, String postCode, Set<Tag> tags, Set<Contact> contacts) {
        this.id = id;
        this.name = name;
        this.postCode = postCode;

        this.tags = tags;
        this.contacts = contacts;
    }

    public Organisation() {
    }


    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void removeContacts() {
        this.contacts.clear();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void removeTag(Tag tag) {this.tags.remove(tag);}

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public void removeTags() {
        this.tags.clear();
    }


}
