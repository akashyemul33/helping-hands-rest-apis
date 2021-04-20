package com.ayprojects.helpinghands.api.place_main_category;


import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.classes.add_strategy.StrategyAddPlaceMainCategoryApi;
import com.ayprojects.helpinghands.dao.placecategories.PlaceCategoryDao;
import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest
public class ValidatePlaceMainCategoryMethodTest {

    @Autowired
    StrategyAddPlaceMainCategoryApi addPlaceCategoryApi;

    @MockBean
    PlaceCategoryDao mockedPlaceCategory;

    @Test
    void placeCategoryApiShouldNotBeNull()
    {
        assertNotNull(addPlaceCategoryApi);
    }

    @Test
    void mockedPlaceCategoryShouldNotBeNull(){
        assertNotNull(mockedPlaceCategory);
    }
    @Test
    void givenNullObjThen402() {
        Response<DhPlaceCategories> expectedResponse = new Response<DhPlaceCategories>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        Response<DhPlaceCategories> actualResponse = addPlaceCategoryApi.validatePlaceMainCategory(null, null);
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
        Response<DhPlaceCategories> actualResponse = addPlaceCategoryApi.validatePlaceMainCategory(null, dhPlaceCategories);
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
        Response<DhPlaceCategories> actualResponse = addPlaceCategoryApi.validatePlaceMainCategory(null, dhPlaceCategories);
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
        Response<DhPlaceCategories> actualResponse = addPlaceCategoryApi.validatePlaceMainCategory(null, dhPlaceCategories);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse.getMessage()));
    }

    @Test
    void givenCategoryAlreadyExistsThen402() {
        DhPlaceCategories dhPlaceCategories = new DhPlaceCategories();
        dhPlaceCategories.setTypeOfPlaceCategory("abc");
        dhPlaceCategories.setDefaultName("abc");
        dhPlaceCategories.setAddedBy("asdfasreww");
        when(mockedPlaceCategory.findByDefaultName("abc")).thenReturn(java.util.Optional.of(dhPlaceCategories));
        Response<DhPlaceCategories> expectedResponse = new Response<DhPlaceCategories>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_CATEGORY_ALREADY_EXISTS), new ArrayList<>(), 0);
        Response<DhPlaceCategories> actualResponse = addPlaceCategoryApi.validatePlaceMainCategory(null, dhPlaceCategories);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse.getMessage()));
    }
}
