package com.ayprojects.helpinghands.services.log;

import com.ayprojects.helpinghands.models.DhLog;

import org.springframework.stereotype.Service;

@Service
public interface LogService {
    DhLog addLog(DhLog dhLog);
}
