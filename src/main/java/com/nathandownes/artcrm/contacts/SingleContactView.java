package com.nathandownes.artcrm.contacts;

import com.nathandownes.artcrm.events.ShortEvent;
import com.nathandownes.artcrm.organisations.ShortOrg;
import com.nathandownes.artcrm.tags.Tag;
import java.util.Set;
import java.util.UUID;

public interface SingleContactView {
    UUID getId();
    String getFirstName();
    String getLastName();
    String getPostCode();
    String getAge();
    String getEmail();
    Set<ShortEvent> getEvents();
    Set<ShortOrg> getOrganisations();
    Set<Tag> getTags();
}
