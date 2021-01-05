package com.ayprojects.helpinghands.api;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.AddBehaviour;
import com.ayprojects.helpinghands.api.behaviours.UploadBehaviour;
import com.ayprojects.helpinghands.api.classes.AddLogApi;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.AllCommonUsedAttributes;
import com.ayprojects.helpinghands.models.DhLog;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.security.UserDetailsServiceImpl;
import com.ayprojects.helpinghands.services.common_service.CommonService;
import com.ayprojects.helpinghands.services.log.LogService;
import com.ayprojects.helpinghands.util.headers.IHeaders;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.CalendarOperations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class ApiOperations<T extends AllCommonUsedAttributes> {

    UploadBehaviour uploadBehaviour;
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    CommonService commonService;
    @Autowired
    LogService logService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

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
    public Response<T> add(Authentication authentication, HttpHeaders httpHeaders, T obj, String version) throws ServerSideException {
        String language = IHeaders.getLanguageFromHeader(httpHeaders);
        if (mongoTemplate == null) {
            LOGGER.info("ApiOperations.add->mongotemplate must not be empty !");
            throw new ServerSideException(ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_SOMETHING_WENT_WRONG));
        }

        try {
            AddBehaviour<T> addBehaviour = (AddBehaviour<T>) ApiOperationsFactory.getObject(userDetailsService, commonService, obj);
            Response<T> returnResponse = addBehaviour.add(language, mongoTemplate, obj);
            addLog(language, authentication.getName(), returnResponse.getLogActionMsg());
            return returnResponse;
        } catch (Exception e) {
            LOGGER.info("add->catch=" + e.getMessage());
            throw new ServerSideException(ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_SOMETHING_WENT_WRONG));
        }
    }

    private void addLog(String language, String name, String logActionMsg) {
        new AddLogApi().add(language, mongoTemplate, new DhLog(name, logActionMsg));
    }

    public void setAddBehaviour(Class<T> dhUserClass) {

    }

    public UploadBehaviour getUploadBehaviour() {
        return uploadBehaviour;
    }

    public void setUploadBehaviour(UploadBehaviour uploadBehaviour) {
        this.uploadBehaviour = uploadBehaviour;
    }

}
