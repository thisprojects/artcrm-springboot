package com.nathandownes.artcrm.tags;

import com.nathandownes.artcrm.organisations.Organisation;
import com.nathandownes.artcrm.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {

    @Query("SELECT s from Tag s WHERE s.name = ?1")
    Optional<Tag> findTagByName(String email);


    @Query("SELECT s from Tag s WHERE s.id = ?1")
    Optional<Tag> findTagById(UUID tagId);
}
