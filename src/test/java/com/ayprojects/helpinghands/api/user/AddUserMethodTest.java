package com.ayprojects.helpinghands.api.user;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.classes.add_strategy.StrategyAddUserApi;
import com.ayprojects.helpinghands.dao.user.UserDao;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


@SpringBootTest
public class AddUserMethodTest {
    @Autowired
    StrategyAddUserApi strategyAddUserApi;

    @MockBean
    UserDao userDao;

    @Test
    void userDaoShouldBeLoaded() {
        assertNotNull(userDao);
    }

    @Test
    void strategyAddUserApiShouldBeLoaded() {
        assertNotNull(strategyAddUserApi);
    }

    @Test
    void ifValidateAddUserReturnsFalseStatusThenFail() {
        String mobile = "7987787878";
        DhUser dhUser = new DhUser();
        dhUser.setMobileNumber(mobile);
        dhUser.setStatus(AppConstants.STATUS_ACTIVE);
        when(userDao.findByMobileNumber(mobile)).thenReturn(java.util.Optional.of(dhUser));

        Response<DhUser> expectedResponse = new Response<DhUser>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_MOBILE_ALREADY_USED), new ArrayList<>());
        Response<DhUser> actualResponse = strategyAddUserApi.add(null, dhUser);
        ;
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse.getMessage()));
    }

    @Test
    void userShouldBePersistedIfValidObject() {
        String mobile = "7987787878";
        DhUser dhUser = new DhUser();
        dhUser.setMobileNumber(mobile);
        dhUser.setProfileImg("adf");
        dhUser.setUserId("zfgdfgrt463sdg");
        dhUser.setPassword("adsfkljk");
        dhUser.setEmailId("adkf@gmail.com");
        dhUser.setFirstName("adsf");
        dhUser.setLastName("asdfdf");

        Response<DhUser> expectedResponse = new Response<>(true, 201, dhUser.getFirstName() + " Sir/Madam", ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_USER_REGISTERED), Collections.singletonList(dhUser));
        Response<DhUser> actualResponse = strategyAddUserApi.add(null, dhUser);
        ;
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse.getMessage()));

        DhUser addedDhUser = actualResponse.getData().get(0);
        assertFalse(Utility.isFieldEmpty(addedDhUser.getUserId()));
        assertFalse(Utility.isFieldEmpty(addedDhUser.getCreatedDateTime()));
        assertFalse(Utility.isFieldEmpty(addedDhUser.getModifiedDateTime()));
        LOGGER.info("userShouldBePersistedIfValidObject->" + actualResponse.getMessage());
    }
}
