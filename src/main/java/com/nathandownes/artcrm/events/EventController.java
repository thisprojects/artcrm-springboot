package com.nathandownes.artcrm.events;

import com.fasterxml.jackson.annotation.JsonView;
import com.nathandownes.artcrm.contacts.Contact;
import com.nathandownes.artcrm.events.Event;
import com.nathandownes.artcrm.organisations.Organisation;
import com.nathandownes.artcrm.tags.Tag;
import com.nathandownes.artcrm.utility.JsonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/event")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService EventService) {
        this.eventService = EventService;
    }

    @GetMapping(path = "/getAll")
    @CrossOrigin(origins = "*")
    @JsonView(JsonModel.CoreData.class)
    public List<Event> getEvents() {
        return eventService.getEvents();
    }

    @GetMapping(path = "/getSingle/{eventId}")
    @CrossOrigin(origins = "*")
    public Event getSingleEvent(@PathVariable("eventId") UUID eventId) {
        return eventService.getSingleEvent(eventId);
    }

    @PostMapping(path = "/create")
    public void registerNewEvent(@RequestBody Event Event) {
        eventService.addNewEvent(Event);
    }

    @DeleteMapping(path = "/delete/{eventId}")
    public void deleteEvent(@PathVariable("eventId") UUID eventId) {
        eventService.deleteEvent(eventId);
    }

    @PutMapping(path = "/update/{eventId}")
    public void updateEvent(@PathVariable("eventId") UUID eventId,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String postCode,
                              @RequestParam(required = false) String venueName,
                              @RequestParam(required = false) Set<Tag> eventTags,
                              @RequestParam(required = false) Set<Organisation> organisations,
                              @RequestParam(required = false) Set<Contact> contacts) {
        eventService.updateEvent(eventId,name, postCode, venueName, eventTags, organisations, contacts);
    }

}