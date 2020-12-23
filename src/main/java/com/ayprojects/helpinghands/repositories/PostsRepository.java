package com.ayprojects.helpinghands.repositories;

import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPosts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostsRepository extends MongoRepository<DhPosts,String> {
        Page<DhPosts> findAllByStatus(String status, PageRequest pageRequest);
}
