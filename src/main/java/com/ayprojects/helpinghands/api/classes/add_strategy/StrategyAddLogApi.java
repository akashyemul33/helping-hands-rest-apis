package com.ayprojects.helpinghands.api.classes.add_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyAddBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.dao.log.LogDao;
import com.ayprojects.helpinghands.models.DhLog;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.tools.CalendarOperations;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class StrategyAddLogApi implements StrategyAddBehaviour<DhLog> {

    CalendarOperations calendarOperations;

    @Autowired
    LogDao logDao;

    public StrategyAddLogApi() {
        this.calendarOperations = new CalendarOperations();
    }

    @Override
    public Response<DhLog> add(String language, DhLog obj) {
        if (obj == null)
            throw new NullPointerException("DhLog must not be null !");

        if (Utility.isFieldEmpty(obj.getAction()))
            throw new IllegalArgumentException("Action must be there in DhLog object.");

        obj.setLogId(Utility.getUUID());
        obj.setCreatedDateTime(calendarOperations.currentDateTimeInUTC());
        obj.setModifiedDateTime(calendarOperations.currentDateTimeInUTC());
        obj.setSchemaVersion(AppConstants.SCHEMA_VERSION);
        logDao.addLog(obj);
        return new Response<>(true, 201, "Log saved successfully", new ArrayList<>());
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.AddLogStrategy;
    }


}
