package com.ayprojects.helpinghands.services.log;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.dao.log.LogDao;
import com.ayprojects.helpinghands.models.DhLog;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogDao logDao;

    @Override
    public DhLog addLog(DhLog dhLog) {
        if (dhLog == null) {
            throw new IllegalArgumentException("DhLog must not be null !");
        }

        if (Utility.isFieldEmpty(dhLog.getAction())) {
            throw new IllegalArgumentException("Action must not be empty in dhLog object!");
        }

        if (Utility.isFieldEmpty(dhLog.getUserName())) {
            throw new IllegalArgumentException("UserName must not be empty in dhLog object!");
        }

        dhLog.setLogId(Utility.isFieldEmpty(dhLog.getLogId()) ? Utility.getUUID() : dhLog.getLogId());
        dhLog.setCreatedDateTime(Utility.isFieldEmpty(dhLog.getCreatedDateTime()) ? Utility.currentDateTimeInUTC() : dhLog.getCreatedDateTime());
        dhLog.setModifiedDateTime(Utility.isFieldEmpty(dhLog.getModifiedDateTime()) ? Utility.currentDateTimeInUTC() : dhLog.getModifiedDateTime());
        dhLog.setSchemaVersion(AppConstants.SCHEMA_VERSION);
        dhLog.setStatus(AppConstants.STATUS_ACTIVE);
        return logDao.addLog(dhLog);
    }
}
