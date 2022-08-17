package com.nathandownes.artcrm.contacts;

import com.fasterxml.jackson.annotation.JsonView;
import com.nathandownes.artcrm.events.Event;
import com.nathandownes.artcrm.organisations.Organisation;
import com.nathandownes.artcrm.tags.Tag;
import com.nathandownes.artcrm.utility.JsonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;


@RestController
@RequestMapping(path = "api/v1/contact")
public class ContactController {
    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping(path = "/getAll")
    @CrossOrigin(origins = "*")
    @JsonView(JsonModel.CoreData.class)
    public List<Contact> getContacts() {
        return contactService.getContacts();
    }

    @GetMapping(path = "/getSingle/{contactId}")
    @CrossOrigin(origins = "*")
    public SingleContactView getSingleContact(@PathVariable("contactId") UUID contactId) {
        return contactService.getSingleContact(contactId);
    }

    @PostMapping(path = "/create")
    @CrossOrigin(origins = "*")
    public void registerNewContact(@RequestBody Contact contact) {
        contactService.addNewContact(contact);
    }

    @PostMapping(path = "/createBulk")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> registerBulkContacts(@RequestBody Set<Contact> contacts) {
        try {
            contacts.forEach(contactService::addNewContact);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }


    @DeleteMapping(path = "/delete/{contactId}")
    @CrossOrigin(origins = "*")
    public void deleteContact(@PathVariable("contactId") UUID contactId) {
        contactService.deleteContact(contactId);
    }


    @DeleteMapping(path = "/deleteMulti")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> deleteMultipleContacts(@RequestBody Set<UUID> contactIds) {
        try {
            contactIds.forEach(contactService::deleteContact);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @PutMapping(path = "/update/{contactId}")
    public void updateContact(@PathVariable("contactId") UUID contactId,
                              @RequestParam(required = false) String firstName,
                              @RequestParam(required = false) String lastName,
                              @RequestParam(required = false) String email,
                              @RequestParam(required = false) String postCode,
                              @RequestParam(required = false) Integer age,
                              @RequestParam(required = false) Set<Tag> contactTags,
                              @RequestParam(required = false) Set<Organisation> organisations,
                              @RequestParam(required = false) Set<Event> events) {
        contactService.updateContact(contactId, email, firstName, lastName, postCode, age, contactTags, organisations, events);
    }

    @PutMapping(path = "/updatejson/{contactId}")
    @CrossOrigin(origins = "*")
    public void updateContactJSON(@PathVariable("contactId") UUID contactId, @RequestBody Contact contact) {
        contactService.updateContactJson(contactId, contact);
    }
}