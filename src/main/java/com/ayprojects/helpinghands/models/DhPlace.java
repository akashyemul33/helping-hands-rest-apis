package com.ayprojects.helpinghands.models;

import java.util.List;

public class DhPlace extends AllCommonUsedAttributes {
    private String placeId;
    private String placeType;
    private Address placeAddress;
    private String placeMainCategoryId;
    private String addedBy;
    private String placeSubCategoryId;
    private String placeCategoryName;
    private String placeName;
    private String placeDesc;
    private Contact placeContact;
    private boolean doorService;
    private List<String> placeImages;
    private PlaceAvailabilityDays placeAvailablityDays;
    private String openingTime;
    private String closingTime;
    private String lunchHourStartTime;
    private String lunchHourEndTime;
    private List<ProductsWithPrices> productsWithPrice;
    private long numberOfRatings;
    private long numberOfViews;
    private long numberOfPosts;
    private double avgRating;
    private List<String> ratingIds;
    private List<String> postIds;
    private List<String> viewIds;
    private List<DhRatingAndComments> topRatings;
    private List<DhViews> topViews;
    private List<DhPosts> topPosts;
    private OwnerDetails ownerDetails;
    private List<String> subscribedUsers;//subscribed user id's

    public DhPlace() {

    }

    public List<String> getSubscribedUsers() {
        return subscribedUsers;
    }

    public void setSubscribedUsers(List<String> subscribedUsers) {
        this.subscribedUsers = subscribedUsers;
    }

    public OwnerDetails getOwnerDetails() {
        return ownerDetails;
    }

    public void setOwnerDetails(OwnerDetails ownerDetails) {
        this.ownerDetails = ownerDetails;
    }

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

    public List<String> getPlaceImages() {
        return placeImages;
    }

    public void setPlaceImages(List<String> placeImages) {
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

    public long getNumberOfPosts() {
        return numberOfPosts;
    }

    public void setNumberOfPosts(long numberOfPosts) {
        this.numberOfPosts = numberOfPosts;
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

    public List<String> getPostIds() {
        return postIds;
    }

    public void setPostIds(List<String> postIds) {
        this.postIds = postIds;
    }

    public List<String> getViewIds() {
        return viewIds;
    }

    public void setViewIds(List<String> viewIds) {
        this.viewIds = viewIds;
    }

    public List<DhRatingAndComments> getTopRatings() {
        return topRatings;
    }

    public void setTopRatings(List<DhRatingAndComments> topRatings) {
        this.topRatings = topRatings;
    }

    public List<DhViews> getTopViews() {
        return topViews;
    }

    public void setTopViews(List<DhViews> topViews) {
        this.topViews = topViews;
    }

    public List<DhPosts> getTopPosts() {
        return topPosts;
    }

    public void setTopPosts(List<DhPosts> topPosts) {
        this.topPosts = topPosts;
    }

    public DhPlace(String schemaVersion, String createdDateTime, String modifiedDateTime, String status,String placeId, String placeType, Address placeAddress, String placeMainCategoryId, String addedBy, String placeSubCategoryId, String placeCategoryName, String placeName, String placeDesc, Contact placeContact, boolean doorService, List<String> placeImages, PlaceAvailabilityDays placeAvailablityDays, String openingTime, String closingTime, String lunchHourStartTime, String lunchHourEndTime, List<ProductsWithPrices> productsWithPrice, long numberOfRatings, long numberOfViews, long numberOfPosts, double avgRating, List<String> ratingIds, List<String> postIds, List<String> viewIds, List<DhRatingAndComments> topRatings, List<DhViews> topViews, List<DhPosts> topPosts, OwnerDetails ownerDetails, List<String> subscribedUsers) {
        this.schemaVersion =schemaVersion;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime =modifiedDateTime;
        this.status=status;
        this.placeId = placeId;
        this.placeType = placeType;
        this.placeAddress = placeAddress;
        this.placeMainCategoryId = placeMainCategoryId;
        this.addedBy = addedBy;
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
        this.numberOfPosts = numberOfPosts;
        this.avgRating = avgRating;
        this.ratingIds = ratingIds;
        this.postIds = postIds;
        this.viewIds = viewIds;
        this.topRatings = topRatings;
        this.topViews = topViews;
        this.topPosts = topPosts;
        this.ownerDetails = ownerDetails;
        this.subscribedUsers = subscribedUsers;
    }

}



