package com.ayprojects.helpinghands.api.place.addplace_api;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.classes.add_strategy.StrategyAddPlaceApi;
import com.ayprojects.helpinghands.dao.placecategories.PlaceCategoryDao;
import com.ayprojects.helpinghands.models.Address;
import com.ayprojects.helpinghands.models.Contact;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.models.PlaceAvailabilityDetails;
import com.ayprojects.helpinghands.models.ProductsWithPrices;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.common_service.CommonService;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ValidateAddPlaceMethodTest {

    @Autowired
    StrategyAddPlaceApi strategyAddPlaceApi;

    @MockBean
    PlaceCategoryDao placeCategoryDao;

    @MockBean
    CommonService commonService;

    @Test
    void strategyAddPlaceApiShouldBeLoaded() {
        assertNotNull(strategyAddPlaceApi);
    }

    @Test
    void placeCategoryDaoShouldBeLoaded() {
        assertNotNull(placeCategoryDao);
    }

    @Test
    void commonServiceShouldBeLoaded(){
        assertNotNull(commonService);
    }

    @Test
    void givenEmptyObjThen402() {
        Response<DhPlace> expectedResponse = new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        Response<DhPlace> actualResponse = strategyAddPlaceApi.validateAddPlace(null, null);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse.getMessage()));
    }

    @Test
    void givenEmptyAddedByThen402() {
        DhPlace dhPlace = new DhPlace();
        dhPlace.setAddedBy("");
        Response<DhPlace> expectedResponse = new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        Response<DhPlace> actualResponse = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(actualResponse.getMessage().contains(AppConstants.ADDED_BY));
    }

    @Test
    void givenEmptyPlaceIdThen402() {
        DhPlace dhPlace = new DhPlace();
        dhPlace.setAddedBy("1232");
        dhPlace.setPlaceId("");
        Response<DhPlace> expectedResponse = new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        Response<DhPlace> actualResponse = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(actualResponse.getMessage().contains(AppConstants.PLACE_ID));
    }

    @Test
    void givenEmptyPlaceMainCategoryIdThen402() {
        DhPlace dhPlace = new DhPlace();
        dhPlace.setAddedBy("1232");
        dhPlace.setPlaceId("3wwqe");
        dhPlace.setPlaceMainCategoryId("");
        Response<DhPlace> expectedResponse = new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        Response<DhPlace> actualResponse = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(actualResponse.getMessage().contains(AppConstants.PLACE_MAIN_CATEGORY_ID));
    }

    @Test
    void givenEmptyPlaceCategoryNameThen402() {
        DhPlace dhPlace = new DhPlace();
        dhPlace.setAddedBy("1232");
        dhPlace.setPlaceId("3wwqe");
        dhPlace.setPlaceMainCategoryId("adf");
        dhPlace.setPlaceCategoryName("");
        Response<DhPlace> expectedResponse = new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        Response<DhPlace> actualResponse = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(actualResponse.getMessage().contains(AppConstants.PLACE_CATEGORY_NAME));
    }

    @Test
    void givenEmptyPlaceNameThen402() {
        DhPlace dhPlace = new DhPlace();
        dhPlace.setAddedBy("1232");
        dhPlace.setPlaceId("3wwqe");
        dhPlace.setPlaceMainCategoryId("adf");
        dhPlace.setPlaceCategoryName("adsf");
        dhPlace.setPlaceName(null);
        Response<DhPlace> expectedResponse = new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        Response<DhPlace> actualResponse = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(actualResponse.getMessage().contains(AppConstants.PLACE_NAME));
    }

    @Test
    void givenEmptyPlaceTypeThen402() {
        DhPlace dhPlace = new DhPlace();
        dhPlace.setAddedBy("1232");
        dhPlace.setPlaceId("3wwqe");
        dhPlace.setPlaceMainCategoryId("adf");
        dhPlace.setPlaceCategoryName("adsf");
        dhPlace.setPlaceName("Adf");
        dhPlace.setPlaceType("");
        Response<DhPlace> expectedResponse = new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        Response<DhPlace> actualResponse = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(actualResponse.getMessage().contains(AppConstants.PLACE_TYPE));
    }

    @Test
    void givenNullPlaceAvailaibilityThen402() {
        DhPlace dhPlace = new DhPlace();
        dhPlace.setAddedBy("1232");
        dhPlace.setPlaceId("3wwqe");
        dhPlace.setPlaceMainCategoryId("adf");
        dhPlace.setPlaceCategoryName("adsf");
        dhPlace.setPlaceName("Adf");
        dhPlace.setPlaceType("asdf");
        dhPlace.setPlaceAvailablityDetails(null);
        Response<DhPlace> expectedResponse = new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        Response<DhPlace> actualResponse = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(actualResponse.getMessage().contains(AppConstants.PLACE_AVAILABLITY_DETAILS));
    }

    @Test
    void givenPlaceAvailaibilityWithout24into7_emptyOrInvalidOpeningTime_Then402() {
        DhPlace dhPlace = new DhPlace();
        dhPlace.setAddedBy("1232");
        dhPlace.setPlaceId("3wwqe");
        dhPlace.setPlaceMainCategoryId("adf");
        dhPlace.setPlaceCategoryName("adsf");
        dhPlace.setPlaceName("Adf");
        dhPlace.setPlaceType("asdf");
        PlaceAvailabilityDetails placeAvailabilityDetails = new PlaceAvailabilityDetails();
        placeAvailabilityDetails.setProvide24into7(false);
        placeAvailabilityDetails.setPlaceOpeningTime(null);
        dhPlace.setPlaceAvailablityDetails(placeAvailabilityDetails);
        Response<DhPlace> expectedResponse = new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        Response<DhPlace> actualResponse = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(actualResponse.getMessage().contains(AppConstants.PLACE_OPENING_TIME));

        dhPlace.getPlaceAvailablityDetails().setPlaceOpeningTime("12-232-23");
        Response<DhPlace> actualResponse2 = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        assertEquals(expectedResponse.getStatus(), actualResponse2.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse2.getStatusCode());
        assertTrue(actualResponse2.getMessage().contains(AppConstants.PLACE_OPENING_TIME));
    }

    @Test
    void givenPlaceAvailaibilityWithout24into7_emptyOrInvalidClosingTime_Then402() {
        DhPlace dhPlace = new DhPlace();
        dhPlace.setAddedBy("1232");
        dhPlace.setPlaceId("3wwqe");
        dhPlace.setPlaceMainCategoryId("adf");
        dhPlace.setPlaceCategoryName("adsf");
        dhPlace.setPlaceName("Adf");
        dhPlace.setPlaceType("asdf");
        PlaceAvailabilityDetails placeAvailabilityDetails = new PlaceAvailabilityDetails();
        placeAvailabilityDetails.setProvide24into7(false);
        placeAvailabilityDetails.setPlaceOpeningTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setPlaceClosingTime("");
        dhPlace.setPlaceAvailablityDetails(placeAvailabilityDetails);
        Response<DhPlace> expectedResponse = new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        Response<DhPlace> actualResponse = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(actualResponse.getMessage().contains(AppConstants.PLACE_CLOSING_TIME));

        dhPlace.getPlaceAvailablityDetails().setPlaceClosingTime("12-232-23");
        Response<DhPlace> actualResponse2 = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        assertEquals(expectedResponse.getStatus(), actualResponse2.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse2.getStatusCode());
        assertTrue(actualResponse2.getMessage().contains(AppConstants.PLACE_CLOSING_TIME));
    }

    @Test
    void givenPlaceAvailaibilityWithout24into7_emptyOrInvalidLunchStartTime_Then402() {
        DhPlace dhPlace = new DhPlace();
        dhPlace.setAddedBy("1232");
        dhPlace.setPlaceId("3wwqe");
        dhPlace.setPlaceMainCategoryId("adf");
        dhPlace.setPlaceCategoryName("adsf");
        dhPlace.setPlaceName("Adf");
        dhPlace.setPlaceType("asdf");
        PlaceAvailabilityDetails placeAvailabilityDetails = new PlaceAvailabilityDetails();
        placeAvailabilityDetails.setProvide24into7(false);
        placeAvailabilityDetails.setPlaceOpeningTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setPlaceClosingTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setLunchStartTime("");
        dhPlace.setPlaceAvailablityDetails(placeAvailabilityDetails);
        Response<DhPlace> expectedResponse = new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        Response<DhPlace> actualResponse = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(actualResponse.getMessage().contains(AppConstants.LUNCH_START_TIME));

        dhPlace.getPlaceAvailablityDetails().setLunchStartTime("12-232-23");
        Response<DhPlace> actualResponse2 = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        assertEquals(expectedResponse.getStatus(), actualResponse2.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse2.getStatusCode());
        assertTrue(actualResponse2.getMessage().contains(AppConstants.LUNCH_START_TIME));
    }

    @Test
    void givenPlaceAvailaibilityWithout24into7_emptyOrInvalidLunchEndTime_Then402() {
        DhPlace dhPlace = new DhPlace();
        dhPlace.setAddedBy("1232");
        dhPlace.setPlaceId("3wwqe");
        dhPlace.setPlaceMainCategoryId("adf");
        dhPlace.setPlaceCategoryName("adsf");
        dhPlace.setPlaceName("Adf");
        dhPlace.setPlaceType("asdf");
        PlaceAvailabilityDetails placeAvailabilityDetails = new PlaceAvailabilityDetails();
        placeAvailabilityDetails.setProvide24into7(false);
        placeAvailabilityDetails.setPlaceOpeningTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setPlaceClosingTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setLunchStartTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setLunchEndTime("");
        dhPlace.setPlaceAvailablityDetails(placeAvailabilityDetails);
        Response<DhPlace> expectedResponse = new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        Response<DhPlace> actualResponse = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(actualResponse.getMessage().contains(AppConstants.LUNCH_END_TIME));

        dhPlace.getPlaceAvailablityDetails().setLunchEndTime("12-232-23");
        Response<DhPlace> actualResponse2 = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        assertEquals(expectedResponse.getStatus(), actualResponse2.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse2.getStatusCode());
        assertTrue(actualResponse2.getMessage().contains(AppConstants.LUNCH_END_TIME));
    }

    @Test
    void givenHaveExchange_notAnyTime_emptyStartTimeThen402() {
        DhPlace dhPlace = new DhPlace();
        dhPlace.setAddedBy("1232");
        dhPlace.setPlaceId("3wwqe");
        dhPlace.setPlaceMainCategoryId("adf");
        dhPlace.setPlaceCategoryName("adsf");
        dhPlace.setPlaceName("Adf");
        dhPlace.setPlaceType("asdf");
        PlaceAvailabilityDetails placeAvailabilityDetails = new PlaceAvailabilityDetails();
        placeAvailabilityDetails.setProvide24into7(false);
        placeAvailabilityDetails.setPlaceOpeningTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setPlaceClosingTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setLunchStartTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setLunchEndTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setHaveExchangeFacility(true);
        placeAvailabilityDetails.setAnyTimeExchange(false);
        placeAvailabilityDetails.setExchangeStartTime("");
        dhPlace.setPlaceAvailablityDetails(placeAvailabilityDetails);
        Response<DhPlace> expectedResponse = new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        Response<DhPlace> actualResponse = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(actualResponse.getMessage().contains(AppConstants.EXCHANGE_START_TIME));

        dhPlace.getPlaceAvailablityDetails().setExchangeStartTime("12-232-23");
        Response<DhPlace> actualResponse2 = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        assertEquals(expectedResponse.getStatus(), actualResponse2.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse2.getStatusCode());
        assertTrue(actualResponse2.getMessage().contains(AppConstants.EXCHANGE_START_TIME));
    }

    @Test
    void givenHaveExchange_notAnyTime_emptyEndTimeThen402() {
        DhPlace dhPlace = new DhPlace();
        dhPlace.setAddedBy("1232");
        dhPlace.setPlaceId("3wwqe");
        dhPlace.setPlaceMainCategoryId("adf");
        dhPlace.setPlaceCategoryName("adsf");
        dhPlace.setPlaceName("Adf");
        dhPlace.setPlaceType("asdf");
        PlaceAvailabilityDetails placeAvailabilityDetails = new PlaceAvailabilityDetails();
        placeAvailabilityDetails.setProvide24into7(false);
        placeAvailabilityDetails.setPlaceOpeningTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setPlaceClosingTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setLunchStartTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setLunchEndTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setHaveExchangeFacility(true);
        placeAvailabilityDetails.setAnyTimeExchange(false);
        placeAvailabilityDetails.setExchangeStartTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setExchangeEndTime("");
        dhPlace.setPlaceAvailablityDetails(placeAvailabilityDetails);
        Response<DhPlace> expectedResponse = new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        Response<DhPlace> actualResponse = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(actualResponse.getMessage().contains(AppConstants.EXCHANGE_END_TIME));

        dhPlace.getPlaceAvailablityDetails().setExchangeEndTime("12-232-23");
        Response<DhPlace> actualResponse2 = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        assertEquals(expectedResponse.getStatus(), actualResponse2.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse2.getStatusCode());
        assertTrue(actualResponse2.getMessage().contains(AppConstants.EXCHANGE_END_TIME));
    }

    @Test
    void givenAddressNullThen402() {
        DhPlace dhPlace = new DhPlace();
        dhPlace.setAddedBy("1232");
        dhPlace.setPlaceId("3wwqe");
        dhPlace.setPlaceMainCategoryId("adf");
        dhPlace.setPlaceCategoryName("adsf");
        dhPlace.setPlaceName("Adf");
        dhPlace.setPlaceType("asdf");
        PlaceAvailabilityDetails placeAvailabilityDetails = new PlaceAvailabilityDetails();
        placeAvailabilityDetails.setProvide24into7(false);
        placeAvailabilityDetails.setPlaceOpeningTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setPlaceClosingTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setLunchStartTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setLunchEndTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setHaveExchangeFacility(true);
        placeAvailabilityDetails.setAnyTimeExchange(false);
        placeAvailabilityDetails.setExchangeStartTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setExchangeEndTime("2021-01-04 12:28:25");
        dhPlace.setPlaceAvailablityDetails(placeAvailabilityDetails);
        dhPlace.setPlaceAddress(null);
        Response<DhPlace> expectedResponse = new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        Response<DhPlace> actualResponse = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(actualResponse.getMessage().contains(AppConstants.PLACE_ADDRESS));
    }

    @Test
    void givenNotNullAddress_addressGenerated_latZero_Then402() {
        DhPlace dhPlace = new DhPlace();
        dhPlace.setAddedBy("1232");
        dhPlace.setPlaceId("3wwqe");
        dhPlace.setPlaceMainCategoryId("adf");
        dhPlace.setPlaceCategoryName("adsf");
        dhPlace.setPlaceName("Adf");
        dhPlace.setPlaceType("asdf");
        PlaceAvailabilityDetails placeAvailabilityDetails = new PlaceAvailabilityDetails();
        placeAvailabilityDetails.setProvide24into7(false);
        placeAvailabilityDetails.setPlaceOpeningTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setPlaceClosingTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setLunchStartTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setLunchEndTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setHaveExchangeFacility(true);
        placeAvailabilityDetails.setAnyTimeExchange(false);
        placeAvailabilityDetails.setExchangeStartTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setExchangeEndTime("2021-01-04 12:28:25");
        dhPlace.setPlaceAvailablityDetails(placeAvailabilityDetails);
        Address address = new Address();
        address.setLat(0);
        dhPlace.setAddressGenerated(true);
        dhPlace.setPlaceAddress(address);
        Response<DhPlace> expectedResponse = new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        Response<DhPlace> actualResponse = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(actualResponse.getMessage().contains(AppConstants.LATTITUDE));
    }

    @Test
    void givenNotNullAddress_addressGenerated_lngZero_Then402() {
        DhPlace dhPlace = new DhPlace();
        dhPlace.setAddedBy("1232");
        dhPlace.setPlaceId("3wwqe");
        dhPlace.setPlaceMainCategoryId("adf");
        dhPlace.setPlaceCategoryName("adsf");
        dhPlace.setPlaceName("Adf");
        dhPlace.setPlaceType("asdf");
        PlaceAvailabilityDetails placeAvailabilityDetails = new PlaceAvailabilityDetails();
        placeAvailabilityDetails.setProvide24into7(false);
        placeAvailabilityDetails.setPlaceOpeningTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setPlaceClosingTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setLunchStartTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setLunchEndTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setHaveExchangeFacility(true);
        placeAvailabilityDetails.setAnyTimeExchange(false);
        placeAvailabilityDetails.setExchangeStartTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setExchangeEndTime("2021-01-04 12:28:25");
        dhPlace.setPlaceAvailablityDetails(placeAvailabilityDetails);
        Address address = new Address();
        address.setLat(1.230);
        address.setLng(0);
        dhPlace.setAddressGenerated(true);
        dhPlace.setPlaceAddress(address);
        Response<DhPlace> expectedResponse = new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        Response<DhPlace> actualResponse = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(actualResponse.getMessage().contains(AppConstants.LONGITUDE));
    }

    @Test
    void givenNotNullAddress_emptyFullAddress_Then402() {
        DhPlace dhPlace = new DhPlace();
        dhPlace.setAddedBy("1232");
        dhPlace.setPlaceId("3wwqe");
        dhPlace.setPlaceMainCategoryId("adf");
        dhPlace.setPlaceCategoryName("adsf");
        dhPlace.setPlaceName("Adf");
        dhPlace.setPlaceType("asdf");
        PlaceAvailabilityDetails placeAvailabilityDetails = new PlaceAvailabilityDetails();
        placeAvailabilityDetails.setProvide24into7(false);
        placeAvailabilityDetails.setPlaceOpeningTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setPlaceClosingTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setLunchStartTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setLunchEndTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setHaveExchangeFacility(true);
        placeAvailabilityDetails.setAnyTimeExchange(false);
        placeAvailabilityDetails.setExchangeStartTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setExchangeEndTime("2021-01-04 12:28:25");
        dhPlace.setPlaceAvailablityDetails(placeAvailabilityDetails);
        Address address = new Address();
        address.setLat(1.230);
        address.setLng(1.230);
        dhPlace.setAddressGenerated(true);
        address.setFullAddress("");
        dhPlace.setPlaceAddress(address);
        Response<DhPlace> expectedResponse = new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        Response<DhPlace> actualResponse = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(actualResponse.getMessage().contains(AppConstants.FULL_ADDRESS));
    }

    @Test
    void givenNullContactThen402() {
        DhPlace dhPlace = new DhPlace();
        dhPlace.setAddedBy("1232");
        dhPlace.setPlaceId("3wwqe");
        dhPlace.setPlaceMainCategoryId("adf");
        dhPlace.setPlaceCategoryName("adsf");
        dhPlace.setPlaceName("Adf");
        dhPlace.setPlaceType("asdf");
        PlaceAvailabilityDetails placeAvailabilityDetails = new PlaceAvailabilityDetails();
        placeAvailabilityDetails.setProvide24into7(false);
        placeAvailabilityDetails.setPlaceOpeningTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setPlaceClosingTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setLunchStartTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setLunchEndTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setHaveExchangeFacility(true);
        placeAvailabilityDetails.setAnyTimeExchange(false);
        placeAvailabilityDetails.setExchangeStartTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setExchangeEndTime("2021-01-04 12:28:25");
        dhPlace.setPlaceAvailablityDetails(placeAvailabilityDetails);
        Address address = new Address();
        address.setLat(1.230);
        address.setLng(1.230);
        address.setFullAddress("");
        dhPlace.setPlaceAddress(address);
        dhPlace.setPlaceContact(null);
        Response<DhPlace> expectedResponse = new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        Response<DhPlace> actualResponse = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(actualResponse.getMessage().contains(AppConstants.CONTACT_DETAILS));
    }

    @Test
    void givenNotNullContact_emptyMobileThen402() {
        DhPlace dhPlace = new DhPlace();
        dhPlace.setAddedBy("1232");
        dhPlace.setPlaceId("3wwqe");
        dhPlace.setPlaceMainCategoryId("adf");
        dhPlace.setPlaceCategoryName("adsf");
        dhPlace.setPlaceName("Adf");
        dhPlace.setPlaceType("asdf");
        PlaceAvailabilityDetails placeAvailabilityDetails = new PlaceAvailabilityDetails();
        placeAvailabilityDetails.setProvide24into7(false);
        placeAvailabilityDetails.setPlaceOpeningTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setPlaceClosingTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setLunchStartTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setLunchEndTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setHaveExchangeFacility(true);
        placeAvailabilityDetails.setAnyTimeExchange(false);
        placeAvailabilityDetails.setExchangeStartTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setExchangeEndTime("2021-01-04 12:28:25");
        dhPlace.setPlaceAvailablityDetails(placeAvailabilityDetails);
        Address address = new Address();
        address.setLat(1.230);
        address.setLng(1.230);
        address.setFullAddress("");
        dhPlace.setPlaceAddress(address);
        Contact contact = new Contact();
        contact.setMobile("");
        dhPlace.setPlaceContact(contact);
        Response<DhPlace> expectedResponse = new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        Response<DhPlace> actualResponse = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(actualResponse.getMessage().contains(AppConstants.MOBILE));
    }

    @Test
    void givenNotNullContact_emptyOwnerThen402() {
        DhPlace dhPlace = new DhPlace();
        dhPlace.setAddedBy("1232");
        dhPlace.setPlaceId("3wwqe");
        dhPlace.setPlaceMainCategoryId("adf");
        dhPlace.setPlaceCategoryName("adsf");
        dhPlace.setPlaceName("Adf");
        dhPlace.setPlaceType("asdf");
        PlaceAvailabilityDetails placeAvailabilityDetails = new PlaceAvailabilityDetails();
        placeAvailabilityDetails.setProvide24into7(false);
        placeAvailabilityDetails.setPlaceOpeningTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setPlaceClosingTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setLunchStartTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setLunchEndTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setHaveExchangeFacility(true);
        placeAvailabilityDetails.setAnyTimeExchange(false);
        placeAvailabilityDetails.setExchangeStartTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setExchangeEndTime("2021-01-04 12:28:25");
        dhPlace.setPlaceAvailablityDetails(placeAvailabilityDetails);
        Address address = new Address();
        address.setLat(1.230);
        address.setLng(1.230);
        dhPlace.setAddressGenerated(true);
        address.setFullAddress("qwerqwer12sdf");
        dhPlace.setPlaceAddress(address);
        Contact contact = new Contact();
        contact.setMobile("232332423423");
        dhPlace.setPlaceContact(contact);
        ProductsWithPrices p = new ProductsWithPrices();
        p.setProductId("asdf");
        dhPlace.setProductDetails(Collections.singletonList(p));
        dhPlace.setOwnerName("");
        Response<DhPlace> expectedResponse = new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        Response<DhPlace> actualResponse = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        LOGGER.info("givenNotNullContact_emptyOwnerThen402=>actualREsponse:" + actualResponse.getMessage());
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(actualResponse.getMessage().contains(AppConstants.OWNER_NAME));
    }

    @Test
    void givenNullProduct_0products_Then402() {
        DhPlace dhPlace = new DhPlace();
        dhPlace.setAddedBy("1232");
        dhPlace.setPlaceId("3wwqe");
        dhPlace.setPlaceMainCategoryId("adf");
        dhPlace.setPlaceCategoryName("adsf");
        dhPlace.setPlaceName("Adf");
        dhPlace.setPlaceType("asdf");
        PlaceAvailabilityDetails placeAvailabilityDetails = new PlaceAvailabilityDetails();
        placeAvailabilityDetails.setProvide24into7(false);
        placeAvailabilityDetails.setPlaceOpeningTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setPlaceClosingTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setLunchStartTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setLunchEndTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setHaveExchangeFacility(true);
        placeAvailabilityDetails.setAnyTimeExchange(false);
        placeAvailabilityDetails.setExchangeStartTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setExchangeEndTime("2021-01-04 12:28:25");
        dhPlace.setPlaceAvailablityDetails(placeAvailabilityDetails);
        Address address = new Address();
        address.setLat(1.230);
        address.setLng(1.230);
        address.setFullAddress("sdf");
        dhPlace.setAddressGenerated(true);
        dhPlace.setPlaceAddress(address);
        Contact contact = new Contact();
        contact.setMobile("232332423423");
        dhPlace.setPlaceContact(contact);
        dhPlace.setOwnerName("asdf");
        dhPlace.setProductDetails(null);
        Response<DhPlace> expectedResponse = new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        Response<DhPlace> actualResponse = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(actualResponse.getMessage().contains(AppConstants.PRODUCT_DETAILS));

        dhPlace.setProductDetails(new ArrayList<>());
        Response<DhPlace> actualResponse2 = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        assertEquals(expectedResponse.getStatus(), actualResponse2.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse2.getStatusCode());
        assertTrue(actualResponse2.getMessage().contains(AppConstants.PRODUCT_DETAILS));
    }

    @Test
    void givenValidObj_mainCategoryNotFound_Then402() {
        when(placeCategoryDao.findByPlaceCategoryId("abcd")).thenReturn(null);
        DhPlace dhPlace = new DhPlace();
        dhPlace.setAddedBy("1232");
        dhPlace.setPlaceId("3wwqe");
        dhPlace.setPlaceMainCategoryId("abcd");
        dhPlace.setPlaceCategoryName("adsf");
        dhPlace.setPlaceName("Adf");
        dhPlace.setPlaceType("asdf");
        PlaceAvailabilityDetails placeAvailabilityDetails = new PlaceAvailabilityDetails();
        placeAvailabilityDetails.setProvide24into7(false);
        placeAvailabilityDetails.setPlaceOpeningTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setPlaceClosingTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setLunchStartTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setLunchEndTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setHaveExchangeFacility(true);
        placeAvailabilityDetails.setAnyTimeExchange(false);
        placeAvailabilityDetails.setExchangeStartTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setExchangeEndTime("2021-01-04 12:28:25");
        dhPlace.setPlaceAvailablityDetails(placeAvailabilityDetails);
        Address address = new Address();
        address.setLat(1.230);
        address.setLng(1.230);
        address.setFullAddress("sdf");
        dhPlace.setAddressGenerated(true);
        dhPlace.setPlaceAddress(address);
        Contact contact = new Contact();
        contact.setMobile("232332423423");
        dhPlace.setPlaceContact(contact);
        dhPlace.setOwnerName("asdf");
        ProductsWithPrices p = new ProductsWithPrices();
        p.setProductId("asdf");
        dhPlace.setProductDetails(Collections.singletonList(p));
        Response<DhPlace> expectedResponse = new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_NOT_FOUND_PLACECATEGORIY_WITH_ID) + "ID : " + dhPlace.getPlaceMainCategoryId(), new ArrayList<>(), 0);
        Response<DhPlace> actualResponse = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
    }

    @Test
    void givenValidObjWithNotExistingUserThen402() {
        when(commonService.checkUserExistence("abcd123")).thenReturn(false);
        DhPlace dhPlace = new DhPlace();
        dhPlace.setAddedBy("abcd123");
        dhPlace.setPlaceId("3wwqe");
        dhPlace.setPlaceMainCategoryId("adf");
        dhPlace.setPlaceCategoryName("adsf");
        dhPlace.setPlaceName("Adf");
        dhPlace.setPlaceType("asdf");
        PlaceAvailabilityDetails placeAvailabilityDetails = new PlaceAvailabilityDetails();
        placeAvailabilityDetails.setProvide24into7(false);
        placeAvailabilityDetails.setPlaceOpeningTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setPlaceClosingTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setLunchStartTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setLunchEndTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setHaveExchangeFacility(true);
        placeAvailabilityDetails.setAnyTimeExchange(false);
        placeAvailabilityDetails.setExchangeStartTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setExchangeEndTime("2021-01-04 12:28:25");
        dhPlace.setPlaceAvailablityDetails(placeAvailabilityDetails);
        Address address = new Address();
        address.setLat(1.230);
        address.setLng(1.230);
        address.setFullAddress("sdf");
        dhPlace.setAddressGenerated(true);
        dhPlace.setPlaceAddress(address);
        Contact contact = new Contact();
        contact.setMobile("232332423423");
        dhPlace.setPlaceContact(contact);
        dhPlace.setOwnerName("asdf");
        ProductsWithPrices p = new ProductsWithPrices();
        p.setProductId("asdf");
        dhPlace.setProductDetails(Collections.singletonList(p));
        Response<DhPlace> expectedResponse = new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_USER_NOT_FOUND_WITH_USERID), new ArrayList<>(), 0);
        Response<DhPlace> actualResponse = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
    }

    @Test
    void givenValidObjThenStatus201() {
        when(commonService.checkUserExistence("abcd123")).thenReturn(true);
        when(placeCategoryDao.findByPlaceCategoryId("adf")).thenReturn(java.util.Optional.of(new DhPlaceCategories()));
        DhPlace dhPlace = new DhPlace();
        dhPlace.setAddedBy("abcd123");
        dhPlace.setPlaceId("3wwqe");
        dhPlace.setPlaceMainCategoryId("adf");
        dhPlace.setPlaceCategoryName("adsf");
        dhPlace.setPlaceName("Adf");
        dhPlace.setPlaceType("asdf");
        PlaceAvailabilityDetails placeAvailabilityDetails = new PlaceAvailabilityDetails();
        placeAvailabilityDetails.setProvide24into7(false);
        placeAvailabilityDetails.setPlaceOpeningTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setPlaceClosingTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setLunchStartTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setLunchEndTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setHaveExchangeFacility(true);
        placeAvailabilityDetails.setAnyTimeExchange(false);
        placeAvailabilityDetails.setExchangeStartTime("2021-01-04 12:28:25");
        placeAvailabilityDetails.setExchangeEndTime("2021-01-04 12:28:25");
        dhPlace.setPlaceAvailablityDetails(placeAvailabilityDetails);
        Address address = new Address();
        address.setLat(1.230);
        address.setLng(1.230);
        address.setFullAddress("sdf");
        dhPlace.setAddressGenerated(true);
        dhPlace.setPlaceAddress(address);
        Contact contact = new Contact();
        contact.setMobile("232332423423");
        dhPlace.setPlaceContact(contact);
        dhPlace.setOwnerName("asdf");
        ProductsWithPrices p = new ProductsWithPrices();
        p.setProductId("asdf");
        dhPlace.setProductDetails(Collections.singletonList(p));
        Response<DhPlace> expectedResponse = new Response<DhPlace>(true, 201, "Validated", new ArrayList<>(), 0);
        Response<DhPlace> actualResponse = strategyAddPlaceApi.validateAddPlace(null, dhPlace);
        LOGGER.info("givenValidObjThenStatus201=>actualResponse:"+actualResponse.getMessage());
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
    }

}
