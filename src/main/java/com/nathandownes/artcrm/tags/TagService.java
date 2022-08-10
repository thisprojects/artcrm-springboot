package com.nathandownes.artcrm.tags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository TagRepository) {
        this.tagRepository = TagRepository;
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

    @Transactional
    public void deleteTag(UUID tagId) {

        if (tagRepository.existsById(tagId)) {
            Tag tag = tagRepository.findTagById(tagId).orElseThrow(() -> new IllegalStateException("Tag not found"));
//            Set<Contact> contacts = tag.getContacts();
//            Set<Event> events = tag.getEvents();
//            Set<Tag> tagTags = tag.getOrganisationTags();
//            if (!contacts.isEmpty()) {
//                tag.removeContacts();
//            }
//            if (!events.isEmpty()) {
//                deleteEventRelationships(events);
//                tag.removeEvents();
//            }
//            if (!tagTags.isEmpty()) {
//                tag.removeTags();
//            }
            tagRepository.deleteById(tag.getId());
        } else {
            throw new IllegalStateException("Tag does not exist in db");
        }
    }

    @Transactional
    public void updateTagJson(UUID tagId, Tag tag) {
        Tag tagFromDb = tagRepository.findById(tagId).orElseThrow(() -> new IllegalStateException("No Tag found"));

        String name = tag.getName();
//        String email = tag.getEmail();
//        String postCode = tag.getPostCode();
//        Set<Tag> orgTags = tag.getOrganisationTags();
//        Set<Contact> contacts = tag.getContacts();


        if (name != null && name.length() > 0 && !Objects.equals(name, tagFromDb.getName())) {
            tagFromDb.setName(name);
        }



//        if (orgTags != null && !orgTags.isEmpty()) {
//            Set<Tag> tags = tag.getOrganisationTags();
//            tags.addAll(orgTags);
//            tagFromDb.setOrganisationTags(tags);
//        }
//
//        if (contacts != null && !contacts.isEmpty()) {
//            Set<Contact> contactSet = tag.getContacts();
//            contactSet.addAll(contacts);
//            tagFromDb.setContacts(contactSet);
//        }
    }

    public Tag getSingleTag(UUID tagId) {
        return tagRepository.findTagById(tagId).orElseThrow(() -> new IllegalStateException("No tag found"));
    }
}
