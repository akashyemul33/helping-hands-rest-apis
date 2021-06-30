package com.ayprojects.helpinghands.api.classes.update_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyUpdateBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.models.UserSettings;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.CalendarOperations;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class StrategyUpdateUserApi implements StrategyUpdateBehaviour<DhUser> {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<DhUser> update(String language, DhUser dhUser, HashMap<String, Object> params) throws ServerSideException {
        if (params == null) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS), new ArrayList<>());
        }

        String userApiType = (String) params.get(AppConstants.KEY_USER_API_TYPE);
        if (AppConstants.VALUE_UPDATE_SETTINGS.equalsIgnoreCase(userApiType)) {
            Response<DhUser> validatedResponse = validateForUserSettings(language, dhUser);
            if (validatedResponse.getStatus()) {
                updateUserSettings(dhUser.getUserId(),dhUser.getUserSettings());
            }
            return validatedResponse;
        }
        else if(AppConstants.VALUE_RESET_SETTINGS.equalsIgnoreCase(userApiType)){
            Response<DhUser> validatedResponse = validateForUserSettings(language, dhUser);
            if (validatedResponse.getStatus()) {
                UserSettings userSettings = Utility.getGlobalUserSettings();
                updateUserSettings(dhUser.getUserId(),userSettings);
            }
            return validatedResponse;
        }
        return null;
    }

    private void updateUserSettings(String userId,UserSettings userSettings) {
        Query query = new Query(Criteria.where(AppConstants.KEY_USER_ID).is(userId));
        Update update = new Update();
        update.set(AppConstants.KEY_USER_SETTINGS, userSettings);
        update.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
        mongoTemplate.updateFirst(query, update, DhUser.class);
    }

    private Response<DhUser> validateForUserSettings(String language, DhUser dhUser) {
        if (dhUser == null || dhUser.getUserSettings() == null)
            return new Response<DhUser>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());

        if (Utility.isFieldEmpty(dhUser.getUserId()))
            return new Response<DhUser>(false, 402, "User Id is missing !", new ArrayList<>());

        return new Response<DhUser>(true, 201, "Updated your settings .", new ArrayList<>());
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.UpdateUserStrategy;
    }
}
