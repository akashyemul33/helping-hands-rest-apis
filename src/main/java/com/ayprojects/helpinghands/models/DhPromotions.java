package com.ayprojects.helpinghands.models;

import org.springframework.data.annotation.Transient;

import java.util.List;

import jdk.nashorn.internal.ir.annotations.Ignore;

public class DhPromotions extends AllCommonUsedAttributes {
    private String promotionId;
    private String promotionType;
    private String placeId;
    private String addedBy;
    private String promotionTitle;
    private String promotionDesc;
    private List<String> videoThumbnails;
    private List<String> videoUrlsLow;
    private String offerMsg;

    public String getOfferMsg() {
        return offerMsg;
    }

    public void setOfferMsg(String offerMsg) {
        this.offerMsg = offerMsg;
    }

    public List<String> getVideoThumbnails() {
        return videoThumbnails;
    }

    public void setVideoThumbnails(List<String> videoThumbnails) {
        this.videoThumbnails = videoThumbnails;
    }

    public List<String> getVideoUrlsLow() {
        return videoUrlsLow;
    }

    public void setVideoUrlsLow(List<String> videoUrlsLow) {
        this.videoUrlsLow = videoUrlsLow;
    }

    public List<String> getVideoUrlsHigh() {
        return videoUrlsHigh;
    }

    public void setVideoUrlsHigh(List<String> videoUrlsHigh) {
        this.videoUrlsHigh = videoUrlsHigh;
    }

    private List<String> videoUrlsHigh;

    private List<String> promotionImagesLow;

    public List<String> getPromotionImagesHigh() {
        return promotionImagesHigh;
    }

    public void setPromotionImagesHigh(List<String> promotionImagesHigh) {
        this.promotionImagesHigh = promotionImagesHigh;
    }

    private List<String> promotionImagesHigh;
    private Contact contactDetails;
    private String fullAddress;
    private String fullName;
    private String offerStartTime;
    private String offerEndTime;
    private boolean areDetailsSameAsRegistered;
    private long numberOfRatings;
    private long numberOfViews;
    private double avgRating;
    @Transient
    @Ignore
    private String userName;//username
    @Transient
    @Ignore
    private String userImage;//userImage

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }


    private String placeName;
    @Transient
    @Ignore
    private String placeCategory;

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceCategory() {
        return placeCategory;
    }

    public void setPlaceCategory(String placeCategory) {
        this.placeCategory = placeCategory;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private List<String> ratingIds;
    private List<String> viewIds;
    private List<DhRatingAndComments> topRatings;
    private List<DhViews> topViews;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public DhPromotions() {

    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getPromotionTitle() {
        return promotionTitle;
    }

    public void setPromotionTitle(String promotionTitle) {
        this.promotionTitle = promotionTitle;
    }

    public String getPromotionDesc() {
        return promotionDesc;
    }

    public void setPromotionDesc(String promotionDesc) {
        this.promotionDesc = promotionDesc;
    }

    public List<String> getPromotionImagesLow() {
        return promotionImagesLow;
    }

    public void setPromotionImagesLow(List<String> promotionImagesLow) {
        this.promotionImagesLow = promotionImagesLow;
    }

    public Contact getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(Contact contactDetails) {
        this.contactDetails = contactDetails;
    }


    public String getOfferStartTime() {
        return offerStartTime;
    }

    public void setOfferStartTime(String offerStartTime) {
        this.offerStartTime = offerStartTime;
    }

    public String getOfferEndTime() {
        return offerEndTime;
    }

    public void setOfferEndTime(String offerEndTime) {
        this.offerEndTime = offerEndTime;
    }

    public boolean isAreDetailsSameAsRegistered() {
        return areDetailsSameAsRegistered;
    }

    public void setAreDetailsSameAsRegistered(boolean areDetailsSameAsRegistered) {
        this.areDetailsSameAsRegistered = areDetailsSameAsRegistered;
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

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public DhPromotions(String schemaVersion, String createdDateTime, String modifiedDateTime, String status, String promotionId, String promotionType, String placeId, String addedBy, String promotionTitle, String promotionDesc, List<String> promotionImagesLow, Contact contactDetails, String fullAddress, String offerStartTime, String offerEndTime, boolean areDetailsSameAsRegistered, long numberOfRatings, long numberOfViews, double avgRating, List<String> ratingIds, List<String> viewIds, List<DhRatingAndComments> topRatings, List<DhViews> topViews, String fullName) {
        this.schemaVersion =schemaVersion;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime =modifiedDateTime;
        this.status=status;
        this.promotionId = promotionId;
        this.promotionType = promotionType;
        this.placeId = placeId;
        this.addedBy = addedBy;
        this.promotionTitle = promotionTitle;
        this.promotionDesc = promotionDesc;
        this.promotionImagesLow = promotionImagesLow;
        this.contactDetails = contactDetails;
        this.fullAddress = fullAddress;
        this.offerStartTime = offerStartTime;
        this.offerEndTime = offerEndTime;
        this.areDetailsSameAsRegistered = areDetailsSameAsRegistered;
        this.numberOfRatings = numberOfRatings;
        this.numberOfViews = numberOfViews;
        this.avgRating = avgRating;
        this.ratingIds = ratingIds;
        this.viewIds = viewIds;
        this.topRatings = topRatings;
        this.topViews = topViews;
        this.fullName = fullName;
    }
}

