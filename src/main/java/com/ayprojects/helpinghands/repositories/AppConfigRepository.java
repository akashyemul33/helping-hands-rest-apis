package com.ayprojects.helpinghands.repositories;

import com.ayprojects.helpinghands.models.DhAppConfig;
import com.ayprojects.helpinghands.models.DhUser;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AppConfigRepository extends MongoRepository<DhAppConfig,Integer> {
    Optional<DhAppConfig> findByStatus(String status);
    Optional<DhAppConfig> findByAppConfigId(String appConfigId);
}
