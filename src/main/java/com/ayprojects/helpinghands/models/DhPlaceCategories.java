package com.ayprojects.helpinghands.models;

import java.util.List;

public class DhPlaceCategories extends AllCommonUsedAttributes {
    private String placeCategoryId;
    private PlaceCategoryName placeCategoryName;
    private String placeMainCategoryImageId;
    private String placeMainCategoryImagePath;
    private String addedBy;

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    private List<PlaceSubCategories> placeSubCategories;

    public String getPlaceCategoryId() {
        return placeCategoryId;
    }

    public void setPlaceCategoryId(String placeCategoryId) {
        this.placeCategoryId = placeCategoryId;
    }

    public PlaceCategoryName getPlaceCategoryName() {
        return placeCategoryName;
    }

    public void setPlaceCategoryName(PlaceCategoryName placeCategoryName) {
        this.placeCategoryName = placeCategoryName;
    }

    public String getPlaceMainCategoryImageId() {
        return placeMainCategoryImageId;
    }

    public void setPlaceMainCategoryImageId(String placeMainCategoryImageId) {
        this.placeMainCategoryImageId = placeMainCategoryImageId;
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

    public DhPlaceCategories(String schemaVersion, String createdDateTime, String modifiedDateTime, String status,String placeCategoryId, PlaceCategoryName placeCategoryName, String placeMainCategoryImageId, String placeMainCategoryImagePath, List<PlaceSubCategories> placeSubCategories,String addedBy) {
        this.schemaVersion =schemaVersion;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime =modifiedDateTime;
        this.status=status;
        this.placeCategoryId = placeCategoryId;
        this.placeCategoryName = placeCategoryName;
        this.placeMainCategoryImageId = placeMainCategoryImageId;
        this.placeMainCategoryImagePath = placeMainCategoryImagePath;
        this.placeSubCategories = placeSubCategories;
        this.addedBy = addedBy;
    }

}
