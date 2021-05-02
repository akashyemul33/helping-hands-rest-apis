package com.ayprojects.helpinghands.models;

import java.util.List;

public class PlaceMainPageItem {
    private List<String> imgUrlsLow;
    private List<String> imgUrlsHigh;
    private String placeName;
    private String placeId;
    private double avgRating;
    private long numberOfRatings;
    private String distance;
    private String openCloseMsg;
    private boolean homeDeliveryAvailable;
    private String placeDesc;
    private String address;
    private double lat;
    private double lng;
    private String addedTime;
    private String since;
    private String category;

    public PlaceMainPageItem() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public PlaceMainPageItem(List<String> imgUrlsLow, List<String> imgUrlsHigh, String placeName, String placeId, String category, String addedTime, String since) {
        this.imgUrlsLow = imgUrlsLow;
        this.imgUrlsHigh = imgUrlsHigh;
        this.placeName = placeName;
        this.placeId = placeId;
        this.category = category;
        this.addedTime = addedTime;
        this.since = since;
    }

    public List<String> getImgUrlsHigh() {
        return imgUrlsHigh;
    }

    public void setImgUrlsHigh(List<String> imgUrlsHigh) {
        this.imgUrlsHigh = imgUrlsHigh;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(String addedTime) {
        this.addedTime = addedTime;
    }

    public String getSince() {
        return since;
    }

    public void setSince(String since) {
        this.since = since;
    }

    public List<String> getImgUrlsLow() {
        return imgUrlsLow;
    }

    public void setImgUrlsLow(List<String> imgUrlsLow) {
        this.imgUrlsLow = imgUrlsLow;
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
