package com.ayprojects.helpinghands.models;

public class DhUser extends AllCommonUsedAttributes {
    private String userId;
    private String name;
    private String password;
    private String profileImg;
    private Address addressDetails;
    private String mobileNumber;
    private String emailId;
    private String[] roles;

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getLanguageSelected() {
        return languageSelected;
    }

    public void setLanguageSelected(String languageSelected) {
        this.languageSelected = languageSelected;
    }

    private String languageSelected;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public Address getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(Address addressDetails) {
        this.addressDetails = addressDetails;
    }

    public DhUser(String schemaVersion, String createdDateTime, String modifiedDateTime, String status, String userId, String name, String password, String profileImg, Address addressDetails, String mobileNumber, String emailId, String languageSelected) {
        this.schemaVersion =schemaVersion;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime =modifiedDateTime;
        this.status=status;
        this.userId = userId;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.emailId = emailId;
        this.password = password;
        this.profileImg = profileImg;
        this.addressDetails = addressDetails;
        this.languageSelected= languageSelected;
    }
}
