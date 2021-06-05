package com.ayprojects.helpinghands.models;

import org.springframework.data.annotation.Transient;

import java.util.List;
import java.util.Objects;

import jdk.nashorn.internal.ir.annotations.Ignore;

public class DhThought extends AllCommonUsedAttributes{
    private String imgUrlLow;
    private String imgUrlHigh;
    private String thoughtId;
    private long itemId;
    private String userId;
    @Transient
    @Ignore
    private String userName;
    @Transient
    @Ignore
    private String profileUrlLow;
    @Transient
    @Ignore
    private boolean alreadyLiked;

    private List<String> likedUserIds;
    @Transient
    @Ignore
    private String profileUrlHigh;
    @Transient
    @Ignore
    private String scheduledNoteWithTime;
    @Transient
    @Ignore
    private String note;
    @Transient
    @Ignore
    private boolean dontShow;

    public boolean isDontShow() {
        return dontShow;
    }

    public void setDontShow(boolean dontShow) {
        this.dontShow = dontShow;
    }

    public DhThought() {
    }

    public DhThought(String thoughtId,String scheduledNoteWithTime, String note,boolean dontShow) {
        this.thoughtId = thoughtId;
        this.scheduledNoteWithTime = scheduledNoteWithTime;
        this.note = note;
        this.dontShow = dontShow;
    }

    public List<String> getLikedUserIds() {
        return likedUserIds;
    }

    public void setLikedUserIds(List<String> likedUserIds) {
        this.likedUserIds = likedUserIds;
    }

    public boolean isAlreadyLiked() {
        return alreadyLiked;
    }

    public void setAlreadyLiked(boolean alreadyLiked) {
        this.alreadyLiked = alreadyLiked;
    }

    public String getProfileUrlLow() {
        return profileUrlLow;
    }

    public void setProfileUrlLow(String profileUrlLow) {
        this.profileUrlLow = profileUrlLow;
    }

    public String getProfileUrlHigh() {
        return profileUrlHigh;
    }

    public void setProfileUrlHigh(String profileUrlHigh) {
        this.profileUrlHigh = profileUrlHigh;
    }

    public String getScheduledNoteWithTime() {
        return scheduledNoteWithTime;
    }

    public void setScheduledNoteWithTime(String scheduledNoteWithTime) {
        this.scheduledNoteWithTime = scheduledNoteWithTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImgUrlLow() {
        return imgUrlLow;
    }

    public void setImgUrlLow(String imgUrlLow) {
        this.imgUrlLow = imgUrlLow;
    }

    public String getImgUrlHigh() {
        return imgUrlHigh;
    }

    public void setImgUrlHigh(String imgUrlHigh) {
        this.imgUrlHigh = imgUrlHigh;
    }

    public String getThoughtId() {
        return thoughtId;
    }

    public void setThoughtId(String thoughtId) {
        this.thoughtId = thoughtId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
}
