package com.nathandownes.artcrm.events;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.nathandownes.artcrm.contacts.Contact;
import com.nathandownes.artcrm.tags.Tag;
import com.nathandownes.artcrm.utility.JsonModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
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

    @JsonView(JsonModel.CoreData.class)
    private LocalDate eventDate;

    private LocalDate created;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "event_tags",
            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private Set<Tag> tags;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "event_contacts",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id"))
    private Set<Contact> contacts;

    public Event() {
    }

    public Event(UUID id, String name, String postCode, String venueName, LocalDate eventDate, Set<Tag> tags, Set<Contact> contacts) {
        this.id = id;
        this.name = name;
        this.postCode = postCode;
        this.venueName = venueName;
        this.tags = tags;
        this.contacts = contacts;
        this.eventDate = eventDate;
        this.created = LocalDate.now();
    }


    public UUID getId() {
        return id;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
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

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public void removeTag(Tag tag) {this.tags.remove(tag);}

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

    public void removeContacts() {
        this.contacts.clear();
    }

    public void removeEventTags() {
        this.tags.clear();
    }
}
