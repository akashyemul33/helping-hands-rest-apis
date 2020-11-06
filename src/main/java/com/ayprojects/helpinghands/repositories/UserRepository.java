package com.ayprojects.helpinghands.repositories;

import com.ayprojects.helpinghands.models.User;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByMobile(String mobile);
    Optional<User> findByEmail(String email);
}
