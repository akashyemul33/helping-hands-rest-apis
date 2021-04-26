package com.ayprojects.helpinghands.controllers;

import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPosts;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.ProductsWithPrices;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.image.ImageService;

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

import io.swagger.annotations.Api;

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

    @PostMapping(value = "/uploadPlaceImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<DhPlace>> uploadPlaceImages(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam(value = "placeType", required = true) String placeType, @RequestParam(value = "addedBy", required = true) String addedBy, @RequestPart(value = "placeImagesLow", required = true) MultipartFile[] placeImagesLow, @RequestPart(value = "placeImagesHigh", required = true) MultipartFile[] placeImagesHigh, @PathVariable String version) throws ServerSideException {
        Response<DhPlace> response = imageService.uploadPlaceImages(httpHeaders, authentication, placeType, addedBy, placeImagesLow, placeImagesHigh, version);
        if (response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping(value = "/uploadPostImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<DhPosts>> uploadPostImages(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam(value = "postType", required = true) String postType, @RequestParam(value = "addedBy", required = true) String addedBy, @RequestPart(value = "postImagesLow", required = true) MultipartFile[] postImagesLow, @RequestPart(value = "postImagesHigh", required = true) MultipartFile[] postImagesHigh, @PathVariable String version) throws ServerSideException {
        Response<DhPosts> response = imageService.uploadPostImages(httpHeaders, authentication, postType, addedBy, postImagesLow, postImagesHigh, version);
        if (response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping(value = "/uploadProductImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<ProductsWithPrices>> uploadProductImages(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam(value = "uniqueProductId", required = true) String uniqueProductId,
                                                                            @RequestParam(value = "placeType", required = true) String placeType,
                                                                            @RequestParam(value = "placeId", required = true) String placeId,
                                                                            @RequestParam(value = "addedBy", required = true) String addedBy, @RequestPart(value = "productImagesLow", required = true) MultipartFile[] productImagesLow, @RequestPart(value = "productImagesHigh", required = true) MultipartFile[] productImagesHigh, @PathVariable String version) throws ServerSideException {
        Response<ProductsWithPrices> response = imageService.uploadProductImages(httpHeaders, authentication, uniqueProductId, placeType, placeId, addedBy, productImagesLow, productImagesHigh, version);
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

}
