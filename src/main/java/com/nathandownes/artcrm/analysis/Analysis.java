package com.nathandownes.artcrm.analysis;
import java.util.Set;

public class Analysis {

    Long numberOfEvents;
    Long numberOfContacts;
    Long numberOfOrganisations;
    Set<EventStats> mostRecentEvents;
    Set<ContactAnalysis> lastFiveContactSignups;

    public Analysis(Long numberOfEvents, Long numberOfContacts, Long numberOfOrganisations, Set<EventStats> mostRecentEvents, Set<ContactAnalysis> lastFiveContactSignups) {
        this.numberOfEvents = numberOfEvents;
        this.numberOfContacts = numberOfContacts;
        this.numberOfOrganisations = numberOfOrganisations;
        this.mostRecentEvents = mostRecentEvents;
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

    public Set<EventStats> getMostRecentEvents() {
        return this.mostRecentEvents;
    }

    public void setMostRecentEvents(Set<EventStats> mostRecentEvents) {
        this.mostRecentEvents = mostRecentEvents;
    }

    public Set<ContactAnalysis> getLastFiveContactSignups() {
        return lastFiveContactSignups;
    }

    public void setLastFiveContactSignups(Set<ContactAnalysis> lastFiveContactSignups) {
        this.lastFiveContactSignups = lastFiveContactSignups;
    }
}

