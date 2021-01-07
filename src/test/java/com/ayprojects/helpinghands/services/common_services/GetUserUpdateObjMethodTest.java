package com.ayprojects.helpinghands.services.common_services;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.services.common_service.CommonServiceImpl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.data.mongodb.core.query.Update;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetUserUpdateObjMethodTest {

    static CommonServiceImpl commonServiceImpl;

    @BeforeAll
    static void setup() {
        commonServiceImpl = new CommonServiceImpl();
    }

    @Test
    void givenEmptyFcmTokenThenException() {
        assertThrows(IllegalArgumentException.class,
                new Executable() {
                    @Override
                    public void execute() throws Throwable {
                        commonServiceImpl.getUpdateObjForUser(null, null, null);
                    }
                });
    }

    @Test
    void givenNonEmptyFcm_emptyDhUserThenException() {
        assertThrows(IllegalArgumentException.class,
                new Executable() {
                    @Override
                    public void execute() throws Throwable {
                        commonServiceImpl.getUpdateObjForUser("adb", null, null);
                    }
                });
    }

    @Test
    void givenNonEmptyFcm_nonEmptyDhUserWithEmptyUserId_ThenException() {
        DhUser dhUser = new DhUser();
        dhUser.setUserId("");
        assertThrows(IllegalArgumentException.class,
                new Executable() {
                    @Override
                    public void execute() throws Throwable {
                        commonServiceImpl.getUpdateObjForUser("adb", null, dhUser);
                    }
                });
    }


    @Test
    void ifFcmTokenMatchesEmptyLastLogoutTimeThenShouldNotUpdateFcmtToken() {
        DhUser dhUser = new DhUser();
        dhUser.setUserId("123abc");
        dhUser.setFcmToken("abdcefg");
        Update update = commonServiceImpl.getUpdateObjForUser("abdcefg", null, dhUser);
        String updateObjStr = update.getUpdateObject().toJson();
        LOGGER.info("ifFcmTokenMatchesEmptyLastLogoutTimeThenItShouldBeUpdated->update." + updateObjStr);
        assertTrue(updateObjStr.contains(AppConstants.TRIED_TO_LOGIN_TIME));
        assertTrue(updateObjStr.contains(AppConstants.MODIFIED_DATE_TIME));
        assertFalse(updateObjStr.contains(AppConstants.KEY_FCM_TOKEN));
        assertFalse(updateObjStr.contains(AppConstants.LAST_LOGIN_TIME));
        assertFalse(updateObjStr.contains(AppConstants.KEY_LAST_LOGOUT_TIME));
        assertFalse(updateObjStr.contains(AppConstants.LOGIN_TIME));
    }

    @Test
    void ifFcmTokenNotMatchesEmptyLastLogoutTimeThenShouldUpdateFcmToken() {
        DhUser dhUser = new DhUser();
        dhUser.setUserId("123abc");
        dhUser.setFcmToken("");
        Update update = commonServiceImpl.getUpdateObjForUser("abdcefg", null, dhUser);
        String updateObjStr = update.getUpdateObject().toJson();
        LOGGER.info("ifFcmTokenMatchesEmptyLastLogoutTimeThenItShouldBeUpdated->update." + updateObjStr);
        assertTrue(updateObjStr.contains(AppConstants.TRIED_TO_LOGIN_TIME));
        assertTrue(updateObjStr.contains(AppConstants.MODIFIED_DATE_TIME));
        assertTrue(updateObjStr.contains(AppConstants.KEY_FCM_TOKEN));
        assertFalse(updateObjStr.contains(AppConstants.LAST_LOGIN_TIME));
        assertFalse(updateObjStr.contains(AppConstants.KEY_LAST_LOGOUT_TIME));
        assertFalse(updateObjStr.contains(AppConstants.LOGIN_TIME));
    }

    @Test
    void ifFcmTokenNotMatchesNonEmptyLastLogoutTimeThenShouldUpdateSessionDetails() {
        DhUser dhUser = new DhUser();
        dhUser.setUserId("123abc");
        dhUser.setFcmToken("");
        dhUser.setLogInTime("2020-12-20 06:29:40");
        Update update = commonServiceImpl.getUpdateObjForUser("abdcefg", "2020-12-21 06:29:40", dhUser);
        String updateObjStr = update.getUpdateObject().toJson();
        LOGGER.info("ifFcmTokenMatchesEmptyLastLogoutTimeThenItShouldBeUpdated->update." + updateObjStr);
        assertTrue(updateObjStr.contains(AppConstants.TRIED_TO_LOGIN_TIME));
        assertTrue(updateObjStr.contains(AppConstants.MODIFIED_DATE_TIME));
        assertTrue(updateObjStr.contains(AppConstants.KEY_FCM_TOKEN));
        assertTrue(updateObjStr.contains(AppConstants.LAST_LOGIN_TIME));
        assertTrue(updateObjStr.contains(AppConstants.KEY_LAST_LOGOUT_TIME));
        assertTrue(updateObjStr.contains(AppConstants.LOGIN_TIME));
    }
}
