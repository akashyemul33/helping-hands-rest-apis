package com.ayprojects.helpinghands.api.classes.delete_strategy;

import com.ayprojects.helpinghands.api.behaviours.StrategyDeleteBehaviour;
import com.ayprojects.helpinghands.models.DhRatingAndComments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class StrategyDeleteRating implements StrategyDeleteBehaviour<DhRatingAndComments> {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void deleteById(Authentication authentication, String objId, int version) {

    }

    @Override
    public void updateStatusToPending(Authentication authentication, String objId, int version) {

    }
}
