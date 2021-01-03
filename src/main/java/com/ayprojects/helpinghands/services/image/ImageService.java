package com.ayprojects.helpinghands.services.image;

import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPosts;
import com.ayprojects.helpinghands.models.DhRequirements;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ImageService {
    Response<DhUser> uploadUserImage(HttpHeaders httpHeaders, MultipartFile image, String version) throws ServerSideException;
    Response<DhPlace> uploadPlaceImages(HttpHeaders httpHeaders, Authentication authentication, String placeType, String addedBy,MultipartFile[] placeImages, String version) throws ServerSideException;
    Response<DhPosts> uploadPostImages(HttpHeaders httpHeaders, Authentication authentication, String postType, String addedBy,MultipartFile[] postImages, String version) throws ServerSideException;
//    Response<DhRequirements> uploadRequirementImages(HttpHeaders httpHeaders, Authentication authentication, String reqType, String addedBy, MultipartFile[] reqImages, String version) throws ServerSideException;

}
