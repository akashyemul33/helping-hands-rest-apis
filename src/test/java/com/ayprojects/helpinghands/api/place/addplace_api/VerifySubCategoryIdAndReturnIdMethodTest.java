package com.ayprojects.helpinghands.api.place.addplace_api;

import com.ayprojects.helpinghands.api.classes.add_strategy.StrategyAddPlaceApi;
import com.ayprojects.helpinghands.dao.placecategories.PlaceCategoryDao;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.models.LangValueObj;
import com.ayprojects.helpinghands.models.PlaceSubCategories;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;
@SpringBootTest
public class VerifySubCategoryIdAndReturnIdMethodTest {

    @Autowired
    private StrategyAddPlaceApi strategyAddPlaceApi;

    @MockBean
    private PlaceCategoryDao placeCategoryDao;

    @Test
    void placeCategoryShouldBeLoaded() {
        assertNotNull(placeCategoryDao);
    }

    @Test
    void strategyAddPlaceApiShouldBeLoaded() {
        assertNotNull(strategyAddPlaceApi);
    }

    @Test
    void givenSubCategoryId_existsInListById_thenGivenSubCategoryId() {
        List<PlaceSubCategories> subCategoriesList = new ArrayList<>();
        PlaceSubCategories ps1 = new PlaceSubCategories();
        ps1.setPlaceSubCategoryId("sub1");
        ps1.setDefaultName("subD1");
        PlaceSubCategories ps2 = new PlaceSubCategories();
        ps2.setPlaceSubCategoryId("sub2");
        ps2.setDefaultName("subD2");
        subCategoriesList.add(ps1);
        subCategoriesList.add(ps2);
        DhPlaceCategories dhPlaceCategories = new DhPlaceCategories();
        dhPlaceCategories.setPlaceCategoryId("main123");
        dhPlaceCategories.setPlaceSubCategories(subCategoriesList);
        when(placeCategoryDao.findByPlaceCategoryId("main123")).thenReturn(java.util.Optional.of(dhPlaceCategories));

        DhPlace dhPlace = new DhPlace();
        dhPlace.setPlaceSubCategoryId("sub1");
        dhPlace.setPlaceMainCategoryId("main123");
        String subCategoryId = strategyAddPlaceApi.verifySubCategoryAndReturnId(dhPlace);
        assertEquals(dhPlace.getPlaceSubCategoryId(), subCategoryId);
    }

    @Test
    void givenSubCategoryId_existsInListByDefaultName_thenSubCategoryId() {
        List<PlaceSubCategories> subCategoriesList = new ArrayList<>();
        PlaceSubCategories ps1 = new PlaceSubCategories();
        ps1.setPlaceSubCategoryId("sub1");
        ps1.setDefaultName("subD1");
        PlaceSubCategories ps2 = new PlaceSubCategories();
        ps2.setPlaceSubCategoryId("sub2");
        ps2.setDefaultName("subD2");
        subCategoriesList.add(ps1);
        subCategoriesList.add(ps2);
        DhPlaceCategories dhPlaceCategories = new DhPlaceCategories();
        dhPlaceCategories.setPlaceCategoryId("main123");
        dhPlaceCategories.setPlaceSubCategories(subCategoriesList);
        when(placeCategoryDao.findByPlaceCategoryId("main123")).thenReturn(java.util.Optional.of(dhPlaceCategories));

        DhPlace dhPlace = new DhPlace();
        dhPlace.setPlaceSubCategoryId("sub123");
        dhPlace.setPlaceSubCategoryName("subD1");
        dhPlace.setPlaceMainCategoryId("main123");
        String subCategoryId = strategyAddPlaceApi.verifySubCategoryAndReturnId(dhPlace);
        assertEquals(ps1.getPlaceSubCategoryId(), subCategoryId);
    }

    @Test
    void givenEmptySubCategoryId_existsInListByDefaultName_thenSubCategoryId() {
        List<PlaceSubCategories> subCategoriesList = new ArrayList<>();
        PlaceSubCategories ps1 = new PlaceSubCategories();
        ps1.setPlaceSubCategoryId("sub1");
        ps1.setDefaultName("subD1");
        PlaceSubCategories ps2 = new PlaceSubCategories();
        ps2.setPlaceSubCategoryId("sub2");
        ps2.setDefaultName("subD2");
        subCategoriesList.add(ps1);
        subCategoriesList.add(ps2);
        DhPlaceCategories dhPlaceCategories = new DhPlaceCategories();
        dhPlaceCategories.setPlaceCategoryId("main123");
        dhPlaceCategories.setPlaceSubCategories(subCategoriesList);
        when(placeCategoryDao.findByPlaceCategoryId("main123")).thenReturn(java.util.Optional.of(dhPlaceCategories));

        DhPlace dhPlace = new DhPlace();
        dhPlace.setPlaceSubCategoryId("");
        dhPlace.setPlaceSubCategoryName("subD1");
        dhPlace.setPlaceMainCategoryId("main123");
        String subCategoryId = strategyAddPlaceApi.verifySubCategoryAndReturnId(dhPlace);
        assertEquals(ps1.getPlaceSubCategoryId(), subCategoryId);
    }

    //this test failing when executed all, but passes if executed single
    @Test
    void givenEmptySubCategoryId_existsInListByTranslation_thenSubCategoryId() {
        List<PlaceSubCategories> subCategoriesList = new ArrayList<>();
        PlaceSubCategories ps1 = new PlaceSubCategories();
        ps1.setPlaceSubCategoryId("sub1");
        ps1.setDefaultName("subD1");
        List<LangValueObj> ps1LangValueObjList = new ArrayList<>();
        ps1LangValueObjList.add(new LangValueObj("en", "subD1"));
        ps1LangValueObjList.add(new LangValueObj("mr", "पहले"));
        ps1.setTranslations(ps1LangValueObjList);
        PlaceSubCategories ps2 = new PlaceSubCategories();
        ps2.setPlaceSubCategoryId("sub2");
        ps2.setDefaultName("subD2");
        subCategoriesList.add(ps1);
        subCategoriesList.add(ps2);
        DhPlaceCategories dhPlaceCategories = new DhPlaceCategories();
        dhPlaceCategories.setPlaceCategoryId("main1234");
        dhPlaceCategories.setPlaceSubCategories(subCategoriesList);
        when(placeCategoryDao.findByPlaceCategoryId("main1234")).thenReturn(java.util.Optional.of(dhPlaceCategories));

        DhPlace dhPlace = new DhPlace();
        dhPlace.setPlaceSubCategoryName("पहले");
        dhPlace.setPlaceMainCategoryId("main1234");
        LOGGER.info("ps1.translations.size="+dhPlaceCategories.getPlaceSubCategories().get(0).getTranslations().size());
        String subCategoryId = strategyAddPlaceApi.verifySubCategoryAndReturnId(dhPlace);
        assertEquals(ps1.getPlaceSubCategoryId(), subCategoryId);
    }

    @Test
    void givenEmptySubCategoryId_notExistsInList_thenEmpty() {
        List<PlaceSubCategories> subCategoriesList = new ArrayList<>();
        PlaceSubCategories ps1 = new PlaceSubCategories();
        ps1.setPlaceSubCategoryId("sub1");
        ps1.setDefaultName("subD1");
        List<LangValueObj> ps1LangValueObjList = new ArrayList<>();
        ps1LangValueObjList.add(new LangValueObj("en", "subD1"));
        ps1LangValueObjList.add(new LangValueObj("mr", "पहले"));
        ps1.setTranslations(ps1LangValueObjList);
        PlaceSubCategories ps2 = new PlaceSubCategories();
        ps2.setPlaceSubCategoryId("sub2");
        ps2.setDefaultName("subD2");
        subCategoriesList.add(ps1);
        subCategoriesList.add(ps2);
        DhPlaceCategories dhPlaceCategories = new DhPlaceCategories();
        dhPlaceCategories.setPlaceCategoryId("main123");
        dhPlaceCategories.setPlaceSubCategories(subCategoriesList);
        when(placeCategoryDao.findByPlaceCategoryId("abcd")).thenReturn(java.util.Optional.of(dhPlaceCategories));

        DhPlace dhPlace = new DhPlace();
        dhPlace.setPlaceSubCategoryId("");
        dhPlace.setPlaceSubCategoryName("पहले23");
        dhPlace.setPlaceMainCategoryId("abcd");
        String subCategoryId = strategyAddPlaceApi.verifySubCategoryAndReturnId(dhPlace);
        assertTrue(Utility.isFieldEmpty(subCategoryId));
    }
}
