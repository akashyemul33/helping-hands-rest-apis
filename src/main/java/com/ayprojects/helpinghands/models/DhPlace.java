package com.ayprojects.helpinghands.models;

import java.util.List;

public class DhPlace extends AllCommonUsedAttributes {
    private String placeId;
    private String placeType;
    private Address placeAddress;
    private String placeMainCategoryId;

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    private String addedBy;
    private String placeSubCategoryId;
    private String placeCategoryName;
    private String placeName;
    private String placeDesc;
    private Contact placeContact;
    private boolean doorService;
    private List<EmbededImage> placeImages;
    private PlaceAvailabilityDays placeAvailablityDays;
    private String openingTime;
    private String closingTime;
    private String lunchHourStartTime;
    private String lunchHourEndTime;
    private List<ProductsWithPrices> productsWithPrice;
    private long numberOfRatings;
    private long numberOfViews;
    private double avgRating;
    private List<String> ratingIds;
    private List<String> viewIds;
    private List<DhRating_comments> topRatings;
    private List<DhViews> topViews;

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public Address getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(Address placeAddress) {
        this.placeAddress = placeAddress;
    }

    public String getPlaceMainCategoryId() {
        return placeMainCategoryId;
    }

    public void setPlaceMainCategoryId(String placeMainCategoryId) {
        this.placeMainCategoryId = placeMainCategoryId;
    }

    public String getPlaceSubCategoryId() {
        return placeSubCategoryId;
    }

    public void setPlaceSubCategoryId(String placeSubCategoryId) {
        this.placeSubCategoryId = placeSubCategoryId;
    }

    public String getPlaceCategoryName() {
        return placeCategoryName;
    }

    public void setPlaceCategoryName(String placeCategoryName) {
        this.placeCategoryName = placeCategoryName;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceDesc() {
        return placeDesc;
    }

    public void setPlaceDesc(String placeDesc) {
        this.placeDesc = placeDesc;
    }

    public Contact getPlaceContact() {
        return placeContact;
    }

    public void setPlaceContact(Contact placeContact) {
        this.placeContact = placeContact;
    }

    public boolean isDoorService() {
        return doorService;
    }

    public void setDoorService(boolean doorService) {
        this.doorService = doorService;
    }

    public List<EmbededImage> getPlaceImages() {
        return placeImages;
    }

    public void setPlaceImages(List<EmbededImage> placeImages) {
        this.placeImages = placeImages;
    }

    public PlaceAvailabilityDays getPlaceAvailablityDays() {
        return placeAvailablityDays;
    }

    public void setPlaceAvailablityDays(PlaceAvailabilityDays placeAvailablityDays) {
        this.placeAvailablityDays = placeAvailablityDays;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public String getLunchHourStartTime() {
        return lunchHourStartTime;
    }

    public void setLunchHourStartTime(String lunchHourStartTime) {
        this.lunchHourStartTime = lunchHourStartTime;
    }

    public String getLunchHourEndTime() {
        return lunchHourEndTime;
    }

    public void setLunchHourEndTime(String lunchHourEndTime) {
        this.lunchHourEndTime = lunchHourEndTime;
    }

    public List<ProductsWithPrices> getProductsWithPrice() {
        return productsWithPrice;
    }

    public void setProductsWithPrice(List<ProductsWithPrices> productsWithPrice) {
        this.productsWithPrice = productsWithPrice;
    }

    public long getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(long numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public long getNumberOfViews() {
        return numberOfViews;
    }

    public void setNumberOfViews(long numberOfViews) {
        this.numberOfViews = numberOfViews;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public List<String> getRatingIds() {
        return ratingIds;
    }

    public void setRatingIds(List<String> ratingIds) {
        this.ratingIds = ratingIds;
    }

    public List<String> getViewIds() {
        return viewIds;
    }

    public void setViewIds(List<String> viewIds) {
        this.viewIds = viewIds;
    }

    public List<DhRating_comments> getTopRatings() {
        return topRatings;
    }

    public void setTopRatings(List<DhRating_comments> topRatings) {
        this.topRatings = topRatings;
    }

    public List<DhViews> getTopViews() {
        return topViews;
    }

    public void setTopViews(List<DhViews> topViews) {
        this.topViews = topViews;
    }

    public DhPlace(String schemaVersion, String createdDateTime, String modifiedDateTime, String status, String addedBy, String placeId, String placeType, Address placeAddress, String placeMainCategoryId, String placeSubCategoryId, String placeCategoryName, String placeName, String placeDesc, Contact placeContact, boolean doorService, List<EmbededImage> placeImages, PlaceAvailabilityDays placeAvailablityDays, String openingTime, String closingTime, String lunchHourStartTime, String lunchHourEndTime, List<ProductsWithPrices> productsWithPrice, long numberOfRatings, long numberOfViews, double avgRating, List<String> ratingIds, List<String> viewIds, List<DhRating_comments> topRatings, List<DhViews> topViews) {
        this.schemaVersion =schemaVersion;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime =modifiedDateTime;
        this.status=status;
        this.addedBy = addedBy;
        this.placeId = placeId;
        this.placeType = placeType;
        this.placeAddress = placeAddress;
        this.placeMainCategoryId = placeMainCategoryId;
        this.placeSubCategoryId = placeSubCategoryId;
        this.placeCategoryName = placeCategoryName;
        this.placeName = placeName;
        this.placeDesc = placeDesc;
        this.placeContact = placeContact;
        this.doorService = doorService;
        this.placeImages = placeImages;
        this.placeAvailablityDays = placeAvailablityDays;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.lunchHourStartTime = lunchHourStartTime;
        this.lunchHourEndTime = lunchHourEndTime;
        this.productsWithPrice = productsWithPrice;
        this.numberOfRatings = numberOfRatings;
        this.numberOfViews = numberOfViews;
        this.avgRating = avgRating;
        this.ratingIds = ratingIds;
        this.viewIds = viewIds;
        this.topRatings = topRatings;
        this.topViews = topViews;
    }
}



