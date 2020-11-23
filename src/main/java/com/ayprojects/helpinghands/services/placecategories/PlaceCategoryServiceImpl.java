package com.ayprojects.helpinghands.services.placecategories;


import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.dao.placecategories.PlaceCategoryDao;
import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.models.PlaceSubCategories;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.security.UserDetailsServiceImpl;
import com.ayprojects.helpinghands.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class PlaceCategoryServiceImpl implements PlaceCategoryService {

    @Autowired
    PlaceCategoryDao placeCategoryDao;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    Utility utility;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<DhPlaceCategories> addPlaceMainCategory(Authentication authentication, HttpHeaders httpHeaders, DhPlaceCategories dhPlaceCategories, String version) {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("PlaceCategoryServiceImpl->add : language=" + language);
        if (dhPlaceCategories == null || dhPlaceCategories.getPlaceCategoryName() == null) {
            return new Response<DhPlaceCategories>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY_OR_PLACECATEGORYNAMES, language), new ArrayList<>(), 0);
        }

        if (Utility.isFieldEmpty(dhPlaceCategories.getPlaceCategoryName().getPlacecategorynameInEnglish())) {
            return new Response<DhPlaceCategories>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_PLACE_CATEGORY_NAMES_EMPTY, language), new ArrayList<>(), 0);
        }

        String categoryNameInEnglish = dhPlaceCategories.getPlaceCategoryName().getPlacecategorynameInEnglish();
        Optional<DhPlaceCategories> existingDhPlaceCategories = placeCategoryDao.findByPlaceCategoryNamePlacecategorynameInEnglish(categoryNameInEnglish);
        if (existingDhPlaceCategories.isPresent()) {
            return new Response<DhPlaceCategories>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_CATEGORY_ALREADY_EXISTS, language), new ArrayList<>(), 0);
        }

        dhPlaceCategories = (DhPlaceCategories) utility.setCommonAttrs(dhPlaceCategories, AppConstants.STATUS_PENDING);
        dhPlaceCategories.setPlaceCategoryId(AppConstants.MAIN_PLACE_INITIAL_ID + Utility.currentDateTimeInUTC(AppConstants.DATE_TIME_FORMAT_WITHOUT_UNDERSCORE));
        dhPlaceCategories.setAddedBy(dhPlaceCategories.getAddedBy());
        if (dhPlaceCategories.getPlaceSubCategories() != null && dhPlaceCategories.getPlaceSubCategories().size() > 0) {
            int counter = 1;
            for (PlaceSubCategories placeSubCategories : dhPlaceCategories.getPlaceSubCategories()) {
                if (placeSubCategories.getPlaceSubCategoryName() == null || Utility.isFieldEmpty(placeSubCategories.getPlaceSubCategoryName().getPlacesubcategorynameInEnglish())) {
                    return new Response<DhPlaceCategories>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_PLACE_CATEGORY_NAMES_EMPTY, language), new ArrayList<>(), 0);
                }
                placeSubCategories.setAddedBy(dhPlaceCategories.getAddedBy());
                placeSubCategories.setPlaceSubCategoryId(AppConstants.SUB_PLACE_INITIAL_ID + "_" + counter + Utility.currentDateTimeInUTC(AppConstants.DATE_TIME_FORMAT_WITHOUT_UNDERSCORE));
                placeSubCategories.setCreatedDateTime(Utility.currentDateTimeInUTC());
                placeSubCategories.setModifiedDateTime(Utility.currentDateTimeInUTC());
                placeSubCategories.setStatus(AppConstants.STATUS_PENDING);
                placeSubCategories.setSchemaVersion(AppConstants.SCHEMA_VERSION);
                counter++;
            }
        }
        placeCategoryDao.add(dhPlaceCategories);
        utility.addLog(authentication.getName(), AppConstants.ACTION_NEW_PLACE_CATEGORY_ADDED);
        return new Response<DhPlaceCategories>(true, 201, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NEW_PLACE_CATEGORY_ADDED, language), Collections.singletonList(dhPlaceCategories), 1);
    }

    @Override
    public Response<DhPlaceCategories> findByPlaceCategoryId(Authentication authentication, HttpHeaders httpHeaders, String placeCategoryId) {
//TODO
        return null;
    }

    @Override
    public Response<DhPlaceCategories> findByStatus(Authentication authentication, HttpHeaders httpHeaders, String status) {
        //TODO
        return null;
    }

    @Override
    public Response<DhPlaceCategories> findAllByStatus(Authentication authentication, HttpHeaders httpHeaders, String status, String version) {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("PlaceCategoryServiceImpl->findAllByStatus : language=" + language);
        String findWithStatus = Utility.isFieldEmpty(status) ? AppConstants.STATUS_ACTIVE : status;

        Query query = new Query(Criteria.where(AppConstants.STATUS).regex(findWithStatus, "i"));
        List<DhPlaceCategories> dhPlaceCategoriesList = mongoTemplate.find(query, DhPlaceCategories.class);
        if (dhPlaceCategoriesList == null) {
            return new Response<>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NO_PLACECATEGORIES, language), new ArrayList<>(), 0);
        } else {
            return new Response<DhPlaceCategories>(true, 201, dhPlaceCategoriesList.size() + " place categories found .", dhPlaceCategoriesList, dhPlaceCategoriesList.size());
        }
    }

    @Override
    public Response<PlaceSubCategories> addPlaceSubCategory(Authentication authentication, HttpHeaders httpHeaders, PlaceSubCategories placeSubCategory, String mainPlaceCategoryId, String version) {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("PlaceCategoryServiceImpl->addPlaceSubCategory : language=" + language);

        if (placeSubCategory == null || placeSubCategory.getPlaceSubCategoryName() == null || Utility.isFieldEmpty(mainPlaceCategoryId)) {
            return new Response<PlaceSubCategories>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY_OR_PLACECATEGORYNAMES, language), new ArrayList<>(), 0);
        }

        if (Utility.isFieldEmpty(placeSubCategory.getPlaceSubCategoryName().getPlacesubcategorynameInEnglish())) {
            return new Response<PlaceSubCategories>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_PLACE_CATEGORY_NAMES_EMPTY, language), new ArrayList<>(), 0);
        }

        Query queryFindCategoryWithId = new Query(Criteria.where(AppConstants.PLACE_CATEGORY_ID).is(mainPlaceCategoryId));
        queryFindCategoryWithId.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        DhPlaceCategories queriedDhPlaceCategories = mongoTemplate.findOne(queryFindCategoryWithId, DhPlaceCategories.class);
        if (queriedDhPlaceCategories == null) {
            return new Response<PlaceSubCategories>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NOT_FOUND_PLACECATEGORIY_WITH_ID, language) + "ID : " + mainPlaceCategoryId, new ArrayList<>(), 0);
        }

        List<PlaceSubCategories> placeSubCategoriesList = new ArrayList<>();
        if (queriedDhPlaceCategories.getPlaceSubCategories() != null) {
            placeSubCategoriesList = queriedDhPlaceCategories.getPlaceSubCategories();
            for (PlaceSubCategories subCategory : queriedDhPlaceCategories.getPlaceSubCategories()) {
                if (subCategory.getPlaceSubCategoryName().getPlacesubcategorynameInEnglish().equalsIgnoreCase(placeSubCategory.getPlaceSubCategoryName().getPlacesubcategorynameInEnglish())) {
                    return new Response<PlaceSubCategories>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_CATEGORY_ALREADY_EXISTS, language), new ArrayList<>(), 0);
                }
            }
        }

        placeSubCategory.setAddedBy(placeSubCategory.getAddedBy());
        placeSubCategory.setPlaceSubCategoryId(AppConstants.SUB_PLACE_INITIAL_ID + Utility.currentDateTimeInUTC(AppConstants.DATE_TIME_FORMAT_WITHOUT_UNDERSCORE));
        placeSubCategory = (PlaceSubCategories) utility.setCommonAttrs(placeSubCategory, AppConstants.STATUS_PENDING);
        placeSubCategoriesList.add(placeSubCategory);
        Update mainCategoryUpdate = new Update();
        mainCategoryUpdate.push(AppConstants.PLACE_SUB_CATEGORIES, placeSubCategory);
        //mainCategoryUpdate.set("placeSubCategories",placeSubCategoriesList);
        mainCategoryUpdate.set(AppConstants.MODIFIED_DATE_TIME, Utility.currentDateTimeInUTC());
        mongoTemplate.updateFirst(queryFindCategoryWithId, mainCategoryUpdate, DhPlaceCategories.class);
        utility.addLog(authentication.getName(), "New sub category [" + placeSubCategory.getPlaceSubCategoryName().getPlacesubcategorynameInEnglish() + "] has been added under [" + queriedDhPlaceCategories.getPlaceCategoryName().getPlacecategorynameInEnglish() + "].");
        return new Response<PlaceSubCategories>(true, 201, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NEW_PLACESUBCATEGORY_ADDED, language), Collections.singletonList(placeSubCategory), 1);
    }
}
