package com.ayprojects.helpinghands.services.products;

import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhProduct;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductsService {
    Response<DhProduct> addProducts(Authentication authentication, HttpHeaders httpHeaders, List<DhProduct> dhProducts, String version) throws ServerSideException;
    Response<DhProduct> addProduct(Authentication authentication, HttpHeaders httpHeaders, DhProduct dhProduct, String version) throws ServerSideException;
    Response<DhProduct> findProductsBySubCategoryId(Authentication authentication, HttpHeaders httpHeaders,String subPlaceCategoryId, String version);
}
