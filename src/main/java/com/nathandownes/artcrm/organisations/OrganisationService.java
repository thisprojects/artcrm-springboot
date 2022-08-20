package com.nathandownes.artcrm.organisations;
import com.nathandownes.artcrm.contacts.Contact;
import com.nathandownes.artcrm.events.Event;
import com.nathandownes.artcrm.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class OrganisationService {

    private final OrganisationRepository organisationRepository;

    @Autowired
    public OrganisationService(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;

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
            Set<Tag> orgTags = org.getTags();

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
        Set<Tag> tags = organisation.getTags();
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

        if (tags != null && !tags.isEmpty()) {
            Set<Tag> tagsFromDb = orgFromDb.getTags();
            for (Tag tag: tags) {
                if (tagsFromDb.contains(tag)) {
                    tagsFromDb.remove(tag);
                } else {
                    tagsFromDb.add(tag);
                }
            }
            orgFromDb.setTags(tags);
        }

        if (contacts != null && !contacts.isEmpty()) {
            Set<Contact> contactSet = orgFromDb.getContacts();
            for (Contact contact: contacts) {
                if (contactSet.contains(contact)) {
                    contactSet.remove(contact);
                } else {
                    contactSet.add(contact);
                }
            }
            orgFromDb.setContacts(contactSet);
        }
    }

    public Organisation getSingleOrganisation(UUID orgId) {
        return organisationRepository.findOrganisationById(orgId).orElseThrow(() -> new IllegalStateException("No organisation found"));
    }

}
