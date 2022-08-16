package com.nathandownes.artcrm.organisations;

import com.nathandownes.artcrm.contacts.Contact;
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

    @Autowired
    public OrganisationService(OrganisationRepository organisationRepository, EventRepository eventRepository) {
        this.organisationRepository = organisationRepository;
        this.eventRepository = eventRepository;
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



    @Transactional
    public void deleteOrganisation(UUID orgId) {

        if (organisationRepository.existsById(orgId)) {
            Organisation org = organisationRepository.findOrganisationById(orgId).orElseThrow(() -> new IllegalStateException("Organisation not found"));
            Set<Contact> contacts = org.getContacts();

            Set<Tag> orgTags = org.getOrganisationTags();
            if (!contacts.isEmpty()) {
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
        }
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
