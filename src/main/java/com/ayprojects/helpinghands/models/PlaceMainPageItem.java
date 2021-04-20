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
    private String category_mr;
    private String category_en;
    private String category_hi;
    private String category_gu;
    private String category_te;
    private String category_kn;
    private boolean homeDeliveryAvailable;
    private String placeDesc;
    private String address;
    private double lat;
    private double lng;
    private String addedTime;
    private String since;

    public String getCategory_mr() {
        return category_mr;
    }

    public void setCategory_mr(String category_mr) {
        this.category_mr = category_mr;
    }

    public String getCategory_en() {
        return category_en;
    }

    public void setCategory_en(String category_en) {
        this.category_en = category_en;
    }

    public String getCategory_hi() {
        return category_hi;
    }

    public void setCategory_hi(String category_hi) {
        this.category_hi = category_hi;
    }

    public String getCategory_gu() {
        return category_gu;
    }

    public void setCategory_gu(String category_gu) {
        this.category_gu = category_gu;
    }

    public String getCategory_te() {
        return category_te;
    }

    public void setCategory_te(String category_te) {
        this.category_te = category_te;
    }

    public String getCategory_kn() {
        return category_kn;
    }

    public void setCategory_kn(String category_kn) {
        this.category_kn = category_kn;
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
