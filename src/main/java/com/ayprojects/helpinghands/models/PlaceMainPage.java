package com.ayprojects.helpinghands.models;

import java.util.List;

public class PlaceMainPage extends AllCommonUsedAttributes {
    private String placeMainPageId;
    private String heading;
    private String subHeading;
    private String subHeadingImg;
    private String bgParentColor;
    private PlaceMainGridType gridType;
    private List<PlaceMainPageItem> itemList;

    public PlaceMainPage(String heading, String subHeading, String subHeadingImg, String bgParentColor, PlaceMainGridType gridType, List<PlaceMainPageItem> itemList) {
        this.heading = heading;
        this.subHeading = subHeading;
        this.subHeadingImg = subHeadingImg;
        this.bgParentColor = bgParentColor;
        this.gridType = gridType;
        this.itemList = itemList;
    }

    public String getPlaceMainPageId() {
        return placeMainPageId;
    }

    public void setPlaceMainPageId(String placeMainPageId) {
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
