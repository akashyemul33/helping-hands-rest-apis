package com.ayprojects.helpinghands.api.classes.add_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.behaviours.StrategyAddBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPromotions;
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

import static com.ayprojects.helpinghands.AppConstants.BUSINESS_PROMOTION;
import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Component
public class StrategyAddPostApi implements StrategyAddBehaviour<DhPromotions> {

    @Autowired
    CommonService commonService;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<DhPromotions> add(String language, DhPromotions dhPromotions) {

        //check if postId present && postImages present
        dhPromotions = setPostIdIfNotExists(dhPromotions);

        Response<DhPromotions> validationResponse = validateAddPosts(dhPromotions, language);
        if (!validationResponse.getStatus())
            return validationResponse;

        //check for user existence
        if (!commonService.checkUserExistence(dhPromotions.getAddedBy())) {
            return new Response<DhPromotions>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_USER_NOT_FOUND_WITH_USERID, language), new ArrayList<>(), 0);
        }

        //check whether place exists with given placeId
        DhPlace queriedDhPlace = null;
        Query queryFindPlaceWithId = null;
        boolean isBusinessPost = false;
        if (dhPromotions.getPromotionType().matches(AppConstants.REGEX_BUSINESS_POST)) {
            isBusinessPost = true;
            queryFindPlaceWithId = new Query(Criteria.where(AppConstants.PLACE_ID).is(dhPromotions.getPlaceId()));
            queryFindPlaceWithId.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
            queryFindPlaceWithId.fields().include(AppConstants.TOP_POSTS);
            queryFindPlaceWithId.fields().include(AppConstants.NUMBER_OF_POSTS);
            queriedDhPlace = mongoTemplate.findOne(queryFindPlaceWithId, DhPlace.class);
            if (queriedDhPlace == null) {
                return new Response<DhPromotions>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_PLACE_NOT_FOUND_WITH_PLACEID, language), new ArrayList<>(), 0);
            }
        }

        //store posts
        dhPromotions = (DhPromotions) ApiOperations.setCommonAttrs(dhPromotions, AppConstants.STATUS_ACTIVE);
        mongoTemplate.save(dhPromotions, AppConstants.COLLECTION_DH_PROMOTIONS);
        validationResponse.setLogActionMsg("New [" + dhPromotions.getPromotionType() + "] post has been added.");

        //update the place if post is businesspost
        if (isBusinessPost) {
            LOGGER.info("PostsServiceImpl->addPost : It's business post");
            Update updatePlace = new Update();
            LOGGER.info("PostsServiceImpl->addPost : postIds block is null, pushing post id into postIds array");
            updatePlace.push(AppConstants.POST_IDS, dhPromotions.getPromotionId());
            updatePlace.set(AppConstants.NUMBER_OF_POSTS, queriedDhPlace.getNumberOfPromotions() + 1);
            if (queriedDhPlace.getTopPromotions() != null && queriedDhPlace.getTopPromotions().size() == AppConstants.LIMIT_POSTS_IN_PLACES) {
                Update updatePopTopPost = new Update();
                updatePopTopPost.pop(AppConstants.TOP_POSTS, Update.Position.FIRST);
                mongoTemplate.updateFirst(queryFindPlaceWithId, updatePopTopPost, DhPlace.class);
            }
            updatePlace.push(AppConstants.TOP_POSTS, dhPromotions);
            updatePlace.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
            mongoTemplate.updateFirst(queryFindPlaceWithId, updatePlace, DhPlace.class);
        }

        return new Response<>(true, 201, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NEW_PROMOTION_ADDED, language), new ArrayList<>(), 1);

    }

    @Override
    public Response<DhPromotions> add(String language, DhPromotions obj, HashMap<String, Object> params) throws ServerSideException {
        return null;
    }

    private DhPromotions setPostIdIfNotExists(DhPromotions dhPromotions) {
        if (Utility.isFieldEmpty(dhPromotions.getPlaceId()) || dhPromotions.getPromotionImagesLow() == null || dhPromotions.getPromotionImagesLow().size() <= 0) {
            dhPromotions.setPromotionId(Utility.getUUID());
        }
        return dhPromotions;
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.AddPostStrategy;
    }

    public Response<DhPromotions> validateAddPosts(DhPromotions dhPromotions, String language) {
        if (dhPromotions == null)
            return new Response<DhPromotions>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>(), 0);

        List<String> missingFieldsList = new ArrayList<>();

        if (Utility.isFieldEmpty(dhPromotions.getAddedBy()))
            missingFieldsList.add(AppConstants.ADDED_BY);
        if (Utility.isFieldEmpty(dhPromotions.getPromotionType()))
            missingFieldsList.add(AppConstants.POST_TYPE);
        if (!dhPromotions.getPromotionType().equalsIgnoreCase(AppConstants.PUBLIC_PROMOTION) && !dhPromotions.getPromotionType().equalsIgnoreCase(BUSINESS_PROMOTION)) {
            return new Response<DhPromotions>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_INVALID_POSTTYPE, language), new ArrayList<>(), 0);
        }

        if (Utility.isFieldEmpty(dhPromotions.getPromotionId())) missingFieldsList.add(AppConstants.POST_ID);

        if (dhPromotions.getPromotionType().matches(AppConstants.REGEX_BUSINESS_POST)) {
            if (Utility.isFieldEmpty(dhPromotions.getPlaceId())) {
                missingFieldsList.add(AppConstants.PLACE_ID);
            }
        }

        if (Utility.isFieldEmpty(dhPromotions.getFullAddress()))
            missingFieldsList.add(AppConstants.FULL_ADDRESS);

        if (dhPromotions.getContactDetails() == null)
            missingFieldsList.add(AppConstants.CONTACT_DETAILS);
        else {
            if (Utility.isFieldEmpty(dhPromotions.getContactDetails().getMobile())) {
                missingFieldsList.add(AppConstants.KEY_MOBILE);
            }
        }

        if (Utility.isFieldEmpty(dhPromotions.getPromotionTitle()))
            missingFieldsList.add(AppConstants.POST_TITLE);

        if (Utility.isFieldEmpty(dhPromotions.getPromotionDesc()))
            missingFieldsList.add(AppConstants.PROMOTION_DESC);

        if (Utility.isFieldEmpty(dhPromotions.getOfferStartTime()) && !Utility.isFieldEmpty(dhPromotions.getOfferEndTime())) {
            missingFieldsList.add(AppConstants.PROMOTION_OFFER_START_TIME);
        } else if (Utility.isFieldEmpty(dhPromotions.getOfferEndTime()) && !Utility.isFieldEmpty(dhPromotions.getOfferStartTime())) {
            missingFieldsList.add(AppConstants.PROMOTION_OFFER_END_TIME);
        }

        if (missingFieldsList.size() > 0) {
            String resMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY);
            resMsg = resMsg + " , these fields are missing : " + missingFieldsList;
            LOGGER.info("missingfields=>" + resMsg);
            return new Response<DhPromotions>(false, 402, resMsg, new ArrayList<>(), 0);
        }
        return new Response<>(true, 201, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NEW_PROMOTION_ADDED, language), new ArrayList<>(), 1);
    }

}
