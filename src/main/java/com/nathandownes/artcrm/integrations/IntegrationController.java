package com.nathandownes.artcrm.integrations;

import com.nathandownes.artcrm.integrations.Integration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "api/v1/integration")
public class IntegrationController {
    private final IntegrationService integrationService;

    @Autowired
    public IntegrationController(IntegrationService IntegrationService) {
        this.integrationService = IntegrationService;
    }

    @GetMapping(path = "/get")
    public List<Integration> getIntegrations() {
        return integrationService.getIntegrations();
    }

    @PostMapping(path = "/add")
    public void registerNewIntegration(@RequestBody Integration Integration) {
        integrationService.addNewIntegration(Integration);
    }
}