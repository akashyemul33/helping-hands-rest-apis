package com.ayprojects.helpinghands.api.classes;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.models.DhLog;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhProduct;
import com.ayprojects.helpinghands.models.ProductsWithPrices;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collections;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

public class CommonMethods {
    public static String prepareDhProductAndStoreItInDb(String language, ProductsWithPrices productsWithPrices, DhPlace dhPlace, MongoTemplate mongoTemplate) {
        DhProduct queriedDhProduct = new DhProduct();
        queriedDhProduct.setProductId(Utility.getUUID());
        queriedDhProduct.setDefaultUnit(productsWithPrices.getSelectedUnit());
        queriedDhProduct.setMainPlaceCategoryId(dhPlace.getPlaceMainCategoryId());
        queriedDhProduct.setSubPlaceCategoryIds(Collections.singletonList(dhPlace.getPlaceSubCategoryId()));
        queriedDhProduct.setCategoryName(dhPlace.getPlaceCategoryName() + "->" + dhPlace.getPlaceSubCategoryName());
        queriedDhProduct.setAddedBy(dhPlace.getAddedBy());
        queriedDhProduct.setDefaultName(productsWithPrices.getUserEnteredProductName());
        queriedDhProduct.setAvgPrice(productsWithPrices.getProductPrice());
        queriedDhProduct = (DhProduct) ApiOperations.setCommonAttrs(queriedDhProduct, AppConstants.STATUS_ACTIVE);
        mongoTemplate.save(queriedDhProduct, AppConstants.COLLECTION_DH_PRODUCT);
        DhLog dhLog = new DhLog(dhPlace.getAddedBy(), "Product [" + productsWithPrices.getUserEnteredProductName() + "] has been added under [" + dhPlace.getPlaceSubCategoryName() + "->" + dhPlace.getSubscribedUsers() + "].");
        mongoTemplate.save(dhLog, AppConstants.COLLECTION_DHLOG);
        return queriedDhProduct.getProductId();

    }

    public static String verifyProductIdAndReturnId(ProductsWithPrices productsWithPrices, MongoTemplate mongoTemplate) {
        if (Utility.isFieldEmpty(productsWithPrices.getProductId())) {

            Query queryToFindProduct = new Query(new Criteria().orOperator(Criteria.where(AppConstants.DEFAULT_NAME).regex(productsWithPrices.getUserEnteredProductName(), "i"),
                    Criteria.where(AppConstants.TRANSLATIONS + ".value").regex(productsWithPrices.getUserEnteredProductName(), "i")
            ));
            DhProduct queriedDhProduct = mongoTemplate.findOne(queryToFindProduct, DhProduct.class);
            if (queriedDhProduct != null) {
                LOGGER.info("Found product with name " + productsWithPrices.getUserEnteredProductName() + " productId=" + productsWithPrices.getProductId());
                return queriedDhProduct.getProductId();
            }
        }
        return "";
    }
}
