package com.ayprojects.helpinghands.repositories;

import com.ayprojects.helpinghands.models.DhPlace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlaceRepository extends MongoRepository<DhPlace,String> {
        Page<DhPlace> findAllByStatus(String status, PageRequest pageRequest);
}
