package com.ayprojects.helpinghands.repositories;

import com.ayprojects.helpinghands.models.DhPromotions;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostsRepository extends MongoRepository<DhPromotions,String> {
        Page<DhPromotions> findAllByStatus(String status, PageRequest pageRequest);
}
