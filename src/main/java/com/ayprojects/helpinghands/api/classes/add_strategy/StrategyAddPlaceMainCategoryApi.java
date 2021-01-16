package com.ayprojects.helpinghands.api.classes.add_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.behaviours.StrategyAddBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.dao.placecategories.PlaceCategoryDao;
import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.models.PlaceSubCategories;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.CalendarOperations;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Component
public class StrategyAddPlaceMainCategoryApi implements StrategyAddBehaviour<DhPlaceCategories> {
    @Autowired
    PlaceCategoryDao placeCategoryDao;

    @Override
    public Response<DhPlaceCategories> add(String language, DhPlaceCategories dhPlaceCategories) {
        if (dhPlaceCategories == null) {
            return new Response<DhPlaceCategories>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        }
        Response<DhPlaceCategories> returnResponse = validatePlaceMainCategory(language, dhPlaceCategories);
        LOGGER.info("returnResponse=>" + returnResponse.getMessage());
        if (returnResponse.getStatus()) {
            CalendarOperations calendarOperations = new CalendarOperations();
            dhPlaceCategories = (DhPlaceCategories) ApiOperations.setCommonAttrs(dhPlaceCategories, AppConstants.STATUS_PENDING);
            dhPlaceCategories.setPlaceCategoryId(AppConstants.MAIN_PLACE_INITIAL_ID + calendarOperations.getTimeAtFileEnd());
            if (dhPlaceCategories.getPlaceSubCategories() != null && dhPlaceCategories.getPlaceSubCategories().size() > 0) {
                int counter = 1;
                for (PlaceSubCategories placeSubCategories : dhPlaceCategories.getPlaceSubCategories()) {
                    if (Utility.isFieldEmpty(placeSubCategories.getDefaultName())) {
                        return new Response<DhPlaceCategories>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_PLACE_CATEGORY_NAMES_EMPTY), new ArrayList<>(), 0);
                    }
                    placeSubCategories.setAddedBy(dhPlaceCategories.getAddedBy());
                    placeSubCategories.setPlaceSubCategoryId(AppConstants.SUB_PLACE_INITIAL_ID + "_" + counter + calendarOperations.getTimeAtFileEnd());
                    String status = placeSubCategories.getStatus();
                    if(Utility.isFieldEmpty(status)){
                        status = AppConstants.STATUS_PENDING;
                    }
                    placeSubCategories = (PlaceSubCategories) ApiOperations.setCommonAttrs(placeSubCategories, status);
                    counter++;
                }
            }
            returnResponse.setLogActionMsg(AppConstants.ACTION_NEW_PLACE_CATEGORY_ADDED);
            returnResponse.setData(Collections.singletonList(dhPlaceCategories));
            placeCategoryDao.add(dhPlaceCategories);
        }
        LOGGER.info("addPlaceMainCategory->" + returnResponse.getMessage());
        return returnResponse;
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.AddPlaceMainCategoryStrategy;
    }

    public Response<DhPlaceCategories> validatePlaceMainCategory(String language, DhPlaceCategories dhPlaceCategories) {
        if (dhPlaceCategories == null) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        }

        if (Utility.isFieldEmpty(dhPlaceCategories.getTypeOfPlaceCategory())) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_TYPEOFMAINPLACECATEGORY), new ArrayList<>());
        }

        if (Utility.isFieldEmpty(dhPlaceCategories.getAddedBy())) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_USER_ID_IS_MISSING), new ArrayList<>());
        }

        if (Utility.isFieldEmpty(dhPlaceCategories.getDefaultName())) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_DEFAULTNAME), new ArrayList<>());
        }

        try {

            Optional<DhPlaceCategories> queriedDhPlaceCategory = placeCategoryDao.findByDefaultName(dhPlaceCategories.getDefaultName());
            if (queriedDhPlaceCategory.isPresent()) {
                return new Response<DhPlaceCategories>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_CATEGORY_ALREADY_EXISTS), new ArrayList<>(), 0);
            }
        } catch (IllegalArgumentException e) {
            LOGGER.info("validatePlaceCategory->catch:" + e.getMessage());
            return new Response<DhPlaceCategories>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_SOMETHING_WENT_WRONG), new ArrayList<>(), 0);
        }

        return new Response<>(true, 201, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NEW_PLACE_CATEGORY_ADDED), Collections.singletonList(dhPlaceCategories));
    }
}
