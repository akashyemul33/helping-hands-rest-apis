package com.ayprojects.helpinghands.api.classes.add_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyAddBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhHHCategory;
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
public class StrategyAddHhCategoryApi implements StrategyAddBehaviour<DhHHCategory> {

    @Autowired
    CommonService commonService;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<DhHHCategory> add(String language, DhHHCategory dhHHCategory) throws ServerSideException {
        dhHHCategory = setCategoryIdIfNotExists(dhHHCategory);
        Response<DhHHCategory> validationResponse = validateAddHhCategory(language, dhHHCategory);
        if (!validationResponse.getStatus())
            return validationResponse;

        dhHHCategory = (DhHHCategory) Utility.setCommonAttrs(dhHHCategory, AppConstants.STATUS_ACTIVE);
        mongoTemplate.save(dhHHCategory, AppConstants.COLLECTION_DH_HH_CATEGORY);
        return validationResponse;
    }

    @Override
    public Response<DhHHCategory> add(String language, DhHHCategory obj, HashMap<String, Object> params) throws ServerSideException {
        return null;
    }

    public DhHHCategory setCategoryIdIfNotExists(DhHHCategory dhHHCategory) {
        if (dhHHCategory != null && Utility.isFieldEmpty(dhHHCategory.getCategoryId())) {
            dhHHCategory.setCategoryId(Utility.getUUID());
        }
        return dhHHCategory;
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.AddHHCategoryStrategy;
    }

    public Response<DhHHCategory> validateAddHhCategory(String language, DhHHCategory dhHHPost) {
        if (dhHHPost == null)
            return new Response<DhHHCategory>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>(), 0);

        List<String> missingFieldsList = new ArrayList<>();
        if (Utility.isFieldEmpty(dhHHPost.getCategoryId()))
            missingFieldsList.add(AppConstants.KEY_HH_CATEGORY_ID);
        if (Utility.isFieldEmpty(dhHHPost.getCategoryName()))
            missingFieldsList.add(AppConstants.KEY_HH_CATEGORY_NAME);


        if (missingFieldsList.size() > 0) {
            String resMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY);
            resMsg = resMsg + " , these fields are missing : " + missingFieldsList;
            LOGGER.info("missingfields=>" + resMsg);
            return new Response<DhHHCategory>(false, 402, resMsg, new ArrayList<>(), 0);
        }


        Query queryGetCategories = new Query(Criteria.where(AppConstants.KEY_HH_CATEGORY_NAME).regex(dhHHPost.getCategoryName(), "i"));
        DhHHCategory category = mongoTemplate.findOne(queryGetCategories, DhHHCategory.class);
        if (category != null)
            return new Response<DhHHCategory>(false, 402, "Category already exists with given name !", new ArrayList<>(), 0);
        return new Response<DhHHCategory>(true, 201, "Validated", new ArrayList<>(), 0);
    }
}
