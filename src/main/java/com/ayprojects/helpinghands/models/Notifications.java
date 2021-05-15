package com.ayprojects.helpinghands.models;

public class Notifications extends AllCommonUsedAttributes {
    private String notificationId;
    private String title;
    private String body;
    private String redirectionContent;
    private String redirectionUrl;

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRedirectionContent() {
        return redirectionContent;
    }

    public void setRedirectionContent(String redirectionContent) {
        this.redirectionContent = redirectionContent;
    }

    public String getRedirectionUrl() {
        return redirectionUrl;
    }

    public void setRedirectionUrl(String redirectionUrl) {
        this.redirectionUrl = redirectionUrl;
    }
}
