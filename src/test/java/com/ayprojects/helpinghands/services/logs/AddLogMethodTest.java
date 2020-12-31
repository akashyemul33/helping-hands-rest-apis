package com.ayprojects.helpinghands.services.logs;


import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.models.DhLog;
import com.ayprojects.helpinghands.services.log.LogService;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
public class AddLogMethodTest {

    @Autowired
    LogService logService;

    @Test
    void contextShouldBeLoaded() {
        assertNotNull(logService);
    }

    @Test
    void givenEmptyDhLogThenException() {
        //DhLog dhLog = new DhLog();
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                logService.addLog(null);
            }
        });
    }

    @Test
    void logIdShouldBeCreatedWhenEmptyLogId() {
        DhLog dhLog = new DhLog("abc", "Adf");
        DhLog actualDhLog = logService.addLog(dhLog);
        assertFalse(Utility.isFieldEmpty(actualDhLog.getLogId()));
    }

    @Test
    void givenEmptyActionThenException() {
        DhLog dhLog1 = new DhLog("userName", null);
        DhLog dhLog2 = new DhLog("userName", "");
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                logService.addLog(dhLog1);
            }
        });
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                logService.addLog(dhLog2);
            }
        });
    }

    @Test
    void givenEmptyUserNameThenException() {
        DhLog dhLog1 = new DhLog("", "ac");
        DhLog dhLog2 = new DhLog(null, "abc");
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                logService.addLog(dhLog1);
            }
        });
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                logService.addLog(dhLog2);
            }
        });
    }

    @Test
    void schemaVersionShouldBeAddedWhenPassedEmtpy() {
        DhLog dhLog = new DhLog("abc", "Adf");
        DhLog actualDhLog = logService.addLog(dhLog);
        assertFalse(Utility.isFieldEmpty(actualDhLog.getSchemaVersion()));
    }

    @Test
    void createdDatTimeShouldBeAddedWhenPassedEmpty() {
        DhLog dhLog = new DhLog("abc", "Adf");
        DhLog actualDhLog = logService.addLog(dhLog);
        assertFalse(Utility.isFieldEmpty(actualDhLog.getCreatedDateTime()));
    }

    @Test
    void modifiedDatTimeShouldBeAddedWhenPassedEmpty() {
        DhLog dhLog = new DhLog("abc", "Adf");
        DhLog actualDhLog = logService.addLog(dhLog);
        assertFalse(Utility.isFieldEmpty(actualDhLog.getModifiedDateTime()));
    }

    @Test
    void logShouldBeAddedWithAllReqdDetailsWhenPassedLessDetails() {
        DhLog dhLog = new DhLog("userName", "action");
        DhLog actualuDhLog = logService.addLog(dhLog);
        assertFalse(Utility.isFieldEmpty(actualuDhLog.getLogId()));
        assertFalse(Utility.isFieldEmpty(actualuDhLog.getUserName()));
        assertFalse(Utility.isFieldEmpty(actualuDhLog.getModifiedDateTime()));
        assertFalse(Utility.isFieldEmpty(actualuDhLog.getCreatedDateTime()));
        assertFalse(Utility.isFieldEmpty(actualuDhLog.getSchemaVersion()));
        assertEquals(AppConstants.SCHEMA_VERSION, actualuDhLog.getSchemaVersion());
    }

    @Test
    void logShouldBeAddedWithEnteredDetailsWhenPassedAllDetails() {
        String expectedLogId = Utility.getUUID();
        String expectedUserName = "A";
        String expectedAction = "Abc";
        String expectedCreatedDateTime = Utility.currentDateTimeInUTC();
        String expectedModifiedDateTime = Utility.currentDateTimeInUTC();
        DhLog dhLog = new DhLog(expectedUserName, expectedAction);
        dhLog.setLogId(expectedLogId);
        dhLog.setCreatedDateTime(expectedCreatedDateTime);
        dhLog.setModifiedDateTime(expectedModifiedDateTime);
        DhLog actualuDhLog = logService.addLog(dhLog);
        assertEquals(expectedLogId, actualuDhLog.getLogId());
        assertEquals(expectedUserName, actualuDhLog.getUserName());
        assertEquals(expectedAction, actualuDhLog.getAction());
        assertEquals(expectedCreatedDateTime, actualuDhLog.getCreatedDateTime());
        assertEquals(expectedModifiedDateTime, actualuDhLog.getModifiedDateTime());
        assertEquals(AppConstants.SCHEMA_VERSION, actualuDhLog.getSchemaVersion());
    }
}
