package com.nathandownes.artcrm.integrations;

import com.nathandownes.artcrm.integrations.Integration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IntegrationService {

    private final IntegrationRepository integrationRepository;

    @Autowired
    public IntegrationService(IntegrationRepository IntegrationRepository) {
        this.integrationRepository = IntegrationRepository;
    }

    public List<Integration> getIntegrations() {
        return integrationRepository.findAll();
    }

    public void addNewIntegration(Integration integration) {
        Optional<Integration> IntegrationByEmail = integrationRepository
                .findIntegrationByName(integration.getName());
        if (IntegrationByEmail.isPresent()) {
            throw new IllegalStateException("Already Exists");
        } else {
            integrationRepository.save(integration);
        }
    }
}
