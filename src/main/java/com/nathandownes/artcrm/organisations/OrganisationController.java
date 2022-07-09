package com.nathandownes.artcrm.organisations;

import com.fasterxml.jackson.annotation.JsonView;
import com.nathandownes.artcrm.contacts.Contact;
import com.nathandownes.artcrm.events.Event;
import com.nathandownes.artcrm.tags.Tag;
import com.nathandownes.artcrm.utility.JsonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;


@RestController
@RequestMapping(path = "api/v1/organisation")
public class OrganisationController {
    private final OrganisationService organisationService;

    @Autowired
    public OrganisationController(OrganisationService OrganisationService) {
        this.organisationService = OrganisationService;
    }

    @GetMapping(path = "/getAll")
    @CrossOrigin(origins = "*")
    @JsonView(JsonModel.CoreData.class)
    public List<Organisation> getOrganisations() {
        return organisationService.getOrganisations();
    }

    @GetMapping(path = "/getSingle/{orgId}")
    public Organisation getSingleOrganisation(@PathVariable("orgId") UUID orgId) {
        return organisationService.getSingleOrganisation(orgId);
    }

    @PostMapping(path = "/create")
    public void registerNewOrganisation(@RequestBody Organisation organisation) {
        organisationService.addNewOrganisation(organisation);
    }

    @DeleteMapping(path = "/delete/{orgId}")
    public void deleteOrganisation(@PathVariable("orgId") UUID orgId) {
        organisationService.deleteOrganisation(orgId);
    }

    @PutMapping(path = "/update/{orgId}")
    public void updateOrganisation(@PathVariable("orgId") UUID orgId,
                                   @RequestParam(required = false) String name,
                                   @RequestParam(required = false) String postCode,
                                   @RequestParam(required = false) String email,
                                   @RequestParam(required = false) Set<Tag> organisationTags,
                                   @RequestParam(required = false) Set<Event> events,
                                   @RequestParam(required = false) Set<Contact> contacts) {
        organisationService.updateOrganisation(orgId, name, postCode, email, organisationTags, events, contacts);
    }
}