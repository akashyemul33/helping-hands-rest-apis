package com.ayprojects.helpinghands.models;

import java.util.List;

public class PlaceMainPageItem {
    private List<String> imgList;
    private String placeName;
    private String placeId;
    private double avgRating;
    private long numberOfRatings;
    private String distance;
    private String openCloseMsg;
    private String category;
    private boolean homeDeliveryAvailable;
    private String placeDesc;
    private double lat;
    private double lng;

    public String getSince() {
        return since;
    }

    public void setSince(String since) {
        this.since = since;
    }

    private String since;

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public long getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(long numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getOpenCloseMsg() {
        return openCloseMsg;
    }

    public void setOpenCloseMsg(String openCloseMsg) {
        this.openCloseMsg = openCloseMsg;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isHomeDeliveryAvailable() {
        return homeDeliveryAvailable;
    }

    public void setHomeDeliveryAvailable(boolean homeDeliveryAvailable) {
        this.homeDeliveryAvailable = homeDeliveryAvailable;
    }

    public String getPlaceDesc() {
        return placeDesc;
    }

    public void setPlaceDesc(String placeDesc) {
        this.placeDesc = placeDesc;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
