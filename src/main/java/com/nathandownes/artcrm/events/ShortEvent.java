package com.nathandownes.artcrm.events;

import com.fasterxml.jackson.annotation.JsonView;
import com.nathandownes.artcrm.utility.JsonModel;

import java.time.LocalDate;
import java.util.UUID;

public interface ShortEvent {
    UUID getId();
    String getName();
    String getPostCode();
    String getVenueName();
    LocalDate getEventDate();
}
