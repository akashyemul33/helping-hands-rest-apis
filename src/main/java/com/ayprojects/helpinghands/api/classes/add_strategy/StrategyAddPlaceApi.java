package com.ayprojects.helpinghands.api.classes.add_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.behaviours.StrategyAddBehaviour;
import com.ayprojects.helpinghands.api.classes.CommonMethods;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.dao.placecategories.PlaceCategoryDao;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhLog;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.models.LangValueObj;
import com.ayprojects.helpinghands.models.PlaceAvailabilityDetails;
import com.ayprojects.helpinghands.models.PlaceMainGridType;
import com.ayprojects.helpinghands.models.DhPlaceMainPage;
import com.ayprojects.helpinghands.models.PlaceMainPageItem;
import com.ayprojects.helpinghands.models.PlaceSubCategories;
import com.ayprojects.helpinghands.models.ProductsWithPrices;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.common_service.CommonService;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.CalendarOperations;
import com.ayprojects.helpinghands.util.tools.Utility;
import com.mongodb.lang.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;
import static com.ayprojects.helpinghands.HelpingHandsApplication.main;

@Component
public class StrategyAddPlaceApi implements StrategyAddBehaviour<DhPlace> {

    @Autowired
    private CommonService commonService;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PlaceCategoryDao placeCategoryDao;

    private Optional<DhPlaceCategories> queriedDhPlaceCategories;

    @Override
    public Response<DhPlace> add(String language, DhPlace dhPlace) {
        dhPlace = setPlaceIdIfNotExists(dhPlace);
        Response<DhPlace> validationResponse = validateAddPlace(language, dhPlace);
        if (!validationResponse.getStatus())
            return validationResponse;

        String placeStatus;
        String existingSubCategoryId = verifySubCategoryAndReturnId(dhPlace);
        if (Utility.isFieldEmpty(existingSubCategoryId)) {
            placeStatus = AppConstants.STATUS_PENDING_NEW_SUBCATEGORY;
            String newlyAddedSubCategoryId = prepareSubCategoryAndStoreInPending(language, dhPlace);
            dhPlace.setPlaceSubCategoryId(newlyAddedSubCategoryId);
        } else {
            placeStatus = AppConstants.STATUS_ACTIVE;
            dhPlace.setPlaceSubCategoryId(existingSubCategoryId);
        }

        for (ProductsWithPrices productsWithPrices : dhPlace.getProductDetails()) {
            if (Utility.isFieldEmpty(productsWithPrices.getProductId())) {
                String existingProductId = CommonMethods.verifyProductIdAndReturnId(productsWithPrices, mongoTemplate);
                if (Utility.isFieldEmpty(existingProductId)) {
                    String newlyAddedProductId = CommonMethods.prepareDhProductAndStoreItInDb(language, productsWithPrices, dhPlace, mongoTemplate);
                    productsWithPrices.setProductId(newlyAddedProductId);
                } else {
                    productsWithPrices.setProductId(existingProductId);
                }
            }
        }

        dhPlace = prepareDhPlaceAndStoreItInDb(dhPlace, placeStatus);
        validationResponse.setLogActionMsg("New [" + dhPlace.getPlaceType() + "] place with category [" + dhPlace.getPlaceCategoryName() + "->" + dhPlace.getPlaceSubCategoryName() + "] has been added in status " + placeStatus);

        //add place to place main pages in newly added section
        Query queryToFindNewlyAddedSection = new Query(Criteria.where(AppConstants.KEY_HEADING).regex(AppConstants.NEWLY_ADDED, "i"));
        DhPlaceMainPage mainPageResult = mongoTemplate.findOne(queryToFindNewlyAddedSection, DhPlaceMainPage.class);
        PlaceMainPageItem pageItem = new PlaceMainPageItem(dhPlace.getImageUrlsLow(), dhPlace.getImageUrlsHigh(), dhPlace.getPlaceName(), dhPlace.getPlaceId(), dhPlace.getPlaceSubCategoryName(), dhPlace.getCreatedDateTime(), dhPlace.getPlaceRegDate());
        if (mainPageResult == null) {
            LOGGER.info("No items in main place pages, so inserting one");
            mainPageResult = new DhPlaceMainPage(AppConstants.PLACE_MAIN_PAGE_ID_NEWLY_ADDED, Utility.getUUID(), AppConstants.NEWLY_ADDED, PlaceMainGridType.SMALL_GRID_ONLY_NAME, Collections.singletonList(pageItem));
            mainPageResult = (DhPlaceMainPage) Utility.setCommonAttrs(mainPageResult, AppConstants.STATUS_ACTIVE);
            mongoTemplate.save(mainPageResult, AppConstants.COLLECTION_DH_PLACE_MAIN_PAGE);
        } else {
            Update updatePlaceMainPages = new Update();
            List<PlaceMainPageItem> mainPageItems = mainPageResult.getItemList();
            if (mainPageItems == null) mainPageItems = new ArrayList<>(1);
            LOGGER.info("else items found for newly added list : size=" + mainPageItems.size());
            if (mainPageItems.size() == AppConstants.MAX_COUNT_OF_NEWLY_ADDED_LIST) {
                mainPageItems.remove(mainPageItems.size() - 1);
            }
            mainPageItems.add(0, pageItem);
            updatePlaceMainPages.set(AppConstants.ITEM_LIST, mainPageItems);
            updatePlaceMainPages.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
            mongoTemplate.updateFirst(queryToFindNewlyAddedSection, updatePlaceMainPages, DhPlaceMainPage.class);
        }


        String responseMsg = getResponseMsgForStatus(language, placeStatus);
        int statusCode = AppConstants.STATUS_ACTIVE.equalsIgnoreCase(placeStatus) ? 201 : 202;
        String headingMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_CONGRATULATIONS);
        return new Response<>(true, statusCode, headingMsg, responseMsg, new ArrayList<>());
    }

    @Override
    public Response<DhPlace> add(String language, DhPlace obj, HashMap<String, Object> params) throws ServerSideException {
        return null;
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.AddPlaceStrategy;
    }

    private String prepareSubCategoryAndStoreInPending(String language, DhPlace dhPlace) {
        CalendarOperations calendarOperations = new CalendarOperations();
        PlaceSubCategories psc = new PlaceSubCategories();
        psc.setDefaultName(dhPlace.getPlaceSubCategoryName());
        psc.setAddedBy(dhPlace.getAddedBy());
        psc.setPlaceSubCategoryId(AppConstants.SUB_PLACE_INITIAL_ID + calendarOperations.getTimeAtFileEnd());
        psc = (PlaceSubCategories) ApiOperations.setCommonAttrs(psc, AppConstants.STATUS_PENDING);

        Update mainCategoryUpdate = new Update();
        mainCategoryUpdate.push(AppConstants.PLACE_SUB_CATEGORIES, psc);
        mainCategoryUpdate.set(AppConstants.MODIFIED_DATE_TIME, calendarOperations.currentDateTimeInUTC());

        Query queryFindCategoryWithId = new Query(Criteria.where(AppConstants.PLACE_MAIN_CATEGORY_ID).is(dhPlace.getPlaceMainCategoryId()));
        queryFindCategoryWithId.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));

        mongoTemplate.updateFirst(queryFindCategoryWithId, mainCategoryUpdate, DhPlaceCategories.class);

        new StrategyAddLogApi().add(language, new DhLog(dhPlace.getAddedBy(), "New sub category while adding place [" + psc.getDefaultName() + "] has been added under [" + dhPlace.getPlaceCategoryName() + "]."));

        return psc.getPlaceSubCategoryId();
    }

    private String getResponseMsgForStatus(String language, String placeStatus) {
        if (AppConstants.STATUS_ACTIVE.equalsIgnoreCase(placeStatus)) {
            return ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NEW_PLACE_ADDED_WITH_ACTIVE);
        } else {
            return ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NEW_PLACE_ADDED_WITH_PENDING);
        }
    }

    private DhPlace prepareDhPlaceAndStoreItInDb(DhPlace dhPlace, String placeStatus) {
        dhPlace.setNumberOfProducts(dhPlace.getProductDetails().size());
        dhPlace = (DhPlace) ApiOperations.setCommonAttrs(dhPlace, placeStatus);
        return mongoTemplate.save(dhPlace, AppConstants.COLLECTION_DH_PLACE);
    }


    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public String verifySubCategoryAndReturnId(@NonNull DhPlace dhPlace) {
        if (queriedDhPlaceCategories == null || !queriedDhPlaceCategories.isPresent()) {
            this.queriedDhPlaceCategories = placeCategoryDao.findByPlaceCategoryId(dhPlace.getPlaceMainCategoryId());
        }

        String subCategoryId = dhPlace.getPlaceSubCategoryId();
        String subCategoryName = dhPlace.getPlaceSubCategoryName();
        LOGGER.info("verifySubCategoryAndReturnId=>subCategoryName:" + subCategoryName);
        if (!Utility.isFieldEmpty(subCategoryId)) {
            //noinspection OptionalGetWithoutIsPresent
            for (PlaceSubCategories ps : queriedDhPlaceCategories.get().getPlaceSubCategories()) {
                if (subCategoryId.equalsIgnoreCase(ps.getPlaceSubCategoryId())) {
                    return subCategoryId;
                }
            }
        }

        if (!Utility.isFieldEmpty(subCategoryName)) {
            for (PlaceSubCategories ps : queriedDhPlaceCategories.get().getPlaceSubCategories()) {
                if (ps.getDefaultName().equalsIgnoreCase(subCategoryName)) {
                    return ps.getPlaceSubCategoryId();
                }
            }

            for (PlaceSubCategories ps : queriedDhPlaceCategories.get().getPlaceSubCategories()) {
                LOGGER.info("verifySubCategoryAndReturnId=>value--1");
                LOGGER.info("verifySubCategoryAndReturnId=>value--1 ps.name=" + ps.getPlaceSubCategoryId());
                if (ps.getTranslations() != null && ps.getTranslations().size() > 0) {
                    LOGGER.info("verifySubCategoryAndReturnId=>value--2");
                    for (LangValueObj langValueObj : ps.getTranslations()) {
                        LOGGER.info("verifySubCategoryAndReturnId=>value--" + langValueObj.getValue());
                        if (langValueObj.getValue().equalsIgnoreCase(subCategoryName)) {
                            return ps.getPlaceSubCategoryId();
                        }
                    }
                }
            }
        }
        return "";
    }

    public DhPlace setPlaceIdIfNotExists(DhPlace dhPlace) {
        if (dhPlace != null && Utility.isFieldEmpty(dhPlace.getPlaceId())) {
            dhPlace.setPlaceId(Utility.getUUID());
        }
        return dhPlace;
    }

    public Response<DhPlace> validateAddPlace(String language, DhPlace dhPlace) {
        if (dhPlace == null) {
            return new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>(), 0);
        }
        CalendarOperations calendarOperations = new CalendarOperations();
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
                missingFieldsList.add(AppConstants.KEY_MOBILE);
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

        this.queriedDhPlaceCategories = placeCategoryDao.findByPlaceCategoryId(dhPlace.getPlaceMainCategoryId());
        if (!queriedDhPlaceCategories.isPresent()) {
            return new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NOT_FOUND_PLACECATEGORIY_WITH_ID) + "ID : " + dhPlace.getPlaceMainCategoryId(), new ArrayList<>(), 0);
        }
        return new Response<DhPlace>(true, 201, "Validated", new ArrayList<>(), 0);
    }
}
