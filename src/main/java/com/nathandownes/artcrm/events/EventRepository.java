package com.nathandownes.artcrm.events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    @Query("SELECT s FROM Event s WHERE s.name = ?1")
    Optional<Event> findEventByName(String email);
    long count();

    Set<Event> findTop5ByOrderByIdDesc();


    List<ShortEvent> findAllByOrderByIdDesc();
}
