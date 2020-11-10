package com.ayprojects.helpinghands.services.place;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhProduct;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.rmi.CORBA.Util;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class PlaceServiceImpl implements PlaceService{


    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    Utility utility;

    @Override
    public Response<DhPlace> addPlace(Authentication authentication, HttpHeaders httpHeaders, DhPlace dhPlace, String version) throws ServerSideException {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("PlaceServiceImpl->addPlace : language=" + language);

        if (dhPlace == null) {
            return new Response<DhPlace>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language), new ArrayList<>(), 0);
        }
        List<String> missingFieldsList = new ArrayList<>();
        if(Utility.isFieldEmpty(dhPlace.getAddedBy())) missingFieldsList.add("AddedBy");
        if(Utility.isFieldEmpty(dhPlace.getPlaceMainCategoryId())) missingFieldsList.add("PlaceMainCategoryId");
        if(Utility.isFieldEmpty(dhPlace.getPlaceSubCategoryId())) missingFieldsList.add("PlaceSubCategoryId");
        if(Utility.isFieldEmpty(dhPlace.getPlaceName())) missingFieldsList.add("PlaceName");
        if(Utility.isFieldEmpty(dhPlace.getPlaceType())) missingFieldsList.add("PlaceType");
        if(dhPlace.getPlaceAddress()==null)missingFieldsList.add("PlaceAddressBlock");
        if(Utility.isFieldEmpty(dhPlace.getPlaceCategoryName()))missingFieldsList.add("PlaceCategoryName");
        else {
            if(dhPlace.getPlaceAddress().getLat()==0)
                missingFieldsList.add("Lattitude");
            if(dhPlace.getPlaceAddress().getLng()==0)
                missingFieldsList.add("Longitude");
            if(Utility.isFieldEmpty(dhPlace.getPlaceAddress().getFullAddress())) {
                missingFieldsList.add("Full Address");
            }
        }
        if(dhPlace.getPlaceContact()==null)missingFieldsList.add("PlaceContactBlock");
        else{
            if(Utility.isFieldEmpty(dhPlace.getPlaceContact().getMobile())){
                missingFieldsList.add("MobileNumber");
            }
            if(Utility.isFieldEmpty(dhPlace.getPlaceContact().getEmail())){
                missingFieldsList.add("Email");
            }
        }
        if(dhPlace.getPlaceAvaialableDays()==null)missingFieldsList.add("PlaceAvailabilityDaysBlock");
        if(missingFieldsList.size()>0){
            String resMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY,language);
            resMsg = resMsg+" , these fields are missing : "+missingFieldsList;
            return new Response<DhPlace>(false,402,resMsg,new ArrayList<>(),0);
        }

        dhPlace.setPlaceId(Utility.getUUID());
        dhPlace.setSchemaVersion(AppConstants.SCHEMA_VERSION);
        dhPlace.setCreatedDateTime(Utility.currentDateTimeInUTC());
        dhPlace.setModifiedDateTime(Utility.currentDateTimeInUTC());
        dhPlace.setStatus(AppConstants.STATUS_ACTIVE);
        mongoTemplate.save(dhPlace,AppConstants.COLLECTION_DH_PLACE);
        utility.addLog(authentication.getName(),"New ["+dhPlace.getPlaceType()+"] place with category ["+dhPlace.getPlaceCategoryName()+"] has been added.");
        return new Response<>(true,201,Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NEW_PLACE_ADDED,language),new ArrayList<>(),1);
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
}
