package com.ayprojects.helpinghands.services.placecategories;


import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.dao.placecategories.PlaceCategoryDao;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.models.LangValueObj;
import com.ayprojects.helpinghands.models.PlaceSubCategories;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.security.UserDetailsServiceImpl;
import com.ayprojects.helpinghands.tools.Utility;
import com.ayprojects.helpinghands.tools.Validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.lang.invoke.ConstantCallSite;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;
import static com.ayprojects.helpinghands.HelpingHandsApplication.main;

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
        if (dhPlaceCategories == null) {
            return new Response<DhPlaceCategories>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language), new ArrayList<>(), 0);
        }

        //validate all other stuffs
        List<String> missingFieldsList = Validations.findMissingFieldsForMainPlaceCategory(dhPlaceCategories);
        if (missingFieldsList.size() > 0) {
            String resMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language);
            resMsg = resMsg + " , these fields are missing : " + missingFieldsList;
            return new Response<DhPlaceCategories>(false, 402, resMsg, new ArrayList<>(), 0);
        }

        Optional<DhPlaceCategories> existingDhPlaceCategories = placeCategoryDao.findByDefaultName(dhPlaceCategories.getDefaultName());
        if (existingDhPlaceCategories.isPresent()) {
            return new Response<DhPlaceCategories>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_CATEGORY_ALREADY_EXISTS, language), new ArrayList<>(), 0);
        }

        dhPlaceCategories = (DhPlaceCategories) utility.setCommonAttrs(dhPlaceCategories, AppConstants.STATUS_PENDING);
        dhPlaceCategories.setPlaceCategoryId(AppConstants.MAIN_PLACE_INITIAL_ID + Utility.currentDateTimeInUTC(AppConstants.DATE_TIME_FORMAT_WITHOUT_UNDERSCORE));
        if (dhPlaceCategories.getPlaceSubCategories() != null && dhPlaceCategories.getPlaceSubCategories().size() > 0) {
            int counter = 1;
            for (PlaceSubCategories placeSubCategories : dhPlaceCategories.getPlaceSubCategories()) {
                if (Utility.isFieldEmpty(placeSubCategories.getDefaultName())) {
                    return new Response<DhPlaceCategories>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_PLACE_CATEGORY_NAMES_EMPTY, language), new ArrayList<>(), 0);
                }
                placeSubCategories.setAddedBy(dhPlaceCategories.getAddedBy());
                placeSubCategories.setPlaceSubCategoryId(AppConstants.SUB_PLACE_INITIAL_ID + "_" + counter + Utility.currentDateTimeInUTC(AppConstants.DATE_TIME_FORMAT_WITHOUT_UNDERSCORE));
                placeSubCategories = (PlaceSubCategories) utility.setCommonAttrs(placeSubCategories, AppConstants.STATUS_PENDING);
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
    public Response<DhPlaceCategories> findAllByStatus(Authentication authentication, HttpHeaders httpHeaders, String version) {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("PlaceCategoryServiceImpl->findAllByStatus : language=" + language);

        Query query = new Query(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        List<DhPlaceCategories> dhPlaceCategoriesList = mongoTemplate.find(query, DhPlaceCategories.class);
        if (dhPlaceCategoriesList.size() <= 0) {
            return new Response<>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NO_PLACECATEGORIES, language), new ArrayList<>(), 0);
        } else {

            //remove the sub categories with status other than active
            //and for active subcategories add maincategoryid and name.
            for (DhPlaceCategories mc : dhPlaceCategoriesList) {
                if (mc != null && mc.getPlaceSubCategories() != null && mc.getPlaceSubCategories().size() > 0) {
                    for (PlaceSubCategories sc : mc.getPlaceSubCategories()) {
                        if (sc != null) {
                            if (!sc.getStatus().equalsIgnoreCase(AppConstants.STATUS_ACTIVE)) {
                                mc.getPlaceSubCategories().remove(sc);
                            } else {
                                sc.setPlaceMainCategoryId(mc.getPlaceCategoryId());
                                sc.setPlaceMainCategoryName(Utility.getMainCategoryName(mc, language));
                            }
                        }
                    }
                }
            }

            return new Response<DhPlaceCategories>(true, 201, dhPlaceCategoriesList.size() + " place categories found .", dhPlaceCategoriesList, dhPlaceCategoriesList.size());
        }
    }


    @Override
    public Response<PlaceSubCategories> addPlaceSubCategory(Authentication authentication, HttpHeaders httpHeaders, PlaceSubCategories placeSubCategory, String mainPlaceCategoryId, String version) {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("PlaceCategoryServiceImpl->addPlaceSubCategory : language=" + language);


        if (placeSubCategory == null) {
            return new Response<PlaceSubCategories>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language), new ArrayList<>(), 0);
        }

        //validate all other stuffs
        List<String> missingFieldsList = Validations.findMissingFieldsForSubPlaceCategory(placeSubCategory, mainPlaceCategoryId);
        if (missingFieldsList.size() > 0) {
            String resMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language);
            resMsg = resMsg + " , these fields are missing : " + missingFieldsList;
            return new Response<PlaceSubCategories>(false, 402, resMsg, new ArrayList<>(), 0);
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
            for (PlaceSubCategories ps : queriedDhPlaceCategories.getPlaceSubCategories()) {
                if (ps != null && placeSubCategory.getDefaultName().equalsIgnoreCase(ps.getDefaultName())) {
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
        mainCategoryUpdate.set(AppConstants.MODIFIED_DATE_TIME, Utility.currentDateTimeInUTC());
        mongoTemplate.updateFirst(queryFindCategoryWithId, mainCategoryUpdate, DhPlaceCategories.class);
        utility.addLog(authentication.getName(), "New sub category [" + placeSubCategory.getDefaultName() + "] has been added under [" + queriedDhPlaceCategories.getDefaultName() + "].");
        return new Response<PlaceSubCategories>(true, 201, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NEW_PLACESUBCATEGORY_ADDED, language), Collections.singletonList(placeSubCategory), 1);
    }

    @Override
    public Response<DhPlaceCategories> getAllPlaceCategoriesByType(Authentication authentication, HttpHeaders httpHeaders, String version, String typeOfPlaceCategory) {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("PlaceCategoryServiceImpl->findByTypeOfCategory : language=" + language);

        Query query = new Query(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        query.addCriteria(Criteria.where(AppConstants.TYPE_OF_PLACECATEGORY).regex(typeOfPlaceCategory, "i"));
        List<DhPlaceCategories> dhPlaceCategoriesList = mongoTemplate.find(query, DhPlaceCategories.class);
        if (dhPlaceCategoriesList.size() <= 0) {
            return new Response<>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NO_PLACECATEGORIES, language), new ArrayList<>(), 0);
        } else {
            //remove the sub categories with status other than active
            //and for active subcategories add maincategoryid and name.
            for (DhPlaceCategories mc : dhPlaceCategoriesList) {
                if (mc != null && mc.getPlaceSubCategories() != null && mc.getPlaceSubCategories().size() > 0) {
                    List<Integer> scToRemoveIndex = new ArrayList<>();
                    for (int j = 0; j < mc.getPlaceSubCategories().size(); j++) {
                        PlaceSubCategories sc = mc.getPlaceSubCategories().get(j);
                        if (sc != null) {
                            if (!sc.getStatus().equalsIgnoreCase(AppConstants.STATUS_ACTIVE)) {
                                //add indexes of subcategories in a list, so that we can remove them later after this for loop
                                //to avoid concurrent modfiication exceptions
                                scToRemoveIndex.add(j);
                            } else {
                                sc.setPlaceMainCategoryId(mc.getPlaceCategoryId());
                                sc.setPlaceMainCategoryName(Utility.getMainCategoryName(mc, language));
                            }
                        }
                    }

                    //remove the subcategories from main list
                    for (int index : scToRemoveIndex) {
                        LOGGER.info("PlaceCategoryServiceImpl->getAllPlaceCategoriesByType-> going to remove : categoryname=" + mc.getPlaceSubCategories().get(index).getDefaultName());
                        mc.getPlaceSubCategories().remove(index);
                    }

                }
            }

            return new Response<DhPlaceCategories>(true, 200, dhPlaceCategoriesList.size() + " place categories found .", dhPlaceCategoriesList, dhPlaceCategoriesList.size());
        }
    }
}
