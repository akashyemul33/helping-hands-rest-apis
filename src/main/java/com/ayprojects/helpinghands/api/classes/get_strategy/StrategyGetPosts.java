package com.ayprojects.helpinghands.api.classes.get_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyGetBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPromotions;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.repositories.PostsRepository;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Component
public class StrategyGetPosts implements StrategyGetBehaviour<DhPromotions> {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    PostsRepository postsRepository;

    @Override
    public Response<DhPromotions> get(String language, HashMap<String, Object> params) {
        if (params == null) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS), new ArrayList<>());
        }
        Set<String> keySet = params.keySet();
        if (keySet.contains(AppConstants.KEY_PAGE) && keySet.contains(AppConstants.KEY_SIZE) && keySet.contains(AppConstants.KEY_PLACE_ID)) {
            int page = (int) params.get(AppConstants.KEY_PAGE);
            int size = (int) params.get(AppConstants.KEY_SIZE);
            String placeId = (String) params.get(AppConstants.KEY_PLACE_ID);
            return getPaginatedPostsByPlaceId(language, page, size, placeId);
        } else if (keySet.contains(AppConstants.KEY_PAGE) && keySet.contains(AppConstants.KEY_SIZE)) {
            int page = (int) params.get(AppConstants.KEY_PAGE);
            int size = (int) params.get(AppConstants.KEY_SIZE);
            return getPaginatedPosts(language, page, size);
        } else {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS), new ArrayList<>());
        }
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.GetPostStrategy;
    }

    public Response<DhPromotions> getPaginatedPosts(String language, int page, int size) {
        PageRequest paging = PageRequest.of(page, size);
        Page<DhPromotions> dhPostPages = postsRepository.findAllByStatus(AppConstants.STATUS_ACTIVE, paging);
        List<DhPromotions> dhPromotionsList = dhPostPages.getContent();
        for (DhPromotions d : dhPromotionsList) {

            if (!Utility.isFieldEmpty(d.getOfferStartTime()) && !Utility.isFieldEmpty(d.getOfferEndTime())) {
                String offerMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_OFFER_MSG);
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

    public Response<DhPromotions> getPaginatedPostsByPlaceId(String language, int page, int size, String placeId) {
        LOGGER.info("PostsServiceImpl->getPaginatedPostsByPlaceId : placeId=" + placeId + " page=" + page + " size=" + size);

        List<String> missingFieldsList = new ArrayList<>();
        if (Utility.isFieldEmpty(placeId)) missingFieldsList.add("PlaceId");
        if (missingFieldsList.size() > 0) {
            String resMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY);
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
