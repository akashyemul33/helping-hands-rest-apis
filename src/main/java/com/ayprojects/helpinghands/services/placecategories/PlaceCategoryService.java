package com.ayprojects.helpinghands.services.placecategories;

import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhAppConfig;
import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PlaceCategoryService {
    Response<DhPlaceCategories> add(Authentication authentication,HttpHeaders httpHeaders,DhPlaceCategories dhPlaceCategories);
    Response<DhPlaceCategories> findByPlaceCategoryId(Authentication authentication,HttpHeaders httpHeaders,String placeCategoryId);
    Response<DhPlaceCategories> findByStatus(Authentication authentication,HttpHeaders httpHeaders,String status);
    Response<List<DhPlaceCategories>> findAllByStatus(Authentication authentication,HttpHeaders httpHeaders,String status);
}
