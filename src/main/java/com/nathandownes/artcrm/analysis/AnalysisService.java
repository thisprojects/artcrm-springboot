package com.nathandownes.artcrm.analysis;

import com.nathandownes.artcrm.contacts.Contact;
import com.nathandownes.artcrm.contacts.ContactRepository;
import com.nathandownes.artcrm.events.Event;
import com.nathandownes.artcrm.events.EventRepository;
import com.nathandownes.artcrm.organisations.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        Set<Event> topFiveEvents = eventRepository.findTop5ByOrderByIdDesc();
        Set<EventStats> parsedTopFiveEvents = topFiveEvents.stream().map(item -> new EventStats(item.getName(), item.getContacts().size())).collect(Collectors.toSet());

        List<Contact> allContacts = contactRepository.findAll();
        Set<String> allPostcodes = allContacts.stream().map(item -> item.getPostCode()).collect(Collectors.toSet());

        return new Analysis(eventRepository.count(), contactRepository.count(), organisationRepository.count(), parsedTopFiveEvents, contactRepository.findAllByOrderByIdDesc(), allPostcodes);
    }
}
