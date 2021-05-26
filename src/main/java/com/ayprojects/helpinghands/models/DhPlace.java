package com.ayprojects.helpinghands.models;

import org.springframework.data.annotation.Transient;

import java.util.List;

import jdk.nashorn.internal.ir.annotations.Ignore;

public class DhPlace extends AllCommonUsedAttributes {
    private String placeId;
    private String placeType;
    private Address placeAddress;
    private String placeRegDate;
    private String offlineMsg;
    private boolean currentStatus;
    private String productPricesVisible;
    private List<ProductPricesVisibleUsers> productPricesVisibleUsers;
    private boolean addressGenerated;
    private String placeMainCategoryId;
    private String addedBy;
    private String placeSubCategoryId;
    private String placeCategoryName;
    private String placeSubCategoryName;
    private String placeName;
    private String placeDesc;
    private Contact placeContact;
    private boolean doorService;
    private List<String> imageUrlsLow;
    private List<String> imageUrlsHigh;
    private PlaceAvailabilityDetails placeAvailablityDetails;
    private List<ProductsWithPrices> productDetails;
    private long numberOfRatings;
    private long numberOfViews;
    private long numberOfPromotions;
    private long numberOfProducts;
    private double avgRating;
    private List<String> ratingIds;
    private List<String> promotionIds;
    private List<DhRatingAndComments> topRatings;
    private List<DhPromotions> topPromotions;
    private String ownerName;
    private List<String> subscribedUsers;//subscribed user id's
    @Transient
    private String distance;
    private String userName;
    @Transient
    private boolean placeOpen;
    private String openCloseMsg;

    public boolean getHasAccessToProductPrices() {
        return hasAccessToProductPrices;
    }

    public void setHasAccessToProductPrices(boolean hasAccessToProductPrices) {
        this.hasAccessToProductPrices = hasAccessToProductPrices;
    }

    @Transient
    @Ignore
    private boolean hasAccessToProductPrices;
    @Transient
    @Ignore
    private boolean alreadyRequestForProductPrices;
    @Transient
    @Ignore
    private boolean rejectedRequestForProductPrices;

    public boolean isRejectedRequestForProductPrices() {
        return rejectedRequestForProductPrices;
    }

    public void setRejectedRequestForProductPrices(boolean rejectedRequestForProductPrices) {
        this.rejectedRequestForProductPrices = rejectedRequestForProductPrices;
    }

    public boolean isAlreadyRequestForProductPrices() {
        return alreadyRequestForProductPrices;
    }

    public void setAlreadyRequestForProductPrices(boolean alreadyRequestForProductPrices) {
        this.alreadyRequestForProductPrices = alreadyRequestForProductPrices;
    }

    public DhPlace() {

    }

    public DhPlace(String schemaVersion, String createdDateTime, String modifiedDateTime, String status, String placeId, String placeType, Address placeAddress, String placeMainCategoryId, String addedBy, String placeSubCategoryId, String placeCategoryName, String placeName, String placeDesc, Contact placeContact, boolean doorService, List<String> imageUrlsLow, PlaceAvailabilityDetails placeAvailablityDetails, List<ProductsWithPrices> productDetails, long numberOfRatings, long numberOfViews, long numberOfPromotions, double avgRating, List<String> ratingIds, List<String> promotionIds, List<DhRatingAndComments> topRatings, List<DhPromotions> topPromotions, List<String> subscribedUsers, boolean addressGenerated, String placeSubCategoryName, String ownerName, long numberOfProducts) {
        this.schemaVersion = schemaVersion;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
        this.status = status;
        this.placeId = placeId;
        this.placeType = placeType;
        this.placeAddress = placeAddress;
        this.placeMainCategoryId = placeMainCategoryId;
        this.addedBy = addedBy;
        this.placeSubCategoryId = placeSubCategoryId;
        this.placeCategoryName = placeCategoryName;
        this.placeSubCategoryName = placeSubCategoryName;
        this.placeName = placeName;
        this.placeDesc = placeDesc;
        this.placeContact = placeContact;
        this.doorService = doorService;
        this.imageUrlsLow = imageUrlsLow;
        this.placeAvailablityDetails = placeAvailablityDetails;
        this.productDetails = productDetails;
        this.numberOfRatings = numberOfRatings;
        this.numberOfViews = numberOfViews;
        this.numberOfPromotions = numberOfPromotions;
        this.avgRating = avgRating;
        this.ratingIds = ratingIds;
        this.promotionIds = promotionIds;
        this.topRatings = topRatings;
        this.topPromotions = topPromotions;
        this.ownerName = ownerName;
        this.subscribedUsers = subscribedUsers;
        this.addressGenerated = addressGenerated;
        this.numberOfProducts = numberOfProducts;
    }

    public String getOfflineMsg() {
        return offlineMsg;
    }

    public void setOfflineMsg(String offlineMsg) {
        this.offlineMsg = offlineMsg;
    }

    public boolean getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(boolean currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getProductPricesVisible() {
        return productPricesVisible;
    }

    public void setProductPricesVisible(String productPricesVisible) {
        this.productPricesVisible = productPricesVisible;
    }

    public List<ProductPricesVisibleUsers> getProductPricesVisibleUsers() {
        return productPricesVisibleUsers;
    }

    public void setProductPricesVisibleUsers(List<ProductPricesVisibleUsers> productPricesVisibleUsers) {
        this.productPricesVisibleUsers = productPricesVisibleUsers;
    }

    public String getPlaceRegDate() {
        return placeRegDate;
    }

    public void setPlaceRegDate(String placeRegDate) {
        this.placeRegDate = placeRegDate;
    }

    public List<String> getImageUrlsHigh() {
        return imageUrlsHigh;
    }

    public void setImageUrlsHigh(List<String> imageUrlsHigh) {
        this.imageUrlsHigh = imageUrlsHigh;
    }

    public boolean isPlaceOpen() {
        return placeOpen;
    }

    public void setPlaceOpen(boolean placeOpen) {
        this.placeOpen = placeOpen;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOpenCloseMsg() {
        return openCloseMsg;
    }

    public void setOpenCloseMsg(String openCloseMsg) {
        this.openCloseMsg = openCloseMsg;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public long getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setNumberOfProducts(long numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPlaceSubCategoryName() {
        return placeSubCategoryName;
    }

    public void setPlaceSubCategoryName(String placeSubCategoryName) {
        this.placeSubCategoryName = placeSubCategoryName;
    }

    public boolean isAddressGenerated() {
        return addressGenerated;
    }

    public void setAddressGenerated(boolean isAddressGenerated) {
        this.addressGenerated = isAddressGenerated;
    }

    public List<String> getSubscribedUsers() {
        return subscribedUsers;
    }

    public void setSubscribedUsers(List<String> subscribedUsers) {
        this.subscribedUsers = subscribedUsers;
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

    public List<String> getImageUrlsLow() {
        return imageUrlsLow;
    }

    public void setImageUrlsLow(List<String> imageUrlsLow) {
        this.imageUrlsLow = imageUrlsLow;
    }

    public PlaceAvailabilityDetails getPlaceAvailablityDetails() {
        return placeAvailablityDetails;
    }

    public void setPlaceAvailablityDetails(PlaceAvailabilityDetails placeAvailablityDetails) {
        this.placeAvailablityDetails = placeAvailablityDetails;
    }

    public List<ProductsWithPrices> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(List<ProductsWithPrices> productDetails) {
        this.productDetails = productDetails;
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

    public long getNumberOfPromotions() {
        return numberOfPromotions;
    }

    public void setNumberOfPromotions(long numberOfPromotions) {
        this.numberOfPromotions = numberOfPromotions;
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

    public List<String> getPromotionIds() {
        return promotionIds;
    }

    public void setPromotionIds(List<String> promotionIds) {
        this.promotionIds = promotionIds;
    }

    public List<DhRatingAndComments> getTopRatings() {
        return topRatings;
    }

    public void setTopRatings(List<DhRatingAndComments> topRatings) {
        this.topRatings = topRatings;
    }

    public List<DhPromotions> getTopPromotions() {
        return topPromotions;
    }

    public void setTopPromotions(List<DhPromotions> topPromotions) {
        this.topPromotions = topPromotions;
    }

}



