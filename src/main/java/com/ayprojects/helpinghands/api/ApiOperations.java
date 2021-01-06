package com.ayprojects.helpinghands.api;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyAddBehaviour;
import com.ayprojects.helpinghands.api.behaviours.StrategyGetBehaviour;
import com.ayprojects.helpinghands.api.behaviours.UploadBehaviour;
import com.ayprojects.helpinghands.api.classes.add_strategy.StrategyAddLogApi;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.AllCommonUsedAttributes;
import com.ayprojects.helpinghands.models.DhLog;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.security.UserDetailsServiceImpl;
import com.ayprojects.helpinghands.services.common_service.CommonService;
import com.ayprojects.helpinghands.services.log.LogService;
import com.ayprojects.helpinghands.util.headers.IHeaders;
import com.ayprojects.helpinghands.util.tools.CalendarOperations;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import javax.rmi.CORBA.Util;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class ApiOperations<T extends AllCommonUsedAttributes> {

    @Autowired
    AddStrategyFactory addStrategyFactory;

    @Autowired
    GetStrategyFactory getStrategyFactory;

    UploadBehaviour uploadBehaviour;

    public static AllCommonUsedAttributes setCommonAttrs(AllCommonUsedAttributes obj, String status) {
        if (obj == null) obj = new AllCommonUsedAttributes();
        obj.setSchemaVersion(AppConstants.SCHEMA_VERSION);
        CalendarOperations calendarOperations = new CalendarOperations();
        obj.setCreatedDateTime(calendarOperations.currentDateTimeInUTC());
        obj.setModifiedDateTime(calendarOperations.currentDateTimeInUTC());
        obj.setStatus(status);
        return obj;
    }

    @SuppressWarnings("unchecked")
    public Response<T> get(Authentication authentication, HttpHeaders httpHeaders, String version, StrategyName strategyName, HashMap<String, Object> params) throws ServerSideException {
        String language = IHeaders.getLanguageFromHeader(httpHeaders);
        StrategyGetBehaviour<T> strategyGetBehaviour = (StrategyGetBehaviour<T>) getStrategyFactory.findStrategy(strategyName);
        LOGGER.info("get=>strategyName=" + strategyGetBehaviour.getStrategyName());
        return strategyGetBehaviour.get(language, params);
    }

    @SuppressWarnings("unchecked")
    public Response<T> add(Authentication authentication, HttpHeaders httpHeaders, T obj, StrategyName strategyName, String version) throws ServerSideException {
        String language = IHeaders.getLanguageFromHeader(httpHeaders);
        StrategyAddBehaviour<T> strategyAddBehaviour = (StrategyAddBehaviour<T>) addStrategyFactory.findStrategy(strategyName);
        LOGGER.info("add=>strategyName=" + strategyAddBehaviour.getStrategyName());
        return strategyAddBehaviour.add(language, obj);
    }
}
