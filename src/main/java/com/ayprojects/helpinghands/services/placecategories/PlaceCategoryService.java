package com.ayprojects.helpinghands.services.placecategories;

import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.models.PlaceSubCategories;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

@Service
public interface PlaceCategoryService {
    Response<DhPlaceCategories> addPlaceMainCategory(Authentication authentication, HttpHeaders httpHeaders, DhPlaceCategories dhPlaceCategories, String version);
    Response<DhPlaceCategories> findByPlaceCategoryId(Authentication authentication,HttpHeaders httpHeaders,String placeCategoryId);
    Response<DhPlaceCategories> findByStatus(Authentication authentication,HttpHeaders httpHeaders,String status);
    Response<List<DhPlaceCategories>> findAllByStatus(Authentication authentication, HttpHeaders httpHeaders, String status, String version);
    Response<PlaceSubCategories> addPlaceSubCategory(Authentication authentication, HttpHeaders httpHeaders, PlaceSubCategories placeSubCategories,String mainPlaceCategoryId,String version);
}
