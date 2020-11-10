package com.ayprojects.helpinghands.models;

public class DhViews extends AllCommonUsedAttributes {
    private String viewId;
    private String addedBy;
    private String contentType;
    private long contentId;

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
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

    public long getContentId() {
        return contentId;
    }

    public void setContentId(long contentId) {
        this.contentId = contentId;
    }

    public DhViews(String viewId, String addedBy, String contentType, long contentId) {
        this.schemaVersion =schemaVersion;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime =modifiedDateTime;
        this.status=status;
        this.viewId = viewId;
        this.addedBy = addedBy;
        this.contentType = contentType;
        this.contentId = contentId;
    }
}
