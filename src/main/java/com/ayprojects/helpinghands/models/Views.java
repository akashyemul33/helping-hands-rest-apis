package com.ayprojects.helpinghands.models;

public class Views extends CommonUsedAttributes {
    private long view_id;
    private long user_id;
    private String user_name;
    private String content_type;
    private long content_id;

    public long getView_id() {
        return view_id;
    }

    public void setView_id(long view_id) {
        this.view_id = view_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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

    public Views(double schemaVersion,String createdDateTime,String modifiedDateTime,String status,long view_id, long user_id, String user_name, String content_type, long content_id) {
        this.view_id = view_id;
        this.user_id = user_id;
        this.user_name = user_name;
        this.content_type = content_type;
        this.content_id = content_id;
        this.schema_version=schemaVersion;
        this.created_date_time = createdDateTime;
        this.modified_date_time=modifiedDateTime;
        this.status=status;
    }
}
