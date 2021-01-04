package com.ayprojects.helpinghands.repositories;

import com.ayprojects.helpinghands.models.DhNewUserSupport;
import com.ayprojects.helpinghands.models.DhUser;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface NewUserSupportRepository extends MongoRepository<DhNewUserSupport,String> {

}
