package com.ayprojects.helpinghands.services.products;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.models.DhProduct;
import com.ayprojects.helpinghands.models.PlaceSubCategories;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.tools.Utility;
import com.ayprojects.helpinghands.util.tools.Validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    Utility utility;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<DhProduct> addProducts(Authentication authentication, HttpHeaders httpHeaders, List<DhProduct> dhProducts, String version) throws ServerSideException {
        return null;
    }

    @Override
    public Response<DhProduct> addProduct(Authentication authentication, HttpHeaders httpHeaders, DhProduct dhProduct, String version) throws ServerSideException {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("ProductsServiceImpl->addProduct : language=" + language);

        if (dhProduct == null) {
            return new Response<DhProduct>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language), new ArrayList<>(), 0);
        }

        //validate all other stuffs
        List<String> missingFieldsList = Validations.findMissingFieldsForProducts(dhProduct);
        if (missingFieldsList.size() > 0) {
            String resMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language);
            resMsg = resMsg + " , these fields are missing : " + missingFieldsList;
            return new Response<DhProduct>(false, 402, resMsg, new ArrayList<>(), 0);
        }

        Query queryFindProduct = new Query(new Criteria().orOperator(Criteria.where(AppConstants.DEFAULT_NAME).regex(dhProduct.getDefaultName(), "i"),
                Criteria.where(AppConstants.TRANSLATIONS + ".value").regex(dhProduct.getDefaultName(), "i")
        ));
        DhProduct existingDhProduct = mongoTemplate.findOne(queryFindProduct, DhProduct.class);
        if (existingDhProduct != null) {
            return new Response<DhProduct>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_PRODUCT_ALREADY_EXISTS, language) + " ProductName : " + dhProduct.getDefaultName(), new ArrayList<>(), 1);
        }

        Query queryFindCategoryWithId = new Query(Criteria.where(AppConstants.PLACE_MAIN_CATEGORY_ID).is(dhProduct.getMainPlaceCategoryId()));
        queryFindCategoryWithId.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        DhPlaceCategories queriedDhPlaceCategories = mongoTemplate.findOne(queryFindCategoryWithId, DhPlaceCategories.class);
        if (queriedDhPlaceCategories == null || queriedDhPlaceCategories.getPlaceSubCategories() == null || queriedDhPlaceCategories.getPlaceSubCategories().size() == 0) {
            return new Response<DhProduct>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NOT_FOUND_PLACECATEGORIY_WITH_ID, language) + "ID : " + dhProduct.getMainPlaceCategoryId(), new ArrayList<>(), 0);
        }

        for (PlaceSubCategories placeSubCategory : queriedDhPlaceCategories.getPlaceSubCategories()) {
            if (dhProduct.getSubPlaceCategoryId().equalsIgnoreCase(placeSubCategory.getPlaceSubCategoryId())) {
                dhProduct.setAddedBy(dhProduct.getAddedBy());
                dhProduct.setProductId(Utility.getUUID());
                dhProduct.setCategoryName(queriedDhPlaceCategories.getDefaultName() + "->" + placeSubCategory.getDefaultName());
                dhProduct = (DhProduct) utility.setCommonAttrs(dhProduct, AppConstants.STATUS_PENDING);
                mongoTemplate.save(dhProduct, AppConstants.COLLECTION_DH_PRODUCT);
                utility.addLog(authentication.getName(), "Product [" + dhProduct.getDefaultName() + "] has been added under [" + queriedDhPlaceCategories.getDefaultName() + "->" + placeSubCategory.getDefaultName() + "].");
                String resMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NEW_PRODUCT_ADDED, language) + " ProductName:" + dhProduct.getDefaultName() + " -> " + placeSubCategory.getDefaultName();
                return new Response<DhProduct>(true, 201, resMsg, Collections.singletonList(dhProduct), 1);
            }
            String resMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NOT_FOUND_PLACECATEGORIY_WITH_ID, language) + " PlaceSubCategoryId:" + dhProduct.getSubPlaceCategoryId();
            return new Response<DhProduct>(false, 402, resMsg, new ArrayList<>(), 0);
        }

        throw new ServerSideException(AppConstants.MSG_SOMETHING_WENT_WRONG);
    }

    @Override
    public Response<DhProduct> findProductsBySubCategoryId(Authentication authentication, HttpHeaders httpHeaders, String subPlaceCategoryId, String version) {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("ProductsServiceImpl->findProductsBySubCategoryId : language=" + language + " default lang=" + AppConstants.LANG_ENGLISH);
        if (Utility.isFieldEmpty(language)) language = AppConstants.LANG_ENGLISH;

        if (Utility.isFieldEmpty(subPlaceCategoryId)) {
            return new Response<DhProduct>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language), new ArrayList<>(), 0);
        }
        Query queryToFindProductsWithSubCategoryId = new Query(Criteria.where(AppConstants.SUB_PLACE_CATEGORY_ID).is(subPlaceCategoryId));
//        queryToFindProductsWithSubCategoryId.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        List<DhProduct> dhProductList = mongoTemplate.find(queryToFindProductsWithSubCategoryId, DhProduct.class);

        if (dhProductList.size() <= 0) {
            return new Response<DhProduct>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NO_PRODUCTS_FOUND_FOR_SUBCATEGORYID, language), new ArrayList<>(), 0);
        }

        return new Response<DhProduct>(true, 200, AppConstants.QUERY_SUCCESSFUL, dhProductList, dhProductList.size());
    }
}
