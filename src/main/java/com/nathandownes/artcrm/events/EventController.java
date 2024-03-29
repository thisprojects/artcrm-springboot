package com.nathandownes.artcrm.events;

import com.fasterxml.jackson.annotation.JsonView;
import com.nathandownes.artcrm.utility.JsonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(path ="/getShortEvents")
    @CrossOrigin(origins = "*")
    public List<ShortEvent> getShortEventsEvents() {
        return eventService.getShortEvents();
    }

    @PostMapping(path = "/create")
    @CrossOrigin(origins = "*")
    public void registerNewEvent(@RequestBody Event Event) {
        eventService.addNewEvent(Event);
    }

    @DeleteMapping(path = "/deleteMulti")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> deleteMultipleEvents(@RequestBody Set<UUID> eventIds) {
        try {
            eventIds.forEach(eventService::deleteEvent);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping(path = "/updatejson/{eventId}")
    @CrossOrigin(origins = "*")
    public void updateEventJSON(@PathVariable("eventId") UUID eventId, @RequestBody Event event) {
        eventService.updateEventJson(eventId, event);
    }

}