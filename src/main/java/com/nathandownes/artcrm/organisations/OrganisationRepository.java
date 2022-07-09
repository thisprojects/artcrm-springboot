package com.nathandownes.artcrm.organisations;

import com.nathandownes.artcrm.organisations.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, UUID> {



    @Query("SELECT s from Organisation s WHERE s.id = ?1")
    Optional<Organisation> findOrganisationById(UUID orgId);

    @Query("SELECT s from Organisation s WHERE s.email =?1")
    Optional<Organisation> findOrganisationByEmail(String email);
}
