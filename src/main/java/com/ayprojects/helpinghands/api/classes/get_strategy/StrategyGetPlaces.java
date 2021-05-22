package com.ayprojects.helpinghands.api.classes.get_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyGetBehaviour;
import com.ayprojects.helpinghands.api.enums.ContentType;
import com.ayprojects.helpinghands.api.enums.ProductPricesVisibilityEnum;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.api.enums.TypeOfData;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.DhViews;
import com.ayprojects.helpinghands.models.ProductPricesVisibleUsers;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.repositories.PlaceRepository;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.CalendarOperations;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Component
public class StrategyGetPlaces implements StrategyGetBehaviour<DhPlace> {

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<DhPlace> get(String language, HashMap<String, Object> params) throws ServerSideException {
        if (params == null) {
            return new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS), new ArrayList<>());
        }

        Set<String> keySet = params.keySet();
        if (keySet.contains(AppConstants.KEY_PAGE)
                && keySet.contains(AppConstants.KEY_SIZE)
                && keySet.contains(AppConstants.KEY_LAT)
                && keySet.contains(AppConstants.KEY_LNG)
        ) {
            try {
                int page = (int) params.get(AppConstants.KEY_PAGE);
                int size = (int) params.get(AppConstants.KEY_SIZE);
                double lat = (double) params.get(AppConstants.KEY_LAT);
                double lng = (double) params.get(AppConstants.KEY_LNG);
                return getPaginatedPlaces(language, page, size, lat, lng);
            } catch (Exception e) {
                throw new ServerSideException(ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_SOMETHING_WENT_WRONG));
            }

        } else if (keySet.contains(AppConstants.KEY_USER_ID) && keySet.contains(AppConstants.KEY_TYPE_OF_DATA)) {
            return getPlaceWithUserId(language, (String) params.get(AppConstants.KEY_USER_ID), (TypeOfData) params.get(AppConstants.KEY_TYPE_OF_DATA));
        } else if (keySet.contains(AppConstants.PLACE_ID) && keySet.contains(AppConstants.KEY_USER_ID) && keySet.contains(AppConstants.KEY_USER_NAME)) {
            return getPlaceDetails((String) params.get(AppConstants.KEY_USER_NAME), (String) params.get(AppConstants.KEY_USER_ID), (String) params.get(AppConstants.PLACE_ID), language);
        }
        throw new ServerSideException("No matching get method found in " + getStrategyName());
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.GetPlaceStrategy;
    }

    public Response<DhPlace> getPaginatedPlaces(String language, int page, int size, double lat, double lng) {
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
                    d.setDistance(ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_UNKNOWN));
                }
            } else {
                d.setDistance(ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_UNKNOWN));
            }
            //calculate open/close msg
            String[] openCloseMsg = Utility.calculatePlaceOpenCloseMsg(d.getPlaceAvailablityDetails(), language);
            boolean isOpen = openCloseMsg.length > 1 && (openCloseMsg[1].equalsIgnoreCase(AppConstants.OPEN));
            LOGGER.info("PlaceServiceImpl->getPaginatedPlaces : isOpen=" + isOpen);
            d.setPlaceOpen(isOpen);
            d.setOpenCloseMsg(openCloseMsg[0]);

            DhUser dhUser = Utility.getUserDetailsFromId(d.getAddedBy(), mongoTemplate, true, false, false);
            if (dhUser != null) d.setUserName(dhUser.getFirstName());

        }
        return new Response<DhPlace>(true, 200, "Query successful", dhPlaceList.size(), dhPlacePages.getNumber(), dhPlacePages.getTotalPages(), dhPlacePages.getTotalElements(), dhPlaceList);

    }

    public Response<DhPlace> getPlaceDetails(String userName, String userId, String placeId, String language) {
        if (Utility.isFieldEmpty(placeId)) {
            return new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_PLACE_ID_IS_MISSING), new ArrayList<>(), 0);
        }
        Query query = new Query(Criteria.where(AppConstants.PLACE_ID).is(placeId));
        query.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        DhPlace dhPlace = mongoTemplate.findOne(query, DhPlace.class, AppConstants.COLLECTION_DH_PLACE);
        if (dhPlace == null) {
            return new Response<DhPlace>(true, 402, "No active place found with given Place Id", new ArrayList<>());
        } else {

            if (ProductPricesVisibilityEnum.ONLY_REQUESTED.name().equalsIgnoreCase(dhPlace.getProductPricesVisible())) {
                List<ProductPricesVisibleUsers> pList = dhPlace.getProductPricesVisibleUsers();
                if (pList != null) {
                    for (ProductPricesVisibleUsers p : pList) {
                        if (userId.equals(p.getUserId())) {
                            dhPlace.setAlreadyRequestForProductPrices(true);
                            if (AppConstants.STATUS_ACTIVE.equalsIgnoreCase(p.getStatus()))
                                dhPlace.setHasAccessToProductPrices(true);
                            break;
                        }
                    }
                }
            } else if (ProductPricesVisibilityEnum.HIDE.name().equalsIgnoreCase(dhPlace.getProductPricesVisible())) {
                dhPlace.setHasAccessToProductPrices(false);
            } else if (ProductPricesVisibilityEnum.PUBLIC.name().equalsIgnoreCase(dhPlace.getProductPricesVisible())) {
                dhPlace.setHasAccessToProductPrices(true);
            }


            DhViews dhViews = new DhViews();
            dhViews.setContentId(dhPlace.getPlaceId());
            dhViews.setContentType(ContentType.CONTENT_PLACE);
            dhViews.setUserId(userId);
            dhViews.setUserName(userName);
            dhViews.setViewId(Utility.getUUID());
            dhViews = (DhViews) Utility.setCommonAttrs(dhViews, AppConstants.STATUS_ACTIVE);
            mongoTemplate.save(dhViews, AppConstants.COLLECTION_DH_VIEWS);
            Update updateVisits = new Update();
            updateVisits.set(AppConstants.NUMBER_OF_VIEWS, dhPlace.getNumberOfViews() + 1);
            updateVisits.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
            mongoTemplate.updateFirst(query, updateVisits, DhPlace.class);
            return new Response<DhPlace>(true, 200, "Query successful", Collections.singletonList(dhPlace));
        }

    }

    public Response<DhPlace> getPlaceWithUserId(String language, String userId, TypeOfData typeOfData) {
        if (Utility.isFieldEmpty(userId)) {
            return new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_USER_ID_IS_MISSING), new ArrayList<>(), 0);
        }

        Query query = new Query(Criteria.where(AppConstants.ADDED_BY).is(userId));
        query.addCriteria(Criteria.where(AppConstants.PLACE_TYPE).regex(AppConstants.BUSINESS_PLACE, "i"));
        query.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        switch (typeOfData) {
            case PLACE_DATA:
                break;
            case POST_DATA:
                query.fields().include(AppConstants.PLACE_TYPE);
                query.fields().include(AppConstants.PLACE_ID);
                query.fields().include(AppConstants.PLACE_NAME);
                query.fields().include(AppConstants.PLACE_ADDRESS);
                query.fields().include(AppConstants.PLACE_CONTACT);
                break;
            default:
                return new Response<>(false, 402, "TypeOfData is required", new ArrayList<>(), 0);
        }

        List<DhPlace> dhPlaceList = mongoTemplate.find(query, DhPlace.class);
        return new Response<DhPlace>(true, 200, "Query successful", dhPlaceList);
    }
}
