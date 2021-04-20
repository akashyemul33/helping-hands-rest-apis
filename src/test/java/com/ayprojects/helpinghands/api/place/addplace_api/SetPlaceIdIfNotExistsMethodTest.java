package com.ayprojects.helpinghands.api.place.addplace_api;

import com.ayprojects.helpinghands.api.classes.add_strategy.StrategyAddPlaceApi;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.services.common_service.CommonService;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class SetPlaceIdIfNotExistsMethodTest {

    @Autowired
    CommonService commonService;

    @Autowired
    StrategyAddPlaceApi strategyAddPlaceApi;

    @Test
    void strategyAddPlaceApiShouldBeLoaded() {
        assertNotNull(strategyAddPlaceApi);
    }

    @Test
    void givenEmptyPlaceIdThenObjWithPlaceId() {
        DhPlace dhPlace = new DhPlace();
        dhPlace.setPlaceId("");
        DhPlace returnDhPlace = strategyAddPlaceApi.setPlaceIdIfNotExists(dhPlace);
        assertFalse(Utility.isFieldEmpty(returnDhPlace.getPlaceId()));
    }

    @Test
    void givenNonEmptyPlaceIdThenObjWithPlaceId() {
        DhPlace dhPlace = new DhPlace();
        dhPlace.setPlaceId("qeradf1323");
        DhPlace returnDhPlace = strategyAddPlaceApi.setPlaceIdIfNotExists(dhPlace);
        assertEquals(dhPlace.getPlaceId(), returnDhPlace.getPlaceId());
    }

}
