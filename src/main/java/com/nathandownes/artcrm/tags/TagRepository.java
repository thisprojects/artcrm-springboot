package com.nathandownes.artcrm.tags;

import com.nathandownes.artcrm.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("SELECT s from Tag s WHERE s.name = ?1")
    Optional<Tag> findTagByName(String email);
}
