package com.nathandownes.artcrm.integrations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

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
        Optional<Integration> IntegrationByName = integrationRepository
                .findIntegrationByName(integration.getName());
        if (IntegrationByName.isPresent()) {
            throw new IllegalStateException("Already Exists");
        } else {
            integrationRepository.save(integration);
        }
    }

    @Transactional
    public void deleteIntegration(UUID integrationId) {

        if (integrationRepository.existsById(integrationId)) {
            Integration integration = integrationRepository.findIntegrationById(integrationId).orElseThrow(() -> new IllegalStateException("Integration not found"));
            integrationRepository.deleteById(integration.getId());
        } else {
            throw new IllegalStateException("Integration does not exist in db");
        }
    }

    @Transactional
    public void updateIntegrationJson(UUID integrationId, Integration integration) {
        Integration integrationFromDb = integrationRepository.findById(integrationId).orElseThrow(() -> new IllegalStateException("No Integration found"));

        String name = integration.getName();

        if (name != null && name.length() > 0 && !Objects.equals(name, integrationFromDb.getName())) {
            integrationFromDb.setName(name);
        }
    }

    public Integration getSingleIntegration(UUID integrationId) {
        return integrationRepository.findIntegrationById(integrationId).orElseThrow(() -> new IllegalStateException("No integration found"));
    }
}
