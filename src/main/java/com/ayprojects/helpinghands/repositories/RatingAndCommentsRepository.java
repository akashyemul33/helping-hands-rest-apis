package com.ayprojects.helpinghands.repositories;

import com.ayprojects.helpinghands.models.DhRating_comments;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RatingAndCommentsRepository extends MongoRepository<DhRating_comments,String> {
        Page<DhRating_comments> findAllByContentIdAndContentTypeAndStatus(String contentId, String contentType, String status, PageRequest pageRequest);
}
