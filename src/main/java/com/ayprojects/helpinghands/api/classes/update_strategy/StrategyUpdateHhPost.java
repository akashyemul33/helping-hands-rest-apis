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
            String hhPostId = (String) params.get(AppConstants.KEY_HH_POST_ID);
            String otherUserId = (String) params.get(AppConstants.KEY_OTHER_USER_ID);
            String otherUserName = (String) params.get(AppConstants.KEY_HH_OTHER_USERNAME);
            switch (hhPostUpdateEnums) {
                case ADD_LIKE:
                    return markAsLiked(language, hhPostId, otherUserId);
                case DELETE_POST:
                    //TODO
                    break;
                case MARK_AS_GENUINE:
                    return markAsGenuineOrNotGenuine(language, hhPostId, true, otherUserId);
                case MARK_AS_NOTGENUINE:
                    return markAsGenuineOrNotGenuine(language, hhPostId, false, otherUserId);
                case MARK_HELPED:
                    return markPostAsHelped(language, obj);
            }
        }
        return null;
    }

    private Response<DhHHPost> markAsGenuineOrNotGenuine(String language, String hhPostId, boolean isGenuine, String genuineNonGenuineUserId) {
        if (Utility.isFieldEmpty(hhPostId) || Utility.isFieldEmpty(genuineNonGenuineUserId)) {
            return new Response<DhHHPost>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>(), 0);
        }

        if (!commonService.checkUserExistence(genuineNonGenuineUserId))
            return new Response<DhHHPost>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_USER_NOT_FOUND_WITH_USERID), new ArrayList<>(), 0);

        Query querFindPostById = new Query(Criteria.where(AppConstants.KEY_HH_POST_ID).is(hhPostId));
        querFindPostById.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        querFindPostById.fields().include(AppConstants.KEY_USER_ID);
        querFindPostById.fields().include(AppConstants.KEY_GENUINE_RATING_USER_IDS);
        querFindPostById.fields().include(AppConstants.KEY_NOTGENUINE_RATING_USER_IDS);
        DhHHPost queriedDhHhPost = mongoTemplate.findOne(querFindPostById, DhHHPost.class);

        //Update post added user
        if (queriedDhHhPost != null) {

            //validating user against user ids intentionally, later in this code used this quried objects to update dhUser
            Query findPostAddedUserQuery = new Query(Criteria.where(AppConstants.KEY_USER_ID).is(queriedDhHhPost.getUserId()).andOperator(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i")));
            findPostAddedUserQuery.fields().include(AppConstants.KEY_GENUINE_PERCENTAGE);
            findPostAddedUserQuery.fields().include(AppConstants.KEY_NUMBER_OF_HH_POSTS);
            DhUser queriedPostAddedDhUser = mongoTemplate.findOne(findPostAddedUserQuery, DhUser.class);
            if (queriedPostAddedDhUser == null)
                return new Response<DhHHPost>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_USER_NOT_FOUND_WITH_USERID), new ArrayList<>(), 0);


            //dont change code location, intentionally place this code here 
            long previousGenuineRatingCount = queriedDhHhPost.getGenuineRatingUserIds() == null ? 0 : queriedDhHhPost.getGenuineRatingUserIds().size();
            long previousNotGenuineRatingCount = queriedDhHhPost.getNotGenuineRatingUserIds() == null ? 0 : queriedDhHhPost.getNotGenuineRatingUserIds().size();

            //to avoid crashes
            if (queriedDhHhPost.getNotGenuineRatingUserIds() == null)
                queriedDhHhPost.setNotGenuineRatingUserIds(new ArrayList<>());

            if (queriedDhHhPost.getGenuineRatingUserIds() == null)
                queriedDhHhPost.setGenuineRatingUserIds(new ArrayList<>());

            //update hh post
            Update updateDhHhPost = new Update();
            //check for whether userId already not exists in not genuine list,
            //if exists remove it before pushing it in genuine user ids
            boolean needToPopFromNonGenuineList = false;
            for (String gn : queriedDhHhPost.getNotGenuineRatingUserIds()) {
                if (gn.equals(genuineNonGenuineUserId)) {
                    if (!isGenuine)
                        return new Response<DhHHPost>(false, 201, "Already marked as not genuine", new ArrayList<>(), 0);

                    queriedDhHhPost.getNotGenuineRatingUserIds().remove(genuineNonGenuineUserId);
                    needToPopFromNonGenuineList = true;
                    break;

                }
            }

            //check for whether userId already not exists in genuine list,
            //if exists remove it before pushing it in non genuine user ids
            boolean needToPopFromGenuineList = false;
            for (String gn : queriedDhHhPost.getGenuineRatingUserIds()) {
                if (gn.equals(genuineNonGenuineUserId)) {
                    if (isGenuine)
                        return new Response<DhHHPost>(false, 201, "Already marked as genuine", new ArrayList<>(), 0);

                    queriedDhHhPost.getGenuineRatingUserIds().remove(genuineNonGenuineUserId);
                    needToPopFromGenuineList = true;
                    break;
                }
            }


            if (isGenuine) {
                if (needToPopFromNonGenuineList)
                    updateDhHhPost.set(AppConstants.KEY_NOTGENUINE_RATING_USER_IDS, queriedDhHhPost.getNotGenuineRatingUserIds());

                updateDhHhPost.push(AppConstants.KEY_GENUINE_RATING_USER_IDS, genuineNonGenuineUserId);

            } else {
                if (needToPopFromGenuineList)
                    updateDhHhPost.set(AppConstants.KEY_GENUINE_RATING_USER_IDS, queriedDhHhPost.getGenuineRatingUserIds());
                updateDhHhPost.push(AppConstants.KEY_NOTGENUINE_RATING_USER_IDS, genuineNonGenuineUserId);
            }
            updateDhHhPost.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
            mongoTemplate.updateFirst(querFindPostById, updateDhHhPost, DhHHPost.class);

            //update user
            Update updatePostAddedUser = new Update();
            long genuineRatingCount = isGenuine ? queriedDhHhPost.getGenuineRatingUserIds().size() + 1 : queriedDhHhPost.getGenuineRatingUserIds().size();
            long nonGenuineRatingCount = !isGenuine ? queriedDhHhPost.getNotGenuineRatingUserIds().size() + 1 : queriedDhHhPost.getNotGenuineRatingUserIds().size();
            float avgGenuinePercentage = queriedPostAddedDhUser.getHhGenuinePercentage();
            long totalAddedPost = queriedPostAddedDhUser.getNumberOfHHPosts();
            float finalAvgGenPerc = calculatePerPostGenuinePercentage(previousGenuineRatingCount, previousNotGenuineRatingCount, genuineRatingCount, nonGenuineRatingCount, avgGenuinePercentage, totalAddedPost);
            updatePostAddedUser.set(AppConstants.KEY_GENUINE_PERCENTAGE, finalAvgGenPerc);
            updatePostAddedUser.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
            mongoTemplate.updateFirst(findPostAddedUserQuery, updatePostAddedUser, DhUser.class);

            return new Response<DhHHPost>(true, 201, isGenuine ? "Marked post as genuine" : "Marked post as non genuine", new ArrayList<>(), 0);
        }
        return new Response<DhHHPost>(true, 402, "Post not found with given postId!", new ArrayList<>(), 0);
    }

    private Response<DhHHPost> markAsLiked(String language, String hhPostId, String likedUserId) {
        if (Utility.isFieldEmpty(hhPostId) || Utility.isFieldEmpty(likedUserId)) {
            return new Response<DhHHPost>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>(), 0);
        }

        if (!commonService.checkUserExistence(likedUserId))
            return new Response<DhHHPost>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_USER_NOT_FOUND_WITH_USERID), new ArrayList<>(), 0);

        Query querFindPostById = new Query(Criteria.where(AppConstants.KEY_HH_POST_ID).is(hhPostId));
        querFindPostById.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        querFindPostById.fields().include(AppConstants.KEY_LIKED_USER_IDS);
        DhHHPost dhHHPost = mongoTemplate.findOne(querFindPostById, DhHHPost.class);
        if (dhHHPost != null) {
            if (dhHHPost.getLikedUserIds() != null) {
                for (String s : dhHHPost.getLikedUserIds()) {
                    if (s.equalsIgnoreCase(likedUserId)) {
                        return new Response<DhHHPost>(true, 201, "Already marked post as liked", new ArrayList<>(), 0);
                    }
                }
            }
            Update update = new Update();
            update.push(AppConstants.KEY_LIKED_USER_IDS, likedUserId);
            update.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
            Query query = new Query(Criteria.where(AppConstants.KEY_HH_POST_ID).is(hhPostId));
            query.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
            mongoTemplate.updateFirst(query, update, DhHHPost.class);

            return new Response<DhHHPost>(true, 201, "Marked post as liked", new ArrayList<>(), 0);
        }
        return new Response<DhHHPost>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_SOMETHING_WENT_WRONG), new ArrayList<>(), 0);
    }

    private Response<DhHHPost> markPostAsHelped(String language, DhHHPost dhHHPost) {
        Response<DhHHPost> validationResponse = validateHhPost(language, dhHHPost, true);
        if (!validationResponse.getStatus())
            return validationResponse;

        String helpedUserId = dhHHPost.getHelpedUserIds().get(0);
        String helpedUsername = dhHHPost.getHelpedUsers().get(0).getUserName();

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

        Query querFindPostById = new Query(Criteria.where(AppConstants.KEY_HH_POST_ID).is(dhHHPost.getPostId()));
        querFindPostById.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        querFindPostById.fields().include(AppConstants.KEY_GENUINE_RATING_USER_IDS);
        querFindPostById.fields().include(AppConstants.KEY_NOTGENUINE_RATING_USER_IDS);
        DhHHPost queriedDhHhPost = mongoTemplate.findOne(querFindPostById, DhHHPost.class);
        if (queriedDhHhPost == null)
            return new Response<DhHHPost>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_SOMETHING_WENT_WRONG), new ArrayList<>(), 0);

        //to avoid crashes
        if (queriedDhHhPost.getGenuineRatingUserIds() == null)
            queriedDhHhPost.setGenuineRatingUserIds(new ArrayList<>());
        if (queriedDhHhPost.getNotGenuineRatingUserIds() == null)
            queriedDhHhPost.setNotGenuineRatingUserIds(new ArrayList<>());
        Update updateHhPost = new Update();
        if (dhHHPost.getHelpedUserIds() == null) {
            List<String> helpedUserIds = new ArrayList<>(1);
            helpedUserIds.add(helpedUserId);
            updateHhPost.set(AppConstants.KEY_HELPED_USER_ID, helpedUserIds);
        } else {
            updateHhPost.push(AppConstants.KEY_HELPED_USER_ID, helpedUserId);
        }

        if (dhHHPost.getGenuineRatingUserIds() == null) {
            List<String> genuineUserIds = new ArrayList<>(1);
            genuineUserIds.add(helpedUserId);
            updateHhPost.set(AppConstants.KEY_GENUINE_RATING_USER_IDS, genuineUserIds);
        } else {
            updateHhPost.push(AppConstants.KEY_GENUINE_RATING_USER_IDS, helpedUserId);
        }
        String modifiedDateTime = CalendarOperations.currentDateTimeInUTC();
        updateHhPost.set(AppConstants.MODIFIED_DATE_TIME, modifiedDateTime);
        String title = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NTFN_TITLE_HH_HELPED);
        String body;
        if (Utility.isFieldEmpty(helpedUsername))
            body = String.format(ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NTFN_BODY_HH_HELPED), helpedUsername);
        else
            body = String.format(ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NTFN_BODY_HH_HELPED_NONAME), helpedUsername);
        Utility.sendNotification(ContentType.CONTENT_HH_POST_HELP, dhHHPost.getUserId(), mongoTemplate, title, body, RedirectionContent.REDCONTENT_HH_HELPED, RedirectionContent.REDURL_HH_HELPED);
        mongoTemplate.updateFirst(querFindPostById, updateHhPost, DhHHPost.class);

        //Update helped user details
        Update updateHelpedUser = new Update();
        updateHelpedUser.set(AppConstants.KEY_NUMBER_OF_HH_HELPS, queriedHelpedDhUser.getNumberOfHHHelps() + 1);
        updateHelpedUser.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
        mongoTemplate.updateFirst(findHelpedUserQuery, updateHelpedUser, DhUser.class);

        //Update post added user
        Update updatePostAddedUser = new Update();
        long previousGenuineRatingCount = queriedDhHhPost.getGenuineRatingUserIds().size();
        long previousNotGenuineRatingCount = queriedDhHhPost.getNotGenuineRatingUserIds().size();
        long genuineRatingCount = previousGenuineRatingCount + 1;
        float avgGenuinePercentage = queriedPostAddedDhUser.getHhGenuinePercentage();
        long totalAddedPost = queriedPostAddedDhUser.getNumberOfHHPosts();
        float finalAvgGenPerc = calculatePerPostGenuinePercentage(previousGenuineRatingCount, previousNotGenuineRatingCount, genuineRatingCount, previousNotGenuineRatingCount, avgGenuinePercentage, totalAddedPost);
        updatePostAddedUser.set(AppConstants.KEY_GENUINE_PERCENTAGE, finalAvgGenPerc);
        updatePostAddedUser.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
        mongoTemplate.updateFirst(findPostAddedUserQuery, updatePostAddedUser, DhUser.class);

        return new Response<DhHHPost>(true, 201, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_HH_MARK_POST_HELPED_MSG), ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_HH_MARK_POST_HELPED_BODY), new ArrayList<>(), 0);
    }

    //working and tested,
    private float calculatePerPostGenuinePercentage(long previousGenuineRatingCount, long previousNotGenuineRatingCount, long genuineRatingCount, long nonGenuineRatingCount, float avgGenuinePercentage, long totalAddedPost) {
        LOGGER.info("calculatePerPostGenuinePercentage:previousGenuineRatingCount:" + previousGenuineRatingCount + " previousNotGenuineRatingCount:" + previousNotGenuineRatingCount + " genuineRatingCount:" + genuineRatingCount + " nonGenuineRatingCount:" + nonGenuineRatingCount + " avgGenuinePercentage:" + avgGenuinePercentage + " totalAddedPost:" + totalAddedPost);
        long totalPreviousRatingCount = previousGenuineRatingCount + previousNotGenuineRatingCount;
        long previousGenPerc = previousGenuineRatingCount == 0 || totalPreviousRatingCount == 0 ? 0 : (previousGenuineRatingCount * 100 / totalPreviousRatingCount);

        long tempAddedPost = totalPreviousRatingCount > 0 && (genuineRatingCount + nonGenuineRatingCount) > 1 ? totalAddedPost : totalAddedPost - 1;
        float previousAvgGenCount = ((tempAddedPost * avgGenuinePercentage) - previousGenPerc);

        long currentGenPerc = (genuineRatingCount * 100 / (genuineRatingCount + nonGenuineRatingCount));
        float finalPerc = (previousAvgGenCount + currentGenPerc) / totalAddedPost;
        LOGGER.info("calculatePerPostGenuinePercentage->finalPerc=" + finalPerc + " currentGenPerc:" + currentGenPerc + " previousAvgGenCount:" + previousAvgGenCount + " previousGenPerc:" + previousGenPerc + " totalPreviousRatingCount:" + totalPreviousRatingCount);
        return (float) Utility.roundTwoDecimals(finalPerc);
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.UpdateHhPostStrategy;
    }

    public Response<DhHHPost> validateHhPost(String language, DhHHPost dhHHPost, boolean forHelped) {
        if (dhHHPost == null)
            return new Response<DhHHPost>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>(), 0);

        List<String> missingFieldsList = new ArrayList<>();
        if (Utility.isFieldEmpty(dhHHPost.getUserId()))
            missingFieldsList.add(AppConstants.KEY_USER_ID);
        if (forHelped && Utility.isFieldEmpty(dhHHPost.getHelpedUserIds().get(0)))
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

        return new Response<DhHHPost>(true, 201, "validated", new ArrayList<>(), 0);
    }
}
