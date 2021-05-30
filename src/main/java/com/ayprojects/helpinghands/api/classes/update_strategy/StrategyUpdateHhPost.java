package com.ayprojects.helpinghands.api.classes.update_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyUpdateBehaviour;
import com.ayprojects.helpinghands.api.enums.ContentType;
import com.ayprojects.helpinghands.api.enums.HhPostUpdateEnums;
import com.ayprojects.helpinghands.api.enums.RedirectionContent;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhHHPost;
import com.ayprojects.helpinghands.models.DhHhHelpedUsers;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.common_service.CommonService;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Component
public class StrategyUpdateHhPost implements StrategyUpdateBehaviour<DhHHPost> {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    CommonService commonService;

    @Override
    public Response<DhHHPost> update(String language, DhHHPost obj, HashMap<String, Object> params) throws ServerSideException {
        if (params == null) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS), new ArrayList<>());
        }
        Set<String> keySet = params.keySet();
        if (keySet.contains(AppConstants.KEY_HH_POST_STEP_ENUM)) {
            HhPostUpdateEnums hhPostUpdateEnums = (HhPostUpdateEnums) params.get(AppConstants.KEY_HH_POST_STEP_ENUM);
            switch (hhPostUpdateEnums) {
                case ADD_LIKE:
                    break;
                case DELETE_POST:
                    break;
                case MARK_HELPED:
                    return markPostAsHelped(language, obj, (String) params.get(AppConstants.KEY_HELPED_USER_ID), (String) params.get(AppConstants.KEY_HELPED_USER_NAME));
            }
        }
        return null;
    }

    private Response<DhHHPost> markPostAsHelped(String language, DhHHPost dhHHPost, String helpedUserId, String helpedUsername) {
        Response<DhHHPost> validationResponse = validateHhPost(language, dhHHPost, true, helpedUserId);
        if (!validationResponse.getStatus())
            return validationResponse;

        //validating user against user ids intentionally, later in this code used this quried objects to update dhUser
        Query findPostAddedUserQuery = new Query(Criteria.where(AppConstants.KEY_USER_ID).is(dhHHPost.getUserId()).andOperator(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i")));
        findPostAddedUserQuery.fields().include(AppConstants.KEY_GENUINE_PERCENTAGE);
        findPostAddedUserQuery.fields().include(AppConstants.KEY_NUMBER_OF_HH_POSTS);
        DhUser queriedPostAddedDhUser = mongoTemplate.findOne(findPostAddedUserQuery, DhUser.class);
        if (queriedPostAddedDhUser == null)
            return new Response<DhHHPost>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_USER_NOT_FOUND_WITH_USERID), new ArrayList<>(), 0);

        Query findHelpedUserQuery = new Query(Criteria.where(AppConstants.KEY_USER_ID).is(helpedUserId).andOperator(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i")));
        findHelpedUserQuery.fields().include(AppConstants.KEY_NUMBER_OF_HH_HELPS);
        DhUser queriedHelpedDhUser = mongoTemplate.findOne(findHelpedUserQuery, DhUser.class);
        if (queriedHelpedDhUser == null)
            return new Response<DhHHPost>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_USER_NOT_FOUND_WITH_HELPED_USERID), new ArrayList<>(), 0);

        //insert into helped user item & update dhHhPost
        DhHhHelpedUsers dhHhHelpedUsers = dhHHPost.getHelpedUsers().get(0);
        dhHhHelpedUsers = (DhHhHelpedUsers) Utility.setCommonAttrs(dhHhHelpedUsers, AppConstants.STATUS_ACTIVE);
        mongoTemplate.save(dhHhHelpedUsers, AppConstants.COLLECTION_DH_HH_HELPED_USERS);

        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where(AppConstants.KEY_HH_POST_ID).is(dhHHPost.getPostId()));
        criteria.andOperator(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        query.addCriteria(criteria);
        Update update = new Update();
        if (dhHHPost.getHelpedUserIds() == null) {
            List<String> helpedUserIds = new ArrayList<>(1);
            helpedUserIds.add(helpedUserId);
            update.set(AppConstants.KEY_HELPED_USER_ID, helpedUserIds);
        } else {
            update.push(AppConstants.KEY_HELPED_USER_ID, helpedUserId);
        }

        if (dhHHPost.getGenuineRatingUserIds() == null) {
            List<String> genuineUserIds = new ArrayList<>(1);
            genuineUserIds.add(helpedUserId);
            dhHHPost.setGenuineRatingUserIds(genuineUserIds);
            update.set(AppConstants.KEY_GENUINE_RATING_USER_IDS, genuineUserIds);
        } else {
            update.push(AppConstants.KEY_GENUINE_RATING_USER_IDS, helpedUserId);
            dhHHPost.getGenuineRatingUserIds().add(helpedUserId);
        }
        String modifiedDateTime = CalendarOperations.currentDateTimeInUTC();
        dhHHPost.setModifiedDateTime(modifiedDateTime);
        update.set(AppConstants.MODIFIED_DATE_TIME, modifiedDateTime);
        String title = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NTFN_TITLE_HH_HELPED);
        String body;
        if (Utility.isFieldEmpty(helpedUsername))
            body = String.format(ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NTFN_BODY_HH_HELPED), helpedUsername);
        else
            body = String.format(ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NTFN_BODY_HH_HELPED_NONAME), helpedUsername);
        Utility.sendNotification(ContentType.CONTENT_HH_POST_HELP, dhHHPost.getUserId(), mongoTemplate, title, body, RedirectionContent.REDCONTENT_HH_HELPED, RedirectionContent.REDURL_HH_HELPED);
        mongoTemplate.updateFirst(query, update, DhHHPost.class);

        //Update helped user details
        Update updateHelpedUser = new Update();
        updateHelpedUser.set(AppConstants.KEY_NUMBER_OF_HH_HELPS, queriedHelpedDhUser.getNumberOfHHHelps() + 1);
        updateHelpedUser.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
        mongoTemplate.updateFirst(findHelpedUserQuery, updateHelpedUser, DhUser.class);


        //Update post added user
        Update updatePostAddedUser = new Update();
        long previousGenuineRatingCount = dhHHPost.getGenuineRatingUserIds().size() - 1;
        long previousNotGenuineRatingCount = dhHHPost.getNotGenuineRatingUserIds() == null ? 0 : dhHHPost.getNotGenuineRatingUserIds().size();
        long genuineRatingCount = dhHHPost.getGenuineRatingUserIds().size();
        float avgGenuinePercentage = queriedPostAddedDhUser.getHhGenuinePercentage();
        long totalAddedPost = queriedPostAddedDhUser.getNumberOfHHPosts();
        float finalAvgGenPerc = calculatePerPostGenuinePercentage(previousGenuineRatingCount, previousNotGenuineRatingCount, genuineRatingCount, previousNotGenuineRatingCount, avgGenuinePercentage, totalAddedPost);
        updatePostAddedUser.set(AppConstants.KEY_GENUINE_PERCENTAGE, finalAvgGenPerc);
        updatePostAddedUser.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
        dhHHPost.setHhGenuinePercentage(finalAvgGenPerc);
        mongoTemplate.updateFirst(findPostAddedUserQuery, updatePostAddedUser, DhUser.class);

        return new Response<DhHHPost>(true, 201, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_HH_MARK_POST_HELPED_MSG), ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_HH_MARK_POST_HELPED_BODY), Collections.singletonList(dhHHPost), 0);
    }

    //working and tested,
    private float calculatePerPostGenuinePercentage(long previousGenuineRatingCount, long previousNotGenuineRatingCount, long genuineRatingCount, long nonGenuineRatingCount, float avgGenuinePercentage, long totalAddedPost) {
        long totalPreviousRatingCount = previousGenuineRatingCount + previousNotGenuineRatingCount;
        float previousGenPerc = (previousGenuineRatingCount / totalPreviousRatingCount) * 100;

        float previousAvgGenCount = (((totalPreviousRatingCount > 0 ? totalAddedPost : totalAddedPost - 1) * avgGenuinePercentage) - previousGenPerc);

        float currentGenPerc = (genuineRatingCount / (genuineRatingCount + nonGenuineRatingCount)) * 100;

        return (previousAvgGenCount + currentGenPerc) / totalAddedPost;
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.UpdateHhPostStrategy;
    }

    public Response<DhHHPost> validateHhPost(String language, DhHHPost dhHHPost, boolean forHelped, String helpedUserId) {
        if (dhHHPost == null)
            return new Response<DhHHPost>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>(), 0);

        List<String> missingFieldsList = new ArrayList<>();
        if (Utility.isFieldEmpty(dhHHPost.getUserId()))
            missingFieldsList.add(AppConstants.KEY_USER_ID);
        if (forHelped && Utility.isFieldEmpty(helpedUserId))
            missingFieldsList.add(AppConstants.KEY_HELPED_USER_ID);
        if (Utility.isFieldEmpty(dhHHPost.getPostId()))
            missingFieldsList.add(AppConstants.KEY_HH_POST_ID);
        if (dhHHPost.getHelpedUsers() == null || dhHHPost.getHelpedUsers().size() == 0)
            missingFieldsList.add(AppConstants.KEY_HELPED_USERS);
        else if (Utility.isFieldEmpty(dhHHPost.getHelpedUsers().get(0).getPostId()))
            missingFieldsList.add("Post id in helped user item");

        if (missingFieldsList.size() > 0) {
            String resMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY);
            resMsg = resMsg + " , these fields are missing : " + missingFieldsList;
            LOGGER.info("missingfields=>" + resMsg);
            return new Response<DhHHPost>(false, 402, resMsg, new ArrayList<>(), 0);
        }

        if (dhHHPost.getHelpedUserIds() != null) {
            for (String hUserId : dhHHPost.getHelpedUserIds()) {
                if (helpedUserId.equals(hUserId)) {
                    return new Response<DhHHPost>(false, 402, "You've already marked this post as helped !", new ArrayList<>(), 0);
                }
            }
        }

        return new Response<DhHHPost>(true, 201, "validated", new ArrayList<>(), 0);
    }
}
