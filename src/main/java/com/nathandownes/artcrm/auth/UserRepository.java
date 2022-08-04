package com.nathandownes.artcrm.auth;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface UserRepository extends JpaRepository<ArtCrmUser, Integer> {


    public ArtCrmUser findByUsername(String username);
}