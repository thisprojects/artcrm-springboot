package com.nathandownes.artcrm.events;
import java.time.LocalDate;
import java.util.UUID;

public interface ShortEvent {
    UUID getId();
    String getName();
    String getPostCode();
    String getVenueName();
    LocalDate getEventDate();
}
