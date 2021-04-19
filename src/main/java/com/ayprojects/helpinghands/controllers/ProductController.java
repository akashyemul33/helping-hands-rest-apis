package com.ayprojects.helpinghands.controllers;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhProduct;
import com.ayprojects.helpinghands.models.DhProductForAddingProducts;
import com.ayprojects.helpinghands.models.LangValueObj;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import io.swagger.annotations.Api;

@Api(value = "Products API's", description = "CRUD for products")
@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/products")
public class ProductController {

    @Autowired
    ApiOperations<DhProduct> apiOperations;

    @PostMapping(value = "/addProduct")
    public ResponseEntity<Response<DhProduct>> addProduct(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody DhProduct dhProduct, @PathVariable String version) throws ServerSideException {
        return new ResponseEntity<>(apiOperations.add(authentication, httpHeaders, dhProduct, StrategyName.AddProductStrategy, version), HttpStatus.CREATED);
    }

    @PostMapping(value = "/addProducts")
    public ResponseEntity<Response<DhProductForAddingProducts>> addProducts(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody List<DhProductForAddingProducts> dhProducts, @PathVariable String version) throws ServerSideException {
        for (DhProductForAddingProducts d : dhProducts) {
            DhProduct dhProduct = new DhProduct();
            dhProduct.setImgUrlLow(d.getImgUrlLow());
            dhProduct.setImgUrlHigh(d.getImgUrlHigh());
            dhProduct.setDefaultName(d.getDefaultName());
            List<LangValueObj> langValueObjList = new ArrayList<>();
            if (!Utility.isFieldEmpty(d.getNameInMarathi())) {
                langValueObjList.add(new LangValueObj("mr", d.getNameInMarathi()));
            }
            if (!Utility.isFieldEmpty(d.getNameInHindi())) {
                langValueObjList.add(new LangValueObj("hi", d.getNameInHindi()));
            }
            if (!Utility.isFieldEmpty(d.getNameInTelugu())) {
                langValueObjList.add(new LangValueObj("te", d.getNameInTelugu()));
            }
            if (!Utility.isFieldEmpty(d.getNameInKannada())) {
                langValueObjList.add(new LangValueObj("kn", d.getNameInKannada()));
            }
            if (!Utility.isFieldEmpty(d.getNameInGujarati())) {
                langValueObjList.add(new LangValueObj("gu", d.getNameInGujarati()));
            }
            dhProduct.setTranslations(langValueObjList);
            dhProduct.setAvgPrice(d.getAvgPrice());
            dhProduct.setMainPlaceCategoryId(d.getMainPlaceCategoryId());
            dhProduct.setSubPlaceCategoryIds(Arrays.asList(d.getSubPlaceCategoryIds().split(",")));
            dhProduct.setDefaultUnit(d.getDefaultUnit());
            dhProduct.setUnitQty(d.getUnitQty());
            dhProduct.setStatus(AppConstants.STATUS_ACTIVE);
            apiOperations.add(authentication, httpHeaders, dhProduct, StrategyName.AddProductStrategy, version);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/getProductsForSubCategory")
    ResponseEntity<Response<DhProduct>> getProductsForSubCategory(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam String subPlaceCategoryId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @PathVariable String version) throws ServerSideException {
        HashMap<String, Object> params = new HashMap<>();
        params.put(AppConstants.KEY_SUB_PLACECATEGORY_ID, subPlaceCategoryId);
        params.put(AppConstants.KEY_PAGE, page);
        params.put(AppConstants.KEY_SIZE, size);
        return new ResponseEntity<>(apiOperations.get(authentication, httpHeaders, StrategyName.GetProductStrategy, params, version), HttpStatus.OK);
    }

    @GetMapping(value = "/searchProducts")
    ResponseEntity<Response<DhProduct>> searchProducts(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam String searchKey, @PathVariable String version) throws ServerSideException {
        HashMap<String, Object> params = new HashMap<>();
        params.put(AppConstants.KEY_PRODUCT_SEARCH, searchKey);
        return new ResponseEntity<>(apiOperations.get(authentication, httpHeaders, StrategyName.GetProductStrategy, params, version), HttpStatus.OK);
    }

}
