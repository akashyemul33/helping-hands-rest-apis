package com.ayprojects.helpinghands.models;

import com.ayprojects.helpinghands.api.enums.ContentType;

import org.springframework.data.annotation.Transient;

import java.util.List;

import jdk.nashorn.internal.ir.annotations.Ignore;

public class DhRatingAndComments extends AllCommonUsedAttributes {
    private String reviewCommentId;
    private double rating;
    private String comment;
    private String addedBy;
    private ContentType contentType;
    private String contentId;
    private String profileImageUrl;
    private String userName;

    public boolean getNotificationRequired() {
        return notificationRequired;
    }

    public void setNotificationRequired(boolean notificationRequired) {
        this.notificationRequired = notificationRequired;
    }
    @Ignore
    @Transient
    private double previousRating;//useful for edit rating to update things in original content

    public double getPreviousRating() {
        return previousRating;
    }

    public void setPreviousRating(double previousRating) {
        this.previousRating = previousRating;
    }

    @Ignore
    @Transient
    private boolean notificationRequired;
    @Ignore
    @Transient
    private String contentUserId;
    @Ignore
    @Transient
    private String contentName;
    @Ignore
    @Transient
    private int totalRating;

    private List<Threads> threads;

    public List<Threads> getThreads() {
        return threads;
    }

    public void setThreads(List<Threads> threads) {
        this.threads = threads;
    }

    public float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }

    @Ignore
    @Transient
    private float avgRating;
    @Ignore
    @Transient
    private int numberOfFiveStars;
    @Ignore
    @Transient
    private int numberOfFourStars;
    @Ignore
    @Transient
    private int numberOfThreeStars;
    @Ignore
    @Transient
    private int numberOfTwoStars;
    @Ignore
    @Transient
    private int numberOfOneStars;
    public DhRatingAndComments(String schemaVersion, String createdDateTime, String modifiedDateTime, String status, String reviewCommentId, double rating, String comment, String addedBy, ContentType contentType, String contentId) {
        this.schemaVersion = schemaVersion;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
        this.status = status;
        this.reviewCommentId = reviewCommentId;
        this.rating = rating;
        this.comment = comment;
        this.addedBy = addedBy;
        this.contentType = contentType;
        this.contentId = contentId;
    }
    public DhRatingAndComments() {

    }

    public String getContentUserId() {
        return contentUserId;
    }

    public void setContentUserId(String contentUserId) {
        this.contentUserId = contentUserId;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public String getReviewCommentId() {
        return reviewCommentId;
    }

    public void setReviewCommentId(String reviewCommentId) {
        this.reviewCommentId = reviewCommentId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public int getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
    }

    public int getNumberOfFiveStars() {
        return numberOfFiveStars;
    }

    public void setNumberOfFiveStars(int numberOfFiveStars) {
        this.numberOfFiveStars = numberOfFiveStars;
    }

    public int getNumberOfFourStars() {
        return numberOfFourStars;
    }

    public void setNumberOfFourStars(int numberOfFourStars) {
        this.numberOfFourStars = numberOfFourStars;
    }

    public int getNumberOfThreeStars() {
        return numberOfThreeStars;
    }

    public void setNumberOfThreeStars(int numberOfThreeStars) {
        this.numberOfThreeStars = numberOfThreeStars;
    }

    public int getNumberOfTwoStars() {
        return numberOfTwoStars;
    }

    public void setNumberOfTwoStars(int numberOfTwoStars) {
        this.numberOfTwoStars = numberOfTwoStars;
    }

    public int getNumberOfOneStars() {
        return numberOfOneStars;
    }

    public void setNumberOfOneStars(int numberOfOneStars) {
        this.numberOfOneStars = numberOfOneStars;
    }
}
