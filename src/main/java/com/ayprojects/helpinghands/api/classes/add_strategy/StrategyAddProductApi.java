package com.ayprojects.helpinghands.api.classes.add_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.behaviours.StrategyAddBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.models.DhProduct;
import com.ayprojects.helpinghands.models.PlaceSubCategories;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;

@Component
public class StrategyAddProductApi implements StrategyAddBehaviour<DhProduct> {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<DhProduct> add(String language, DhProduct dhProduct) throws ServerSideException {
        Response<DhProduct> returnResponse = validateAddDhProduct(dhProduct, language);
        if (returnResponse.getStatus()) {
            if (checkProductAlreadyExists(dhProduct)) {
                return new Response<DhProduct>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_PRODUCT_ALREADY_EXISTS) + " ProductName : " + dhProduct.getDefaultName(), new ArrayList<>(), 1);
            }
            DhPlaceCategories queriedDhPlaceCategories = findMainCategoryWithId(mongoTemplate, dhProduct);
            if (queriedDhPlaceCategories == null || queriedDhPlaceCategories.getPlaceSubCategories() == null || queriedDhPlaceCategories.getPlaceSubCategories().size() == 0) {
                return new Response<DhProduct>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NOT_FOUND_PLACECATEGORIY_WITH_ID) + "ID : " + dhProduct.getMainPlaceCategoryId(), new ArrayList<>(), 0);
            }

            for (PlaceSubCategories placeSubCategory : queriedDhPlaceCategories.getPlaceSubCategories()) {
                boolean subCategoryfound = false;
                for (String subCategoryId : dhProduct.getSubPlaceCategoryIds()) {
                    if (subCategoryId.equalsIgnoreCase(placeSubCategory.getPlaceSubCategoryId())) {
                        /*dhProduct.setAddedBy(dhProduct.getAddedBy());
                        dhProduct.setProductId(Utility.getUUID());
                        if (Utility.isFieldEmpty(dhProduct.getUnitQty())) dhProduct.setUnitQty("1");
                        if (Utility.isFieldEmpty(dhProduct.getDefaultUnit()))
                            dhProduct.setDefaultUnit(AppConstants.DEFAULT_UNIT_IF_EMPTY);
                        dhProduct.setCategoryName(queriedDhPlaceCategories.getDefaultName());
                        dhProduct = (DhProduct) ApiOperations.setCommonAttrs(dhProduct, AppConstants.STATUS_PENDING);
                        mongoTemplate.save(dhProduct, AppConstants.COLLECTION_DH_PRODUCT);
                        String resMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NEW_PRODUCT_ADDED) + " ProductName:" + dhProduct.getDefaultName() + " -> " + placeSubCategory.getDefaultName();
                        returnResponse.setLogActionMsg("Product [" + dhProduct.getDefaultName() + "] has been added under [" + queriedDhPlaceCategories.getDefaultName() + "->" + placeSubCategory.getDefaultName() + "].");
                        return new Response<DhProduct>(true, 201, resMsg, Collections.singletonList(dhProduct), 1);*/
                        dhProduct.getSubCategoryNames().add(placeSubCategory.defaultName);
                        subCategoryfound = true;
                    }
                }
                if(subCategoryfound){
                    dhProduct.setAddedBy(dhProduct.getAddedBy());
                    dhProduct.setProductId(Utility.getUUID());
                    if (Utility.isFieldEmpty(dhProduct.getUnitQty())) dhProduct.setUnitQty("1");
                    if (Utility.isFieldEmpty(dhProduct.getDefaultUnit()))
                        dhProduct.setDefaultUnit(AppConstants.DEFAULT_UNIT_IF_EMPTY);
                    dhProduct.setCategoryName(queriedDhPlaceCategories.getDefaultName());
                    dhProduct = (DhProduct) ApiOperations.setCommonAttrs(dhProduct, AppConstants.STATUS_PENDING);
                    mongoTemplate.save(dhProduct, AppConstants.COLLECTION_DH_PRODUCT);
                    String resMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NEW_PRODUCT_ADDED) + " ProductName:" + dhProduct.getDefaultName() + " -> " + placeSubCategory.getDefaultName();
                    returnResponse.setLogActionMsg("Product [" + dhProduct.getDefaultName() + "] has been added under [" + queriedDhPlaceCategories.getDefaultName() + "->" + placeSubCategory.getDefaultName() + "].");
                    return new Response<DhProduct>(true, 201, resMsg, Collections.singletonList(dhProduct), 1);
                }
                else {
                    String resMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NOT_FOUND_PLACECATEGORIY_WITH_ID) + " PlaceSubCategoryId:" + dhProduct.getSubPlaceCategoryIds();
                    return new Response<DhProduct>(false, 402, resMsg, new ArrayList<>(), 0);
                }
            }
            String resMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NOT_FOUND_PLACECATEGORIY_WITH_ID) + " PlaceSubCategoryId:" + dhProduct.getSubPlaceCategoryIds();
            return new Response<DhProduct>(false, 402, resMsg, new ArrayList<>(), 0);
        }
        return returnResponse;
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.AddProductStrategy;
    }

    private DhPlaceCategories findMainCategoryWithId(MongoTemplate mongoTemplate, DhProduct dhProduct) {
        Query queryFindCategoryWithId = new Query(Criteria.where(AppConstants.PLACE_MAIN_CATEGORY_ID).is(dhProduct.getMainPlaceCategoryId()));
        queryFindCategoryWithId.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        return mongoTemplate.findOne(queryFindCategoryWithId, DhPlaceCategories.class);
    }

    private boolean checkProductAlreadyExists(DhProduct dhProduct) {
        Query queryFindProduct = new Query(new Criteria().orOperator(Criteria.where(AppConstants.DEFAULT_NAME).regex(dhProduct.getDefaultName(), "i"),
                Criteria.where(AppConstants.TRANSLATIONS + ".value").regex(dhProduct.getDefaultName(), "i")
        ));
        DhProduct existingDhProduct = mongoTemplate.findOne(queryFindProduct, DhProduct.class);
        if (existingDhProduct != null) {
            return true;
        }
        return false;
    }

    private Response<DhProduct> validateAddDhProduct(DhProduct dhProduct, String language) {
        if (dhProduct == null) {
            return new Response<DhProduct>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>(), 0);
        }

        if (Utility.isFieldEmpty(dhProduct.getMainPlaceCategoryId()))
            return new Response<DhProduct>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MAINCATEGORYID_MISSING), new ArrayList<>(), 0);

        if (dhProduct.getSubPlaceCategoryIds() == null || dhProduct.getSubPlaceCategoryIds().isEmpty()) {
            return new Response<DhProduct>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_SUBCATEGORYID_MISSING), new ArrayList<>(), 0);
        }

        if (Utility.isFieldEmpty(dhProduct.getDefaultName())) {
            return new Response<DhProduct>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_DEFAULTNAME), new ArrayList<>(), 0);
        }
        String resMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NEW_PRODUCT_ADDED) + " ProductName:" + dhProduct.getDefaultName();
        return new Response<DhProduct>(true, 201, resMsg, Collections.singletonList(dhProduct), 1);
    }
}
