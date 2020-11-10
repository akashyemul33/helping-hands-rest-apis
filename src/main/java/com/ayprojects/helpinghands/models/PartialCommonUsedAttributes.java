package com.ayprojects.helpinghands.models;

public class PartialCommonUsedAttributes {
    public String createdDateTime;
    public String modifiedDateTime;


    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setModifiedDateTime(String modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public PartialCommonUsedAttributes(String createdDateTime, String modifiedDateTime) {
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
    }
}
