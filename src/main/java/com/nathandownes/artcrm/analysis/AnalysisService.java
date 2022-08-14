package com.nathandownes.artcrm.analysis;

import com.nathandownes.artcrm.contacts.ContactRepository;
import com.nathandownes.artcrm.events.EventRepository;
import com.nathandownes.artcrm.organisations.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalysisService {

    private final ContactRepository contactRepository;
    private final OrganisationRepository organisationRepository;
    private final EventRepository eventRepository;

    @Autowired
    public AnalysisService(ContactRepository ContactRepository, OrganisationRepository organisationRepository, EventRepository eventRepository) {
        this.contactRepository = ContactRepository;
        this.organisationRepository = organisationRepository;
        this.eventRepository = eventRepository;
    }

    public Analysis getAnalysis() {
        return new Analysis(eventRepository.count(), contactRepository.count(), organisationRepository.count(), eventRepository.findTop5ByOrderByCreatedDesc(), contactRepository.findTop5ByOrderByIdDesc());
    }
}
