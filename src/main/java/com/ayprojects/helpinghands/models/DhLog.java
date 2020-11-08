package com.ayprojects.helpinghands.models;

public class DhLog extends CommonUsedAttributes{
    private String logId;
    private String userId;
    private String action;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public DhLog(String logId, String userName, String userId, String action, String createdDateTime, String modifiedDateTime, String schemaVersion) {
        this.logId = logId;
        this.userName = userName;
        this.userId = userId;
        this.action = action;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
        this.schemaVersion = schemaVersion;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
