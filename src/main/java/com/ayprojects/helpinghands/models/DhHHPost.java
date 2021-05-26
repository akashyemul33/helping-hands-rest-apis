package com.ayprojects.helpinghands.models;

import java.util.List;

public class DhHHPost extends AllCommonUsedAttributes {
    private String userId;
    private String userName;
    private String userProfile;
    private double avgHHRating;
    private String postId;
    private Address address;
    private String categoryId;
    private int numberOfPeople;
    private List<String> imgUrlLow;
    private List<String> imgUrlHigh;
    private List<String> helpedPeopleUserIds;
    private String message;
    private long postRatingCount;
    private List<DhHHPostRating> postRatings;
    private List<String> likedUserIds;
    private String priorityId;
    private String priorityName;

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

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public double getAvgHHRating() {
        return avgHHRating;
    }

    public void setAvgHHRating(double avgHHRating) {
        this.avgHHRating = avgHHRating;
    }

    public String getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(String priorityName) {
        this.priorityName = priorityName;
    }

    public String getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(String priorityId) {
        this.priorityId = priorityId;
    }

    public List<String> getLikedUserIds() {
        return likedUserIds;
    }

    public void setLikedUserIds(List<String> likedUserIds) {
        this.likedUserIds = likedUserIds;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public List<String> getImgUrlLow() {
        return imgUrlLow;
    }

    public void setImgUrlLow(List<String> imgUrlLow) {
        this.imgUrlLow = imgUrlLow;
    }

    public List<String> getImgUrlHigh() {
        return imgUrlHigh;
    }

    public void setImgUrlHigh(List<String> imgUrlHigh) {
        this.imgUrlHigh = imgUrlHigh;
    }

    public List<String> getHelpedPeopleUserIds() {
        return helpedPeopleUserIds;
    }

    public void setHelpedPeopleUserIds(List<String> helpedPeopleUserIds) {
        this.helpedPeopleUserIds = helpedPeopleUserIds;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getPostRatingCount() {
        return postRatingCount;
    }

    public void setPostRatingCount(long postRatingCount) {
        this.postRatingCount = postRatingCount;
    }

    public List<DhHHPostRating> getPostRatings() {
        return postRatings;
    }

    public void setPostRatings(List<DhHHPostRating> postRatings) {
        this.postRatings = postRatings;
    }

}
