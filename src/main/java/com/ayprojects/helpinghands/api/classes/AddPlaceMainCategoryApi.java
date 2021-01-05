package com.ayprojects.helpinghands.api.classes;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.behaviours.AddBehaviour;
import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.models.PlaceSubCategories;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.CalendarOperations;
import com.ayprojects.helpinghands.util.tools.Utility;
import com.mongodb.lang.NonNull;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Collections;

import static com.ayprojects.helpinghands.AppConstants.DEFAULT_NAME;
import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

public class AddPlaceMainCategoryApi implements AddBehaviour<DhPlaceCategories> {
    @Override
    public Response<DhPlaceCategories> add(String language, MongoTemplate mongoTemplate, DhPlaceCategories dhPlaceCategories) {
        if (dhPlaceCategories == null) {
            return new Response<DhPlaceCategories>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language), new ArrayList<>());
        }
        Response<DhPlaceCategories> returnResponse = validatePlaceMainCategory(mongoTemplate, language, dhPlaceCategories);
        LOGGER.info("returnResponse=>" + returnResponse.getMessage());
        if (returnResponse.getStatus()) {
            CalendarOperations calendarOperations = new CalendarOperations();
            dhPlaceCategories = (DhPlaceCategories) ApiOperations.setCommonAttrs(dhPlaceCategories, AppConstants.STATUS_PENDING);
            dhPlaceCategories.setPlaceCategoryId(AppConstants.MAIN_PLACE_INITIAL_ID + calendarOperations.getTimeAtFileEnd());
            if (dhPlaceCategories.getPlaceSubCategories() != null && dhPlaceCategories.getPlaceSubCategories().size() > 0) {
                int counter = 1;
                for (PlaceSubCategories placeSubCategories : dhPlaceCategories.getPlaceSubCategories()) {
                    if (Utility.isFieldEmpty(placeSubCategories.getDefaultName())) {
                        return new Response<DhPlaceCategories>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_PLACE_CATEGORY_NAMES_EMPTY, language), new ArrayList<>(), 0);
                    }
                    placeSubCategories.setAddedBy(dhPlaceCategories.getAddedBy());
                    placeSubCategories.setPlaceSubCategoryId(AppConstants.SUB_PLACE_INITIAL_ID + "_" + counter + calendarOperations.getTimeAtFileEnd());
                    placeSubCategories = (PlaceSubCategories) ApiOperations.setCommonAttrs(placeSubCategories, AppConstants.STATUS_PENDING);
                    counter++;
                }
            }
            returnResponse.setLogActionMsg(AppConstants.ACTION_NEW_PLACE_CATEGORY_ADDED);
            returnResponse.setData(Collections.singletonList(dhPlaceCategories));
            mongoTemplate.save(dhPlaceCategories);
        }
        LOGGER.info("addPlaceMainCategory->" + returnResponse.getMessage());
        return returnResponse;
    }

    public DhPlaceCategories getDhPlaceCategoryByDefaultName(String defaultName, @NonNull MongoTemplate mongoTemplate) {
        if (Utility.isFieldEmpty(defaultName))
            throw new IllegalArgumentException("Default name must not be empty !");
        Query query = new Query(Criteria.where(DEFAULT_NAME).regex(defaultName, "i"));
        return mongoTemplate.findOne(query, DhPlaceCategories.class);
    }

    public Response<DhPlaceCategories> validatePlaceMainCategory(@NonNull MongoTemplate mongoTemplate, String language, DhPlaceCategories dhPlaceCategories) {
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
            DhPlaceCategories existingDhPlaceCategories = getDhPlaceCategoryByDefaultName(dhPlaceCategories.getDefaultName(), mongoTemplate);
            if (existingDhPlaceCategories != null) {
                return new Response<DhPlaceCategories>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_CATEGORY_ALREADY_EXISTS, language), new ArrayList<>(), 0);
            }
        } catch (IllegalArgumentException e) {
            LOGGER.info("validatePlaceCategory->catch:" + e.getMessage());
            return new Response<DhPlaceCategories>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_SOMETHING_WENT_WRONG, language), new ArrayList<>(), 0);
        }

        return new Response<>(true, 201, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NEW_PLACE_CATEGORY_ADDED, language), Collections.singletonList(dhPlaceCategories));
    }
}
