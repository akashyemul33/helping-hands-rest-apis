package com.ayprojects.helpinghands.models;

public class PlaceSubCategories extends AllCommonUsedAttributes {
    private String placeSubCategoryId;
    private PlaceSubCategoryName placeSubCategoryName;
    private String placeSubCategoryImageId;
    private String placeSubCategoryImagePath;
    private String addedBy;
    private String langBasedSubCategoryName;

    public String getLangBasedSubCategoryName() {
        return langBasedSubCategoryName;
    }

    public void setLangBasedSubCategoryName(String langBasedSubCategoryName) {
        this.langBasedSubCategoryName = langBasedSubCategoryName;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public PlaceSubCategories(String schemaVersion, String createdDateTime, String modifiedDateTime, String status, String placeSubCategoryId,PlaceSubCategoryName placeSubCategoryName, String placeSubCategoryImageId, String placeSubCategoryImagePath, String addedBy) {
        this.schemaVersion =schemaVersion;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime =modifiedDateTime;
        this.status=status;
        this.placeSubCategoryId = placeSubCategoryId;
        this.placeSubCategoryName = placeSubCategoryName;
        this.placeSubCategoryImageId = placeSubCategoryImageId;
        this.placeSubCategoryImagePath = placeSubCategoryImagePath;
        this.addedBy = addedBy;
    }

    public String getPlaceSubCategoryId() {
        return placeSubCategoryId;
    }

    public void setPlaceSubCategoryId(String placeSubCategoryId) {
        this.placeSubCategoryId = placeSubCategoryId;
    }

    public PlaceSubCategoryName getPlaceSubCategoryName() {
        return placeSubCategoryName;
    }

    public void setPlaceSubCategoryName(PlaceSubCategoryName placeSubCategoryName) {
        this.placeSubCategoryName = placeSubCategoryName;
    }

    public String getPlaceSubCategoryImageId() {
        return placeSubCategoryImageId;
    }

    public void setPlaceSubCategoryImageId(String placeSubCategoryImageId) {
        this.placeSubCategoryImageId = placeSubCategoryImageId;
    }

    public String getPlaceSubCategoryImagePath() {
        return placeSubCategoryImagePath;
    }

    public void setPlaceSubCategoryImagePath(String placeSubCategoryImagePath) {
        this.placeSubCategoryImagePath = placeSubCategoryImagePath;
    }
}

