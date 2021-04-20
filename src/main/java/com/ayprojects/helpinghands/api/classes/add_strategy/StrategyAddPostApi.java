package com.ayprojects.helpinghands.api.classes.add_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.behaviours.StrategyAddBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPosts;
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
import java.util.List;

import static com.ayprojects.helpinghands.AppConstants.BUSINESS_POST;
import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Component
public class StrategyAddPostApi implements StrategyAddBehaviour<DhPosts> {

    @Autowired
    CommonService commonService;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<DhPosts> add(String language, DhPosts dhPosts) {

        //check if postId present && postImages present
        dhPosts = setPostIdIfNotExists(dhPosts);

        Response<DhPosts> validationResponse = validateAddPosts(dhPosts, language);
        if (!validationResponse.getStatus())
            return validationResponse;

        CalendarOperations calendarOperations = new CalendarOperations();
        //check for user existence
        if (!commonService.checkUserExistence(dhPosts.getAddedBy())) {
            return new Response<DhPosts>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_USER_NOT_FOUND_WITH_USERID, language), new ArrayList<>(), 0);
        }

        //check whether place exists with given placeId
        DhPlace queriedDhPlace = null;
        Query queryFindPlaceWithId = null;
        boolean isBusinessPost = false;
        if (dhPosts.getPostType().matches(AppConstants.REGEX_BUSINESS_POST)) {
            isBusinessPost = true;
            queryFindPlaceWithId = new Query(Criteria.where(AppConstants.PLACE_ID).is(dhPosts.getPlaceId()));
            queryFindPlaceWithId.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
            queryFindPlaceWithId.fields().include(AppConstants.TOP_POSTS);
            queryFindPlaceWithId.fields().include(AppConstants.NUMBER_OF_POSTS);
            queriedDhPlace = mongoTemplate.findOne(queryFindPlaceWithId, DhPlace.class);
            if (queriedDhPlace == null) {
                return new Response<DhPosts>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_PLACE_NOT_FOUND_WITH_PLACEID, language), new ArrayList<>(), 0);
            }
        }

        //store posts
        dhPosts = (DhPosts) ApiOperations.setCommonAttrs(dhPosts, AppConstants.STATUS_ACTIVE);
        mongoTemplate.save(dhPosts, AppConstants.COLLECTION_DH_POSTS);
        validationResponse.setLogActionMsg("New [" + dhPosts.getPostType() + "] post has been added.");

        //update the place if post is businesspost
        if (isBusinessPost) {
            LOGGER.info("PostsServiceImpl->addPost : It's business post");
            Update updatePlace = new Update();
            LOGGER.info("PostsServiceImpl->addPost : postIds block is null, pushing post id into postIds array");
            updatePlace.push(AppConstants.POST_IDS, dhPosts.getPostId());
            updatePlace.set(AppConstants.NUMBER_OF_POSTS, queriedDhPlace.getNumberOfPosts() + 1);
            if (queriedDhPlace.getTopPosts() != null && queriedDhPlace.getTopPosts().size() == AppConstants.LIMIT_POSTS_IN_PLACES) {
                Update updatePopTopPost = new Update();
                updatePopTopPost.pop(AppConstants.TOP_POSTS, Update.Position.FIRST);
                mongoTemplate.updateFirst(queryFindPlaceWithId, updatePopTopPost, DhPlace.class);
            }
            updatePlace.push(AppConstants.TOP_POSTS, dhPosts);
            updatePlace.set(AppConstants.MODIFIED_DATE_TIME, calendarOperations.currentDateTimeInUTC());
            mongoTemplate.updateFirst(queryFindPlaceWithId, updatePlace, DhPlace.class);
        }

        return new Response<>(true, 201, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NEW_POST_ADDED, language), new ArrayList<>(), 1);

    }

    private DhPosts setPostIdIfNotExists(DhPosts dhPosts) {
        if (Utility.isFieldEmpty(dhPosts.getPlaceId()) || dhPosts.getPostImagesLow() == null || dhPosts.getPostImagesLow().size() <= 0) {
            dhPosts.setPostId(Utility.getUUID());
        }
        return dhPosts;
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.AddPostStrategy;
    }

    public Response<DhPosts> validateAddPosts(DhPosts dhPosts, String language) {
        if (dhPosts == null)
            return new Response<DhPosts>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>(), 0);

        List<String> missingFieldsList = new ArrayList<>();

        if (Utility.isFieldEmpty(dhPosts.getAddedBy()))
            missingFieldsList.add(AppConstants.ADDED_BY);
        if (Utility.isFieldEmpty(dhPosts.getPostType()))
            missingFieldsList.add(AppConstants.POST_TYPE);
        if (!dhPosts.getPostType().equalsIgnoreCase(AppConstants.PUBLIC_POST) && !dhPosts.getPostType().equalsIgnoreCase(BUSINESS_POST)) {
            return new Response<DhPosts>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_INVALID_POSTTYPE, language), new ArrayList<>(), 0);
        }

        if (Utility.isFieldEmpty(dhPosts.getPostId())) missingFieldsList.add(AppConstants.POST_ID);

        if (dhPosts.getPostType().matches(AppConstants.REGEX_BUSINESS_POST)) {
            if (Utility.isFieldEmpty(dhPosts.getPlaceId())) {
                missingFieldsList.add(AppConstants.PLACE_ID);
            }
        }

        if (Utility.isFieldEmpty(dhPosts.getFullAddress()))
            missingFieldsList.add(AppConstants.FULL_ADDRESS);

        if (dhPosts.getContactDetails() == null)
            missingFieldsList.add(AppConstants.CONTACT_DETAILS);
        else {
            if (Utility.isFieldEmpty(dhPosts.getContactDetails().getMobile())) {
                missingFieldsList.add(AppConstants.KEY_MOBILE);
            }
        }

        if (Utility.isFieldEmpty(dhPosts.getPostTitle()))
            missingFieldsList.add(AppConstants.POST_TITLE);

        if (Utility.isFieldEmpty(dhPosts.getPostDesc()))
            missingFieldsList.add(AppConstants.POST_DESC);

        if (Utility.isFieldEmpty(dhPosts.getOfferStartTime()) && !Utility.isFieldEmpty(dhPosts.getOfferEndTime())) {
            missingFieldsList.add(AppConstants.POST_OFFER_START_TIME);
        } else if (Utility.isFieldEmpty(dhPosts.getOfferEndTime()) && !Utility.isFieldEmpty(dhPosts.getOfferStartTime())) {
            missingFieldsList.add(AppConstants.POST_OFFER_END_TIME);
        }

        if (missingFieldsList.size() > 0) {
            String resMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY);
            resMsg = resMsg + " , these fields are missing : " + missingFieldsList;
            LOGGER.info("missingfields=>" + resMsg);
            return new Response<DhPosts>(false, 402, resMsg, new ArrayList<>(), 0);
        }
        return new Response<>(true, 201, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NEW_POST_ADDED, language), new ArrayList<>(), 1);
    }

}
