package com.nathandownes.artcrm.organisations;

import java.time.LocalDate;
import java.util.UUID;

public interface ShortOrg {
    UUID getId();
    String getName();
    String getPostCode();
    String getEmail();
}
