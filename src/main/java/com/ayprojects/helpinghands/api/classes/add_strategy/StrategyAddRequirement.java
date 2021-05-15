package com.ayprojects.helpinghands.api.classes.add_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.behaviours.StrategyAddBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhRequirements;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.tools.Utility;
import com.ayprojects.helpinghands.util.tools.Validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

public class StrategyAddRequirement implements StrategyAddBehaviour<DhRequirements> {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<DhRequirements> add(String language, DhRequirements dhRequirements) {
        if (dhRequirements == null) {
            return new Response<DhRequirements>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language), new ArrayList<>(), 0);
        }


        //fields check against empty
        List<String> missingFieldsList = Validations.findMissingFieldsForRequirements(dhRequirements);
        if (missingFieldsList.size() > 0) {
            String resMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language);
            resMsg = resMsg + " , these fields are missing : " + missingFieldsList;
            return new Response<DhRequirements>(false, 402, resMsg, new ArrayList<>(), 0);
        }

        dhRequirements = (DhRequirements) ApiOperations.setCommonAttrs(dhRequirements, AppConstants.STATUS_ACTIVE);
        mongoTemplate.save(dhRequirements, AppConstants.COLLECTION_DH_REQUIREMENTS);
        Response<DhRequirements> returnResponse = new Response<>(true, 201, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NEW_REQUIREMENT_ADDED, language), new ArrayList<>(), 1);
        returnResponse.setLogActionMsg("New [" + dhRequirements.getRequirementType() + "]  has been added.");
        return new Response<>(true, 201, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NEW_REQUIREMENT_ADDED, language), new ArrayList<>(), 1);

    }

    @Override
    public Response<DhRequirements> add(String language, DhRequirements obj, HashMap<String, Object> params) throws ServerSideException {
        return null;
    }

    @Override
    public StrategyName getStrategyName() {
        return null;
    }
}
