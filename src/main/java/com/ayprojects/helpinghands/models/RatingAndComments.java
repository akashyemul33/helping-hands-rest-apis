package com.ayprojects.helpinghands.models;

public class RatingAndComments extends CommonUsedAttributes{
    private long review_comment_id;
    private double rating;
    private String comment;
    private long added_by_user_id;
    private String added_by_user_name;
    private String content_type;
    private long content_id;

    public long getReview_comment_id() {
        return review_comment_id;
    }

    public void setReview_comment_id(long review_comment_id) {
        this.review_comment_id = review_comment_id;
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

    public long getAdded_by_user_id() {
        return added_by_user_id;
    }

    public void setAdded_by_user_id(long added_by_user_id) {
        this.added_by_user_id = added_by_user_id;
    }

    public String getAdded_by_user_name() {
        return added_by_user_name;
    }

    public void setAdded_by_user_name(String added_by_user_name) {
        this.added_by_user_name = added_by_user_name;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public long getContent_id() {
        return content_id;
    }

    public void setContent_id(long content_id) {
        this.content_id = content_id;
    }

    public RatingAndComments(double schemaVersion,String createdDateTime,String modifiedDateTime,String status,long review_comment_id, double rating, String comment, long added_by_user_id, String added_by_user_name, String content_type, long content_id) {
        this.schema_version=schemaVersion;
        this.created_date_time = createdDateTime;
        this.modified_date_time=modifiedDateTime;
        this.status=status;
        this.review_comment_id = review_comment_id;
        this.rating = rating;
        this.comment = comment;
        this.added_by_user_id = added_by_user_id;
        this.added_by_user_name = added_by_user_name;
        this.content_type = content_type;
        this.content_id = content_id;
    }
}
