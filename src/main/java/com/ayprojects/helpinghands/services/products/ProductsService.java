package com.ayprojects.helpinghands.services.products;

import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.models.DhProduct;
import com.ayprojects.helpinghands.models.PlaceSubCategories;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.swing.text.html.Option;

@Service
public interface ProductsService {
    Response<DhProduct> addProduct(Authentication authentication, HttpHeaders httpHeaders,DhProduct dhProduct, String version) throws ServerSideException;
    Response<List<DhProduct>> findProductsByCategory(Authentication authentication, HttpHeaders httpHeaders,String mainPlaceCategoryId,String subPlaceCategoryId, String version);
}
