package com.ayprojects.helpinghands.services.log;

import com.ayprojects.helpinghands.dao.log.LogDao;
import com.ayprojects.helpinghands.models.DhLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService{

    @Autowired
    LogDao logDao;

    @Override
    public DhLog addLog(DhLog dhLog) {
        return logDao.addLog(dhLog);
    }
}
