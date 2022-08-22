package com.nathandownes.artcrm.analysis;
import java.util.List;
import java.util.Set;

public class Analysis {

    Long numberOfEvents;
    Long numberOfContacts;
    Long numberOfOrganisations;
    Set<EventStats> mostRecentEvents;
    Set<ContactAnalysis> ageDemographic;
    List<String> postcodes;


    public Analysis(Long numberOfEvents, Long numberOfContacts, Long numberOfOrganisations, Set<EventStats> mostRecentEvents, Set<ContactAnalysis> ageDemographic, List<String> postcodes) {
        this.numberOfEvents = numberOfEvents;
        this.numberOfContacts = numberOfContacts;
        this.numberOfOrganisations = numberOfOrganisations;
        this.mostRecentEvents = mostRecentEvents;
        this.ageDemographic = ageDemographic;
        this.postcodes = postcodes;
    }

    public List<String> getPostcodes() {
        return postcodes;
    }

    public void setPostcodes(List<String> postcodes) {
        this.postcodes = postcodes;
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

    public Set<ContactAnalysis> getAgeDemographic() {
        return ageDemographic;
    }

    public void setAgeDemographic(Set<ContactAnalysis> ageDemographic) {
        this.ageDemographic = ageDemographic;
    }
}

