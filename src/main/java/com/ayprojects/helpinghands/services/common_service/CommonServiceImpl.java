package com.ayprojects.helpinghands.services.common_service;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    Utility utility;

    @Autowired
    MongoTemplate mongoTemplate;

    //returns true if user exist
    @Override
    public boolean checkUserExistence(String userId) {
        if (Utility.isFieldEmpty(userId)) {
            return false;
        }

        Query query = new Query(Criteria.where(AppConstants.USER_ID).is(userId));
        query.fields().include(AppConstants.OBJECT_ID);//include only _id which is indexed, as here we only need existence of user
        DhUser dhUser = mongoTemplate.findOne(query, DhUser.class);
        return dhUser != null;
    }

    @Override
    public DhUser getUserWithUserId(String userId) {
        if (Utility.isFieldEmpty(userId)) {
            return null;
        }

        Query query = new Query(Criteria.where(AppConstants.USER_ID).is(userId));
        return mongoTemplate.findOne(query, DhUser.class);
    }

    //returns true if place exist
    @Override
    public boolean checkPlaceExistence(String placeId) {
        if (Utility.isFieldEmpty(placeId)) {
            return false;
        }

        Query query = new Query(Criteria.where(AppConstants.PLACE_ID).is(placeId));
        query.fields().include(AppConstants.OBJECT_ID);//include only _id which is indexed, as here we only need existence of place
        DhPlace dhPlace = mongoTemplate.findOne(query, DhPlace.class);
        return dhPlace != null;
    }

    @Override
    public DhPlace getPlaceWithPlaceId(String placeId) {
        if (Utility.isFieldEmpty(placeId)) {
            return null;
        }

        Query query = new Query(Criteria.where(AppConstants.PLACE_ID).is(placeId));
        return mongoTemplate.findOne(query, DhPlace.class);
    }

}
