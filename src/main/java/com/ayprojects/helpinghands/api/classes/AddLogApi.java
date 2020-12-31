package com.ayprojects.helpinghands.api.classes;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.AddBehaviour;
import com.ayprojects.helpinghands.dao.log.LogDao;
import com.ayprojects.helpinghands.models.DhLog;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AddLogApi implements AddBehaviour<DhLog> {
    @Autowired
    LogDao logDao;

    DhLog dhLog;


    public AddLogApi(){}//do not remove, its needed
    public AddLogApi(DhLog dhLog) {
        this.dhLog = dhLog;
    }

    @Override
    public Response<DhLog> add() {
        if (dhLog == null)
            throw new NullPointerException("DhLog must not be null !");

        if (Utility.isFieldEmpty(dhLog.getAction()))
            throw new IllegalArgumentException("Action should be set in DhLog object.");

        dhLog.setLogId(Utility.getUUID());
        dhLog.setCreatedDateTime(Utility.currentDateTimeInUTC());
        dhLog.setModifiedDateTime(Utility.currentDateTimeInUTC());
        dhLog.setSchemaVersion(AppConstants.SCHEMA_VERSION);

        logDao.addLog(dhLog);
        return new Response<>(true, 201, "Log saved successfully", new ArrayList<>());
    }
}
