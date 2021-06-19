package com.ayprojects.helpinghands.controllers;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.enums.SinglePlaceImageOperationsEnum;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhHHPost;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPromotions;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.ProductsWithPrices;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.models.Thoughts;
import com.ayprojects.helpinghands.services.image.ImageService;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.swagger.annotations.Api;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Api(value = "Views API's", description = "CRUD for image uploads")
@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/imageUpload")
public class ImageController {

    @Autowired
    ImageService imageService;

    @PostMapping(value = "/uploadUserImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<DhUser>> uploadUserImage(@RequestHeader HttpHeaders httpHeaders, @RequestPart(value = "userImageLow", required = true) MultipartFile userImageLow, @RequestPart(value = "userImageHigh", required = true) MultipartFile userImageHigh, @PathVariable String version) throws ServerSideException {
        return new ResponseEntity<>(imageService.uploadUserImage(httpHeaders, userImageLow, userImageHigh, version), HttpStatus.CREATED);
    }

    @PostMapping(value = "/uploadThoughtImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<Thoughts>> uploadThoughtImage(@RequestHeader HttpHeaders httpHeaders, @RequestPart(value = "thoughtImageLow", required = true) MultipartFile thoughtImageLow, @RequestPart(value = "thoughtImageHigh", required = true) MultipartFile thoughtImageHigh, @RequestParam(value = "addedBy", required = true) String addedBy, @RequestParam(value = "thoughtsStr", required = true) String thoughtsStr, @RequestParam(value = "userName", required = true) String userName, @RequestParam(value = "userImg", required = true) String userImg, @RequestParam(value = "fromSystem", required = true) boolean fromSystem, @PathVariable String version) throws ServerSideException {
        Response<Thoughts> response = imageService.uploadThoughtImage(httpHeaders, thoughtImageLow, thoughtImageHigh, addedBy, thoughtsStr, userName, userImg, fromSystem, version);
        if (response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping(value = "/uploadPlaceImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<DhPlace>> uploadPlaceImages(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam(value = "placeType", required = true) String placeType, @RequestParam(value = "addedBy", required = true) String addedBy, @RequestPart(value = "placeImagesLow", required = true) MultipartFile[] placeImagesLow, @RequestPart(value = "placeImagesHigh", required = true) MultipartFile[] placeImagesHigh, @PathVariable String version) throws ServerSideException {
        Response<DhPlace> response = imageService.uploadPlaceImages(httpHeaders, authentication, placeType, addedBy, placeImagesLow, placeImagesHigh, version);
        if (response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping(value = "/singlePlaceImagesDeleteOperations", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<DhPlace>> singlePlaceImageDeleteApi(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam(value = "existingImgUrlsLowList", required = true) String existingImgUrlsLowList, @RequestParam(value = "existingImgUrlsHighList", required = true) String existingImgUrlsHighList, @RequestParam(value = "editOrRemovePos", required = true) String editOrRemovePos, @RequestParam(value = "placeId", required = true) String placeId, @RequestParam(value = "placeType", required = true) String placeType, @RequestParam(value = "addedBy", required = true) String addedBy, @RequestParam(value = "operationsEnum", required = true) SinglePlaceImageOperationsEnum operationsEnum, @PathVariable String version) throws ServerSideException {
        List<String> lowUrlList = new ArrayList<>();
        List<String> highUrlList = new ArrayList<>();
        if (!Utility.isFieldEmpty(existingImgUrlsHighList) && !Utility.isFieldEmpty(existingImgUrlsLowList)) {
            lowUrlList = new ArrayList<>(Arrays.asList(existingImgUrlsLowList.split(",")));
            highUrlList = new ArrayList<>(Arrays.asList(existingImgUrlsHighList.split(",")));
        }
        Response<DhPlace> response = imageService.singlePlaceImageOperations(httpHeaders, authentication, lowUrlList, highUrlList, Integer.parseInt(editOrRemovePos), placeId, placeType, addedBy, null, null, operationsEnum, version);
        if (response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping(value = "/singlePlaceImagesOperations", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<DhPlace>> singlePlaceImageApi(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam(value = "existingImgUrlsLowList", required = true) String existingImgUrlsLowList, @RequestParam(value = "existingImgUrlsHighList", required = true) String existingImgUrlsHighList, @RequestParam(value = "editOrRemovePos", required = true) String editOrRemovePos, @RequestParam(value = "placeId", required = true) String placeId, @RequestParam(value = "placeType", required = true) String placeType, @RequestParam(value = "addedBy", required = true) String addedBy, @RequestPart(value = "placeImagesLow", required = true) MultipartFile placeImageLow, @RequestPart(value = "placeImagesHigh", required = true) MultipartFile placeImageHigh, @RequestParam(value = "operationsEnum", required = true) SinglePlaceImageOperationsEnum operationsEnum, @PathVariable String version) throws ServerSideException {

        List<String> lowUrlList = new ArrayList<>();
        List<String> highUrlList = new ArrayList<>();
        if (!Utility.isFieldEmpty(existingImgUrlsHighList) && !Utility.isFieldEmpty(existingImgUrlsLowList)) {
            lowUrlList = new ArrayList<>(Arrays.asList(existingImgUrlsLowList.split(",")));
            highUrlList = new ArrayList<>(Arrays.asList(existingImgUrlsHighList.split(",")));
        }
        Response<DhPlace> response = imageService.singlePlaceImageOperations(httpHeaders, authentication, lowUrlList, highUrlList, Integer.parseInt(editOrRemovePos), placeId, placeType, addedBy, placeImageLow, placeImageHigh, operationsEnum, version);
        if (response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping(value = "/uploadPromotionFiles", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<DhPromotions>> uploadPromotionImagesVideosThumbnails(@RequestHeader HttpHeaders httpHeaders, Authentication authentication,
                                                                                        @RequestPart(value = "promotionImagesLow", required = true) MultipartFile[] promotionImagesLow,
                                                                                        @RequestPart(value = "promotionImagesHigh", required = true) MultipartFile[] promotionImagesHigh,
                                                                                        @RequestPart(value = "promotionThumbnails", required = true) MultipartFile[] promotionThumbnails,
                                                                                        @RequestPart(value = "promotionVideoLowUrls", required = true) MultipartFile[] promotionVideoLowUrls,
                                                                                        @RequestPart(value = "promotionVideoHighUrls", required = true) MultipartFile[] promotionVideoHighUrls,
                                                                                        @RequestParam(value = "promotionType", required = true) String promotionType,
                                                                                        @RequestParam(value = "placeId", required = false) String placeId,
                                                                                        @RequestParam(value = "placeName", required = false) String placeName,
                                                                                        @RequestParam(value = "placeCategory", required = false) String placeCategory,
                                                                                        @RequestParam(value = "addedBy", required = true) String addedBy,
                                                                                        @RequestParam(value = "promotionTitle", required = true) String promotionTitle,
                                                                                        @RequestParam(value = "promotionDesc", required = true) String promotionDesc,
                                                                                        @RequestParam(value = "fullAddress", required = true) String fullAddress,
                                                                                        @RequestParam(value = "fullName", required = true) String fullName,
                                                                                        @RequestParam(value = "offerStartTime", required = true) String offerStartTime,
                                                                                        @RequestParam(value = "offerEndTime", required = true) String offerEndTime,
                                                                                        @RequestParam(value = "areDetailsSameAsRegistered", required = true) boolean areDetailsSameAsRegistered,
                                                                                        @RequestParam(value = "mobile", required = true) String mobile,
                                                                                        @RequestParam(value = "email", required = true) String email) throws ServerSideException {
        Response<DhPromotions> response = imageService.uploadPromotionImagesVideosThumbnails(httpHeaders, authentication, placeName, placeCategory, promotionType, placeId, addedBy, promotionTitle, promotionDesc, fullAddress, fullName, offerStartTime, offerEndTime, areDetailsSameAsRegistered, mobile, email, promotionImagesLow, promotionImagesHigh, promotionThumbnails, promotionVideoLowUrls, promotionVideoHighUrls, AppConstants.SCHEMA_VERSION);
        if (response.getStatus()) {
            LOGGER.info("videoHigh.length:" + promotionVideoHighUrls.length);
            LOGGER.info("videoLow.length:" + promotionVideoLowUrls.length);
            LOGGER.info("imagesLow.length:" + promotionImagesLow.length);
            LOGGER.info("imagesHigh.length:" + promotionImagesHigh.length);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping(value = "/uploadProductImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<ProductsWithPrices>> uploadProductImages(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam(value = "uniqueProductId", required = true) String uniqueProductId,
                                                                            @RequestParam(value = "placeType", required = true) String placeType,
                                                                            @RequestParam(value = "placeId", required = true) String placeId,
                                                                            @RequestParam(value = "addedBy", required = true) String addedBy,
                                                                            @RequestParam(value = "existingProductLowList", required = true) String existingProductLowList,
                                                                            @RequestParam(value = "existingProductHighList", required = true) String existingProductHighList,
                                                                            @RequestParam(value = "deletePos", required = true) String deletePos,
                                                                            @RequestPart(value = "productImagesLow", required = true) MultipartFile[] productImagesLow, @RequestPart(value = "productImagesHigh", required = true) MultipartFile[] productImagesHigh, @PathVariable String version) throws ServerSideException {
        List<String> lowUrlList = new ArrayList<>();
        List<String> highUrlList = new ArrayList<>();
        List<String> deletePosList = new ArrayList<>();
        if (!Utility.isFieldEmpty(existingProductHighList) && !Utility.isFieldEmpty(existingProductLowList)) {
            lowUrlList = new ArrayList<>(Arrays.asList(existingProductLowList.split(",")));
            highUrlList = new ArrayList<>(Arrays.asList(existingProductHighList.split(",")));
        }
        if (!Utility.isFieldEmpty(deletePos))
            deletePosList = new ArrayList<>(Arrays.asList(deletePos.split(",")));
        Response<ProductsWithPrices> response = imageService.uploadProductImages(httpHeaders, authentication, uniqueProductId, placeType, placeId, addedBy, lowUrlList, highUrlList, deletePosList, productImagesLow, productImagesHigh, version);
        if (response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }
    /*@PostMapping(value="/uploadRequirementImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<DhRequirements>> uploadReqImages(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam(value = "reqType",required = true) String reqType, @RequestParam(value = "addedBy",required = true) String addedBy, @RequestPart(value="reqImages",required = true) MultipartFile[] reqImages, @PathVariable String version) throws ServerSideException {
        return new ResponseEntity<>(imageService.uploadRequirementImages(httpHeaders,authentication,reqType,addedBy,reqImages,version), HttpStatus.CREATED);
    }*/

    @PostMapping(value = "/uploadHhPostImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<DhHHPost>> uploadHhPostImages(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam(value = "addedBy", required = true) String addedBy, @RequestPart(value = "postImagesLow", required = true) MultipartFile[] postImagesLow, @RequestPart(value = "postImagesHigh", required = true) MultipartFile[] postImagesHigh, @PathVariable String version) throws ServerSideException {
        Response<DhHHPost> response = imageService.uploadHhPostImages(httpHeaders, authentication, addedBy, postImagesLow, postImagesHigh, version);
        if (response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }
}
