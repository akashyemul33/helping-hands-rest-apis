package com.ayprojects.helpinghands.repositories;

import com.ayprojects.helpinghands.models.DhPosts;
import com.ayprojects.helpinghands.models.DhRequirements;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RequirementsRepository extends MongoRepository<DhRequirements,String> {
        Page<DhRequirements> findAllByStatus(String status, PageRequest pageRequest);
}
