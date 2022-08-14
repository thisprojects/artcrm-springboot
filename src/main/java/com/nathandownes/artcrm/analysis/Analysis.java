package com.nathandownes.artcrm.analysis;

import com.nathandownes.artcrm.contacts.Contact;
import com.nathandownes.artcrm.events.Event;

import java.util.Set;

public class Analysis {

    Long numberOfEvents;
    Long numberOfContacts;
    Long numberOfOrganisations;
    Set<Event> fiveMostRecentEVents;
    Set<Contact> lastFiveContactSignups;

    public Analysis(Long numberOfEvents, Long numberOfContacts, Long numberOfOrganisations, Set<Event> fiveMostRecentEVents, Set<Contact> lastFiveContactSignups) {
        this.numberOfEvents = numberOfEvents;
        this.numberOfContacts = numberOfContacts;
        this.numberOfOrganisations = numberOfOrganisations;
        this.fiveMostRecentEVents = fiveMostRecentEVents;
        this.lastFiveContactSignups = lastFiveContactSignups;
    }

    public Long getNumberOfEvents() {
        return numberOfEvents;
    }

    public void setNumberOfEvents(Long numberOfEvents) {
        this.numberOfEvents = numberOfEvents;
    }

    public Long getNumberOfContacts() {
        return numberOfContacts;
    }

    public void setNumberOfContacts(Long numberOfContacts) {
        this.numberOfContacts = numberOfContacts;
    }

    public Long getNumberOfOrganisations() {
        return numberOfOrganisations;
    }

    public void setNumberOfOrganisations(Long numberOfOrganisations) {
        this.numberOfOrganisations = numberOfOrganisations;
    }

    public Set<Event> getFiveMostRecentEVents() {
        return fiveMostRecentEVents;
    }

    public void setFiveMostRecentEVents(Set<Event> fiveMostRecentEVents) {
        this.fiveMostRecentEVents = fiveMostRecentEVents;
    }

    public Set<Contact> getLastFiveContactSignups() {
        return lastFiveContactSignups;
    }

    public void setLastFiveContactSignups(Set<Contact> lastFiveContactSignups) {
        this.lastFiveContactSignups = lastFiveContactSignups;
    }
}
