package com.ayprojects.helpinghands.models;

import java.util.List;

public class DhUser extends AllCommonUsedAttributes {
    private String userId;
    private String name;
    private String password;
    private String profileImg;
    private Address addressDetails;
    private String mobileNumber;
    private String emailId;
    private String[] roles;
    private UserSettings userSettings;

    public DhUser() {
        super();
    }

    private boolean isSponsored;
    private String sponsoredDate;
    private List<String> subscribedPlaces;
    private UserActivity userActivity;

    public DhUser(String uniqueUserID, String imgUrl) {
        this.userId = uniqueUserID;
        this.profileImg = imgUrl;
    }

    public UserActivity getUserActivity() {
        return userActivity;
    }

    public void setUserActivity(UserActivity userActivity) {
        this.userActivity = userActivity;
    }

    public List<String> getSubscribedPlaces() {
        return subscribedPlaces;
    }

    public void setSubscribedPlaces(List<String> subscribedPlaces) {
        this.subscribedPlaces = subscribedPlaces;
    }

    public boolean isUserSettingEnabled() {
        return isUserSettingEnabled;
    }

    public boolean isSponsored() {
        return isSponsored;
    }

    public void setSponsored(boolean sponsored) {
        isSponsored = sponsored;
    }

    public String getSponsoredDate() {
        return sponsoredDate;
    }

    public void setSponsoredDate(String sponsoredDate) {
        this.sponsoredDate = sponsoredDate;
    }

    public void setUserSettingEnabled(boolean userSettingEnabled) {
        isUserSettingEnabled = userSettingEnabled;
    }

 //decides whether user settings should be applied or not
    private boolean isUserSettingEnabled;

    public UserSettings getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(UserSettings userSettings) {
        this.userSettings = userSettings;
    }

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

    /*public DhUser(String schemaVersion, String createdDateTime, String modifiedDateTime, String status,
    this.schemaVersion =schemaVersion;
                          this.createdDateTime = createdDateTime;
                          this.modifiedDateTime =modifiedDateTime;
                          this.status=status;*/

    public DhUser(String schemaVersion, String createdDateTime, String modifiedDateTime, String status,String userId, String name, String password, String profileImg, Address addressDetails, String mobileNumber, String emailId, String[] roles, UserSettings userSettings, boolean isSponsored, String sponsoredDate, List<String> subscribedPlaces, UserActivity userActivity, boolean isUserSettingEnabled, String languageSelected) {
        this.schemaVersion =schemaVersion;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime =modifiedDateTime;
        this.status=status;
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.profileImg = profileImg;
        this.addressDetails = addressDetails;
        this.mobileNumber = mobileNumber;
        this.emailId = emailId;
        this.roles = roles;
        this.userSettings = userSettings;
        this.isSponsored = isSponsored;
        this.sponsoredDate = sponsoredDate;
        this.subscribedPlaces = subscribedPlaces;
        this.userActivity = userActivity;
        this.isUserSettingEnabled = isUserSettingEnabled;
        this.languageSelected = languageSelected;
    }

    public DhUser(String userId, String name, String password, String profileImg, Address addressDetails, String mobileNumber, String emailId, String[] roles, String languageSelected) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.profileImg = profileImg;
        this.addressDetails = addressDetails;
        this.mobileNumber = mobileNumber;
        this.emailId = emailId;
        this.roles = roles;
        this.languageSelected = languageSelected;
    }
}
