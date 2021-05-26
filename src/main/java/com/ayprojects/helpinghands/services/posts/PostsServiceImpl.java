package com.ayprojects.helpinghands.services.posts;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPromotions;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.repositories.PostsRepository;
import com.ayprojects.helpinghands.services.common_service.CommonService;
import com.ayprojects.helpinghands.util.tools.CalendarOperations;
import com.ayprojects.helpinghands.util.tools.Utility;
import com.ayprojects.helpinghands.util.tools.Validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.ayprojects.helpinghands.AppConstants.BUSINESS_PROMOTION;
import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class PostsServiceImpl implements PostsService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    PostsRepository postsRepository;

    @Autowired
    Utility utility;

    @Autowired
    CommonService commonService;

    @Override
    public Response<DhPromotions> addPost(Authentication authentication, HttpHeaders httpHeaders, DhPromotions dhPromotions, String version) throws ServerSideException {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("PostsServiceImpl->addPost : language=" + language);

        String emptyBodyResMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language);
        if (dhPromotions == null) {
            return new Response<DhPromotions>(false, 402, emptyBodyResMsg, new ArrayList<>(), 0);
        }

        //check if postId present && postImages present
        if (Utility.isFieldEmpty(dhPromotions.getPlaceId()) || dhPromotions.getPromotionImagesLow() == null || dhPromotions.getPromotionImagesLow().size() <= 0) {
            dhPromotions.setPromotionId(Utility.getUUID());
        }

        List<String> missingFieldsList = Validations.findMissingFieldsForPosts(dhPromotions);
        if (missingFieldsList.size() > 0) {
            emptyBodyResMsg = emptyBodyResMsg + " , these fields are missing : " + missingFieldsList;
            return new Response<DhPromotions>(false, 402, emptyBodyResMsg, new ArrayList<>(), 0);
        } else {
            if (!dhPromotions.getPromotionType().equalsIgnoreCase(AppConstants.PUBLIC_PROMOTION) && !dhPromotions.getPromotionType().equalsIgnoreCase(BUSINESS_PROMOTION)) {
                return new Response<DhPromotions>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_INVALID_PROMOTIONTYPE, language), new ArrayList<>(), 0);
            }
        }


        //check for user existence
        if (!commonService.checkUserExistence(dhPromotions.getAddedBy())) {
            return new Response<DhPromotions>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_USER_NOT_FOUND_WITH_USERID, language), new ArrayList<>(), 0);
        }

        //check whether place exists with given placeId
        DhPlace queriedDhPlace = null;
        Query queryFindPlaceWithId = null;
        boolean isBusinessPost = false;
        if (dhPromotions.getPromotionType().matches(AppConstants.REGEX_BUSINESS_PROMOTION)) {
            isBusinessPost = true;
            queryFindPlaceWithId = new Query(Criteria.where(AppConstants.PLACE_ID).is(dhPromotions.getPlaceId()));
            queryFindPlaceWithId.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
            queryFindPlaceWithId.fields().include(AppConstants.TOP_PROMOTIONS);
            queryFindPlaceWithId.fields().include(AppConstants.NUMBER_OF_PROMOTIONS);
            queriedDhPlace = mongoTemplate.findOne(queryFindPlaceWithId, DhPlace.class);
            if (queriedDhPlace == null) {
                return new Response<DhPromotions>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_PLACE_NOT_FOUND_WITH_PLACEID, language), new ArrayList<>(), 0);
            }
        }

        //store posts
        dhPromotions = (DhPromotions) utility.setCommonAttrs(dhPromotions, AppConstants.STATUS_ACTIVE);
        mongoTemplate.save(dhPromotions, AppConstants.COLLECTION_DH_PROMOTIONS);
        utility.addLog(authentication.getName(), "New [" + dhPromotions.getPromotionType() + "] post has been added.");

        //update the place if post is businesspost
        if (isBusinessPost) {
            LOGGER.info("PostsServiceImpl->addPost : It's business post");
            Update updatePlace = new Update();
            LOGGER.info("PostsServiceImpl->addPost : postIds block is null, pushing post id into postIds array");
            updatePlace.push(AppConstants.PROMOTION_IDS, dhPromotions.getPromotionId());
            updatePlace.set(AppConstants.NUMBER_OF_PROMOTIONS, queriedDhPlace.getNumberOfPromotions() + 1);
            if (queriedDhPlace.getTopPromotions() != null && queriedDhPlace.getTopPromotions().size() == AppConstants.LIMIT_PROMOTIONS_IN_PLACES) {
                Update updatePopTopPost = new Update();
                updatePopTopPost.pop(AppConstants.TOP_PROMOTIONS, Update.Position.FIRST);
                mongoTemplate.updateFirst(queryFindPlaceWithId, updatePopTopPost, DhPlace.class);
            }
            updatePlace.push(AppConstants.TOP_PROMOTIONS, dhPromotions);
            updatePlace.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
            mongoTemplate.updateFirst(queryFindPlaceWithId, updatePlace, DhPlace.class);
        }

        return new Response<>(true, 201, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NEW_PROMOTION_ADDED, language), new ArrayList<>(), 1);
    }

    @Override
    public Response<DhPromotions> deletePost(Authentication authentication, HttpHeaders httpHeaders, String postId, String version) throws ServerSideException {
        return null;
    }

    @Override
    public Response<DhPromotions> updatePost(Authentication authentication, HttpHeaders httpHeaders, DhPromotions dhPromotions, String version) throws ServerSideException {
        return null;
    }

    @Override
    public Response<DhPromotions> getPosts(Authentication authentication, HttpHeaders httpHeaders, String searchValue, String version) {
        return null;
    }

    @Override
    public Response<DhPromotions> getPaginatedPosts(Authentication authentication, HttpHeaders httpHeaders, int page, int size, String version) {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("PostsServiceImpl->addPost : language=" + language);

        PageRequest paging = PageRequest.of(page, size);
        Page<DhPromotions> dhPostPages = postsRepository.findAllByStatus(AppConstants.STATUS_ACTIVE, paging);
        List<DhPromotions> dhPromotionsList = dhPostPages.getContent();
        for (DhPromotions d : dhPromotionsList) {

            if (!Utility.isFieldEmpty(d.getOfferStartTime()) && !Utility.isFieldEmpty(d.getOfferEndTime())) {
                String offerMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_OFFER_MSG, language);
                offerMsg = String.format("%s %s - %s", offerMsg, d.getOfferStartTime(), d.getOfferEndTime());
                d.setOfferMsg(offerMsg);
            }

            try {
                DhUser dhUser = Utility.getUserDetailsFromId(d.getAddedBy(), mongoTemplate, true, false, true);
                d.setUserName(dhUser.getFirstName());
                d.setUserImage(dhUser.getProfileImgLow());
            } catch (NullPointerException e) {
                LOGGER.info("getPaginatedPosts->catch while fetching userdetails->message:" + e.getMessage());
            }

            try {
                DhPlace dhPlace = Utility.getPlaceDetailsFromId(d.getPlaceId(), mongoTemplate, true, true);
                d.setPlaceName(dhPlace.getPlaceName());
                d.setPlaceCategory(dhPlace.getPlaceSubCategoryName());
            } catch (NullPointerException e) {
                LOGGER.info("getPaginatedPosts->catch while fetching placedetails->message:" + e.getMessage());
            }
        }

        return new Response<DhPromotions>(true, 200, "Query successful", dhPromotionsList.size(), dhPostPages.getNumber(), dhPostPages.getTotalPages(), dhPostPages.getTotalElements(), dhPromotionsList);
    }

    @Override
    public Response<DhPromotions> getPaginatedPostsByPlaceId(Authentication authentication, HttpHeaders httpHeaders, int page, int size, String placeId, String version) {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("PostsServiceImpl->getPaginatedPostsByPlaceId : language=" + language);
        LOGGER.info("PostsServiceImpl->getPaginatedPostsByPlaceId : placeId=" + placeId + " page=" + page + " size=" + size);

        List<String> missingFieldsList = new ArrayList<>();
        if (Utility.isFieldEmpty(placeId)) missingFieldsList.add("PlaceId");
        if (missingFieldsList.size() > 0) {
            String resMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language);
            resMsg = resMsg + " , these fields are missing : " + missingFieldsList;
            return new Response<>(false, 402, resMsg, new ArrayList<>(), 0);
        }
        Pageable pageable = PageRequest.of(page, size);
        Criteria criteria = new Criteria();
        Pattern patternPostType = Pattern.compile(AppConstants.REGEX_BUSINESS_PROMOTION);
        criteria.and(AppConstants.PROMOTION_TYPE).regex(patternPostType);
        criteria.and(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i");
        criteria.and(AppConstants.PLACE_ID).is(placeId);
        Query queryGetPosts = new Query(criteria).with(pageable);
        List<DhPromotions> dhPromotionsList = mongoTemplate.find(queryGetPosts, DhPromotions.class);
        Page<DhPromotions> dhPostsPage = PageableExecutionUtils.getPage(
                dhPromotionsList,
                pageable,
                () -> mongoTemplate.count(queryGetPosts, DhPromotions.class));
        return new Response<>(true, 200, "Query successful", dhPromotionsList.size(), dhPostsPage.getNumber(), dhPostsPage.getTotalPages(), dhPostsPage.getTotalElements(), dhPromotionsList);
    }
}
