package com.nathandownes.artcrm.events;
import com.nathandownes.artcrm.contacts.Contact;
import com.nathandownes.artcrm.contacts.ContactRepository;
import com.nathandownes.artcrm.tags.Tag;
import com.nathandownes.artcrm.tags.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final ContactRepository contactRepository;
    private final TagRepository tagRepository;

    @Autowired
    public EventService(EventRepository EventRepository, ContactRepository contactRepository, TagRepository tagRepository) {
        this.eventRepository = EventRepository;
        this.contactRepository = contactRepository;
        this.tagRepository = tagRepository;
    }

    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

    public List<ShortEvent> getShortEvents() {return eventRepository.findAllByOrderByIdDesc();}

    public void addNewEvent(Event event) {
        Optional<Event> EventByName = eventRepository
                .findEventByName(event.getName());

        if (EventByName.isPresent()) {
            throw new IllegalStateException("Event Already Exists");
        } else {
            eventRepository.save(event);
        }
    }

    public Event getSingleEvent(UUID eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new IllegalStateException("No Event found"));
    }


    @Transactional
    public void deleteEvent(UUID eventId) {

        if (eventRepository.existsById(eventId)) {
            Event event = eventRepository.findById(eventId).orElseThrow(() -> new IllegalStateException("Event not found"));

            Set<Contact> contacts = event.getContacts();
            Set<Tag> eventTags = event.getTags();
            if (!contacts.isEmpty()) {
                event.removeContacts();
            }
            if (!eventTags.isEmpty()) {
                event.removeEventTags();
            }
            eventRepository.deleteById(eventId);

        } else {
            throw new IllegalStateException("Event does not exist in db");
        }
    }

    @Transactional
    public void updateEventJson(UUID eventId, Event event) {
        Event eventFromDb = eventRepository.findById(eventId).orElseThrow(() -> new IllegalStateException("No Event found"));

        String name = event.getName();
        String venueName = event.getVenueName();
        String postCode = event.getPostCode();
        LocalDate eventDate = event.getEventDate();
        Set<Tag> tags = event.getTags();
        Set<Contact> contacts = event.getContacts();


        if (name != null && name.length() > 0 && !Objects.equals(name, eventFromDb.getName())) {
            eventFromDb.setName(name);
        }

        if (postCode != null && postCode.length() > 0 && !Objects.equals(postCode, eventFromDb.getPostCode())) {
            eventFromDb.setPostCode(postCode);
        }

        if (venueName != null && venueName.length() > 0 && !Objects.equals(venueName, eventFromDb.getVenueName())) {
            eventFromDb.setVenueName(venueName);
        }

        if (eventDate != null  && !Objects.equals(eventDate, eventFromDb.getEventDate())) {
            eventFromDb.setEventDate(eventDate);
        }

        if (tags != null && !tags.isEmpty()) {
            Set<Tag> eventTagsFromDb = eventFromDb.getTags();
            for (Tag tag: tags) {
                Tag tagFromDb = tagRepository.findById(tag.getId()).orElseThrow(() -> new  IllegalStateException("No  tag found"));
                if (eventTagsFromDb.contains(tagFromDb)) {
                    eventTagsFromDb.remove(tagFromDb);
                } else {
                    eventTagsFromDb.add(tag);
                }
            }
            eventFromDb.setTags(eventTagsFromDb);
        }

        if (contacts != null && !contacts.isEmpty()) {
            Set<Contact> contactSet = eventFromDb.getContacts();
            for (Contact contact: contacts) {
               Contact contactFromDb = contactRepository.findById(contact.getId()).orElseThrow(() -> new  IllegalStateException("No  contact found"));
                if (contactSet.contains(contactFromDb)) {
                    contactSet.remove(contactFromDb);
                } else {
                    contactSet.add(contact);
                }
            }
            eventFromDb.setContacts(contactSet);
        }
    }
}
