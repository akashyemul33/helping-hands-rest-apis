package com.ayprojects.helpinghands.models;

import org.springframework.data.annotation.Transient;

import java.util.List;

import jdk.nashorn.internal.ir.annotations.Ignore;

public class Thoughts extends AllCommonUsedAttributes {
    private String thoughtStr;
    private String thoughtImgPathLow;
    private String thoughtImgPathHigh;
    private String addedBy;
    private String userName;
    private String userImg;
    @Transient
    @Ignore
    private boolean canAddThoughts;

    public boolean isCanAddThoughts() {
        return canAddThoughts;
    }

    public void setCanAddThoughts(boolean canAddThoughts) {
        this.canAddThoughts = canAddThoughts;
    }

    private long numberOfLikes;
    private List<String> likedUserIds;
    private List<String> liveDateOn;
    private boolean postWon;
    private boolean prizeApplicable;
    private boolean fromSystem;
    private int numberOfAttempts;

    public String getThoughtStr() {
        return thoughtStr;
    }

    public void setThoughtStr(String thoughtStr) {
        this.thoughtStr = thoughtStr;
    }

    public String getThoughtImgPathLow() {
        return thoughtImgPathLow;
    }

    public void setThoughtImgPathLow(String thoughtImgPathLow) {
        this.thoughtImgPathLow = thoughtImgPathLow;
    }

    public String getThoughtImgPathHigh() {
        return thoughtImgPathHigh;
    }

    public void setThoughtImgPathHigh(String thoughtImgPathHigh) {
        this.thoughtImgPathHigh = thoughtImgPathHigh;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public long getNumberOfLikes() {
        return numberOfLikes;
    }

    public void setNumberOfLikes(long numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    public List<String> getLikedUserIds() {
        return likedUserIds;
    }

    public void setLikedUserIds(List<String> likedUserIds) {
        this.likedUserIds = likedUserIds;
    }

    public List<String> getLiveDateOn() {
        return liveDateOn;
    }

    public void setLiveDateOn(List<String> liveDateOn) {
        this.liveDateOn = liveDateOn;
    }

    public boolean isPostWon() {
        return postWon;
    }

    public void setPostWon(boolean postWon) {
        this.postWon = postWon;
    }

    public boolean isPrizeApplicable() {
        return prizeApplicable;
    }

    public void setPrizeApplicable(boolean prizeApplicable) {
        this.prizeApplicable = prizeApplicable;
    }

    public boolean isFromSystem() {
        return fromSystem;
    }

    public void setFromSystem(boolean fromSystem) {
        this.fromSystem = fromSystem;
    }

    public int getNumberOfAttempts() {
        return numberOfAttempts;
    }

    public void setNumberOfAttempts(int numberOfAttempts) {
        this.numberOfAttempts = numberOfAttempts;
    }
}
