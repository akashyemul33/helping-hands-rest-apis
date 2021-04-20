package com.ayprojects.helpinghands.api.classes.get_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyGetBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.dao.appconfig.AppConfigDao;
import com.ayprojects.helpinghands.models.DhAppConfig;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.headers.IHeaders;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

@Component
public class StrategyGetAppConfig implements StrategyGetBehaviour<DhAppConfig> {
    @Autowired
    AppConfigDao appConfigDao;

    @Override
    public Response<DhAppConfig> get(String language, HashMap<String, Object> params) {
        return getActiveAppConfig(language);
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.GetAppConfigStrategy;
    }

    public Response<DhAppConfig> getActiveAppConfig(String language) {
        Optional<DhAppConfig> dhAppConfigData = appConfigDao.getActiveAppConfig();

        if (dhAppConfigData.isPresent()) {
            DhAppConfig dhAppConfig = dhAppConfigData.get();
            if (dhAppConfig != null && dhAppConfig.getStatus().equalsIgnoreCase(AppConstants.STATUS_ACTIVE)) {
                return new Response<>(true, 200, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_APP_CONFIG_FOUND), Collections.singletonList(dhAppConfig), 1);
            }
        }
        return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_APP_CONFIG_NOT_FOUND), new ArrayList<>());
    }
}
