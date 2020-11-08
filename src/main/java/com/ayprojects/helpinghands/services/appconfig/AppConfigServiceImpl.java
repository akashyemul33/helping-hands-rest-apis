package com.ayprojects.helpinghands.services.appconfig;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.dao.appconfig.AppConfigDao;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhAppConfig;
import com.ayprojects.helpinghands.models.DhLog;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.security.UserDetailsDecorator;
import com.ayprojects.helpinghands.security.UserDetailsServiceImpl;
import com.ayprojects.helpinghands.services.log.LogService;
import com.ayprojects.helpinghands.tools.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class AppConfigServiceImpl implements AppConfigService {

    @Autowired
    AppConfigDao appConfigDao;

    @Autowired
    LogService logService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    public Response<DhAppConfig> addAppConfig(Authentication authentication,HttpHeaders httpHeaders,DhAppConfig dhAppConfig) throws ServerSideException {
        String language =  Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("AppConfigServiceImpl->addAppConfig : language="+language);

        try{
        Response<DhAppConfig> res = new Response<>();
        res.setStatus(true);
        res.setStatusCode(201);
        res.setMessage(Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_APP_CONFIG_ADDED,language));
        res.setData(Collections.singletonList(dhAppConfig));
        appConfigDao.addAppConfig(dhAppConfig);
        UserDetailsDecorator userDetails = userDetailsService.loadUserByUsername(authentication.getName());
        dhAppConfig.setCreatedDateTime(Utility.currentDateTimeInUTC());
        dhAppConfig.setModifiedDateTime(Utility.currentDateTimeInUTC());
        dhAppConfig.setStatus(AppConstants.PENDING);
        dhAppConfig.setSchemaVersion(AppConstants.SCHEMA_VERSION);
        appConfigDao.addAppConfig(dhAppConfig);
        logService.addLog(new DhLog(UUID.randomUUID().toString(),userDetails.getUser().getMobileNumber(),userDetails.getUser().getUserId(),AppConstants.NEW_APPCONFIG_ADDED,Utility.currentDateTimeInUTC(),Utility.currentDateTimeInUTC(),AppConstants.SCHEMA_VERSION));
        return res;
        }
        catch (Exception e){
            throw new ServerSideException(Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_ERROR_WHILE_ADDING_APP_CONFIG,language));
        }
    }

    @Override
    public Response<DhAppConfig> getActiveAppConfig(HttpHeaders httpHeaders, Authentication authentication){
        String language =  Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("language="+language);
        Optional<DhAppConfig> dhAppConfig = appConfigDao.getActiveAppConfig();
        return dhAppConfig.isPresent() ?
                new Response<DhAppConfig>(true,201,AppConstants.FOUND_APP_CONFIG,Collections.singletonList(dhAppConfig.get()), (long) 1)
        : new Response<DhAppConfig>(false,402,"App config not found",new ArrayList<>(), (long) 0);
    }
}
