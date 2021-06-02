package com.ayprojects.helpinghands.models;

import com.ayprojects.helpinghands.api.enums.ContentType;

import java.util.List;

public class DhComments extends AllCommonUsedAttributes {
    private String commentId;
    private String comment;
    private String addedBy;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private ContentType contentType;
    private String contentId;
    private List<Threads> threadsList;

    public List<Threads> getThreadsList() {
        return threadsList;
    }

    public void setThreadsList(List<Threads> threadsList) {
        this.threadsList = threadsList;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
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

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

}
