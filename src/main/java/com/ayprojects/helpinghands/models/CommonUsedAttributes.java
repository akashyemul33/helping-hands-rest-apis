package com.ayprojects.helpinghands.models;

public class CommonUsedAttributes {
    private String created_date_time;
    private String modified_date_time;
    private double schema_version;
    private String status;

    public String getCreated_date_time() {
        return created_date_time;
    }

    public void setCreated_date_time(String created_date_time) {
        this.created_date_time = created_date_time;
    }

    public String getModified_date_time() {
        return modified_date_time;
    }

    public void setModified_date_time(String modified_date_time) {
        this.modified_date_time = modified_date_time;
    }

    public double getSchema_version() {
        return schema_version;
    }

    public void setSchema_version(double schema_version) {
        this.schema_version = schema_version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
