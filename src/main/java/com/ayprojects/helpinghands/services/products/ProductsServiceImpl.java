package com.ayprojects.helpinghands.services.products;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.dao.products.ProductDao;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhLog;
import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.models.DhProduct;
import com.ayprojects.helpinghands.models.PlaceSubCategories;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.log.LogService;
import com.ayprojects.helpinghands.tools.Utility;

import org.bson.AbstractBsonReader;
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
import java.util.Optional;

import javax.rmi.CORBA.Util;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class ProductsServiceImpl implements ProductsService{

    @Autowired
    Utility utility;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<DhProduct> addProduct(Authentication authentication, HttpHeaders httpHeaders, DhProduct dhProduct, String version) throws ServerSideException {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("ProductsServiceImpl->addProduct : language=" + language);

        if (dhProduct == null || dhProduct.getProductName() == null) {
            return new Response<DhProduct>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY_OR_PRODUCTNAMES, language), new ArrayList<>(), 0);
        }

        if(Utility.isFieldEmpty(dhProduct.getMainPlaceCategoryId()) || Utility.isFieldEmpty(dhProduct.getSubPlaceCategoryId())){
            return new Response<DhProduct>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_CATEGORY_IDS_MISSING, language), new ArrayList<>(), 0);
        }

        if (Utility.isFieldEmpty(dhProduct.getProductName().getProductnameInEnglish())) {
            return new Response<DhProduct>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_PRODUCT_NAMES_EMPTY, language), new ArrayList<>(), 0);
        }

        Query queryFindProduct = new Query(Criteria.where("productName.productnameInEnglish").regex(dhProduct.getProductName().getProductnameInEnglish(),"i"));
        DhProduct existingDhProduct = mongoTemplate.findOne(queryFindProduct,DhProduct.class);
        if(existingDhProduct!=null){
            return new Response<DhProduct>(false,402,Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_PRODUCT_ALREADY_EXISTS,language)+" ProductName : "+dhProduct.getProductName().getProductnameInEnglish(),new ArrayList<>(), 1);
        }

        Query queryFindCategoryWithId = new Query(Criteria.where("placeCategoryId").is(dhProduct.getMainPlaceCategoryId()));
        queryFindCategoryWithId.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE,"i"));
        DhPlaceCategories queriedDhPlaceCategories = mongoTemplate.findOne(queryFindCategoryWithId,DhPlaceCategories.class);
        if (queriedDhPlaceCategories==null || queriedDhPlaceCategories.getPlaceSubCategories()==null || queriedDhPlaceCategories.getPlaceSubCategories().size()==0) {
            return new Response<DhProduct>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NOT_FOUND_PLACECATEGORIY_WITH_ID, language) + "ID : " + dhProduct.getMainPlaceCategoryId(), new ArrayList<>(), 0);
        }

        for(PlaceSubCategories placeSubCategory : queriedDhPlaceCategories.getPlaceSubCategories()){
            if(placeSubCategory.getPlaceSubCategoryId().equalsIgnoreCase(dhProduct.getSubPlaceCategoryId())){
                dhProduct.setAddedBy(dhProduct.getAddedBy());
                dhProduct.setCategoryName(queriedDhPlaceCategories.getPlaceCategoryName().getPlacecategorynameInEnglish()+"->"+placeSubCategory.getPlaceSubCategoryName().getPlacesubcategorynameInEnglish());
                dhProduct.setSchemaVersion(AppConstants.SCHEMA_VERSION);
                dhProduct.setCreatedDateTime(Utility.currentDateTimeInUTC());
                dhProduct.setModifiedDateTime(Utility.currentDateTimeInUTC());
                dhProduct.setStatus(AppConstants.STATUS_PENDING);
                mongoTemplate.save(dhProduct,AppConstants.COLLECTION_DH_PRODUCT);
                utility.addLog(authentication.getName(),"Product ["+dhProduct.getProductName().getProductnameInEnglish()+"] has been added under ["+queriedDhPlaceCategories.getPlaceCategoryName().getPlacecategorynameInEnglish()+"->"+placeSubCategory.getPlaceSubCategoryName().getPlacesubcategorynameInEnglish()+"].");
                String resMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NEW_PRODUCT_ADDED,language)+" ProductName:"+dhProduct.getProductName().getProductnameInEnglish()+" -> "+placeSubCategory.getPlaceSubCategoryName().getPlacesubcategorynameInEnglish();
                return new Response<DhProduct>(true,201,resMsg, Collections.singletonList(dhProduct), 1);
            }
            String resMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NOT_FOUND_PLACECATEGORIY_WITH_ID,language)+" PlaceSubCategoryId:"+dhProduct.getSubPlaceCategoryId();
            return new Response<DhProduct>(false,402,resMsg,new ArrayList<>(), 0);
        }

        throw new ServerSideException(AppConstants.MSG_SOMETHING_WENT_WRONG);
    }

    @Override
    public Response<DhProduct> findProductsBySubCategoryId(Authentication authentication, HttpHeaders httpHeaders, String subPlaceCategoryId, String version) {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("ProductsServiceImpl->findProductsBySubCategoryId : language=" + language);

        if(Utility.isFieldEmpty(subPlaceCategoryId)){
            return new Response<DhProduct>(false,402,Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY,language),new ArrayList<>(), 0);
        }
        Query queryToFindProductsWithSubCategoryId = new Query(Criteria.where(AppConstants.SUB_PLACE_CATEGORY_ID).is(subPlaceCategoryId));
        queryToFindProductsWithSubCategoryId.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE,"i"));
        List<DhProduct> dhProductList = mongoTemplate.find(queryToFindProductsWithSubCategoryId,DhProduct.class);
        if(dhProductList==null || dhProductList.size()==0){
            return new Response<DhProduct>(false,402,Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NO_PRODUCTS_FOUND_FOR_SUBCATEGORYID,language),new ArrayList<>(),0);
        }
        return new Response<DhProduct>(true,200,AppConstants.QUERY_SUCCESSFUL,dhProductList,dhProductList.size());
    }

}
