package com.ayprojects.helpinghands.api.classes.get_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyGetBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.models.DhProduct;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Component
public class StrategyGetProducts implements StrategyGetBehaviour<DhProduct> {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<DhProduct> get(String language, HashMap<String, Object> params) {
        if (params == null) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS), new ArrayList<>());
        }
        Set<String> keySet = params.keySet();
        if (keySet.contains(AppConstants.KEY_SUB_PLACECATEGORY_ID)) {
            return getProductsBySubCategoryId(language, (String) params.get(AppConstants.KEY_SUB_PLACECATEGORY_ID));
        } else if (keySet.contains(AppConstants.KEY_PRODUCT_SEARCH)) {
            return searchProducts(language, (String) params.get(AppConstants.KEY_PRODUCT_SEARCH));
        } else {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS) + ": " + AppConstants.KEY_SUB_PLACECATEGORY_ID, new ArrayList<>());
        }
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.GetProductStrategy;
    }

    public Response<DhProduct> searchProducts(String language, String searchKey) {
        if (Utility.isFieldEmpty(searchKey)) {
            return new Response<DhProduct>(false, AppConstants.EMPTY_BODY_STATUS_CODE, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>(), 0);
        }
        //TODO write proper criteria later
        Query searchQuery = new Query(new Criteria().orOperator(Criteria.where(AppConstants.DEFAULT_NAME).regex(searchKey, "i"),
                Criteria.where(AppConstants.TRANSLATIONS + ".value").regex(searchKey, "i")
        ));
        searchQuery.addCriteria(Criteria.where(AppConstants.STATUS).is(AppConstants.STATUS_ACTIVE));
        List<DhProduct> dhProductList = mongoTemplate.find(searchQuery, DhProduct.class);
        if (dhProductList.isEmpty()) {
            return new Response<DhProduct>(false, AppConstants.EMPTY_RESPONSE_STATUS_CODE, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NOT_FOUND_ANY_PRODUCTS_WITH_SEARCH_KEY), new ArrayList<>(), 0);
        }
        return new Response<DhProduct>(true, AppConstants.QUERY_SUCCESSFUL_STATUS_CODE, AppConstants.QUERY_SUCCESSFUL, dhProductList, dhProductList.size());
    }

    public Response<DhProduct> getProductsBySubCategoryId(String language, String subPlaceCategoryId) {

        if (Utility.isFieldEmpty(subPlaceCategoryId)) {
            return new Response<DhProduct>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>(), 0);
        }
        Query queryToFindProductsWithSubCategoryId = new Query(Criteria.where(AppConstants.SUB_PLACE_CATEGORY_ID).is(subPlaceCategoryId));
//        queryToFindProductsWithSubCategoryId.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        List<DhProduct> dhProductList = mongoTemplate.find(queryToFindProductsWithSubCategoryId, DhProduct.class);

        if (dhProductList.isEmpty()) {
            return new Response<DhProduct>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NO_PRODUCTS_FOUND_FOR_SUBCATEGORYID), new ArrayList<>(), 0);
        }

        return new Response<DhProduct>(true, 200, AppConstants.QUERY_SUCCESSFUL, dhProductList, dhProductList.size());
    }
}
