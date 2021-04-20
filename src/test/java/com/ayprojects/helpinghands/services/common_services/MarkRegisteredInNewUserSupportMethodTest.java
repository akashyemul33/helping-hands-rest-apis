package com.ayprojects.helpinghands.services.common_services;

import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.services.common_service.CommonService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MarkRegisteredInNewUserSupportMethodTest {

    @Autowired
    CommonService commonService;

    @Test
    void contextShouldBeLoaded() {
        assertNotNull(commonService);
    }

    @Test
    void givenEmptyDhUserThenFalse() {
        assertFalse(commonService.markRegisteredInNewUserSupport(null));
    }

    @Test
    void givenEmptyUserIdInDhUserThenFalse() {
        DhUser dhUser = new DhUser();
        dhUser.setUserId("");
        assertFalse(commonService.markRegisteredInNewUserSupport(dhUser));
    }

    @Test
    void givenEmptyMobileInDhUserThenFalse() {
        DhUser dhUser = new DhUser();
        dhUser.setUserId("asdlfkj");
        dhUser.setMobileNumber("");
        assertFalse(commonService.markRegisteredInNewUserSupport(dhUser));
    }

    @Test
    void givenMobileOrFcmMatchesThenUpdate(){
        //TODO mock dhNewUserSupport and validate result

        DhUser dhUser = new DhUser();
        dhUser.setUserId("asdlfkj");
        dhUser.setMobileNumber("");
    }
}
