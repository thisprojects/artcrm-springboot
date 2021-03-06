package com.nathandownes.artcrm.events;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.nathandownes.artcrm.contacts.Contact;
import com.nathandownes.artcrm.organisations.Organisation;
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
public class Event {

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
    private String venueName;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "event_tags",
            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private Set<Tag> eventTags;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "event_organisations",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "organisation_id"))
    private Set<Organisation> organisation;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "event_contacts",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id"))
    private Set<Contact> contacts;

    public Event() {
    }

    public Event(UUID id, String name, String postCode, String venueName, Set<Tag> eventTags, Set<Organisation> organisation, Set<Contact> contacts) {
        this.id = id;
        this.name = name;
        this.postCode = postCode;
        this.venueName = venueName;
        this.eventTags = eventTags;
        this.organisation = organisation;
        this.contacts = contacts;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public Set<Organisation> getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Set<Organisation> organisation) {
        this.organisation = organisation;
    }

    public Set<Tag> getTags() {
        return eventTags;
    }

    public void setTags(Set<Tag> tags) {
        this.eventTags = eventTags;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventName='" + name + '\'' +
                '}';
    }

    public void removeOrganisations() {
        this.organisation.clear();
    }

    public void removeContacts() {
        this.contacts.clear();
    }

    public void removeEventTags() {
        this.eventTags.clear();
    }
}
