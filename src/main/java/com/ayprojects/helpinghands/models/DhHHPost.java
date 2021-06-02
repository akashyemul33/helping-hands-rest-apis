package com.ayprojects.helpinghands.models;

import org.springframework.data.annotation.Transient;

import java.lang.annotation.Inherited;
import java.util.List;

import jdk.nashorn.internal.ir.annotations.Ignore;

public class DhHHPost extends AllCommonUsedAttributes {
    @Transient
    @Ignore
    private long numberOfHHPosts;

    private boolean postCommentsOnOff;
    private List<String> dhCommentsIds;
    @Transient
    @Ignore
    private List<DhComments> dhComments;
    private float postGenuinePerc;
    @Transient
    @Ignore
    private long numberOfHHHelps;
    @Transient
    @Ignore
    private String profileImgLow;
    @Transient
    @Ignore
    private String profileImgHigh;
    private String userId;
    @Transient
    @Ignore
    private String userName;
    @Transient
    @Ignore
    private List<DhHhHelpedUsers> helpedUsers;
    private List<String> helpedUserIds;
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
    private List<String> likedUserIds;
    private String priorityId;
    private String priorityName;

    public boolean isPostCommentsOnOff() {
        return postCommentsOnOff;
    }

    public void setPostCommentsOnOff(boolean postCommentsOnOff) {
        this.postCommentsOnOff = postCommentsOnOff;
    }

    public List<String> getDhCommentsIds() {
        return dhCommentsIds;
    }

    public void setDhCommentsIds(List<String> dhCommentsIds) {
        this.dhCommentsIds = dhCommentsIds;
    }

    public List<DhComments> getDhComments() {
        return dhComments;
    }

    public void setDhComments(List<DhComments> dhComments) {
        this.dhComments = dhComments;
    }

    public float getPostGenuinePerc() {
        return postGenuinePerc;
    }

    public void setPostGenuinePerc(float postGenuinePerc) {
        this.postGenuinePerc = postGenuinePerc;
    }

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

    public List<String> getHelpedUserIds() {
        return helpedUserIds;
    }

    public void setHelpedUserIds(List<String> helpedUserIds) {
        this.helpedUserIds = helpedUserIds;
    }

    public List<DhHhHelpedUsers> getHelpedUsers() {
        return helpedUsers;
    }

    public void setHelpedUsers(List<DhHhHelpedUsers> helpedUsers) {
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

}
