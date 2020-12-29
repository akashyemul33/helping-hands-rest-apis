package com.ayprojects.helpinghands.services.appconfig;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.dao.appconfig.AppConfigDao;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhAppConfig;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.security.UserDetailsDecorator;
import com.ayprojects.helpinghands.security.UserDetailsServiceImpl;
import com.ayprojects.helpinghands.util.tools.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class AppConfigServiceImpl implements AppConfigService {

    @Autowired
    AppConfigDao appConfigDao;

    @Autowired
    Utility utility;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    public Response<DhAppConfig> addAppConfig(Authentication authentication, HttpHeaders httpHeaders, DhAppConfig dhAppConfig, String version) throws ServerSideException {
        String language =  Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("AppConfigServiceImpl->addAppConfig : language="+language);
        if(dhAppConfig==null)
        {
            return  new Response<DhAppConfig>(false,402,Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY,language),new ArrayList<>(),0);
        }

        try{
        appConfigDao.addAppConfig(dhAppConfig);
        UserDetailsDecorator userDetails = userDetailsService.loadUserByUsername(authentication.getName());
        dhAppConfig.setCreatedDateTime(Utility.currentDateTimeInUTC());
        dhAppConfig.setModifiedDateTime(Utility.currentDateTimeInUTC());
        dhAppConfig.setStatus(AppConstants.STATUS_PENDING);
        dhAppConfig.setSchemaVersion(AppConstants.SCHEMA_VERSION);
        appConfigDao.addAppConfig(dhAppConfig);
        utility.addLog(authentication.getName(),AppConstants.ACTION_NEW_APPCONFIG_ADDED+"by UserId:"+userDetails.getUser().getUserId());
        return new Response<>(true,201,Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_APP_CONFIG_ADDED,language),Collections.singletonList(dhAppConfig));
        }
        catch (Exception e){
            throw new ServerSideException(Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_ERROR_WHILE_ADDING_APP_CONFIG,language));
        }
    }

    @Override
    public Response<DhAppConfig> getActiveAppConfig(HttpHeaders httpHeaders, Authentication authentication, String version){
        String language =  Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("AppConfigServiceImpl->getActiveAppConfig : language="+language);
        Optional<DhAppConfig> dhAppConfig = appConfigDao.getActiveAppConfig();
        return dhAppConfig.isPresent() ?
                new Response<DhAppConfig>(true,201,AppConstants.FOUND_APP_CONFIG,Collections.singletonList(dhAppConfig.get()), 1)
        : new Response<DhAppConfig>(false,402,"App config not found",new ArrayList<>(), 0);
    }
}
