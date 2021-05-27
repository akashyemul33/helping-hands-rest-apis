package com.ayprojects.helpinghands.api.classes.get_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyGetBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.models.DhHHCategory;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class StrategyGetHhCategories implements StrategyGetBehaviour<DhHHCategory> {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<DhHHCategory> get(String language, HashMap<String, Object> params) {
        if (params == null) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS), new ArrayList<>());
        }
        return getAllCategories(language);
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.GetHhCategoryStrategy;
    }

    public Response<DhHHCategory> getAllCategories(String language) {
        Query query = new Query(Criteria.where(AppConstants.STATUS).is(AppConstants.STATUS_ACTIVE));
        List<DhHHCategory> categories = mongoTemplate.find(query, DhHHCategory.class);

        if (categories.isEmpty()) {
            return new Response<DhHHCategory>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NO_RECORDS_FOUND), new ArrayList<>(), 0);
        }
        return new Response<DhHHCategory>(true, 200, AppConstants.QUERY_SUCCESSFUL, categories, categories.size());
    }
}
