package com.ayprojects.helpinghands.models;

import java.util.List;

public class PlaceSubCategories extends AllCommonUsedAttributes {
    private String placeSubCategoryId;
    private String defaultName;
    private List<LangValueObj> translations;
    private String placeSubCategoryImageId;
    private String placeSubCategoryImagePath;
    private String addedBy;

    public PlaceSubCategories() {

    }
    public PlaceSubCategories(String schemaVersion, String createdDateTime, String modifiedDateTime, String status, String placeSubCategoryId, String defaultName, List<LangValueObj> translations, String placeSubCategoryImageId, String placeSubCategoryImagePath, String addedBy) {
        this.schemaVersion = schemaVersion;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
        this.status = status;
        this.placeSubCategoryId = placeSubCategoryId;
        this.defaultName = defaultName;
        this.translations = translations;
        this.placeSubCategoryImageId = placeSubCategoryImageId;
        this.placeSubCategoryImagePath = placeSubCategoryImagePath;
        this.addedBy = addedBy;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public List<LangValueObj> getTranslations() {
        return translations;
    }

    public void setTranslations(List<LangValueObj> translations) {
        this.translations = translations;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getPlaceSubCategoryId() {
        return placeSubCategoryId;
    }

    public void setPlaceSubCategoryId(String placeSubCategoryId) {
        this.placeSubCategoryId = placeSubCategoryId;
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

