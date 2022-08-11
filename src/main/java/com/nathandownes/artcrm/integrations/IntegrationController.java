package com.nathandownes.artcrm.integrations;

import com.nathandownes.artcrm.integrations.Integration;
import com.nathandownes.artcrm.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;


@RestController
@RequestMapping(path = "api/v1/integration")
public class IntegrationController {
    private final IntegrationService integrationService;

    @Autowired
    public IntegrationController(IntegrationService IntegrationService) {
        this.integrationService = IntegrationService;
    }


    @GetMapping(path = "/getAll")
    @CrossOrigin(origins = "*")
    public List<Integration> getIntegrations() {
        return integrationService.getIntegrations();
    }

    @GetMapping(path = "/getSingle/{integrationId}")
    @CrossOrigin(origins = "*")
    public Integration getSingleIntegration(@PathVariable("integrationId") UUID integrationId) {
        return integrationService.getSingleIntegration(integrationId);
    }

    @PostMapping(path = "/create")
    @CrossOrigin(origins = "*")
    public void registerNewIntegration(@RequestBody Integration integration) {
        integrationService.addNewIntegration(integration);
    }

    @DeleteMapping(path = "/delete/{integrationId}")
    @CrossOrigin(origins = "*")
    public void deleteIntegration(@PathVariable("integrationId") UUID integrationId) {
        integrationService.deleteIntegration(integrationId);
    }

    @DeleteMapping(path = "/deleteMulti")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> deleteMultipleTgs(@RequestBody Set<UUID> integrationIds) {
        try {
            integrationIds.forEach(integrationService::deleteIntegration);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping(path = "/updatejson/{integrationId}")
    @CrossOrigin(origins = "*")
    public void updateIntegrationJSON(@PathVariable("integrationId") UUID integrationId, @RequestBody Integration org) {
        integrationService.updateIntegrationJson(integrationId, org);
    }
}