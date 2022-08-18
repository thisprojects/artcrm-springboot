package com.nathandownes.artcrm.contacts;

import com.nathandownes.artcrm.events.Event;
import com.nathandownes.artcrm.events.EventRepository;
import com.nathandownes.artcrm.organisations.Organisation;
import com.nathandownes.artcrm.organisations.OrganisationRepository;
import com.nathandownes.artcrm.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final OrganisationRepository organisationRepository;
    private final EventRepository eventRepository;

    @Autowired
    public ContactService(ContactRepository ContactRepository, OrganisationRepository organisationRepository, EventRepository eventRepository) {
        this.contactRepository = ContactRepository;
        this.organisationRepository = organisationRepository;
        this.eventRepository = eventRepository;
    }

    public List<Contact> getContacts() {
        return contactRepository.findAll();
    }

    public void addNewContact(Contact contact) {
        Optional<Contact> contactEmail = contactRepository
                .findContactByEmail(contact.getEmail());
        if (contactEmail.isPresent()) {
            throw new IllegalStateException("Already Exists");
        } else if (contact.getEmail() == null) {
            throw new IllegalStateException("Email was not provided");
        } else {
            contact.setCreated();
            contactRepository.save(contact);
        }
    }

    public void deleteOrgRelationships(Set<Organisation> orgs) {
        for (Organisation org : orgs) {
            Organisation organisation = organisationRepository.findById(org.getId()).orElseThrow(() -> new IllegalStateException("Org not found"));
            organisation.removeContacts();
        }
    }

    public void deleteEventRelationships(Set<Event> events) {
        for (Event event : events) {
            Event eventToRemoveRelationshipsFrom = eventRepository.findById(event.getId()).orElseThrow(() -> new IllegalStateException("Org not found"));
            eventToRemoveRelationshipsFrom.removeContacts();
        }
    }

    @Transactional
    public void deleteContact(UUID contactId) {

        if (contactRepository.existsById(contactId)) {
            Contact contact = contactRepository.findById(contactId).orElseThrow(() -> new IllegalStateException("Contact not found"));


            Set<Tag> contactTags = contact.getTags();
            Set<Event> contactEvents = contact.getEvents();
            Set<Organisation> contactOrgs = contact.getOrganisations();

            if (!contactOrgs.isEmpty()) {
                deleteOrgRelationships(contactOrgs);
            }

            if (!contactEvents.isEmpty()) {
                deleteEventRelationships(contactEvents);
            }

            if (!contactTags.isEmpty()) {
                contact.removeTags();
            }
            contactRepository.deleteById(contactId);
        } else {
            throw new IllegalStateException("Contact does not exist in db");
        }
    }

    @Transactional
    public void updateContact(UUID contactId, String email, String firstName, String lastName, String postCode, Integer age, Set<Tag>
            contactTags) {
        Contact contact = contactRepository.findById(contactId).orElseThrow(() -> new IllegalStateException("No contact found"));

        if (firstName != null && firstName.length() > 0 && !Objects.equals(firstName, contact.getFirstName())) {
            contact.setFirstName(firstName);
        }

        if (lastName != null && lastName.length() > 0 && !Objects.equals(lastName, contact.getLastName())) {
            contact.setLastName(lastName);
        }

        if (postCode != null && postCode.length() > 0 && !Objects.equals(postCode, contact.getPostCode())) {
            contact.setPostCode(postCode);
        }

        if (email != null && email.length() > 0 && !Objects.equals(email, contact.getEmail())) {
            contact.setEmail(email);
        }

        if (age != null && age > 0 && !Objects.equals(age, contact.getAge())) {
            contact.setAge(age);
        }

        if (contactTags != null && !contactTags.isEmpty()) {
            Set<Tag> tags = contact.getTags();
            tags.addAll(contactTags);
            contact.setTags(tags);
        }

    }

    @Transactional
    public void updateContactJson(UUID contactId, Contact contact) {
        Contact contactFromDb = contactRepository.findById(contactId).orElseThrow(() -> new IllegalStateException("No contact found"));

        String firstName = contact.getFirstName();
        String lastName = contact.getLastName();
        String postCode = contact.getPostCode();
        String email = contact.getEmail();
        Integer age = contact.getAge();
        Set<Tag> contactTags = contact.getTags();


        if (firstName != null && firstName.length() > 0 && !Objects.equals(firstName, contactFromDb.getFirstName())) {
            contactFromDb.setFirstName(firstName);
        }

        if (lastName != null && lastName.length() > 0 && !Objects.equals(lastName, contactFromDb.getLastName())) {
            contactFromDb.setLastName(lastName);
        }

        if (postCode != null && postCode.length() > 0 && !Objects.equals(postCode, contactFromDb.getPostCode())) {
            contactFromDb.setPostCode(postCode);
        }

        if (email != null && email.length() > 0 && !Objects.equals(email, contactFromDb.getEmail())) {
            contactFromDb.setEmail(email);
        }

        if (age != null && age > 0 && !Objects.equals(age, contactFromDb.getAge())) {
            contactFromDb.setAge(age);
        }

        if (contactTags != null && !contactTags.isEmpty()) {
            Set<Tag> tags = contact.getTags();
            tags.addAll(contactTags);
            contactFromDb.setTags(tags);
        }

    }


    public SingleContactView getSingleContact(UUID contactId) {
        return contactRepository.findSingleById(contactId).orElseThrow(() -> new IllegalStateException("No contact found"));
    }
}
