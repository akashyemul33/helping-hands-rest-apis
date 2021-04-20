package com.ayprojects.helpinghands.api.logs;


import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.dao.log.LogDao;
import com.ayprojects.helpinghands.models.DhLog;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
public class AddLogApiTest {

    @Autowired
    ApiOperations<DhLog> apiOperations;

    @Autowired
    LogDao logDao;

    @Test
    void contextShouldBeLoaded() {
        assertNotNull(apiOperations);
    }

    @Test
    void logDaoShouldBeLoaded() {
        assertNotNull(logDao);
    }

    @Test
    void givenEmptyDhLogThenException() {
        //DhLog dhLog = new DhLog();
        assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                apiOperations.add(null, null,null, StrategyName.AddLogStrategy, AppConstants.CURRENT_API_VERSION);
            }
        });
    }

    /*@Test
    void givenEmptyActionThenException() {
        DhLog dhLog1 = new DhLog("userName", "");
        DhLog dhLog2 = new DhLog("userName", "");
        AddLogApi addLogApi1 = new AddLogApi(dhLog1);
        AddLogApi addLogApi2 = new AddLogApi(dhLog2);
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                addLogApi1.add();
            }
        });
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                addLogApi2.add();
            }
        });
    }


    @Test
    void givenDhLogThenSucceed() {
        DhLog dhLog = new DhLog("userName", "action");
        AddLogApi addLogApi = new AddLogApi(dhLog);
        Response<DhLog> expectedResponse = new Response<>(true, 201, "Log saved successfully", new ArrayList<>());
        Response<DhLog> actualResponse = addLogApi.add();
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
    }*/
}
