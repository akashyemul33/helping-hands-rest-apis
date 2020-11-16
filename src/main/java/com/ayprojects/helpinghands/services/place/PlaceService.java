package com.ayprojects.helpinghands.services.place;

import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface PlaceService {
    Response<DhPlace> addPlace(Authentication authentication, HttpHeaders httpHeaders, MultipartFile[] placeImages, String placeBody, String version) throws ServerSideException;
    Response<DhPlace> deletePlace(Authentication authentication, HttpHeaders httpHeaders,String placeId, String version) throws ServerSideException;
    Response<DhPlace> updatePlace(Authentication authentication, HttpHeaders httpHeaders, DhPlace dhPlace, String version) throws ServerSideException;
    Response<DhPlace> getPlaces(Authentication authentication, HttpHeaders httpHeaders, String searchValue, String version);
    Response<DhPlace> getPaginatedPlaces(Authentication authentication, HttpHeaders httpHeaders,int page,int size, String version);

}
