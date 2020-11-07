package com.ayprojects.helpinghands.repositories;

import com.ayprojects.helpinghands.models.DhUser;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<DhUser,String> {
    Optional<DhUser> findByMobileNumber(String mobileNumber);
    Optional<DhUser> findByEmailId(String emailId);
}
