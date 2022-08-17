package com.nathandownes.artcrm.contacts;

import com.nathandownes.artcrm.events.ShortEvent;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public interface SingleContactView {
    UUID getId();
    String getFirstName();
    String getLastName();
    String getPostCode();
    String getAge();
    Set<ShortEvent> getEvents();
}
