package com.ayprojects.helpinghands.services.placecategories;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.dao.appconfig.AppConfigDao;
import com.ayprojects.helpinghands.dao.placecategories.PlaceCategoryDao;
import com.ayprojects.helpinghands.models.DhLog;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.PlaceSubCategories;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.security.UserDetailsServiceImpl;
import com.ayprojects.helpinghands.services.log.LogService;
import com.ayprojects.helpinghands.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.print.DocFlavor;
import javax.rmi.CORBA.Util;
import javax.swing.text.html.Option;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class PlaceCategoryServiceImpl implements PlaceCategoryService {

    @Autowired
    PlaceCategoryDao placeCategoryDao;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    LogService logService;


    @Override
    public Response<DhPlaceCategories> add(Authentication authentication, HttpHeaders httpHeaders, DhPlaceCategories dhPlaceCategories) {
        String language =  Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("PlaceCategoryServiceImpl->add : language="+language);
        if(dhPlaceCategories==null || dhPlaceCategories.getPlaceCategoryName()==null)
        {
            return new Response<DhPlaceCategories>(false,402,Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY_OR_PLACECATEGORYNAMES,language),new ArrayList<>(),(long)0);
        }

        if(Utility.isFieldEmpty(dhPlaceCategories.getPlaceCategoryName().getPlacecategorynameInEnglish())){
            return new Response<DhPlaceCategories>(false,402,Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_PLACE_CATEGORY_NAMES_EMPTY,language),new ArrayList<>(),(long)0);
        }

        DhUser dhUser = userDetailsService.loadUserByUsername(authentication.getName()).getUser();
        String addedBy = dhUser.getUserId();
        dhPlaceCategories.setSchemaVersion(AppConstants.SCHEMA_VERSION);
        dhPlaceCategories.setCreatedDateTime(Utility.currentDateTimeInUTC());
        dhPlaceCategories.setModifiedDateTime(Utility.currentDateTimeInUTC());
        dhPlaceCategories.setStatus(AppConstants.STATUS_PENDING);
        dhPlaceCategories.setPlaceCategoryId(Utility.getUUID());
        dhPlaceCategories.setAddedBy(addedBy);
        if(dhPlaceCategories.getPlaceSubCategories()!=null && dhPlaceCategories.getPlaceSubCategories().size()>0) {
            for(PlaceSubCategories placeSubCategories : dhPlaceCategories.getPlaceSubCategories())
            {
                if(placeSubCategories.getPlaceSubCategoryName()==null || Utility.isFieldEmpty(placeSubCategories.getPlaceSubCategoryName().getPlacesubcategorynameInEnglish())){
                    return new Response<DhPlaceCategories>(false,402,Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_PLACE_CATEGORY_NAMES_EMPTY,language),new ArrayList<>(),(long)0);
                }
                placeSubCategories.setAddedBy(addedBy);
                placeSubCategories.setPlaceSubCategoryId(Utility.getUUID());
                placeSubCategories.setCreatedDateTime(Utility.currentDateTimeInUTC());
                placeSubCategories.setModifiedDateTime(Utility.currentDateTimeInUTC());
                placeSubCategories.setStatus(AppConstants.STATUS_PENDING);
            }
        }
        placeCategoryDao.add(dhPlaceCategories);
        logService.addLog(new DhLog(Utility.getUUID(),dhUser.getMobileNumber(),dhUser.getUserId(),AppConstants.ACTION_NEW_PLACE_CATEGORY_ADDED,Utility.currentDateTimeInUTC(),Utility.currentDateTimeInUTC(),AppConstants.SCHEMA_VERSION));
        return new Response<DhPlaceCategories>(true,201,Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NEW_PLACE_CATEGORY_ADDED,language), Collections.singletonList(dhPlaceCategories), (long) 1);
    }

    @Override
    public Response<DhPlaceCategories> findByPlaceCategoryId(Authentication authentication, HttpHeaders httpHeaders, String placeCategoryId) {

        return null;
    }

    @Override
    public Response<DhPlaceCategories> findByStatus(Authentication authentication, HttpHeaders httpHeaders, String status) {
        return null;
    }

    @Override
    public Response<List<DhPlaceCategories>> findAllByStatus(Authentication authentication, HttpHeaders httpHeaders,String status) {
        String language =  Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("PlaceCategoryServiceImpl->findAllByStatus : language="+language);
        String findWithStatus = Utility.isFieldEmpty(status) ? AppConstants.STATUS_ACTIVE : status;

        Optional<List<DhPlaceCategories>> dhPlaceCategoriesList = placeCategoryDao.findAllByStatus(findWithStatus);
        int resStatusCode;
        boolean resStatus;
        String resMessage;
        int resTotalCount;
        List<DhPlaceCategories> resData;
        if(dhPlaceCategoriesList.isPresent()) {
            resStatus = true;
            resStatusCode = 201;
            resMessage = dhPlaceCategoriesList.get().size()+" place categories found .";
            resTotalCount = dhPlaceCategoriesList.get().size();
            resData = dhPlaceCategoriesList.get();
        }
        else{
            resStatus = false;
            resStatusCode = 402;
            resMessage = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NO_PLACECATEGORIES,language);
            resTotalCount = 0;
            resData=new ArrayList<>();
        }
        return new Response<List<DhPlaceCategories>>(resStatus,resStatusCode,resMessage, Collections.singletonList(resData),(long)resTotalCount);
    }
}
