package com.ayprojects.helpinghands.api.place.addplace_api;

import com.ayprojects.helpinghands.api.classes.AddPlaceApi;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.services.common_service.CommonService;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class SetPlaceIdIfNotExistsMethodTest {

    @Autowired
    CommonService commonService;

    @Test
    void givenEmptyPlaceIdThenObjWithPlaceId() {
        AddPlaceApi addPlaceApi = new AddPlaceApi(commonService);
        DhPlace dhPlace = new DhPlace();
        dhPlace.setPlaceId("");
        DhPlace returnDhPlace = addPlaceApi.setPlaceIdIfNotExists(dhPlace);
        assertFalse(Utility.isFieldEmpty(returnDhPlace.getPlaceId()));
    }

    @Test
    void givenNonEmptyPlaceIdThenObjWithPlaceId() {
        AddPlaceApi addPlaceApi = new AddPlaceApi(commonService);
        DhPlace dhPlace = new DhPlace();
        dhPlace.setPlaceId("qeradf1323");
        DhPlace returnDhPlace = addPlaceApi.setPlaceIdIfNotExists(dhPlace);
        assertEquals(dhPlace.getPlaceId(), returnDhPlace.getPlaceId());
    }

}
