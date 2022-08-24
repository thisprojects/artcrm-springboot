package com.nathandownes.artcrm.tags;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.nathandownes.artcrm.contacts.Contact;
import com.nathandownes.artcrm.events.Event;
import com.nathandownes.artcrm.organisations.Organisation;
import com.nathandownes.artcrm.utility.JsonModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Table
@Entity
public class Tag {
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
    private String name;

    public void setId(UUID id) {
        this.id = id;
    }
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, mappedBy = "contactTags")
    private Set<Contact> contacts;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, mappedBy = "tags")
    private Set<Event> events;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, mappedBy = "tags")
    private Set<Organisation> organisations;

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public Set<Organisation> getOrganisations() {
        return organisations;
    }

    public void setOrganisations(Set<Organisation> organisations) {
        this.organisations = organisations;
    }

    public void emptyContactTags() {
        this.contacts.clear();
    }

    public void emptyOrgTags() {
        this.organisations.clear();
    }

    public void emptyEventTags() {
        this.events.clear();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
