package com.ayprojects.helpinghands.dao.log;

import com.ayprojects.helpinghands.models.DhLog;
import com.ayprojects.helpinghands.repositories.LogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LogDaoImpl implements LogDao{
    @Autowired
    LogRepository logRepository;

    @Override
    public DhLog addLog(DhLog dhLog) {
        return logRepository.save(dhLog);
    }
}
