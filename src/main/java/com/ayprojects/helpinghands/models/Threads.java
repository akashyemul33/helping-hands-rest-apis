package com.ayprojects.helpinghands.models;

public class Threads extends AllCommonUsedAttributes{
    private String replyToComment;
    private String name;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReplyToComment() {
        return replyToComment;
    }

    public void setReplyToComment(String replyToComment) {
        this.replyToComment = replyToComment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
