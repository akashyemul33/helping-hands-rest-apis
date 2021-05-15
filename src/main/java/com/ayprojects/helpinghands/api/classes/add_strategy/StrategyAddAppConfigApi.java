package com.ayprojects.helpinghands.api.classes.add_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyAddBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.dao.appconfig.AppConfigDao;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhAppConfig;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.tools.CalendarOperations;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Component
public class StrategyAddAppConfigApi implements StrategyAddBehaviour<DhAppConfig> {

    @Autowired
    AppConfigDao appConfigDao;

    @Override
    public Response<DhAppConfig> add(String language, DhAppConfig dhAppConfig) throws ServerSideException {
        if (dhAppConfig == null) {
            return new Response<DhAppConfig>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language), new ArrayList<>(), 0);
        }

        try {
            appConfigDao.addAppConfig(dhAppConfig);
            dhAppConfig.setCreatedDateTime(CalendarOperations.currentDateTimeInUTC());
            dhAppConfig.setModifiedDateTime(CalendarOperations.currentDateTimeInUTC());
            dhAppConfig.setStatus(AppConstants.STATUS_PENDING);
            dhAppConfig.setSchemaVersion(AppConstants.SCHEMA_VERSION);
            appConfigDao.addAppConfig(dhAppConfig);
            Response<DhAppConfig> returnResponse = new Response<>(true, 201, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_APP_CONFIG_ADDED, language), Collections.singletonList(dhAppConfig));
            returnResponse.setLogActionMsg(AppConstants.ACTION_NEW_APPCONFIG_ADDED + "by UserName:" + dhAppConfig.getAddedBy());
            return new Response<>(true, 201, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_APP_CONFIG_ADDED, language), Collections.singletonList(dhAppConfig));
        } catch (Exception e) {
            throw new ServerSideException(Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_ERROR_WHILE_ADDING_APP_CONFIG, language));
        }
    }

    @Override
    public Response<DhAppConfig> add(String language, DhAppConfig obj, HashMap<String, Object> params) throws ServerSideException {
        return null;
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.AddAppConfigStrategy;
    }
}
