package com.ayprojects.helpinghands.models;

public class DhAuthorization extends AllCommonUsedAttributes{
    private String username;
    private String password;
    private String userId;
    private String[] roles;

    public DhAuthorization(String schemaVersion, String createdDateTime, String modifiedDateTime, String status,String username, String password, String[] roles,String userId) {
        this.schemaVersion =schemaVersion;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime =modifiedDateTime;
        this.status=status;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
}
