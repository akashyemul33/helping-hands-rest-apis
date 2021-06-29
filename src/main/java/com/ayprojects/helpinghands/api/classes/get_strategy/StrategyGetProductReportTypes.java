package com.ayprojects.helpinghands.api.classes.get_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyGetBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhProductReportType;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class StrategyGetProductReportTypes implements StrategyGetBehaviour<DhProductReportType> {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<DhProductReportType> get(String language, HashMap<String, Object> params) throws ServerSideException {
        return getProductReportTypes(language);
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.GetProductReportTypeStrategy;
    }

    private Response<DhProductReportType> getProductReportTypes(String language) {
        Query query = new Query(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        List<DhProductReportType> list = mongoTemplate.find(query, DhProductReportType.class);
        return new Response<DhProductReportType>(true, 200, "Query successful", list);
    }
}
