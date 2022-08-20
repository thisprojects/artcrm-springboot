package com.nathandownes.artcrm.events;
import com.nathandownes.artcrm.contacts.Contact;
import com.nathandownes.artcrm.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository EventRepository) {
        this.eventRepository = EventRepository;
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

        if (tags != null && !tags.isEmpty()) {
            Set<Tag> tagsFromDb = eventFromDb.getTags();
            for (Tag tag: tags) {
                if (tagsFromDb.contains(tag)) {
                    tagsFromDb.remove(tag);
                } else {
                    tagsFromDb.add(tag);
                }
            }
            eventFromDb.setTags(tagsFromDb);
        }

        if (contacts != null && !contacts.isEmpty()) {
            Set<Contact> contactSet = eventFromDb.getContacts();
            for (Contact contact: contacts) {
                if (contactSet.contains(contact)) {
                    contactSet.remove(contact);
                } else {
                    contactSet.add(contact);
                }
            }
            eventFromDb.setContacts(contactSet);
        }
    }
}
