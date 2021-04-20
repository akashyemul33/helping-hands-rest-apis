package com.ayprojects.helpinghands.services.common_services;

import com.ayprojects.helpinghands.services.common_service.CommonService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class UpdateUserWithSessionDetails {

    @Autowired
    CommonService commonService;

    @Test
    void contextShouldBeLoaded() {
        assertNotNull(commonService);
    }

    @Test
    void givenEmptyFcmTokenThenFalse() {
        assertFalse(commonService.updateUserWithSessionAndOtherDetails(null, null, null));
    }

    @Test
    void givenNonEmptyFcmTokenButDhUserEmptyThenFalse() {
        assertFalse(commonService.updateUserWithSessionAndOtherDetails("abcdk2", null, null));
    }

    @Test
    void validateDetailsInDhUserObjManually() {
        //TODO
        assert fail("Validate the details manually and then mark assertions true");
    }
}
