package com.ayprojects.helpinghands.controllers;

import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhProduct;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.LoginResponse;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.products.ProductsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/products")
public class ProductController {

    @Autowired
    ProductsService productsService;
    @RequestMapping(value="/addProduct", method= RequestMethod.POST)
    public ResponseEntity<Response<DhProduct>> addProduct(@RequestHeader HttpHeaders httpHeaders,Authentication authentication, @RequestBody DhProduct dhProduct, @PathVariable String version) throws ServerSideException {
        return new ResponseEntity<>(productsService.addProduct(authentication,httpHeaders,dhProduct,version), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/getProductsForSubCategory",method = RequestMethod.GET)
    ResponseEntity<Response<DhProduct>> getProductsForSubCategory(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam String mainPlaceCategoryId,@RequestParam String subPlaceCategoryId, @PathVariable String version){
    return null;
    }

}
