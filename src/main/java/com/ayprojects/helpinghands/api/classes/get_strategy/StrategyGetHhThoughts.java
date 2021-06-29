package com.ayprojects.helpinghands.api.classes.get_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyGetBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhAppConfig;
import com.ayprojects.helpinghands.models.DhLog;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.models.Thoughts;
import com.ayprojects.helpinghands.models.ThoughtsConfig;
import com.ayprojects.helpinghands.models.ThoughtsFrequency;
import com.ayprojects.helpinghands.models.ThoughtsStatus;
import com.ayprojects.helpinghands.services.log.LogService;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.CalendarOperations;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Component
public class StrategyGetHhThoughts implements StrategyGetBehaviour<Thoughts> {

    @Autowired
    LogService logService;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<Thoughts> get(String language, HashMap<String, Object> params) throws ServerSideException {
        if (params == null) {
            return new Response<Thoughts>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS), new ArrayList<>());
        }

        String userId = (String) params.get(AppConstants.KEY_USER_ID);
        String apiType = (String) params.get(AppConstants.API_TYPE);
        if (AppConstants.API_ADDTHOUGHT_ELIGIBILITY.equals(apiType)) {
            return checkAddThoughtEligibility(language, userId);
        } else {
            return getUserSpecificThoughts(language, userId);
        }
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
        if (dhUser == null) {
            LOGGER.info("returning as dhUser is null !");
            return new Response<Thoughts>(false, 402, "User not found", new ArrayList<>(), 0);
        }

        //get dh app config
        Query queryToFetchAppConfig = new Query(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        queryToFetchAppConfig.fields().include(AppConstants.THOUGHTS_CONFIG);
        DhAppConfig dhAppConfig = mongoTemplate.findOne(queryToFetchAppConfig, DhAppConfig.class);
        if (dhAppConfig == null || dhAppConfig.getThoughtsConfig() == null)
            return new Response<Thoughts>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_SOMETHING_WENT_WRONG), new ArrayList<>());

        //get todays added thoughts
        String currentDate = CalendarOperations.currentDateInUTC();
        LOGGER.info("checkAddThoughtEligibility:currentDate:" + currentDate);
        Query queryToFindTodaysThoughts = new Query(Criteria.where(AppConstants.CREATED_DATETIME).regex(currentDate, "i"));
        queryToFetchAppConfig.fields().include(AppConstants.CREATED_DATETIME);
        List<Thoughts> todaysThoughtsList = mongoTemplate.find(queryToFindTodaysThoughts, Thoughts.class, AppConstants.COLLECTION_DH_USER_THOUGHTS);

        //return response with cannot add if daily thought limit reached
        Thoughts thoughts = new Thoughts();
        thoughts.setCanAddThoughts(false);
        ThoughtsConfig thoughtsConfig = dhAppConfig.getThoughtsConfig();
        long thoughtsLeft = thoughtsConfig.getMaxDailyLimit() - todaysThoughtsList.size();
        if (todaysThoughtsList.size() >= thoughtsConfig.getMaxDailyLimit())
            return new Response<Thoughts>(true, 200, "", ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_THOUGHTS_DAILY_LIMIT_REACHED), Collections.singletonList(thoughts), thoughtsLeft);

        //get user details to verify that user helped or posted atleast one person
        boolean atleastOneHelpOrPostNeeded = true;
        int daysLimit = 0;
        if (thoughtsConfig.getEligibilityFrequency() == ThoughtsFrequency.NONE) {
            atleastOneHelpOrPostNeeded = false;
        } else if (thoughtsConfig.getEligibilityFrequency() == ThoughtsFrequency.DAILY) {
            daysLimit = 1;
        } else if (thoughtsConfig.getEligibilityFrequency() == ThoughtsFrequency.WEEKLY) {
            daysLimit = 7;
        } else if (thoughtsConfig.getEligibilityFrequency() == ThoughtsFrequency.MONTHLY) {
            daysLimit = 30;
        } else if (thoughtsConfig.getEligibilityFrequency() == ThoughtsFrequency.YEARLY) {
            daysLimit = 365;
        }

        String responseWhenNotEligible = String.format(Locale.US, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_THOUGHTS_NOT_POSTED_OR_HELPED), thoughtsConfig.getEligibilityFrequency().name(), dhUser.getLastHhPostAddedDateTime(), dhUser.getLastHhPostHelpedDateTime());
        String responseHeaderRegret = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_THOUGHT_CANNOT_ADD_HEADING);
        if (atleastOneHelpOrPostNeeded && dhUser.getNumberOfHHHelps() == 0 && dhUser.getNumberOfHHPosts() == 0) {
            return new Response<Thoughts>(true, 200, responseHeaderRegret, responseWhenNotEligible, Collections.singletonList(thoughts), thoughtsLeft);
        }

        int diffBetweenPostAddedDays = Utility.isFieldEmpty(dhUser.getLastHhPostAddedDateTime()) ? -1 : CalendarOperations.findDiffBetweenTwoDates(dhUser.getLastHhPostAddedDateTime(), currentDate);
        int diffBetweenHelpedDays = Utility.isFieldEmpty(dhUser.getLastHhPostHelpedDateTime()) ? -1 : CalendarOperations.findDiffBetweenTwoDates(dhUser.getLastHhPostHelpedDateTime(), currentDate);
        LOGGER.info("checkAddThoughtEligibility:diffBetweenPostAddedDays:" + diffBetweenPostAddedDays + "   diffBetweenHelpedDays:" + diffBetweenHelpedDays);
        LOGGER.info("checkAddThoughtEligibility:daysLimit:" + daysLimit);
        boolean isPostAddedDateNone = diffBetweenPostAddedDays == -1;
        boolean isPostHelpedDateNone = diffBetweenHelpedDays == -1;
        boolean isPostAddedDateGreaterThanLimit = diffBetweenPostAddedDays > daysLimit;
        boolean isPostHelpedDateGreaterThanLimit = diffBetweenHelpedDays > daysLimit;
        if ((isPostAddedDateNone && isPostHelpedDateNone) || (isPostAddedDateGreaterThanLimit && isPostHelpedDateGreaterThanLimit) || (isPostAddedDateGreaterThanLimit && isPostHelpedDateNone) || (isPostHelpedDateGreaterThanLimit && isPostAddedDateNone))
            return new Response<Thoughts>(true, 200, responseHeaderRegret, responseWhenNotEligible, Collections.singletonList(thoughts), thoughtsLeft);

        thoughts.setCanAddThoughts(true);
        return new Response<Thoughts>(true, 200, "Ok", "Ok, you can add thought .", Collections.singletonList(thoughts), thoughtsLeft);
    }

    private Response<Thoughts> getUserSpecificThoughts(String language, String userId) {
        //picking up yesterday date UTC, and current hour of the day Locale.Default
        Calendar calendar = Calendar.getInstance();
        int hourOfTheDayLocal = calendar.get(Calendar.HOUR_OF_DAY) + 1;
        LOGGER.info("getAllThoughts->hourOfTheDay:" + hourOfTheDayLocal);
        calendar.setTimeZone(TimeZone.getTimeZone(AppConstants.UTC));
        calendar.add(Calendar.DATE, -1);
        LOGGER.info("getAllThoughts->hourOfTheDay UTC:" + calendar.get(Calendar.HOUR_OF_DAY));
        DateFormat dateFormat = new SimpleDateFormat(AppConstants.DATE_FORMAT);
        String yesterdayDate = dateFormat.format(calendar.getTime());
        LOGGER.info("getAllThoughts->yesterdayDate:" + yesterdayDate);

        //get user details
        Query queryGetUserDetails = new Query();
        queryGetUserDetails.addCriteria(Criteria.where(AppConstants.KEY_USER_ID).is(userId));
        queryGetUserDetails.fields().include(AppConstants.KEY_NUMBER_OF_HH_POSTS);
        queryGetUserDetails.fields().include(AppConstants.KEY_NUMBER_OF_HH_HELPS);
        queryGetUserDetails.fields().include(AppConstants.KEY_TWENTY_FOUR_THOUGHTS);
        queryGetUserDetails.fields().include(AppConstants.KEY_USER_ID);
        DhUser dhUser = mongoTemplate.findOne(queryGetUserDetails, DhUser.class);
        if (dhUser == null) {
            return new Response<Thoughts>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_USER_NOT_FOUND_WITH_USERID, language), new ArrayList<>(), 0);
        }

        //get thoughts pool
        Query queryToGetUserThoughts = new Query();
        queryToGetUserThoughts.addCriteria(Criteria.where(AppConstants.CREATED_DATETIME).regex(yesterdayDate, "i"));
        queryToGetUserThoughts.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.THOUGHTS_STATUS_VALIDATED_ATTEMPTED_LIVE, "i"));
        List<Thoughts> allThoughtsList = mongoTemplate.find(queryToGetUserThoughts, Thoughts.class, AppConstants.COLLECTION_DH_USER_THOUGHTS);
        if (allThoughtsList.size() < 24) {
            Query queryToGetSystemThoughts = new Query();
            queryToGetSystemThoughts.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.THOUGHTS_STATUS_VALIDATED_ATTEMPTED_LIVE, "i"));
            int neededThoughtsFromSystem = 24 - allThoughtsList.size();
            queryToGetSystemThoughts.limit(neededThoughtsFromSystem);
            List<Thoughts> systemThoughts = mongoTemplate.find(queryToGetSystemThoughts, Thoughts.class, AppConstants.COLLECTION_DH_SYSTEM_THOUGHTS);

            if (systemThoughts.size() < neededThoughtsFromSystem) {
                logService.addLog(new DhLog(userId, "Please have attention, User Thoughts are less than 24 and also system thoughts are less than " + neededThoughtsFromSystem));
                return new Response<Thoughts>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_SOMETHING_WENT_WRONG), new ArrayList<>());
            } else
                logService.addLog(new DhLog(userId, "Please have attention, User Thoughts are less than 24, So taken " + neededThoughtsFromSystem + " thoughts from system ."));
            allThoughtsList.addAll(systemThoughts);
        }

        //prepare thoughts needed to return according to stored user thoughts and current hour of the day
        List<Thoughts> returningThoughts = new ArrayList<>(hourOfTheDayLocal);
        Random random = new Random();
        List<String> updatedThoughtsIdsList = new ArrayList<>(hourOfTheDayLocal);
        List<Thoughts> newlyPickedThoughtIdsList = new ArrayList<>();
        //if user has no thoughts stored in his table, then fetch all the thoughts up to current hour of the day
        if (dhUser.getTwentyFourThougths() == null || dhUser.getTwentyFourThougths().size() == 0) {
            LOGGER.info("if ");
            int tempInt = hourOfTheDayLocal;
            while (tempInt > 0) {
                Thoughts t = allThoughtsList.get(random.nextInt(allThoughtsList.size()));
                t.setAlreadyLiked(t.getLikedUserIds() != null && t.getLikedUserIds().contains(userId));
                returningThoughts.add(t);
                updatedThoughtsIdsList.add(t.getThoughtId());
                tempInt--;
            }
            newlyPickedThoughtIdsList.addAll(returningThoughts);
        } else if (dhUser.getTwentyFourThougths().size() < hourOfTheDayLocal) {
            LOGGER.info("else if dhUser.getTwentyFourThougths().size() < hourOfTheDayLocal");
            LOGGER.info("All thoughts list .size:" + allThoughtsList.size());
            for (int i = 0; i < allThoughtsList.size(); i++) {
                Thoughts t = allThoughtsList.get(i);
                for (String thoughtId : dhUser.getTwentyFourThougths()) {
                    if (thoughtId.equals(t.getThoughtId())) {
                        t.setAlreadyLiked(t.getLikedUserIds() != null && t.getLikedUserIds().contains(userId));
                        returningThoughts.add(t);
                        updatedThoughtsIdsList.add(t.getThoughtId());
                        allThoughtsList.remove(i);
                        LOGGER.info("Removing item at " + i + " from all thoughts list.");
                        break;
                    }

                }
            }
            LOGGER.info("All thoughts list.size after removal:" + allThoughtsList.size());


            int neededThoughtsUpToHour = hourOfTheDayLocal - dhUser.getTwentyFourThougths().size();
            LOGGER.info("NeededThoughtsUpToHour:" + neededThoughtsUpToHour);
            while (neededThoughtsUpToHour > 0) {
                Thoughts t = allThoughtsList.get(random.nextInt(allThoughtsList.size()));
                t.setAlreadyLiked(t.getLikedUserIds() != null && t.getLikedUserIds().contains(userId));
                returningThoughts.add(t);
                updatedThoughtsIdsList.add(t.getThoughtId());
                newlyPickedThoughtIdsList.add(t);
                neededThoughtsUpToHour--;
            }
        } else if (dhUser.getTwentyFourThougths().size() > hourOfTheDayLocal) {
            LOGGER.info("start of 24thoughts>hourOfTheDay, else if dhUser.getTwentyFourThougths().size()>hourOfTheDayLocal");
            LOGGER.info("24thoughts>hourOfTheDay, All thoughts list .size:" + allThoughtsList.size());
            for (int i = 0; i < allThoughtsList.size(); i++) {
                Thoughts t = allThoughtsList.get(i);
                for (String thoughtId : dhUser.getTwentyFourThougths()) {
                    if (thoughtId.equals(t.getThoughtId()) && dhUser.getTwentyFourThougths().indexOf(thoughtId) > hourOfTheDayLocal) {
                        t.setYesterdaysThought(true);
                        t.setAlreadyLiked(t.getLikedUserIds() != null && t.getLikedUserIds().contains(userId));
                        returningThoughts.add(t);
                        updatedThoughtsIdsList.add(t.getThoughtId());
                        allThoughtsList.remove(i);
                        LOGGER.info("Removing item at " + i + " from all thoughts list.");
                        break;
                    }

                }
            }
            LOGGER.info("24thoughts>hourOfTheDay, All thoughts list.size after removal:" + allThoughtsList.size());
            LOGGER.info("24thoughts>hourOfTheDay, REturning list.size:" + returningThoughts.size());

            int neededThoughtsUpToHour = dhUser.getTwentyFourThougths().size() - hourOfTheDayLocal;
            LOGGER.info("NeededThoughtsUpToHour:" + neededThoughtsUpToHour);
            while (neededThoughtsUpToHour > 0) {
                Thoughts t = allThoughtsList.get(random.nextInt(allThoughtsList.size()));
                t.setAlreadyLiked(t.getLikedUserIds() != null && t.getLikedUserIds().contains(userId));
                returningThoughts.add(t);
                updatedThoughtsIdsList.add(t.getThoughtId());
                newlyPickedThoughtIdsList.add(t);
                neededThoughtsUpToHour--;
            }
            LOGGER.info("end of 24thoughts>hourOfTheDay, else if dhUser.getTwentyFourThougths().size()>hourOfTheDayLocal");
        } else {
            LOGGER.info("else");

            for (Thoughts thoughts : allThoughtsList) {
                for (String thoughtId : dhUser.getTwentyFourThougths()) {
                    if (thoughtId.equals(thoughts.getThoughtId())) {
                        thoughts.setAlreadyLiked(thoughts.getLikedUserIds() != null && thoughts.getLikedUserIds().contains(userId));
                        returningThoughts.add(thoughts);
                    }

                }
            }
        }

        if (updatedThoughtsIdsList.size() > 0) {
            Update updateThoughtIdsOfUser = new Update();
            updateThoughtIdsOfUser.set(AppConstants.KEY_TWENTY_FOUR_THOUGHTS, updatedThoughtsIdsList);
            mongoTemplate.updateFirst(queryGetUserDetails, updateThoughtIdsOfUser, DhUser.class);
            LOGGER.info("Updating thought ids of user.");
        }
        if (newlyPickedThoughtIdsList.size() > 0) {

            Update updatePosts = new Update();
            updatePosts.set(AppConstants.STATUS, ThoughtsStatus.ATTEMPTED);
            updatePosts.push(AppConstants.KEY_LIVE_DATE_ON, CalendarOperations.currentDateInUTC());
            updatePosts.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
            for (Thoughts t : newlyPickedThoughtIdsList) {
                Query query = new Query();
                query.addCriteria(Criteria.where(AppConstants.KEY_THOUGHT_ID).is(t.getThoughtId()));
                updatePosts.set(AppConstants.KEY_NUMBER_OF_ATTEMPTS, t.getNumberOfAttempts() + 1);
                mongoTemplate.updateFirst(query, updatePosts, Thoughts.class, AppConstants.COLLECTION_DH_USER_THOUGHTS);
                mongoTemplate.updateFirst(query, updatePosts, Thoughts.class, AppConstants.COLLECTION_DH_SYSTEM_THOUGHTS);
            }
        }

        return new Response<Thoughts>(true, 200, AppConstants.QUERY_SUCCESSFUL, returningThoughts, returningThoughts.size());
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.GetHhThoughtStrategy;
    }
}
