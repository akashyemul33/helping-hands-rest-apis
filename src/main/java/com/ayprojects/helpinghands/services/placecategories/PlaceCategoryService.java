package com.ayprojects.helpinghands.services.placecategories;

import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.models.PlaceSubCategories;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface PlaceCategoryService {
    Response<DhPlaceCategories> addPlaceMainCategory(Authentication authentication, HttpHeaders httpHeaders, DhPlaceCategories dhPlaceCategories, String version);
    Response<DhPlaceCategories> findByPlaceCategoryId(Authentication authentication,HttpHeaders httpHeaders,String placeCategoryId);
    Response<DhPlaceCategories> findByStatus(Authentication authentication,HttpHeaders httpHeaders,String status);
    Response<DhPlaceCategories> findAllByStatus(Authentication authentication, HttpHeaders httpHeaders,String version);
    Response<PlaceSubCategories> addPlaceSubCategory(Authentication authentication, HttpHeaders httpHeaders, PlaceSubCategories placeSubCategories,String mainPlaceCategoryId,String version);
    Response<DhPlaceCategories> getAllPlaceCategoriesByType(Authentication authentication, HttpHeaders httpHeaders, String version, String typeOfPlaceCategory);
}
