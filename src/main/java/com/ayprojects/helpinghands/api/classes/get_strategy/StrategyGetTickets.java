package com.ayprojects.helpinghands.api.classes.get_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyGetBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.models.DhTicket;
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
import java.util.Set;

@Component
public class StrategyGetTickets implements StrategyGetBehaviour<DhTicket> {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<DhTicket> get(String language, HashMap<String, Object> params) {
        if (params == null) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS), new ArrayList<>());
        }
        Set<String> keySet = params.keySet();
        if (keySet.contains(AppConstants.KEY_USER_ID)) {
            String raisedBy = (String) params.get(AppConstants.KEY_USER_ID);
            return getAllTickets(language, raisedBy);
        }
        return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS) + ": " + AppConstants.ISSUE_RAISED_BY, new ArrayList<>());
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.GetTicketStrategy;
    }

    public Response<DhTicket> getAllTickets(String language, String userId) {
        Query query = new Query(Criteria.where(AppConstants.ISSUE_RAISED_BY).is(userId));
        List<DhTicket> dhTicketList = mongoTemplate.find(query, DhTicket.class);

        if (dhTicketList.isEmpty()) {
            return new Response<DhTicket>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NO_TICKETS_FOUND), new ArrayList<>(), 0);
        }
        return new Response<DhTicket>(true, 200, AppConstants.QUERY_SUCCESSFUL, dhTicketList, dhTicketList.size());
    }
}
