package com.ayprojects.helpinghands.models;

public class DhImages extends AllCommonUsedAttributes {
    private long image_id;
    private String image_path;
    private String image_type;

    public DhImages() {

    }

    public long getImage_id() {
        return image_id;
    }

    public void setImage_id(long image_id) {
        this.image_id = image_id;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getImage_type() {
        return image_type;
    }

    public void setImage_type(String image_type) {
        this.image_type = image_type;
    }

    public DhImages(String schemaVersion, String createdDateTime, String modifiedDateTime, String status, long image_id, String image_path, String image_type) {
        this.image_id = image_id;
        this.image_path = image_path;
        this.image_type = image_type;
        this.schemaVersion =schemaVersion;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime =modifiedDateTime;
        this.status=status;
    }
}
