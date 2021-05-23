package com.ayprojects.helpinghands.api.classes.get_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyGetBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhNotifications;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Component
public class StrategyGetNotifications implements StrategyGetBehaviour<DhNotifications> {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<DhNotifications> get(String language, HashMap<String, Object> params) throws ServerSideException {
        if (params == null) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS), new ArrayList<>());
        }
        Set<String> keySet = params.keySet();
        if (keySet.contains(AppConstants.KEY_USER_ID)) {
            String userId = (String) params.get(AppConstants.KEY_USER_ID);
            return getNotifications(language, userId, AppConstants.STATUS_ACTIVE);
        } else
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS), new ArrayList<>());
    }

    private Response<DhNotifications> getNotifications(String language, String userId, String status) {
        Criteria criteria = new Criteria();
        criteria.and(AppConstants.KEY_USER_ID).is(userId);
        criteria.and(AppConstants.STATUS).regex(status, "i");
        Query queryGetNotifications = new Query(criteria);
        queryGetNotifications.with(Sort.by(Sort.Direction.DESC, AppConstants.MODIFIED_DATE_TIME));
        List<DhNotifications> notifications = mongoTemplate.find(queryGetNotifications, DhNotifications.class);
        return new Response<DhNotifications>(true, 200, AppConstants.QUERY_SUCCESSFUL, notifications, notifications.size());
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.GetNotificationsStrategy;
    }
}
