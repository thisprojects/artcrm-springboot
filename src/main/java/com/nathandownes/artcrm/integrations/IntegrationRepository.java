package com.nathandownes.artcrm.integrations;

import com.nathandownes.artcrm.integrations.Integration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IntegrationRepository extends JpaRepository<Integration, Long> {

    @Query("SELECT s from Integration s WHERE s.name = ?1")
    Optional<Integration> findIntegrationByName(String email);
}
