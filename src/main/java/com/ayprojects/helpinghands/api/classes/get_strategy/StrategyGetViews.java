package com.ayprojects.helpinghands.api.classes.get_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyGetBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.models.DhViews;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Component
public class StrategyGetViews implements StrategyGetBehaviour<DhViews> {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<DhViews> get(String language, HashMap<String, Object> params) {
        if (params == null) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS), new ArrayList<>());
        }
        Set<String> keySet = params.keySet();
        String contentId = (String) params.get(AppConstants.KEY_CONTENT_ID);
        String contentType = (String) params.get(AppConstants.KEY_CONTENT_TYPE);
        Response<DhViews> response = validate(contentId, contentType, language);
        if (!response.getStatus())
            return response;
        if (keySet.contains(AppConstants.STATUS) &&
                keySet.contains(AppConstants.KEY_PAGE) &&
                keySet.contains(AppConstants.KEY_SIZE)
        ) {
            String status = (String) params.get(AppConstants.STATUS);
            int page = (int) params.get(AppConstants.KEY_PAGE);
            int size = (int) params.get(AppConstants.KEY_SIZE);
            return getPaginatedViews(language, contentId, contentType, status, page, size);
        } else {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS), new ArrayList<>());
        }
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.GetViewsStrategy;
    }

    private Response<DhViews> validate(String contentId, String contentType, String language) {
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

    public Response<DhViews> getPaginatedViews(String language, String contentId, String contentType, String status, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Criteria criteria = new Criteria();
        criteria.and(AppConstants.CONTENT_ID).is(contentId);
        criteria.and(AppConstants.CONTENT_TYPE).is(contentType);
        criteria.and(AppConstants.STATUS).regex(status, "i");
        Query queryGetViews = new Query(criteria).with(pageable);
        queryGetViews.with(Sort.by(Sort.Direction.DESC, AppConstants.MODIFIED_DATE_TIME));
        List<DhViews> dhViews = mongoTemplate.find(queryGetViews, DhViews.class);
        if (dhViews.size() == 0)
            return new Response<DhViews>(true, 200, "Query successful", 0, page, 0, (long) 0, new ArrayList<>());

        Page<DhViews> ratingAndCommentsPage = PageableExecutionUtils.getPage(
                dhViews,
                pageable,
                () -> mongoTemplate.count(Query.of(queryGetViews).limit(-1).skip(-1), DhViews.class));
        return new Response<>(true, 200, "Query successful", ratingAndCommentsPage.getNumberOfElements(), ratingAndCommentsPage.getNumber(), ratingAndCommentsPage.getTotalPages(), ratingAndCommentsPage.getTotalElements(), ratingAndCommentsPage.getContent());

    }
}
