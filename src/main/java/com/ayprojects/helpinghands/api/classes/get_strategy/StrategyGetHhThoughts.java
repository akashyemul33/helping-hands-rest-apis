package com.ayprojects.helpinghands.api.classes.get_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyGetBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhThought;
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
import java.util.Locale;
import java.util.Set;

@Component
public class StrategyGetHhThoughts implements StrategyGetBehaviour<DhThought> {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<DhThought> get(String language, HashMap<String, Object> params) throws ServerSideException {
        if (params == null) {
            return new Response<DhThought>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS), new ArrayList<>());
        }
        Set<String> keySet = params.keySet();
        String userId = (String) params.get(AppConstants.KEY_USER_ID);
        return getAllThoughts(language, userId);
    }

    private Response<DhThought> getAllThoughts(String language, String userId) {
        Query queryGetSingleThought = new Query();
        queryGetSingleThought.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        List<DhThought> thoughtsList = mongoTemplate.find(queryGetSingleThought, DhThought.class);
        String scheduledNote = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_THOUGHT_SCHEDULED_NOTE);
        String note = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_THOUGHT_NOTE);

        for (int i = thoughtsList.size(); i < 24; i++) {
            thoughtsList.add(new DhThought("" + i, String.format(Locale.US,scheduledNote,i),note , true));
        }

        return new Response<DhThought>(true, 200, AppConstants.QUERY_SUCCESSFUL, thoughtsList, thoughtsList.size());
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.GetHhThoughtStrategy;
    }
}
