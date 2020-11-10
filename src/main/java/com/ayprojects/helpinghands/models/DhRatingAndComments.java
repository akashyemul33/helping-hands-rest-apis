package com.ayprojects.helpinghands.models;

public class DhRatingAndComments extends AllCommonUsedAttributes {
    private String reviewCommentId;
    private double rating;
    private String comment;
    private String addedBy;
    private String contentType;
    private String contentId;



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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
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

    public DhRatingAndComments(String schemaVersion, String createdDateTime, String modifiedDateTime, String status, String reviewCommentId, double rating, String comment, String addedBy, String contentType, String contentId) {
        this.schemaVersion =schemaVersion;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime =modifiedDateTime;
        this.status=status;
        this.reviewCommentId = reviewCommentId;
        this.rating = rating;
        this.comment = comment;
        this.addedBy = addedBy;
        this.contentType = contentType;
        this.contentId = contentId;
    }
}
