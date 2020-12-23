package com.ayprojects.helpinghands.repositories;

import com.ayprojects.helpinghands.models.DhAuthorization;
import com.ayprojects.helpinghands.models.DhUser;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AuthorizationRepository extends MongoRepository<DhAuthorization,String> {
    Optional<DhAuthorization> findByUsername(String username);
}
