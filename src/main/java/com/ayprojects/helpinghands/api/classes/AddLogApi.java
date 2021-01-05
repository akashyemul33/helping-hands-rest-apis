package com.ayprojects.helpinghands.api.classes;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.AddBehaviour;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhLog;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.tools.CalendarOperations;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AddLogApi implements AddBehaviour<DhLog> {

    CalendarOperations calendarOperations;

    public AddLogApi() {
        this.calendarOperations = new CalendarOperations();
    }

    @Override
    public Response<DhLog> add(String language, MongoTemplate mongoTemplate, DhLog obj){
        if (obj == null || mongoTemplate==null)
            throw new NullPointerException("DhLog must not be null !");

        if (Utility.isFieldEmpty(obj.getAction()))
            throw new IllegalArgumentException("Action must be there in DhLog object.");

        obj.setLogId(Utility.getUUID());
        obj.setCreatedDateTime(calendarOperations.currentDateTimeInUTC());
        obj.setModifiedDateTime(calendarOperations.currentDateTimeInUTC());
        obj.setSchemaVersion(AppConstants.SCHEMA_VERSION);
        mongoTemplate.save(obj);
        return new Response<>(true, 201, "Log saved successfully", new ArrayList<>());
    }


}
