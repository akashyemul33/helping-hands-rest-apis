package com.ayprojects.helpinghands.api.classes.get_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyGetBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhComments;
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
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Component
public class StrategyGetComments implements StrategyGetBehaviour<DhComments> {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<DhComments> get(String language, HashMap<String, Object> params) throws ServerSideException {
        if (params == null) {
            return new Response<DhComments>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS), new ArrayList<>());
        }
        Set<String> keySet = params.keySet();
        if (keySet.contains(AppConstants.KEY_PAGE) && keySet.contains(AppConstants.KEY_SIZE) && keySet.contains(AppConstants.KEY_CONTENT_ID)) {
            int page = (int) params.get(AppConstants.KEY_PAGE);
            int size = (int) params.get(AppConstants.KEY_SIZE);
            String contentId = (String) params.get(AppConstants.KEY_CONTENT_ID);
            return getPaginatedComments(language, page, size, contentId);
        }
        return new Response<DhComments>(false, 402, "No matching method found to process the api call !", new ArrayList<>());
    }

    public Response<DhComments> getPaginatedComments(String language, int page, int size, String contentId) {
        if (Utility.isFieldEmpty(contentId))
            return new Response<DhComments>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());

        Pageable pageable = PageRequest.of(page, size);

        Query query = new Query(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        query.addCriteria(Criteria.where(AppConstants.KEY_CONTENT_ID).is(contentId));

        List<DhComments> dhComments = mongoTemplate.find(query.with(pageable), DhComments.class);
        Page<DhComments> dhCommentsPage = PageableExecutionUtils.getPage(
                dhComments, pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), DhComments.class, AppConstants.COLLECTION_DH_COMMENTS));
        LOGGER.info("getPaginatedComments->total elements=" + dhCommentsPage.getTotalElements());
        return new Response<DhComments>(true, 200, "Query successful", dhCommentsPage.getNumberOfElements(), dhCommentsPage.getNumber(), dhCommentsPage.getTotalPages(), (long) dhCommentsPage.getTotalElements(), dhCommentsPage.getContent());
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.GetDhCommentsStrategy;
    }
}
