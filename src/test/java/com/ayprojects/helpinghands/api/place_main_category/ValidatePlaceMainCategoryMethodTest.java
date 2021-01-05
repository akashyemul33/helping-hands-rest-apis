package com.ayprojects.helpinghands.api.place_main_category;


import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.classes.AddPlaceMainCategoryApi;
import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatePlaceMainCategoryMethodTest {
    private static AddPlaceMainCategoryApi addPlaceCategoryApi;
    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeAll
    static void setup() {
        addPlaceCategoryApi = new AddPlaceMainCategoryApi();
    }

    @Test
    void givenNullObjThen402() {
        Response<DhPlaceCategories> expectedResponse = new Response<DhPlaceCategories>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        Response<DhPlaceCategories> actualResponse = addPlaceCategoryApi.validatePlaceMainCategory(mongoTemplate,null, null);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse.getMessage()));
    }

    @Test
    void givenEmptyTypeOfCategoryThen402() {
        Response<DhPlaceCategories> expectedResponse = new Response<DhPlaceCategories>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_TYPEOFMAINPLACECATEGORY), new ArrayList<>());
        DhPlaceCategories dhPlaceCategories = new DhPlaceCategories();
        dhPlaceCategories.setTypeOfPlaceCategory("");
        dhPlaceCategories.setAddedBy("2323");
        dhPlaceCategories.setDefaultName("adbd");
        Response<DhPlaceCategories> actualResponse = addPlaceCategoryApi.validatePlaceMainCategory(mongoTemplate,null, dhPlaceCategories);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse.getMessage()));
    }

    @Test
    void givenEmptyDefaultNameInPlaceCategoryThen402() {
        Response<DhPlaceCategories> expectedResponse = new Response<DhPlaceCategories>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_DEFAULTNAME), new ArrayList<>());
        DhPlaceCategories dhPlaceCategories = new DhPlaceCategories();
        dhPlaceCategories.setTypeOfPlaceCategory("asdfasdf");
        dhPlaceCategories.setAddedBy("2323");
        dhPlaceCategories.setDefaultName("");
        Response<DhPlaceCategories> actualResponse = addPlaceCategoryApi.validatePlaceMainCategory(mongoTemplate,null, dhPlaceCategories);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse.getMessage()));
    }

    @Test
    void givenEmptyUserIdThen402() {
        Response<DhPlaceCategories> expectedResponse = new Response<DhPlaceCategories>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_USER_ID_IS_MISSING), new ArrayList<>());
        DhPlaceCategories dhPlaceCategories = new DhPlaceCategories();
        dhPlaceCategories.setTypeOfPlaceCategory("adsfadf");
        dhPlaceCategories.setAddedBy("");
        dhPlaceCategories.setDefaultName("adbd");
        Response<DhPlaceCategories> actualResponse = addPlaceCategoryApi.validatePlaceMainCategory(mongoTemplate,null, dhPlaceCategories);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse.getMessage()));
    }

    @Test
    void givenCategoryAlreadyExistsThen402() {
        //TODO
        /*Response<DhPlaceCategories> expectedResponse = new Response<DhPlaceCategories>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_DEFAULTNAME_PLACEMAINCATEGORY), new ArrayList<>());
        DhPlaceCategories dhPlaceCategories = new DhPlaceCategories();
        dhPlaceCategories.setTypeOfPlaceCategory("abc");
        dhPlaceCategories.setDefaultName("");
        dhPlaceCategories.setAddedBy("");
        Response<DhPlaceCategories> actualResponse = addPlaceCategoryApi.validatePlaceMainCategory(null,dhPlaceCategories);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse.getMessage()));*/
    }
}
