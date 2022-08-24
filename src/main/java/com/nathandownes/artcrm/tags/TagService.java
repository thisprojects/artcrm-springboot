package com.nathandownes.artcrm.tags;

import com.nathandownes.artcrm.contacts.Contact;
import com.nathandownes.artcrm.contacts.ContactRepository;
import com.nathandownes.artcrm.events.Event;
import com.nathandownes.artcrm.events.EventRepository;
import com.nathandownes.artcrm.organisations.Organisation;
import com.nathandownes.artcrm.organisations.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final EventRepository eventRepository;
    private final ContactRepository contactRepository;
    private final OrganisationRepository organisationRepository;

    @Autowired
    public TagService(TagRepository TagRepository, EventRepository eventRepository, ContactRepository contactRepository, OrganisationRepository organisationRepository) {
        this.tagRepository = TagRepository;
        this.eventRepository = eventRepository;
        this.contactRepository = contactRepository;
        this.organisationRepository = organisationRepository;
    }

    public List<Tag> getTags() {
        return tagRepository.findAll();
    }

    public void addNewTag(Tag tag) {
        Optional<Tag> TagByEmail = tagRepository
                .findTagByName(tag.getName());
        if (TagByEmail.isPresent()) {
            throw new IllegalStateException("Tag Already Exists");
        } else {
            tagRepository.save(tag);
        }
    }

    public void deleteContactRelations(UUID contactId, Tag tag) {
        Contact contact = contactRepository.findById(contactId).orElseThrow(() -> new IllegalStateException("contact not found"));
        contact.removeTag(tag);
        contactRepository.save(contact);
    }

    public void deleteEventRelations(UUID eventId, Tag tag) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new IllegalStateException("event not found"));
        event.removeTag(tag);
        eventRepository.save(event);
    }

    public void deleteOrgRelations(UUID orgId, Tag tag) {
        Organisation org = organisationRepository.findById(orgId).orElseThrow(() -> new IllegalStateException("org not found"));
        org.removeTag(tag);
        organisationRepository.save(org);
    }

    @Transactional
    public void deleteTag(UUID tagId) {

        if (tagRepository.existsById(tagId)) {
            Tag tag = tagRepository.findTagById(tagId).orElseThrow(() -> new IllegalStateException("Tag not found"));

            Set<Contact> contactTags= tag.getContacts();
            Set<Event> eventTags = tag.getEvents();
            Set<Organisation> orgTags = tag.getOrganisations();
            if (!contactTags.isEmpty()) {
                contactTags.forEach(item -> deleteContactRelations(item.getId(), tag));
            }
            if (!eventTags.isEmpty()) {
                eventTags.forEach(item -> deleteEventRelations(item.getId(), tag));
            }
            if (!orgTags.isEmpty()) {
                orgTags.forEach(item -> deleteOrgRelations(item.getId(), tag));
            }
            tagRepository.deleteById(tag.getId());
        } else {
            throw new IllegalStateException("Tag does not exist in db");
        }
    }

    @Transactional
    public void updateTagJson(UUID tagId, Tag tag) {
        Tag tagFromDb = tagRepository.findById(tagId).orElseThrow(() -> new IllegalStateException("No Tag found"));
        String name = tag.getName();

        if (name != null && name.length() > 0 && !Objects.equals(name, tagFromDb.getName())) {
            tagFromDb.setName(name);
        }
    }

    public Tag getSingleTag(UUID tagId) {
        return tagRepository.findTagById(tagId).orElseThrow(() -> new IllegalStateException("No tag found"));
    }
}
