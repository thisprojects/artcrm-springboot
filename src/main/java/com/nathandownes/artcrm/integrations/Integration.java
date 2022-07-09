package com.nathandownes.artcrm.integrations;

import javax.persistence.*;

@Table @Entity
public class Integration {
    @Id
    @SequenceGenerator(
            name = "integration_sequence",
            sequenceName = "integration_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "integration_sequence"
    )
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
