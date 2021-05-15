package com.ayprojects.helpinghands.api.classes.add_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.behaviours.StrategyAddBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhTicket;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class StrategyAddTicketsApi implements StrategyAddBehaviour<DhTicket> {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    Utility utility;

    @Override
    public Response<DhTicket> add(String language, DhTicket dhTicket) throws ServerSideException {
        Response<DhTicket> returnResponse = validateAddTicket(language, dhTicket);
        if (returnResponse.getStatus()) {
            dhTicket.setTicketId(Utility.getUUID());
            dhTicket = (DhTicket) ApiOperations.setCommonAttrs(dhTicket, AppConstants.STATUS_PENDING);
            dhTicket.setIsIssueResolved(false);
            mongoTemplate.save(dhTicket, AppConstants.COLLECTION_DH_TICKET);
            utility.addLog(dhTicket.getIssueRaisedBy(), "Raised ticket of type : " + dhTicket.getIssueType());
        }
        return returnResponse;
    }

    @Override
    public Response<DhTicket> add(String language, DhTicket obj, HashMap<String, Object> params) throws ServerSideException {
        return null;
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.AddTicketStrategy;
    }

    public Response<DhTicket> validateAddTicket(String language, DhTicket dhTicket) {
        if (dhTicket == null) {
            return new Response<DhTicket>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        } else if (Utility.isFieldEmpty(dhTicket.getIssueRaisedBy())) {
            return new Response<DhTicket>(false, 402, "User id is missing !", new ArrayList<>());
        } else if (Utility.isFieldEmpty(dhTicket.getIssueTitle()) || Utility.isFieldEmpty(dhTicket.getIssueDesc()) || Utility.isFieldEmpty(dhTicket.getIssueType())) {
            return new Response<DhTicket>(false, 402, "Issue title, issue description and issue type are compulsory !", new ArrayList<>());
        } else {
            Query query = new Query(Criteria.where(AppConstants.KEY_USER_ID).is(dhTicket.getIssueRaisedBy()));
            DhUser dhUser = mongoTemplate.findOne(query, DhUser.class);
            if (dhUser == null) {
                return new Response<DhTicket>(false, 402, "Invalid User ID !", new ArrayList<>());
            }
        }
        return new Response<>(true, 201, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_TICKET_RAISED), new ArrayList<>());
    }
}
