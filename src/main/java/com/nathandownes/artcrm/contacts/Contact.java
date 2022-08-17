package com.nathandownes.artcrm.contacts;

import com.fasterxml.jackson.annotation.*;
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

    private LocalDate created;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "contact_tags",
            joinColumns = @JoinColumn(name = "contact_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private Set<Tag> contactTags;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "contact_attendance",
            joinColumns = @JoinColumn(name = "contact_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "attendance_id", referencedColumnName = "id")
    )
    private Set<Attendance> attendance;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "contact_shortorg",
            joinColumns = @JoinColumn(name = "contact_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "shortorg_id", referencedColumnName = "id")
    )
    private Set<ShortOrg> organisations;

    public Contact(UUID id, String firstName, String email, String lastName, String postCode, Integer age, LocalDate created, Set<Tag> contactTags, Set<Attendance> attendance, Set<ShortOrg> organisations) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.postCode = postCode;
        this.email = email;
        this.age = age;
        this.contactTags = contactTags;
        this.attendance = attendance;
        this.organisations = organisations;
        this.created = LocalDate.now();
    }

    public Contact() {
    }

    public Set<Attendance> getAttendance() {
        return attendance;
    }

    public void setAttendance(Set<Attendance> attendance) {
        this.attendance = attendance;
    }

    public void addAttendance(Attendance event) {
        this.attendance.add(event);
    }

    public void setCreated() {
        this.created = LocalDate.now();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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



    @Override
    public String toString() {
        return "Contact{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public Set<ShortOrg> getOrganisations() {
        return organisations;
    }

    public void setOrganisations(Set<ShortOrg> organisations) {
        this.organisations = organisations;
    }
}
