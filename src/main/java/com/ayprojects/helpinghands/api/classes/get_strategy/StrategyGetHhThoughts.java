package com.ayprojects.helpinghands.api.classes.get_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyGetBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhAppConfig;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.models.Thoughts;
import com.ayprojects.helpinghands.models.ThoughtsConfig;
import com.ayprojects.helpinghands.models.ThoughtsFrequency;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.CalendarOperations;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.apache.tomcat.jni.Local;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import sun.util.resources.LocaleData;

@Component
public class StrategyGetHhThoughts implements StrategyGetBehaviour<Thoughts> {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<Thoughts> get(String language, HashMap<String, Object> params) throws ServerSideException {
        if (params == null) {
            return new Response<Thoughts>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS), new ArrayList<>());
        }

        Set<String> keySet = params.keySet();
        String userId = (String) params.get(AppConstants.KEY_USER_ID);
        String apiType = (String) params.get(AppConstants.API_TYPE);
        if (AppConstants.API_ADDTHOUGHT_ELIGIBILITY.equals(apiType)) {
            return checkAddThoughtEligibility(language, userId);
        } else {
            String status = (String) params.get(AppConstants.STATUS);
            return getAllThoughts(language, userId, status);
        }
        return null;
    }

    private Response<Thoughts> checkAddThoughtEligibility(String language, String userId) {
        if (Utility.isFieldEmpty(userId))
            return new Response<Thoughts>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());

        //get user details
        Query queryToVerifyUserHelpedOrNot = new Query();
        queryToVerifyUserHelpedOrNot.addCriteria(Criteria.where(AppConstants.KEY_USER_ID).is(userId));
        queryToVerifyUserHelpedOrNot.fields().include(AppConstants.KEY_NUMBER_OF_HH_POSTS);
        queryToVerifyUserHelpedOrNot.fields().include(AppConstants.KEY_NUMBER_OF_HH_HELPS);
        queryToVerifyUserHelpedOrNot.fields().include(AppConstants.KEY_LAST_HH_POST_HELPED_DATETIME);
        queryToVerifyUserHelpedOrNot.fields().include(AppConstants.KEY_LAST_HH_POST_ADDED_DATETIME);
        DhUser dhUser = mongoTemplate.findOne(queryToVerifyUserHelpedOrNot, DhUser.class);
        if (dhUser == null)
            return new Response<Thoughts>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_USER_NOT_FOUND_WITH_USERID, language), new ArrayList<>(), 0);

        //get dh app config
        Query queryToFetchAppConfig = new Query(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        queryToFetchAppConfig.fields().include(AppConstants.THOUGHTS_CONFIG);
        DhAppConfig dhAppConfig = mongoTemplate.findOne(queryToFetchAppConfig, DhAppConfig.class);
        if (dhAppConfig == null || dhAppConfig.getThoughtsConfig() == null)
            return new Response<Thoughts>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_SOMETHING_WENT_WRONG), new ArrayList<>());

        //get todays added thoughts
        String currentDate = CalendarOperations.currentDateInUTC();
        Query queryToFindTodaysThoughts = new Query(Criteria.where(AppConstants.CREATED_DATETIME).regex(currentDate, "i"));
        queryToFetchAppConfig.fields().include(AppConstants.CREATED_DATETIME);
        List<Thoughts> todaysThoughtsList = mongoTemplate.find(queryToFindTodaysThoughts, Thoughts.class, AppConstants.COLLECTION_DH_USER_THOUGHTS);

        //return response with cannot add if daily thought limit reached
        Thoughts thoughts = new Thoughts();
        thoughts.setCanAddThoughts(false);
        ThoughtsConfig thoughtsConfig = dhAppConfig.getThoughtsConfig();
        if (todaysThoughtsList.size() >= thoughtsConfig.getMaxDailyLimit())
            return new Response<Thoughts>(true, 200, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_THOUGHTS_DAILY_LIMIT_REACHED), Collections.singletonList(thoughts));

        //get user details to verify that user helped or posted atleast one person
        boolean atleastOneHelpOrPostNeeded = true;
        int daysLimit = 0;
        if (thoughtsConfig.getEligibilityFrequency() == ThoughtsFrequency.NONE) {
            atleastOneHelpOrPostNeeded = false;
        }
        else if (thoughtsConfig.getEligibilityFrequency() == ThoughtsFrequency.DAILY) {
daysLimit = 1;
        }
        else if (thoughtsConfig.getEligibilityFrequency() == ThoughtsFrequency.WEEKLY) {
            daysLimit = 7;
        } else if (thoughtsConfig.getEligibilityFrequency() == ThoughtsFrequency.MONTHLY) {
            daysLimit = 30;
        } else if (thoughtsConfig.getEligibilityFrequency() == ThoughtsFrequency.YEARLY) {
            daysLimit = 365;
        }

        /*DateTimeFormatter.ofPattern(A)
        SimpleDateFormat sdfDateTime = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT);
        SimpleDateFormat sdfDateTime = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT);
        SimpleDateFormat sdfDate = new SimpleDateFormat(AppConstants.DATE_FORMAT);
        sdfDate.setTimeZone(TimeZone.getTimeZone(AppConstants.UTC));
        sdfDateTime.setTimeZone(TimeZone.getTimeZone(AppConstants.UTC));
        try {
            Date lastAddedDateTime = DateTimeFormatter.ofPattern().parse(dhUser.getLastHhPostAddedDateTime());
            Date lastAddedDateTime = sdfDateTime.parse(dhUser.getLastHhPostAddedDateTime());
            Date lastHelpedDateTime = sdfDateTime.parse(dhUser.getLastHhPostHelpedDateTime());
            Date date = sdfDateTime.parse(CalendarOperations.currentDateTimeInUTC());
            Duration.between(LocalDate.now(),LocalDate.parse());

        } catch (ParseException e) {
            e.printStackTrace();
            return timeIn24HourFormat;
        }*/

        String responseWhenNotEligible = String.format(Locale.US,ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_THOUGHTS_NOT_POSTED_OR_HELPED),thoughtsConfig.getEligibilityFrequency().name());
        if(atleastOneHelpOrPostNeeded && dhUser.getNumberOfHHHelps() == 0 && dhUser.getNumberOfHHPosts() == 0)
        {
            return new Response<Thoughts>(true, 200, responseWhenNotEligible, Collections.singletonList(thoughts));
        }

        if()
        thoughts.setCanAddThoughts(true);
        return new Response<Thoughts>(true, 200, responseWhenNotEligible, Collections.singletonList(thoughts));
    }

    private Response<Thoughts> getAllThoughts(String language, String userId, String status) {
        Query queryGetSingleThought = new Query();
        queryGetSingleThought.addCriteria(Criteria.where(AppConstants.STATUS).regex(status, "i"));
        List<Thoughts> thoughtsList = mongoTemplate.find(queryGetSingleThought, Thoughts.class);
        return new Response<Thoughts>(true, 200, AppConstants.QUERY_SUCCESSFUL, thoughtsList, thoughtsList.size());
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.GetHhThoughtStrategy;
    }
}
