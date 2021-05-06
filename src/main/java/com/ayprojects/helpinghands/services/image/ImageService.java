package com.ayprojects.helpinghands.services.image;

import com.ayprojects.helpinghands.api.enums.SinglePlaceImageOperationsEnum;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPosts;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.ProductsWithPrices;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ImageService {
    Response<DhUser> uploadUserImage(HttpHeaders httpHeaders, MultipartFile imageLow, MultipartFile imageHigh, String version) throws ServerSideException;

    Response<DhPlace> uploadPlaceImages(HttpHeaders httpHeaders, Authentication authentication, String placeType, String addedBy, MultipartFile[] placeImagesLow, MultipartFile[] placeImagesHigh, String version) throws ServerSideException;

    Response<DhPlace> singlePlaceImageOperations(HttpHeaders httpHeaders, Authentication authentication, List<String> existingImgUrlsLowList, List<String> existingImgUrlsHighList,int editOrRemovePos,String placeId, String placeType, String addedBy, MultipartFile placeImagesLow, MultipartFile placeImagesHigh, SinglePlaceImageOperationsEnum operationsEnum, String version) throws ServerSideException;

    Response<DhPosts> uploadPostImages(HttpHeaders httpHeaders, Authentication authentication, String postType, String addedBy, MultipartFile[] postImagesLow, MultipartFile[] postImagesHigh, String version) throws ServerSideException;

    Response<ProductsWithPrices> uploadProductImages(HttpHeaders httpHeaders, Authentication authentication, String uniqueProductId, String placeType, String placeId, String addedBy,List<String> deleteProductHighList,List<String> deleteProductLowList, MultipartFile[] productImagesLow, MultipartFile[] productImagesHigh, String version) throws ServerSideException;
//    Response<DhRequirements> uploadRequirementImages(HttpHeaders httpHeaders, Authentication authentication, String reqType, String addedBy, MultipartFile[] reqImages, String version) throws ServerSideException;

}
