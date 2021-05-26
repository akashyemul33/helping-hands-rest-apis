package com.ayprojects.helpinghands.models;

public class DhHHPostRating extends AllCommonUsedAttributes {
    private String postRatingId;
    private boolean genuine;
    private String comment;
    private String userId;
    private String profileImageUrl;
    private String userName;

    public String getPostRatingId() {
        return postRatingId;
    }

    public void setPostRatingId(String postRatingId) {
        this.postRatingId = postRatingId;
    }

    public boolean isGenuine() {
        return genuine;
    }

    public void setGenuine(boolean genuine) {
        this.genuine = genuine;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
