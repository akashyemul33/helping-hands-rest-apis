package com.ayprojects.helpinghands.api.place.addplace_api;

import com.ayprojects.helpinghands.api.classes.add_strategy.StrategyAddPlaceApi;
import com.ayprojects.helpinghands.dao.products.ProductDao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class VerifyProductIdAndReturnIdMethodTest {

    @Autowired
    private StrategyAddPlaceApi strategyAddPlaceApi;

    @MockBean
    private ProductDao productDao;

    @Test
    void strategyAddPlaceApiShouldBeLoaded() {
        assertNotNull(strategyAddPlaceApi);
    }


}
