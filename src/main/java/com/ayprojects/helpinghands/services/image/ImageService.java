package com.ayprojects.helpinghands.services.image;

import com.ayprojects.helpinghands.api.enums.SinglePlaceImageOperationsEnum;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhHHPost;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPromotions;
import com.ayprojects.helpinghands.models.DhThought;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.ProductsWithPrices;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.models.Thoughts;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ImageService {
    Response<DhUser> uploadUserImage(HttpHeaders httpHeaders, MultipartFile imageLow, MultipartFile imageHigh, String version) throws ServerSideException;
    Response<Thoughts> uploadThoughtImage(HttpHeaders httpHeaders, MultipartFile imageLow, MultipartFile imageHigh,String addedBy,String thoughtStr,String userName,String userImg,boolean fromSystem, String version) throws ServerSideException;

    Response<DhPlace> uploadPlaceImages(HttpHeaders httpHeaders, Authentication authentication, String placeType, String addedBy, MultipartFile[] placeImagesLow, MultipartFile[] placeImagesHigh, String version) throws ServerSideException;

    Response<DhPlace> singlePlaceImageOperations(HttpHeaders httpHeaders, Authentication authentication, List<String> existingImgUrlsLowList, List<String> existingImgUrlsHighList, int editOrRemovePos, String placeId, String placeType, String addedBy, MultipartFile placeImagesLow, MultipartFile placeImagesHigh, SinglePlaceImageOperationsEnum operationsEnum, String version) throws ServerSideException;

    public Response<DhPromotions> uploadPromotionImagesVideosThumbnails(HttpHeaders httpHeaders, Authentication authentication, String placeName, String placeCategory, String promotionType, String placeId, String addedBy, String promotionTitle, String promotionDesc, String fullAddress, String fullName, String offerStartTime, String offerEndTime, boolean areDetailsSameAsRegistered, String mobile, String email, MultipartFile[] promotionImagesLow, MultipartFile[] promotionImagesHigh, MultipartFile[] promotionThumbnails, MultipartFile[] promotionVideoLowUrls, MultipartFile[] promotionVideoHighUrls, String version) throws ServerSideException;

    Response<ProductsWithPrices> uploadProductImages(HttpHeaders httpHeaders, Authentication authentication, String uniqueProductId, String placeType, String placeId, String addedBy, List<String> existingProductLowList, List<String> existingProductHighList, List<String> deleteProductLowList, MultipartFile[] productImagesLow, MultipartFile[] productImagesHigh, String version) throws ServerSideException;
//    Response<DhRequirements> uploadRequirementImages(HttpHeaders httpHeaders, Authentication authentication, String reqType, String addedBy, MultipartFile[] reqImages, String version) throws ServerSideException;

    Response<DhHHPost> uploadHhPostImages(HttpHeaders httpHeaders, Authentication authentication, String addedBy, MultipartFile[] postImagesLow, MultipartFile[] postImagesHigh, String version) throws ServerSideException;

}
