package com.nathandownes.artcrm.organisations;

import com.nathandownes.artcrm.contacts.Attendance;
import com.nathandownes.artcrm.contacts.Contact;
import com.nathandownes.artcrm.contacts.ContactRepository;
import com.nathandownes.artcrm.contacts.ShortOrg;
import com.nathandownes.artcrm.events.Event;
import com.nathandownes.artcrm.events.EventRepository;
import com.nathandownes.artcrm.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class OrganisationService {

    private final OrganisationRepository organisationRepository;
    private final EventRepository eventRepository;
    private final ContactRepository contactRepository;

    @Autowired
    public OrganisationService(OrganisationRepository organisationRepository, EventRepository eventRepository, ContactRepository contactRepository) {
        this.organisationRepository = organisationRepository;
        this.eventRepository = eventRepository;
        this.contactRepository = contactRepository;
    }

    public List<Organisation> getOrganisations() {
        return organisationRepository.findAll();
    }

    public void addNewOrganisation(Organisation organisation) {
        Optional<Organisation> OrganisationByEmail = organisationRepository
                .findOrganisationByEmail(organisation.getEmail());
        if (OrganisationByEmail.isPresent()) {
            throw new IllegalStateException("Organisation Already Exists");
        } else {
            organisationRepository.save(organisation);
        }
    }


    public void deleteContactRelationship (UUID contactId, UUID orgId) {
        Contact currContact = contactRepository.findById(contactId).orElseThrow(() -> new IllegalStateException("Contact not found"));
        Set<ShortOrg> shortOrg = currContact.getOrganisations();
        shortOrg.removeIf(item -> item.getOrgId().equals(orgId));
        currContact.setOrganisations(shortOrg);
        contactRepository.save(currContact);
    }
    @Transactional
    public void deleteOrganisation(UUID orgId) {

        if (organisationRepository.existsById(orgId)) {
            Organisation org = organisationRepository.findOrganisationById(orgId).orElseThrow(() -> new IllegalStateException("Organisation not found"));
            Set<Contact> contacts = org.getContacts();
            Set<Tag> orgTags = org.getOrganisationTags();

            if (!contacts.isEmpty()) {
                contacts.stream().forEach(item -> deleteContactRelationship(item.getId(), org.getId()));
                org.removeContacts();
            }

            if (!orgTags.isEmpty()) {
                org.removeTags();
            }
            organisationRepository.deleteById(orgId);
        } else {
            throw new IllegalStateException("Organisation does not exist in db");
        }
    }

    @Transactional
    public void updateOrganisationJson(UUID orgId, Organisation organisation) {
        Organisation orgFromDb = organisationRepository.findById(orgId).orElseThrow(() -> new IllegalStateException("No Organisation found"));

        String name = organisation.getName();
        String email = organisation.getEmail();
        String postCode = organisation.getPostCode();
        Set<Tag> orgTags = organisation.getOrganisationTags();
        Set<Contact> contacts = organisation.getContacts();


        if (name != null && name.length() > 0 && !Objects.equals(name, orgFromDb.getName())) {
            orgFromDb.setName(name);
        }

        if (postCode != null && postCode.length() > 0 && !Objects.equals(postCode, orgFromDb.getPostCode())) {
            orgFromDb.setPostCode(postCode);
        }

        if (email != null && email.length() > 0 && !Objects.equals(email, orgFromDb.getEmail())) {
            orgFromDb.setEmail(email);
        }

        if (orgTags != null && !orgTags.isEmpty()) {
            Set<Tag> tags = organisation.getOrganisationTags();
            tags.addAll(orgTags);
            orgFromDb.setOrganisationTags(tags);
        }

        if (contacts != null && !contacts.isEmpty()) {
            Set<Contact> contactSet = organisation.getContacts();
            contactSet.addAll(contacts);
            orgFromDb.setContacts(contactSet);
            contactSet.stream().forEach(item -> addShortOrg(item.getId(), orgFromDb));
        }
    }

    @Transactional
    public void addShortOrg (UUID contactId, Organisation org) {
        Contact contact = contactRepository.findById(contactId).orElseThrow(() -> new IllegalStateException("No User found"));
        Set<ShortOrg> contactsOrgs = contact.getOrganisations();
        contactsOrgs.add(new ShortOrg(org.getName(), org.getId()));
        contact.setOrganisations(contactsOrgs);
        contactRepository.save(contact);
    }

    @Transactional
    public void updateOrganisation(UUID orgId, String name, String postCode, String email, Set<Tag>
            organisationTags, Set<Event> events, Set<Contact> contacts) {
        Organisation organisation = organisationRepository.findOrganisationById(orgId).orElseThrow(() -> new IllegalStateException("No Organisation found"));

        if (name != null && name.length() > 0 && !Objects.equals(name, organisation.getName())) {
            organisation.setName(name);
        }

        if (postCode != null && postCode.length() > 0 && !Objects.equals(postCode, organisation.getPostCode())) {
            organisation.setPostCode(postCode);
        }

        if (email != null && email.length() > 0 && !Objects.equals(email, organisation.getEmail())) {
            organisation.setEmail(email);
        }

        if (organisationTags != null && !organisationTags.isEmpty()) {
            Set<Tag> tags = organisation.getOrganisationTags();
            tags.addAll(organisationTags);
            organisation.setOrganisationTags(tags);
        }

        if (contacts != null && !contacts.isEmpty()) {
            Set<Contact> currentContacts = organisation.getContacts();
            currentContacts.addAll(contacts);
            organisation.setContacts(currentContacts);
        }

    }


    public Organisation getSingleOrganisation(UUID orgId) {
        return organisationRepository.findOrganisationById(orgId).orElseThrow(() -> new IllegalStateException("No organisation found"));
    }

}
