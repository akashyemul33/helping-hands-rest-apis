package com.ayprojects.helpinghands.services.user;


import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.dao.user.UserDao;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GetUserByMobileMethodTest {

    @Autowired
    UserService userService;

    @MockBean
    UserDao userDao;

    @Test
    void contextShouldBeLoaded() {
        assertNotNull(userService);
    }

    @Test
    void givenEmptyMobileThen402() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(AppConstants.LABEL_HEADER_APPLANGUAGE, AppConstants.LANG_MARATHI);
        Response<DhUser> expectedResponse = new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(AppConstants.LANG_MARATHI, AppConstants.RESPONSEMESSAGE_MOBILE_IS_EMPTY), new ArrayList<>());
        Response<DhUser> actualResponse = userService.getUserByMobile(httpHeaders, "", "+91", "",AppConstants.CURRENT_API_VERSION);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse.getMessage()));


        Response<DhUser> actualResponse2 = userService.getUserByMobile(httpHeaders, null, "+91", "",AppConstants.CURRENT_API_VERSION);
        assertEquals(expectedResponse.getStatus(), actualResponse2.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse2.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse2.getMessage()));
    }


    @Test
    void givenInvalidMobileThen403() {
        String mobile = "7987787878";
        when(userDao.findByMobileNumber(mobile)).thenReturn(null);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(AppConstants.LABEL_HEADER_APPLANGUAGE, AppConstants.LANG_HINDI);
        Response<DhUser> expectedResponse = new Response<>(false, 403, ResponseMsgFactory.getResponseMsg(AppConstants.LANG_HINDI, AppConstants.RESPONSEMESSAGE_USER_NOT_FOUND_WITH_MOBILE), new ArrayList<>());
        Response<DhUser> actualResponse = userService.getUserByMobile(httpHeaders, mobile, "+91", "",AppConstants.CURRENT_API_VERSION);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse.getMessage()));
    }


    @Test
    void givenValidMobileWithStatusOtherThanActiveThen402() {
        String mobile = "7987787878";
        DhUser dhUser = new DhUser();
        dhUser.setMobileNumber(mobile);
        dhUser.setStatus(AppConstants.STATUS_PENDING);
        when(userDao.findByMobileNumber(mobile)).thenReturn(java.util.Optional.of(dhUser));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(AppConstants.LABEL_HEADER_APPLANGUAGE, AppConstants.LANG_HINDI);
        Response<DhUser> expectedResponse = new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(AppConstants.LANG_HINDI, AppConstants.RESPONSEMESSAGE_NOT_ACTIVE_USER), new ArrayList<>());
        Response<DhUser> actualResponse = userService.getUserByMobile(httpHeaders, mobile, "+91","adf2334", AppConstants.CURRENT_API_VERSION);
        LOGGER.info("givenValidMobileWithStatusOtherThanActiveThen402:" + actualResponse.getMessage());
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse.getMessage()));
    }

    @Test
    void givenValidMobileWithStatusActiveThen200() {
        String mobile = "7987787878";
        DhUser dhUser = new DhUser();
        dhUser.setMobileNumber(mobile);
        dhUser.setStatus(AppConstants.STATUS_ACTIVE);
        when(userDao.findByMobileNumber(mobile)).thenReturn(java.util.Optional.of(dhUser));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(AppConstants.LABEL_HEADER_APPLANGUAGE, AppConstants.LANG_HINDI);
        Response<DhUser> expectedResponse = new Response<>(true, 200, ResponseMsgFactory.getResponseMsg(AppConstants.LANG_HINDI, AppConstants.RESPONSEMESSAGE_USER_FOUND_WITH_MOBILE), new ArrayList<>());
        Response<DhUser> actualResponse = userService.getUserByMobile(httpHeaders, mobile, "+91","adsfasfe2323", AppConstants.CURRENT_API_VERSION);
        LOGGER.info("givenValidMobileWithStatusActiveThen200:" + actualResponse.getMessage());
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse.getMessage()));


    }

    @Test
    void givenValidMobileWithStatusNullThen402() {
        String mobile = "7987787878";
        DhUser dhUser = new DhUser();
        dhUser.setMobileNumber(mobile);
        dhUser.setStatus(null);
        when(userDao.findByMobileNumber(mobile)).thenReturn(java.util.Optional.of(dhUser));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(AppConstants.LABEL_HEADER_APPLANGUAGE, AppConstants.LANG_HINDI);
        Response<DhUser> expectedResponse = new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(AppConstants.LANG_HINDI, AppConstants.RESPONSEMESSAGE_NOT_ACTIVE_USER), new ArrayList<>());
        Response<DhUser> actualResponse = userService.getUserByMobile(httpHeaders, mobile, "+91","asdfasf2342", AppConstants.CURRENT_API_VERSION);
        LOGGER.info("givenValidMobileWithStatusNullThen402:" + actualResponse.getMessage());
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse.getMessage()));

    }
}
