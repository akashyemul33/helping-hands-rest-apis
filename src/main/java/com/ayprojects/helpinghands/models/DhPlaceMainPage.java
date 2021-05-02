package com.ayprojects.helpinghands.models;

import java.util.List;

public class DhPlaceMainPage extends AllCommonUsedAttributes {
    public String createdDateTime;
    public String modifiedDateTime;
    public String schemaVersion;
    public String status;
    private int placeMainPageId;
    private String placeMainPageUniqueId;
    private String heading;
    private String subHeading;
    private String subHeadingImg;
    private String bgParentColor;
    private PlaceMainGridType gridType;
    private List<PlaceMainPageItem> itemList;

    public DhPlaceMainPage(int placeMainPageId, String placeMainPageUniqueId, String heading, PlaceMainGridType gridType, List<PlaceMainPageItem> itemList) {
        this.placeMainPageId = placeMainPageId;
        this.placeMainPageUniqueId = placeMainPageUniqueId;
        this.heading = heading;
        this.gridType = gridType;
        this.itemList = itemList;
    }
    public DhPlaceMainPage(String heading, String subHeading, String subHeadingImg, String bgParentColor, PlaceMainGridType gridType, List<PlaceMainPageItem> itemList) {
        this.heading = heading;
        this.subHeading = subHeading;
        this.subHeadingImg = subHeadingImg;
        this.bgParentColor = bgParentColor;
        this.gridType = gridType;
        this.itemList = itemList;
    }

    @Override
    public String getCreatedDateTime() {
        return createdDateTime;
    }

    @Override
    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    @Override
    public String getModifiedDateTime() {
        return modifiedDateTime;
    }

    @Override
    public void setModifiedDateTime(String modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    @Override
    public String getSchemaVersion() {
        return schemaVersion;
    }

    @Override
    public void setSchemaVersion(String schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlaceMainPageUniqueId() {
        return placeMainPageUniqueId;
    }

    public void setPlaceMainPageUniqueId(String placeMainPageUniqueId) {
        this.placeMainPageUniqueId = placeMainPageUniqueId;
    }

    public int getPlaceMainPageId() {
        return placeMainPageId;
    }

    public void setPlaceMainPageId(int placeMainPageId) {
        this.placeMainPageId = placeMainPageId;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getSubHeading() {
        return subHeading;
    }

    public void setSubHeading(String subHeading) {
        this.subHeading = subHeading;
    }

    public String getSubHeadingImg() {
        return subHeadingImg;
    }

    public void setSubHeadingImg(String subHeadingImg) {
        this.subHeadingImg = subHeadingImg;
    }

    public String getBgParentColor() {
        return bgParentColor;
    }

    public void setBgParentColor(String bgParentColor) {
        this.bgParentColor = bgParentColor;
    }

    public PlaceMainGridType getGridType() {
        return gridType;
    }

    public void setGridType(PlaceMainGridType gridType) {
        this.gridType = gridType;
    }

    public List<PlaceMainPageItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<PlaceMainPageItem> itemList) {
        this.itemList = itemList;
    }
}
