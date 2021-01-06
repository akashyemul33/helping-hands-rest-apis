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
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Component
public class StrategyAddPlaceSubCategoryApi implements StrategyAddBehaviour<PlaceSubCategories> {
    @Autowired
    PlaceCategoryDao placeCategoryDao;
    @Autowired
    private MongoOperations mongoTemplate;


    @Override
    public Response<PlaceSubCategories> add(String language, PlaceSubCategories placeSubCategory) {
        Response<PlaceSubCategories> returnResponse = validateAddPlaceSubCategory(language, placeSubCategory);
        if (returnResponse.getStatus()) {

            Optional<DhPlaceCategories> queriedDhPlaceCategories = getMainCategory(placeSubCategory.getPlaceMainCategoryId());
            if (!queriedDhPlaceCategories.isPresent()) {
                return new Response<PlaceSubCategories>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NOT_FOUND_PLACECATEGORIY_WITH_ID, language) + "ID : " + placeSubCategory.getPlaceMainCategoryId(), new ArrayList<>(), 0);
            }
            DhPlaceCategories mainPlaceCategory = queriedDhPlaceCategories.get();
            boolean isSubCategoryAlreadyExists = checkWhetherSubCategoryAlreadyExists(mainPlaceCategory.getPlaceSubCategories(), placeSubCategory.getDefaultName());
            if (isSubCategoryAlreadyExists) {
                return new Response<PlaceSubCategories>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_CATEGORY_ALREADY_EXISTS, language), new ArrayList<>(), 0);
            }
            placeSubCategory.setAddedBy(placeSubCategory.getAddedBy());
            CalendarOperations calendarOperations = new CalendarOperations();
            placeSubCategory.setPlaceSubCategoryId(AppConstants.SUB_PLACE_INITIAL_ID + calendarOperations.getTimeAtFileEnd());
            placeSubCategory = (PlaceSubCategories) ApiOperations.setCommonAttrs(placeSubCategory, AppConstants.STATUS_PENDING);
            mainPlaceCategory.getPlaceSubCategories().add(placeSubCategory);

            updateMainCategory(placeSubCategory);
            returnResponse.setLogActionMsg("New sub category [" + placeSubCategory.getDefaultName() + "] has been added under [" + mainPlaceCategory.getDefaultName() + "].");
        }
        LOGGER.info("addPlaceSubCategory->" + returnResponse.getMessage());
        return new Response<PlaceSubCategories>(true, 201, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NEW_PLACESUBCATEGORY_ADDED, language), Collections.singletonList(placeSubCategory), 1);
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.AddPlaceSubCategoryStrategy;
    }

    private void updateMainCategory(PlaceSubCategories placeSubCategory) {
        CalendarOperations calendarOperations = new CalendarOperations();
        Update mainCategoryUpdate = new Update();
        mainCategoryUpdate.push(AppConstants.PLACE_SUB_CATEGORIES, placeSubCategory);
        mainCategoryUpdate.set(AppConstants.MODIFIED_DATE_TIME, calendarOperations.currentDateTimeInUTC());
        Query queryFindCategoryWithId = new Query(Criteria.where(AppConstants.PLACE_MAIN_CATEGORY_ID).is(placeSubCategory.getPlaceMainCategoryId()));
        mongoTemplate.updateFirst(queryFindCategoryWithId, mainCategoryUpdate, DhPlaceCategories.class);
    }

    private Optional<DhPlaceCategories> getMainCategory(String mainCategoryId) {
        return placeCategoryDao.findByPlaceCategoryId(mainCategoryId);
    }

    private boolean checkWhetherSubCategoryAlreadyExists(List<PlaceSubCategories> placeSubCategories, String defaultName) {
        if (placeSubCategories != null) {
            for (PlaceSubCategories ps : placeSubCategories) {
                if (ps != null && defaultName.equalsIgnoreCase(ps.getDefaultName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public Response<PlaceSubCategories> validateAddPlaceSubCategory(String language, PlaceSubCategories placeSubCategories) {
        if (placeSubCategories == null)
            return new Response<PlaceSubCategories>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());

        if (Utility.isFieldEmpty(placeSubCategories.getPlaceMainCategoryId()))
            return new Response<PlaceSubCategories>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MAINCATEGORYID_MISSING), new ArrayList<>());

        if (Utility.isFieldEmpty(placeSubCategories.getDefaultName())) {
            return new Response<PlaceSubCategories>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_DEFAULTNAME), new ArrayList<>());
        }
        return new Response<PlaceSubCategories>(true, 201, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NEW_PLACESUBCATEGORY_ADDED), Collections.singletonList(placeSubCategories), 1);
    }
}
