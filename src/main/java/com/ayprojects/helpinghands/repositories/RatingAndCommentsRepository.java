package com.ayprojects.helpinghands.repositories;

import com.ayprojects.helpinghands.models.DhRatingAndComments;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RatingAndCommentsRepository extends MongoRepository<DhRatingAndComments,String> {
}
