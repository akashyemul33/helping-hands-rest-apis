package com.ayprojects.helpinghands.api.user;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.classes.add_strategy.StrategyAddLogApi;
import com.ayprojects.helpinghands.api.classes.add_strategy.StrategyAddUserApi;
import com.ayprojects.helpinghands.api.classes.get_strategy.StrategyGetLoginResponse;
import com.ayprojects.helpinghands.dao.appconfig.AppConfigDao;
import com.ayprojects.helpinghands.dao.user.UserDao;
import com.ayprojects.helpinghands.models.DhAppConfig;
import com.ayprojects.helpinghands.models.LoginResponse;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.GetAuthenticationObj;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GetUserDetailsTest {
    @Autowired
    StrategyGetLoginResponse strategyGetLoginResponse;

    @MockBean
    UserDao userDao;

    @Test
    void userDaoShouldBeLoaded() {
        assertNotNull(userDao);
    }

    @Test
    void strategyGetLoginResponseShouldBeLoaded(){
        assertNotNull(strategyGetLoginResponse);
    }

    @MockBean
    AppConfigDao appConfigDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Test
    void appConfigDaoShouldBeLoaded() {
        assertNotNull(appConfigDao);
    }

    @Test
    void givenEmptyAuthenticatioObjectThen402() {
        Response<LoginResponse> expectedResponse = new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_AUTHENTICATION_REQD), new ArrayList<>());
        Response<LoginResponse> actualResponse = strategyGetLoginResponse.getUserDetails(null, null,"", "");
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse.getMessage()));
    }

    @Test
    void givenAuthenticationButReceivedInvalidAppConfigThen402() {
        Authentication authentication = GetAuthenticationObj.getValidAuthenticationObj(userDao, authenticationManager);
        DhAppConfig dhAppConfig = new DhAppConfig();
        dhAppConfig.setStatus(AppConstants.STATUS_PENDING);
        when(appConfigDao.getActiveAppConfig()).thenReturn(Optional.of(dhAppConfig));

        Response<LoginResponse> actualResponse = strategyGetLoginResponse.getUserDetails(authentication,null, "","");
        Response<LoginResponse> expectedResponse = new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_SOMETHING_WENT_WRONG), new ArrayList<>());
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse.getMessage()));

    }

    @Test
    void givenAuthenticationReceivedValidAppConfigWithEmptyLastLogoutTimeThen200() {
        Authentication authentication = GetAuthenticationObj.getValidAuthenticationObj(userDao, authenticationManager);
        DhAppConfig dhAppConfig = new DhAppConfig();
        dhAppConfig.setStatus(AppConstants.STATUS_ACTIVE);
        when(appConfigDao.getActiveAppConfig()).thenReturn(Optional.of(dhAppConfig));

        Response<LoginResponse> actualResponse = strategyGetLoginResponse.getUserDetails( authentication,null, "","");
        Response<LoginResponse> expectedResponse = new Response<>(true,
                200,
                ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_USER_AND_APPCONFIG_DETAILS_FETCHED)
                , null
                , 1);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse.getMessage()));
        assertTrue(actualResponse.getData() != null && actualResponse.getData().get(0) != null && actualResponse.getData().get(0).getDhUser() != null && actualResponse.getData().get(0).getDhAppConfig() != null);
    }

    @Test
    void givenAuthenticationReceivedValidAppConfigWithNonEmptyLastLogoutTimeThen200() {
        Authentication authentication = GetAuthenticationObj.getValidAuthenticationObj(userDao, authenticationManager);
        DhAppConfig dhAppConfig = new DhAppConfig();
        dhAppConfig.setStatus(AppConstants.STATUS_ACTIVE);
        when(appConfigDao.getActiveAppConfig()).thenReturn(Optional.of(dhAppConfig));

        Response<LoginResponse> actualResponse = strategyGetLoginResponse.getUserDetails(authentication,null, "","2020-12-21 06:29:40");
        Response<LoginResponse> expectedResponse = new Response<>(true,
                200,
                ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_USER_AND_APPCONFIG_DETAILS_FETCHED)
                , null
                , 1);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse.getMessage()));
        assertTrue(actualResponse.getData() != null && actualResponse.getData().get(0) != null && actualResponse.getData().get(0).getDhUser() != null && actualResponse.getData().get(0).getDhAppConfig() != null);
    }
}
