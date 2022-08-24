package com.nathandownes.artcrm.organisations;

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
    @CrossOrigin(origins = "*")
    public Organisation getSingleOrganisation(@PathVariable("orgId") UUID orgId) {
        return organisationService.getSingleOrganisation(orgId);
    }

    @PostMapping(path = "/create")
    @CrossOrigin(origins = "*")
    public void registerNewOrganisation(@RequestBody Organisation organisation) {
        organisationService.addNewOrganisation(organisation);
    }

    @DeleteMapping(path = "/delete/{orgId}")
    @CrossOrigin(origins = "*")
    public void deleteOrganisation(@PathVariable("orgId") UUID orgId) {
        organisationService.deleteOrganisation(orgId);
    }

    @DeleteMapping(path = "/deleteMulti")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> deleteMultipleOrgs(@RequestBody Set<UUID> orgIds) {
        try {
            orgIds.forEach(organisationService::deleteOrganisation);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping(path = "/updatejson/{orgId}")
    @CrossOrigin(origins = "*")
    public void updateOrgJSON(@PathVariable("orgId") UUID orgId, @RequestBody Organisation org) {
        organisationService.updateOrganisationJson(orgId, org);
    }
}