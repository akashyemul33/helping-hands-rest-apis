package com.ayprojects.helpinghands.api.classes.add_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyAddBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhHHPriorities;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.common_service.CommonService;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Component
public class StrategyAddHhPriorityApi implements StrategyAddBehaviour<DhHHPriorities> {

    @Autowired
    CommonService commonService;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<DhHHPriorities> add(String language, DhHHPriorities dhHHCategory) throws ServerSideException {
        dhHHCategory = setCategoryIdIfNotExists(dhHHCategory);
        Response<DhHHPriorities> validationResponse = validateAddHhPriorities(language, dhHHCategory);
        if (!validationResponse.getStatus())
            return validationResponse;

        dhHHCategory = (DhHHPriorities) Utility.setCommonAttrs(dhHHCategory, AppConstants.STATUS_ACTIVE);
        mongoTemplate.save(dhHHCategory, AppConstants.COLLECTION_DH_HH_PRIORITY);
        return validationResponse;
    }

    @Override
    public Response<DhHHPriorities> add(String language, DhHHPriorities obj, HashMap<String, Object> params) throws ServerSideException {
        return null;
    }

    public DhHHPriorities setCategoryIdIfNotExists(DhHHPriorities dhHHPriorities) {
        if (dhHHPriorities != null && Utility.isFieldEmpty(dhHHPriorities.getPriorityId())) {
            dhHHPriorities.setPriorityId(Utility.getUUID());
        }
        return dhHHPriorities;
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.AddHHPriorityStrategy;
    }

    public Response<DhHHPriorities> validateAddHhPriorities(String language, DhHHPriorities dhHHPost) {
        if (dhHHPost == null)
            return new Response<DhHHPriorities>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>(), 0);

        List<String> missingFieldsList = new ArrayList<>();
        if (Utility.isFieldEmpty(dhHHPost.getPriorityId()))
            missingFieldsList.add(AppConstants.KEY_HH_PRIORITY_ID);
        if (Utility.isFieldEmpty(dhHHPost.getPriorityName()))
            missingFieldsList.add(AppConstants.KEY_HH_PRIORITY_NAME);
        if (Utility.isFieldEmpty(dhHHPost.getAddedBy()))
            missingFieldsList.add(AppConstants.ADDED_BY);

        if (missingFieldsList.size() > 0) {
            String resMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY);
            resMsg = resMsg + " , these fields are missing : " + missingFieldsList;
            LOGGER.info("missingfields=>" + resMsg);
            return new Response<DhHHPriorities>(false, 402, resMsg, new ArrayList<>(), 0);
        }

        if (!commonService.checkUserExistence(dhHHPost.getAddedBy())) {
            return new Response<DhHHPriorities>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_USER_NOT_FOUND_WITH_USERID), new ArrayList<>(), 0);
        }

        Query queryGetCategories = new Query(Criteria.where(AppConstants.KEY_HH_PRIORITY_NAME).regex(dhHHPost.getPriorityName(), "i"));
        DhHHPriorities category = mongoTemplate.findOne(queryGetCategories, DhHHPriorities.class);
        if (category != null)
            return new Response<DhHHPriorities>(false, 402, "Priority already exists with given name !", new ArrayList<>(), 0);
        return new Response<DhHHPriorities>(true, 201, "Validated", new ArrayList<>(), 0);
    }
}
