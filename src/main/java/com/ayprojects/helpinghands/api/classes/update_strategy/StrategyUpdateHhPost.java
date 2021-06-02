package com.ayprojects.helpinghands.api.classes.update_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyUpdateBehaviour;
import com.ayprojects.helpinghands.api.enums.ContentType;
import com.ayprojects.helpinghands.api.enums.HhPostUpdateEnums;
import com.ayprojects.helpinghands.api.enums.RedirectionContent;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhComments;
import com.ayprojects.helpinghands.models.DhHHPost;
import com.ayprojects.helpinghands.models.DhHhHelpedUsers;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.models.Threads;
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
import java.util.Locale;
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
                    return markAsLiked(language, hhPostId, otherUserId, true);
                case DIS_LIKE:
                    return markAsLiked(language, hhPostId, otherUserId, false);
                case DELETE_POST:
                    //TODO
                    break;
                case MARK_AS_GENUINE:
                    return markAsGenuineOrNotGenuine(language, hhPostId, true, otherUserId);
                case MARK_AS_NOTGENUINE:
                    return markAsGenuineOrNotGenuine(language, hhPostId, false, otherUserId);
                case MARK_HELPED:
                    return markPostAsHelped(language, obj);
                case ADD_HH_POST_COMMENT:
                    return addCommentToPost(language, hhPostId, otherUserId, otherUserName, (String) params.get(AppConstants.COMMENT));
                case ADD_HH_POST_REPLY_TO_COMMENT:
                    return addReplyOnPostComment(language, hhPostId, (String) params.get(AppConstants.KEY_DH_COMMENT_ID), otherUserId, otherUserName, (String) params.get(AppConstants.KEY_REPLY_TO_COMMENT));
                case DELETE_HH_POST_COMMENT:
                    return removeCommentFromPost(language, hhPostId, otherUserId, (String) params.get(AppConstants.KEY_DH_COMMENT_ID));
            }
        }
        return null;
    }


    private Response<DhHHPost> removeCommentFromPost(String language, String postId, String commentUserId, String commentId) {
        if (Utility.isFieldEmpty(postId) || Utility.isFieldEmpty(commentUserId))
            return new Response<DhHHPost>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>(), 0);

        Query querFindPostById = new Query(Criteria.where(AppConstants.KEY_HH_POST_ID).is(postId));
        querFindPostById.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        querFindPostById.fields().include(AppConstants.KEY_USER_ID);
        querFindPostById.fields().include(AppConstants.KEY_DH_HH_COMMENTS_IDS);
        DhHHPost queriedDhHhPost = mongoTemplate.findOne(querFindPostById, DhHHPost.class);
        if (queriedDhHhPost == null)
            return new Response<DhHHPost>(false, 402, "There is no post with given postId !", new ArrayList<>(), 0);

        Query queryFindCommentById = new Query();
        queryFindCommentById.addCriteria(Criteria.where(AppConstants.KEY_DH_COMMENT_ID).is(commentId));
        Update updateCommentStatus = new Update();
        updateCommentStatus.set(AppConstants.STATUS, AppConstants.STATUS_DELETED);
        updateCommentStatus.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
        mongoTemplate.updateFirst(queryFindCommentById, updateCommentStatus, DhComments.class);

        if (queriedDhHhPost.getDhCommentsIds() != null) {
            for (String comment : queriedDhHhPost.getDhCommentsIds()) {
                if (comment.equals(commentId)) {
                    Update updateHhPost = new Update();
                    queriedDhHhPost.getDhCommentsIds().remove(comment);
                    updateHhPost.set(AppConstants.KEY_DH_HH_COMMENTS_IDS, queriedDhHhPost.getDhCommentsIds());
                    updateHhPost.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
                    mongoTemplate.updateFirst(querFindPostById, updateHhPost, DhHHPost.class);
                    break;
                }
            }
        }
        return new Response<DhHHPost>(true, 201, "Comment has been removed .", new ArrayList<>(), 0);
    }

    private Response<DhHHPost> addReplyOnPostComment(String language, String postId, String commentId, String replyUserId, String replyUsername, String reply) {
        if (Utility.isFieldEmpty(postId) || Utility.isFieldEmpty(replyUserId) || Utility.isFieldEmpty(reply))
            return new Response<DhHHPost>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>(), 0);

        if (!commonService.checkUserExistence(replyUserId))
            return new Response<DhHHPost>(false, 402, "User not found with given replyUserId", new ArrayList<>(), 0);

        Query querFindPostById = new Query(Criteria.where(AppConstants.KEY_HH_POST_ID).is(postId));
        querFindPostById.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        querFindPostById.fields().include(AppConstants.KEY_USER_ID);
        DhHHPost queriedDhHhPost = mongoTemplate.findOne(querFindPostById, DhHHPost.class);
        if (queriedDhHhPost == null)
            return new Response<DhHHPost>(false, 402, "There is no post with given postId !", new ArrayList<>(), 0);

        Query querFindCommentById = new Query(Criteria.where(AppConstants.KEY_DH_COMMENT_ID).is(commentId));
        querFindCommentById.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        querFindCommentById.fields().include(AppConstants.COMMENTS_THREADS_LIST);
        DhComments dhComments = mongoTemplate.findOne(querFindCommentById, DhComments.class);

        if (dhComments == null)
            return new Response<DhHHPost>(false, 402, "There is no comment with given commentId !", new ArrayList<>(), 0);

        Update updateComments = new Update();
        Threads threads = new Threads();
        threads.setReplyToComment(reply);
        threads.setName(replyUsername);
        threads.setUserId(replyUserId);
        threads = (Threads) Utility.setCommonAttrs(threads, AppConstants.STATUS_ACTIVE);
        if (dhComments.getThreadsList() == null) {
            List<Threads> tempList = new ArrayList<>();
            tempList.add(threads);
            updateComments.set(AppConstants.COMMENTS_THREADS_LIST, tempList);
        } else {
            updateComments.push(AppConstants.COMMENTS_THREADS_LIST, threads);
        }
        updateComments.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
        mongoTemplate.updateFirst(querFindCommentById, updateComments, DhComments.class);

        return new Response<DhHHPost>(true, 201, "Reply to the comment has been added .", new ArrayList<>(), 0);
    }

    private Response<DhHHPost> addCommentToPost(String language, String postId, String commentUserId, String commentUserName, String comment) {
        if (Utility.isFieldEmpty(postId) || Utility.isFieldEmpty(commentUserId) || Utility.isFieldEmpty(comment))
            return new Response<DhHHPost>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>(), 0);

        if (!commonService.checkUserExistence(commentUserId))
            return new Response<DhHHPost>(false, 402, "User not found with given commentUserId", new ArrayList<>(), 0);

        Query querFindPostById = new Query(Criteria.where(AppConstants.KEY_HH_POST_ID).is(postId));
        querFindPostById.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        querFindPostById.fields().include(AppConstants.KEY_USER_ID);
        querFindPostById.fields().include(AppConstants.KEY_POST_COMMENTS_ON_OFF);
        querFindPostById.fields().include(AppConstants.KEY_DH_HH_COMMENTS_IDS);
        DhHHPost queriedDhHhPost = mongoTemplate.findOne(querFindPostById, DhHHPost.class);
        if (queriedDhHhPost == null)
            return new Response<DhHHPost>(false, 402, "There is no post with given postId !", new ArrayList<>(), 0);

        if (!queriedDhHhPost.isPostCommentsOnOff())
            return new Response<DhHHPost>(false, 402, "Unable to add comments as user turned off this feature !", new ArrayList<>(), 0);

        //validating user against user ids intentionally, later in this code used this quried objects to update dhUser
        Query findUserQuery = new Query(Criteria.where(AppConstants.KEY_USER_ID).is(queriedDhHhPost.getUserId()).andOperator(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i")));
        findUserQuery.fields().include(AppConstants.KEY_USER_SETTINGS);
        findUserQuery.fields().include(AppConstants.KEY_USER_SETTINGS_ENABLED);
        findUserQuery.fields().include(AppConstants.KEY_NOTIFICATIONS_REQUIRED);
        DhUser queriedDhUser = mongoTemplate.findOne(findUserQuery, DhUser.class);
        if (queriedDhUser == null)
            return new Response<DhHHPost>(false, 402, "User not found with given commentUserId", new ArrayList<>(), 0);

        if (queriedDhUser.getUserSettingEnabled() && queriedDhUser.getUserSettings() != null && !queriedDhUser.getUserSettings().isHhPostCommentsOnOff())
            return new Response<DhHHPost>(false, 402, "Unable to add comments as user turned off this feature !", new ArrayList<>(), 0);

        //insert things into dhComments
        String commentId = Utility.getUUID();
        DhComments dhComments = new DhComments();
        dhComments.setCommentId(commentId);
        dhComments.setAddedBy(commentUserId);
        dhComments.setComment(comment);
        dhComments.setUserName(commentUserName);
        dhComments.setContentId(postId);
        dhComments.setContentType(ContentType.CONTENT_HH_POST);
        dhComments = (DhComments) Utility.setCommonAttrs(dhComments, AppConstants.STATUS_ACTIVE);
        mongoTemplate.save(dhComments, AppConstants.COLLECTION_DH_COMMENTS);

        //update dh hh post
        Update updateHhPost = new Update();
        if (queriedDhHhPost.getDhCommentsIds() == null) {
            List<String> tempList = new ArrayList<>();
            tempList.add(commentId);
            updateHhPost.set(AppConstants.KEY_DH_HH_COMMENTS_IDS, tempList);
        } else
            updateHhPost.push(AppConstants.KEY_DH_HH_COMMENTS_IDS, commentId);
        updateHhPost.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
        mongoTemplate.updateFirst(querFindPostById, updateHhPost, DhHHPost.class);

        if (queriedDhUser.getUserSettingEnabled() && queriedDhUser.getUserSettings().isNotificationsRequired() && !queriedDhHhPost.getUserId().equals(commentUserId))
            Utility.sendNotification(ContentType.CONTENT_ADD_HH_POST_COMMENT, queriedDhHhPost.getUserId(), mongoTemplate, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NTFN_TITLE_HH_POST_COMMENT_ADD), String.format(Locale.US, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NTFN_BODY_HH_POST_COMMENT_ADD), commentUserName), RedirectionContent.REDCONTENT_HH_POST_COMMENT_ADD, RedirectionContent.REDURL_HH_POST_COMMENT_ADD);
        return new Response<DhHHPost>(true, 201, "Comment has been added .", new ArrayList<>(), 0);

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
        querFindPostById.fields().include(AppConstants.HH_POST_GENUINE_PERC);
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


            //update user
            Update updatePostAddedUser = new Update();
            float prevAvgGenPerc = queriedDhHhPost.getPostGenuinePerc();
            long genuineRatingCount = isGenuine ? queriedDhHhPost.getGenuineRatingUserIds().size() + 1 : queriedDhHhPost.getGenuineRatingUserIds().size();
            long nonGenuineRatingCount = !isGenuine ? queriedDhHhPost.getNotGenuineRatingUserIds().size() + 1 : queriedDhHhPost.getNotGenuineRatingUserIds().size();
            float avgGenuinePercentage = queriedPostAddedDhUser.getHhGenuinePercentage();
            long totalAddedPost = queriedPostAddedDhUser.getNumberOfHHPosts();
            long currentGenPerc = (genuineRatingCount * 100 / (genuineRatingCount + nonGenuineRatingCount));
            float finalAvgGenPerc = calculatePerPostGenuinePercentage(avgGenuinePercentage, prevAvgGenPerc, currentGenPerc, totalAddedPost);
            updatePostAddedUser.set(AppConstants.KEY_GENUINE_PERCENTAGE, finalAvgGenPerc);
            updatePostAddedUser.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
            mongoTemplate.updateFirst(findPostAddedUserQuery, updatePostAddedUser, DhUser.class);

            updateDhHhPost.set(AppConstants.HH_POST_GENUINE_PERC, currentGenPerc);
            mongoTemplate.updateFirst(querFindPostById, updateDhHhPost, DhHHPost.class);
            return new Response<DhHHPost>(true, 201, isGenuine ? "Marked post as genuine" : "Marked post as non genuine", new ArrayList<>(), 0);
        }
        return new Response<DhHHPost>(true, 402, "Post not found with given postId!", new ArrayList<>(), 0);
    }

    private Response<DhHHPost> markAsLiked(String language, String hhPostId, String likedUserId, boolean markOrUnmark) {
        if (Utility.isFieldEmpty(hhPostId) || Utility.isFieldEmpty(likedUserId)) {
            return new Response<DhHHPost>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>(), 0);
        }


        if (markOrUnmark && !commonService.checkUserExistence(likedUserId))
            return new Response<DhHHPost>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_USER_NOT_FOUND_WITH_USERID), new ArrayList<>(), 0);

        Query querFindPostById = new Query(Criteria.where(AppConstants.KEY_HH_POST_ID).is(hhPostId));
        querFindPostById.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        querFindPostById.fields().include(AppConstants.KEY_LIKED_USER_IDS);
        DhHHPost dhHHPost = mongoTemplate.findOne(querFindPostById, DhHHPost.class);
        Query query = new Query(Criteria.where(AppConstants.KEY_HH_POST_ID).is(hhPostId));
        query.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        if (dhHHPost != null) {
            if (dhHHPost.getLikedUserIds() != null) {
                for (String s : dhHHPost.getLikedUserIds()) {
                    if (s.equalsIgnoreCase(likedUserId)) {
                        if (markOrUnmark)
                            return new Response<DhHHPost>(true, 201, "Already marked post as liked", new ArrayList<>(), 0);
                        else {
                            dhHHPost.getLikedUserIds().remove(likedUserId);
                            Update update = new Update();
                            update.set(AppConstants.KEY_LIKED_USER_IDS, dhHHPost.getLikedUserIds());
                            update.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
                            mongoTemplate.updateFirst(query, update, DhHHPost.class);
                            return new Response<DhHHPost>(true, 201, "Marked post as dis liked", new ArrayList<>(), 0);
                        }
                    }
                }
            }
            if (!markOrUnmark)
                return new Response<DhHHPost>(true, 201, "No likes found with given UserId !", new ArrayList<>(), 0);

            Update update = new Update();
            update.push(AppConstants.KEY_LIKED_USER_IDS, likedUserId);
            update.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
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
        querFindPostById.fields().include(AppConstants.HH_POST_GENUINE_PERC);
        DhHHPost queriedDhHhPost = mongoTemplate.findOne(querFindPostById, DhHHPost.class);
        if (queriedDhHhPost == null)
            return new Response<DhHHPost>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_SOMETHING_WENT_WRONG), new ArrayList<>(), 0);

        //to avoid crashes
        if (queriedDhHhPost.getGenuineRatingUserIds() == null)
            queriedDhHhPost.setGenuineRatingUserIds(new ArrayList<>());
        if (queriedDhHhPost.getNotGenuineRatingUserIds() == null)
            queriedDhHhPost.setNotGenuineRatingUserIds(new ArrayList<>());

        Update updateHhPost = new Update();
        for (String gn : queriedDhHhPost.getNotGenuineRatingUserIds()) {
            if (gn.equals(helpedUserId)) {
                queriedDhHhPost.getNotGenuineRatingUserIds().remove(helpedUserId);
                updateHhPost.set(AppConstants.KEY_NOTGENUINE_RATING_USER_IDS, queriedDhHhPost.getNotGenuineRatingUserIds());
                break;
            }
        }

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

        //Update helped user details
        Update updateHelpedUser = new Update();
        updateHelpedUser.set(AppConstants.KEY_NUMBER_OF_HH_HELPS, queriedHelpedDhUser.getNumberOfHHHelps() + 1);
        updateHelpedUser.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
        mongoTemplate.updateFirst(findHelpedUserQuery, updateHelpedUser, DhUser.class);

        //Update post added user
        Update updatePostAddedUser = new Update();
        float prevAvgGenPerc = queriedDhHhPost.getPostGenuinePerc();
        long nonGenuineRatingCount = queriedDhHhPost.getNotGenuineRatingUserIds().size();
        long genuineRatingCount = queriedDhHhPost.getGenuineRatingUserIds().size() + 1;
        float avgGenuinePercentage = queriedPostAddedDhUser.getHhGenuinePercentage();
        long totalAddedPost = queriedPostAddedDhUser.getNumberOfHHPosts();
        long currentGenPerc = (genuineRatingCount * 100 / (genuineRatingCount + nonGenuineRatingCount));
        float finalAvgGenPerc = calculatePerPostGenuinePercentage(avgGenuinePercentage, prevAvgGenPerc, currentGenPerc, totalAddedPost);
        updatePostAddedUser.set(AppConstants.KEY_GENUINE_PERCENTAGE, finalAvgGenPerc);
        updatePostAddedUser.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
        mongoTemplate.updateFirst(findPostAddedUserQuery, updatePostAddedUser, DhUser.class);

        updateHhPost.set(AppConstants.HH_POST_GENUINE_PERC, currentGenPerc);
        mongoTemplate.updateFirst(querFindPostById, updateHhPost, DhHHPost.class);

        return new Response<DhHHPost>(true, 201, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_HH_MARK_POST_HELPED_MSG), ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_HH_MARK_POST_HELPED_BODY), new ArrayList<>(), 0);
    }

    //working and tested,
    private float calculatePerPostGenuinePercentage(float avgGenuinePercentage, float prevAvgGenuinePercentage, long currentGenPerc, long totalAddedPost) {
        float sumOfAllPostsAvg = avgGenuinePercentage * totalAddedPost;
        float finalPerc = ((sumOfAllPostsAvg - prevAvgGenuinePercentage) + currentGenPerc) / totalAddedPost;
        LOGGER.info("calculatePerPostGenuinePercentage:avgGenuinePercentage:" + avgGenuinePercentage + " prevAvgGenuinePercentage:" + prevAvgGenuinePercentage + " currentGenPerc:" + currentGenPerc + " totalAddedPost:" + totalAddedPost + " sumOfAllPostsAvg:" + sumOfAllPostsAvg + " finalPerc:" + finalPerc);
        return (float) Utility.roundOneDecimals(finalPerc);
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
