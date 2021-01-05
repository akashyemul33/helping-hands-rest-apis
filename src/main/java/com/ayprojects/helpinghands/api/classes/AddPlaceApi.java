package com.ayprojects.helpinghands.api.classes;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.behaviours.AddBehaviour;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.models.DhProduct;
import com.ayprojects.helpinghands.models.LangValueObj;
import com.ayprojects.helpinghands.models.PlaceAvailabilityDetails;
import com.ayprojects.helpinghands.models.PlaceSubCategories;
import com.ayprojects.helpinghands.models.ProductsWithPrices;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.common_service.CommonService;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.CalendarOperations;
import com.ayprojects.helpinghands.util.tools.Utility;
import com.mongodb.lang.NonNull;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.List;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

public class AddPlaceApi implements AddBehaviour<DhPlace> {

    private final CommonService commonService;

    public AddPlaceApi(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public Response<DhPlace> add(String language, MongoTemplate mongoTemplate, DhPlace dhPlace) {

        String placeStatus = AppConstants.STATUS_ACTIVE;

        dhPlace = setPlaceIdIfNotExists(dhPlace);

        Response<DhPlace> validationResponse = validateAddPlace(mongoTemplate, language, dhPlace);
        if (!validationResponse.getStatus())
            return validationResponse;

        CalendarOperations calendarOperations = new CalendarOperations();

        boolean canProceed = false;
        DhPlaceCategories queriedDhPlaceCategories = getMainCategoryById(dhPlace.getPlaceMainCategoryId(), mongoTemplate);
        //check for sub category existence
        //check whether category existence if user didn't send subcategoryId
        if (Utility.isFieldEmpty(dhPlace.getPlaceSubCategoryId())) {
            //TODO add category to db, but first check with the sub category name whether it exists
            for (PlaceSubCategories ps : queriedDhPlaceCategories.getPlaceSubCategories()) {
                if (dhPlace.getPlaceSubCategoryName().equalsIgnoreCase(ps.getDefaultName())) {
                    LOGGER.info("sub category found with the default name search, it's id is " + ps.getPlaceSubCategoryId() + " name=" + ps.getDefaultName());
                    dhPlace.setPlaceSubCategoryId(ps.getPlaceSubCategoryId());
                    canProceed = true;
                    break;
                } else {
                    if (ps.getTranslations() != null && ps.getTranslations().size() > 0) {
                        for (LangValueObj langValueObj : ps.getTranslations()) {
                            if (dhPlace.getPlaceSubCategoryName().equalsIgnoreCase(langValueObj.getValue())) {
                                LOGGER.info("sub category found with the translations obj search, it's id is " + ps.getPlaceSubCategoryId() + " name=" + ps.getDefaultName() + " and lang=" + langValueObj.getLang());
                                dhPlace.setPlaceSubCategoryId(ps.getPlaceSubCategoryId());
                                canProceed = true;
                                break;
                            }
                        }
                    }
                }
            }
            if (!canProceed) {
                //create if category not found
                //create new sub category with status pending
                PlaceSubCategories psc = new PlaceSubCategories();
                psc.setDefaultName(dhPlace.getPlaceSubCategoryName());
                psc.setAddedBy(dhPlace.getAddedBy());
                psc.setPlaceSubCategoryId(AppConstants.SUB_PLACE_INITIAL_ID + calendarOperations.getTimeAtFileEnd());
                psc = (PlaceSubCategories) ApiOperations.setCommonAttrs(psc, AppConstants.STATUS_PENDING);
                List<PlaceSubCategories> pscList = queriedDhPlaceCategories.getPlaceSubCategories();
                pscList.add(psc);
                Update mainCategoryUpdate = new Update();
                mainCategoryUpdate.push(AppConstants.PLACE_SUB_CATEGORIES, psc);
                mainCategoryUpdate.set(AppConstants.MODIFIED_DATE_TIME, calendarOperations.currentDateTimeInUTC());
//                mongoTemplate.updateFirst(queryFindCategoryWithId, mainCategoryUpdate, DhPlaceCategories.class);
//                utility.addLog(authentication.getName(), "New sub category while adding place [" + psc.getDefaultName() + "] has been added under [" + queriedDhPlaceCategories.getDefaultName() + "].");
                dhPlace.setPlaceSubCategoryId(psc.getPlaceSubCategoryId());
                canProceed = true;
                placeStatus = AppConstants.STATUS_PENDING;
            }

        } else {
            for (PlaceSubCategories placeSubCategory : queriedDhPlaceCategories.getPlaceSubCategories()) {
                if (dhPlace.getPlaceSubCategoryId().equalsIgnoreCase(placeSubCategory.getPlaceSubCategoryId())) {
                    canProceed = true;
                    placeStatus = AppConstants.STATUS_ACTIVE;
                    break;
                }
            }
        }

        if (canProceed) {
            for (ProductsWithPrices p : dhPlace.getProductDetails()) {
                if (Utility.isFieldEmpty(p.getProductId())) {
                    //TODO check whether product already exists
                    Query queryToFindProduct = new Query(new Criteria().orOperator(Criteria.where(AppConstants.DEFAULT_NAME).regex(p.getUserEnteredProductName(), "i"),
                            Criteria.where(AppConstants.TRANSLATIONS + ".value").regex(p.getUserEnteredProductName(), "i")
                    ));
                    DhProduct queriedDhProduct = mongoTemplate.findOne(queryToFindProduct, DhProduct.class);
                    if (queriedDhProduct == null) {
                        queriedDhProduct = new DhProduct();
                        //product does not exist so add it to db
                        LOGGER.info("product does not exist, so create product to DB");
                        queriedDhProduct.setProductId(Utility.getUUID());
                        queriedDhProduct.setDefaultUnit(p.getSelectedUnit());
                        queriedDhProduct.setMainPlaceCategoryId(dhPlace.getPlaceMainCategoryId());
                        queriedDhProduct.setSubPlaceCategoryId(dhPlace.getPlaceSubCategoryId());
                        queriedDhProduct.setCategoryName(dhPlace.getPlaceCategoryName() + "->" + dhPlace.getPlaceSubCategoryName());
                        queriedDhProduct.setAddedBy(dhPlace.getAddedBy());
                        queriedDhProduct.setDefaultName(p.getUserEnteredProductName());
                        queriedDhProduct.setAvgPrice(p.getProductPrice());
                        queriedDhProduct = (DhProduct) ApiOperations.setCommonAttrs(queriedDhProduct, AppConstants.STATUS_ACTIVE);
                        mongoTemplate.save(queriedDhProduct, AppConstants.COLLECTION_DH_PRODUCT);
//                        utility.addLog(authentication.getName(), "Product [" + p.getUserEnteredProductName() + "] has been added under [" + queriedDhPlaceCategories.getDefaultName() + "->" + dhPlace.getSubscribedUsers() + "].");
                        p.setProductId(queriedDhProduct.getProductId());
                    } else {
                        LOGGER.info("Found product with name " + p.getUserEnteredProductName() + " productId=" + p.getProductId());
                        p.setProductId(queriedDhProduct.getProductId());
                    }
                }
            }
        } else {
            String resMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_UNABLE_TO_ADD_PLACE_ISSUES_WITH_SUBCATEGORY) + " PlaceSubCategoryName:" + dhPlace.getPlaceSubCategoryName();
            return new Response<DhPlace>(false, 402, resMsg, new ArrayList<>(), 0);
        }

        dhPlace.setNumberOfProducts(dhPlace.getProductDetails().size());
        dhPlace = (DhPlace) ApiOperations.setCommonAttrs(dhPlace, placeStatus);
        mongoTemplate.save(dhPlace, AppConstants.COLLECTION_DH_PLACE);
//        utility.addLog(authentication.getName(), "New [" + dhPlace.getPlaceType() + "] place with category [" + dhPlace.getPlaceCategoryName() + "->" + dhPlace.getPlaceSubCategoryName() + "] has been added in status " + placeStatus);
        int statusCode = 201;//for active
        String headingMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_CONGRATULATIONS);
        String responseMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NEW_PLACE_ADDED_WITH_ACTIVE);
        if (AppConstants.STATUS_ACTIVE.equalsIgnoreCase(placeStatus)) {
            statusCode = 201;
            responseMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NEW_PLACE_ADDED_WITH_ACTIVE);
        } else if (AppConstants.STATUS_PENDING.equalsIgnoreCase(placeStatus)) {
            statusCode = 202;//for pending
            responseMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NEW_PLACE_ADDED_WITH_PENDING);
        }
        return new Response<>(true, statusCode, headingMsg, responseMsg, new ArrayList<>());
    }

    public DhPlace setPlaceIdIfNotExists(@NonNull DhPlace dhPlace) {
        if (Utility.isFieldEmpty(dhPlace.getPlaceId())) {
            dhPlace.setPlaceId(Utility.getUUID());
        }
        return dhPlace;
    }

    private DhPlaceCategories getMainCategoryById(String placeMainCategoryId, MongoTemplate mongoTemplate) {
        Query queryFindCategoryWithId = new Query(Criteria.where(AppConstants.PLACE_MAIN_CATEGORY_ID).is(placeMainCategoryId));
        queryFindCategoryWithId.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        return mongoTemplate.findOne(queryFindCategoryWithId, DhPlaceCategories.class);
    }


    public Response<DhPlace> validateAddPlace(@NonNull MongoTemplate mongoTemplate, String language, DhPlace dhPlace) {
        CalendarOperations calendarOperations = new CalendarOperations();
        if (dhPlace == null) {
            return new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>(), 0);
        }

        List<String> missingFieldsList = new ArrayList<>();
        if (Utility.isFieldEmpty(dhPlace.getAddedBy()))
            missingFieldsList.add(AppConstants.ADDED_BY);
        if (Utility.isFieldEmpty(dhPlace.getPlaceId()))
            missingFieldsList.add(AppConstants.PLACE_ID);
        if (Utility.isFieldEmpty(dhPlace.getPlaceMainCategoryId()))
            missingFieldsList.add(AppConstants.PLACE_MAIN_CATEGORY_ID);
        if (Utility.isFieldEmpty(dhPlace.getPlaceCategoryName()))
            missingFieldsList.add(AppConstants.PLACE_CATEGORY_NAME);
        if (Utility.isFieldEmpty(dhPlace.getPlaceName()))
            missingFieldsList.add(AppConstants.PLACE_NAME);
        if (Utility.isFieldEmpty(dhPlace.getPlaceType()))
            missingFieldsList.add(AppConstants.PLACE_TYPE);

        if (dhPlace.getPlaceAvailablityDetails() == null)
            missingFieldsList.add(AppConstants.PLACE_AVAILABLITY_DETAILS);
        else {
            PlaceAvailabilityDetails p = dhPlace.getPlaceAvailablityDetails();
            if (!p.isProvide24into7()) {
                if (Utility.isFieldEmpty(p.getPlaceOpeningTime()) || !calendarOperations.verifyTimeFollowsCorrectFormat(p.getPlaceOpeningTime()))
                    missingFieldsList.add(AppConstants.PLACE_OPENING_TIME);
                if (Utility.isFieldEmpty(p.getPlaceClosingTime()) || !calendarOperations.verifyTimeFollowsCorrectFormat(p.getPlaceClosingTime()))
                    missingFieldsList.add(AppConstants.PLACE_CLOSING_TIME);
                if (!p.getHaveNoLunchHours()) {
                    if (Utility.isFieldEmpty(p.getLunchStartTime()) || !calendarOperations.verifyTimeFollowsCorrectFormat(p.getLunchStartTime()))
                        missingFieldsList.add(AppConstants.LUNCH_START_TIME);
                    if (Utility.isFieldEmpty(p.getLunchEndTime()) || !calendarOperations.verifyTimeFollowsCorrectFormat(p.getLunchEndTime()))
                        missingFieldsList.add(AppConstants.LUNCH_END_TIME);
                }
            }

            if (p.isHaveExchangeFacility()) {
                if (!p.isAnyTimeExchange()) {
                    if (Utility.isFieldEmpty(p.getExchangeStartTime()) || !calendarOperations.verifyTimeFollowsCorrectFormat(p.getExchangeStartTime()))
                        missingFieldsList.add(AppConstants.EXCHANGE_START_TIME);
                    if (Utility.isFieldEmpty(p.getExchangeEndTime()) || !calendarOperations.verifyTimeFollowsCorrectFormat(p.getExchangeEndTime()))
                        missingFieldsList.add(AppConstants.EXCHANGE_END_TIME);
                }
            }
        }

        if (dhPlace.getPlaceAddress() == null) missingFieldsList.add(AppConstants.PLACE_ADDRESS);
        else {
            if (dhPlace.isAddressGenerated()) {
                if (dhPlace.getPlaceAddress().getLat() == 0)
                    missingFieldsList.add(AppConstants.LATTITUDE);
                if (dhPlace.getPlaceAddress().getLng() == 0)
                    missingFieldsList.add(AppConstants.LONGITUDE);
            }

            if (Utility.isFieldEmpty(dhPlace.getPlaceAddress().getFullAddress())) {
                missingFieldsList.add(AppConstants.FULL_ADDRESS);
            }
        }
        if (dhPlace.getPlaceContact() == null) missingFieldsList.add(AppConstants.CONTACT_DETAILS);
        else {
            if (Utility.isFieldEmpty(dhPlace.getPlaceContact().getMobile())) {
                missingFieldsList.add(AppConstants.MOBILE);
            }
        }
        if (Utility.isFieldEmpty(dhPlace.getOwnerName()))
            missingFieldsList.add(AppConstants.OWNER_NAME);

        if (dhPlace.getProductDetails() == null || dhPlace.getProductDetails().size() <= 0)
            missingFieldsList.add(AppConstants.PRODUCT_DETAILS);

        if (missingFieldsList.size() > 0) {
            String resMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY);
            resMsg = resMsg + " , these fields are missing : " + missingFieldsList;
            LOGGER.info("missingfields=>" + resMsg);
            return new Response<DhPlace>(false, 402, resMsg, new ArrayList<>(), 0);
        }

        if (!commonService.checkUserExistence(dhPlace.getAddedBy())) {
            return new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_USER_NOT_FOUND_WITH_USERID), new ArrayList<>(), 0);
        }

        DhPlaceCategories queriedDhPlaceCategories = getMainCategoryById(dhPlace.getPlaceMainCategoryId(), mongoTemplate);
        if (queriedDhPlaceCategories == null) {
            return new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NOT_FOUND_PLACECATEGORIY_WITH_ID) + "ID : " + dhPlace.getPlaceMainCategoryId(), new ArrayList<>(), 0);
        }
        return new Response<DhPlace>(true, 201, "Validated", new ArrayList<>(), 0);
    }
}
