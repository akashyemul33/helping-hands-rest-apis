package com.ayprojects.helpinghands.api.classes;

import com.ayprojects.helpinghands.api.behaviours.AddBehaviour;
import com.ayprojects.helpinghands.dao.log.LogDao;
import com.ayprojects.helpinghands.models.DhLog;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.beans.factory.annotation.Autowired;

public class AddLogApi implements AddBehaviour<DhLog> {
    @Autowired
    LogDao logDao;

    DhLog dhLog;

    public AddLogApi(DhLog dhLog) {
        this.dhLog = dhLog;
    }

    @Override
    public Response<DhLog> add() {
        //TODO do whatever you want
        return null;
    }
}
