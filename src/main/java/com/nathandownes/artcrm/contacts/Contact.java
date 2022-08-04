package com.nathandownes.artcrm.contacts;

import com.fasterxml.jackson.annotation.*;
import com.nathandownes.artcrm.events.Event;
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
public class Contact {
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
    private String firstName;
    @JsonView(JsonModel.CoreData.class)
    private String lastName;
    @JsonView(JsonModel.CoreData.class)
    private String postCode;
    @JsonView(JsonModel.CoreData.class)
    private String email;
    @JsonView(JsonModel.CoreData.class)
    private Integer age;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "contact_tags",
            joinColumns = @JoinColumn(name = "contact_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private Set<Tag> contactTags;
    @ManyToMany(mappedBy = "contacts", fetch = FetchType.LAZY,
            cascade =
                    CascadeType.MERGE
    )
    private Set<Organisation> organisations;
    @ManyToMany(mappedBy = "contacts",
            fetch = FetchType.LAZY,
            cascade =
                    CascadeType.MERGE
    )
    private Set<Event> events;


    public Contact(UUID id, String firstName, String email, String lastName, String postCode, Integer age, Set<Tag> contactTags, Set<Organisation> organisations, Set<Event> events) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.postCode = postCode;
        this.email = email;
        this.age = age;
        this.contactTags = contactTags;
        this.organisations = organisations;
        this.events = events;
    }

    public Contact() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void removeOrganisations() {
        this.organisations.clear();
    }

    public void removeEvents() {
        this.events.clear();
    }

    public void removeTags() {
        this.contactTags.clear();
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<Tag> getTags() {
        return contactTags;
    }

    public void setTags(Set<Tag> contactTags) {
        this.contactTags = contactTags;
    }

    public Set<Organisation> getOrganisations() {
        return organisations;
    }

    public void setOrganisations(Set<Organisation> organisations) {
        this.organisations = organisations;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
