package com.ayprojects.helpinghands.models;

public class Images extends CommonUsedAttributes {
    private long image_id;
    private String image_path;
    private String image_type;

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

    public Images(double schemaVersion,String createdDateTime,String modifiedDateTime,String status,long image_id, String image_path, String image_type) {
        this.image_id = image_id;
        this.image_path = image_path;
        this.image_type = image_type;
        this.schema_version=schemaVersion;
        this.created_date_time = createdDateTime;
        this.modified_date_time=modifiedDateTime;
        this.status=status;
    }
}
