package com.nathandownes.artcrm.events;

import com.nathandownes.artcrm.contacts.Attendance;
import com.nathandownes.artcrm.contacts.Contact;
import com.nathandownes.artcrm.contacts.ContactRepository;
import com.nathandownes.artcrm.organisations.Organisation;
import com.nathandownes.artcrm.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final ContactRepository contactRepository;

    @Autowired
    public EventService(EventRepository EventRepository, ContactRepository contactRepository) {
        this.eventRepository = EventRepository;
        this.contactRepository = contactRepository;
    }

    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

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

    public void deleteContactRelationship (UUID contactId, UUID eventId) {
        Contact currContact = contactRepository.findById(contactId).orElseThrow(() -> new IllegalStateException("Contact not found"));
        Set<Attendance> attendance = currContact.getAttendance();
        attendance.removeIf(item -> item.getEventId().equals(eventId));
        currContact.setAttendance(attendance);
        contactRepository.save(currContact);
    }

    @Transactional
    public void deleteEvent(UUID eventId) {

        if (eventRepository.existsById(eventId)) {
            Event event = eventRepository.findById(eventId).orElseThrow(() -> new IllegalStateException("Event not found"));

            Set<Contact> contacts = event.getContacts();
            Set<Tag> eventTags = event.getTags();
            if (!contacts.isEmpty()) {
                contacts.stream().forEach(item -> deleteContactRelationship(item.getId(), event.getId()));
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
        Set<Tag> eventTags = event.getTags();
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

        if (eventTags != null && !eventTags.isEmpty()) {
            Set<Tag> tags = eventFromDb.getTags();
            tags.addAll(eventTags);
            eventFromDb.setTags(tags);
        }

        if (contacts != null && !contacts.isEmpty()) {
            Set<Contact> contactSet = eventFromDb.getContacts();
            contactSet.addAll(contacts);
            eventFromDb.setContacts(contactSet);
            contactSet.stream().forEach(item -> addAttendance(item.getId(), eventFromDb));
        }
    }


    @Transactional
    public void addAttendance (UUID contactId, Event event) {
        Contact contact = contactRepository.findById(contactId).orElseThrow(() -> new IllegalStateException("No User found"));
        Set<Attendance> contactsAttendance = contact.getAttendance();
        contactsAttendance.add(new Attendance(event.getName(), event.getId()));
        contact.setAttendance(contactsAttendance);
        contactRepository.save(contact);
    }
    @Transactional
    public void updateEvent(UUID eventId, String name, String postCode, String venueName, Set<Tag>
            eventTags, Set<Organisation> organisations, Set<Contact> contacts) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new IllegalStateException("No event found"));

        if (name != null && name.length() > 0 && !Objects.equals(name, event.getName())) {
            event.setName(name);
        }

        if (postCode != null && postCode.length() > 0 && !Objects.equals(postCode, event.getPostCode())) {
            event.setPostCode(postCode);
        }

        if (venueName != null && venueName.length() > 0 && !Objects.equals(venueName, event.getVenueName())) {
            event.setVenueName(venueName);
        }

        if (eventTags != null && !eventTags.isEmpty()) {
            Set<Tag> tags = event.getTags();
            tags.addAll(eventTags);
            event.setTags(tags);
        }

        if (contacts != null && !contacts.isEmpty()) {
            Set<Contact> contactSet = event.getContacts();
            contactSet.addAll(contacts);
            event.setContacts(contactSet);
            contactSet.stream().forEach(item -> addAttendance(item.getId(), event));
        }
    }


}
