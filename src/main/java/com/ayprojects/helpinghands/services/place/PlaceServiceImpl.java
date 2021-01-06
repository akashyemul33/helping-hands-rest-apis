package com.ayprojects.helpinghands.services.place;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.models.DhProduct;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.LangValueObj;
import com.ayprojects.helpinghands.models.PlaceSubCategories;
import com.ayprojects.helpinghands.models.ProductsWithPrices;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.repositories.PlaceRepository;
import com.ayprojects.helpinghands.services.common_service.CommonService;
import com.ayprojects.helpinghands.services.placecategories.PlaceCategoryService;
import com.ayprojects.helpinghands.services.products.ProductsService;
import com.ayprojects.helpinghands.util.tools.CalendarOperations;
import com.ayprojects.helpinghands.util.tools.Utility;
import com.ayprojects.helpinghands.util.tools.Validations;

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
    CalendarOperations calendarOperations;
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

    @Autowired
    CommonService commonService;

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

        if (!commonService.checkUserExistence(dhPlace.getAddedBy())) {
            return new Response<DhPlace>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_USER_NOT_FOUND_WITH_USERID, language), new ArrayList<>(), 0);
        }

        //Check for mainCategory existence
        Query queryFindCategoryWithId = new Query(Criteria.where(AppConstants.PLACE_MAIN_CATEGORY_ID).is(dhPlace.getPlaceMainCategoryId()));
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
                psc.setPlaceSubCategoryId(AppConstants.SUB_PLACE_INITIAL_ID + calendarOperations.getTimeAtFileEnd());
                psc = (PlaceSubCategories) utility.setCommonAttrs(psc, AppConstants.STATUS_PENDING);
                List<PlaceSubCategories> pscList = queriedDhPlaceCategories.getPlaceSubCategories();
                pscList.add(psc);
                Update mainCategoryUpdate = new Update();
                mainCategoryUpdate.push(AppConstants.PLACE_SUB_CATEGORIES, psc);
                mainCategoryUpdate.set(AppConstants.MODIFIED_DATE_TIME, calendarOperations.currentDateTimeInUTC());
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

        dhPlace.setNumberOfProducts(dhPlace.getProductDetails().size());
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
    public Response<DhPlace> getPaginatedPlaces(Authentication authentication, HttpHeaders httpHeaders, int page, int size, String version, double lat, double lng) {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("PlaceServiceImpl->getPaginatedPlaces : language=" + language);
        LOGGER.info("PlaceServiceImpl->getPaginatedPlaces : lat=%s" + lat + " lng=%s" + lng);
        PageRequest paging = PageRequest.of(page, size);
        Page<DhPlace> dhPlacePages = placeRepository.findAllByStatus(AppConstants.STATUS_ACTIVE, paging);
        List<DhPlace> dhPlaceList = dhPlacePages.getContent();
        for (DhPlace d : dhPlaceList) {
            //calculate distance of place from given lat lng
            if (lat != 0 && lng != 0) {
                if (d.getPlaceAddress() != null) {
                    double placeLat = d.getPlaceAddress().getLat();
                    double placeLng = d.getPlaceAddress().getLng();
                    d.setDistance("\t" + "(" + Utility.distance(lat, placeLat, lng, placeLng) + ")");
                } else {
                    d.setDistance(Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_UNKNOWN, language));
                }
            } else {
                d.setDistance(Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_UNKNOWN, language));
            }
            //calculate open/close msg
            String[] openCloseMsg = Utility.calculatePlaceOpenCloseMsg(d.getPlaceAvailablityDetails(), language);
            boolean isOpen = openCloseMsg.length > 1 && (openCloseMsg[1].equalsIgnoreCase(AppConstants.OPEN));
            LOGGER.info("PlaceServiceImpl->getPaginatedPlaces : isOpen=" + isOpen);
            d.setPlaceOpen(isOpen);
            d.setOpenCloseMsg(openCloseMsg[0]);

            DhUser dhUser = Utility.getUserDetailsFromId(d.getAddedBy(),mongoTemplate,true,false,false);
            if(dhUser!=null)d.setUserName(dhUser.getFirstName());

        }
        return new Response<DhPlace>(true, 200, "Query successful", dhPlaceList.size(), dhPlacePages.getNumber(), dhPlacePages.getTotalPages(), dhPlacePages.getTotalElements(), dhPlaceList);
    }

    @Override
    public Response<DhPlace> getBusinessPlacesOfUserWhileAddingPost(Authentication authentication, HttpHeaders httpHeaders, String version, String userId) {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("PlaceServiceImpl->getBusinessPlacesOfUser : language=" + language);

        if (Utility.isFieldEmpty(userId)) {
            return new Response<DhPlace>(false, 402, "Empty UserId", new ArrayList<>(), 0);
        }

        Query query = new Query(Criteria.where(AppConstants.ADDED_BY).is(userId));
        query.addCriteria(Criteria.where(AppConstants.PLACE_TYPE).regex(AppConstants.BUSINESS_PLACE, "i"));
        query.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        query.fields().include(AppConstants.PLACE_TYPE);
        query.fields().include(AppConstants.PLACE_ID);
        query.fields().include(AppConstants.PLACE_NAME);
        query.fields().include(AppConstants.PLACE_ADDRESS);
        query.fields().include(AppConstants.PLACE_CONTACT);
        List<DhPlace> dhPlaceList = mongoTemplate.find(query, DhPlace.class);
        return new Response<DhPlace>(true, 200, "Query successful", dhPlaceList);
    }

}

