package com.nathandownes.artcrm.contacts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<Contact, UUID> {

    @Query("SELECT s from Contact s WHERE s.email = ?1")
    Optional<Contact> findContactByEmail(String email);

    long count();

    Set<Contact> findTop5ByOrderByIdDesc();
}
