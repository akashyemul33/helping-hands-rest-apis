package com.ayprojects.helpinghands.models;

public class NameAndUserId {
    private String userId;
    private String userName;
    private String createdDateTime;

    public NameAndUserId(String userId, String userName, String createdDateTime) {
        this.userId = userId;
        this.userName = userName;
        this.createdDateTime = createdDateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }
}
