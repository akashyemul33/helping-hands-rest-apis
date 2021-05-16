package com.ayprojects.helpinghands.api.classes.get_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyGetBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.models.DhRatingAndComments;
import com.ayprojects.helpinghands.models.Response;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Component
public class StrategyGetRatingAndComments implements StrategyGetBehaviour<DhRatingAndComments> {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<DhRatingAndComments> get(String language, HashMap<String, Object> params) {
        if (params == null) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS), new ArrayList<>());
        }
        Set<String> keySet = params.keySet();
        String contentId = (String) params.get(AppConstants.KEY_CONTENT_ID);
        String contentType = (String) params.get(AppConstants.KEY_CONTENT_TYPE);
        Response<DhRatingAndComments> response = validate(contentId, contentType, language);
        if (!response.getStatus())
            return response;
        if (keySet.contains(AppConstants.RATING_API_TYPE) && params.get(AppConstants.RATING_API_TYPE).equals(AppConstants.TOTAL_RATING)) {
            return getTotalRatingAndComments(contentId, contentType, language);
        } else if (keySet.contains(AppConstants.STATUS) &&
                keySet.contains(AppConstants.KEY_PAGE) &&
                keySet.contains(AppConstants.KEY_SIZE)
        ) {
            String status = (String) params.get(AppConstants.STATUS);
            int page = (int) params.get(AppConstants.KEY_PAGE);
            int size = (int) params.get(AppConstants.KEY_SIZE);
            return getPaginatedRatingAndComments(language, contentId, contentType, status, page, size);
        } else {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS), new ArrayList<>());
        }

    }

    private Response<DhRatingAndComments> validate(String contentId, String contentType, String language) {
        List<String> missingFieldsList = new ArrayList<>();
        if (Utility.isFieldEmpty(contentId)) missingFieldsList.add("ContentId");
        if (Utility.isFieldEmpty(contentType)) missingFieldsList.add("ContentType");
        if (missingFieldsList.size() > 0) {
            String resMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY);
            resMsg = resMsg + " , these fields are missing : " + missingFieldsList;
            return new Response<>(false, 402, resMsg, new ArrayList<>(), 0);
        }
        return new Response<>(true, 200, "Validated", new ArrayList<>(), 0);
    }

    private Response<DhRatingAndComments> getTotalRatingAndComments(String contentId, String contentType, String language) {
        Criteria criteria = new Criteria();
        criteria.and(AppConstants.CONTENT_ID).regex(contentId, "i");
        criteria.and(AppConstants.CONTENT_TYPE).regex(contentType, "i");
        criteria.and(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i");
        Query queryGetRC = new Query(criteria);
        List<DhRatingAndComments> dhRatingCommentsList = mongoTemplate.find(queryGetRC, DhRatingAndComments.class);
        float avgRating = 0;
        int fiveStar = 0;
        int fourStar = 0;
        int thirdStar = 0;
        int twoStar = 0;
        int oneStar = 0;
        for (DhRatingAndComments dh : dhRatingCommentsList) {
            avgRating += dh.getRating();
            if (dh.getRating() > 4)
                fiveStar++;
            else if (dh.getRating() > 3)
                fourStar++;
            else if (dh.getRating() > 2)
                thirdStar++;
            else if (dh.getRating() > 1)
                twoStar++;
            else
                oneStar++;
        }
        avgRating = avgRating / dhRatingCommentsList.size();
        DhRatingAndComments dhRatingAndComments = new DhRatingAndComments();
        dhRatingAndComments.setTotalRating(avgRating);
        dhRatingAndComments.setNumberOfFiveStars(fiveStar);
        dhRatingAndComments.setNumberOfFourStars(fourStar);
        dhRatingAndComments.setNumberOfThreeStars(thirdStar);
        dhRatingAndComments.setNumberOfTwoStars(twoStar);
        dhRatingAndComments.setNumberOfOneStars(oneStar);
        return new Response<>(true, 200, "Query successful", Collections.singletonList(dhRatingAndComments));
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.GetRatingStrategy;
    }

    public Response<DhRatingAndComments> getPaginatedRatingAndComments(String language, String contentId, String contentType, String status, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Criteria criteria = new Criteria();
        criteria.and(AppConstants.CONTENT_ID).regex(contentId, "i");
        criteria.and(AppConstants.CONTENT_TYPE).regex(contentType, "i");
        criteria.and(AppConstants.STATUS).regex(status, "i");
        Query queryGetRC = new Query(criteria).with(pageable);
        List<DhRatingAndComments> dhRatingCommentsList = mongoTemplate.find(queryGetRC, DhRatingAndComments.class);
        Page<DhRatingAndComments> ratingAndCommentsPage = PageableExecutionUtils.getPage(
                dhRatingCommentsList,
                pageable,
                () -> mongoTemplate.count(Query.of(queryGetRC).limit(-1).skip(-1), DhRatingAndComments.class));
        return new Response<>(true, 200, "Query successful", ratingAndCommentsPage.getNumberOfElements(), ratingAndCommentsPage.getNumber(), ratingAndCommentsPage.getTotalPages(), ratingAndCommentsPage.getTotalElements(), ratingAndCommentsPage.getContent());
    }
}
