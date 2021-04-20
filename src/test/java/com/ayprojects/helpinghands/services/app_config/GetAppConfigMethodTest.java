package com.ayprojects.helpinghands.services.app_config;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.dao.appconfig.AppConfigDao;
import com.ayprojects.helpinghands.dao.user.UserDao;
import com.ayprojects.helpinghands.models.DhAppConfig;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.GetAuthenticationObj;
import com.ayprojects.helpinghands.services.appconfig.AppConfigService;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;
@SpringBootTest
public class GetAppConfigMethodTest {

    @Autowired
    AppConfigService appConfigService;

    @MockBean
    AppConfigDao appConfigDao;

    @MockBean
    UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Test
    void appConfigDaoShouldBeLoaded() {
        assertNotNull(appConfigDao);
    }


    @Test
    void appConfigServiceShouldBeLoaded() {
        assertNotNull(appConfigService);
    }

    @Test
    void givenEmptyAuthenticatioObjectThen402() {
        Response<DhAppConfig> expectedResponse = new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_AUTHENTICATION_REQD), new ArrayList<>());
        Response<DhAppConfig> actualResponse = appConfigService.getActiveAppConfig(null, null, AppConstants.CURRENT_API_VERSION);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse.getMessage()));
    }


    @Test
    void ifReceivedEmptyAppConfigThen402() {
        when(appConfigDao.getActiveAppConfig()).thenReturn(null);
        Response<DhAppConfig> actualResponse = appConfigService.getActiveAppConfig(null, null, AppConstants.CURRENT_API_VERSION);
        assertFalse(actualResponse.getStatus());
        assertEquals(402, actualResponse.getStatusCode());
    }


    @Test
    void ifReceivedAppConfigWithStatusOtherThanActiveThen402() {
        Authentication authentication = GetAuthenticationObj.getValidAuthenticationObj(userDao,authenticationManager);
        DhAppConfig dhAppConfig = new DhAppConfig();
        dhAppConfig.setStatus(AppConstants.STATUS_PENDING);
        when(appConfigDao.getActiveAppConfig()).thenReturn(Optional.of(dhAppConfig));
        Response<DhAppConfig> actualResponse = appConfigService.getActiveAppConfig(null, authentication, AppConstants.CURRENT_API_VERSION);
        assertFalse(actualResponse.getStatus());
        assertEquals(402, actualResponse.getStatusCode());
    }

    @Test
    void ifReceivedValidAppConfigThen200() {
        Authentication authentication = GetAuthenticationObj.getValidAuthenticationObj(userDao,authenticationManager);
        LOGGER.info("isAuthenticated="+authentication.isAuthenticated());
        LOGGER.info("userName="+authentication.getName());
        DhAppConfig dhAppConfig = new DhAppConfig();
        dhAppConfig.setStatus(AppConstants.STATUS_ACTIVE);

        when(appConfigDao.getActiveAppConfig()).thenReturn(Optional.of(dhAppConfig));
        Response<DhAppConfig> actualResponse = appConfigService.getActiveAppConfig(null, authentication, AppConstants.CURRENT_API_VERSION);
        LOGGER.info("actualResponse="+actualResponse.getStatusCode()+" "+actualResponse.getStatus()+" "+actualResponse.getMessage());
        assertTrue(actualResponse.getStatus());
        assertEquals(200, actualResponse.getStatusCode());
    }
}
