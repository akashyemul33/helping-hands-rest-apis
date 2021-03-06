package com.ayprojects.helpinghands.api.classes.get_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyGetBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlaceMainPage;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;

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
public class StrategyGetPlaceMainPages implements StrategyGetBehaviour<DhPlaceMainPage> {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<DhPlaceMainPage> get(String language, HashMap<String, Object> params) throws ServerSideException {
        if (params == null) {
            return new Response<DhPlaceMainPage>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS), new ArrayList<>());
        }

        Set<String> keySet = params.keySet();

        if (keySet.contains(AppConstants.KEY_PAGE)
                && keySet.contains(AppConstants.KEY_SIZE)) {
            try {
                int page = (int) params.get(AppConstants.KEY_PAGE);
                int size = (int) params.get(AppConstants.KEY_SIZE);
                return getPaginatedPlaceMainPages(language, page, size);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServerSideException(ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_SOMETHING_WENT_WRONG));
            }
        }
        throw new ServerSideException("No matching get method found in " + getStrategyName());
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.GetPlaceMainPageStrategy;
    }

    public Response<DhPlaceMainPage> getPaginatedPlaceMainPages(String language, int page, int size) {
        LOGGER.info("getPaginatedPlaceMainPages->page=" + page + " size=" + size);

        Pageable pageable = PageRequest.of(page, size);

        Query query = new Query(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        List<DhPlaceMainPage> dhPlaceMainPageList = mongoTemplate.find(query.with(pageable), DhPlaceMainPage.class);
        LOGGER.info("placeMainPageList=" + dhPlaceMainPageList.size());
        Page<DhPlaceMainPage> placeMainPages = PageableExecutionUtils.getPage(
                dhPlaceMainPageList,
                pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), DhPlaceMainPage.class, AppConstants.COLLECTION_DH_PLACE_MAIN_PAGE));

        LOGGER.info("getPaginatedPlaceMainPages->placeMainPages,total elements=" + placeMainPages.getTotalElements());
        return new Response<DhPlaceMainPage>(true, 200, "Query successful", placeMainPages.getNumberOfElements(), placeMainPages.getNumber(), placeMainPages.getTotalPages(), (long) placeMainPages.getTotalElements(), placeMainPages.getContent());
    }
}
