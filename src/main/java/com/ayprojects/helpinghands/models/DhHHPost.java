package com.ayprojects.helpinghands.models;

import org.springframework.data.annotation.Transient;

import java.util.List;

import jdk.nashorn.internal.ir.annotations.Ignore;

public class DhHHPost extends AllCommonUsedAttributes {
    @Transient
    @Ignore
    private long numberOfHHPosts;

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

    @Transient
    @Ignore
    private long numberOfHHHelps;
    @Transient
    @Ignore
    private String profileImgLow;
    @Transient
    @Ignore
    private String profileImgHigh;

    public String getProfileImgLow() {
        return profileImgLow;
    }

    public void setProfileImgLow(String profileImgLow) {
        this.profileImgLow = profileImgLow;
    }

    public String getProfileImgHigh() {
        return profileImgHigh;
    }

    public void setProfileImgHigh(String profileImgHigh) {
        this.profileImgHigh = profileImgHigh;
    }

    private String userId;
    @Transient
    @Ignore
    private String userName;
    @Transient
    @Ignore
    private List<DhHelpedUsers> helpedUsers;

    private List<String> helpedUserIds;

    public List<String> getHelpedUserIds() {
        return helpedUserIds;
    }

    public void setHelpedUserIds(List<String> helpedUserIds) {
        this.helpedUserIds = helpedUserIds;
    }

    private String postId;
    private Address address;
    private String categoryId;
    private String categoryName;
    @Transient
    @Ignore
    private float hhGenuinePercentage;
    private List<String> genuineRatingUserIds;
    private List<String> notGenuineRatingUserIds;
    @Transient
    @Ignore
    private String distance;
    private int numberOfPeople;
    private List<String> imgUrlLow;
    private List<String> imgUrlHigh;
    private String message;
    private long postRatingCount;
    private List<String> likedUserIds;
    private String priorityId;
    private String priorityName;


    public List<DhHelpedUsers> getHelpedUsers() {
        return helpedUsers;
    }

    public void setHelpedUsers(List<DhHelpedUsers> helpedUsers) {
        this.helpedUsers = helpedUsers;
    }

    public float getHhGenuinePercentage() {
        return hhGenuinePercentage;
    }

    public void setHhGenuinePercentage(float hhGenuinePercentage) {
        this.hhGenuinePercentage = hhGenuinePercentage;
    }

    public List<String> getGenuineRatingUserIds() {
        return genuineRatingUserIds;
    }

    public void setGenuineRatingUserIds(List<String> genuineRatingUserIds) {
        this.genuineRatingUserIds = genuineRatingUserIds;
    }

    public List<String> getNotGenuineRatingUserIds() {
        return notGenuineRatingUserIds;
    }

    public void setNotGenuineRatingUserIds(List<String> notGenuineRatingUserIds) {
        this.notGenuineRatingUserIds = notGenuineRatingUserIds;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

}
