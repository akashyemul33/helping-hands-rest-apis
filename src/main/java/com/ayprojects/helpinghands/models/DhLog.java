package com.ayprojects.helpinghands.models;

public class DhLog extends AllCommonUsedAttributes {
    private String logId;
    private String action;
    private String userName;

    public DhLog(String action) {
        this.action = action;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public DhLog(String userName,String action){
        this.userName = userName;
        this.action = action;
    }
    /*public DhLog(String logId, String userName, String action, String createdDateTime, String modifiedDateTime) {
        this.logId = logId;
        this.userName = userName;
        this.action = action;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
    }
*/
    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
