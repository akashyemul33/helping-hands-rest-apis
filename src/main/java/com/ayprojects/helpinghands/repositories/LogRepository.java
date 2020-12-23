package com.ayprojects.helpinghands.repositories;

import com.ayprojects.helpinghands.models.DhLog;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<DhLog,String> {

}
