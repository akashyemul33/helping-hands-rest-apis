package com.ayprojects.helpinghands.api.classes.update_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyUpdateBehaviour;
import com.ayprojects.helpinghands.api.classes.CommonMethods;
import com.ayprojects.helpinghands.api.enums.PlaceStepEnums;
import com.ayprojects.helpinghands.api.enums.ProductPricesVisibilityEnum;
import com.ayprojects.helpinghands.api.enums.RedirectionContent;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.PlaceAvailabilityDetails;
import com.ayprojects.helpinghands.models.ProductPricesVisibleUsers;
import com.ayprojects.helpinghands.models.ProductsWithPrices;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.CalendarOperations;
import com.ayprojects.helpinghands.util.tools.Utility;

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
import java.util.Set;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;


@Component
public class StrategyUpdatePlaceApi implements StrategyUpdateBehaviour<DhPlace> {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<DhPlace> update(String language, DhPlace obj, HashMap<String, Object> params) throws ServerSideException {
        if (params == null) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS), new ArrayList<>());
        }

        Set<String> keySet = params.keySet();
        if (keySet.contains(AppConstants.KEY_PLACE_STEP_ENUM)) {
            PlaceStepEnums placeStepEnum = (PlaceStepEnums) params.get(AppConstants.KEY_PLACE_STEP_ENUM);

            switch (placeStepEnum) {
                case PLACE_DETAILS:
                    return updatePlaceDetails(language, obj);
                case NATURE_OF_BUSINESS:
                    return updateNatureOfBusiness(language, obj);
                case PLACE_AVAILAIBILITY:
                    return updatePlaceAvailabilityDetails(language, obj);
                case PRODUCTS:
                    return updateProducts(language, obj);
                case SINGLE_PRODUCT:
                    if (keySet.contains(AppConstants.PRODUCT_POS)) {
                        int productPos = (int) params.get(AppConstants.PRODUCT_POS);
                        return updateSingleProduct(language, obj, productPos);
                    }
                    break;
                case CURRENT_AVAILABLE_STATUS:
                    return updateCurrentStatus(language, obj);
                case PRODUCT_PRICES_VISIBILITY:
                    return updateProductPricesVisibility(language, obj);
                case REQUEST_TO_SHOW_PRODUCT_PRICES:
                    return requestToShowProductPrices(language, (String) params.get(AppConstants.KEY_PLACE_USER_ID), (String) params.get(AppConstants.KEY_PLACE_ID), (String) params.get(AppConstants.KEY_USER_ID), (String) params.get(AppConstants.KEY_USER_NAME));
                case CONFIRM_SHOW_PRODUCT_PRICE_REQUEST:
                    return updateShowProductPricesRequest(language, (String) params.get(AppConstants.KEY_PLACE_ID),(String) params.get(AppConstants.PLACE_NAME), (String) params.get(AppConstants.KEY_USER_ID), (String) params.get(AppConstants.KEY_REQUESTED_USER_ID), (int) params.get(AppConstants.SELECTED_POS), AppConstants.STATUS_APPROVED);
                case REJECT_SHOW_PRODUCT_PRICE_REQUEST:
                    return updateShowProductPricesRequest(language, (String) params.get(AppConstants.KEY_PLACE_ID),(String) params.get(AppConstants.PLACE_NAME), (String) params.get(AppConstants.KEY_USER_ID), (String) params.get(AppConstants.KEY_REQUESTED_USER_ID), (int) params.get(AppConstants.SELECTED_POS), AppConstants.STATUS_REJECTED);

            }
        }

        return null;
    }

    private Response<DhPlace> updateShowProductPricesRequest(String language, String placeId,String placeName, String userId, String requestedUserId, int selectedPos, String status) {
        if (Utility.isFieldEmpty(placeId) || Utility.isFieldEmpty(userId))
            return new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());

        Query query = new Query(Criteria.where(AppConstants.PLACE_ID).is(placeId));
        Update update = new Update();

        update.set(String.format("productPricesVisibleUsers.%d.status", selectedPos), status);
        update.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
        mongoTemplate.updateFirst(query, update, AppConstants.COLLECTION_DH_PLACE);
        String title = "";
        String body = "";
        String redirectionContent = RedirectionContent.REDCONTENT_PLACEDETAILS;
        String redirectionContentUrl = RedirectionContent.REDURL_PLACEDETAILS;
        if (AppConstants.STATUS_REJECTED.equalsIgnoreCase(status)) {
            title = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NTFN_TITLE_SHOWPRICES_REQ_REJECTED);
            body = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NTFN_BODY_SHOWPRICES_REQ_REJECTED);
        } else if (AppConstants.STATUS_APPROVED.equalsIgnoreCase(status)) {
            title = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NTFN_TITLE_SHOWPRICES_REQ_APPROVED);
            body = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NTFN_BODY_SHOWPRICES_REQ_APPROVED);
        }
        body = String.format(body,placeName);
        Utility.sendNotification(requestedUserId, mongoTemplate, title, body, redirectionContent, redirectionContentUrl);

        return new Response<>(true, 200, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_UPDATED_SHOW_PRODUCT_PRICES_REQUEST), new ArrayList<>(), 1);
    }

    private Response<DhPlace> requestToShowProductPrices(String language, String placeUserId, String placeId, String userId, String userName) {
        if (Utility.isFieldEmpty(placeId) || Utility.isFieldEmpty(userId) || Utility.isFieldEmpty(userName))
            return new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());

        Query query = new Query(Criteria.where(AppConstants.PLACE_ID).is(placeId));
        query.fields().include(AppConstants.KEY_PRODUCTPRICES_VISIBLE_USERS);
        query.fields().include(AppConstants.KEY_PRODUCTPRICES_VISIBLE);
        DhPlace dhPlace = mongoTemplate.findOne(query, DhPlace.class);
        if (dhPlace != null && ProductPricesVisibilityEnum.ONLY_REQUESTED.name().equalsIgnoreCase(dhPlace.getProductPricesVisible())) {
            List<ProductPricesVisibleUsers> visibleUsers = dhPlace.getProductPricesVisibleUsers();

            if (visibleUsers != null) {
                for (ProductPricesVisibleUsers p : visibleUsers) {
                    if (p.getUserId().equals(userId)) {
                        return new Response<>(true, 200, "Already requested !", new ArrayList<>(), 0);
                    }
                }
            }
            Update update = new Update();
            ProductPricesVisibleUsers p = new ProductPricesVisibleUsers();
            p.setUserId(userId);
            p.setUserName(userName);
            p = (ProductPricesVisibleUsers) Utility.setCommonAttrs(p, AppConstants.STATUS_PENDING);
            update.push(AppConstants.KEY_PRODUCTPRICES_VISIBLE_USERS, p);
            update.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
            mongoTemplate.updateFirst(query, update, AppConstants.COLLECTION_DH_PLACE);

            String title = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NTFN_TITLE_SHOWPRICESREQUEST_PLACED);
            String body = String.format(ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NTFN_BODY_SHOWPRICESREQUEST_PLACED), userName);
            String redirectionContent = RedirectionContent.REDCONTENT_EDITPLACE_TOPSECTION;
            String redirectionUrl = RedirectionContent.REDURL_EDITPLACE_TOPSECTION;
            Utility.sendNotification(placeUserId, mongoTemplate, title, body, redirectionContent, redirectionUrl);
            return new Response<>(true, 200, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_REQUESTED_FOR_PRODUCT_PRICES), new ArrayList<>(), 1);
        }

        return new Response<>(true, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_UNABLE_TO_PLACE_SHOW_PRICES_REQUEST), new ArrayList<>(), 0);
    }

    private Response<DhPlace> updateProductPricesVisibility(String language, DhPlace dhPlace) {
        Response<DhPlace> validationResponse = validateProductPriceVisibility(language, dhPlace);
        if (!validationResponse.getStatus())
            return validationResponse;
        Query query = new Query(Criteria.where(AppConstants.PLACE_ID).is(dhPlace.getPlaceId()));
        Update update = new Update();
        update.set(AppConstants.KEY_PRODUCTPRICES_VISIBLE, dhPlace.getProductPricesVisible());
        update.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
        mongoTemplate.updateFirst(query, update, AppConstants.COLLECTION_DH_PLACE);

        String msg = "";
        if (ProductPricesVisibilityEnum.PUBLIC.name().equalsIgnoreCase(dhPlace.getProductPricesVisible())) {
            msg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_PRODUCTPRICES_PUBLIC_NOW);
        } else if (ProductPricesVisibilityEnum.HIDE.name().equalsIgnoreCase(dhPlace.getProductPricesVisible())) {
            msg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_PRODUCTPRICES_HIDDEN_NOW);
        } else if (ProductPricesVisibilityEnum.ONLY_REQUESTED.name().equalsIgnoreCase(dhPlace.getProductPricesVisible())) {
            msg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_PRODUCTPRICES_ONLY_REQUESTED);
        }

        return new Response<>(true, 200, msg, new ArrayList<>(), 1);
    }

    private Response<DhPlace> updateCurrentStatus(String language, DhPlace dhPlace) {
        Response<DhPlace> validationResponse = basicPlaceValidation(language, dhPlace);
        if (!validationResponse.getStatus())
            return validationResponse;
        Query query = new Query(Criteria.where(AppConstants.PLACE_ID).is(dhPlace.getPlaceId()));
        Update update = new Update();
        update.set(AppConstants.KEY_CURRENT_STATUS, dhPlace.getCurrentStatus());
        update.set(AppConstants.KEY_OFFLINE_MSG, dhPlace.getOfflineMsg());
        update.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
        mongoTemplate.updateFirst(query, update, AppConstants.COLLECTION_DH_PLACE);
        String currentStatus = ResponseMsgFactory.getResponseMsg(language, dhPlace.getCurrentStatus() ? AppConstants.RESPONSEMESSAGE_ONLINE : AppConstants.RESPONSEMESSAGE_OFFLINE);
        String msg = String.format(ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_CURRENT_STATUS_UPDATED), currentStatus);
        return new Response<>(true, 200, msg, new ArrayList<>(), 1);
    }

    private Response<DhPlace> updatePlaceAvailabilityDetails(String language, DhPlace dhPlace) {
        Response<DhPlace> validationResponse = validatePlaceAvailabilityDetails(language, dhPlace);
        if (!validationResponse.getStatus())
            return validationResponse;
        Query query = new Query(Criteria.where(AppConstants.PLACE_ID).is(dhPlace.getPlaceId()));
        Update update = new Update();
        update.set(AppConstants.PLACE_AVAILABLITY_DETAILS, dhPlace.getPlaceAvailablityDetails());
        update.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
        mongoTemplate.updateFirst(query, update, AppConstants.COLLECTION_DH_PLACE);

        return new Response<>(true, 200, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_PLACE_AVAILABILITY_UPDATED), new ArrayList<>(), 1);
    }

    private Response<DhPlace> updateNatureOfBusiness(String language, DhPlace dhPlace) {
        Response<DhPlace> validationResponse = validateSecondStepDetails(language, dhPlace);
        if (!validationResponse.getStatus())
            return validationResponse;
        Query query = new Query(Criteria.where(AppConstants.PLACE_ID).is(dhPlace.getPlaceId()));
        Update update = new Update();
        update.set(AppConstants.PLACE_DESC, dhPlace.getPlaceDesc());
        update.set(AppConstants.PLACE_CONTACT, dhPlace.getPlaceContact());
        update.set(AppConstants.DOOR_SERVICE, dhPlace.isDoorService());
        update.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
        mongoTemplate.updateFirst(query, update, AppConstants.COLLECTION_DH_PLACE);

        return new Response<>(true, 200, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NATURE_OF_BUSINESS_UPDATED), new ArrayList<>(), 1);
    }

    /***
     * pass dhplace by setting product with prices object which need to be udpated at 0th position of dhplac.productdetails
     * @param language
     * @param dhPlace
     * @return
     */
    private Response<DhPlace> updateSingleProduct(String language, DhPlace dhPlace, int productPos) {
        if (dhPlace == null || dhPlace.getProductDetails() == null || dhPlace.getProductDetails().get(0) == null) {
            return new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        }
        Query query = new Query(Criteria.where(AppConstants.PLACE_ID).is(dhPlace.getPlaceId()));
        Update update = new Update();
        update.set("productDetails." + productPos, dhPlace.getProductDetails().get(0));
        update.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
        mongoTemplate.updateFirst(query, update, AppConstants.COLLECTION_DH_PLACE);

        DhPlace updatedDhPlace = mongoTemplate.findOne(query, DhPlace.class, AppConstants.COLLECTION_DH_PLACE);
        return new Response<>(true, 200, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_PRODUCT_DETAILS_UPDATED), Collections.singletonList(updatedDhPlace), 1);
    }

    private Response<DhPlace> updateProducts(String language, DhPlace dhPlace) {
        Response<DhPlace> validationResponse = basicPlaceValidation(language, dhPlace);
        if (!validationResponse.getStatus())
            return validationResponse;
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
        Query query = new Query(Criteria.where(AppConstants.PLACE_ID).is(dhPlace.getPlaceId()));
        Update update = new Update();
        update.set(AppConstants.PRODUCT_DETAILS, dhPlace.getProductDetails());
        update.set(AppConstants.NUMBER_OF_PRODUCTS, dhPlace.getProductDetails().size());
        update.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
        mongoTemplate.updateFirst(query, update, AppConstants.COLLECTION_DH_PLACE);

        Query queryGetProducts = new Query(Criteria.where(AppConstants.PLACE_ID).is(dhPlace.getPlaceId()));
        queryGetProducts.fields().include(AppConstants.NUMBER_OF_PRODUCTS);
        queryGetProducts.fields().include(AppConstants.PRODUCT_DETAILS);
        queryGetProducts.fields().include(AppConstants.MODIFIED_DATE_TIME);
        DhPlace updatedDhPlace = mongoTemplate.findOne(queryGetProducts, DhPlace.class, AppConstants.COLLECTION_DH_PLACE);
        return new Response<>(true, 200, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_PRODUCT_DETAILS_UPDATED), Collections.singletonList(updatedDhPlace), 1);
    }

    private Response<DhPlace> updatePlaceDetails(String language, DhPlace dhPlace) {
        Response<DhPlace> validationResponse = validateFirstStepDetails(language, dhPlace);
        if (!validationResponse.getStatus())
            return validationResponse;
        Query query = new Query(Criteria.where(AppConstants.PLACE_ID).is(dhPlace.getPlaceId()));
        Update update = new Update();
        update.set(AppConstants.PLACE_NAME, dhPlace.getPlaceName());
        update.set(AppConstants.OWNER_NAME, dhPlace.getOwnerName());
        update.set(AppConstants.PLACE_REG_DATE, dhPlace.getPlaceRegDate());
        update.set(AppConstants.PLACE_ADDRESS, dhPlace.getPlaceAddress());
        update.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
        mongoTemplate.updateFirst(query, update, AppConstants.COLLECTION_DH_PLACE);

        return new Response<>(true, 200, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_PLACE_DETAILS_UPDATED), new ArrayList<>(), 1);
    }

    private Response<DhPlace> validateProducts(String language, DhPlace dhPlace) {
        if (dhPlace == null) {
            return new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        }
        return new Response<DhPlace>(true, 201, "Validated", new ArrayList<>(), 0);
    }

    private Response<DhPlace> validateSecondStepDetails(String language, DhPlace dhPlace) {
        if (dhPlace == null) {
            return new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        }
        List<String> missingFieldsList = new ArrayList<>();
        if (Utility.isFieldEmpty(dhPlace.getPlaceId()))
            missingFieldsList.add(AppConstants.PLACE_ID);
        if (dhPlace.getPlaceContact() == null) missingFieldsList.add(AppConstants.PLACE_CONTACT);
        else if (Utility.isFieldEmpty(dhPlace.getPlaceContact().getMobile()))
            missingFieldsList.add(AppConstants.PLACE_MOBILE);

        if (!missingFieldsList.isEmpty()) {
            String resMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY);
            resMsg = resMsg + " , these fields are missing : " + missingFieldsList;
            LOGGER.info("missingfields=>" + resMsg);
            return new Response<DhPlace>(false, 402, resMsg, new ArrayList<>(), 0);
        }
        return new Response<DhPlace>(true, 201, "Validated", new ArrayList<>(), 0);

    }

    private Response<DhPlace> basicPlaceValidation(String language, DhPlace dhPlace) {
        if (dhPlace == null) {
            return new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        }
        List<String> missingFieldsList = new ArrayList<>();
        if (Utility.isFieldEmpty(dhPlace.getPlaceId()))
            missingFieldsList.add(AppConstants.PLACE_ID);

        if (!missingFieldsList.isEmpty()) {
            String resMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY);
            resMsg = resMsg + " , these fields are missing : " + missingFieldsList;
            LOGGER.info("missingfields=>" + resMsg);
            return new Response<DhPlace>(false, 402, resMsg, new ArrayList<>(), 0);
        }
        return new Response<DhPlace>(true, 201, "Validated", new ArrayList<>(), 0);
    }

    private Response<DhPlace> validateProductPriceVisibility(String language, DhPlace dhPlace) {
        if (dhPlace == null) {
            return new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        }
        List<String> missingFieldsList = new ArrayList<>();
        if (Utility.isFieldEmpty(dhPlace.getPlaceId()))
            missingFieldsList.add(AppConstants.PLACE_ID);

        if (Utility.isFieldEmpty(dhPlace.getProductPricesVisible())) {
            missingFieldsList.add(AppConstants.KEY_PRODUCTPRICES_VISIBLE);
        }

        /*if (ProductPricesVisibilityEnum.ONLY_FEW.name().equalsIgnoreCase(dhPlace.getProductPricesVisible())) {
            if (dhPlace.getProductPricesVisibleUsers() == null || dhPlace.getProductPricesVisibleUsers().isEmpty())
                missingFieldsList.add(AppConstants.KEY_PRODUCTPRICES_VISIBLE_USERS);
        }*/
        if (!missingFieldsList.isEmpty()) {
            String resMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY);
            resMsg = resMsg + " , these fields are missing : " + missingFieldsList;
            LOGGER.info("missingfields=>" + resMsg);
            return new Response<DhPlace>(false, 402, resMsg, new ArrayList<>(), 0);
        }
        return new Response<DhPlace>(true, 201, "Validated", new ArrayList<>(), 0);
    }

    private Response<DhPlace> validatePlaceAvailabilityDetails(String language, DhPlace dhPlace) {
        if (dhPlace == null) {
            return new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        }
        CalendarOperations calendarOperations = new CalendarOperations();
        List<String> missingFieldsList = new ArrayList<>();
        if (Utility.isFieldEmpty(dhPlace.getPlaceId()))
            missingFieldsList.add(AppConstants.PLACE_ID);

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

        if (!missingFieldsList.isEmpty()) {
            String resMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY);
            resMsg = resMsg + " , these fields are missing : " + missingFieldsList;
            LOGGER.info("missingfields=>" + resMsg);
            return new Response<DhPlace>(false, 402, resMsg, new ArrayList<>(), 0);
        }
        return new Response<DhPlace>(true, 201, "Validated", new ArrayList<>(), 0);

    }

    private Response<DhPlace> validateFirstStepDetails(String language, DhPlace dhPlace) {
        if (dhPlace == null) {
            return new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        }
        List<String> missingFieldsList = new ArrayList<>();
        if (Utility.isFieldEmpty(dhPlace.getPlaceId()))
            missingFieldsList.add(AppConstants.PLACE_ID);
        if (Utility.isFieldEmpty(dhPlace.getPlaceName()))
            missingFieldsList.add(AppConstants.PLACE_NAME);
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

        if (!missingFieldsList.isEmpty()) {
            String resMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY);
            resMsg = resMsg + " , these fields are missing : " + missingFieldsList;
            LOGGER.info("missingfields=>" + resMsg);
            return new Response<DhPlace>(false, 402, resMsg, new ArrayList<>(), 0);
        }
        return new Response<DhPlace>(true, 201, "Validated", new ArrayList<>(), 0);

    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.UpdatePlaceStrategy;
    }
}

