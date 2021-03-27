package com.ayprojects.helpinghands.api.classes.add_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.behaviours.StrategyAddBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.models.PlaceMainPage;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.common_service.CommonService;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.ayprojects.helpinghands.AppConstants.COLLECTION_PLACE_MAIN_PAGE;
import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Component
public class StrategyAddPlaceMainPagesApi implements StrategyAddBehaviour<PlaceMainPage> {

    @Autowired
    private CommonService commonService;

    @Autowired
    private MongoOperations mongoTemplate;

    @Override
    public Response<PlaceMainPage> add(String language, PlaceMainPage placeMainPage) {
        placeMainPage = setIdIfNotExists(placeMainPage);

        Response<PlaceMainPage> validationResponse = validateAddPlaceMainPage(language, placeMainPage);
        if (!validationResponse.getStatus())
            return validationResponse;

        placeMainPage = (PlaceMainPage) ApiOperations.setCommonAttrs(placeMainPage, AppConstants.STATUS_PENDING);

        mongoTemplate.save(placeMainPage, COLLECTION_PLACE_MAIN_PAGE);
        validationResponse.setLogActionMsg("New [" + placeMainPage.getGridType().name() + "]  has been added in status " + placeMainPage.getStatus());
        return new Response<>(true, 201, "Place main page has been added.", new ArrayList<>());
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.AddPlaceMainPageStrategy;
    }

    public PlaceMainPage setIdIfNotExists(PlaceMainPage placeMainPage) {
        if (placeMainPage != null && Utility.isFieldEmpty(placeMainPage.getPlaceMainPageId())) {
            placeMainPage.setPlaceMainPageId(Utility.getUUID());
        }
        return placeMainPage;
    }

    public Response<PlaceMainPage> validateAddPlaceMainPage(String language, PlaceMainPage placeMainPage) {
        if (placeMainPage == null) {
            return new Response<PlaceMainPage>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>(), 0);
        }

        List<String> missingFieldsList = new ArrayList<>();

        //TODO Do not use UUID, use some incrementable value for better usage at app side
        if (Utility.isFieldEmpty(placeMainPage.getPlaceMainPageId()))
            missingFieldsList.add(AppConstants.PLACE_MAIN_PAGE_ID);
        if (placeMainPage.getGridType() == null)
            missingFieldsList.add(AppConstants.PLACE_MAIN_GRID_TYPE);

        if (!missingFieldsList.isEmpty()) {
            String resMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY);
            resMsg = resMsg + " , these fields are missing : " + missingFieldsList;
            LOGGER.info("missingfields=>" + resMsg);
            return new Response<PlaceMainPage>(false, 402, resMsg, new ArrayList<>(), 0);
        }
        return new Response<PlaceMainPage>(true, 201, "Validated", new ArrayList<>(), 0);
    }
}
