package com.ayprojects.helpinghands.models;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class DhUser extends AllCommonUsedAttributes {
    @Indexed
    private String userId;
    private String firstName;
    private String lastName;
    private String password;
    private String countryCode;
    private String logInTime;
    private String lastLogInTime;
    private String lastLogOutTime;
    private String triedToLoginTime;
    private String profileImgLow;
    private String profileImgHigh;
    private long numberOfHHPosts;
    private long numberOfHHHelps;
    private long avgHHRating;

    public long getNumberOfHHPosts() {
        return numberOfHHPosts;
    }

    public void setNumberOfHHPosts(long numberOfHHPosts) {
        this.numberOfHHPosts = numberOfHHPosts;
    }

    public long getNumberOfHHHelps() {
        return numberOfHHHelps;
    }

    public void setNumberOfHHHelps(long numberOfHHHelps) {
        this.numberOfHHHelps = numberOfHHHelps;
    }

    public long getAvgHHRating() {
        return avgHHRating;
    }

    public void setAvgHHRating(long avgHHRating) {
        this.avgHHRating = avgHHRating;
    }

    //    private Address addressDetails;
    @Indexed
    private String mobileNumber;
    private String emailId;
    private String[] roles;
    private UserSettings userSettings;
    private boolean sponsored;
    private String sponsoredDate;
    private List<String> subscribedPlaces;
    private UserActivity userActivity;
    //decides whether user settings should be applied or not
    private boolean userSettingEnabled;
    private String languageSelected;
    private String fcmToken;

    public DhUser() {
        super();
    }

    public DhUser(String uniqueUserID, String imgUrlLow, String imgUrlHigh) {
        this.userId = uniqueUserID;
        this.profileImgLow = imgUrlLow;
        this.profileImgHigh = imgUrlHigh;
    }

    public DhUser(String schemaVersion, String createdDateTime, String modifiedDateTime, String status, String userId, String firstName, String password, String profileImgLow, String mobileNumber, String emailId, String[] roles, UserSettings userSettings, boolean sponsored, String sponsoredDate, List<String> subscribedPlaces, UserActivity userActivity, boolean isUserSettingEnabled, String languageSelected, String lastName) {
        this.schemaVersion = schemaVersion;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
        this.status = status;
        this.userId = userId;
        this.firstName = firstName;
        this.password = password;
        this.profileImgLow = profileImgLow;
        this.mobileNumber = mobileNumber;
        this.emailId = emailId;
        this.roles = roles;
        this.userSettings = userSettings;
        this.sponsored = sponsored;
        this.sponsoredDate = sponsoredDate;
        this.subscribedPlaces = subscribedPlaces;
        this.userActivity = userActivity;
        this.userSettingEnabled = isUserSettingEnabled;
        this.languageSelected = languageSelected;
        this.lastName = lastName;
    }

    public DhUser(String userId, String firstName, String password, String profileImgLow, String mobileNumber, String emailId, String[] roles, String languageSelected, String lastName) {
        this.userId = userId;
        this.firstName = firstName;
        this.password = password;
        this.profileImgLow = profileImgLow;
        this.mobileNumber = mobileNumber;
        this.emailId = emailId;
        this.roles = roles;
        this.languageSelected = languageSelected;
        this.lastName = lastName;
    }

    public String getProfileImgHigh() {
        return profileImgHigh;
    }

    public void setProfileImgHigh(String profileImgHigh) {
        this.profileImgHigh = profileImgHigh;
    }

    public String getTriedToLoginTime() {
        return triedToLoginTime;
    }

    public void setTriedToLoginTime(String triedToLoginTime) {
        this.triedToLoginTime = triedToLoginTime;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getLastLogInTime() {
        return lastLogInTime;
    }

    public void setLastLogInTime(String lastLogInTime) {
        this.lastLogInTime = lastLogInTime;
    }

    public String getLastLogOutTime() {
        return lastLogOutTime;
    }

    public void setLastLogOutTime(String lastLogOutTime) {
        this.lastLogOutTime = lastLogOutTime;
    }

    public String getLogInTime() {
        return logInTime;
    }

    public void setLogInTime(String logInTime) {
        this.logInTime = logInTime;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public boolean getUserSettingEnabled() {
        return userSettingEnabled;
    }

    public void setUserSettingEnabled(boolean userSettingEnabled) {
        this.userSettingEnabled = userSettingEnabled;
    }

    public boolean getSponsored() {
        return sponsored;
    }

    public void setSponsored(boolean sponsored) {
        this.sponsored = sponsored;
    }

    public String getSponsoredDate() {
        return sponsoredDate;
    }

    public void setSponsoredDate(String sponsoredDate) {
        this.sponsoredDate = sponsoredDate;
    }

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileImgLow() {
        return profileImgLow;
    }

    public void setProfileImgLow(String profileImgLow) {
        this.profileImgLow = profileImgLow;
    }
}
