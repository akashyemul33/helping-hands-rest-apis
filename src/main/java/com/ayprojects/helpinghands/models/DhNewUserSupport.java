package com.ayprojects.helpinghands.models;

public class DhNewUserSupport extends AllCommonUsedAttributes {
    private String fcmToken;
    private String mobileNumber;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String userId;

    public DhNewUserSupport(String fcmToken, String mobileNumber, String countryCode) {
        this.fcmToken = fcmToken;
        this.mobileNumber = mobileNumber;
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    private String countryCode;

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
