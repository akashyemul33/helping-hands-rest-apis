package com.ayprojects.helpinghands.api.classes.add_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyAddBehaviour;
import com.ayprojects.helpinghands.api.enums.ContentType;
import com.ayprojects.helpinghands.api.enums.RedirectionContent;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhLog;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPosts;
import com.ayprojects.helpinghands.models.DhRatingAndComments;
import com.ayprojects.helpinghands.models.DhRequirements;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.models.Threads;
import com.ayprojects.helpinghands.services.firebase.FirebaseSetup;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.CalendarOperations;
import com.ayprojects.helpinghands.util.tools.Utility;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Component
public class StrategyAddRatingApi implements StrategyAddBehaviour<DhRatingAndComments> {
    @Autowired
    FirebaseSetup firebaseSetup;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Response<DhRatingAndComments> add(String language, DhRatingAndComments obj) throws ServerSideException {
        Response<DhRatingAndComments> validationResponse = validateAddRatingAndComments(language, obj);

        if (!validationResponse.getStatus())
            return validationResponse;
        boolean edit = false;
        if (Utility.isFieldEmpty(obj.getReviewCommentId())) {
            obj.setReviewCommentId(Utility.getUUID());
            obj = (DhRatingAndComments) Utility.setCommonAttrs(obj, AppConstants.STATUS_ACTIVE);
            mongoTemplate.save(obj, AppConstants.COLLECTION_DH_RATING_COMMENT);
        } else {
            edit = true;
            Update update = new Update();
            Query updateRatingQuery = new Query(Criteria.where(AppConstants.REVIEW_COMMENT_ID).is(obj.getReviewCommentId()));
            update.set(AppConstants.RATING, obj.getRating());
            update.set(AppConstants.COMMENT, obj.getComment());
            update.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
            mongoTemplate.updateFirst(updateRatingQuery, update, DhRatingAndComments.class);
        }
        if (obj.getNotificationRequired())
            sendNotificationToContentOwner(language, obj.getContentType(), obj.getContentUserId(), obj.getContentName(), obj.getUserName(), edit, obj.getRating());
        persistRatingIntoContentClass(obj, edit);
        return validationResponse;
    }

    @Override
    public Response<DhRatingAndComments> add(String language, DhRatingAndComments obj, HashMap<String, Object> params) throws ServerSideException {
        if (params == null || obj == null || obj.getThreads() == null)
            return new Response<DhRatingAndComments>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>(), 0);
        if (AppConstants.REPLY_TO_RATING.equals(params.get(AppConstants.RATING_API_TYPE))) {
            Threads threads = (Threads) Utility.setCommonAttrs(obj.getThreads().get(0), AppConstants.STATUS_ACTIVE);
            Update update = new Update();
            update.push(AppConstants.THREADS, threads);
            Query updateQuery = new Query(Criteria.where(AppConstants.REVIEW_COMMENT_ID).is(obj.getReviewCommentId()));
            mongoTemplate.updateFirst(updateQuery, update, DhRatingAndComments.class);
            sendNotificationOnReply(language, obj.getAddedBy(), obj.getContentType(), threads.getName());
            return new Response<>(true, 201, "Update successful", new ArrayList<>());
        }
        return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_SOMETHING_WENT_WRONG), new ArrayList<>());
    }

    private void persistRatingIntoContentClass(DhRatingAndComments dhRatingComments, boolean edit) {
        String contentIdToSearch = "";
        switch (dhRatingComments.getContentType()) {
            case CONTENT_PLACE:
                contentIdToSearch = AppConstants.PLACE_ID;
                Query queryFindPlaceWithId = new Query(Criteria.where(contentIdToSearch).is(dhRatingComments.getContentId()));
                DhPlace queriedDhPlace = mongoTemplate.findOne(queryFindPlaceWithId, DhPlace.class);
                if (queriedDhPlace == null) {
                    mongoTemplate.save(new DhLog("Unable to add rating into places collection, seems like no place found with given id : " + dhRatingComments.getContentId() + " rating id =" + dhRatingComments.getReviewCommentId()));
                    return;
                }
                Update updatePlace = new Update();

                double avgPlaceRating = 0;
                if (queriedDhPlace.getAvgRating() <= 0) {
                    avgPlaceRating = dhRatingComments.getRating();
                } else {
                    double totalRatingNumber = queriedDhPlace.getAvgRating() * queriedDhPlace.getNumberOfRatings();
                    if (edit) {
                        avgPlaceRating = ((totalRatingNumber - dhRatingComments.getPreviousRating()) + dhRatingComments.getRating()) / queriedDhPlace.getNumberOfRatings();
                    } else
                        avgPlaceRating = (totalRatingNumber + dhRatingComments.getRating()) / (queriedDhPlace.getNumberOfRatings() + 1);
                }
                if (!edit) {
                    updatePlace.set(AppConstants.NUMBER_OF_RATINGS, queriedDhPlace.getNumberOfRatings() + 1);
                    updatePlace.push(AppConstants.RATINGS_IDS, dhRatingComments.getReviewCommentId());
                    if (queriedDhPlace.getTopRatings() != null && queriedDhPlace.getTopRatings().size() == AppConstants.LIMIT_RATINGS_IN_PLACES) {
                        Update updatePopTopRating = new Update();
                        updatePopTopRating.pop(AppConstants.TOP_RATINGS, Update.Position.FIRST);
                        mongoTemplate.updateFirst(queryFindPlaceWithId, updatePopTopRating, DhPlace.class);
                    }
                    updatePlace.push(AppConstants.TOP_RATINGS, dhRatingComments);
                }
                updatePlace.set(AppConstants.AVG_RATING, avgPlaceRating);
                updatePlace.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
                mongoTemplate.updateFirst(queryFindPlaceWithId, updatePlace, DhPlace.class);
                break;
            case CONTENT_POST:
                contentIdToSearch = AppConstants.POST_ID;
                Query queryFindPostWithId = new Query(Criteria.where(contentIdToSearch).is(dhRatingComments.getContentId()));
                DhPosts queriedDhPost = mongoTemplate.findOne(queryFindPostWithId, DhPosts.class);
                if (queriedDhPost == null) {
                    mongoTemplate.save(new DhLog("Unable to add rating into posts collection, seems like no post found with given id:" + dhRatingComments.getReviewCommentId() + " rating id=" + dhRatingComments.getReviewCommentId()));
                    return;
                }
                Update updatePost = new Update();
                updatePost.set(AppConstants.NUMBER_OF_RATINGS, queriedDhPost.getNumberOfRatings() + 1);
                double avgPostsRating = 0;
                if (queriedDhPost.getAvgRating() <= 0) {
                    avgPostsRating = dhRatingComments.getRating();
                } else {
                    double totalRatingNumber = queriedDhPost.getAvgRating() * queriedDhPost.getNumberOfRatings();
                    if (edit) {
                        avgPostsRating = ((totalRatingNumber - dhRatingComments.getPreviousRating()) + dhRatingComments.getRating()) / queriedDhPost.getNumberOfRatings();
                    } else
                        avgPostsRating = (totalRatingNumber + dhRatingComments.getRating()) / (queriedDhPost.getNumberOfRatings() + 1);
                }
                updatePost.set(AppConstants.AVG_RATING, avgPostsRating);
                if (!edit) {
                    updatePost.push(AppConstants.RATINGS_IDS, dhRatingComments.getReviewCommentId());
                    if (queriedDhPost.getTopRatings() != null && queriedDhPost.getTopRatings().size() == AppConstants.LIMIT_RATINGS_IN_POSTS) {
                        Update updatePopTopRating = new Update();
                        updatePopTopRating.pop(AppConstants.TOP_RATINGS, Update.Position.LAST);
                        mongoTemplate.updateFirst(queryFindPostWithId, updatePopTopRating, DhPosts.class);
                    }
                    updatePost.push(AppConstants.TOP_RATINGS, dhRatingComments);
                }
                updatePost.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
                mongoTemplate.updateFirst(queryFindPostWithId, updatePost, DhPosts.class);
                break;
            case CONTENT_REQUIREMENT:
                contentIdToSearch = AppConstants.REQUIREMENT_ID;
                Query queryFindRequirementWithId = new Query(Criteria.where(contentIdToSearch).is(dhRatingComments.getContentId()));
                DhRequirements queriedDhRequirement = mongoTemplate.findOne(queryFindRequirementWithId, DhRequirements.class);
                if (queriedDhRequirement == null) {
                    mongoTemplate.save(new DhLog("Unable to add rating into requirements collection, seems like no requirement found with given id:" + dhRatingComments.getContentId() + " rating id:" + dhRatingComments.getReviewCommentId()));
                    return;
                }
                Update updateRequirement = new Update();
                updateRequirement.set(AppConstants.NUMBER_OF_RATINGS, queriedDhRequirement.getNumberOfRatings() + 1);
                double avgRequirementRating = 0;
                if (queriedDhRequirement.getAvgRating() <= 0) {
                    avgRequirementRating = dhRatingComments.getRating();
                } else {
                    double totalRatingNumber = queriedDhRequirement.getAvgRating() * queriedDhRequirement.getNumberOfRatings();
                    if (edit) {
                        avgRequirementRating = ((totalRatingNumber - dhRatingComments.getPreviousRating()) + dhRatingComments.getRating()) / queriedDhRequirement.getNumberOfRatings();
                    } else
                        avgRequirementRating = (totalRatingNumber + dhRatingComments.getRating()) / (queriedDhRequirement.getNumberOfRatings() + 1);
                }
                updateRequirement.set(AppConstants.AVG_RATING, avgRequirementRating);
                if (!edit) {
                    updateRequirement.push(AppConstants.RATINGS_IDS, dhRatingComments.getReviewCommentId());
                    if (queriedDhRequirement.getTopRatings() != null && queriedDhRequirement.getTopRatings().size() == AppConstants.LIMIT_RATINGS_IN_REQUIREMENTS) {
                        Update updatePopTopRating = new Update();
                        updatePopTopRating.pop(AppConstants.TOP_RATINGS, Update.Position.LAST);
                        mongoTemplate.updateFirst(queryFindRequirementWithId, updatePopTopRating, DhRequirements.class);
                    }
                    updateRequirement.push(AppConstants.TOP_RATINGS, dhRatingComments);
                }
                updateRequirement.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
                mongoTemplate.updateFirst(queryFindRequirementWithId, updateRequirement, DhRequirements.class);
                break;
        }
    }

    private void sendNotificationOnReply(String lang, String contentUserId, ContentType contentType, String replyName) {
        Query query = new Query(Criteria.where(AppConstants.KEY_USER_ID).is(contentUserId));
        query.fields().include(AppConstants.KEY_FCM_TOKEN);
        DhUser dhUser = mongoTemplate.findOne(query, DhUser.class);
        if (dhUser != null) {
            String fcmToken = dhUser.getFcmToken();
            LOGGER.info("sendNotificationToContentOwner:fcmToken->" + fcmToken);
            String body = replyName + " has replied to your comment.";
            String title = ResponseMsgFactory.getResponseMsg(lang, AppConstants.RESPONSEMESSAGE_RATING_REPLIED_TITLE);
            Message message = Message.builder()
                    .putData("title", title)
                    .putData("body", body)
                    .setToken(fcmToken)
                    .build();
            try {
                FirebaseMessaging.getInstance().send(message);
                Utility.insertNotification(contentUserId, title, body, RedirectionContent.REDCONTENT_PLACEDETAILS, "", mongoTemplate);
            } catch (FirebaseMessagingException e) {
                e.printStackTrace();
            }
        }

    }

    //Intentionally avoided using Utility.sendNotification method
    private void sendNotificationToContentOwner(String lang, ContentType contentType, String contentUserId, String contentName, String userName, boolean edit, double rating) {
        Query query = new Query(Criteria.where(AppConstants.KEY_USER_ID).is(contentUserId));
        query.fields().include(AppConstants.KEY_FCM_TOKEN);
        DhUser dhUser = mongoTemplate.findOne(query, DhUser.class);
        if (dhUser != null) {
            String fcmToken = dhUser.getFcmToken();
            LOGGER.info("sendNotificationToContentOwner:fcmToken->" + fcmToken);
            String body = "";
            if (edit)
                body = userName + " has changed rating of your " + contentType.name().split("_")[1].toLowerCase() + " " + contentName + " to " + rating;
            else
                body = userName + " has rated " + rating + " to your " + contentType.name().split("_")[1].toLowerCase() + " " + contentName;
            String title = ResponseMsgFactory.getResponseMsg(lang, AppConstants.RESPONSEMESSAGE_RATING_ADDED_TITLE);
            Message message = Message.builder()
                    .putData("title", title)
                    .putData("body", body)
                    .setToken(fcmToken)
                    .build();
            try {
                FirebaseMessaging.getInstance().send(message);
                Utility.insertNotification(contentUserId, title, body, RedirectionContent.REDCONTENT_EDITPLACE_TOPSECTION, "", mongoTemplate);
            } catch (FirebaseMessagingException e) {
                e.printStackTrace();
            }
        }
    }

    private Response<DhRatingAndComments> validateAddRatingAndComments(String language, DhRatingAndComments obj) {
        if (obj == null) {
            return new Response<DhRatingAndComments>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>(), 0);
        }
        List<String> missingFieldsList = new ArrayList<>();
        if (Utility.isFieldEmpty(obj.getAddedBy()))
            missingFieldsList.add(AppConstants.ADDED_BY);
        if (Utility.isFieldEmpty(obj.getContentId()))
            missingFieldsList.add(AppConstants.CONTENT_ID);
        if (Utility.isFieldEmpty(obj.getContentUserId()))
            missingFieldsList.add(AppConstants.KEY_CONTENT_USER_ID);
        if (obj.getContentType() == null)
            missingFieldsList.add(AppConstants.CONTENT_TYPE);

        if (missingFieldsList.size() > 0) {
            String resMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY);
            resMsg = resMsg + " , these fields are missing : " + missingFieldsList;
            LOGGER.info("missingfields=>" + resMsg);
            return new Response<DhRatingAndComments>(false, 402, resMsg, new ArrayList<>(), 0);
        }
        return new Response<DhRatingAndComments>(true, 201, "Validated", new ArrayList<>(), 0);
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.AddRatingStrategy;
    }
}
