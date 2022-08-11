package com.nathandownes.artcrm.integrations;

import com.nathandownes.artcrm.integrations.Integration;
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
        Optional<Integration> IntegrationByEmail = integrationRepository
                .findIntegrationByName(integration.getName());
        if (IntegrationByEmail.isPresent()) {
            throw new IllegalStateException("Already Exists");
        } else {
            integrationRepository.save(integration);
        }
    }

    @Transactional
    public void deleteIntegration(UUID integrationId) {

        if (integrationRepository.existsById(integrationId)) {
            Integration integration = integrationRepository.findIntegrationById(integrationId).orElseThrow(() -> new IllegalStateException("Integration not found"));
//            Set<Contact> contacts = integration.getContacts();
//            Set<Event> events = integration.getEvents();
//            Set<Integration> integrationIntegrations = integration.getOrganisationIntegrations();
//            if (!contacts.isEmpty()) {
//                integration.removeContacts();
//            }
//            if (!events.isEmpty()) {
//                deleteEventRelationships(events);
//                integration.removeEvents();
//            }
//            if (!integrationIntegrations.isEmpty()) {
//                integration.removeIntegrations();
//            }
            integrationRepository.deleteById(integration.getId());
        } else {
            throw new IllegalStateException("Integration does not exist in db");
        }
    }

    public void updateIntegrationJson(UUID integrationId, Integration integration) {
        Integration integrationFromDb = integrationRepository.findById(integrationId).orElseThrow(() -> new IllegalStateException("No Integration found"));

        String name = integration.getName();
//        String email = integration.getEmail();
//        String postCode = integration.getPostCode();
//        Set<Integration> orgIntegrations = integration.getOrganisationIntegrations();
//        Set<Contact> contacts = integration.getContacts();


        if (name != null && name.length() > 0 && !Objects.equals(name, integrationFromDb.getName())) {
            integrationFromDb.setName(name);
        }



//        if (orgIntegrations != null && !orgIntegrations.isEmpty()) {
//            Set<Integration> integrations = integration.getOrganisationIntegrations();
//            integrations.addAll(orgIntegrations);
//            integrationFromDb.setOrganisationIntegrations(integrations);
//        }
//
//        if (contacts != null && !contacts.isEmpty()) {
//            Set<Contact> contactSet = integration.getContacts();
//            contactSet.addAll(contacts);
//            integrationFromDb.setContacts(contactSet);
//        }
    }

    public Integration getSingleIntegration(UUID integrationId) {
        return integrationRepository.findIntegrationById(integrationId).orElseThrow(() -> new IllegalStateException("No integration found"));
    }
}
