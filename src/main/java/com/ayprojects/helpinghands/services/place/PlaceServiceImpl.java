package com.ayprojects.helpinghands.services.place;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.models.DhProduct;
import com.ayprojects.helpinghands.models.PlaceSubCategories;
import com.ayprojects.helpinghands.models.PlaceSubCategoryName;
import com.ayprojects.helpinghands.models.ProductsWithPrices;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.repositories.PlaceRepository;
import com.ayprojects.helpinghands.services.placecategories.PlaceCategoryService;
import com.ayprojects.helpinghands.services.placecategories.PlaceCategoryServiceImpl;
import com.ayprojects.helpinghands.services.products.ProductsService;
import com.ayprojects.helpinghands.tools.Utility;
import com.ayprojects.helpinghands.tools.Validations;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.invoke.ConstantCallSite;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.rmi.CORBA.Util;

import ch.qos.logback.core.rolling.helper.TimeBasedArchiveRemover;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Value("${images.base_folder}")
    String imagesBaseFolder;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    Utility utility;

    @Autowired
    PlaceCategoryService placeCategoryService;

    @Autowired
    ProductsService productsService;

    @Override
    public Response<DhPlace> addPlace(Authentication authentication, HttpHeaders httpHeaders, DhPlace dhPlace, String version) throws ServerSideException {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("PlaceServiceImpl->addPlace : language=" + language);

        if (dhPlace == null) {
            return new Response<DhPlace>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language), new ArrayList<>(), 0);
        }

        //check if placeId && placeimages present
        if (Utility.isFieldEmpty(dhPlace.getPlaceId()) || dhPlace.getPlaceImages() == null || dhPlace.getPlaceImages().size() <= 0) {
            dhPlace.setPlaceId(Utility.getUUID());
        }

        //validate all other stuffs
        List<String> missingFieldsList = Validations.findMissingFieldsForPlaces(dhPlace);
        if (missingFieldsList.size() > 0) {
            String resMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language);
            resMsg = resMsg + " , these fields are missing : " + missingFieldsList;
            return new Response<DhPlace>(false, 402, resMsg, new ArrayList<>(), 0);
        }

        //handle categories
        if (Utility.isFieldEmpty(dhPlace.getPlaceSubCategoryId())) {
            PlaceSubCategories placeSubCategories = new PlaceSubCategories();
            placeSubCategories.setStatus(AppConstants.STATUS_PENDING);
            placeSubCategories.setPlaceSubCategoryId(Utility.getUUID());
            placeSubCategories.setAddedBy(dhPlace.getAddedBy());
            placeSubCategories.setPlaceSubCategoryName(new PlaceSubCategoryName(dhPlace.getPlaceSubCategoryName()));
            Response<PlaceSubCategories> addPlaceCategoryResp = placeCategoryService.addPlaceSubCategory(authentication, httpHeaders, placeSubCategories, dhPlace.getPlaceMainCategoryId(), version);
            if (addPlaceCategoryResp.getStatusCode() == 201) {
                LOGGER.info("PlaceServiceImpl->Added sub category");
                dhPlace.setPlaceSubCategoryId(placeSubCategories.getPlaceSubCategoryId());
            }
        }

        //handle products
        for (ProductsWithPrices p : dhPlace.getProductDetails()) {
            //check whether product is not new one
            if (Utility.isFieldEmpty(p.getProductId())) {
                DhProduct dhProduct = new DhProduct();
                dhProduct.setStatus(AppConstants.STATUS_PENDING);
                dhProduct.setCategoryName(dhPlace.getPlaceCategoryName() + "->" + dhPlace.getPlaceSubCategoryName());
                dhProduct.setDefaultUnit(p.getSelectedUnit());
                dhProduct.setAvgPrice(Double.parseDouble(p.getProductPrice()));
                dhProduct.setMainPlaceCategoryId(dhPlace.getPlaceMainCategoryId());
                dhProduct.setProductId(Utility.getUUID());
                dhProduct.setUserEnteredProductName(p.getUserEnteredProductName());
                dhProduct.setSubPlaceCategoryId(dhPlace.getPlaceSubCategoryId());
                Response<DhProduct> addProductResp = productsService.addProduct(authentication, httpHeaders, new DhProduct(), version);
                if (addProductResp.getStatusCode() == 201) {
                    LOGGER.info("PlaceServiceImpl->Added product");
                    p.setProductId(dhProduct.getProductId());
                }
            }
        }

        dhPlace = (DhPlace) utility.setCommonAttrs(dhPlace, AppConstants.STATUS_ACTIVE);
        mongoTemplate.save(dhPlace, AppConstants.COLLECTION_DH_PLACE);
        utility.addLog(authentication.getName(), "New [" + dhPlace.getPlaceType() + "] place with category [" + dhPlace.getPlaceCategoryName() + "] has been added.");
        return new Response<>(true, 201, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NEW_PLACE_ADDED, language), new ArrayList<>(), 1);
    }

    @Override
    public Response<DhPlace> deletePlace(Authentication authentication, HttpHeaders httpHeaders, String placeId, String version) throws ServerSideException {
        return null;
    }

    @Override
    public Response<DhPlace> updatePlace(Authentication authentication, HttpHeaders httpHeaders, DhPlace dhPlace, String version) throws ServerSideException {
        return null;
    }

    @Override
    public Response<DhPlace> getPlaces(Authentication authentication, HttpHeaders httpHeaders, String searchValue, String version) {
        return null;
    }

    @Override
    public Response<DhPlace> getPaginatedPlaces(Authentication authentication, HttpHeaders httpHeaders, int page, int size, String version) {
        PageRequest paging = PageRequest.of(page, size);
        Page<DhPlace> dhPlacePages = placeRepository.findAllByStatus(AppConstants.STATUS_ACTIVE, paging);
        List<DhPlace> dhPlaceList = dhPlacePages.getContent();
        return new Response<DhPlace>(true, 201, "Query successful", dhPlaceList.size(), dhPlacePages.getNumber(), dhPlacePages.getTotalPages(), dhPlacePages.getTotalElements(), dhPlaceList);
    }
}
