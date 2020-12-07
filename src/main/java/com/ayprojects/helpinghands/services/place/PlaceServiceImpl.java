package com.ayprojects.helpinghands.services.place;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.models.DhProduct;
import com.ayprojects.helpinghands.models.LangValueObj;
import com.ayprojects.helpinghands.models.PlaceSubCategories;
import com.ayprojects.helpinghands.models.ProductsWithPrices;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.repositories.PlaceRepository;
import com.ayprojects.helpinghands.services.placecategories.PlaceCategoryService;
import com.ayprojects.helpinghands.services.products.ProductsService;
import com.ayprojects.helpinghands.tools.Utility;
import com.ayprojects.helpinghands.tools.Validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    MongoTemplate mongoTemplate;
    @Value("${images.base_folder}")
    String imagesBaseFolder;
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
        String placeStatus = AppConstants.STATUS_ACTIVE;
        if (dhPlace == null) {
            return new Response<DhPlace>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language), new ArrayList<>(), 0);
        }
        LOGGER.info("PlaceServiceImpl->addPlace : isAddressGenerated=" + dhPlace.isAddressGenerated());
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

        //Check for mainCategory existence
        Query queryFindCategoryWithId = new Query(Criteria.where(AppConstants.PLACE_CATEGORY_ID).is(dhPlace.getPlaceMainCategoryId()));
        queryFindCategoryWithId.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        DhPlaceCategories queriedDhPlaceCategories = mongoTemplate.findOne(queryFindCategoryWithId, DhPlaceCategories.class);
        if (queriedDhPlaceCategories == null) {
            return new Response<DhPlace>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NOT_FOUND_PLACECATEGORIY_WITH_ID, language) + "ID : " + dhPlace.getPlaceMainCategoryId(), new ArrayList<>(), 0);
        }

        boolean canProceed = false;
        //check for sub category existence
        //check whether category existence if user didn't send subcategoryId
        if (Utility.isFieldEmpty(dhPlace.getPlaceSubCategoryId())) {
            //TODO add category to db, but first check with the sub category name whether it exists
            for (PlaceSubCategories ps : queriedDhPlaceCategories.getPlaceSubCategories()) {
                if (dhPlace.getPlaceSubCategoryName().equalsIgnoreCase(ps.getDefaultName())) {
                    LOGGER.info("sub category found with the default name search, it's id is " + ps.getPlaceSubCategoryId() + " name=" + ps.getDefaultName());
                    dhPlace.setPlaceSubCategoryId(ps.getPlaceSubCategoryId());
                    canProceed = true;
                    break;
                } else {
                    if (ps.getTranslations() != null && ps.getTranslations().size() > 0) {
                        for (LangValueObj langValueObj : ps.getTranslations()) {
                            if (dhPlace.getPlaceSubCategoryName().equalsIgnoreCase(langValueObj.getValue())) {
                                LOGGER.info("sub category found with the translations obj search, it's id is " + ps.getPlaceSubCategoryId() + " name=" + ps.getDefaultName() + " and lang=" + langValueObj.getLang());
                                dhPlace.setPlaceSubCategoryId(ps.getPlaceSubCategoryId());
                                canProceed = true;
                                break;
                            }
                        }
                    }
                }
            }
            if (!canProceed) {
                //create if category not found
                //create new sub category with status pending
                PlaceSubCategories psc = new PlaceSubCategories();
                psc.setDefaultName(dhPlace.getPlaceSubCategoryName());
                psc.setAddedBy(dhPlace.getAddedBy());
                psc.setPlaceSubCategoryId(AppConstants.SUB_PLACE_INITIAL_ID + Utility.currentDateTimeInUTC(AppConstants.DATE_TIME_FORMAT_WITHOUT_UNDERSCORE));
                psc = (PlaceSubCategories) utility.setCommonAttrs(psc, AppConstants.STATUS_PENDING);
                List<PlaceSubCategories> pscList = queriedDhPlaceCategories.getPlaceSubCategories();
                pscList.add(psc);
                Update mainCategoryUpdate = new Update();
                mainCategoryUpdate.push(AppConstants.PLACE_SUB_CATEGORIES, psc);
                mainCategoryUpdate.set(AppConstants.MODIFIED_DATE_TIME, Utility.currentDateTimeInUTC());
                mongoTemplate.updateFirst(queryFindCategoryWithId, mainCategoryUpdate, DhPlaceCategories.class);
                utility.addLog(authentication.getName(), "New sub category while adding place [" + psc.getDefaultName() + "] has been added under [" + queriedDhPlaceCategories.getDefaultName() + "].");
                dhPlace.setPlaceSubCategoryId(psc.getPlaceSubCategoryId());
                canProceed = true;
                placeStatus = AppConstants.STATUS_PENDING;
            }

        } else {
            for (PlaceSubCategories placeSubCategory : queriedDhPlaceCategories.getPlaceSubCategories()) {
                if (dhPlace.getPlaceSubCategoryId().equalsIgnoreCase(placeSubCategory.getPlaceSubCategoryId())) {
                    canProceed = true;
                    placeStatus = AppConstants.STATUS_ACTIVE;
                    break;
                }
            }
        }

        if (canProceed) {
            for (ProductsWithPrices p : dhPlace.getProductDetails()) {
                if (Utility.isFieldEmpty(p.getProductId())) {
                    //TODO check whether product already exists
                    Query queryToFindProduct = new Query(new Criteria().orOperator(Criteria.where(AppConstants.DEFAULT_NAME).regex(p.getUserEnteredProductName(), "i"),
                            Criteria.where(AppConstants.TRANSLATIONS + ".value").regex(p.getUserEnteredProductName(), "i")
                    ));
                    DhProduct queriedDhProduct = mongoTemplate.findOne(queryToFindProduct, DhProduct.class);
                    if (queriedDhProduct == null) {
                        queriedDhProduct = new DhProduct();
                        //product does not exist so add it to db
                        LOGGER.info("product does not exist, so create product to DB");
                        queriedDhProduct.setProductId(Utility.getUUID());
                        queriedDhProduct.setDefaultUnit(p.getSelectedUnit());
                        queriedDhProduct.setMainPlaceCategoryId(dhPlace.getPlaceMainCategoryId());
                        queriedDhProduct.setSubPlaceCategoryId(dhPlace.getPlaceSubCategoryId());
                        queriedDhProduct.setCategoryName(dhPlace.getPlaceCategoryName() + "->" + dhPlace.getPlaceSubCategoryName());
                        queriedDhProduct.setAddedBy(dhPlace.getAddedBy());
                        queriedDhProduct.setDefaultName(p.getUserEnteredProductName());
                        queriedDhProduct.setAvgPrice(p.getProductPrice());
                        queriedDhProduct = (DhProduct) utility.setCommonAttrs(queriedDhProduct, AppConstants.STATUS_ACTIVE);
                        mongoTemplate.save(queriedDhProduct, AppConstants.COLLECTION_DH_PRODUCT);
                        utility.addLog(authentication.getName(), "Product [" + p.getUserEnteredProductName() + "] has been added under [" + queriedDhPlaceCategories.getDefaultName() + "->" + dhPlace.getSubscribedUsers() + "].");
                        p.setProductId(queriedDhProduct.getProductId());
                    } else {
                        LOGGER.info("Found product with name " + p.getUserEnteredProductName() + " productId=" + p.getProductId());
                        p.setProductId(queriedDhProduct.getProductId());
                    }
                }
            }
        } else {
            String resMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_UNABLE_TO_ADD_PLACE_ISSUES_WITH_SUBCATEGORY, language) + " PlaceSubCategoryName:" + dhPlace.getPlaceSubCategoryName();
            return new Response<DhPlace>(false, 402, resMsg, new ArrayList<>(), 0);
        }

        dhPlace = (DhPlace) utility.setCommonAttrs(dhPlace, placeStatus);
        mongoTemplate.save(dhPlace, AppConstants.COLLECTION_DH_PLACE);
        utility.addLog(authentication.getName(), "New [" + dhPlace.getPlaceType() + "] place with category [" + dhPlace.getPlaceCategoryName() + "->" + dhPlace.getPlaceSubCategoryName() + "] has been added in status " + placeStatus);
        int statusCode = 201;//for active
        String headingMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_CONGRATULATIONS, language);
        String responseMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NEW_PLACE_ADDED_WITH_ACTIVE, language);
        if (AppConstants.STATUS_ACTIVE.equalsIgnoreCase(placeStatus)) {
            statusCode = 201;
            responseMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NEW_PLACE_ADDED_WITH_ACTIVE, language);
        } else if (AppConstants.STATUS_PENDING.equalsIgnoreCase(placeStatus)) {
            statusCode = 202;//for pending
            responseMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NEW_PLACE_ADDED_WITH_PENDING, language);
        }
        return new Response<>(true, statusCode, headingMsg, responseMsg, new ArrayList<>());
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
        return new Response<DhPlace>(true, 200, "Query successful", dhPlaceList.size(), dhPlacePages.getNumber(), dhPlacePages.getTotalPages(), dhPlacePages.getTotalElements(), dhPlaceList);
    }
}
