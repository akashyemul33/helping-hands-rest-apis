package com.ayprojects.helpinghands.repositories;

import com.ayprojects.helpinghands.models.User;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByMobileNumber(String mobileNumber);
    Optional<User> findByEmailId(String emailId);
}
