package com.ayprojects.helpinghands.api.place_main_category;

import com.ayprojects.helpinghands.api.classes.AddPlaceMainCategoryApi;
import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class GetDhPlaceCategoryByDefaultNameTest {
    private static AddPlaceMainCategoryApi addPlaceCategoryApi;
    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeAll
    static void setup(){
        addPlaceCategoryApi = new AddPlaceMainCategoryApi();
    }
    @Test
    void givenEmptyDefaultNameThenException() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                addPlaceCategoryApi.getDhPlaceCategoryByDefaultName(null, mongoTemplate);
            }
        });
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                addPlaceCategoryApi.getDhPlaceCategoryByDefaultName(null, mongoTemplate);
            }
        });
    }

    @Test
    void givenNonExistDefaultNameThenNull(){
        //TODO
    }

    @Test
    void givenAlreadyExistDefaultNameThenNonEmptyObject(){
        //TODO
    }
}
