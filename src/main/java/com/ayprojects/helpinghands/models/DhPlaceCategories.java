package com.ayprojects.helpinghands.models;

import java.util.List;

public class DhPlaceCategories extends AllCommonUsedAttributes {
    private String placeCategoryId;
    private String typeOfPlaceCategory;//Business or public
    private String placeMainCategoryImagePath;
    private String addedBy;
    private List<PlaceSubCategories> placeSubCategories;
    public String defaultName;
    public List<LangValueObj> translations;

    public DhPlaceCategories(String schemaVersion, String createdDateTime, String modifiedDateTime, String status, String placeCategoryId, String typeOfPlaceCategory,String placeMainCategoryImagePath, String addedBy, String defaultName, List<LangValueObj> translations, List<PlaceSubCategories> placeSubCategories) {
        this.schemaVersion = schemaVersion;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
        this.status = status;
        this.placeCategoryId = placeCategoryId;
        this.typeOfPlaceCategory = typeOfPlaceCategory;
        this.placeMainCategoryImagePath = placeMainCategoryImagePath;
        this.addedBy = addedBy;
        this.defaultName = defaultName;
        this.translations = translations;
        this.placeSubCategories = placeSubCategories;
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

    public void setTranslations(List<LangValueObj> t) {
        this.translations = t;
    }

    public String getTypeOfPlaceCategory() {
        return typeOfPlaceCategory;
    }

    public void setTypeOfPlaceCategory(String typeOfPlaceCategory) {
        this.typeOfPlaceCategory = typeOfPlaceCategory;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getPlaceCategoryId() {
        return placeCategoryId;
    }

    public void setPlaceCategoryId(String placeCategoryId) {
        this.placeCategoryId = placeCategoryId;
    }

    public String getPlaceMainCategoryImagePath() {
        return placeMainCategoryImagePath;
    }

    public void setPlaceMainCategoryImagePath(String placeMainCategoryImagePath) {
        this.placeMainCategoryImagePath = placeMainCategoryImagePath;
    }

    public List<PlaceSubCategories> getPlaceSubCategories() {
        return placeSubCategories;
    }

    public void setPlaceSubCategories(List<PlaceSubCategories> placeSubCategories) {
        this.placeSubCategories = placeSubCategories;
    }


}

