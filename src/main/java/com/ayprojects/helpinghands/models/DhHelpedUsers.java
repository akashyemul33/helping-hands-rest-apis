package com.ayprojects.helpinghands.models;

import org.springframework.data.annotation.Transient;

import java.util.List;

import jdk.nashorn.internal.ir.annotations.Ignore;

public class DhHelpedUsers {
    private String userId;
    private String userName;
    @Transient
    @Ignore
    private String profileImgLow;
    @Transient
    @Ignore
    private String profileImgHigh;
    @Transient
    @Ignore
    private long numberOfHHPosts;
    @Transient
    @Ignore
    private long numberOfHHHelps;

    private List<DhComments> comments;

    public List<DhComments> getComments() {
        return comments;
    }

    public void setComments(List<DhComments> comments) {
        this.comments = comments;
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
}
