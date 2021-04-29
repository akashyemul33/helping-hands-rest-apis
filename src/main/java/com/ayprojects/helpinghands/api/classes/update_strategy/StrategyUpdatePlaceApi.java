package com.ayprojects.helpinghands.api.classes.update_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyUpdateBehaviour;
import com.ayprojects.helpinghands.api.classes.CommonMethods;
import com.ayprojects.helpinghands.api.enums.PlaceStepEnums;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
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
            if (placeStepEnum == PlaceStepEnums.PLACE_DETAILS)
                return updateFirstStepDetails(language, obj);
            if (placeStepEnum == PlaceStepEnums.PRODUCTS)
                return updateProducts(language, obj);
            if (placeStepEnum == PlaceStepEnums.SINGLE_PRODUCT && keySet.contains(AppConstants.PRODUCT_POS)) {
                int productPos = (int) params.get(AppConstants.PRODUCT_POS);
                return updateSingleProduct(language, obj, productPos);
            }
        }

        return null;
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
        CalendarOperations calendarOperations = new CalendarOperations();
        Query query = new Query(Criteria.where(AppConstants.PLACE_ID).is(dhPlace.getPlaceId()));
        Update update = new Update();
        update.set("productDetails." + productPos, dhPlace.getProductDetails().get(0));
        update.set(AppConstants.MODIFIED_DATE_TIME, calendarOperations.currentDateTimeInUTC());
        mongoTemplate.updateFirst(query, update, AppConstants.COLLECTION_DH_PLACE);
        return new Response<>(true, 200, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_PRODUCT_DETAILS_UPDATED), new ArrayList<>(), 1);
    }

    private Response<DhPlace> updateProducts(String language, DhPlace dhPlace) {
        Response<DhPlace> validationResponse = validateProducts(language, dhPlace);
        if (!validationResponse.getStatus())
            return validationResponse;
        CalendarOperations calendarOperations = new CalendarOperations();
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
        update.set(AppConstants.MODIFIED_DATE_TIME, calendarOperations.currentDateTimeInUTC());
        mongoTemplate.updateFirst(query, update, AppConstants.COLLECTION_DH_PLACE);

        return new Response<>(true, 200, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_PRODUCT_DETAILS_UPDATED), new ArrayList<>(), 1);
    }

    private Response<DhPlace> updateFirstStepDetails(String language, DhPlace dhPlace) {
        Response<DhPlace> validationResponse = validateFirstStepDetails(language, dhPlace);
        if (!validationResponse.getStatus())
            return validationResponse;
        CalendarOperations calendarOperations = new CalendarOperations();
        Query query = new Query(Criteria.where(AppConstants.PLACE_ID).is(dhPlace.getPlaceId()));
        Update update = new Update();
        update.set(AppConstants.PLACE_NAME, dhPlace.getPlaceName());
        update.set(AppConstants.OWNER_NAME, dhPlace.getOwnerName());
        update.set(AppConstants.PLACE_REG_DATE, dhPlace.getPlaceRegDate());
        update.set(AppConstants.PLACE_ADDRESS, dhPlace.getPlaceAddress());
        update.set(AppConstants.MODIFIED_DATE_TIME, calendarOperations.currentDateTimeInUTC());
        mongoTemplate.updateFirst(query, update, AppConstants.COLLECTION_DH_PLACE);

        return new Response<>(true, 200, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_PLACE_DETAILS_UPDATED), new ArrayList<>(), 1);
    }

    private Response<DhPlace> validateProducts(String language, DhPlace dhPlace) {
        if (dhPlace == null) {
            return new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
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
        } else {
            Query query = new Query(Criteria.where(AppConstants.PLACE_ID).is(dhPlace.getPlaceId()));
            query.fields().include(AppConstants.PLACE_ID);
            DhPlace queriedDhPlace = mongoTemplate.findOne(query, DhPlace.class, AppConstants.COLLECTION_DH_PLACE);
            if (queriedDhPlace == null) {
                return new Response<DhPlace>(false, 402, "Place Id not found !", new ArrayList<>(), 0);
            }
        }
        return new Response<DhPlace>(true, 201, "Validated", new ArrayList<>(), 0);

    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.UpdatePlaceStrategy;
    }
}

