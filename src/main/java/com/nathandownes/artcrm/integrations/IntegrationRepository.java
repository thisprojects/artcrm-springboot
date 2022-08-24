package com.nathandownes.artcrm.integrations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IntegrationRepository extends JpaRepository<Integration, UUID> {

    @Query("SELECT s from Integration s WHERE s.name = ?1")
    Optional<Integration> findIntegrationByName(String email);

    @Query("SELECT s from Integration s WHERE s.id = ?1")
    Optional<Integration> findIntegrationById(UUID integrationId);

}
